import numpy as np
import pandas as pd
import xlwt

#########################Company Name#########################
#导入企业名称的Ocr输出文件
lines=open("ocr_company_name_output.txt")

#寻找企业名称
no_id,company_id=0,0
company=[]
for line in lines:
    if line[-5:-1]==".jpg":
        no_id=no_id+1
        if no_id == company_id+2:
            company_id=company_id+1
            company.append("Too Difficult")

    if(line[0:4]=="企业名称"):
        company_id = company_id + 1
        if company_id == no_id:
            company.append(line[7:])
if no_id!= company_id:
    company.append('Too Difficult')
for i in range(0,no_id):
    print(i+1,company[i])
#将数据保存到excel表格之中
book = xlwt.Workbook(encoding='utf-8',style_compression=0)#创建工作簿
sheet = book.add_sheet('sheet1',cell_overwrite_ok=True)#创建sheet
sheet.write(0,0,"企业序号")
sheet.write(0,1,"企业名称")
for i in range(0,no_id):
    sheet.write(i+1,0,i+1)
    sheet.write(i+1,1,company[i])

#########################Company Registration Number#########################
#导入企业注册号的Ocr输出文件
lines=open("ocr_company_registration_number_output.txt")

#寻找企业注册号
no_id,registration_id=0,0
registration=[]
for line in lines:
    if line[-5:-1]==".png":
        no_id=no_id+1
        if no_id == registration_id+2:
            registration_id=registration_id+1
            registration.append("Too Difficult")

    if(line[0:5]=="企业注册号"):
        registration_id = registration_id + 1
        if registration_id == no_id:
            registration.append(line[7:])
if no_id!= registration_id:
    registration.append('Too Difficult')
for i in range(0,no_id):
    print(i+1,registration[i])
#将数据保存到excel表格之中
sheet.write(0,2,"企业注册号")
for i in range(0,no_id):
    sheet.write(i+1,2,registration[i])
book.save("ocr_output.xls")

