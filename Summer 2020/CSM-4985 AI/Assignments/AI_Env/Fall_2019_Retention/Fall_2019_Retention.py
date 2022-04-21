import pandas as pd
import numpy as np

pd.set_option('display.max_columns', None)
pd.set_option('display.max_rows', None)

data = pd.read_csv("Fall_2019_Retention.csv")

features = data.columns
print(features)
missing_values = data.isnull().sum()
print(missing_values)
print(data.shape)

number_rows = data.shape[0]
dict_topvalues = {}
for row, name in zip(missing_values, features):
    if row > (number_rows // 3):
        dict_topvalues[name] = row

print(dict_topvalues)
print(len(dict_topvalues.keys()))
sorted_dict = sorted(dict_topvalues.items(), key=lambda x: x[1], reverse=True)

print(sorted_dict)
