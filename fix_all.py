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

# 临时文件列表
temp_files = {
    'filelist.txt',
    'filelist2.txt',
    'filelist3.txt',
    'rename_final.txt',
    'rename_log.txt',
    'rename_result.txt',
    'all_files.txt',
}

# 乱码文件名到正确名称的映射
rename_map = {
    '濮斿璁╂枃浠剁粏琛.md': '委外订单执行明细表.md',
    '鏃ユ姤琛md': '日报表.md',  # 注意这个扩展名不对
    '鐜伴噾鏃ユ姤琛md': '现金日报表.md',
    '鐢熶骇璁╂枃浠剁粏琛.md': '生产订单执行明细表.md',
    '鐢熶骇鍏ュ簱鏈堟姤琛.md': '生产入库月报表.md',
    '閲囪喘璁╂枃浠剁粏琛.md': '采购订单执行明细表.md',
    '閿€鍞嚭搴撴眹鎬绘姤琛.md': '销售出库汇总报表.md',
}

log = []
renamed_count = 0
deleted_count = 0

# 首先处理临时文件
log.append("=== 清理临时文件 ===")
for temp in temp_files:
    if os.path.exists(temp):
        os.remove(temp)
        log.append(f"DEL: {temp}")
        deleted_count += 1

# 然后重命名乱码文件
log.append("\n=== 重命名乱码文件 ===")
for old_name, new_name in rename_map.items():
    if os.path.exists(old_name):
        # 检查扩展名
        if not old_name.endswith('.md'):
            # 修正扩展名
            base_name = old_name.rstrip('md')
            old_name = base_name + '.md'

        if os.path.exists(old_name):
            old_path = os.path.join(base, old_name)
            new_path = os.path.join(base, new_name)
            log.append(f"RENAME: {repr(old_name)} -> {new_name}")
            os.rename(old_path, new_path)
            renamed_count += 1

# 验证结果
log.append("\n=== 当前md文件列表 ===")
current_md = []
for entry in sorted(os.scandir('.'), key=lambda x: x.name):
    if entry.name.endswith('.md'):
        current_md.append(entry.name)
        status = "OK" if entry.name in correct_names else "PROBLEM"
        log.append(f"{status}: {entry.name}")

# 检查缺失
log.append("\n=== 缺失检查 ===")
found = set(current_md)
missing = correct_names - found
if missing:
    log.append(f"缺失文件 ({len(missing)}):")
    for m in sorted(missing):
        log.append(f"  - {m}")
else:
    log.append("所有正确文件都存在！")

# 写入日志
with open('rename_complete.txt', 'w', encoding='utf-8') as f:
    f.write('\n'.join(log))

print(f"完成! 重命名: {renamed_count}, 删除: {deleted_count}")
