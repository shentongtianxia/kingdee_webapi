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

# 扫描当前所有md文件
current_files = set()
for entry in os.scandir('.'):
    if entry.name.endswith('.md'):
        current_files.add(entry.name)

log = []
renamed_count = 0
already_correct = 0

for correct_name in correct_names:
    if correct_name not in current_files:
        # 文件不存在，需要查找乱码版本
        found = False
        for current_name in current_files:
            # 尝试匹配（忽略编码问题）
            try:
                # 检查文件名是否包含相同的汉字
                if all(char in current_name for char in correct_name[:4]):
                    old_path = os.path.join(base, current_name)
                    new_path = os.path.join(base, correct_name)
                    log.append(f"RECOVER: {repr(current_name)} -> {correct_name}")
                    os.rename(old_path, new_path)
                    current_files.remove(current_name)
                    renamed_count += 1
                    found = True
                    break
            except:
                pass

        if not found:
            log.append(f"MISSING: {correct_name}")
    else:
        already_correct += 1
        log.append(f"OK: {correct_name}")

# 报告当前存在的文件
log.append("\n=== 当前存在的md文件 ===")
for entry in sorted(os.scandir('.'), key=lambda x: x.name):
    if entry.name.endswith('.md'):
        log.append(f"- {entry.name}")

# 写入日志
with open('rename_result.txt', 'w', encoding='utf-8') as f:
    f.write('\n'.join(log))

print(f"完成! 正确文件: {already_correct}, 恢复文件: {renamed_count}")
