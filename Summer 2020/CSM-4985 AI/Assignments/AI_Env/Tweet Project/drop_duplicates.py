import pandas as pd
import numpy as np

badTweetsName = "Tweet Project\\BadTweets.csv"
data = pd.read_csv(badTweetsName)
print("Shape bad tweets before: ", data.shape)

# dropping ALL duplicte values 
# data.drop_duplicates(subset ="text", 
#                      keep = False, inplace = True)

# print("Shape bad tweets after: ",data.shape)

# data.to_csv(badTweetsName, encoding='utf-8', index=False)


goodTweetsName = "Tweet Project\\GoodTweets.csv"
data2 = pd.read_csv(goodTweetsName)
print("Shape good tweets before: ", data2.shape)

# dropping ALL duplicte values 
# data2.drop_duplicates(subset ="text", 
#                      keep = False, inplace = True)

# print("Shape good tweets after: ",data2.shape)

# data2.to_csv(goodTweetsName, encoding='utf-8', index=False)





randomTweetsName = "Tweet Project\\RandomTweets.csv"
data3 = pd.read_csv(randomTweetsName)
print("Shape random tweets before: ", data3.shape)

# dropping ALL duplicte values 
# data3.drop_duplicates(subset ="text", 
#                      keep = False, inplace = True)

# print("Shape random tweets after: ",data3.shape)

# data3.to_csv(randomTweetsName, encoding='utf-8', index=False)



