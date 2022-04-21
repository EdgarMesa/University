import numpy as np
from sklearn.ensemble import RandomForestClassifier
from sklearn.ensemble import VotingClassifier
from sklearn.pipeline import Pipeline
from sklearn.metrics import accuracy_score
from sklearn import metrics
from sklearn.base import BaseEstimator
from sklearn.preprocessing import StandardScaler
from sklearn.model_selection import train_test_split
from sklearn.model_selection import cross_val_score
from sklearn.datasets import load_breast_cancer
from sklearn.tree import DecisionTreeClassifier
from sklearn.neighbors import KNeighborsClassifier
from sklearn.naive_bayes import MultinomialNB
from sklearn import svm



if __name__=="__main__":

    cancer_data = load_breast_cancer()
    X, y = cancer_data.data, cancer_data.target

    target_names = cancer_data.target_names
    criterions = ["gini", "entropy"]
    max_deph = [1, 2, 3, 4, 5, 6, 7, 8, 9, 11, 12, 13, None]
    classifiers = ["Random Forest", "Decision Tree", "KNN", "Naive Bayes", "SVM", "voting_clf_hard"]
    all_scores = np.zeros(6)

    # max_score_data_randomForest = {"max_score" : 0, "criterion" : " ", "n_estimator" : 100, "max_depth" : 1}
    # for criteria in criterions:
    #     for i in range(100, 151, 5):
    #         for dep in max_deph:
    #             print("Criterion: {} n_estimator: {} max_depth: {}".format(criteria, i, dep))
    #             ramdom_forest = RandomForestClassifier(n_estimators=i, criterion=criteria, max_depth=dep)
    #             cls = Pipeline([['sc', StandardScaler()], ['clf', ramdom_forest]])
    #             scores = cross_val_score(estimator=cls, X=X, y=y, cv=10, scoring="accuracy").mean()
    #
    #             if scores > max_score_data_randomForest["max_score"]:
    #                 max_score_data_randomForest["max_score"] = scores
    #                 max_score_data_randomForest["criterion"] = criteria
    #                 max_score_data_randomForest["n_estimator"] = i
    #                 max_score_data_randomForest["max_depth"] = dep
    # print(max_score_data_randomForest)

    x_train, x_test, y_train, y_test = train_test_split(X, y, test_size=0.2)

    ramdom_forest = RandomForestClassifier(n_estimators=150, criterion="gini", max_depth=13)
    cls_random = Pipeline([['sc', StandardScaler()], ['clf', ramdom_forest]])



    # max_score_data_tree = {"max_score": 0, "criterion": " ", "max_depth": 2}
    # for criteria in criterions:
        # for i in range(2, 31):
        #     print("Criterion: {} max_depth: {}".format(criteria, i))
        #     forest = DecisionTreeClassifier( criterion=criteria, max_depth=i)
        #     cls = Pipeline([['sc', StandardScaler()], ['clf', forest]])
        #     scores = cross_val_score(estimator=cls, X=X, y=y, cv=10, scoring="accuracy").mean()
        #     if scores > max_score_data_tree["max_score"]:
        #         max_score_data_tree["max_score"] = scores
        #         max_score_data_tree["criterion"] = criteria
        #         max_score_data_tree["max_depth"] = i
    # print(max_score_data_tree)

    tree = DecisionTreeClassifier(criterion="entropy", max_depth=19)
    cls_tree = Pipeline([['sc', StandardScaler()], ['clf', tree]])


    # tree_form = ["ball_tree", "kd_tree"]
    # max_score_data_knn = {"tree_form": 0, "n_neighbors": 1}
    # for form in tree_form:
    #     for i in range(2, 51):
    #         print("tree_form: {} n_neighbors: {}".format(form, i))
    #         knn = KNeighborsClassifier(n_neighbors=i, algorithm=form)
    #         cls = Pipeline([['sc', StandardScaler()], ['clf', knn]])
    #         scores = cross_val_score(estimator=cls, X=X, y=y, cv=10, scoring="accuracy").mean()
    #         print(scores)
    #         if scores > max_score_data_tree["max_score"]:
    #             max_score_data_knn["max_score"] = scores
    #             max_score_data_knn["tree_form"] = form
    #             max_score_data_knn["n_neighbors"] = i
    # print(max_score_data_knn)


    knn = KNeighborsClassifier(n_neighbors=50, algorithm="kd_tree")
    cls_knn = Pipeline([['sc', StandardScaler()], ['clf', tree]])


    text_nv = MultinomialNB()

    # max_score_data_svm = {"max_score": 0, "models": " ", "C": 1}
    # models = ["linear", "poly", "rbf", "sigmoid"]
    # for model in models:
    #     for i in range(1, 11):
    #         print("model: {} C: {}".format(model, i))
    #         smv = svm.SVC(kernel=model, C=i)
    #         scores = cross_val_score(estimator=smv, X=X, y=y, cv=10, scoring="accuracy").mean()
    #         print(scores)
    #         if scores > max_score_data_svm["max_score"]:
    #             max_score_data_svm["max_score"] = scores
    #             max_score_data_svm["models"] = model
    #             max_score_data_svm["C"] = i
    # print(max_score_data_svm)

    text_svm = svm.SVC(kernel="linear", C=6)


    voting_clf_hard = VotingClassifier(estimators = [(classifiers[0], cls_random),
                                                     (classifiers[1], cls_tree),
                                                     (classifiers[2], cls_knn),
                                                     (classifiers[3], text_nv),
                                                     (classifiers[4], text_svm)],
                                        voting="hard")


    i = 0
    for cls, label in zip([cls_random, cls_tree, cls_knn, text_nv, text_svm, voting_clf_hard], classifiers):
        cls.fit(x_train, y_train)
        prediction = cls.predict(x_test)
        score = accuracy_score(prediction, y_test)
        all_scores[i] = score
        i += 1
        report = metrics.classification_report(prediction, y_test, target_names=target_names)
        confusion_matrix = metrics.confusion_matrix(prediction, y_test)
        print(label)
        print("Average score: {}\nConfusion matrix:\n {}\nReport:\n {}\n".format(score, confusion_matrix, report))
        print()

    print("All the scores: ", all_scores)
    print("Best score is {} for the model {} ".format(max(all_scores), classifiers[np.argmax(all_scores)]))


