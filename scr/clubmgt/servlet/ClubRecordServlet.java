package ntpc.ccai.clubmgt.servlet;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ntpc.ccai.bean.UserData;
import ntpc.ccai.clubmgt.bean.Club;
import ntpc.ccai.clubmgt.bean.ClubMemberCadre;
import ntpc.ccai.clubmgt.bean.ClubRecord;
import ntpc.ccai.clubmgt.bean.StuBasis;
import ntpc.ccai.clubmgt.service.ClubCadreService;
import ntpc.ccai.clubmgt.service.ClubMemberCadreService;
import ntpc.ccai.clubmgt.service.ClubRecordService;
import ntpc.ccai.clubmgt.service.StuBasisService;

@WebServlet("/ClubRecordServlet.java")
public class ClubRecordServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String MANAGEMENT_SERVLET = "/StuClub/ClubMgt";
    private static final String INIT = "/WEB-INF/jsp/ClubMgt/ClubRecord.jsp"; //社團成員設定
    private static final String ERROR = "/WEB-INF/jsp/ErrorPage.jsp"; // 失敗的 view
    private static final Logger logger = Logger.getLogger(ClubMemberSetting.class);    

    private ClubRecordService clubRecordService = new ClubRecordService();
    private StuBasisService stuBasisService = new StuBasisService();
    private ClubMemberCadreService membercadreService = new ClubMemberCadreService();
    private ClubCadreService cadreService = new ClubCadreService();
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    
    // service
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String error_msg = null; // 錯誤訊息
        request.setCharacterEncoding("UTF-8"); 

        // 收集資料
        HttpSession session = request.getSession(); // 取得 session 物件

        // 設定資料
        UserData ud = (UserData) session.getAttribute("ud"); // 取得 session 中的物件
        Club club = (Club) session.getAttribute("club");
        Integer sbj_year = (Integer) session.getAttribute("sbj_year");
        Character sbj_sem = (Character) session.getAttribute("sbj_sem");

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
        
        if (club == null) {
            error_msg = "操作錯誤，請先選擇社團。";
        }
        
        if (error_msg != null) {
            request.setAttribute("error_msg", error_msg);
            request.getRequestDispatcher(ERROR).forward(request, response);
            return;
        }
        // Step 2: doGet or doPost
        super.service(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        UserData ud = (UserData) session.getAttribute("ud"); // 取得 session 中的物件
        String sch_code = ud.getSch_code();
        Club club = (Club) request.getSession().getAttribute("club");
        Integer sbj_year = (Integer) session.getAttribute("sbj_year");
        Character sbj_sem = (Character) session.getAttribute("sbj_sem");
        
        request.setAttribute("records", clubRecordService.getClubRecordsByClubNumAndYearAndSem(sch_code, club.getClub_num(), sbj_year, sbj_sem));
        
        request.getRequestDispatcher(INIT).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String error_msg = null;
        String targetURL = INIT;
        
        HttpSession session = request.getSession();
        UserData ud = (UserData) session.getAttribute("ud"); // 取得 session 中的物件
        String sch_code = ud.getSch_code();
        
        String result = null;
        Club club = (Club) session.getAttribute("club");
        Integer sbj_year = (Integer) session.getAttribute("sbj_year");
        Character sbj_sem = (Character) session.getAttribute("sbj_sem");
        
        if ("delete".equals(request.getParameter("op"))) {
        	try {
        		String cr_num_temp = request.getParameter("cr_num");
        		if (cr_num_temp != null && cr_num_temp.trim().length() > 0) {
        			Boolean b = clubRecordService.delete(sch_code, Integer.parseInt(cr_num_temp));
                    result = b ? "刪除成功" : "刪除失敗";
        		} else {
        			result = "未收到資料";
        		}
        	} catch (NumberFormatException ex) {
        		result = "輸入錯誤";
        		ex.printStackTrace();
        		logger.error(ex);
        	}
        	response.setCharacterEncoding("UTF-8");
            response.setContentType("text/plain");
            response.getWriter().print(result);
        	return;
        } else if("getStuCadres".equals(request.getParameter("op"))){
        	try {
	        	String reg_no = request.getParameter("reg_no");
	        	StuBasis stuBasis = stuBasisService.getStudentByRegno(sch_code, reg_no);
	        	List<ClubMemberCadre> clubMemberCadreList = null;
	        	
	        	if(reg_no != null && !reg_no.isEmpty() && stuBasis != null) {
	        		clubMemberCadreList = membercadreService.getClubMemberCadreByClubNumAndYearAndSemAndRgno(sch_code, club.getClub_num(), sbj_year, sbj_sem, stuBasis.getRgno());
	        		
	        		if(clubMemberCadreList.size() > 0) {
		        		result = stuBasis.getReg_no() + " " + stuBasis.getCname() + "同學幹部紀錄：";
		        		for(int i = 0 ; i < clubMemberCadreList.size();i++) {
		        			clubMemberCadreList.get(i).setClubCadre(cadreService.getClubCadreByCadreNum(sch_code, clubMemberCadreList.get(i).getCadre_num()));
		        			result +=" " + clubMemberCadreList.get(i).getClubCadre().getCadre_name();
	
		        		}
	        		} else {
	        			result = "查無相關幹部紀錄";
	        		}
	        	} else {
	        		result="請重新輸入學號";
	        	}
        	} catch (Exception e) {
        		result = "幹部查詢錯誤，請回報以下內容 /n" + e;
        		e.printStackTrace();
        		logger.error(e);
			}
        	response.setCharacterEncoding("UTF-8");
            response.setContentType("text/plain");
            response.getWriter().print(result);
        	return;
        }
        
        
        
        try {
            if (club != null && sbj_year != null && sbj_sem != null) {
                String name = request.getParameter("name");
                String reg_no = request.getParameter("reg_no");
                String cr_sdate_temp = request.getParameter("cr_sdate");
                String cr_edate_temp = request.getParameter("cr_edate");
                String cr_hours_temp = request.getParameter("cr_hours");
                String cr_detail = request.getParameter("cr_detail");
                String level = request.getParameter("level");
                String cr_cadre = request.getParameter("cr_cadre");
                
                //Changing some of the info from the request into their matching type
                Integer cr_hours = Integer.parseInt(cr_hours_temp);
                Timestamp cr_time1 = new Timestamp(System.currentTimeMillis());
                Date cr_time = new Date(cr_time1.getTime());
                Date cr_sdate = format.parse(cr_sdate_temp);
                Date cr_edate = format.parse(cr_edate_temp);

                StuBasis stuBasis = stuBasisService.getStudentByRegno(sch_code, reg_no);
                if (stuBasis != null && stuBasis.getCname().equals(name)) {
                	//The new club record that is intended to add
                    ClubRecord clubRecord = new ClubRecord();
                    clubRecord.setClub_num(club.getClub_num());
                    clubRecord.setSbj_year(sbj_year);
                    clubRecord.setSbj_sem(sbj_sem);
                    clubRecord.setRgno(stuBasis.getRgno());
                    clubRecord.setCr_detail(cr_detail);
                    clubRecord.setCr_sdate(cr_sdate);
                    clubRecord.setCr_edate(cr_edate);
                    clubRecord.setCr_hours(cr_hours);
                    clubRecord.setStaff_code(ud.getStaff_code());
                    clubRecord.setCr_time(cr_time);
                    if(cr_cadre != null && !cr_cadre.isEmpty() && level != null) {
                    	clubRecord.setLevel(level);
                    	clubRecord.setCr_cadre(cr_cadre);
                    }
                    clubRecord = clubRecordService.insert(sch_code, clubRecord);
                    
                    if (clubRecord != null && clubRecord.getCr_num() != null) {
                    	response.sendRedirect("/StuClub/ClubRecord");
                    	return;
                    } else {
                        error_msg = "新增失敗"; 
                    }
                } else {
                    error_msg = "學生資料錯誤，請確認學生姓名與學號是否正確";
                }
            } else {
                error_msg = "連線逾期，無法順利建立資料，請重新登入"; 
                targetURL = ERROR;
            }
        } catch (NumberFormatException | ParseException ex) {
            ex.printStackTrace();
            logger.error(ex);
            error_msg = "新增失敗：資料格式錯誤";
        }
        
        if (error_msg != null && error_msg.length() > 0) {
            request.setAttribute("error_msg", error_msg); 
        }
        
        request.getRequestDispatcher(targetURL).forward(request, response);
    }
}