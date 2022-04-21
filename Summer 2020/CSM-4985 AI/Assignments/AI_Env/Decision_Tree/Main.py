from KNN.Main import plot_decision_regions
import numpy as np
import matplotlib.pyplot as plt
from sklearn import datasets
from sklearn.model_selection import train_test_split
from sklearn.tree import DecisionTreeClassifier
from pydotplus import graph_from_dot_data
from sklearn.tree import export_graphviz


data = datasets.load_iris()

X = data.data[:, [2, 3]]
y = data.target

X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.3, random_state=1, stratify=y)

tree_gini = DecisionTreeClassifier(criterion='gini', max_depth=3, random_state=1)
tree_entropy = DecisionTreeClassifier(criterion='entropy', max_depth=4, random_state=1)

tree_gini.fit(X_train, y_train)
tree_entropy.fit(X_train, y_train)

acc_gini = tree_gini.score(X_test, y_test)
acc_entropy = tree_gini.score(X_test, y_test)
print(acc_gini)
print(acc_entropy)

combined_X, combined_Y = np.vstack((X_train, X_test)), np.hstack((y_train, y_test))
plot_decision_regions(combined_X, combined_Y, classifier=tree_gini, test=X_test)

plt.title("Gini Impurity Measure")
plt.xlabel('petal length [cm]')
plt.ylabel('petal width  [cm]')
plt.legend(loc='upper left')
plt.tight_layout()
plt.show()
plot_decision_regions(combined_X, combined_Y, classifier=tree_entropy, test=X_test)

plt.title("Entropy Impurity Measure")
plt.show()

dot_data_gini = export_graphviz(tree_gini,
                           filled=True,
                           rounded=True,
                           class_names=['Setosa',
                                        'Versicolor',
                                        'Virginica'],
                           feature_names=['petal length',
                                          'petal width'],
                           out_file=None)

graph = graph_from_dot_data(dot_data_gini)
graph.write_png('Tree_gini.png')













