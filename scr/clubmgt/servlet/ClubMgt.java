package ntpc.ccai.clubmgt.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ntpc.ccai.bean.UserData;
import ntpc.ccai.clubmgt.bean.Club;
import ntpc.ccai.clubmgt.bean.ClubCategory;
import ntpc.ccai.clubmgt.bean.ClubSem;
import ntpc.ccai.clubmgt.bean.SemesterType;
import ntpc.ccai.clubmgt.service.ClubCategoryService;
import ntpc.ccai.clubmgt.service.ClubSemService;
import ntpc.ccai.clubmgt.service.ClubService;
import ntpc.ccai.clubmgt.service.SemesterTypeService;
import ntpc.ccai.clubmgt.util.StringUtils;

@WebServlet("/ClubMgt")
public class ClubMgt extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String MANAGEMENT = "/WEB-INF/jsp/ClubMgt/ClubMgtmenu.jsp"; //社團管理 
    private static final String SETTINGS_SERVLET = "/StuClub/ClubSettings"; //基本社團設定
    private static final String ERROR = "/WEB-INF/jsp/ErrorPage.jsp"; // 失敗的 view
    private static final Logger logger = Logger.getLogger(ClubMgt.class);    
    
    // service
    private ClubCategoryService clubCategoryService = new ClubCategoryService();
    private ClubSemService clubSemService = new ClubSemService();  
    private ClubService clubService = new ClubService();
    private SemesterTypeService semesterTypeService = new SemesterTypeService();
    
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String error_msg = null; // 錯誤訊息
        request.setCharacterEncoding("UTF-8"); 

        // 收集資料
        HttpSession session = request.getSession(); // 取得 session 物件
        session.removeAttribute("club");
        session.removeAttribute("clubSem");

        // 設定資料
        UserData ud = (UserData) session.getAttribute("ud"); // 取得 session 中的物件

        // Step 1: 權限檢查
        if (ud == null) {
            // 尚未登入
            error_msg = "連線逾期，無法順利建立資料，請重新登入";
        }
        
        if (error_msg != null) {
            request.setAttribute("error_msg", error_msg);
            request.getRequestDispatcher(ERROR).forward(request, response);
            return;
        }
        // Step 2: doGet or doPost
        super.service(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String error_msg = null;
        String targetURL = null;
        
        HttpSession session = request.getSession();
        Integer sbj_year = (Integer) session.getAttribute("sbj_year");
        Character sbj_sem = (Character) session.getAttribute("sbj_sem");

        UserData ud = (UserData) session.getAttribute("ud"); // 取得 session 中的物件
        String sch_code = ud.getSch_code();
        String cat_num_temp = request.getParameter("cat_num");
        String club_name = request.getParameter("club_name");
        String club_code = request.getParameter("club_code");
        
        if (sbj_year == null || sbj_sem == null) {
            SemesterType semesterType = semesterTypeService.getSemesterTypeByDate(sch_code, new java.util.Date());
            
            if (semesterType != null) {
                sbj_year = semesterType.getSbj_year();
                sbj_sem = semesterType.getSbj_sem();
                session.setAttribute("sbj_year", sbj_year);
                session.setAttribute("sbj_sem", sbj_sem);
            } else {
                if(ud.getRole_code().equals("sta")){
                    error_msg = "尚未設定該日期的學年期別，請至【學務系統 - 點名節次設定】";
                } else {
                    error_msg = "尚未設定該日期的學年期別，請聯絡學務處承辦人";
                }
            }
        }
        
        if (error_msg == null) {
            List<ClubCategory> categories = clubCategoryService.getAllClubCategory(sch_code);
            List<SemesterType> semesterTypes = semesterTypeService.getAllSemesterTypes(sch_code);
            if (categories != null && !categories.isEmpty() ) {
                Map<Integer, String> categoryMap = new HashMap<Integer, String>();
                for (ClubCategory cat : categories) {
                    categoryMap.put(cat.getCat_num(), cat.getCat_name());
                }
                request.setAttribute("categoryMap", categoryMap);
                
                if (semesterTypes != null && !semesterTypes.isEmpty()) {
                    Map<Integer, Set<Character>> semesterMap = new TreeMap<Integer, Set<Character>>();
                    for (SemesterType semesterType : semesterTypes) {
                        Integer year = semesterType.getSbj_year();
                        if (semesterMap.containsKey(year)) {
                            semesterMap.get(year).add(semesterType.getSbj_sem());
                        } else {
                            Set<Character> set = new TreeSet<Character>();
                            set.add(semesterType.getSbj_sem());
                            semesterMap.put(year, set);
                        }
                    }
                    
                    //如果該選擇學年內並無相關學期資料
                    if(!semesterMap.get(sbj_year).contains(sbj_sem)) {
                    	//取得該學年內有學期資料取代
                    	Character replaceSem = semesterMap.get(sbj_year).iterator().next();
                    	session.setAttribute("sbj_sem", replaceSem);
                    	sbj_sem = replaceSem;
                    }
                    
                    request.setAttribute("semesterMap", semesterMap);
                    
                    List<ClubSem> clubList = null;
                    if (cat_num_temp != null) {
                        Integer cat_num = Integer.parseInt(cat_num_temp);
                        clubList = clubSemService.getClubSemsByClubCategoryAndYearAndSem(sch_code, cat_num, sbj_year, sbj_sem);
                    } else if (club_name != null) {
                        clubList = clubSemService.getClubSemsByClubNameAndYearAndSem(sch_code, club_name, sbj_year, sbj_sem);
                    } else if (club_code != null) {
                        clubList = clubSemService.getClubSemsByClubCodeAndYearAndSem(sch_code, club_code, sbj_year, sbj_sem);
                    } else {
                        clubList = clubSemService.getClubSemsByYearAndSem(sch_code, sbj_year, sbj_sem);
                    }
                    
                    request.setAttribute("clubList", clubList);
                    targetURL = MANAGEMENT;
                } else {
                    error_msg = "社團管理：載入學年期資料發生錯誤，請至學務系統設定";
                }
            } else {
                error_msg = "社團管理：載入社團類別發生錯誤";
            }
        }
        
        if (error_msg != null && error_msg.length() > 0) {
            request.setAttribute("error_msg", error_msg); 
            targetURL = ERROR;
            session.removeAttribute("ud");
        }
        request.getRequestDispatcher(targetURL).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String error_msg = null;
        String targetURL = null;
        
        HttpSession session = request.getSession();
        UserData ud = (UserData) session.getAttribute("ud"); // 取得 session 中的物件
        String sch_code = ud.getSch_code();
        Integer sbj_year = (Integer) session.getAttribute("sbj_year");
        Character sbj_sem = (Character) session.getAttribute("sbj_sem");
        
        String club_num_temp = request.getParameter("club_num");
        String op = request.getParameter("op");
        
        if (op != null) {
            if (club_num_temp == null || club_num_temp.trim().length() == 0) {
                if (op.equals("set")) {
                    String sbj_year_temp = request.getParameter("sbj_year");
                    sbj_sem = StringUtils.toCharacter(request.getParameter("sbj_sem"));
                    sbj_year = Integer.parseInt(sbj_year_temp);
                    if (sbj_year != null && sbj_sem != null) {
                        session.setAttribute("sbj_year", sbj_year);
                        session.setAttribute("sbj_sem", sbj_sem);
                        return;
                    }
                }
                error_msg = "錯誤: 無參數";
            } else {
                Integer club_num = null;
                try {
                    club_num = Integer.parseInt(club_num_temp);
                    if (op.equals("edit")) {
                        Club club = clubService.getClubByClubNum(sch_code, club_num);
                        if (club != null) {
                            request.getSession().setAttribute("club", club);
                            response.sendRedirect(SETTINGS_SERVLET);
                            return;
                        } else {
                            error_msg = "查無目標";
                        }
                    } else if (op.equals("delete")) {
                        Boolean b = clubSemService.delete(sch_code, club_num, sbj_year, sbj_sem);
                        String result = b ? "刪除成功" : "刪除失敗";
                        
                        response.setCharacterEncoding("UTF-8");
                        response.setContentType("text/plain");
                        response.getWriter().print(result);
                        return;
                    } else {
                        error_msg = "無此操作";
                    }
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                    logger.error("轉換參數失敗: " + ex);
                    error_msg = "參數錯誤";
                } 
            }
        } else {
            error_msg = "無操作";
        }
        if (error_msg != null) {
            request.setAttribute("error_msg", error_msg); 
            targetURL = ERROR;
        }
        request.getRequestDispatcher(targetURL).forward(request, response);
    }   
}