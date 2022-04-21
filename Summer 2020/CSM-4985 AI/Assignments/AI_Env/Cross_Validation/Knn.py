import matplotlib.pyplot as plt
import numpy as np
import pandas as pd
from sklearn import preprocessing
from sklearn.neighbors import KNeighborsClassifier
from sklearn.model_selection import cross_val_score




if __name__ == "__main__":

    pd.set_option('display.max_rows', None)
    pd.set_option('display.max_columns', None)
    data = pd.read_csv("C:\\Users\\Lenovo\\Desktop\\EIU\\Summer 2020\\CSM-4985 AI\\Assignments\\AI_Env\\KNN\\BlackFriday.csv")
    data = data.drop(["User_ID", "Product_ID", "Product_Category_2", "Product_Category_3"], 1)
    Ages = data["Age"].values
    Final_Ages = []
    for age in Ages:
        average_age = 0
        try:
            first, second = map(int, age.split("-"))
            average_age = (first + second) // 2
        except:
            average_age = (55 + 75) // 2
        Final_Ages.append(average_age)

    Final_Ages = np.array(Final_Ages)

    le = preprocessing.LabelEncoder()

    Gender = le.fit_transform(data["Gender"].values)
    Occupation = le.fit_transform(data["Occupation"].values)
    City_Category = le.fit_transform(data["City_Category"].values)
    Stay_In_Current_City_Years = le.fit_transform(data["Stay_In_Current_City_Years"].values)



    X = np.array(list(zip(Final_Ages, Occupation, City_Category, Stay_In_Current_City_Years, data["Marital_Status"],
                          data["Product_Category_1"], data["Purchase"])))
    y = np.array(Gender)
    k_neigh = list(range(1, 32))
    scores = []

    for k in k_neigh:
        KNN = KNeighborsClassifier(n_neighbors=k)
        k_scores = cross_val_score(KNN, X, y, cv=10, scoring="accuracy")
        scores.append(k_scores.mean())

    print(scores)
    plt.plot(k_neigh, scores)
    plt.xlabel("Values of K for KNN")
    plt.ylabel("Cross-Validated Accuracy")
    plt.savefig("Cross_Validation_KNN")


        








