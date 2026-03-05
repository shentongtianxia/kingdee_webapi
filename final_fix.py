# -*- coding: utf-8 -*-
import os

base = r'E:\kingdee_webapi\md'
os.chdir(base)

log = []
renamed_count = 0

# 修正扩展名错误的文件
fix_ext_map = {
    '濮斿璁╂枃浠剁粏琛md': '委外订单执行明细表.md',
    '鐢熶骇璁╂枃浠剁粏琛md': '生产订单执行明细表.md',
    '閲囪喘璁╂枃浠剁粏琛md': '采购订单执行明细表.md',
}

# 清理临时文件
temp_files = {'all_files.txt', 'rename_done.txt'}

log.append("=== 最终修复 ===")

# 首先修正扩展名
for old_name, new_name in fix_ext_map.items():
    if os.path.exists(old_name):
        old_path = os.path.join(base, old_name)
        new_path = os.path.join(base, new_name)
        # 如果目标文件已存在，先删除
        if os.path.exists(new_path):
            log.append(f"DEL DUPLICATE: {new_name}")
            os.remove(new_path)
        log.append(f"FIX: {old_name} -> {new_name}")
        os.rename(old_path, new_path)
        renamed_count += 1

# 删除临时文件
for temp in temp_files:
    if os.path.exists(temp):
        os.remove(temp)
        log.append(f"DEL: {temp}")

# 验证最终结果
log.append("\n=== 最终md文件列表 ===")
all_correct = True
for entry in sorted(os.scandir('.'), key=lambda x: x.name):
    if entry.name.endswith('.md'):
        log.append(f"- {entry.name}")

# 写入日志
with open('rename_final_result.txt', 'w', encoding='utf-8') as f:
    f.write('\n'.join(log))

print(f"完成! 修复: {renamed_count}")
