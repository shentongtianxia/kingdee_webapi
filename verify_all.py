# -*- coding: utf-8 -*-
import os

base = r'E:\kingdee_webapi\md'
os.chdir(base)

# 所有正确的md文件名
correct_names = {
    '应收款汇总表.md',
    '应付款汇总表.md',
    '科目余额.md',
    '多账簿日报表.md',
    '采购资金计划报表.md',
    '销售出库明细表.md',
    '销售出库汇总报表.md',
    '销售订单执行明细表.md',
    '采购订单执行明细表.md',
    '生产订单执行明细表.md',
    '生产入库月报表.md',
    '委外订单执行明细表.md',
    '日报表.md',
    '现金日报表.md',
}

# 获取当前所有md文件
current_files = set()
for entry in os.scandir('.'):
    if entry.name.endswith('.md'):
        current_files.add(entry.name)

print("=== 当前正确的md文件 ===")
for name in sorted(correct_names):
    if name in current_files:
        print(f"[OK] {name}")
    else:
        print(f"[MISSING] {name}")

print(f"\n总共: {len(correct_names)} 个文件")
print(f"已存在: {len(current_files & correct_names)} 个文件")
print(f"缺失: {len(correct_names - current_files)} 个文件")

# 列出所有当前文件
print("\n=== 所有当前md文件 ===")
for entry in sorted(os.scandir('.'), key=lambda x: x.name):
    if entry.name.endswith('.md'):
        print(f"- {entry.name}")
