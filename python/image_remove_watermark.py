import matplotlib.pyplot as plt
import matplotlib.image as mpimg
import numpy as np
from PIL import Image
import os

path='input'
files=os.listdir(path=path)

for file in files:
    #print(file)
    if file[-1]=='g':
        img=Image.open('input/'+file)
        #print(img.size)#打印图片大小
        width=img.size[0]
        height=img.size[1]
        for i in range(1, width):  # 遍历所有长度的点
            for j in range(1, height):  # 遍历所有宽度的点
                data = (img.getpixel((i, j)))  # 打印该图片的所有点
                # print (data)#打印每个像素点的颜色RGBA的值(r,g,b,alpha)
                print (data[0])#打印RGBA的r值
                if (data[0] >= 200):  # RGBA的r值大于170，并且g值大于170,并且b值大于170
                    img.putpixel((i, j), (255, 255, 255, 0))  # 则这些像素点的颜色改成白色
        img.save("output/"+file)  # 保存修改像素点后的图片