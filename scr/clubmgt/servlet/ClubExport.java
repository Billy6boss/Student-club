package ntpc.ccai.clubmgt.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import ntpc.ccai.bean.DBCon;
import ntpc.ccai.bean.UserData;
import ntpc.ccai.clubmgt.bean.*;
import ntpc.ccai.clubmgt.service.ClubMemberCadreService;
import ntpc.ccai.clubmgt.service.ClubMemberService;
import ntpc.ccai.clubmgt.service.ClubRecordService;
import ntpc.ccai.clubmgt.service.ClubSelectionService;
import ntpc.ccai.clubmgt.service.ClubSemService;
import ntpc.ccai.clubmgt.service.ClubTeachingService;
import ntpc.ccai.clubmgt.service.StuClassService;
import ntpc.ccai.clubmgt.util.StringUtils;
import ntpc.ccai.util.SystemUtil;

/**
 * Servlet implementation class ClubExport
 */
public class ClubExport extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Logger        logger = Logger.getLogger(this.getClass());  // Log4j
	private String          INIT = "/WEB-INF/jsp/ClubMgt/Clubpdf.jsp"; // 社團管理PDF首頁
	private String         ERROR = "/WEB-INF/jsp/ErrorPage.jsp";       // 失敗的view
	private String stu_photoPath = "/WEB-INF/stu_photo/";              // 照片存放路徑

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String error_msg = ""; // 錯誤訊息
		String targetURL = "";

		HttpSession session = request.getSession();          // 取得session
		UserData ud = (UserData) session.getAttribute("ud"); // 取得session中的物件

		// Step 1: 權限檢查
		if (ud == null) {
			// 尚未登入
			error_msg = "連線逾期，無法順利建立資料，請重新登入";
			request.setAttribute("error_msg", error_msg);
			targetURL = ERROR;
			SystemUtil.forward(targetURL, request, response);
			return;
		} else {
			if (!ud.getRole_code().equals("sta")) { // sta承辦人
				// 沒有使用權限
				error_msg = "您沒有使用權限";
				request.setAttribute("error_msg", error_msg); // 錯誤訊息
				targetURL = ERROR;
				SystemUtil.forward(targetURL, request, response);
				return;
			}
		}
		// Step 2: doGet or doPost
		super.service(request, response);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SystemUtil.forward(this.INIT, request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int result = 0;
		String error_msg  = "";  // 錯誤訊息
		String targetURL  = "";  // forward view
		String print_page = "1"; // 從第一頁開始
		String pagetype   = "s"; // 預設格式
		String print_type = "";
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		DateFormat datetimeFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		
		String print_date = dateFormat.format(date);
		String paget_datatime = datetimeFormat.format(date);
		
		request.setCharacterEncoding("UTF-8");                    // Ajax need
		String exportClass = request.getParameter("exportClass"); // 操作類別
//		System.out.println("output: " + exportClass);
		
		try {
			if (exportClass == null || exportClass.length() <= 0) {
				// 無操作別
				error_msg = "<script>alert('請指定操作類別');</script>";
				request.setAttribute("error_msg", error_msg); // 錯誤訊息
				
			} else if ("query".equals(exportClass.substring(0, 5))) {
				// --------------------------JSON DATA--------------------------------------
				response.setCharacterEncoding("UTF-8");
				response.setContentType("application/json");
				PrintWriter out = response.getWriter();
				
				// 查詢社團學年
				if (exportClass.equals("queryClubYear")) {
					print_type = this.doQueryClubYear(request);
					
				// 查詢社團
				} else if (exportClass.equals("queryClub")) {
					print_type = this.doQueryClub(request);
					
				// 查詢社團成員list
				} else if (exportClass.equals("queryClubMember")) {
					print_type = this.doQueryClubMember(request);
					
				// 查詢社團幹部
				} else if (exportClass.equals("queryClubCader")) {
					print_type = this.doQueryClubCader(request);
					
				// 查詢社團學生幹部經歷
				} else if (exportClass.equals("queryClubCaderExperience")) {
					print_type = this.doQueryClubCaderExperience(request);
					
				// 查詢班級社團成員
				} else if (exportClass.equals("queryClassClub")) {
					print_type = this.doQueryClassClub(request);
					
				// 查詢班級社團成員
				} else if (exportClass.equals("queryClassClubTable")) {
					print_type = this.doQueryClassClubTable(request);
					
				// 查詢線上選社團
				} else if (exportClass.equals("queryClubSelectionTable")) {
					print_type = this.doQueryClubSelectionTable(request);
					
				// 查詢線上選社團
				} else if (exportClass.equals("queryClubMemberScore")) {
					print_type = this.doQueryClubMemberScore(request);
					
				// 查詢線上成績
				} else if (exportClass.equals("queryClubRecord")) {
					print_type = this.doQueryClubRecord(request);
					
				// 教師聘書
				} else if (exportClass.equals("queryClubTeacher")) {
					print_type = this.doQueryClubTeacher(request);
					
				// 績優成績
				} else if (exportClass.equals("queryClubMemberScoreBest")) {
					print_type = this.doQueryClubMemberScoreBest(request);
					
				}
				out.print(print_type);
				out.flush();
				return;
				
			} else {
				// ------------------------------ 以下為報表列印 --------------------------------------
				// 社團成員名單
				if (exportClass.equals("ClubPDF1")) {
					result = this.ClubPDF1(print_page, print_date, pagetype, request, response);
					
				// 社團幹部名單
				} else if (exportClass.equals("ClubPDF2")) {
					result = this.ClubPDF2(print_page, paget_datatime, pagetype, request, response);
					
				// 社團幹部經歷證明
				} else if (exportClass.equals("ClubPDF3")) {
					result = this.ClubPDF3(print_page, paget_datatime, pagetype, request, response);
					
				// 社團點名單
				} else if (exportClass.equals("ClubPDF4")) {
					result = this.ClubPDF4(print_page, paget_datatime, pagetype, request, response);
					
				// 班級參加社團名單
				} else if (exportClass.equals("ClubPDF5")) {
					result = this.ClubPDF5(print_page, paget_datatime, pagetype, request, response);
					
				// 線上選課一覽表
				} else if (exportClass.equals("ClubPDF6")) {
					result = this.ClubPDF6(print_page, paget_datatime, pagetype, request, response);
					
				// 線上成績一覽表
				} else if (exportClass.equals("ClubPDF7")) {
					result = this.ClubPDF7(print_page, paget_datatime, pagetype, request, response);
					
				// 社團服務時數一覽表
				} else if (exportClass.equals("ClubPDF8")) {
					result = this.ClubPDF8(print_page, paget_datatime, pagetype, request, response);
					
				// 社團教師聘書
				} else if (exportClass.equals("ClubPDF9")) {
					result = this.ClubPDF9(print_page, paget_datatime, pagetype, request, response);
					
				// 社團學生證書
				} else if (exportClass.equals("ClubPDF10")) {
					result = this.ClubPDF10(print_page, paget_datatime, pagetype, request, response);
					
				// 社團學生證明書
				} else if (exportClass.equals("ClubPDF11")) {
					result = this.ClubPDF11(print_page, paget_datatime, pagetype, request, response);
				}
				
				if (result == 0) {
					error_msg += "產生PDF回傳值錯誤";
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		// set error_msg、 forward to
		if (error_msg.length() > 0) {
			request.setAttribute("error_msg", error_msg);
			targetURL = this.ERROR;
			SystemUtil.forward(targetURL, request, response);
		}
	}

	// queryClubYear: 查詢社團學年 (list)
	private String doQueryClubYear(HttpServletRequest request) throws ServletException, IOException {
		String error_msg = ""; // 錯誤訊息
		String sch_code  = "";
		
		ClubSemService css = new ClubSemService();
		try {
			UserData ud = new UserData();
			ud = (UserData) request.getSession().getAttribute("ud"); // 取得session中的物件
			sch_code = ud.getSch_code();
			
			return new Gson().toJson(css.getClubYearList(sch_code), new TypeToken<List<ClubSem>>() { }.getType());

		} catch (Exception ex) {
			logger.error("查詢[社團學年]資料發生錯誤!", ex);
			error_msg = "<script>alert('查詢[社團學年]資料發生錯誤，請聯絡系統管理人員!');</script>";
			request.setAttribute("error_msg", error_msg); // 錯誤訊息
			return ERROR;
		}
	}

	// queryClub: 查詢社團 (list)
	private String doQueryClub(HttpServletRequest request) throws ServletException, IOException {
		String    error_msg = ""; // 錯誤訊息
		String    sch_code  = "";
		int       sbj_year  = Integer.valueOf(request.getParameter("sbj_year"));        // 學年
		Character sbj_sem   = StringUtils.toCharacter(request.getParameter("sbj_sem")); // 學期
		
		ClubSemService css = new ClubSemService();
		try {
			UserData ud = new UserData();
			ud = (UserData) request.getSession().getAttribute("ud"); // 取得session中的物件
			sch_code = ud.getSch_code();
			
			return new Gson().toJson(css.getClubDataList(sch_code, sbj_year, sbj_sem), new TypeToken<List<ClubSem>>() { }.getType());

		} catch (Exception ex) {
			logger.error("查詢[社團]資料發生錯誤!", ex);
			error_msg = "<script>alert('查詢[社團]資料發生錯誤，請聯絡系統管理人員!');</script>";
			request.setAttribute("error_msg", error_msg); // 錯誤訊息
			return ERROR;
		}
	}

	// queryClubMember: 查詢社團成員 (list)
	private String doQueryClubMember(HttpServletRequest request) throws ServletException, IOException {
		String    error_msg = ""; // 錯誤訊息
		String    sch_code  = "";
		int       sbj_year  = Integer.valueOf(request.getParameter("sbj_year"));        // 學年
		Character sbj_sem   = StringUtils.toCharacter(request.getParameter("sbj_sem")); // 學期
		int       club      = Integer.valueOf(request.getParameter("club"));            // 社團號
		
		ClubMemberService cms = new ClubMemberService();
		try {
			UserData ud = new UserData();
			ud = (UserData) request.getSession().getAttribute("ud"); // 取得session中的物件
			sch_code = ud.getSch_code();
			
			return new Gson().toJson(
					cms.getClubMembersByPDFClubNumAndYearAndSem(sch_code, club, sbj_year, sbj_sem), new TypeToken<List<ClubMember>>() {	}.getType());

		} catch (Exception ex) {
			logger.error("查詢[社團成員]資料發生錯誤!", ex);
			error_msg = "<script>alert('查詢[社團成員]資料發生錯誤，請聯絡系統管理人員!');</script>";
			request.setAttribute("error_msg", error_msg); // 錯誤訊息
			return ERROR;
		}
	}

	// queryClubCader: 查詢社團幹部 (table)
	private String doQueryClubCader(HttpServletRequest request) throws ServletException, IOException {
		String    error_msg = ""; // 錯誤訊息
		String    sch_code  = "";
		int       sbj_year  = Integer.valueOf(request.getParameter("sbj_year"));        // 學年
		Character sbj_sem   = StringUtils.toCharacter(request.getParameter("sbj_sem")); // 學期
		int       club      = Integer.valueOf(request.getParameter("club"));            // 社團號
		
		ClubMemberService cms = new ClubMemberService();
		try {
			UserData ud = new UserData();
			ud = (UserData) request.getSession().getAttribute("ud"); // 取得session中的物件
			sch_code = ud.getSch_code();
			
			return new Gson().toJson(
					cms.getClubCadersByPDFClubNumAndYearAndSem(sch_code, club, sbj_year, sbj_sem), new TypeToken<List<ClubMember>>() { }.getType());

		} catch (Exception ex) {
			logger.error("查詢[社團幹部]資料發生錯誤!", ex);
			error_msg = "<script>alert('查詢[社團幹部]資料發生錯誤，請聯絡系統管理人員!');</script>";
			request.setAttribute("error_msg", error_msg); // 錯誤訊息
			return ERROR;
		}
	}

	// queryClassClub: 查詢班級社團 (list)
	private String doQueryClassClub(HttpServletRequest request) throws ServletException, IOException {
		String    error_msg = ""; // 錯誤訊息
		String    sch_code  = "";
		int       sbj_year  = Integer.valueOf(request.getParameter("sbj_year"));
		Character sbj_sem   = StringUtils.toCharacter(request.getParameter("sbj_sem"));
		Character grade     = StringUtils.toCharacter(request.getParameter("grade"));
		
		StuClassService scs = new StuClassService();
		try {
			UserData ud = new UserData();
			ud = (UserData) request.getSession().getAttribute("ud"); // 取得session中的物件
			sch_code = ud.getSch_code();
			
			return new Gson().toJson(
					scs.getStuClassesByYearAndSemAndGrade(sch_code, sbj_year, sbj_sem, grade), new TypeToken<List<StuClass>>() { }.getType());
			
		} catch (Exception ex) {
			logger.error("查詢[班級社團]資料發生錯誤!", ex);
			error_msg = "<script>alert('查詢[班級社團]資料發生錯誤，請聯絡系統管理人員!');</script>";
			request.setAttribute("error_msg", error_msg); // 錯誤訊息
			return ERROR;
		}
	}

	// queryClassClubTable: 查詢班級社團 (table)
	private String doQueryClassClubTable(HttpServletRequest request) throws ServletException, IOException {
		String    error_msg = ""; // 錯誤訊息
		String    sch_code  = "";
		int       sbj_year  = Integer.valueOf(request.getParameter("sbj_year"));
		Character sbj_sem   = StringUtils.toCharacter(request.getParameter("sbj_sem"));
		String    cls_class = request.getParameter("cls_class");
		
		StuClassService scs = new StuClassService();
		try {
			UserData ud = new UserData();
			ud = (UserData) request.getSession().getAttribute("ud"); // 取得session中的物件
			sch_code = ud.getSch_code();
			
			return new Gson().toJson(
					scs.getClassClubByYearAndSemAndClsCode(sch_code, sbj_year, sbj_sem, cls_class), new TypeToken<List<StuClass>>() { }.getType());
			
		} catch (Exception ex) {
			logger.error("查詢[班級社團]資料發生錯誤!", ex);
			error_msg = "<script>alert('查詢[班級社團]資料發生錯誤，請聯絡系統管理人員!');</script>";
			request.setAttribute("error_msg", error_msg); // 錯誤訊息
			return ERROR;
		}
	}

	// queryClubCaderExperience: 查詢社團學生幹部經歷 (list)
	private String doQueryClubCaderExperience(HttpServletRequest request) throws ServletException, IOException {
		String error_msg = ""; // 錯誤訊息
		String sch_code  = "";
		String reg_no    = request.getParameter("reg_no");
		String idno      = request.getParameter("idno");
		
		ClubMemberCadreService cmcs = new ClubMemberCadreService();
		try {
			UserData ud = new UserData();
			ud = (UserData) request.getSession().getAttribute("ud"); // 取得session中的物件
			sch_code = ud.getSch_code();
			
			return new Gson().toJson(
					cmcs.getClubMemberCadreExperienceByRegnoOrIdno(sch_code, reg_no, idno),	new TypeToken<List<ClubMemberCadre>>() {
					}.getType());
			
		} catch (Exception ex) {
			logger.error("查詢[社團學生幹部經歷]資料發生錯誤!", ex);
			error_msg = "<script>alert('查詢[社團學生幹部經歷]資料發生錯誤，請聯絡系統管理人員!');</script>";
			request.setAttribute("error_msg", error_msg); // 錯誤訊息
			return ERROR;
		}
	}

	// queryClubSelectionTable: 查詢線上選社團 (table)
	private String doQueryClubSelectionTable(HttpServletRequest request) throws ServletException, IOException {
		String    error_msg = ""; // 錯誤訊息
		String    sch_code  = "";
		int       sbj_year  = Integer.valueOf(request.getParameter("sbj_year"));
		Character sbj_sem   = StringUtils.toCharacter(request.getParameter("sbj_sem"));
		
		ClubSelectionService css = new ClubSelectionService();
		try {
			UserData ud = new UserData();
			ud = (UserData) request.getSession().getAttribute("ud"); // 取得session中的物件
			sch_code = ud.getSch_code();
			
			return new Gson().toJson(
					css.getClubSelectionsOnlineByYearAndSem(sch_code, sbj_year, sbj_sem), new TypeToken<List<ClubSelection>>() {
					}.getType());
			
		} catch (Exception ex) {
			logger.error("查詢[線上選社團]資料發生錯誤!", ex);
			error_msg = "<script>alert('查詢[線上選社團]資料發生錯誤，請聯絡系統管理人員!');</script>";
			request.setAttribute("error_msg", error_msg); // 錯誤訊息
			return ERROR;
		}
	}

	// queryClubMemberScore: 查詢社團成員成績 (table)
	private String doQueryClubMemberScore(HttpServletRequest request) throws ServletException, IOException {
		String error_msg = ""; // 錯誤訊息
		String sch_code  = "";
		int       sbj_year = Integer.valueOf(request.getParameter("sbj_year"));         // 學年
		Character sbj_sem  = StringUtils.toCharacter(request.getParameter("sbj_sem"));  // 學期
		int       club     = Integer.valueOf(request.getParameter("club"));             // 社團號
		
		ClubMemberService cms = new ClubMemberService();
		try {
			UserData ud = new UserData();
			ud = (UserData) request.getSession().getAttribute("ud"); // 取得session中的物件
			sch_code = ud.getSch_code();
			
			return new Gson().toJson(
					cms.getClubMemberScoreByClubNumAndYearAndSem(sch_code, club, sbj_year, sbj_sem), new TypeToken<List<ClubMember>>() {
					}.getType());

		} catch (Exception ex) {
			logger.error("查詢[社團成員成績]資料發生錯誤!", ex);
			error_msg = "<script>alert('查詢[社團成員成績]資料發生錯誤，請聯絡系統管理人員!');</script>";
			request.setAttribute("error_msg", error_msg); // 錯誤訊息
			return ERROR;
		}
	}

	// queryClubRecord: 查詢社團學生服務紀錄 (table)
	private String doQueryClubRecord(HttpServletRequest request) throws ServletException, IOException {
		String error_msg = ""; // 錯誤訊息
		String sch_code  = "";
		String reg_no    = request.getParameter("reg_no");
		String idno      = request.getParameter("idno");

		ClubRecordService cmcs = new ClubRecordService();
		try {
			UserData ud = new UserData();
			ud = (UserData) request.getSession().getAttribute("ud"); // 取得session中的物件
			sch_code = ud.getSch_code();
			
			return new Gson().toJson(
					cmcs.getClubRecordsByRegnoAndIdno(sch_code, reg_no, idno), new TypeToken<List<ClubRecord>>() { }.getType());

		} catch (Exception ex) {
			logger.error("查詢[社團學生服務紀錄]資料發生錯誤!", ex);
			error_msg = "<script>alert('查詢[社團學生服務紀錄]資料發生錯誤，請聯絡系統管理人員!');</script>";
			request.setAttribute("error_msg", error_msg); // 錯誤訊息
			return ERROR;
		}
	}

	// queryClubTeacher: 查詢社團教師聘書 (table)
	private String doQueryClubTeacher(HttpServletRequest request) throws ServletException, IOException {
		String    error_msg  = ""; // 錯誤訊息
		String    sch_code   = "";
		String    staff_code = request.getParameter("staff_code");
		String    idno       = request.getParameter("idno");
		int       sbj_year   = Integer.valueOf(request.getParameter("sbj_year"));
		Character sbj_sem    = StringUtils.toCharacter(request.getParameter("sbj_sem"));
		
		ClubTeachingService cts = new ClubTeachingService();
		try {
			UserData ud = new UserData();
			ud = (UserData) request.getSession().getAttribute("ud"); // 取得session中的物件
			sch_code = ud.getSch_code();
			
			return new Gson().toJson(
					cts.getClubTeachingByStaffcodeAndIdno(sch_code, staff_code, idno, sbj_year, sbj_sem), new TypeToken<List<ClubTeaching>>() {
					}.getType());

		} catch (Exception ex) {
			logger.error("查詢[社團教師聘書]資料發生錯誤!", ex);
			error_msg = "<script>alert('查詢[社團教師聘書]資料發生錯誤，請聯絡系統管理人員!');</script>";
			request.setAttribute("error_msg", error_msg); // 錯誤訊息
			return ERROR;
		}
	}

	// queryClubMemberScoreBest: 查詢社團成績  (table)
	private String doQueryClubMemberScoreBest(HttpServletRequest request) throws ServletException, IOException {
		String    error_msg = ""; // 錯誤訊息
		String    sch_code  = "";
		int       sbj_year  = Integer.valueOf(request.getParameter("sbj_year"));        // 學年
		Character sbj_sem   = StringUtils.toCharacter(request.getParameter("sbj_sem")); // 學期
		int       club      = Integer.valueOf(request.getParameter("club"));            // 社團號

		ClubSemService css = new ClubSemService();
		try {
			UserData ud = new UserData();
			ud = (UserData) request.getSession().getAttribute("ud"); // 取得session中的物件
			sch_code = ud.getSch_code();
			
			return new Gson().toJson(css.getClubMenberScore(sch_code, club, sbj_year, sbj_sem), new TypeToken<List<ClubSem>>() {
					}.getType());

		} catch (Exception ex) {
			logger.error("查詢[社團成績]資料發生錯誤!", ex);
			error_msg = "<script>alert('查詢[社團成績]資料發生錯誤，請聯絡系統管理人員!');</script>";
			request.setAttribute("error_msg", error_msg); // 錯誤訊息
			return ERROR;
		}
	}

	// ClubPDF1: 社團成員名單
	private int ClubPDF1(String print_page, String print_date, String pagetype, HttpServletRequest request, HttpServletResponse response) throws Exception {
		int result = 0;
		int check  = 0;
		ClubExportPDF pdf = new ClubExportPDF();
		List<ClubMember> cmlist = null;

		UserData     ud = (UserData) request.getSession().getAttribute("ud"); // 取得session中的物件
		String sbj_year = request.getParameter("sbj_year");
		String  sbj_sem = request.getParameter("sbj_sem");
		String     club = request.getParameter("club"); // 社團號

		try {
			// Step 1:建立PDF類別，設定字型檔路徑、列印日期和頁碼
			pdf.setFontStyle(request.getRealPath("") + "/WEB-INF/font/kaiu.ttf");
			pdf.setPrint_date(print_date);
			pdf.setPrint_page(print_page);
			pdf.setPageType(pagetype);// set 編碼方式
			
			// Step 2:開啟檔案
			pdf.openDocument(response);
			
			// Step 3:查詢社團資料，並建立表格資料
			pdf.createPageArray(1); // create aray size
			pdf.number = 1; // set default
			for (int i = 0; i < 1; i++) {
				// -----------------------------------開始-----------------------------
				// Step 3-1:查詢
				ClubMemberService cms = new ClubMemberService(); // 社團成員資料

				cmlist = cms.getClubMembersByPDFClubNumAndYearAndSem(ud.getSch_code(), Integer.valueOf(club),
						 Integer.valueOf(sbj_year), StringUtils.toCharacter(sbj_sem));
				pdf.setCmlist(cmlist);
				
				// Step 3-2:產生PDF
				String sch = ud.getSch_cname();
				pdf.setTitle(sch); // 重新設定標題
				pdf.setHeaderString((sbj_sem.equals("1")) ? "上" : "下", sbj_year, "「" + cmlist.get(0).getClub().getClub_name() + "」成員名單 ");
				check = pdf.Generate();
				// -----------------------------------結束-----------------------------
				
				// 2012.09.12判斷是否為中間的系統組，true的話要換頁
				if ((10 > 1) && (i != 10 - 1)) {
					pdf.newDivPage();
				}

				pdf.countPage[i] = pdf.number; // get number & set
				pdf.number = 1; // default 1

				if (check == 0) {
					break;
				} else {
					result = result + check;
				}
			}

			// Step 4:關閉檔案
			pdf.closeDocument();
			
		} catch (Exception e) {
			this.logger.error("產生[社團成員名單PDF]時發生例外錯誤", e);
			return 0;
		}

		// 加 if 判斷 result <>div_code 數量
		if (result > 0) {
			// Step 5:設定開啟檔案名稱及格式
			String filename = sbj_year + "_" + ((sbj_sem.equals("1")) ? "上" : "下") + "學期_" + cmlist.get(0).getClub().getClub_name() + "成員名單.pdf";
			response.setContentType("application/rtf;charset");
			response.setHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes("big5"), "ISO8859-1"));
			ServletOutputStream sout = response.getOutputStream();
			pdf.getBuffer().writeTo(sout);
			sout.flush();
			sout.close();
			result = 1;
		} else {
			result = 0;
		}
		
		return 1;
	}
	
	// ClubPDF2: 社團幹部名單
	private int ClubPDF2(String print_page, String print_date, String pagetype, HttpServletRequest request, HttpServletResponse response) throws Exception {
		int result = 0;
		int check  = 0;
		ClubExportPDF2 pdf = new ClubExportPDF2();
		List<ClubMember> cmlist = null;
		
		UserData     ud = (UserData) request.getSession().getAttribute("ud"); // 取得session中的物件
		String sbj_year = request.getParameter("sbj_year");
		String  sbj_sem = request.getParameter("sbj_sem");
		String     club = request.getParameter("club");

		try {
			// Step 1:建立PDF類別，設定字型檔路徑、列印日期和頁碼
			pdf.setFontStyle(request.getRealPath("") + "/WEB-INF/font/kaiu.ttf");
			pdf.setPrint_date(print_date);
			pdf.setPrint_page(print_page);
			pdf.setPageType(pagetype); // set 編碼方式
			
			// Step 2:開啟檔案
			pdf.openDocument(response);
			
			// Step 3:逐筆查詢各系所(組)課程科目資料，並建立表格資料
			pdf.createPageArray(1); // create aray size
			pdf.number = 1; // set default
			for (int i = 0; i < 1; i++) {
				// -----------------------------------開始-----------------------------
				// Step 3-1:查詢
				ClubMemberService cms = new ClubMemberService(); // 社團成員資料

				cmlist = cms.getClubCadersByPDFClubNumAndYearAndSem(ud.getSch_code(), Integer.valueOf(club),
						Integer.valueOf(sbj_year), StringUtils.toCharacter(sbj_sem));
				pdf.setCmlist(cmlist);

				// Step 3-2:產生PDF
				String sch = ud.getSch_cname();
				pdf.setTitle(sch); // 重新設定標題
				pdf.setHeaderString((sbj_sem.equals("1")) ? "上" : "下", sbj_year, "「" + cmlist.get(0).getClub().getClub_name() + "」社團幹部名單 ");
				check = pdf.Generate();
				// -----------------------------------結束-----------------------------

				// 2012.09.12判斷是否為中間的系統組，true的話要換頁
				if ((10 > 1) && (i != 10 - 1)) {
					pdf.newDivPage();
				}

				pdf.countPage[i] = pdf.number; // get number & set
				pdf.number = 1; // default 1

				if (check == 0) {
					break;
				} else {
					result = result + check;
				}
			}

			// Step 4:關閉檔案
			pdf.closeDocument();
			
		} catch (Exception e) {
			this.logger.error("產生[社團幹部名單PDF]時發生例外錯錯誤", e);
			return 0;
		}

		// 加 if 判斷 result <>div_code 數量
		if (result > 0) {
			// Step 5：設定開啟檔案名稱及格式
			String filename = sbj_year + "_" + ((sbj_sem.equals("1")) ? "上" : "下") + "學期_" + cmlist.get(0).getClub().getClub_name() + "幹部名單.pdf";
			response.setContentType("application/rtf;charset");
			response.setHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes("big5"), "ISO8859-1"));
			ServletOutputStream sout = response.getOutputStream();
			pdf.getBuffer().writeTo(sout);
			sout.flush();
			sout.close();
			result = 1;
		} else {
			result = 0;
		}
		
		return 1;
	}

	// ClubPDF3: 社團幹部經歷證明
	private int ClubPDF3(String print_page, String print_date, String pagetype, HttpServletRequest request, HttpServletResponse response) throws Exception {
		int result = 0;
		int check  = 0;
		ClubExportPDF3 pdf = new ClubExportPDF3();
		List<ClubMemberCadre> cmclist = null;
		
		UserData   ud = (UserData) request.getSession().getAttribute("ud"); // 取得session中的物件
		String reg_no = request.getParameter("reg_no");
		String   idno = request.getParameter("idno");

		try {
			// Step 1:建立PDF類別，設定字型檔路徑、列印日期和頁碼
			pdf.setFontStyle(request.getRealPath("") + "/WEB-INF/font/kaiu.ttf");
			pdf.setPrint_date(print_date);
			pdf.setPrint_page(print_page);
			pdf.setPageType(pagetype); // set 編碼方式
			
			// Step 2:開啟檔案
			pdf.openDocument(response);
			
			// Step 3:逐筆查詢各系所(組)課程科目資料，並建立表格資料
			pdf.createPageArray(1); // create aray size
			pdf.number = 1; // set default
			for (int i = 0; i < 1; i++) {
				// -----------------------------------開始-----------------------------
				// Step 3-1:查詢
				ClubMemberCadreService cmcs = new ClubMemberCadreService();
				cmclist = cmcs.getClubMemberCadreExperienceByRegnoOrIdno(ud.getSch_code(), reg_no, idno);
				String imgUrl = request.getServletContext().getRealPath("/").replace("StuClub", "StuData") + stu_photoPath + ud.getSch_code() + "/" + cmclist.get(0).getStuBasis().getRgno() + ".jpg";

				pdf.setCmclist(cmclist);
				pdf.setStuimgurl(imgUrl);

				// Step 2：產生PDF
				String sch = ud.getSch_cname();
				pdf.setTitle(sch); // 重新設定標題
				
				if (cmclist.size() > 0) {
					pdf.setHeaderString("", cmclist.get(0).getStuBasis().getCname(), "社團經歷   社團幹部證明 ");
				} else {
					pdf.setHeaderString("", "", "社團經歷   社團幹部名單 ");
				}
				check = pdf.Generate();
				// -----------------------------------結束-----------------------------

				// 2012.09.12判斷是否為中間的系統組，true的話要換頁
				if ((10 > 1) && (i != 10 - 1)) {
					pdf.newDivPage();
				}

				pdf.countPage[i] = pdf.number;// get number & set
				pdf.number = 1;// default 1

				if (check == 0) {
					break;
				} else {
					result = result + check;
				}
			}

			// Step 4:關閉檔案
			pdf.closeDocument();
			
		} catch (Exception e) {
			this.logger.error("產生[社團幹部經歷證明PDF]時發生例外錯誤", e);
			return 0;
		}

		// 加 if 判斷 result <>div_code 數量
		if (result > 0) {
			// Step 5：設定開啟檔案名稱及格式
			String filename = cmclist.get(0).getStuBasis().getCname() + "_社團幹部經歷證明.pdf";
			response.setContentType("application/rtf;charset");
			response.setHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes("big5"), "ISO8859-1"));
			ServletOutputStream sout = response.getOutputStream();
			pdf.getBuffer().writeTo(sout);
			sout.flush();
			sout.close();
			result = 1;
		} else {
			result = 0;
		}
		
		return 1;
	}

	// ClubPDF4: 社團點名單
	private int ClubPDF4(String print_page, String print_date, String pagetype, HttpServletRequest request, HttpServletResponse response) throws Exception {
		int result = 0;
		int check  = 0;
		ClubExportPDF4 pdf = new ClubExportPDF4();
		List<ClubMember> cmlist = null;

		UserData     ud = (UserData) request.getSession().getAttribute("ud"); // 取得session中的物件
		String sbj_year = request.getParameter("sbj_year");
		String  sbj_sem = request.getParameter("sbj_sem");
		String     club = request.getParameter("club"); // 社團號
		
		try {
			// Step 1:建立PDF類別，設定字型檔路徑、列印日期和頁碼
			pdf.setFontStyle(request.getRealPath("") + "/WEB-INF/font/kaiu.ttf");
			pdf.setPrint_date(print_date);
			pdf.setPrint_page(print_page);
			pdf.setPageType(pagetype); // set 編碼方式
			
			// Step 2:開啟檔案
			pdf.openDocument(response);
			
			// Step 3:逐筆查詢各系所(組)課程科目資料，並建立表格資料
			pdf.createPageArray(1); // create aray size
			pdf.number = 1; // set default
			for (int i = 0; i < 1; i++) {
				check = 0;
				// -----------------------------------開始-----------------------------
				// Step 3-1:查詢
				ClubMemberService cms = new ClubMemberService(); // 社團成員資料
				cmlist = cms.getClubMembersByPDFClubNumAndYearAndSem(ud.getSch_code(), Integer.valueOf(club), Integer.valueOf(sbj_year), StringUtils.toCharacter(sbj_sem));
				pdf.setCmlist(cmlist);

				// Step 3-2:產生PDF
				String sch = ud.getSch_cname();
				pdf.setTitle(sch); // 重新設定標題
				pdf.setHeaderString((sbj_sem.equals("1")) ? "上學期" : "下學期", sbj_year, "「" + cmlist.get(0).getClub().getClub_name() + "」_社團點名單 ");
				check = pdf.Generate();
				// -----------------------------------結束-----------------------------

				// 2012.09.12判斷是否為中間的系統組，true的話要換頁
				if ((10 > 1) && (i != 10 - 1)) {
					pdf.newDivPage();
				}

				pdf.countPage[i] = pdf.number;// get number & set
				pdf.number = 1;// default 1

				if (check == 0) {
					break;
				} else {
					result = result + check;
				}
			}

			// Step 4:關閉檔案
			pdf.closeDocument();
			
		} catch (Exception e) {
			this.logger.error("產生[社團點名單PDF]時發生例外錯誤", e);
			return 0;
		}

		// 加 if 判斷 result <>div_code 數量
		if (result > 0) {
			// Step 5：設定開啟檔案名稱及格式
			String filename = "「" + cmlist.get(0).getClub().getClub_name() + "」_社團點名單.pdf";
			response.setContentType("application/rtf;charset");
			response.setHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes("big5"), "ISO8859-1"));
			ServletOutputStream sout = response.getOutputStream();
			pdf.getBuffer().writeTo(sout);
			sout.flush();
			sout.close();
			result = 1;
		} else {
			result = 0;
		}
		return 1;
	}

	// ClubPDF5: 班級參加社團名單
	private int ClubPDF5(String print_page, String print_date, String pagetype, HttpServletRequest request, HttpServletResponse response) throws Exception {
		int result = 0;
		int check  = 0;
		ClubExportPDF5 pdf = new ClubExportPDF5();
		List<StuClass> sclist = null;

		UserData      ud = (UserData) request.getSession().getAttribute("ud"); // 取得session中的物件
		String  sbj_year = request.getParameter("sbj_year");
		String   sbj_sem = request.getParameter("sbj_sem");
		String cls_class = request.getParameter("cls_class"); // 社團號
		
		try {
			// Step 1:建立PDF類別，設定字型檔路徑、列印日期和頁碼
			pdf.setFontStyle(request.getRealPath("") + "/WEB-INF/font/kaiu.ttf");
			pdf.setPrint_date(print_date);
			pdf.setPrint_page(print_page);
			pdf.setPageType(pagetype); // set 編碼方式
			// Step 2:開啟檔案
			pdf.openDocument(response);
			// Step 3:逐筆查詢各系所(組)課程科目資料，並建立表格資料
			pdf.createPageArray(1); // create aray size
			pdf.number = 1; // set default
			for (int i = 0; i < 1; i++) {
				// -----------------------------------開始-----------------------------
				// Step 3-1:查詢
				StuClassService scs = new StuClassService();
				sclist = scs.getClassClubByYearAndSemAndClsCode(ud.getSch_code(), Integer.valueOf(sbj_year), StringUtils.toCharacter(sbj_sem), cls_class);
				pdf.setSclist(sclist);

				// Step 3-2:產生PDF
				String sch = ud.getSch_cname();
				pdf.setTitle(sch); // 重新設定標題
				pdf.setHeaderString(sbj_year + "學年度", (sbj_sem.equals("1")) ? "上學期" : "下學期", sclist.get(0).getCls_cname(), "參加社團名單 ");
				check = pdf.Generate();
				// -----------------------------------結束-----------------------------

				// 2012.09.12判斷是否為中間的系統組，true的話要換頁
				if ((10 > 1) && (i != 10 - 1)) {
					pdf.newDivPage();
				}

				pdf.countPage[i] = pdf.number;// get number & set
				pdf.number = 1;// default 1

				if (check == 0) {
					break;
				} else {
					result = result + check;
				}
			}

			// Step 4:關閉檔案
			pdf.closeDocument();
			
		} catch (Exception e) {
			this.logger.error("產生[班級參加社團名單PDF]時發生例外錯誤", e);
			return 0;
		}

		// 加 if 判斷 result <>div_code 數量
		if (result > 0) {
			// Step 5：設定開啟檔案名稱及格式
			String filename = sclist.get(0).getCls_cname() + "參加社團名單.pdf";
			response.setContentType("application/rtf;charset");
			response.setHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes("big5"), "ISO8859-1"));
			ServletOutputStream sout = response.getOutputStream();
			pdf.getBuffer().writeTo(sout);
			sout.flush();
			sout.close();
			result = 1;
		} else {
			result = 0;
		}

		return 1;
	}

	// ClubPDF6: 線上選課一覽表
	private int ClubPDF6(String print_page, String print_date, String pagetype, HttpServletRequest request, HttpServletResponse response) throws Exception {
		int result = 0;
		int check = 0;
		ClubExportPDF6 pdf = new ClubExportPDF6();
		List<ClubSelection> cslist = null;

		UserData     ud = (UserData) request.getSession().getAttribute("ud"); // 取得session中的物件
		String sbj_year = request.getParameter("sbj_year");
		String  sbj_sem = request.getParameter("sbj_sem");
		
		try {
			// Step 1:建立PDF類別，設定字型檔路徑、列印日期和頁碼
			pdf.setFontStyle(request.getRealPath("") + "/WEB-INF/font/kaiu.ttf");
			pdf.setPrint_date(print_date);
			pdf.setPrint_page(print_page);
			pdf.setPageType(pagetype);// set 編碼方式
			
			// Step 2:開啟檔案
			pdf.openDocument(response);
			
			// Step 3:逐筆查詢各系所(組)課程科目資料，並建立表格資料
			pdf.createPageArray(1); // create aray size
			pdf.number = 1; // set default
			for (int i = 0; i < 1; i++) {
				// -----------------------------------開始-----------------------------
				// Step 3-1:查詢
				ClubSelectionService ccs = new ClubSelectionService();
				cslist = ccs.getClubSelectionsOnlineByYearAndSem(ud.getSch_code(), Integer.valueOf(sbj_year), StringUtils.toCharacter(sbj_sem));
				pdf.setCslist(cslist);

				// Step 3-2:產生PDF
				String sch = ud.getSch_cname();
				pdf.setTitle(sch); // 重新設定標題
				pdf.setHeaderString(sbj_year + "學年度", (sbj_sem.equals("1")) ? "上學期" : "下學期", "線上選課一覽表 ");
				check = pdf.Generate();
				// -----------------------------------結束-----------------------------

				// 2012.09.12判斷是否為中間的系統組，true的話要換頁
				if ((10 > 1) && (i != 10 - 1)) {
					pdf.newDivPage();
				}

				pdf.countPage[i] = pdf.number;// get number & set
				pdf.number = 1;// default 1

				if (check == 0) {
					break;
				} else {
					result = result + check;
				}
			}

			// Step 4:關閉檔案
			pdf.closeDocument();
			
		} catch (Exception e) {
			this.logger.error("產生[線上選課一覽表PDF]時發生例外錯誤", e);
			return 0;
		}

		// 加 if 判斷 result <>div_code 數量
		if (result > 0) {
			// Step 5：設定開啟檔案名稱及格式
			String filename = sbj_year + "學年度" + ((sbj_sem.equals("1")) ? "上學期" : "下學期") + "線上選課一覽表.pdf";
			response.setContentType("application/rtf;charset");
			response.setHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes("big5"), "ISO8859-1"));
			ServletOutputStream sout = response.getOutputStream();
			pdf.getBuffer().writeTo(sout);
			sout.flush();
			sout.close();
			result = 1;
		} else {
			result = 0;
		}

		return 1;
	}

	// ClubPDF7: 社團成績一覽表
	private int ClubPDF7(String print_page, String print_date, String pagetype, HttpServletRequest request, HttpServletResponse response) throws Exception {
		int result = 0;
		int check  = 0;
		ClubExportPDF7 pdf = new ClubExportPDF7();
		List<ClubMember> cmlist = null;

		UserData     ud = (UserData) request.getSession().getAttribute("ud"); // 取得session中的物件
		String sbj_year = request.getParameter("sbj_year");
		String  sbj_sem = request.getParameter("sbj_sem");
		String     club = request.getParameter("club"); // 社團號
		
		try {
			// Step 1:建立PDF類別，設定字型檔路徑、列印日期和頁碼
			pdf.setFontStyle(request.getRealPath("") + "/WEB-INF/font/kaiu.ttf");
			pdf.setPrint_date(print_date);
			pdf.setPrint_page(print_page);
			pdf.setPageType(pagetype); // set 編碼方式
			
			// Step 2:開啟檔案
			pdf.openDocument(response);
			
			// Step 3:逐筆查詢各系所(組)課程科目資料，並建立表格資料
			pdf.createPageArray(1); // create aray size
			pdf.number = 1; // set default
			for (int i = 0; i < 1; i++) {
				// -----------------------------------開始-----------------------------
				// Step 3-1:查詢
				ClubMemberService cms = new ClubMemberService(); // 社團成員資料
				cmlist = cms.getClubMemberScoreByClubNumAndYearAndSem(ud.getSch_code(), Integer.valueOf(club), Integer.valueOf(sbj_year), StringUtils.toCharacter(sbj_sem));
				pdf.setCmlist(cmlist);

				// Step 3-2:產生PDF
				String sch = ud.getSch_cname();
				pdf.setTitle(sch); // 重新設定標題
				pdf.setHeaderString(sbj_year + "學年度", (sbj_sem.equals("1")) ? "上學期" : "下學期", "「 " + cmlist.get(0).getClub().getClub_name() + "」 學生成績一覽表");
				check = pdf.Generate();
				// -----------------------------------結束-----------------------------

				// 2012.09.12判斷是否為中間的系統組，true的話要換頁
				if ((10 > 1) && (i != 10 - 1)) {
					pdf.newDivPage();
				}

				pdf.countPage[i] = pdf.number;// get number & set
				pdf.number = 1;// default 1

				if (check == 0) {
					break;
				} else {
					result = result + check;
				}
			}

			// Step 4:關閉檔案
			pdf.closeDocument();
			
		} catch (Exception e) {
			this.logger.error("產生[社團成績一覽表PDF]時發生例外錯誤", e);
			return 0;
		}

		// 加 if 判斷 result <>div_code 數量
		if (result > 0) {
			// Step 5：設定開啟檔案名稱及格式
			String filename = sbj_year + "學年度" + ((sbj_sem.equals("1")) ? "上學期" : "下學期") + "線上成績一覽表.pdf";
			response.setContentType("application/rtf;charset");
			response.setHeader("Content-Disposition",
					"attachment;filename=" + new String(filename.getBytes("big5"), "ISO8859-1"));
			ServletOutputStream sout = response.getOutputStream();
			pdf.getBuffer().writeTo(sout);
			sout.flush();
			sout.close();
			result = 1;
		} else {
			result = 0;
		}

		return 1;
	}

	// ClubPDF8: 社團服務時數一覽表
	private int ClubPDF8(String print_page, String print_date, String pagetype, HttpServletRequest request, HttpServletResponse response) throws Exception {
		int result = 0;
		int check  = 0;
		ClubExportPDF8 pdf = new ClubExportPDF8();
		List<ClubRecord> crlist = null;

		UserData   ud = (UserData) request.getSession().getAttribute("ud"); // 取得session中的物件
		String reg_no = request.getParameter("reg_no");
		String   idno = request.getParameter("idno");

		try {
			// Step 1:建立PDF類別，設定字型檔路徑、列印日期和頁碼
			pdf.setFontStyle(request.getRealPath("") + "/WEB-INF/font/kaiu.ttf");
			pdf.setPrint_date(print_date);
			pdf.setPrint_page(print_page);
			pdf.setPageType(pagetype);// set 編碼方式
			
			// Step 2:開啟檔案
			pdf.openDocument(response);
			
			// Step 3:逐筆查詢各系所(組)課程科目資料，並建立表格資料
			pdf.createPageArray(1); // create aray size
			pdf.number = 1; // set default
			for (int i = 0; i < 1; i++) {
				// -----------------------------------開始-----------------------------
				// Step 3-1:查詢
				ClubRecordService crs = new ClubRecordService(); // 社團成員資料
				crlist = crs.getClubRecordsByRegnoAndIdno(ud.getSch_code(), reg_no, idno);
				String imgUrl = request.getServletContext().getRealPath("/").replace("StuClub", "StuData") + stu_photoPath + ud.getSch_code() + "/" + crlist.get(0).getRgno() + ".jpg";
				pdf.setCrlist(crlist);
				pdf.setStuimgurl(imgUrl);

				// Step 3-2:產生PDF
				String sch = ud.getSch_cname();
				pdf.setTitle(sch); // 重新設定標題
				pdf.setHeaderString("", crlist.get(0).getStuRegister().getStuBasis().getCname(), "「 " + crlist.get(0).getClub().getClub_name() + "」  服務一覽表");
				check = pdf.Generate();
				// -----------------------------------結束-----------------------------

				// 2012.09.12判斷是否為中間的系統組，true的話要換頁
				if ((10 > 1) && (i != 10 - 1)) {
					pdf.newDivPage();
				}

				pdf.countPage[i] = pdf.number;// get number & set
				pdf.number = 1;// default 1

				if (check == 0) {
					break;
				} else {
					result = result + check;
				}
			}

			// Step 4:關閉檔案
			pdf.closeDocument();
			
		} catch (Exception e) {
			this.logger.error("產生[社團服務時數一覽表PDF]時發生例外錯誤", e);
			return 0;
		}

		// 加 if 判斷 result <>div_code 數量
		if (result > 0) {
			// Step 5：設定開啟檔案名稱及格式
			String filename = crlist.get(0).getStuRegister().getStuBasis().getCname() + "社團服務時數一覽表.pdf";
			response.setContentType("application/rtf;charset");
			response.setHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes("big5"), "ISO8859-1"));
			ServletOutputStream sout = response.getOutputStream();
			pdf.getBuffer().writeTo(sout);
			sout.flush();
			sout.close();
			result = 1;
		} else {
			result = 0;
		}

		return 1;
	}

	// ClubPDF9: 社團教師聘書
	private int ClubPDF9(String print_page, String print_date, String pagetype, HttpServletRequest request, HttpServletResponse response) throws Exception {
		int result = 0;
		int check  = 0;
		ClubExportPDF9      pdf = new ClubExportPDF9();
		ClubTeachingService cts = new ClubTeachingService();
		List<ClubTeaching> ctlist = null;
		Boolean chstudata  = null;
		Boolean chcmupdata = null;
		
		UserData           ud = (UserData) request.getSession().getAttribute("ud"); // 取得session中的物件
		String       sbj_year = request.getParameter("sbj_year");
		String        sbj_sem = request.getParameter("sbj_sem");
		String     club_value = request.getParameter("club_value");
		String     staff_code = request.getParameter("staff_code");
		String    staff_value = request.getParameter("staff_value");    // table value
		String           idno = request.getParameter("idno");
		String        ct_code = request.getParameter("ct_code");        // 證書字號
		String headfontnumber = request.getParameter("headfontnumber"); // 社團字號(頭)
		String footfontnumber = request.getParameter("footfontnumber"); // 社團字號(尾)
		
		try {
			// Step 1:建立PDF類別，設定字型檔路徑、列印日期和頁碼
			pdf.setFontStyle(request.getRealPath("") + "/WEB-INF/font/kaiu.ttf");
			pdf.setPrint_date(print_date);
			pdf.setPrint_page(print_page);
			pdf.setPageType(pagetype); // set 編碼方式
			
			// Step 2:開啟檔案
			pdf.openDocument(response);
			
			// Step 3:逐筆查詢各系所(組)課程科目資料，並建立表格資料
			pdf.createPageArray(1); // create aray size
			pdf.number = 1; // set default
			for (int i = 0; i < 1; i++) {
				// -----------------------------------開始-----------------------------
				// Step 3-1:查詢
				if (!ct_code.equals("0")) {
					pdf.setFontnumber(ct_code);
					ctlist = cts.getClubTeachingByStaffcodeAndIdno(ud.getSch_code(), staff_code, idno, Integer.valueOf(sbj_year), StringUtils.toCharacter(sbj_sem));
					pdf.setCtlist(ctlist);
				} else {
					if (headfontnumber != null && footfontnumber != null) {
						String message;
						ClubTeachingService ctsce = new ClubTeachingService();
//						logger.debug("ClubPDF9 club_value： " + club_value);
//						logger.debug("ClubPDF9 sbj_year： " + sbj_year);
//						logger.debug("ClubPDF9 new ct_code： " + headfontnumber + footfontnumber);
						chstudata = ctsce.chkctcode(ud.getSch_code(), Integer.valueOf(club_value), Integer.valueOf(sbj_year),
								    StringUtils.toCharacter(sbj_sem), headfontnumber + footfontnumber);
						if (chstudata) {
							chcmupdata = ctsce.updatactcode(ud.getSch_code(), Integer.valueOf(club_value), Integer.valueOf(sbj_year),
									     StringUtils.toCharacter(sbj_sem), headfontnumber + footfontnumber, staff_value);
//							logger.debug("ClubPDF9 headfontnumber+footfontnumber :" + headfontnumber + footfontnumber);
							if (chcmupdata) {
								message = headfontnumber + footfontnumber;
							} else {
								message = "fontnumerror";
							}
						} else {
							message = "fontnumrepeat";
						}
						response.setContentType("text/plain");
						ServletOutputStream out = response.getOutputStream();
						out.print(message);
						out.flush();
						return 1;
					}
				}

				// Step 3-2:產生PDF
				String sch = ud.getSch_cname();
				pdf.setTitle(""); // 重新設定標題
				pdf.setSchname(sch);
				pdf.setHeaderString("", "", "");
				check = pdf.Generate();
				// -----------------------------------結束-----------------------------

				// 2012.09.12判斷是否為中間的系統組，true的話要換頁
				if ((10 > 1) && (i != 10 - 1)) {
					pdf.newDivPage();
				}

				pdf.countPage[i] = pdf.number;// get number & set
				pdf.number = 1; // default 1

				if (check == 0) {
					break;
				} else {
					result = result + check;
				}
			}

			// Step 4:關閉檔案
			pdf.closeDocument();
			
		} catch (Exception e) {
			this.logger.error("產生[社團教師聘書PDF]時發生例外錯誤", e);
			return 0;
		}

		// 加 if 判斷 result <>div_code 數量
		if (result > 0) {
			// Step 5：設定開啟檔案名稱及格式
			String filename = ctlist.get(0).getStaff().getStaff_cname() + "_社團教師聘書.pdf";
			response.setContentType("application/rtf;charset");
			response.setHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes("big5"), "ISO8859-1"));
			ServletOutputStream sout = response.getOutputStream();
			pdf.getBuffer().writeTo(sout);
			sout.flush();
			sout.close();
			result = 1;
		} else {
			result = 0;
		}

		return 1;
	}

	// ClubPDF10: 社團學生證書
	private int ClubPDF10(String print_page, String print_date, String pagetype, HttpServletRequest request, HttpServletResponse response) throws Exception {
		int result = 0;
		int check  = 0;
		ClubExportPDF10 pdf = new ClubExportPDF10();
		ClubSemService  css = new ClubSemService();
		List<ClubSem> cslist = null;
		Boolean chstudata  = null;
		Boolean chcmupdata = null;
		
		UserData           ud = (UserData) request.getSession().getAttribute("ud"); // 取得session中的物件
		String       sbj_year = request.getParameter("sbj_year");
		String        sbj_sem = request.getParameter("sbj_sem");
		String           club = request.getParameter("club");
		String           rgno = request.getParameter("rgno");
		String        ex_code = request.getParameter("ex_code");        // 社團字號
		String headfontnumber = request.getParameter("headfontnumber"); // 社團字號(頭)
		String footfontnumber = request.getParameter("footfontnumber"); // 社團字號(尾)
		
		try {
			// Step 1:建立PDF類別，設定字型檔路徑、列印日期和頁碼
			pdf.setFontStyle(request.getRealPath("") + "/WEB-INF/font/kaiu.ttf");
			pdf.setPrint_date(print_date);
			pdf.setPrint_page(print_page);
			pdf.setPageType(pagetype);// set 編碼方式
			
			// Step 2:開啟檔案
			pdf.openDocument(response);
			
			// Step 3:逐筆查詢各系所(組)課程科目資料，並建立表格資料
			pdf.createPageArray(1); // create aray size
			pdf.number = 1; // set default
			for (int i = 0; i < 1; i++) {
				// -----------------------------------開始-----------------------------
				// Step 3-1:查詢
				if (!ex_code.equals("0")) {
					pdf.setFontnumber(ex_code);
					cslist = css.getClubMenberScorePDF(ud.getSch_code(), Integer.valueOf(club), Integer.valueOf(sbj_year),
							 StringUtils.toCharacter(sbj_sem), Integer.valueOf(rgno));
					pdf.setCslist(cslist);
				} else {
					if (headfontnumber != null && footfontnumber != null) {
						String message;
						ClubMemberService cms = new ClubMemberService();
						chstudata = cms.chkdateex_code(ud.getSch_code(), headfontnumber + footfontnumber, Integer.valueOf(club),
								    Integer.valueOf(sbj_year), StringUtils.toCharacter(sbj_sem), Integer.valueOf(rgno));
						if (chstudata) {
							chcmupdata = cms.updateex_code(ud.getSch_code(), headfontnumber + footfontnumber, Integer.valueOf(club),
									     Integer.valueOf(sbj_year), StringUtils.toCharacter(sbj_sem), Integer.valueOf(rgno));
//							logger.debug("ClubPDF10 headfontnumber+footfontnumber :" + headfontnumber + footfontnumber);
							if (chcmupdata) {
								message = headfontnumber + footfontnumber;
							} else {
								message = "fontnumerror";
							}
						} else {
							message = "fontnumrepeat";
						}
						response.setContentType("text/plain");
						ServletOutputStream out = response.getOutputStream();
						out.print(message);
						out.flush();
						return 1;
					}
				}

				// Step 3-2:產生PDF
				String sch = "證		書";
				pdf.setTitle(sch); // 重新設定標題
				pdf.setTitleschool(ud.getSch_cname());
				pdf.setHeaderString("", "", "");
				check = pdf.Generate();
				// -----------------------------------結束-----------------------------

				// 2012.09.12判斷是否為中間的系統組，true的話要換頁
				if ((10 > 1) && (i != 10 - 1)) {
					pdf.newDivPage();
				}

				pdf.countPage[i] = pdf.number;// get number & set
				pdf.number = 1;// default 1

				if (check == 0) {
					break;
				} else {
					result = result + check;
				}
			}

			// Step 4:關閉檔案
			pdf.closeDocument();
			
		} catch (Exception e) {
			this.logger.error("產生[社團學生證書PDF]時發生例外錯誤", e);
			return 0;
		}

		// 加 if 判斷 result <>div_code 數量
		if (result > 0) {
			// Step 5：設定開啟檔案名稱及格式
			String filename = cslist.get(0).getStubasis().getCname() + "_社團學生證書.pdf";
			response.setContentType("application/rtf;charset");
			response.setHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes("big5"), "ISO8859-1"));
			ServletOutputStream sout = response.getOutputStream();
			pdf.getBuffer().writeTo(sout);
			sout.flush();
			sout.close();
			result = 1;
		} else {
			result = 0;
		}

		return 1;
	}

	// ClubPDF11: 社團學生證明書
	private int ClubPDF11(String print_page, String print_date, String pagetype, HttpServletRequest request, HttpServletResponse response) throws Exception {
		int result = 0;
		int check  = 0;
		ClubExportPDF11 pdf = new ClubExportPDF11();
		ClubSemService  css = new ClubSemService();
		List<ClubSem> cslist = null;
		Boolean chstudata  = null;
		Boolean chcmupdata = null;
		
		UserData           ud = (UserData) request.getSession().getAttribute("ud"); // 取得session中的物件
		String       sbj_year = request.getParameter("sbj_year");
		String        sbj_sem = request.getParameter("sbj_sem");
		String           club = request.getParameter("club");
		String           rgno = request.getParameter("rgno");
		String     issue_code = request.getParameter("issue_code");     // 社團字號
		String headfontnumber = request.getParameter("headfontnumber"); // 社團字號(頭)
		String footfontnumber = request.getParameter("footfontnumber"); // 社團字號(尾)
//		logger.debug("ClubPDF11 issue_code ： " + issue_code);

		try {
			// Step 1:建立PDF類別，設定字型檔路徑、列印日期和頁碼
			pdf.setFontStyle(request.getServletContext().getRealPath("") + "/WEB-INF/font/kaiu.ttf");
			pdf.setPrint_date(print_date);
			pdf.setPrint_page(print_page);
			pdf.setPageType(pagetype);// set 編碼方式
			
			// Step 2:開啟檔案
			pdf.openDocument(response);
			
			// Step 3:逐筆查詢各系所(組)課程科目資料，並建立表格資料
			pdf.createPageArray(1); // create aray size
			pdf.number = 1; // set default
			for (int i = 0; i < 1; i++) {
				// -----------------------------------開始-----------------------------
				// Step 3-1:查詢
				if (!issue_code.equals("0")) {
					pdf.setFontnumber(issue_code);
					cslist = css.getClubMenberScorePDF(ud.getSch_code(), Integer.valueOf(club),
							 Integer.valueOf(sbj_year), StringUtils.toCharacter(sbj_sem), Integer.valueOf(rgno));
					pdf.setCslist(cslist);
				} else {
					if (headfontnumber != null && footfontnumber != null) {
						String message;
						ClubMemberService cms = new ClubMemberService();
						chstudata = cms.chkdateissue_code(ud.getSch_code(), headfontnumber + footfontnumber, Integer.valueOf(club),
								    Integer.valueOf(sbj_year), StringUtils.toCharacter(sbj_sem), Integer.valueOf(rgno));
						if (chstudata) {
							chcmupdata = cms.updateissue_code(ud.getSch_code(), headfontnumber + footfontnumber, Integer.valueOf(club),
									     Integer.valueOf(sbj_year), StringUtils.toCharacter(sbj_sem), Integer.valueOf(rgno));
//							logger.debug("ClubPDF11 headfontnumber+footfontnumber :" + headfontnumber + footfontnumber);
							if (chcmupdata) {
								message = headfontnumber + footfontnumber;
							} else {
								message = "fontnumerror";
							}
						} else {
							message = "fontnumrepeat";
						}
						response.setContentType("text/plain");
						ServletOutputStream out = response.getOutputStream();
						out.print(message);
						out.flush();
						return 1;
					}
				}
				// Step 3-2:產生PDF
				String sch = "社  團  證  明   書";
				pdf.setTitle(sch); // 重新設定標題
				pdf.setTitleschool(ud.getSch_cname());
				pdf.setHeaderString("", "", "");
				check = pdf.Generate();
				// -----------------------------------結束-----------------------------

				// 2012.09.12判斷是否為中間的系統組，true的話要換頁
				if ((10 > 1) && (i != 10 - 1)) {
					pdf.newDivPage();
				}

				pdf.countPage[i] = pdf.number;// get number & set
				pdf.number = 1;// default 1

				if (check == 0) {
					break;
				} else {
					result = result + check;
				}
			}

			// Step 4:關閉檔案
			pdf.closeDocument();

		} catch (Exception e) {
			this.logger.error("產生[社團學生證明書PDF]時發生例外錯誤", e);
			return 0;
		}

		// 加 if 判斷 result <>div_code 數量
		if (result > 0) {
			// Step 5：設定開啟檔案名稱及格式
			String filename = cslist.get(0).getStubasis().getCname() + "_社團學生明證書.pdf";
			response.setContentType("application/rtf;charset");
			response.setHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes("big5"), "ISO8859-1"));
			ServletOutputStream sout = response.getOutputStream();
			pdf.getBuffer().writeTo(sout);
			sout.flush();
			sout.close();
			result = 1;
		} else {
			result = 0;
		}

		return 1;
	}
}
