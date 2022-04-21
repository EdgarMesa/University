import tweepy
import csv
import pandas as pd
import sys

# API credentials here
consumer_key = 'mxRmoj9vrdEaiC3Ah6xF0naee'
consumer_secret = 'RgemPWUjOoTEV47cim0IafbHizn0rV2TqmSOZ0HunMgogsw6lG'
access_token = '1060414506-NJtO2HBVuRMENkwvEzyBKjcXnV14vfQDsbBegJh'
access_token_secret = 'gehpwmsbP9DkkbqxClLrPugpsxMP4oqtYhkr477QktlxO'

auth = tweepy.OAuthHandler(consumer_key, consumer_secret)
auth.set_access_token(access_token, access_token_secret)
api = tweepy.API(auth,wait_on_rate_limit=True,wait_on_rate_limit_notify=True)

# # Search word/hashtag value 
HashValues = ["MyFaceIsNotAPrivatePart", "NoMask", "EndTheLockdown ", "NoMaskOnMe", "NoVaccine"]

# search start date value. the search will start from this date to the current date.
# StartDate = "2020-4-01"
# EndDate = "2020-12-10"




# Open/Create a file to append data
# csvFile = open('Tweet Project\\Data\\BadTweets.csv', 'a')

#Use csv Writer
# csvWriter = csv.writer(csvFile)
# csvWriter.writerow(["label", "text"])

# i = 0
# for HashValue in HashValues:


#     for tweet in tweepy.Cursor(api.search,q=HashValue,count=50,lang="en",since=StartDate,until=EndDate, tweet_mode='extended').items():
#         csvWriter.writerow([1, tweet.full_text.encode('utf-8')])

#     print(f'Done with hashtag {HashValues[i]}')    
#     i += 1


# print ('Scraping finished')


# HashValues = ["StayHome", "WearAMask", "StaySafe", "SocialDistancing", "MaskUp"]

# StartDate = "2020-11-01"
# EndDate = "2020-12-01"



# csvFile = open('Tweet Project\\Data\\GoodTweets.csv', 'a')

# csvWriter = csv.writer(csvFile)
# csvWriter.writerow(["label", "text"])

# i = 0
# for HashValue in HashValues:


#     for tweet in tweepy.Cursor(api.search,q=HashValue,count=30,lang="en",since=StartDate, until=EndDate, tweet_mode='extended').items():
#         csvWriter.writerow([1, tweet.full_text.encode('utf-8')])
#     print(f'Done with hashtag {HashValues[i]}')    
#     i += 1


# print ('Scraping finished')





HashValues = ["Famous"]
#"Covid19", "Sports", "Economy", "Random", "Fashion", "VideoGame", "Movie", "Cinema" 

StartDate = "2020-12-01"
EndDate = "2020-12-27"



csvFile = open('Tweet Project\\Data\\RandomTweets.csv', 'a')

csvWriter = csv.writer(csvFile)
# csvWriter.writerow(["label", "text"])

i = 0
for HashValue in HashValues:


    for tweet in tweepy.Cursor(api.search,q=HashValue,count=30,lang="en",since=StartDate, until=EndDate, tweet_mode='extended').items():
        csvWriter.writerow([1, tweet.full_text.encode('utf-8')])
    print(f'Done with hashtag {HashValues[i]}')    
    i += 1


print ('Scraping finished')