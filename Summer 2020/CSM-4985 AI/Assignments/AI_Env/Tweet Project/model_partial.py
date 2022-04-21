import numpy as np
import pandas as pd
from sklearn.naive_bayes import MultinomialNB
from sklearn.feature_extraction.text import TfidfTransformer
from sklearn.feature_extraction.text import CountVectorizer
from sklearn import metrics
from sklearn.metrics import accuracy_score
from sklearn import svm
from sklearn.model_selection import train_test_split



if __name__ == "__main__":

    good = pd.read_csv("Tweet Project\\GoodTweets.csv")
    bad = pd.read_csv("Tweet Project\\BadTweets.csv")
    random = pd.read_csv("Tweet Project\\RandomTweets.csv")
    frames = [good.iloc[:2000], bad, random.iloc[:2000]]

    data = pd.concat(frames)
    target_Names = ["Obedient", "No Obedient", "Dont Know"]

    best_score = []
    AI_models = ["Naive Bayes", "SVM(linear)", "SVM(rbf)"]

    features = data['text']
    target_values = data['label'].values



    X_train, X_test, y_train, y_test = train_test_split(features, target_values, test_size=0.3, random_state=1, stratify=target_values)

    count_vect = CountVectorizer()
    X_train_tf = count_vect.fit_transform(X_train)

    tfidf = TfidfTransformer()
    X_train_tfidf = tfidf.fit_transform(X_train_tf)

    X_test_tf = count_vect.transform(X_test)
    X_test_tfidf = tfidf.transform(X_test_tf)

    print("Training Naive Bayes model")
    NaiveBayes = MultinomialNB().fit(X_train_tfidf, y_train)


    predicted = NaiveBayes.predict(X_test_tfidf)
    acc = accuracy_score(y_test, predicted)
    best_score.append(acc)
    print("Accuracy For Naive Bayes: ", acc)
    print("Report for Naive Bayes:\n ",
          metrics.classification_report(y_test, predicted, target_names=target_Names))
    print("Confusion Matrix for Naive Bayes:\n", metrics.confusion_matrix(y_test, predicted))

    print("----------------------------------------------------------------------------------------------")

    models = ["linear", "rbf"]

    for model in models:
        SVM = svm.SVC(kernel=model, C=5)
        print(f'Training SVM Using Kernel {model}')
        SVM.fit(X_train_tfidf, y_train)
        predicted = SVM.predict(X_test_tfidf)
        acc = accuracy_score(y_test, predicted)
        best_score.append(acc)
        print(f'Accuracy For SVM Using Kernel {model}: ', acc)
        print(f'Report for SVM({model}):\n ',
        metrics.classification_report(y_test, predicted, target_names=target_Names))
        print(f'Confusion Matrix for SVM({model}):\n', metrics.confusion_matrix(y_test, predicted))
        print("----------------------------------------------------------------------------------------------")


    best = max(best_score)

    print(f'The best model was {AI_models[best_score.index(best)]} with a accuracy of {best}')