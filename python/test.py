import matplotlib.pyplot as plt
import matplotlib.image as mpimg
import numpy as np
from PIL import Image
import os

path='/Users/zhenhai/Downloads/SoftwareCup/Tess4J/test/resources/tianmao1'
files=os.listdir(path=path)
num=0
sumwidth,sumheight=0,0
for file in files:
    #print(file)
    if file[-1]=='g':
        img=Image.open('/Users/zhenhai/Downloads/SoftwareCup/Tess4J/test/resources/tianmao1/'+file)
        print(num,img.size)#打印图片大小
        sumwidth+=img.size[0]
        sumheight+=img.size[1]
    num=num+1
averagewidth,averageheight=sumwidth/50,sumheight/50
print(averagewidth,averageheight)
