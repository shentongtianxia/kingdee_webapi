# -*- coding: utf-8 -*-
import os

base = r'E:\kingdee_webapi\md'
os.chdir(base)

# 获取所有文件
all_files = []
for entry in os.scandir('.'):
    if entry.is_file():
        all_files.append(entry.name)

# 按字节写入文件
with open('all_files.txt', 'wb') as f:
    for filename in sorted(all_files):
        try:
            f.write(filename.encode('utf-8'))
            f.write(b'\n')
        except:
            f.write(repr(filename).encode('utf-8'))
            f.write(b'\n')

print(f"找到 {len(all_files)} 个文件")
