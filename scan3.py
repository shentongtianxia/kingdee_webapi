# -*- coding: utf-8 -*-
import os

base = r'E:\kingdee_webapi\md'
os.chdir(base)

# 使用os.scandir获取所有文件
files = []
for entry in os.scandir('.'):
    if entry.name.endswith('.md'):
        files.append(entry.name)

# 按字节写入文件
with open('filelist3.txt', 'wb') as f:
    for filename in files:
        f.write(filename.encode('utf-8'))
        f.write(b'\n')

print("Scanned all files!")
