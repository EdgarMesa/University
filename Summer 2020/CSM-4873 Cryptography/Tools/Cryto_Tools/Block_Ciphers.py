import numpy as np
from PIL import Image
def hex_to_rgb(value):
    lv = len(value)
    return [int(value[i:i + lv // 4], 16) for i in range(0, lv, lv // 4)]
if __name__ == "__main__":

    with open('hex_data') as file:
        data = file.read()
    hex_data = data.split(sep=" ")
    hex_data = [hex_data[i:i+4] for i in range(0, len(hex_data), 4)]
    decimal_dat = []
    for hex in hex_data:
        together = "".join(hex)
        num = hex_to_rgb(together)
        num = np.array(num, dtype=np.uint8)
        num[3] = 255
        decimal_dat.append(num)

    num = np.array(decimal_dat, dtype=np.uint8)

    decimal_dat = [num[i:i+816] for i in range(0, len(num), 816)]
    decimal_dat = np.array(decimal_dat[:-1], dtype=np.uint8)
    print("Shape: ", decimal_dat.shape)
    img = Image.fromarray(decimal_dat, 'RGBA')
    img.save('Flag.png')
    img.show()


    #hex_data = [(int(x, 16)) for x in hex_data]
    #hex_data = [hex_data[i:i+4] for i in range(0, len(hex_data), 4)]
    #print(len(hex_data))
    #hex_data = [hex_data[i:i+184] for i in range(0, len(hex_data), 184)]





