package ntpc.ccai.clubmgt.bean;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfWriter;

/**
 * 社團管理 : 報表系統 
 * 社團學生證明書
 * Tom
 * 創立日期 : 2018/04/30
 * */

public class ClubExportPDF11 {


	private Document document = null;
    /*檔案參數*/
    //private String fontstyle = "D:\\resin\\webapps\\CurrProgramSystem\\font\\kaiu.ttf";
    private String fontstyle = "";
    private String print_page ="";//頁碼
    private String print_date  = ""; // 列印日期
    private String title="";//title
    private String titleschool = "";
    private String fontnumber = ""; // 發文字號
    
    /*字型設定*/
    private Font f_title   = null;//表格title:國立臺北科技大學 xxx系xx制課程科目表
    private Font f_title2  = null;//聘書用標題
    private Font f_date    = null;//表格列印日期
    private Font f_col     = null;//表頭欄位
    private Font f_col2     = null;//欄位2
    private Font f_col3     = null;//聘書欄位
    private Font f_small   = null;//特小的字
    private Font f_content = null;//資料內容

  //建立不同系所之頁碼陣列集合{5,1,8}表示共有三組系所組，總頁數分
    public int number=1;
    public int[] countPage = null;
    public void createPageArray(int size){
        this.countPage = new int[size];
    }
    
    public String pagetype = null;//編製頁碼方式
    public void setPageType(String value){this.pagetype= value;}

    private ByteArrayOutputStream buffer = null;
    
    public ByteArrayOutputStream getBuffer(){
        return this.buffer;
    }
    
    private List<ClubSem> cslist = null;
    
 // method 
    public void setPrint_page(String Pvalue) {this.print_page = Pvalue;}//列印主頁碼
    
    //public void setPrint_date(String Pvalue) {this.print_date = (Integer.parseInt(Pvalue.substring(0,4)))-1911 +  Pvalue.substring(4);}//列印日期
    public void setPrint_date(String Pvalue) {this.print_date = Pvalue;}//列印日期
    public void setTitle(String Pvalue)      {this.title       = Pvalue;}//
    public void setFontStyle(String Pvalue)  {this.fontstyle   = Pvalue;};
    public void setTitleschool(String titleschool) {this.titleschool = titleschool;}   
    public void setFontnumber(String fontnumber) {this.fontnumber = fontnumber;}

// open document
public void openDocument(HttpServletResponse response) throws Exception{  

    // step1:建立檔案
   document = new Document(PageSize.A4.rotate(), 20, 20, 1, 7);//建立A4橫向(.rotate)之檔案：左右上下      
   // step2:輸入pdf檔       
   buffer = new ByteArrayOutputStream();  // new
   PdfWriter.getInstance(document,buffer);
  
   // step3:設定中文字體
   BaseFont bfChinese = BaseFont.createFont(fontstyle, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);

   f_title = new Font(bfChinese,18,Font.HELVETICA);
   
   f_title2 = new Font(bfChinese,60,Font.HELVETICA);
 
   f_date  = new Font(bfChinese,12,Font.NORMAL);
   
   f_col   = new Font(bfChinese,10,Font.HELVETICA);
   
   f_col2   = new Font(bfChinese,16,Font.HELVETICA);
   
   f_col3   = new Font(bfChinese,32,Font.NORMAL);
   
   f_small = new Font(bfChinese,8,Font.HELVETICA);  
   
   f_content = new Font(bfChinese,10,Font.NORMAL); 
   /*  聘書無頁尾
   // step:設定頁尾頁尾 
   if (this.pagetype!=null && this.pagetype.equals("s")){
       Phrase phrase_1 = new Phrase("~"+this.print_page+"-",f_date);
       Phrase phrase_2 = new Phrase("~",f_date);
       phrase_1.setLeading(10);
       phrase_2.setLeading(10);
       HeaderFooter footer = new HeaderFooter(phrase_1, phrase_2);
       footer.setAlignment(Element.ALIGN_CENTER);
       footer.setBorder(Rectangle.NO_BORDER);
       //footer.setAlignment(0);
       footer.setTop(0);
       
       document.setFooter(footer); 
   }
   */
   // step4:開啟檔案
   document.open();
}// End of openDocument

/*關閉檔案*/
public void closeDocument()throws Exception{
    document.close();
    if (this.pagetype!=null && this.pagetype.equals("g")){//==g,以系所組區分不同之流水號
        this.addPageNumber();
    }
}// END OF closeDocument()


/** 產生PDF檔案的主程式
 * @return
 * @throws Exception
 */
    public int Generate()throws Exception{
       // CprogramData beanData = new CprogramData();
       // CprogramDataList ec1= new CprogramDataList(); //必修
       // CprogramDataList ec2= new CprogramDataList(); //選修      
        
        // Step 1： Table 
        

        try {
        	setonTable(); //建立聘書資料表
        }
        
           
         catch (DocumentException e) {
            e.printStackTrace();
            return 0;
        }        
        return 1;     
    }

    
    
/*建立儲存格*/
/**
 * pTxt 內容, pFont 字型 ,
 * pHor ,
 * pPadding 儲存格邊界 ,
 * pColor 儲存格 背景顏色 ,
 * pRowspan 上下表格 ,
 * pColspan 左右表格 ,
 * pFixedH ,
 * pTopW  表格邊框粗細(高) ,
 * pBottomW 表格邊框粗細(底) ,
 * pLeftW  表格邊框粗細(左) ,
 * pRightW 表格邊框粗細(右).
 * 
 * **/
private PdfPCell addNewCell(String pTxt, Font pFont, int pHor, int pPadding, Color pColor, int pRowspan, int pColspan, int pFixedH
                            , float pTopW , float pBottomW, float pLeftW ,float pRightW ){
    PdfPCell cell =  null;
    cell = new PdfPCell(new Paragraph(pTxt, pFont));
    cell.setUseAscender(true);//設true，置中的效果才有用
    cell.setHorizontalAlignment(pHor);
    cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
    cell.setPadding(pPadding);
    
    
    cell.setBackgroundColor(pColor);
    cell.setBorderColor(Color.WHITE); //表格內分隔線
    
    cell.setBorderWidthTop(pTopW);
    cell.setBorderWidthBottom(pBottomW);
    cell.setBorderWidthLeft(pLeftW);
    cell.setBorderWidthRight(pRightW);

    if (pRowspan >0) { cell.setRowspan(pRowspan);}
    if (pColspan >0) { cell.setColspan(pColspan);}
    if (pFixedH  >0) { cell.setFixedHeight(pFixedH);}
    //101.12.14 教務處徐宜瑩#1136 要求課程名稱對齊
    cell.setPaddingLeft(3f);   
    
    return cell;
}// END OF addNewCell

/*建立圖片儲存格*/
/**
 * imageURL 圖片連結 ,
 * pHor ,
 * pPadding 儲存格邊界 ,
 * pColor 儲存格 背景顏色 ,
 * pRowspan 上下表格 ,
 * pColspan 左右表格 ,
 * pFixedH ,
 * pTopW  表格邊框粗細(高) ,
 * pBottomW 表格邊框粗細(底) ,
 * pLeftW  表格邊框粗細(左) ,
 * pRightW 表格邊框粗細(右).
 * 
 * **/
private static PdfPCell headPng(String imageURL, int pHor, int pPadding, Color pColor, int pRowspan, int pColspan, int pFixedH
        , float pTopW , float pBottomW, float pLeftW ,float pRightW ) throws IOException, DocumentException {
      
    PdfPCell cell =  null;   
    Image img = Image.getInstance(imageURL);      
    cell = new PdfPCell(img); // 匯入圖片
    //img.scaleAbsolute(5,5);
    cell.setUseAscender(true);//設true，置中的效果才有用
    cell.setHorizontalAlignment(pHor);
    cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
    cell.setPadding(pPadding);
    
    cell.setBackgroundColor(pColor);
    cell.setBorderColor(Color.BLACK); //表格內分隔線
    
    cell.setBorderWidthTop(pTopW);
    cell.setBorderWidthBottom(pBottomW);
    cell.setBorderWidthLeft(pLeftW);
    cell.setBorderWidthRight(pRightW);
    
    
    
    if (pRowspan >0) { cell.setRowspan(pRowspan);}
    if (pColspan >0) { cell.setColspan(pColspan);}
    if (pFixedH  >0) { cell.setFixedHeight(pFixedH);}
    
    
    return cell;
}

/**
 * @param mat_code
 * @param adg_cname
 * @param dep_cname
 */
//設定標頭  "OO高中          107學年度          下學期          「 籃球」         社團成員名單"
public void setHeaderString(String mat_cname, String adg_cname, String dep_cname){
    String header =  this.title+""+adg_cname+""+mat_cname+""+dep_cname;
    this.setTitle(header);
}

/*20120912判斷是否為中間的系統組，true的話要換頁*/
public void newDivPage()throws Exception{ this.document.newPage();}

/** 在文件上加入頁碼
 * @throws Exception
 */
private void addPageNumber() throws Exception{
    try {
        PdfReader readert   = new PdfReader(this.buffer.toByteArray());// Create a reader
        PdfStamper stamper = new PdfStamper(readert,this.buffer); // Create a stamper
        int startPage = 0;
        startPage = Integer.valueOf(this.print_page)-1;
        int pre = 0;//前一組系所組的總頁數
        for (int i = 0; i < this.countPage.length ; i++) {//Loop系所組
            int p = this.countPage[i];//get當前該系所組的總頁數
            
            for (int j = 0; j < p ; j++) {//loop
                int page = pre+(j+1);//真實的頁數=前頁數+(j+1)
                getHeaderTable(i+1+startPage, j+1).writeSelectedRows(//i+1:系所組流水編號;j+1:該系所組下的第幾頁
                    0, -1, 180, 25, stamper.getOverContent(page));   
            }
            pre =pre + p;//set 前一組的頁數
        }
        stamper.close();// Close the stamper
    }catch(Exception ex){
        throw ex;
    }
}//END OF addPageNumber

/** 建置有第x頁共y頁的table
 * @param x
 * @param y
 * @return
 * @throws DocumentException
 */
private PdfPTable getHeaderTable(int x, int y) throws DocumentException {

    PdfPTable table = new PdfPTable(1);
    table.setWidthPercentage(100);
    table.setLockedWidth(true);
    table.setTotalWidth(500);

    table.getDefaultCell().setFixedHeight(20);
    table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
    table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
    Paragraph pp = new Paragraph(String.format("~%d-%d~", x, y),this.f_date);
    
    PdfPCell cellp = new PdfPCell(pp);
    cellp.setBorder(Rectangle.NO_BORDER);
    cellp.setHorizontalAlignment(Element.ALIGN_CENTER);
         
    table.addCell(cellp);
    return table;
}// END OF getHeaderTable


/*建立聘書基本資料表*/
private void setonTable()throws Exception{ 
    //step1:列印標題
    Paragraph title = new Paragraph(this.title, this.f_title2);
    title.setAlignment("center");
    this.document.add(title);
    
    //標題 與 標單的間距
    title = new Paragraph("		", this.f_date);
    title.setAlignment("right");
    this.document.add(title);
    
    //字號
    if(!fontnumber.equals("")) {
     title = new Paragraph(fontnumber, this.f_date);
    } else{
     title = new Paragraph(cslist.get(0).getClubMember().getIssue_code(), this.f_date);    
    }
    title.setAlignment("right");
    this.document.add(title);
    
    //step2:建立表格
    float headerwidths[] = {99};
    Table tablep = new Table(1);
    tablep.setWidths(headerwidths);
    tablep.getDefaultCell().setBorderColor(Color.WHITE);
    tablep.setBorderColor(Color.WHITE); // table 邊框線
    tablep.setConvert2pdfptable(true);//把Table轉回PdfPTable(Table預設外框為粗)  
    PdfPTable beanTable = tablep.createPdfPTable();
    beanTable.setWidthPercentage(100);
    beanTable.setSpacingBefore(4);
    
    //step3:列印內容
    PdfPCell cell =  null;// cell
    
    
    
    Color hcolor  = new java.awt.Color(255,255,255);//color
    
        //為區隔用
    	beanTable.addCell(this.addNewCell(" ", f_title, Element.ALIGN_CENTER, 5, hcolor, 0, 1, 0,  0.1f, 0.1f, 0.1f,0.1f));
        beanTable.addCell(this.addNewCell(" ", f_title, Element.ALIGN_CENTER, 5, hcolor, 0, 1, 0,  0.1f, 0.1f, 0.1f,0.1f));    
        //導師姓名    
        beanTable.addCell(this.addNewCell("查本校  		" + cslist.get(0).getStubasis().getCname(), f_col3, Element.ALIGN_CENTER, 5, hcolor, 0, 1, 0,  0.1f, 0.1f, 0.1f,0.1f));
        
        beanTable.addCell(this.addNewCell(" ", f_title, Element.ALIGN_CENTER, 5, hcolor, 0, 1, 0,  0.1f, 0.1f, 0.1f,0.1f));
        //學年度  + 學期          
        beanTable.addCell(this.addNewCell("於    "+cslist.get(0).getSbj_year()+"	學年度" + "  第  "+ cslist.get(0).getSbj_sem() +"  學期參加", f_col3, Element.ALIGN_CENTER, 5, hcolor, 0, 1, 0,  0.1f, 0.1f, 0.1f,0.1f));
        
        beanTable.addCell(this.addNewCell(" ", f_col2, Element.ALIGN_CENTER, 5, hcolor, 0, 1, 0,  0.1f, 0.1f, 0.1f,0.1f));
        //學校
        beanTable.addCell(this.addNewCell(cslist.get(0).getClub().getClub_name()+"	    社團", f_col3, Element.ALIGN_CENTER, 5, hcolor, 0, 1, 0,  0.1f, 0.1f, 0.1f,0.1f));
        
        beanTable.addCell(this.addNewCell(" ", f_title, Element.ALIGN_CENTER, 5, hcolor, 0, 1, 0,  0.1f, 0.1f, 0.1f,0.1f));
        //社團
        beanTable.addCell(this.addNewCell("特此證明", f_col3, Element.ALIGN_CENTER, 5, hcolor, 0, 1, 0,  0.1f, 0.1f, 0.1f,0.1f));
        
        beanTable.addCell(this.addNewCell(" ", f_title, Element.ALIGN_CENTER, 5, hcolor, 0, 1, 0,  0.1f, 0.1f, 0.1f,0.1f));
        //內容
        beanTable.addCell(this.addNewCell(titleschool, f_col3, Element.ALIGN_CENTER, 5, hcolor, 0, 1, 0,  0.1f, 0.1f, 0.1f,0.1f));
        
        beanTable.addCell(this.addNewCell(" ", f_col2, Element.ALIGN_CENTER, 5, hcolor, 0, 1, 0,  0.1f, 0.1f, 0.1f,0.1f));
        //內容
        beanTable.addCell(this.addNewCell("學務處		社團活動組", f_col3, Element.ALIGN_CENTER, 5, hcolor, 0, 1, 0,  0.1f, 0.1f, 0.1f,0.1f));
        beanTable.addCell(this.addNewCell(" ", f_col2, Element.ALIGN_CENTER, 5, hcolor, 0, 1, 0,  0.1f, 0.1f, 0.1f,0.1f));
        //民國年 + 月 + 日
        
        DateFormat dy = new SimpleDateFormat("yyyy");
        DateFormat dm = new SimpleDateFormat("MM");
        DateFormat dd = new SimpleDateFormat("dd");
        Date date = new Date();
		
        String print_date_y = dy.format(date);
		String print_date_m = dm.format(date);
		String print_date_d = dd.format(date);
		
		int chyears = Integer.parseInt(print_date_y)-1911;
		String chedyear = String.valueOf(chyears);
        
        beanTable.addCell(this.addNewCell("中  華  民  國    "+ chedyear +" 年   "+ print_date_m +" 月  "+ print_date_d +" 日", f_col3, Element.ALIGN_CENTER, 5, hcolor, 0, 1, 0,  0.1f, 0.1f, 0.1f,0.1f));
        
        this.document.add(beanTable);
    
}
public void setCslist(List<ClubSem> cslist) {
	this.cslist = cslist;
}
}
