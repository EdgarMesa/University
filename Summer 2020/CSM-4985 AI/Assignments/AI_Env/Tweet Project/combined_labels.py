import pandas as pd
import numpy as np



pd.set_option('display.max_columns', None)
frame = pd.read_excel("Tweet Project\\Data\\Labled data.xls")

frame.drop(["Unnamed: 9", "Unnamed: 10", "Unnamed: 11", "Unnamed: 12", "Unnamed: 13", "Unnamed: 14"], axis=1, inplace=True)
frame = frame.rename(columns={"label":"label 0"})
frame.columns = frame.columns.str.replace(".", " ")
frame["label 4"] = frame["label 4"].replace(np.nan, 1)


frame = frame.astype({"label 4": "int"})

# print(frame)

labels = frame[["label 0", "label 1", "label 2", "label 3", "label 4", "label 5", "label 6", "label 7"]].copy()
drop_rows = []
sumation = []
for index, row in labels.iterrows():
    values_per_row = row.value_counts()
    if len(np.argwhere(values_per_row.values == np.amax(values_per_row.values))) > 1:
        drop_rows.append(index)
    else:
        max_value = values_per_row.index[np.argmax(values_per_row.values)]
        sumation.append(max_value)


frame.drop(drop_rows, inplace=True, axis=0)
newD = {"text":frame["text"], "label":sumation}
newFrame = pd.DataFrame(newD)
print(newFrame["label"].value_counts())
newFrame.to_csv("Tweet Project\\Data\\Final_Test_Data.csv", encoding='utf-8', index=False)



