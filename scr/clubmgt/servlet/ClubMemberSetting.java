package ntpc.ccai.clubmgt.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ntpc.ccai.bean.UserData;
import ntpc.ccai.clubmgt.bean.Club;
import ntpc.ccai.clubmgt.bean.ClubMember;
import ntpc.ccai.clubmgt.bean.ClubMemberCadre;
import ntpc.ccai.clubmgt.service.ClubCadreService;
import ntpc.ccai.clubmgt.service.ClubMemberService;

@WebServlet("/ClubMemberSetting")
public class ClubMemberSetting extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String MANAGEMENT_SERVLET = "/StuClub/ClubMgt";
    private static final String INIT = "/WEB-INF/jsp/ClubMgt/ClubMemberSetting.jsp"; //社團成員設定
    private static final String ERROR = "/WEB-INF/jsp/ErrorPage.jsp"; // 失敗的 view
    private static final Logger logger = Logger.getLogger(ClubMemberSetting.class); 
    
    private ClubMemberService clubMemberService = new ClubMemberService();
    private ClubCadreService clubCadreService = new ClubCadreService();

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
        String error_msg;
        String targetURL;
        
        HttpSession session = request.getSession();
        
        UserData ud = (UserData) session.getAttribute("ud"); // 取得 session 中的物件
        String sch_code = ud.getSch_code();
        Integer sbj_year = (Integer) session.getAttribute("sbj_year");
        Character sbj_sem = (Character) session.getAttribute("sbj_sem");
        Club club = (Club) session.getAttribute("club");
        
        try {
            request.setAttribute("isFirstTime", true);
            request.setAttribute("members", clubMemberService.getClubMembersByClubNumAndYearAndSem(sch_code, club.getClub_num(), sbj_year, sbj_sem));
            request.setAttribute("cadres", clubCadreService.getAllClubCadres(sch_code));
            targetURL = INIT;
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error(ex);
            error_msg = "社團成員設定：載入資料發生錯誤";
            request.setAttribute("error_msg", error_msg); 
            targetURL = ERROR;
        }
        
        request.getRequestDispatcher(targetURL).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {        
        HttpSession session = request.getSession();
        
        UserData ud = (UserData) session.getAttribute("ud"); // 取得 session 中的物件
        String sch_code = ud.getSch_code();
        Integer sbj_year = (Integer) session.getAttribute("sbj_year");
        Character sbj_sem = (Character) session.getAttribute("sbj_sem");
        Club club = (Club) session.getAttribute("club");
        Map<Integer, ClubMember> clubMembers = new HashMap<Integer, ClubMember>();
        
        for (Enumeration<String> e = request.getParameterNames(); e.hasMoreElements();) {
        	String str = e.nextElement();
        	Integer rgno = Integer.parseInt(str.split("_")[1]);
            
            ClubMember clubMember = null;
            if (clubMembers.containsKey(rgno)) {
                clubMember = clubMembers.get(rgno);
            } else {
                clubMember = new ClubMember();
            }
        	
        	if (str.startsWith("score")) {
        		String score_temp = request.getParameter(str);
        	
        		clubMember.setClub_num(club.getClub_num());
        		clubMember.setSbj_year(sbj_year);
        		clubMember.setSbj_sem(sbj_sem);
        		clubMember.setRgno(rgno);
        		clubMember.setClub_score(Integer.parseInt(score_temp));
        	} else if (str.startsWith("cadre")) {
        	    String[] cadre_nums = request.getParameterValues(str);
        	    
        	    for (String cadre : cadre_nums) {
        	        ClubMemberCadre clubMemberCadre = new ClubMemberCadre();
                    clubMemberCadre.setCadre_num(Integer.parseInt(cadre));
                    clubMemberCadre.setClub_num(club.getClub_num());
                    clubMemberCadre.setRgno(rgno);
                    clubMemberCadre.setSbj_sem(sbj_sem);
                    clubMemberCadre.setSbj_year(sbj_year);
                    clubMember.getClubMemberCadre().add(clubMemberCadre);
        	    }
        	}

            clubMembers.put(rgno, clubMember);
        }
        request.setAttribute("updateResult", clubMemberService.updateScoreAndCadre(sch_code, new ArrayList<ClubMember>(clubMembers.values())));
        request.setAttribute("members", clubMemberService.getClubMembersByClubNumAndYearAndSem(sch_code, club.getClub_num(), sbj_year, sbj_sem));
        request.setAttribute("cadres", clubCadreService.getAllClubCadres(sch_code)); 
        
        request.getRequestDispatcher(INIT).forward(request, response);
    }
}