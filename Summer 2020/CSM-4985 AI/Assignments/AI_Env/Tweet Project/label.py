import numpy as np 
import pandas as pd

all_data = pd.read_csv("Tweet Project\\textToLabel2.csv")
del all_data['index']
all_data = all_data.dropna()
# all_data.reset_index(inplace=True)
print(all_data['label'].value_counts())


all_data.to_csv('Tweet Project\\textLabeled.csv', encoding='utf-8')