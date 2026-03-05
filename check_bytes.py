# -*- coding: utf-8 -*-
import os

base = r'E:\kingdee_webapi\md'
os.chdir(base)

# 获取所有当前md文件
current_files = []
for entry in os.scandir('.'):
    if entry.name.endswith('.md'):
        current_files.append(entry.name)

# 按字节写入文件
with open('current_files_bytes.txt', 'wb') as f:
    for current in sorted(current_files):
        f.write(f"=== {repr(current)} ===\n".encode('utf-8'))
        f.write(f"原始: {current}\n".encode('utf-8'))
        f.write(f"UTF-8 bytes: {current.encode('utf-8')}\n".encode('utf-8'))
        f.write(b"\n")

print("已写入current_files_bytes.txt")
