package ntpc.ccai.clubmgt.servlet;

import java.io.IOException;
import java.util.HashSet;
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

import com.google.gson.Gson;

import ntpc.ccai.bean.UserData;
import ntpc.ccai.clubmgt.bean.Club;
import ntpc.ccai.clubmgt.bean.ClubMember;
import ntpc.ccai.clubmgt.bean.ClubSem;
import ntpc.ccai.clubmgt.bean.SemesterType;
import ntpc.ccai.clubmgt.service.ClubCategoryService;
import ntpc.ccai.clubmgt.service.ClubMemberService;
import ntpc.ccai.clubmgt.service.ClubSemService;
import ntpc.ccai.clubmgt.service.SemesterTypeService;
import ntpc.ccai.clubmgt.util.StringUtils;

@WebServlet("/ClubCopy")
public class ClubCopy extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String MANAGEMENT_SERVLET = "/StuClub/ClubMgt";
    private static final String INIT = "/WEB-INF/jsp/ClubMgt/ClubCopy.jsp"; //社團選社設定
    private static final String ERROR = "/WEB-INF/jsp/ErrorPage.jsp"; // 失敗的 view
    private static final Logger logger = Logger.getLogger(ClubSelectionMethod.class);
    
    private SemesterTypeService semesterTypeService = new SemesterTypeService();
    private ClubSemService clubSemService = new ClubSemService();
    private ClubMemberService clubMemberService = new ClubMemberService();
    private ClubCategoryService clubCategoryService = new ClubCategoryService();

    // service
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String error_msg = null; // 錯誤訊息
        request.setCharacterEncoding("UTF-8"); 

        // 收集資料
        HttpSession session = request.getSession(); // 取得 session 物件

        // 設定資料
        UserData ud = (UserData) session.getAttribute("ud"); // 取得 session 中的物件
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
        String error_msg = null;
        String targetURL = null;
        Gson gson = new Gson();
        
        HttpSession session = request.getSession();
        UserData ud = (UserData) session.getAttribute("ud");
        String sch_code = ud.getSch_code();
        
        String yearStr = request.getParameter("year");
        String semStr = request.getParameter("sem");
        
        if (yearStr != null && semStr != null) {
            Integer year = null;
            Character sem = null;
            String result = null;
            
            try {
                year = Integer.parseInt(yearStr);
                sem = StringUtils.toCharacter(semStr);
                List<ClubSem> list = clubSemService.getClubSemsByYearAndSem(sch_code, year, sem);
                for (int i = 0, size = list.size(); i < size; i++) {
                    ClubSem clubSem = list.get(i);
                    Club club = clubSem.getClub();
                    Integer club_num = clubSem.getClub_num();
                    clubSem.setClubMembers(new HashSet<ClubMember>(clubMemberService.getClubMembersByClubNumAndYearAndSem(sch_code, club_num, year, sem)));
                    club.setClubCategory(clubCategoryService.getClubCategoryByCatNum(sch_code, club.getCat_num()));
                }
                result = gson.toJson(list);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                logger.error("搜尋社團參數錯誤: " + e);
            }            
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().println(result);
            return;
        }
        
        List<SemesterType> semesterTypes = semesterTypeService.getAllSemesterTypes(sch_code);
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
            
            request.setAttribute("fromSemester", gson.toJson(clubSemService.getClubYearsAndSemesters(sch_code)));
            request.setAttribute("toSemester", gson.toJson(semesterMap));
            
            targetURL = INIT;
        } else {
            error_msg = "社團複製：載入資料發生錯誤";
        }
        
        if (error_msg != null && error_msg.length() > 0) {
            request.setAttribute("error_msg", error_msg); 
            targetURL = ERROR;
        }
        request.getRequestDispatcher(targetURL).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String error_msg = null;
        String targetURL = ERROR;
        
        HttpSession session = request.getSession();
        UserData ud = (UserData) session.getAttribute("ud");
        String sch_code = ud.getSch_code();
        
        String from_year_temp = request.getParameter("from_year");
        String to_year_temp = request.getParameter("to_year");
        String from_sem_temp = request.getParameter("from_sem");
        String to_sem_temp = request.getParameter("to_sem");
        boolean member = "true".equals(request.getParameter("member"));
        boolean teaching = "true".equals(request.getParameter("teaching"));
        boolean time = "true".equals(request.getParameter("time"));
        String[] club_nums_temp = request.getParameterValues("club_num");
        
        if (from_year_temp != null && to_year_temp != null && from_sem_temp != null && to_sem_temp != null) {
            if (club_nums_temp != null && club_nums_temp.length > 0) {
                try {
                    Integer from_year = Integer.parseInt(from_year_temp);
                    Integer to_year = Integer.parseInt(to_year_temp);
                    Character from_sem = StringUtils.toCharacter(from_sem_temp);
                    Character to_sem = StringUtils.toCharacter(to_sem_temp);
                    Map<Integer, Set<Character>> map = clubSemService.getClubYearsAndSemesters(sch_code);                    
                    if (map.containsKey(to_year) && map.get(to_year).contains(to_sem)) {
                        error_msg = "該學年期已有社團資料";
                    } else {
                        try {
                            Set<Integer> club_nums = new HashSet<Integer>();
                            for (int i = 0, l = club_nums_temp.length; i < l; i++) {
                                club_nums.add(Integer.parseInt(club_nums_temp[i]));
                            }
                            Integer result = clubSemService.copy(sch_code, from_year, from_sem, to_year, to_sem, club_nums, member, teaching, time);
                            if (result > 0) {
                                request.setAttribute("result", "複製完成");
                                targetURL = INIT;
                            } else {
                                error_msg = "複製失敗";
                            }
                        } catch (NumberFormatException ex) {
                            error_msg = "社團編號錯誤";
                            ex.printStackTrace();
                            logger.error(error_msg + ": " + ex);
                        }
                    }
                } catch (IllegalArgumentException ex) {
                    error_msg = "社團複製學年期錯誤";
                    ex.printStackTrace();
                    logger.error(error_msg + ": " + ex);
                }
            } else {
                error_msg = "未選擇社團";
            }
        } else {
            error_msg = "選擇學年期錯誤";
        }
        
        if (error_msg != null && error_msg.length() > 0) {
            request.setAttribute("error_msg", error_msg); 
        }
        request.getRequestDispatcher(targetURL).forward(request, response);
    }
}