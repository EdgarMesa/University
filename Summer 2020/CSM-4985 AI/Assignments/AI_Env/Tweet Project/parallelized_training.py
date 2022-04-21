import numpy as np
import pandas as pd
from sklearn.naive_bayes import MultinomialNB
from sklearn.feature_extraction.text import TfidfTransformer
from sklearn.feature_extraction.text import CountVectorizer
from sklearn import metrics
from sklearn.metrics import accuracy_score
from sklearn import svm
from sklearn.model_selection import train_test_split
import pickle
import concurrent.futures



target_Names = ["Obedient", "No Obedient", "Dont Know"]


good = pd.read_csv("GoodTweets.csv")
bad = pd.read_csv("BadTweets.csv")
random = pd.read_csv("RandomTweets.csv")
frames = [good, bad, random]
data = pd.concat(frames)

AI_models = ["Naive Bayes", "SVM(linear)", "SVM(rbf)"]
print("Processing data...")

features = data['text']
target_values = data['label'].values



X_train, X_test, y_train, y_test = train_test_split(features, target_values, test_size=0.3, random_state=1, stratify=target_values)

count_vect = CountVectorizer()
X_train_tf = count_vect.fit_transform(X_train)

tfidf = TfidfTransformer()
X_train_tfidf = tfidf.fit_transform(X_train_tf)

X_test_tf = count_vect.transform(X_test)
X_test_tfidf = tfidf.transform(X_test_tf)

print("Data processed")


def training(model, c):
    print(f'Training SVM Using Kernel {model} with c:{c}...')
    SVM = svm.SVC(kernel=model, C=c, gamma="scale")
    SVM.fit(X_train_tfidf, y_train)
    print(f'Done Training SVM Using Kernel {model} with c:{c}')
    return SVM




print("Starting training of the models...")

threads = []

models = ["linear", "rbf"]
with open("results.txt", "w") as file:
    scores = {}

    with concurrent.futures.ProcessPoolExecutor() as executor: #ThreadPoolExecutor() instead in case does not work.


        futures = {
            executor.submit(training, model, c) :
            f'{model} c:{c}' for model in models for c in range(1, 31)
        }

        i = 1
        for mod in concurrent.futures.as_completed(futures):
            if i == 1:
                print()

            original = futures[mod]
            
            report = f'SVM Using Kernel {original}\n'
            predicted = mod.result().predict(X_test_tfidf)
            acc = accuracy_score(y_test, predicted)
            report += f'Accuracy For SVM Using Kernel {original}: {acc}\n' 
            report += f'Report for SVM({original}):\n {metrics.classification_report(y_test, predicted, target_names=target_Names)}\n'
            report += f'Confusion Matrix for SVM({original}):\n{metrics.confusion_matrix(y_test, predicted)}\n'
            report += "------------------------------------------------------------------------\n\n"
            file.write(report)
            print(f'Report written for {original}. {i}/{len(futures)} models completed')
            i += 1
            scores[mod.result()] = {"name":original, "score":acc}

    
            
    best = {"model":None, "score":0, "name":""}
    for mod in scores:
        score = scores[mod]['score']
        if score > best['score']:
            best['score'] = score
            best['model'] = mod
            best['name'] = scores[mod]['name']
     

    file.write('The best model was {} with an accuaracy of {}\n'.format(best['name'], best['score']))

    best_model = best['model']

    with open(best['name'] + ".pkl", "wb") as mo:
        pickle.dump(best_model, mo)
        print("\nBest model saved")

