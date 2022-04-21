from sklearn.datasets import fetch_20newsgroups
from sklearn.pipeline import Pipeline
from sklearn.naive_bayes import MultinomialNB
from sklearn.feature_extraction.text import TfidfTransformer
from sklearn.feature_extraction.text import CountVectorizer
from sklearn import metrics
from sklearn.metrics import accuracy_score

categories = ["rec.sport.baseball", "talk.politics.guns", "sci.space", "sci.electronics", "soc.religion.christian"]

news_train = fetch_20newsgroups(subset='train', categories=categories, shuffle=True)
news_test = fetch_20newsgroups(subset='test', categories=categories, shuffle=True)
target_Names = news_train.target_names
print(target_Names)

text_clf = Pipeline([
    ('vectorizer', CountVectorizer()), 
    ('tfidf', TfidfTransformer()), 
    ('bayes', MultinomialNB())])

text_clf.fit(news_train.data, news_train.target)
predicted = text_clf.predict(news_test.data)

print("Accuracy: ", accuracy_score(news_test.target, predicted))
print(metrics.classification_report(news_test.target, predicted, target_names=target_Names))
print(metrics.confusion_matrix(news_test.target, predicted))
