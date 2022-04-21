import pandas as pd 
import numpy as np

all_data = pd.read_csv("Tweet Project\\all_data.csv")
del all_data['index']
# copyFrame = all_data.copy()
# copyFrame = copyFrame.drop_duplicates(subset=['user_id'])

# uniqueIds = np.unique(all_data['user_id'])



# for id in uniqueIds:
#     copyFrame.loc[copyFrame['user_id'] == id, 'text'] = " || ".join([str(x).replace("\n", "") for x in all_data.loc[all_data['user_id'] == id, 'text']])

# copyFrame.to_csv('Tweet Project\\all_data_filtered.csv', encoding='utf-8')
all_data['text'] = [str(x).replace("\n", "") for x in all_data['text']]
dataText = all_data[['user_id', 'text']]
# print(dataText)
dataText.to_csv('Tweet Project\\textToLabel2.csv', encoding='utf-8')

# copyFrame = pd.read_csv("Tweet Project\\textToLabel.csv", encoding='utf-8')

# for c in copyFrame['text']:
#     print(c)

