package ntpc.ccai.clubmgt.bean;

import java.util.ArrayList;

public class SheetGenerater {
	public String sheetName = "";
    public ArrayList<String> sheetDataList= new ArrayList<String>(); 
    public int firstRow = 0;
    public int lastRow  = 0;
    public int firstCol = 0;
    public int lastCol  = 0;
    public String tooltipStr = "";
    
    public SheetGenerater() {
        
    }
    
    /**
     * 下拉選單
     * @param sheetName
     * @param sheetDataList
     * @param firstRow
     * @param lastRow
     * @param firstCol
     * @param lastCol
     */
    public SheetGenerater(String sheetName,ArrayList<String> sheetDataList,int firstRow,int lastRow,int firstCol,int lastCol) {
        
        setSheetName(sheetName);
        setSheetDataList(sheetDataList);
        setFirstRow(firstRow);
        setLastRow(lastRow);
        setFirstCol(firstCol);
        setLastCol(lastCol);
    }
    
    /**
     * 提示框
     * @param sheetName
     * @param sheetDataList
     * @param firstRow
     * @param lastRow
     * @param firstCol
     * @param lastCol
     */
    public SheetGenerater(String sheetName,String tooltipStr,int firstRow,int lastRow,int firstCol,int lastCol) {
        setSheetName(sheetName);
        setTooltipStr(tooltipStr);
        setFirstRow(firstRow);
        setLastRow(lastRow);
        setFirstCol(firstCol);
        setLastCol(lastCol);
    }
    
    /**
     * setter & getter
     */
    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }
    public String getSheetName() {
        return sheetName;
    }
    
    public void setSheetDataList(ArrayList<String> sheetDataList) {
        this.sheetDataList = sheetDataList;
    }
    public ArrayList<String> getSheetDataList() {
        return sheetDataList;
    }
    
    public void setTooltipStr(String tooltipStr) {
        this.tooltipStr=tooltipStr;
    }
    public String getTooltipStr() {
        return tooltipStr;
    }
    
    public void setFirstRow(int firstRow) {
        this.firstRow=firstRow;
    }
    public int getFirstRow() {
        return firstRow;
    }
    
    public void setLastRow(int lastRow) {
        this.lastRow=lastRow;
    }
    public int getLastRow() {
        return lastRow;
    }
    
    public void setFirstCol(int firstCol) {
        this.firstCol=firstCol;
    }
    public int getFirstCol() {
        return firstCol;
    }
    
    public void setLastCol(int lastCol) {
        this.lastCol=lastCol;
    }
    public int getLastCol() {
        return lastCol;
    }
}
