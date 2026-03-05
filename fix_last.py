# -*- coding: utf-8 -*-
import os

base = r'E:\kingdee_webapi\md'
os.chdir(base)

# 需要修正扩展名的文件
fix_list = [
    ('委外订单执行明细.md', '委外订单执行明细表.md'),
    ('生产订单执行明细.md', '生产订单执行明细表.md'),
    ('采购订单执行明细.md', '采购订单执行明细表.md'),
]

log = []

for old_name, new_name in fix_list:
    if os.path.exists(old_name):
        old_path = os.path.join(base, old_name)
        new_path = os.path.join(base, new_name)

        # 如果目标已存在，先删除
        if os.path.exists(new_path):
            log.append(f"删除重复: {new_name}")
            os.remove(new_path)

        log.append(f"修正扩展名: {old_name} -> {new_name}")
        os.rename(old_path, new_path)
    else:
        log.append(f"未找到: {old_name}")

# 清理临时文件
temp_files = ['current_files_bytes.txt', 'rename_final_result.txt']
for temp in temp_files:
    if os.path.exists(temp):
        os.remove(temp)
        log.append(f"删除临时: {temp}")

# 验证结果
log.append("\n=== 最终md文件列表 ===")
count = 0
for entry in sorted(os.scandir('.'), key=lambda x: x.name):
    if entry.name.endswith('.md'):
        log.append(f"- {entry.name}")
        count += 1

log.append(f"\n总共 {count} 个md文件")

# 写入日志
with open('rename_complete.log', 'w', encoding='utf-8') as f:
    f.write('\n'.join(log))

print(f"完成! 查看 rename_complete.log 获取详情")
