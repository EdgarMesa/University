import pandas as pd 
import numpy as np
import os, os.path
from os import listdir
from langdetect import detect
pd.set_option('display.max_columns', 22)

# Path to get the files we want.
dataFolder = os.getcwd() + "Tweet Project\\\\Data"

# All the cvs.files
fileNames = [ "Tweet Project\\Data\\" + f for f in listdir(dataFolder) if os.path.isfile(os.path.join(dataFolder, f))]

FRAMES = []

i = 0

for frame in fileNames:

    frame = pd.read_csv(frame)
    nulls = frame.isnull().sum(axis = 0)
    data = frame[['user_id', 'country_code', 'place_full_name','text', 'source', 'is_quote', 'favourites_count', 'retweet_count', 'followers_count', 'friends_count', 'verified']].dropna()


    data['place_full_name'] = [x.split(',')[-1].strip() for x in data['place_full_name']]

    data = data[data.country_code == "US"]
    #data = data[data.place_full_name == "IL"  or data.place_full_name == "CA"]


    data['is_quote'] = np.where(data['is_quote'] == False, 0, 1)
    data['verified'] = np.where(data['verified'] == False, 0, 1)

    # print((data['favourites_count'] <= 1000).sum())
    # print(((data['favourites_count'] > 1000) & (data['favourites_count'] <= 10000)).sum())
    # print(((data['favourites_count'] > 10000) & (data['favourites_count'] <= 100000)).sum())
    # print((data['favourites_count'] > 100000).sum())


    data.loc[data['retweet_count'] <= 80 , 'retweet_count'] = 0
    data.loc[(data['retweet_count'] > 80) & (data['retweet_count'] <= 1000), 'retweet_count'] = 1
    data.loc[data['retweet_count'] > 1000 , 'retweet_count'] = 2



    data.loc[data['favourites_count'] <= 1000, 'favourites_count'] = 0
    data.loc[(data['favourites_count'] > 1000) & (data['favourites_count'] <= 10000), 'favourites_count'] = 1
    data.loc[(data['favourites_count'] > 10000) & (data['favourites_count'] <= 100000), 'favourites_count'] = 2
    data.loc[data['favourites_count'] > 100000, 'favourites_count'] = 3
    indexes = []
    for t in data['text']:
        try:
            
            lang = detect(str(t))
            if t == "es":
                indexes.append(True)
            else:
                indexes.append(False)
            

        except Exception:
             indexes.append(False)
             continue
    indexes = data[indexes].index
    
    data.drop(indexes, inplace = True) 
    print(i)
    i +=1
    FRAMES.append(data)

# states = np.unique(data['place_full_name'].to_numpy())
# states = [x for x in states if len(x) == 2]

# for x in states:
#     print(x, "  ", (data['place_full_name'] == x).sum())


all_data = pd.concat(FRAMES)
all_data.reset_index(inplace=True)
print(all_data)
all_data.to_csv('Tweet Project\\all_data.csv', encoding='utf-8')


# uniques = np.unique(all_data['user_id'])
# for i in uniques:
#     print("user: ",i , (all_data['user_id'] == i).sum())