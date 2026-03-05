# -*- coding: utf-8 -*-
import os
import shutil

def rename_files():
    base_path = r'E:\kingdee_webapi\md'

    # 文件名映射表
    rename_map = {
        # 正确的文件名
        '应收款汇总表.md': None,  # 保持不变
        '应付款汇总表.md': None,  # 保持不变
        '多账簿日报表.md': None,  # 保持不变
        '采购资金计划报表.md': None,  # 保持不变
        '科目余\xe5\xbd\x93.md': '科目余额.md',
        '销售出库明细表.md': None,  # 保持不变
        '销售订单执行明细表.md': None,  # 保持不变

        # 乱码文件名检测（根据之前看到的内容）
        '\u62a5\u8863\u7c3f.md': '日报表.md',
        '\u73b0\u91d1\u65e5\u62a5\u7c3f.md': '现金日报表.md',
        '\u751f\u4ea7\u5165\u5e93\u6708\u62a5\u7c3f.md': '生产入库月报表.md',
        '\u9500\u552e\u51fa\u5e93\u6c47\u603b\u62a5\u7c3f.md': '销售出库汇总报表.md',
        '\u91c7\u8d2d\u8ba2\u5355\u6267\u884c\u660e\u7ec6\u8868.md': '采购订单执行明细表.md',
        '\u751f\u4ea7\u8ba2\u5355\u6267\u884c\u660e\u7ec6\u8868.md': '生产订单执行明细表.md',
        '\u59d4\u5916\u8ba2\u5355\u6267\u884c\u660e\u7ec6\u8868.md': '委外订单执行明细表.md',
    }

    # 遍历md文件
    for filename in os.listdir(base_path):
        if not filename.endswith('.md'):
            continue

        full_path = os.path.join(base_path, filename)

        # 如果文件名已经在映射表中
        if filename in rename_map:
            new_name = rename_map[filename]
            if new_name:
                new_path = os.path.join(base_path, new_name)
                print(f"重命名: {filename} -> {new_name}")
                os.rename(full_path, new_path)
        else:
            print(f"保持不变: {filename}")

if __name__ == '__main__':
    rename_files()
    print("\n完成！")
