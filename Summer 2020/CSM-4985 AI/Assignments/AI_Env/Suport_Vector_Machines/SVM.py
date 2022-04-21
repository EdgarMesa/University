
from sklearn.datasets import fetch_20newsgroups
from sklearn.naive_bayes import MultinomialNB
from sklearn.feature_extraction.text import TfidfTransformer
from sklearn.feature_extraction.text import CountVectorizer
from sklearn import metrics
from sklearn.metrics import accuracy_score
from sklearn import svm
import matplotlib.pyplot as plt
import statistics
import numpy as np



if __name__ == "__main__":

    categories = ["rec.sport.baseball", "talk.politics.guns", "sci.space", "sci.electronics", "soc.religion.christian"]
    news_train = fetch_20newsgroups(subset='train', categories=categories, shuffle=True)
    news_test = fetch_20newsgroups(subset='test', categories=categories, shuffle=True)

    target_Names = news_train.target_names
    print(target_Names)

    
    count_vect = CountVectorizer()
    X_train_tf = count_vect.fit_transform(news_train.data)
    tfidf = TfidfTransformer()
    X_train_tfidf = tfidf.fit_transform(X_train_tf)

    text_clf = MultinomialNB().fit(X_train_tfidf, news_train.target)
    X_test = count_vect.transform(news_test.data)
    X_test_tfidf = tfidf.transform(X_test)


    predicted = text_clf.predict(X_test_tfidf)

    print("Accuracy For Naive Bayes: ", accuracy_score(news_test.target, predicted))
    print("Report for Naive Bayes:\n ",
          metrics.classification_report(news_test.target, predicted, target_names=target_Names))
    print("Confusion Matrix for Naive Bayes:\n", metrics.confusion_matrix(news_test.target, predicted))

    print("----------------------------------------------------------------------------------------------")

    models = ["linear", "poly", "rbf", "sigmoid"]
    scores = [[0 for _ in range(1, 11)] for _ in range(4)]

    avrg_scores = []

    for j in range(4):
        for i in range(1, 11):
            #print("Model: ", models[j], "C: ", i)
            SVM = svm.SVC(kernel=models[j], C=i)
            SVM.fit(X_train_tfidf, news_train.target)
            predicted = SVM.predict(X_test_tfidf)
            scores[j][i - 1] = accuracy_score(news_test.target, predicted)
            if j == 0 and i == 1:
                print("Report for SVM:\n ",
                      metrics.classification_report(news_test.target, predicted, target_names=target_Names))
                print("Confusion Matrix for SVM:\n", metrics.confusion_matrix(news_test.target, predicted))


        avrg_scores.append(statistics.mean(scores[j]))
    
    for i in range(4):
        best = np.array(scores[i])
        best_C = np.argmax(best) + 1
        print(">>")
        print(" Average Accuracy For " + models[i]+" : ", avrg_scores[i])
        print(" For this SVM model, best value of C: ", best_C, "With a score of: ", best[best_C - 1] )
    
    plt.plot(np.arange(1, 11), scores[0], label=models[0])
    plt.plot(np.arange(1, 11), scores[1], label=models[1])
    plt.plot(np.arange(1, 11), scores[2], label=models[2])
    plt.plot(np.arange(1, 11), scores[3], label=models[3])

    plt.ylabel("Percentage of Correctness")
    plt.xlabel("Value of C")
    plt.legend(loc="center right")
    plt.tight_layout()
    plt.title("Models of SVM for Different Values of C")
    plt.savefig("Models of SVM for Different Values of C")






