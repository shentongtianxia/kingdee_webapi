# -*- coding: utf-8 -*-
import os

base = r'E:\kingdee_webapi\md'
os.chdir(base)

# 文件名映射
rename_map = {
    '应付款汇总表.md': None,  # 正确，保持
    '应收款汇总表.md': None,  # 正确，保持
    '澶氳处绨挎棩鎶ヨ〃.md': '多账簿日报表.md',
    '绉戠洰浣欓\x01\x82.md': '科目余额.md',
    '閲囪喘璧勯噾璁″垝鎶ヨ〃.md': '采购资金计划报表.md',
    '閿€鍞\x01\xbc嚭搴撴槑缁嗚〃.md': '销售出库明细表.md',
    '閿€鍞\x01\xbf\x01\x79鍗曟墽琛屾槑缁嗚〃.md': '销售订单执行明细表.md',
}

log = []

# 遍历并重命名
for old_name in os.listdir('.'):
    if not old_name.endswith('.md'):
        continue

    if old_name in rename_map:
        new_name = rename_map[old_name]
        if new_name:
            old_path = os.path.join(base, old_name)
            new_path = os.path.join(base, new_name)
            log.append(f"RENAME: {repr(old_name)} -> {new_name}")
            os.rename(old_path, new_path)
        else:
            log.append(f"OK: {old_name}")
    else:
        log.append(f"OK: {old_name}")

# 写入日志文件
with open('rename_log.txt', 'w', encoding='utf-8') as f:
    f.write('\n'.join(log))

print("Done! Check rename_log.txt for details.")
