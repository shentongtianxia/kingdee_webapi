# -*- coding: utf-8 -*-
import os

base = r'E:\kingdee_webapi\md'
os.chdir(base)

# 目标文件名列表
target_names = [
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
]

# 获取所有当前md文件
current_files = []
for entry in os.scandir('.'):
    if entry.name.endswith('.md'):
        current_files.append(entry.name)

print(f"找到 {len(current_files)} 个md文件\n")

# 尝试将UTF-8编码的目标名称与当前文件名匹配
for target in target_names:
    # 获取目标名称的字节表示
    target_bytes = target.encode('utf-8')
    target_chars = set(target[:4])  # 取前4个字符用于匹配

    # 在当前文件中查找
    found = False
    for current in current_files:
        try:
            # 尝试用UTF-8解码当前文件名
            decoded = current.encode('latin1').decode('utf-8')
            # 检查是否匹配目标名称
            if target in decoded or decoded in target:
                print(f"{decoded:30s} -> {target}")
                found = True
                break
        except:
            pass

    if not found:
        # 直接匹配前几个字符
        for current in current_files:
            try:
                # 检查前几个字符是否匹配
                if all(char in current for char in target[:3]):
                    print(f"{current:30s} -> {target}")
                    found = True
                    break
            except:
                pass

    if not found:
        print(f"未找到: {target}")
