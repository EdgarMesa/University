import matplotlib.pyplot as plt
import numpy as np
import sklearn
from matplotlib.colors import ListedColormap
import pandas as pd
from sklearn import preprocessing
from sklearn.neighbors import KNeighborsClassifier




if __name__ == "__main__":
    
    data = pd.read_csv("KNN\\BlackFriday.csv")


    data = data.drop(["User_ID", "Product_ID", "Product_Category_2", "Product_Category_3"], 1)
    Ages = data["Age"].values
    Final_Ages = []
    for age in Ages:
        average_age = 0
        try:
            first, second = map(int, age.split("-"))
            average_age = (first + second) // 2
        except:
            average_age = 55
        Final_Ages.append(average_age)

    Final_Ages = np.array(Final_Ages)


    le = preprocessing.LabelEncoder()

    Gender = le.fit_transform(data["Gender"].values)
    Occupation = le.fit_transform(data["Occupation"].values)
    City_Category = le.fit_transform(data["City_Category"].values)
    Stay_In_Current_City_Years = le.fit_transform(data["Stay_In_Current_City_Years"].values)


    pd.set_option('display.max_rows', None)
    pd.set_option('display.max_columns', None)
    


    X = np.array(list(zip(Final_Ages, Occupation, City_Category, Stay_In_Current_City_Years, data["Marital_Status"], data["Product_Category_1"], data["Purchase"])))
    y = np.array(Gender)


    x_train, x_test, y_train, y_test = sklearn.model_selection.train_test_split(X, y, test_size=0.3)


    KNN = KNeighborsClassifier(n_neighbors= 31)
    KNN.fit(x_train, y_train)

    acc = KNN.score(x_test, y_test)
    print("Score: ", acc)







