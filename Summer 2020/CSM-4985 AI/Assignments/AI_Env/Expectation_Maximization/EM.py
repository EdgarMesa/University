#%%
import matplotlib.pyplot as plt
from numpy.random import normal
from numpy import hstack

sample = normal(size=2000)
plt.hist(sample, bins=50)
# %%
sample1 = normal(loc=20, scale=5, size=4000)
sample2 = normal(loc=40, scale=5, size=8000)
sample = hstack((sample1, sample2))

plt.hist(sample, bins=50)


# %%
from sklearn.mixture import GaussianMixture
sample = sample.reshape(len(sample), 1)
model = GaussianMixture(n_components=2, init_params='random')
model.fit(sample)
y = model.predict(sample)
print(y[:80])
print(y)

