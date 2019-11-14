package ntpc.ccai.clubmgt.bean;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
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
 * 社團點名單
 * Tom
 * 創立日期 : 2018/04/27
 * */

public class ClubExportPDF4 {


	private Document document = null;
    /*檔案參數*/
    //private String fontstyle = "D:\\resin\\webapps\\CurrProgramSystem\\font\\kaiu.ttf";
    private String fontstyle = "";
    private String print_page ="";//頁碼
    private String print_date  = ""; // 列印日期
    private String title="";//title
    private String titleman = "";
    int tableno = 0; //報表流水號
    /*字型設定*/
    private Font f_title   = null;//表格title:國立臺北科技大學 xxx系xx制課程科目表
    private Font f_date    = null;//表格列印日期
    private Font f_col     = null;//表頭欄位
    private Font f_col2     = null;//表頭欄位2
    private Font f_small   = null;//特小的字
    private Font f_content = null;//資料內容

    Color hcolor  = new java.awt.Color(255,255,255);//color
    
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
    
  //ClubMember data list
    private List<ClubMember> cmlist = null;
    
 // method 
    public void setPrint_page(String Pvalue) {this.print_page = Pvalue;}//列印主頁碼
    
    //public void setPrint_date(String Pvalue) {this.print_date = (Integer.parseInt(Pvalue.substring(0,4)))-1911 +  Pvalue.substring(4);}//列印日期
    public void setPrint_date(String Pvalue) {this.print_date = Pvalue;}//列印日期
    public void setTitle(String Pvalue)      {this.title       = Pvalue;}//
    public void setFontStyle(String Pvalue)  {this.fontstyle   = Pvalue;};
    public void setTitleMan(String Pvalue)  {this.titleman   = Pvalue;};
    
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
 
   f_date  = new Font(bfChinese,12,Font.NORMAL);
   
   f_col   = new Font(bfChinese,10,Font.HELVETICA);
   
   f_col2   = new Font(bfChinese,16,Font.HELVETICA);
   
   f_small = new Font(bfChinese,8,Font.HELVETICA);  
   
   f_content = new Font(bfChinese,10,Font.NORMAL); 
 
   // step4:設定頁尾頁尾 
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
   
   // step5:開啟檔案
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
      
        // Step 1：建立 上方 Table 
               
        PdfPTable datatable = this.setHeader();   //下表單 
                      
        // Step 2：建立版面配置所需要的參數
        int limit_count = 27; // 版面  限制每一頁可列印的筆數
        int span_count  = 0; // 學年學期要setRowSpan()的列數
        int loss_count  = 0; // 每一學期(max)在同一頁尚未列印完的筆數
        int r_count     = 0; // 目前的筆數(用來判斷是否為同一頁最後一筆？)  
        int max         = 0; // 選,必修中最大的筆數
        float border    =0f; // 筆數的底線寬度
        PdfPCell cell =  null;// cell
        float hor_count  = 0; // 水平合計
        int chkfor = 0; //判斷 是否將資料印完
        
        try {
            // Step 3：列印課程大綱資料
         //  for (int i=0;i<semester.length;i++){//學期迴圈-------------------------------------------------
                loss_count =0;  // 每一學期開始,loss_count都應該為0
                max        =0;  // default               
                int checkrgno = 0; // 確認 學生是否重複
                int cadrenamecount = 0; // 一位學生  總共有多 職稱
                int forruncount = 0; // for 共跑了幾次
                
                // 找出最大的筆數
                //max = (ec1.size()>ec2.size() == true) ? ec1.size() : ec2.size(); 
                int rowrk = 2;
                max = cmlist.size(); // cell 數量
                // if (max>0):表示有資料才列印
                if (max>0){
                    //beanData= new CprogramData();
                   // 課程科目迴圈------------------------------------------------- 
                    for (int i=0;i<max;i++){
                    	
                    	// 基本資料     
                    	if(cmlist.get(i).getRgno() != checkrgno) {
                    	  checkrgno = cmlist.get(i).getRgno() ; 
                    	String  cadrename = "";
                    	//(1) 此迴圈 確認 學生有多少職稱
            		    for(int j=forruncount; j<cmlist.size() ;j++) {                        			
            			  if( checkrgno == cmlist.get(j).getRgno() ) {
            				cadrenamecount += 1;
            		      }
            			}
            		    //(2) 依照現在學生 進行 職稱 group
            			for(int z=forruncount; z < (forruncount+cadrenamecount) ;z++) {   // z = 迴圈跑幾次 < (迴圈跑幾次+共有多少職稱) ;
            				if(forruncount == z) {
            					if((cmlist.get(z).getClubCadre().getCadre_name() == null) || cmlist.get(z).getClubCadre().getCadre_name().equals("")) {
            						cadrename += "社員";
            					} else {
            						cadrename += cmlist.get(z).getClubCadre().getCadre_name();
            					}                             		                                       		    
            				} else {  
            					if((cmlist.get(z).getClubCadre().getCadre_name() == null) || cmlist.get(z).getClubCadre().getCadre_name().equals("")) {
            						cadrename += "社員";
            					} else {
            						cadrename += "、" + cmlist.get(z).getClubCadre().getCadre_name();
            					}                 					
            				}
                		}
            			cadrenamecount = 0 ; // 完成 後清空 學生 職稱 總比數 
                    	
                             cell = this.addNewCell( String.valueOf(++tableno), f_content, Element.ALIGN_CENTER, 5, null, span_count, 0, 0, 0.1f, 0.1f, 0.1f, 0.1f);   
                             datatable.addCell(cell);
                             cell = this.addNewCell(cadrename, f_content, Element.ALIGN_CENTER, 5, null, span_count, 0, 0, 0.1f, 0.1f, 0.1f, 0.1f);   
                             datatable.addCell(cell);        
                             cell = this.addNewCell(cmlist.get(i).getStuRegister().getStuBasis().getCname(), f_content, Element.ALIGN_CENTER, 5, null, span_count, 0, 0, 0.1f, 0.1f, 0.1f, 0.1f);   
                             datatable.addCell(cell);
                             cell = this.addNewCell((cmlist.get(i).getStuRegister().getStuBasis().getSex() == 1) ? "男" : "女" , f_content, Element.ALIGN_CENTER, 5, null, span_count, 0, 0, 0.1f, 0.1f, 0.1f, 0.1f);   
                             datatable.addCell(cell);
                             cell = this.addNewCell(cmlist.get(i).getStuRegister().getStuClass().getCls_cname() + " " + cmlist.get(i).getStuRegister().getCls_no() + "號", f_content, Element.ALIGN_CENTER, 5, null, span_count, 2, 0, 0.1f, 0.1f, 0.1f, 0.1f);   
                             datatable.addCell(cell);
                    	
                             //for (int z=0;z<1;z++){ // 列印[課程類別] (直向欄位)
                            // ccd = this.ccdl.get(i);
                               // 點名用表格
                             for (int s=0;s<4;s++){ //寬
                              cell = this.addNewCell("", f_date, Element.ALIGN_CENTER, 5, hcolor, 0, 0, 0, 0.1f, 0.1f, 0.1f, 0.1f);   
                              datatable.addCell(cell);
                             }
                             r_count = r_count +1; //目前筆數+1
                    	} else {
                	    	checkrgno = 0 ;
                	    	
                	    }
                            // }   
                    	
                        /**************************************************/ 
                        // 計算版面參數(底線寬)
                        //chkfor = chkfor +1;     
                        
                        border  = 0;         //如果本頁最後一筆或學期最後一筆，則底線設為空0f
                        if (i == max-1){
                            border=0.5f;//如果最後一筆，則底線設為實線0.5f
                        }
                        
                        if (r_count % limit_count ==0 ){//是否為每一頁的最後一筆
                           loss_count = max-(i+1);//計算剩餘的筆數
                           border=1;
                           r_count=0;//筆數(列印至....)設回0
                        }                    
                        
                        if (r_count==0 ){//如果已經是最後一筆了
                            this.document.add(datatable);    // 就新增table
                            //System.out.println(max +","+ chkfor);
                            this.document.newPage();
                            // 換下一頁                           
                            //if(max != chkfor) {this.document.newPage(); System.out.println("換頁");}  //整除的話 還是會nowpag
                            this.number++;
                            datatable =this.setHeader();     // 再新增一個有header(但尚未新增加入至document)
                        }
                        forruncount += 1;
                    }
                }
         //  }// end OF 學期迴圈-------------------------------------------------
         
           // Step 5：clear print
         
           if (r_count >0){ // 表示最 後一頁未滿limt筆數，但仍要把它印完....             
        	   this.document.add(datatable);
           }
           
           // Step 6：列印備註資料
           //if (this.crmd!=null){addRemark(this.crmd,r_count);}
           
           // Step 7：列印學分數資料  
           //this.document.newPage();
           //addCredits();
        
           
        } catch (DocumentException e) {
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
    cell.setBorderColor(Color.BLACK); //表格內分隔線
    
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
    String header =  this.title+" "+adg_cname+" 學年度："+mat_cname+" 學期 "+dep_cname;
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


/*建立社團點名資料*/
private PdfPTable setHeader()throws Exception{
    /*新的學分數統計表(二維)*/
    int colunm = 0; // 共幾欄

    float total_count = 0; // 總學分數
    float hor_count  = 0; // 水平合計
    float ver_count  = 0; // 垂直合計
    //float[][] credit_array = new float[ccdl.size()][codl.size()];  // 學分數暫存
    float min_credit = 0; // 最底應修學數
    String text = "";
    
    
    
    //float[][] credit_array = new {{02},{}};  // 學分數暫存
    
    
    //step1:列印標題 
    Paragraph title = new Paragraph(this.title, this.f_title);
    title.setAlignment("center");
    this.document.add(title);
          
    
    //此為區隔用  
    
    title = new Paragraph(" ", this.f_date);
    title.setAlignment("right");  
    title.setLeading(10);//設定行距
    this.document.add(title);
    
    //step2:列印時間   不列印
    /*
    title = new Paragraph("列印時間:"+this.print_date, this.f_date);
    title.setAlignment("right");
    title.setLeading(10);//設定行距   
    this.document.add(title);
    */
    //CurrOptionData   cod = new CurrOptionData();
    //CurrCategoryData ccd = new CurrCategoryData();
     colunm = 10; //暫定 23
        float headerwidths[] = {4,4,4,4,4,4,8,8,8,8};
        Table datatable_c = new Table(colunm);
        datatable_c.setWidths(headerwidths);
        datatable_c.getDefaultCell().setBorderColor(Color.BLACK);
        datatable_c.setConvert2pdfptable(true);//把Table轉回PdfPTable(Table預設外框為粗) 
        PdfPTable beanTable = datatable_c.createPdfPTable();
        beanTable.setWidthPercentage(100);
        beanTable.setSpacingBefore(10);
        beanTable.setSplitRows(false);
        PdfPCell cell =  null;// cell
    
    if (cmlist.size()>0){
    	//this.cdl!=null && this.codl!=null && this.ccdl!=null
        //colunm = this.codl.size() + 2;  // +2 第一個空白+最後一個[合計]
    	
        
        // 列印第一行(橫向)---------------------------------------------------------------------------------開始
        // first colunm
        cell = this.addNewCell("序號", f_date, Element.ALIGN_CENTER, 5, hcolor, 2, 0, 0, 0.1f, 0.1f, 0.1f, 0.1f);   
        beanTable.addCell(cell);   
        
        String[] codl = {"職稱","姓名","性別","班級座號"};
        int codlsize = 4;
        for (int i=0;i<codlsize;i++){ // 列印[部校訂選別](橫向欄位)
            //cod = codl.get(i);
            beanTable.addCell(this.addNewCell(codl[i], f_date, Element.ALIGN_CENTER, 5, hcolor, 2, 0, 0, 0.1f, 0.1f, 0.1f, 0.1f)); //A  
        }
        
        beanTable.addCell(this.addNewCell("日期", f_date, Element.ALIGN_CENTER, 5, hcolor, 0, 1, 0, 0.1f, 0.1f, 0.1f, 0.1f)); //A
        for (int i=0;i<4;i++){ // 日
            //cod = codl.get(i);
        	String[] w = {"","","",""}; 
            beanTable.addCell(this.addNewCell(w[i], f_date, Element.ALIGN_CENTER, 5, hcolor, 0, 1, 0, 0.1f, 0.1f, 0.1f, 0.1f)); //A  
        }
        
        beanTable.addCell(this.addNewCell("節次", f_date, Element.ALIGN_CENTER, 5, hcolor, 0, 1, 0, 0.1f, 0.1f, 0.1f, 0.1f)); //A
        for (int i=0;i<4;i++){ // 月
            //cod = codl.get(i);
        	String[] d = {"","","",""};
            beanTable.addCell(this.addNewCell(d[i], f_date, Element.ALIGN_CENTER, 5, hcolor, 0, 1, 0, 0.1f, 0.1f, 0.1f, 0.1f)); //A  
            //beanTable.addCell(this.addNewCell(d[i], f_date, Element.ALIGN_CENTER, 5, hcolor, 2, 1, 0, 0.1f, 0.1f, 0.1f, 0.1f)); //A  
        }
              
        // 列印第一行(橫向)---------------------------------------------------------------------------------結束
        // 列印第N行(含title&各學分數)------------------------------------------------------------------------結束
 
       // this.document.add(beanTable);        
    }else{
        text = "※ 無法取得《社團成員》資料，請聯絡系統管理員處理!";
        Paragraph p = new Paragraph(text, f_title);
        p.setAlignment(Element.ALIGN_CENTER);
        this.document.add(p);   
    }
	return beanTable;

}

public void setCmlist(List<ClubMember> cmlist) {
	this.cmlist = cmlist;
}

	

}
