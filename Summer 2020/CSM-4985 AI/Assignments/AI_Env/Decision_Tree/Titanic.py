import pandas as pd
import numpy as np
from sklearn.tree import DecisionTreeClassifier
from pydotplus import graph_from_dot_data
from sklearn.tree import export_graphviz
import pickle
import re
from sklearn import preprocessing

# Define function to extract titles from passenger names
def get_title(name):
    title_search = re.search(' ([A-Za-z]+)\.', name)
    # If the title exists, extract and return it.
    if title_search:
        return title_search.group(1)
    return ""
X_train = pd.read_csv('train.csv')
X_test = pd.read_csv('test.csv')
y_test = pd.read_csv('gender_submission.csv')
y_test = y_test["Survived"].values
y_train = X_train["Survived"].values
original_data = X_train.copy()


full_data = [X_train, X_test]

missing_values = X_train.isnull().sum()

print("Missing values:")
print(missing_values)

# Feature that tells whether a passenger had a cabin on the Titanic
X_train["Has_Cabin"] = np.where(X_train["Cabin"].isna(), 0, 1)
X_test["Has_Cabin"] = np.where(X_test["Cabin"].isna(), 0, 1)

# Create new feature FamilySize as a combination of SibSp and Parch
for dataset in full_data:
    dataset["Family Size"] = dataset["SibSp"] + dataset["Parch"] + 1

# Create new feature IsAlone from FamilySize
for dataset in full_data:
    dataset["IsALone"] = np.where(dataset["Family Size"] == 1, 1, 0)

# Remove all NULLS in the Embarked column
for dataset in full_data:
    dataset["Embarked"] = dataset["Embarked"].fillna('S')


# Remove all NULLS in the Fare column
for dataset in full_data:
    dataset["Fare"] = dataset["Fare"].fillna(X_train["Fare"].median())

# Remove all the NULLS in the Age column
for dataset in full_data:
    avg_age = dataset["Age"].mean()
    std_age = dataset["Age"].std()
    age_null_count = dataset["Age"].isnull().sum()
    random_list_ages = np.random.randint(avg_age - std_age, avg_age + std_age, size=age_null_count)
    
    dataset.loc[dataset["Age"].isna(), "Age"] = random_list_ages
    dataset["Age"] = dataset["Age"].astype(int)

for dataset in full_data:
    dataset["Title"] = dataset["Name"].apply(get_title)

# Group all non-common titles into one single grouping "Rare"
for dataset in full_data:
    dataset['Title'] = dataset['Title'].replace(['Lady', 'Countess','Capt', 'Col','Don', 'Dr', 'Major', 'Rev', 'Sir', 'Jonkheer', 'Dona'], 'Rare')

    dataset['Title'] = dataset['Title'].replace('Mlle', 'Miss')
    dataset['Title'] = dataset['Title'].replace('Ms', 'Miss')
    dataset['Title'] = dataset['Title'].replace('Mme', 'Mrs')

le = preprocessing.LabelEncoder()

for dataset in full_data:
    # Mapping Sex
    dataset["Sex"] = le.fit_transform(dataset["Sex"].values).astype(int)

    # Mapping titles
    titles_mapping = {"Mr" : 1, "Master" : 2, "Mrs" : 3, "Miss" : 4, "Rare": 5}
    dataset["Title"] = dataset["Title"].map(titles_mapping.get).astype(int)
    dataset["Title"] = dataset["Title"].fillna(0)

    # Mapping Embarked
    dataset['Embarked'] = dataset['Embarked'].map( {'S': 0, 'C': 1, 'Q': 2} ).astype(int)

    # Mapping Fare
    dataset.loc[dataset['Fare'] <= 7.91, 'Fare'] = 0
    dataset.loc[(dataset['Fare'] > 7.91) & (dataset['Fare'] <= 14.454), 'Fare'] = 1
    dataset.loc[(dataset['Fare'] > 14.454) & (dataset['Fare'] <= 31), 'Fare'] = 2
    dataset.loc[dataset['Fare'] > 31, 'Fare'] = 3
    dataset['Fare'] = dataset['Fare'].astype(int)

    # Mapping Age
    dataset.loc[dataset['Age'] <= 16, 'Age'] = 0
    dataset.loc[(dataset['Age'] > 16) & (dataset['Age'] <= 32), 'Age'] = 1
    dataset.loc[(dataset['Age'] > 32) & (dataset['Age'] <= 48), 'Age'] = 2
    dataset.loc[(dataset['Age'] > 48) & (dataset['Age'] <= 64), 'Age'] = 3
    dataset.loc[dataset['Age'] > 64, 'Age'] = 4;


# Feature selection: remove variables no longer containing relevant information
drop_elements1 = ['PassengerId', "Survived", 'Name', 'Ticket', 'Cabin', 'SibSp']
drop_elements2 = ['PassengerId', 'Name', 'Ticket', 'Cabin', 'SibSp']


X_train = X_train.drop(drop_elements1, axis=1)
X_test = X_test.drop(drop_elements2, axis=1)
best_gini = 0
best_entropy = 0
best_index = np.array([0, 0])

for i in range(2, 11):

    tree_gini = DecisionTreeClassifier(criterion='gini', max_depth=i)
    tree_entropy = DecisionTreeClassifier(criterion='entropy', max_depth=i)

    tree_gini.fit(X_train, y_train)
    tree_entropy.fit(X_train, y_train)

    acc_gini = tree_gini.score(X_test, y_test)
    acc_entropy = tree_entropy.score(X_test, y_test)

    if acc_gini > best_gini:
        best_gini = acc_gini
        best_index[0] = i
        with open("titanic_gini_model.pickle", "wb") as f:
            pickle.dump(tree_gini, f)

    if acc_entropy > best_entropy:
        best_entropy = acc_entropy
        best_index[1] = i
        with open("titanic_entropy_model.pickle", "wb") as f:
            pickle.dump(tree_entropy, f)

best_gini_model = open("titanic_gini_model.pickle", "rb")
tree_gini = pickle.load(best_gini_model)

best_entropy_model = open("titanic_entropy_model.pickle", "rb")
tree_entropy = pickle.load(best_entropy_model)

print("Score for the gini measure: %s with a max length of %s" % (best_gini, best_index[0]))
print("Score for the entropy measure: %s with a max length of %s" % (best_entropy, best_index[1]))

features = X_train.columns
classes = ["Survived", "No Survived"]

dot_data_gini = export_graphviz(tree_gini,
                           filled=True,
                           rounded=True,
                           class_names=classes,
                           feature_names=features,
                           out_file=None)

graph = graph_from_dot_data(dot_data_gini)
graph.write_png('Tree_gini_titanic.png')

dot_data_entorpy = export_graphviz(tree_entropy,
                           filled=True,
                           rounded=True,
                           class_names=classes,
                           feature_names=features,
                           out_file=None)

graph2 = graph_from_dot_data(dot_data_entorpy)
graph2.write_png('Tree_entropy_titanic.png')


                
