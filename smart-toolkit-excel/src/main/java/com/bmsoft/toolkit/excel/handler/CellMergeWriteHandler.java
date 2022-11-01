package com.bmsoft.toolkit.excel.handler;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.List;

/**
 * excel导出数据内容单元格合并规则
 *
 * @Author shenghao.xiao
 * @Date 2020/9/17
 **/
public class CellMergeWriteHandler implements CellWriteHandler {
    /**
     * 合并列的范围索引
     */
    private int[] mergeColumnIndex;
    /**
     * 合并起始行索引
     */
    private int mergeRowIndex;

    public CellMergeWriteHandler(int[] mergeColumnIndex, int mergeRowIndex) {
        this.mergeColumnIndex = mergeColumnIndex;
        this.mergeRowIndex = mergeRowIndex;
    }


    @Override
    public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder,
                                 List<WriteCellData<?>> cellDataList, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
        // 当前行
        int curRowIndex = cell.getRowIndex();
        // 当前列
        int curColIndex = cell.getColumnIndex();
        if (curColIndex >= mergeRowIndex) {
            for (int columnIndex : mergeColumnIndex) {
                if (curColIndex == columnIndex) {
                    mergeWithPrevRow(writeSheetHolder, cell, curRowIndex, curColIndex);
                    break;
                }
            }
        }
    }

    private void mergeWithPrevRow(WriteSheetHolder writeSheetHolder, Cell cell, int curRowIndex, int curColIndex) {
        // 第一行数据不用合并
        if (curRowIndex == 0) {
            return;
        }
        // 获取当前行的当前列的数据和上一行的当前列数据，通过上一行数据是否相同进行合并
        Object curData = cell.getCellType() == CellType.STRING ? cell.getStringCellValue() : cell.getNumericCellValue();
        Cell preCell = cell.getSheet().getRow(curRowIndex - 1).getCell(curColIndex);
        Object preData = preCell.getCellType() == CellType.STRING ? preCell.getStringCellValue() : preCell.getNumericCellValue();

        // 比较当前行的第一列的单元格与上一行是否相同，相同合并当前单元格与上一行
        if (curData.equals(preData)) {
            Sheet sheet = writeSheetHolder.getSheet();
            List<CellRangeAddress> mergedRegions = sheet.getMergedRegions();
            boolean isMerged = false;
            for (int i = 0; i < mergedRegions.size() && !isMerged; i++) {
                CellRangeAddress cellAddresses = mergedRegions.get(i);
                //若上 一个单元格已经被合并，则先移出原有的合并单元，再重新添加合并单元
                if (cellAddresses.isInRange(curRowIndex - 1, curColIndex)) {
                    sheet.removeMergedRegion(i);
                    cellAddresses.setLastRow(curRowIndex);
                    sheet.addMergedRegion(cellAddresses);
                    isMerged = true;
                }
            }
            // 若上一个单元格未被合并，则新增合并单元
            if (!isMerged) {
                CellRangeAddress cellAddresses = new CellRangeAddress(curRowIndex - 1, curRowIndex, curColIndex, curColIndex);
                sheet.addMergedRegion(cellAddresses);
            }
        }
    }
}
