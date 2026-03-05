# -*- coding: utf-8 -*-
import os

base = r'E:\kingdee_webapi\md'
os.chdir(base)

# 扫描所有md文件
files = [f for f in os.listdir('.') if f.endswith('.md')]

with open('filelist2.txt', 'w', encoding='utf-8') as f:
    for filename in files:
        f.write(f'{repr(filename)}\n')

print("Scanned!")
