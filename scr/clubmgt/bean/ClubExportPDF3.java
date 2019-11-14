package ntpc.ccai.clubmgt.bean;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.Image;
import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
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
 * 社團管理 : 報表系統 社團幹部經歷證明 Tom 創立日期 : 2018/04/25
 */

public class ClubExportPDF3 {

	private Document document = null;
	/* 檔案參數 */
	// private String fontstyle =
	// "D:\\resin\\webapps\\CurrProgramSystem\\font\\kaiu.ttf";
	private String fontstyle = "";
	private String print_page = "";// 頁碼
	private String print_date = ""; // 列印日期
	private String title = "";// title
	private String titleman = "";
	private String stuimgurl = "";
	/* 字型設定 */
	private Font f_title = null;// 表格title:國立臺北科技大學 xxx系xx制課程科目表
	private Font f_date = null;// 表格列印日期
	private Font f_col = null;// 表頭欄位
	private Font f_col2 = null;// 表頭欄位2
	private Font f_small = null;// 特小的字
	private Font f_content = null;// 資料內容

	// 建立不同系所之頁碼陣列集合{5,1,8}表示共有三組系所組，總頁數分
	public int number = 1;
	public int[] countPage = null;

	public void createPageArray(int size) {
		this.countPage = new int[size];
	}

	public String pagetype = null;// 編製頁碼方式

	public void setPageType(String value) {
		this.pagetype = value;
	}

	
	private ByteArrayOutputStream buffer = null;

	public ByteArrayOutputStream getBuffer() {
		return this.buffer;
	}

	// ClubMember data list
	private List<ClubMemberCadre> cmclist = null;

	// method
	public void setPrint_page(String Pvalue) {
		this.print_page = Pvalue;
	}// 列印主頁碼

	// public void setPrint_date(String Pvalue) {this.print_date =
	// (Integer.parseInt(Pvalue.substring(0,4)))-1911 + Pvalue.substring(4);}//列印日期
	public void setPrint_date(String Pvalue) {
		this.print_date = Pvalue;
	}// 列印日期

	public void setTitle(String Pvalue) {
		this.title = Pvalue;
	}//

	public void setFontStyle(String Pvalue) {
		this.fontstyle = Pvalue;
	};

	public void setTitleMan(String Pvalue) {
		this.titleman = Pvalue;
	};
		
	public void setStuimgurl(String stuimgurl) {
		this.stuimgurl = stuimgurl;
	}

	// open document
	public void openDocument(HttpServletResponse response) throws Exception {

		// step1:建立檔案
		document = new Document(PageSize.A4.rotate(), 20, 20, 1, 7);// 建立A4橫向(.rotate)之檔案：左右上下
		// step2:輸入pdf檔
		buffer = new ByteArrayOutputStream(); // new
		PdfWriter.getInstance(document, buffer);

		// step3:設定中文字體
		BaseFont bfChinese = BaseFont.createFont(fontstyle, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);

		f_title = new Font(bfChinese, 18, Font.HELVETICA);

		f_date = new Font(bfChinese, 12, Font.NORMAL);

		f_col = new Font(bfChinese, 10, Font.HELVETICA);

		f_col2 = new Font(bfChinese, 16, Font.HELVETICA);

		f_small = new Font(bfChinese, 8, Font.HELVETICA);

		f_content = new Font(bfChinese, 10, Font.NORMAL);

		// step4:設定頁尾頁尾
		if (this.pagetype != null && this.pagetype.equals("s")) {
			Phrase phrase_1 = new Phrase("~" + this.print_page + "-", f_date);
			Phrase phrase_2 = new Phrase("~", f_date);
			phrase_1.setLeading(10);
			phrase_2.setLeading(10);
			HeaderFooter footer = new HeaderFooter(phrase_1, phrase_2);
			footer.setAlignment(Element.ALIGN_CENTER);
			footer.setBorder(Rectangle.NO_BORDER);
			// footer.setAlignment(0);
			footer.setTop(0);

			document.setFooter(footer);
		}

		// step5:開啟檔案
		document.open();
	}// End of openDocument

	/* 關閉檔案 */
	public void closeDocument() throws Exception {
		document.close();
		if (this.pagetype != null && this.pagetype.equals("g")) {// ==g,以系所組區分不同之流水號
			this.addPageNumber();
		}
	}// END OF closeDocument()

	/**
	 * 產生PDF檔案的主程式
	 * 
	 * @return
	 * @throws Exception
	 */
	public int Generate() throws Exception {
		// CprogramData beanData = new CprogramData();
		// CprogramDataList ec1= new CprogramDataList(); //必修
		// CprogramDataList ec2= new CprogramDataList(); //選修
		String[] semester = { "11", "12", "21", "22", "31", "32" }; // 高中3學期

		// Step 1：建立 上方 Table

		setonTable(); // 上表單
		PdfPTable datatable = this.setunderTable(); // 下表單

		// Step 2：建立版面配置所需要的參數
		int limit_count = 19; // 版面 限制每一頁可列印的筆數
		int span_count = 0; // 學年學期要setRowSpan()的列數
		int loss_count = 0; // 每一學期(max)在同一頁尚未列印完的筆數
		int r_count = 0; // 目前的筆數(用來判斷是否為同一頁最後一筆？)
		int max = 0; // 選,必修中最大的筆數
		float border = 0f; // 筆數的底線寬度

		try {
			// Step 3：列印課程大綱資料
			// for (int
			// i=0;i<semester.length;i++){//學期迴圈-------------------------------------------------
			loss_count = 0; // 每一學期開始,loss_count都應該為0
			max = 0; // default

			// 找出最大的筆數
			// max = (ec1.size()>ec2.size() == true) ? ec1.size() : ec2.size();
			int rowrk = 2;
			max = cmclist.size(); // cell 數量
			// if (max>0):表示有資料才列印
			if (max > 0) {
				// beanData= new CprogramData();
				// 課程科目迴圈-------------------------------------------------
				for (int i = 0; i < max; i++) {

					datatable.addCell(this.addNewCell(
							cmclist.get(i).getSbj_year().toString() + " "
									+ (cmclist.get(i).getSbj_sem().toString().equals("1") ? "上" : "下"),
							f_content, Element.ALIGN_CENTER, 5, null, span_count, 0, 0, 0.1f, 0.1f, 0.1f, 0.1f));
					datatable.addCell(this.addNewCell(cmclist.get(i).getClub().getClub_name(), f_content,
							Element.ALIGN_CENTER, 5, null, span_count, 0, 0, 0.1f, 0.1f, 0.1f, 0.1f));
					datatable.addCell(this.addNewCell(cmclist.get(i).getClubCadre().getCadre_name(), f_content,
							Element.ALIGN_CENTER, 5, null, span_count, 0, 0, 0.1f, 0.1f, 0.1f, 0.1f));

					/**************************************************/
					// 計算版面參數(底線寬)
					r_count = r_count + 1; // 目前筆數+1
					border = 0; // 如果本頁最後一筆或學期最後一筆，則底線設為空0f
					if (i == max - 1) {
						border = 0.5f;// 如果最後一筆，則底線設為實線0.5f
					}

					if (r_count % limit_count == 0) {// 是否為每一頁的最後一筆
						loss_count = max - (i + 1);// 計算剩餘的筆數
						border = 1;
						r_count = 0;// 筆數(列印至....)設回0
					}

					// step4-4:列印課程科目表資料 (要再修改)
					/*
					 * //1:必修 beanData = ec1.get(j); this.addCell2(beanData, datatable, border);
					 * //2:選修 beanData = ec2.get(j); this.addCell2(beanData, datatable, border);
					 */

					if (r_count == 0) {// 如果已經是最後一筆了
						this.document.add(datatable); // 就新增table
						this.document.newPage(); // 換下一頁
						this.number++;
						// --------------- 這裡要改變 limit_count = ** 因為版面 學生資料第2業不需要 ----------------
						limit_count = 27;
						datatable = this.setunderTable(); // 再新增一個有header(但尚未新增加入至document)
					}
				}
			}
			/*
			 * else { datatable.addCell(this.addNewCell("無服務紀錄", f_content,
			 * Element.ALIGN_CENTER, 5, null, span_count, 0, 0, 0.1f, 0.1f, 0.1f, 0.1f)); }
			 */
			// }// end OF 學期迴圈-------------------------------------------------

			// Step 5：clear print

			if (r_count > 0) { // 表示最 後一頁未滿limt筆數，但仍要把它印完....

				this.document.add(datatable);
			}

			// Step 6：列印備註資料
			// if (this.crmd!=null){addRemark(this.crmd,r_count);}

			// Step 7：列印學分數資料
			// this.document.newPage();
			// addCredits();

		} catch (DocumentException e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/* 建立儲存格 */
	/**
	 * pTxt 內容, pFont 字型 , pHor , pPadding 儲存格邊界 , pColor 儲存格 背景顏色 , pRowspan 上下表格 ,
	 * pColspan 左右表格 , pFixedH , pTopW 表格邊框粗細(高) , pBottomW 表格邊框粗細(底) , pLeftW
	 * 表格邊框粗細(左) , pRightW 表格邊框粗細(右).
	 * 
	 **/
	private PdfPCell addNewCell(String pTxt, Font pFont, int pHor, int pPadding, Color pColor, int pRowspan,
			int pColspan, int pFixedH, float pTopW, float pBottomW, float pLeftW, float pRightW) {
		PdfPCell cell = null;
		cell = new PdfPCell(new Paragraph(pTxt, pFont));
		cell.setUseAscender(true);// 設true，置中的效果才有用
		cell.setHorizontalAlignment(pHor);
		cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
		cell.setPadding(pPadding);

		cell.setBackgroundColor(pColor);
		cell.setBorderColor(Color.BLACK); // 表格內分隔線

		cell.setBorderWidthTop(pTopW);
		cell.setBorderWidthBottom(pBottomW);
		cell.setBorderWidthLeft(pLeftW);
		cell.setBorderWidthRight(pRightW);

		if (pRowspan > 0) {
			cell.setRowspan(pRowspan);
		}
		if (pColspan > 0) {
			cell.setColspan(pColspan);
		}
		if (pFixedH > 0) {
			cell.setFixedHeight(pFixedH);
		}
		// 101.12.14 教務處徐宜瑩#1136 要求課程名稱對齊
		cell.setPaddingLeft(3f);

		return cell;
	}// END OF addNewCell

	/* 建立圖片儲存格 */
	/**
	 * imageURL 圖片連結 , pHor , pPadding 儲存格邊界 , pColor 儲存格 背景顏色 , pRowspan 上下表格 ,
	 * pColspan 左右表格 , pFixedH , pTopW 表格邊框粗細(高) , pBottomW 表格邊框粗細(底) , pLeftW
	 * 表格邊框粗細(左) , pRightW 表格邊框粗細(右).
	 * 
	 **/
	private static PdfPCell headPng(String imageURL, int pHor, int pPadding, Color pColor, int pRowspan, int pColspan,
			int pFixedH, float pTopW, float pBottomW, float pLeftW, float pRightW)
			throws IOException, DocumentException {

		PdfPCell cell = null;
		Image img = Image.getInstance(imageURL);

		img.scaleToFit(80,80);  //設置寬高 80  80
	
		cell = new PdfPCell(img); // 匯入圖片
		// img.scaleAbsolute(5,5);
		cell.setUseAscender(true);// 設true，置中的效果才有用
		cell.setHorizontalAlignment(pHor);
		cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
		cell.setPadding(pPadding);

		cell.setBackgroundColor(pColor);
		cell.setBorderColor(Color.BLACK); // 表格內分隔線

		cell.setBorderWidthTop(pTopW);
		cell.setBorderWidthBottom(pBottomW);
		cell.setBorderWidthLeft(pLeftW);
		cell.setBorderWidthRight(pRightW);

		if (pRowspan > 0) {
			cell.setRowspan(pRowspan);
		}
		if (pColspan > 0) {
			cell.setColspan(pColspan);
		}
		if (pFixedH > 0) {
			cell.setFixedHeight(pFixedH);
		}

		return cell;
	}

	/**
	 * @param mat_code
	 * @param adg_cname
	 * @param dep_cname
	 */
	// 設定標頭 "OO高中 107學年度 下學期 「 籃球」 社團成員名單"
	public void setHeaderString(String mat_cname, String adg_cname, String dep_cname) {
		String header = this.title + " " + adg_cname + " " + mat_cname + " " + dep_cname;
		this.setTitle(header);
	}

	/* 20120912判斷是否為中間的系統組，true的話要換頁 */
	public void newDivPage() throws Exception {
		this.document.newPage();
	}

	/**
	 * 在文件上加入頁碼
	 * 
	 * @throws Exception
	 */
	private void addPageNumber() throws Exception {
		try {
			PdfReader readert = new PdfReader(this.buffer.toByteArray());// Create a reader
			PdfStamper stamper = new PdfStamper(readert, this.buffer); // Create a stamper
			int startPage = 0;
			startPage = Integer.valueOf(this.print_page) - 1;
			int pre = 0;// 前一組系所組的總頁數
			for (int i = 0; i < this.countPage.length; i++) {// Loop系所組
				int p = this.countPage[i];// get當前該系所組的總頁數

				for (int j = 0; j < p; j++) {// loop
					int page = pre + (j + 1);// 真實的頁數=前頁數+(j+1)
					getHeaderTable(i + 1 + startPage, j + 1).writeSelectedRows(// i+1:系所組流水編號;j+1:該系所組下的第幾頁
							0, -1, 180, 25, stamper.getOverContent(page));
				}
				pre = pre + p;// set 前一組的頁數
			}
			stamper.close();// Close the stamper
		} catch (Exception ex) {
			throw ex;
		}
	}// END OF addPageNumber

	/**
	 * 建置有第x頁共y頁的table
	 * 
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
		Paragraph pp = new Paragraph(String.format("~%d-%d~", x, y), this.f_date);

		PdfPCell cellp = new PdfPCell(pp);
		cellp.setBorder(Rectangle.NO_BORDER);
		cellp.setHorizontalAlignment(Element.ALIGN_CENTER);

		table.addCell(cellp);
		return table;
	}// END OF getHeaderTable

	/* 建立學生基本資料表 */
	private void setonTable() throws Exception {
		// step1:列印標題
		//System.out.println("setHeader Paragraph " + this.title + "    " + this.f_title);
		Paragraph title = new Paragraph(this.title, this.f_title);
		title.setAlignment("center");
		this.document.add(title);

		// 標題 與 標單的間距
		title = new Paragraph(" ", this.f_title);
		title.setAlignment("center");
		this.document.add(title);

		// step2:建立表格
		float headerwidths[] = { 33, 66 };
		Table tablep = new Table(2);
		tablep.setWidths(headerwidths);
		tablep.getDefaultCell().setBorderColor(Color.BLACK);
		tablep.setBorderColor(Color.BLACK); // table 邊框線
		tablep.setPadding(0);
		tablep.setConvert2pdfptable(true);// 把Table轉回PdfPTable(Table預設外框為粗)
		PdfPTable beanTable = tablep.createPdfPTable();
		beanTable.setWidthPercentage(100);
		// beanTable.setSpacingBefore(4);
		// step3:列印內容
		PdfPCell cell = null;// cell

		Color hcolor = new java.awt.Color(255, 255, 255);// color

		// 圖片內容
		// beanTable.addCell(this.addNewCell("IMG", f_col, Element.ALIGN_CENTER, 5,
		// hcolor, 4, 1, 0, 0.1f, 0.1f, 0.1f,0.1f));
		//Image img = Image.getInstance(stuimgurl);
		//cell = new PdfPCell(img); // 匯入圖片
		
		File file = new File(stuimgurl);  
    	if (file.exists() && file.canRead()) {
    		beanTable.addCell(headPng(stuimgurl, Element.ALIGN_CENTER, 3, hcolor, 4, 1, 0,
    				0.1f, 0.1f, 0.1f, 0.1f));
    	}else {
    		beanTable.addCell(this.addNewCell("大頭貼", f_col2, Element.ALIGN_CENTER, 3, hcolor, 4, 1, 0,
    				0.1f, 0.1f, 0.1f, 0.1f));
    	}

		
		float twoheaderwidths[] = { 33, 67 };
		Table twotablep = new Table(2);
		twotablep.setWidths(twoheaderwidths);
		twotablep.getDefaultCell().setBorderColor(Color.WHITE);
		twotablep.setBorderColor(Color.WHITE); // table 邊框線
		twotablep.setPadding(0);
		twotablep.setBorderWidth(0);
		twotablep.setConvert2pdfptable(true);// 把Table轉回PdfPTable(Table預設外框為粗)
		PdfPTable twobeanTable = twotablep.createPdfPTable();
		twobeanTable.setWidthPercentage(100);
		// twobeanTable.setSpacingBefore(4);

		// 姓名欄位
		// cell = this.addNewCell("姓 名", f_col2, Element.ALIGN_LEFT, 5, hcolor, 0, 1, 0,
		// 0.1f, 0.1f, 0.1f,0.1f);
		twobeanTable.addCell(this.addNewCell("姓	名", f_col2, Element.ALIGN_LEFT, 5, hcolor, 0, 1, 0, 0, 0.1f, 0, 0.1f));
		// 姓名
		// cell = this.addNewCell("李莫愁", f_col2, Element.ALIGN_LEFT, 5, hcolor, 0, 1, 0,
		// 0.1f, 0.1f, 0.1f,0.1f);
		twobeanTable.addCell(this.addNewCell(cmclist.get(0).getStuBasis().getCname(), f_col2, Element.ALIGN_LEFT, 5,
				hcolor, 0, 1, 0, 0, 0.1f, 0.1f, 0));
		// 班級欄位
		twobeanTable.addCell(this.addNewCell("班	級", f_col2, Element.ALIGN_LEFT, 5, hcolor, 0, 0, 0, 0.1f, 0, 0, 0.1f));
		// 班級
		twobeanTable.addCell(this.addNewCell(cmclist.get(0).getStuClass().getCls_cname(), f_col2, Element.ALIGN_LEFT, 5,
				hcolor, 0, 0, 0, 0.1f, 0, 0.1f, 0));
		beanTable.addCell(twobeanTable);

		float thrheaderwidths[] = { 50, 50 };
		Table thrtablep = new Table(2);
		thrtablep.setWidths(thrheaderwidths);
		thrtablep.getDefaultCell().setBorderColor(Color.WHITE);
		thrtablep.setBorderColor(Color.WHITE); // table 邊框線
		thrtablep.setPadding(0);
		thrtablep.setBorderWidth(0);
		thrtablep.setConvert2pdfptable(true);// 把Table轉回PdfPTable(Table預設外框為粗)
		PdfPTable thrbeanTable = thrtablep.createPdfPTable();
		thrbeanTable.setWidthPercentage(100);
		// thrbeanTable.setSpacingBefore(4);
		// 出生年月欄位 + 資料
		thrbeanTable.addCell(this.addNewCell("出生日期  : " + cmclist.get(0).getStuBasis().getBirthday(), f_col2,
				Element.ALIGN_LEFT, 5, hcolor, 0, 1, 0, 0, 0.1f, 0, 0.1f));
		// 學號欄位 + 資料
		thrbeanTable.addCell(this.addNewCell("學號  : " + cmclist.get(0).getStuBasis().getReg_no(), f_col2,
				Element.ALIGN_LEFT, 5, hcolor, 0, 1, 0, 0, 0.1f, 0.1f, 0));
		// 入學日期欄位 + 入學日期資料
		thrbeanTable.addCell(this.addNewCell("入學日期  : " + cmclist.get(0).getStuBasis().getCmat_year(), f_col2,
				Element.ALIGN_LEFT, 5, hcolor, 0, 0, 0, 0.1f, 0, 0, 0.1f));
		// 性別欄位 + 性別代號 (1:男 , 2:女)
		thrbeanTable.addCell(this.addNewCell("性別  : " + ((cmclist.get(0).getStuBasis().getSex() == 1) ? "男" : "女"),
				f_col2, Element.ALIGN_LEFT, 5, hcolor, 0, 0, 0, 0.1f, 0, 0.1f, 0));

		beanTable.addCell(thrbeanTable);

		this.document.add(beanTable);

	}

	/* 建立學生歷程資料表 */
	private PdfPTable setunderTable() throws Exception {
		// step1:列印標題
		//System.out.println("setHeader Paragraph " + this.title + "    " + this.f_title);
		Paragraph title = new Paragraph(" ", this.f_title);
		title.setAlignment("center");
		this.document.add(title);

		// step2:建立表格
		float headerwidths[] = { 33, 33, 33 };
		Table tablep = new Table(3);
		tablep.setWidths(headerwidths);
		tablep.getDefaultCell().setBorderColor(Color.BLACK);
		tablep.setBorderColor(Color.BLACK); // table 邊框線
		tablep.setConvert2pdfptable(true);// 把Table轉回PdfPTable(Table預設外框為粗)
		PdfPTable beanTable = tablep.createPdfPTable();
		beanTable.setWidthPercentage(100);
		beanTable.setSpacingBefore(4);

		// step3:列印內容
		PdfPCell cell = null;// cell
		Color hcolor = new java.awt.Color(255, 255, 255);// color

		// 學年學期
		beanTable.addCell(
				this.addNewCell("學年學期", f_col2, Element.ALIGN_CENTER, 5, hcolor, 0, 0, 0, 0.1f, 0.1f, 0.1f, 0.1f));
		// 參加社團
		beanTable.addCell(
				this.addNewCell("參加社團", f_col2, Element.ALIGN_CENTER, 5, hcolor, 0, 0, 0, 0.1f, 0.1f, 0.1f, 0.1f));
		// 職務
		beanTable.addCell(
				this.addNewCell("職務", f_col2, Element.ALIGN_CENTER, 5, hcolor, 0, 0, 0, 0.1f, 0.1f, 0.1f, 0.1f));

		return beanTable;
	}

	// 設定 社團學生 幹部經歷 資料
	public void setCmclist(List<ClubMemberCadre> cmclist) {
		this.cmclist = cmclist;
	}

}
