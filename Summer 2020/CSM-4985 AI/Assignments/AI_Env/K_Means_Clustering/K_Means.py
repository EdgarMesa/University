import numpy as np
import matplotlib.pyplot as plt
from sklearn import metrics
from sklearn.preprocessing import scale
from sklearn.cluster import KMeans
import pandas as pd
from time import time
import numpy as np
from matplotlib import cm
from sklearn.metrics import silhouette_samples

def bench_k_means(estimator, name, data):
    t0 = time()
    global n_nalues
    global labels
    estimator.fit(data)

    #labels_est = labels[len(np.unique(estimator.labels_))]
    print('%-9s\t%.5fs\t%i'
          % (name, (time() - t0), estimator.inertia_))

def Silhouette_graph(estimator, data ,title):

    y_km = estimator.labels_
    cluster_labels = np.unique(y_km)
    n_clusters = cluster_labels.shape[0]
    silhouette_vals = silhouette_samples(data,
                                         y_km,
                                         metric='euclidean')
    y_ax_lower, y_ax_upper = 0, 0
    yticks = []
    for i, c in enumerate(cluster_labels):
        c_silhouette_vals = silhouette_vals[y_km == c]
        c_silhouette_vals.sort()
        y_ax_upper += len(c_silhouette_vals)
        color = cm.jet(float(i) / n_clusters)
        plt.barh(range(y_ax_lower, y_ax_upper),
                 c_silhouette_vals,
                 height=1.0,
                 edgecolor='none',
                 color=color)
        yticks.append((y_ax_lower + y_ax_upper) / 2.)
        y_ax_lower += len(c_silhouette_vals)
    silhouette_avg = np.mean(silhouette_vals)
    plt.axvline(silhouette_avg,
                color="red",
                linestyle="--")
    plt.yticks(yticks, cluster_labels + 1)
    plt.ylabel('Cluster')
    plt.xlabel('Silhouette coefficient')
    plt.title(title)
    plt.savefig(title)
    plt.show()

def plot_estimator(estimator, colors, markers, title):

    estimator.fit(X)
    y_km = estimator.labels_
    labels = np.unique(y_km)

    for label, color, marker in zip(labels, colors, markers):
        plt.scatter(X[y_km == label, 0],
                    X[y_km == label, 1],
                    s = 50, c=color, marker=marker,
                    edgecolors='black', label="cluster "+ str(label))
    plt.scatter(estimator.cluster_centers_[:, 0],
                estimator.cluster_centers_[:, 1],
                s=50, c='red', marker='*',
                edgecolors='black', label="centroids")
    plt.legend(scatterpoints=1)
    plt.grid()
    plt.title(title)
    plt.savefig(title)
    plt.show()



if __name__=="__main__":
    labels = np.arange(50)
    full_data = pd.read_csv("xclara.csv")
    X = full_data.values
    X = scale(X)

    n_nalues, n_atrributes = X.shape
    
    colors = ['lightgreen', 'orange', 'lightblue']
    markers = ['s', 'o', 'v']

    bad_km = KMeans(n_clusters=2,
                init='k-means++',
                n_init=10,
                max_iter=300,
                random_state=0)

    plot_estimator(bad_km, colors, markers, "Bad K-means++ with two clusters")


    distortion = []
    print(82 * '_')
    print('init\t\ttime\tinertia')
    for k in range(1, 18):

        km = KMeans(n_clusters=k,
                    init='k-means++',
                    n_init=10,
                    max_iter=300,
                    random_state=0)
        bench_k_means(km, str(k), X)
        distortion.append(km.inertia_)
    print(82 * '_')
    plt.plot(np.arange(1, 18), distortion, marker='o')
    plt.xlabel("Number of clusters")
    plt.ylabel("Distortion")
    title = "Elbow method"
    plt.title(title)
    plt.savefig(title)
    plt.show()


    km_mean_plus = KMeans(n_clusters=3,
                init='k-means++',
                n_init=10,
                max_iter=300,
                random_state=0)
    plot_estimator(km_mean_plus, colors, markers, "Best k-means++")

    km_mean = KMeans(n_clusters=3,
                init='random',
                n_init=10,
                max_iter=300,
                random_state=0)
    plot_estimator(km_mean, colors, markers, "Best k-means (random)")
    print('init\t\ttime\tinertia')
    bench_k_means(km_mean_plus, "k-means++", X)
    bench_k_means(km_mean, "random", X)

    Silhouette_graph(km_mean_plus, X, "Silhouette graph for k-means++ with k = 3")
    Silhouette_graph(km_mean, X, "Silhouette graph for k-means (random) with k = 3")
    Silhouette_graph(bad_km, X, "Silhouette graph for bad k-means++ with k = 2")





    

