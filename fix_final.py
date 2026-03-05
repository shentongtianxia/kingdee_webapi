# -*- coding: utf-8 -*-
import os

base = r'E:\kingdee_webapi\md'
os.chdir(base)

# 所有正确的文件名（UTF-8编码）
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

# 手动映射乱码文件名到正确名称
manual_map = {
    '绉戠洰浣欓.md': '科目余额.md',
    '閿€鍞嚭搴撴槑缁嗚〃.md': '销售出库明细表.md',
    '閿€鍞鍗曟墽琛屾槑缁嗚〃.md': '销售订单执行明细表.md',
}

log = []
renamed_count = 0

# 首先应用手动映射
for current_name, correct_name in manual_map.items():
    if os.path.exists(current_name):
        old_path = os.path.join(base, current_name)
        new_path = os.path.join(base, correct_name)
        log.append(f"RENAME: {repr(current_name)} -> {correct_name}")
        os.rename(old_path, new_path)
        renamed_count += 1

# 扫描并验证
log.append("\n=== 当前存在的md文件 ===")
all_correct = True
for entry in sorted(os.scandir('.'), key=lambda x: x.name):
    if entry.name.endswith('.md'):
        if entry.name in correct_names:
            log.append(f"OK: {entry.name}")
        else:
            log.append(f"PROBLEM: {repr(entry.name)}")
            all_correct = False

# 报告缺失的文件
log.append("\n=== 验证结果 ===")
missing = []
for name in correct_names:
    if not os.path.exists(name):
        missing.append(name)

if missing:
    log.append(f"缺失文件 ({len(missing)}):")
    for m in sorted(missing):
        log.append(f"  - {m}")
else:
    log.append("所有文件都已正确命名！")

# 写入日志
with open('rename_final.txt', 'w', encoding='utf-8') as f:
    f.write('\n'.join(log))

print(f"完成! 重命名: {renamed_count}")
