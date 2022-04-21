import pandas as pd
from sklearn.feature_extraction.text import TfidfTransformer
from sklearn.feature_extraction.text import CountVectorizer
from sklearn import metrics
from sklearn.metrics import accuracy_score
from sklearn import svm
import numpy as np
from sklearn.model_selection import train_test_split


import pickle




target_Names = ["Obedient", "No Obedient", "Dont Know"]

print("Processing data...")

good = pd.read_csv("Tweet Project\\Data\\GoodTweets.csv")
bad = pd.read_csv("Tweet Project\\Data\\BadTweets.csv")
random = pd.read_csv("Tweet Project\\Data\\RandomTweets.csv")
frames = [good, bad, random]
data = pd.concat(frames)
features = data['text']
target_values = data['label'].values
test = pd.read_csv("Tweet Project\\Data\\Final_Test_Data.csv")

X_train, _, _, _ = train_test_split(features, target_values, test_size=0.3, random_state=1, stratify=target_values)


X_test = test['text']

y_test = test['label'].values


count_vect = CountVectorizer()
X_train_tf = count_vect.fit_transform(X_train)

tfidf = TfidfTransformer()
X_train_tfidf = tfidf.fit_transform(X_train_tf)

X_test_tf = count_vect.transform(X_test)
X_test_tfidf = tfidf.transform(X_test_tf)

print("Data processed")
with open("Tweet Project\\model_tweet.pkl", "rb") as f:
    report = ""
    model = pickle.load(f)
    predicted = model.predict(X_test_tfidf)
    acc = accuracy_score(y_test, predicted)
    report += f'Accuracy: {acc}\n' 
    report += f'Report:\n {metrics.classification_report(y_test, predicted, target_names=target_Names)}\n'
    report += f'Confusion Matrix:\n{metrics.confusion_matrix(y_test, predicted)}\n'
    report += "------------------------------------------------------------------------\n\n"
    print(report)




