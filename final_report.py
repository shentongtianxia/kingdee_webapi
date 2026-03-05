# -*- coding: utf-8 -*-
import os

base = r'E:\kingdee_webapi\md'
os.chdir(base)

# 所有正确的md文件名（14个）
all_correct_names = {
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

# 写入报告文件
report = []
report.append("=" * 60)
report.append("文件名修复结果报告")
report.append("=" * 60)
report.append("")
report.append("[OK] 已正确命名的文件 (10个):")
report.append("-" * 40)
for name in sorted(current_files & all_correct_names):
    report.append(f"  OK: {name}")
report.append("")
report.append("[MISSING] 丢失的文件 (4个):")
report.append("-" * 40)
missing = all_correct_names - current_files
for name in sorted(missing):
    report.append(f"  MISSING: {name}")
report.append("")
report.append(f"[STATS] 统计:")
report.append(f"  - 应该存在: {len(all_correct_names)} 个")
report.append(f"  - 已修复: {len(current_files & all_correct_names)} 个")
report.append(f"  - 丢失: {len(missing)} 个")
report.append(f"  - 成功率: {len(current_files & all_correct_names) / len(all_correct_names) * 100:.1f}%")
report.append("")
report.append("=" * 60)
report.append("说明: 丢失的文件可能是重命名过程中被误删")
report.append("      如需恢复，请从备份或git历史中获取")
report.append("=" * 60)
# 写入文件
with open('FILENAME_FIX_REPORT.txt', 'w', encoding='utf-8') as f:
    f.write('\n'.join(report))
print("报告已写入 FILENAME_FIX_REPORT.txt")
