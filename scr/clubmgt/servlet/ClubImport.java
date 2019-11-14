package ntpc.ccai.clubmgt.servlet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Font;

import com.google.gson.Gson;

import ntpc.ccai.bean.UserData;
import ntpc.ccai.clubmgt.bean.ClubCadre;
import ntpc.ccai.clubmgt.bean.ClubMember;
import ntpc.ccai.clubmgt.bean.ClubSem;
import ntpc.ccai.clubmgt.bean.SheetGenerater;
import ntpc.ccai.clubmgt.bean.StuClass;
import ntpc.ccai.clubmgt.service.ClubCadreService;
import ntpc.ccai.clubmgt.service.ClubMemberService;
import ntpc.ccai.clubmgt.service.ClubSemService;
import ntpc.ccai.clubmgt.service.StuClassService;
import ntpc.ccai.clubmgt.util.StringUtils;
import ntpc.ccai.util.FileUtil;
import ntpc.ccai.util.ParseXLSUtil;

/**
 * Servlet implementation class ClubImport
 */
@WebServlet("/ClubImport")
public class ClubImport extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String MANAGEMENT_SERVLET = "/StuClub/ClubMgt";
	private static final String INIT   = "/WEB-INF/jsp/ClubMgt/ClubImport.jsp"; // 社團選社設定
	private static final String ERROR  = "/WEB-INF/jsp/ErrorPage.jsp";          // 失敗的 view
	private static final Logger logger = Logger.getLogger(ClubSelectionMethod.class);
	
	private ClubSemService    clubSemService    = new ClubSemService();
	private ClubCadreService  clubCadreService  = new ClubCadreService();
	private ClubMemberService clubMemberService = new ClubMemberService();
	private StuClassService   stuClassService   = new StuClassService();
       
	// service
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String error_msg = null; // 錯誤訊息
        request.setCharacterEncoding("UTF-8");
        
        // 收集資料
        HttpSession session = request.getSession(); // 取得 session 物件
        
        // 設定資料
        UserData  ud       = (UserData) session.getAttribute("ud"); // 取得 session 中的物件
        Integer   sbj_year = (Integer) session.getAttribute("sbj_year");
        Character sbj_sem  = (Character) session.getAttribute("sbj_sem");
        
        // Step 1: 權限檢查
        if (ud == null) {
            // 尚未登入
            error_msg = "連線逾期，無法順利建立資料，請重新登入";
        } else {
            if (!ud.getRole_code().equals("sta")) { // sta承辦人
                // 沒有使用權限
                error_msg = "您沒有使用權限";
            }
        }
        
        if (sbj_year == null || sbj_sem == null) {
            response.sendRedirect(MANAGEMENT_SERVLET);
            return;
        }
        
        if (error_msg != null) {
            request.setAttribute("error_msg", error_msg);
            request.getRequestDispatcher(ERROR).forward(request, response);
            return;
        }
        // Step 2: doGet or doPost
        super.service(request, response);
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String targetURL  = "";
		String fileaction = "";
		String error_msg  = null;
		String sbj_year   = "";
		String sbj_sem    = "";
		
		// 收集資料
        request.setCharacterEncoding("UTF-8");      // Ajax need
        HttpSession session = request.getSession(); // 取得 session 物件
        
        // 設定資料
        UserData ud = (UserData)session.getAttribute("ud"); // 取得 session 中的ud
        String sch_code = ud.getSch_code();                 // 取得學校代碼
		
        try {
        	targetURL = this.INIT;
        	fileaction = request.getParameter("fileaction");
        	//System.out.println("fileaction= " + fileaction);
        	
        	// 下載範例檔
        	if("download".equals(fileaction)) {
        		sbj_year = request.getParameter("sbjYear");
        		sbj_sem  = request.getParameter("sbjSem");
        		this.doPostExportExcel(request, response, sch_code, sbj_year, sbj_sem);
        		return;
        	}
        	
        	request.setAttribute("semesterMap", new Gson().toJson(clubSemService.getClubYearsAndSemesters(sch_code)));
        
        } catch(Exception ex) {
        	logger.error(ex);
        	error_msg = "下載範例檔發生例外錯誤";
        }
        
        if (error_msg != null && error_msg.length() > 0) {
            request.setAttribute("error_msg", error_msg);
            targetURL = ERROR;
        }
        request.getRequestDispatcher(targetURL).forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String targetURL = INIT;
		String error_msg = null;
		
		// 收集資料
        request.setCharacterEncoding("UTF-8");      // Ajax need
        HttpSession session = request.getSession(); // 取得session物件
        
        // 設定資料
        UserData ud = (UserData)session.getAttribute("ud"); // 取得session中的ud
        String sch_code = ud.getSch_code();                 // 取得學校代碼
		
        try {
    		error_msg = this.doPostImportExcel(request, response, sch_code);
        	request.setAttribute("semesterMap", new Gson().toJson(clubSemService.getClubYearsAndSemesters(sch_code)));
        
        } catch(Exception ex) {
        	logger.error(ex);
        	error_msg = "社團批次匯入發生例外錯誤";
        }
        
        if (error_msg != null && error_msg.length() > 0) {
            request.setAttribute("error_msg", error_msg);
            targetURL = ERROR;
        } else {
        	request.setAttribute("success_msg", "上傳成功");
        }
        request.getRequestDispatcher(targetURL).forward(request, response);
	}
	
	private void doPostExportExcel(HttpServletRequest request, HttpServletResponse response, String sch_code, String sbj_year, String sbj_sem) throws Exception, IOException {
		ServletOutputStream out = null;
		
		try {
            // 設定下載EXCEL檔的檔名
            String fileName = sbj_year + "學年度第" + sbj_sem + "學期社團成員幹部範例檔.xls";
            fileName = new String(fileName.getBytes(), "ISO8859-1");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition","attachment;filename=\"" + fileName + "\"");
            
            // 產生EXCEL的內容
            /******************** 產生Sheet內容開始 ********************/
            LinkedHashMap<String,ArrayList<ArrayList<String>>> sheetNameMap = new LinkedHashMap<String,ArrayList<ArrayList<String>>>();
            ArrayList<SheetGenerater> dropdownDataList = new ArrayList<SheetGenerater>();
            ArrayList<SheetGenerater> tooltipDataList  = new ArrayList<SheetGenerater>();
            boolean isEmptySheet = false; // 為了讓空白頁產生範例資料
            
            /**** 產生Sheet的區塊 ****/
            // 第一張Sheet: 產生SheetName與Sheet內容
            ArrayList<ArrayList<String>> sheetContentlist1 = new ArrayList<ArrayList<String>>();
            ArrayList<String> rowList1 = new ArrayList<String>();
            String sheetName1 = "社團成員幹部表";
            String[] colsName = {"*學年度", "*學期", "*社團編號", "*班級代碼", "*座號", "成績", "幹部代碼1", "幹部代碼2", "幹部代碼3"};
            rowList1= new ArrayList<String>(Arrays.asList(colsName));
            sheetContentlist1.add(rowList1);
            
            
            sheetNameMap.put(sheetName1,sheetContentlist1);
            
            // 第二張Sheet: 社團編號代碼
            ArrayList<ArrayList<String>> sheetContentlist2 = new ArrayList<ArrayList<String>>();
            ArrayList<String> rowList2 = new ArrayList<String>();
            rowList2.add("社團編號");
            rowList2.add("社團名稱");
            sheetContentlist2.add(rowList2);
            List<ClubSem> clubSems = clubSemService.getClubSemsByYearAndSem(sch_code, Integer.parseInt(sbj_year), StringUtils.toCharacter(sbj_sem));
            for (ClubSem clubSem : clubSems) {
            	ArrayList<String> list = new ArrayList<String>();
            	list.add(clubSem.getClub_num().toString());
            	list.add(clubSem.getClub().getClub_name());
            	sheetContentlist2.add(list);
            }
            sheetNameMap.put("參考(1)社團編號代碼",sheetContentlist2);
            
            // 第三張Sheet: 班級代碼
            ArrayList<ArrayList<String>> sheetContentlist3 = new ArrayList<ArrayList<String>>();
            ArrayList<String> rowList3 = new ArrayList<String>();
            rowList3.add("班級代碼");
            rowList3.add("班級名稱");
            sheetContentlist3.add(rowList3);
            List<StuClass> stuClasses = stuClassService.getStuClassesByYearAndSem(sch_code, Integer.parseInt(sbj_year), StringUtils.toCharacter(sbj_sem));
            for (StuClass stuClass : stuClasses) {
            	ArrayList<String> list = new ArrayList<String>();
            	list.add(stuClass.getCls_code());
            	list.add(stuClass.getCls_cname());
            	sheetContentlist3.add(list);
            }
            sheetNameMap.put("參考(2)班級代碼",sheetContentlist3);
            
            // 第四張Sheet: 社團幹部代碼
            ArrayList<ArrayList<String>> sheetContentlist4 = new ArrayList<ArrayList<String>>();
            ArrayList<String> rowList4 = new ArrayList<String>();
            rowList4.add("幹部代碼");
            rowList4.add("幹部名稱");
            sheetContentlist4.add(rowList4);
            List<ClubCadre> clubCadres = clubCadreService.getAllClubCadres(sch_code);
            for (ClubCadre clubCadre : clubCadres) {
            	ArrayList<String> list = new ArrayList<String>();
            	list.add(clubCadre.getCadre_num().toString());
            	list.add(clubCadre.getCadre_name());
            	sheetContentlist4.add(list);
            }
            sheetNameMap.put("參考(3)社團幹部代碼",sheetContentlist4);
            
            /******* 產生下拉選單區塊 *******/
            dropdownDataList = null;
            
            /******* 產生提示黃框區塊 *******/
            tooltipDataList = null;
            
            /* 產生Excel檔 */
            HSSFWorkbook workbook = new HSSFWorkbook();  
            String SheetName = "";
            HSSFSheet sheet = null; 
            ArrayList<ArrayList<String>> sheetDataList = new ArrayList<ArrayList<String>>();
            ArrayList<String> rowList = new ArrayList<String>();
            
            // 宣告POI物件
            HSSFRow row = null;
            HSSFCell cell = null;
            HSSFCellStyle styleheader;
            HSSFCellStyle stylecolumn;
            HSSFCellStyle styleExamplecolumn;
            
            /******* 設定標題單元格格式  *******/
            /* 設定文件格式 */
            // 設定字型
            Font font = workbook.createFont();
            font.setColor(HSSFColor.BLACK.index);     // 顏色
            font.setBoldweight(Font.BOLDWEIGHT_BOLD); // 粗體
            // 設定儲存格格式(例如:顏色)
            styleheader = workbook.createCellStyle();
            styleheader.setFont(font);
            styleheader.setFillForegroundColor(HSSFColor.PALE_BLUE.index); // 設定顏色                                    
            styleheader.setAlignment(HSSFCellStyle.ALIGN_CENTER);          // 水平置中 
            styleheader.setFillPattern((short) 1);
            // 設定儲存格格線
            styleheader.setBorderBottom((short) 1);
            styleheader.setBorderTop((short) 1);
            styleheader.setBorderLeft((short) 1);
            styleheader.setBorderRight((short) 1);

            /* 設定內容文字格式 */
            stylecolumn = workbook.createCellStyle();
            stylecolumn.setBorderBottom((short) 1);
            stylecolumn.setBorderTop((short) 1);
            stylecolumn.setBorderLeft((short) 1);
            stylecolumn.setBorderRight((short) 1);
            
            /* 設定範例資料格式 */
            Font exampleFont = workbook.createFont();
            exampleFont.setColor(HSSFColor.GREY_50_PERCENT.index);
            styleExamplecolumn = workbook.createCellStyle();
            styleExamplecolumn.setFont(exampleFont);
            styleExamplecolumn.setFillForegroundColor(HSSFColor.WHITE.index); // 設定顏色           
            styleExamplecolumn.setAlignment(HSSFCellStyle.ALIGN_CENTER);      // 水平置中 
            styleExamplecolumn.setBorderBottom((short) 1);
            styleExamplecolumn.setBorderTop((short) 1);
            styleExamplecolumn.setBorderLeft((short) 1);
            styleExamplecolumn.setBorderRight((short) 1);
            
            // 第一層for:有幾張sheet(Map)
            // 第二層for:有幾列(ArrayList)
            // 第三層for:有幾行
            int sheetNum = 0;
            for (Object key : sheetNameMap.keySet()) {
                SheetName = key.toString();
                sheet = workbook.createSheet(SheetName);
                sheetDataList = sheetNameMap.get(key);
                
                for(int r = 0; r<sheetDataList.size();r++) {
                    row = sheet.createRow(r); 
                    rowList = sheetDataList.get(r);
                    
                    for(int c = 0;c<rowList.size();c++) {
                        cell = row.createCell(c); 
                        cell.setCellValue(rowList.get(c));  
                        if(r==0) {
                            cell.setCellStyle(styleheader);
                        } else {
                            if(sheetNum==0) {
                                if (isEmptySheet) {
                                    cell.setCellStyle(styleExamplecolumn);
                                } else {
                                    cell.setCellStyle(stylecolumn);
                                }
                            } else {
                                cell.setCellStyle(stylecolumn);                               
                            }
                        }
                    }
                }
                sheetNum++;
            }
            // 設定標題列鎖定
            sheet.createFreezePane(0, 1, 0, 1);
            
            /******************** 產生Sheet內容結束 ********************/
            out = response.getOutputStream();
            workbook.write(out);
            workbook.close();
            
		} catch(Exception ex) {
			logger.error(ex);
		} finally {
            try {
                if (out != null) {
                    out.flush();
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
	}
	
	private String doPostImportExcel(HttpServletRequest request, HttpServletResponse response, String sch_code) throws Exception {
		String         result   = null;
		File           tmpDir   = null;
		Integer        sbj_year = null;
		Character      sbj_sem  = null;
        List<FileItem> items    = null;
		
        if (!ServletFileUpload.isMultipartContent(request)) {
            // 若非多媒體則終止上傳
            result = "Error: 表單必須包含enctype=multipart/form-data";
        } else {
        	try {
            	// 使用POI拆解excel
            	ParseXLSUtil excel = new ParseXLSUtil();
            	
            	FileItem ExcelItem = null;
                // Create a factory for disk-based file items
                DiskFileItemFactory factory = new DiskFileItemFactory();
            	
                // Set factory constraints
                factory.setSizeThreshold(1024*1024);  // 限制緩衝區大小
                factory.setRepository(tmpDir);        // 檔案超過threshold大小的的暫存目錄
                
                ServletFileUpload fileUpload = new ServletFileUpload(factory);
                items = (List<FileItem>)fileUpload.parseRequest(request);
                
                Iterator<FileItem> iter = items.iterator();
                while (iter.hasNext()) {
                    FileItem item = (FileItem) iter.next();
                    if (!item.isFormField()) {
                        // 檢查副檔名
                    	result = FileUtil.checkFileExtName(item.getName(), ".xls");
                        ExcelItem = item;
                    } else {
                    	if (item.getFieldName().equals("sbjYear")) {
                    		sbj_year = Integer.parseInt(item.getString());
                    	} else {
                    		sbj_sem = StringUtils.toCharacter(item.getString());
                    	}
                    }
                    //System.out.println(item.getFieldName());
                    //logger.debug("ExcelItem:" + ExcelItem);
                }
                
                if (result == null || result.length() == 0) {
                	@SuppressWarnings("unchecked")
					ArrayList<ClubMember> list = excel.parseData(ExcelItem, sch_code, sbj_year, sbj_sem);
                	boolean b = clubMemberService.insert(sch_code, list);
                	if (!b) result = "上傳失敗";
                }
                
            } catch(IllegalArgumentException ex) {
            	logger.error(ex);
            	result = "上傳失敗，請檢查資料內容或格式是否正確";
            } catch(Exception ex) {
            	logger.error(ex);
            }
        }
        
        return result;
	}
}