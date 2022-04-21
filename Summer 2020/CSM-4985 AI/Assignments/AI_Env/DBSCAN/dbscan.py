# %%
from sklearn.datasets import make_moons
import matplotlib.pyplot as plt
X, y = make_moons(n_samples=200, noise=0.05, random_state=0)
plt.scatter(X[:, 0], X[:, 1])
plt.show()
plt.scatter(X[y == 0, 0], X[y == 0, 1],s = 50, c='lightblue', marker='o',
                    edgecolors='black', label='clustering 1')
plt.scatter(X[y == 1, 0], X[y == 1, 1],s = 50, c='red', marker='s',
                    edgecolors='black', label='clustering 2')
plt.legend()
plt.show()

# %%
from sklearn.cluster import KMeans
from sklearn.cluster import AgglomerativeClustering
import numpy as np
colors = ['lightblue', 'red', 'blue']
markers = ['o', 's', 'x']
f, (ax1, ax2) = plt.subplots(1, 2, figsize=(8, 3))
km = KMeans(n_clusters=2, random_state=0)
y_km = km.fit_predict(X)
labels = np.unique(y_km)

for i, (label, c, mark) in enumerate(zip(labels, colors, markers)):
    ax1.scatter(X[y_km == label, 0], X[y_km == label, 1],
                c=c, edgecolor='black', marker=mark, s=40, label='cluster ' + str(i))

ax1.set_title('K-means clustering')

ac = AgglomerativeClustering(n_clusters=2, affinity='euclidean', linkage='complete')
y_ac = ac.fit_predict(X)

for i, (label, c, mark) in enumerate(zip(labels, colors, markers)):
    ax2.scatter(X[y_ac == label, 0], X[y_ac == label, 1],
                c=c, edgecolor='black', marker=mark, s=40, label='cluster ' + str(i))

ax2.set_title('Agglomerative clustering')
plt.legend()
plt.show()

# %%
from sklearn.cluster import DBSCAN
db = DBSCAN(eps=0.2, min_samples=5, metric ='euclidean')
y_db = db.fit_predict(X)

for i, (label, c, mark) in enumerate(zip(labels, colors, markers)):
    plt.scatter(X[y_db == label, 0], X[y_db == label, 1],
                c=c, edgecolor='black', marker=mark, s=40, label='cluster ' + str(i))

ax2.set_title('DBSCAN clustering')
plt.legend()
plt.show()
