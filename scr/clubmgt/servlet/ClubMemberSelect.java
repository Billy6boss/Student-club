package ntpc.ccai.clubmgt.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import ntpc.ccai.bean.UserData;
import ntpc.ccai.clubmgt.bean.Club;
import ntpc.ccai.clubmgt.bean.ClubMember;
import ntpc.ccai.clubmgt.bean.StuClass;
import ntpc.ccai.clubmgt.bean.StuRegister;
import ntpc.ccai.clubmgt.service.ClubMemberService;
import ntpc.ccai.clubmgt.service.StuClassService;
import ntpc.ccai.clubmgt.service.StuRegisterService;
import ntpc.ccai.clubmgt.util.StringUtils;

@WebServlet("/ClubMemberSelect")
public class ClubMemberSelect extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String MANAGEMENT_SERVLET = "/StuClub/ClubMgt";
    private static final String INIT = "/WEB-INF/jsp/ClubMgt/ClubMemberSelect.jsp"; //社團成員
    private static final String ERROR = "/WEB-INF/jsp/ErrorPage.jsp"; // 失敗的 view
    private static final Logger logger = Logger.getLogger(ClubMemberSelect.class);    

    private StuClassService stuClassService = new StuClassService();
    private StuRegisterService stuRegisterService = new StuRegisterService();
    private ClubMemberService clubMemberService = new ClubMemberService();
    
    
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
	        request.setAttribute("ud", ud);
	        List<StuClass> classes = stuClassService.getStuClassesByYearAndSemAndGrade(sch_code, sbj_year, sbj_sem, '1');
	        request.setAttribute("classes", classes);
	        if (classes != null && !classes.isEmpty()) {
	            request.setAttribute("students", stuRegisterService.getStuRegistersByYearAndSemAndClsCode(sch_code, sbj_year, sbj_sem, classes.get(0).getCls_code()));
	        }
	        request.setAttribute("members", clubMemberService.getClubMembersByClubNumAndYearAndSem(sch_code, club.getClub_num(), sbj_year, sbj_sem));
	        targetURL = INIT;
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error(ex);
            error_msg = "社團成員：載入資料發生錯誤";
            request.setAttribute("error_msg", error_msg); 
            targetURL = ERROR;
        } 
        
        request.getRequestDispatcher(targetURL).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String result = null;
    	
    	HttpSession session = request.getSession();
    	
    	UserData ud = (UserData) session.getAttribute("ud"); // 取得 session 中的物件
        String sch_code = ud.getSch_code();
    	Integer sbj_year = (Integer) session.getAttribute("sbj_year");
        Character sbj_sem = (Character) session.getAttribute("sbj_sem");
        Club club = (Club) session.getAttribute("club");
    	
        String op = request.getParameter("op");
        if ("getClass".equals(op)) {
        	Character grade = StringUtils.toCharacter(request.getParameter("grade"));
        	List<StuClass> list = stuClassService.getStuClassesByYearAndSemAndGrade(sch_code, sbj_year, sbj_sem, grade);
        	if (list != null) {
        		Gson gson = new Gson();
                result = gson.toJson(list, new TypeToken<List<StuClass>>() {}.getType());
        	} else {
        		JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("error", "資料庫連線失敗");
                result = jsonObject.toString();
        	}
        } else if ("getStuByClass".equals(op)) {
        	String cls_code=request.getParameter("class");
        	List<StuRegister> list = stuRegisterService.getStuRegistersByYearAndSemAndClsCode(sch_code, sbj_year, sbj_sem, cls_code);
        	if (list != null) {
        		Gson gson = new Gson();
                result = gson.toJson(list, new TypeToken<List<StuRegister>>() {}.getType());
        	} else {
        		JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("error", "資料庫連線失敗");
                result = jsonObject.toString();
        	}
        } else if ("insert".equals(op)) {
            JsonObject jsonObject = new JsonObject();
            String[] rgnos = request.getParameterValues("rgno");
            if (rgnos != null && rgnos.length > 0) {
                List<ClubMember> clubMembers = new ArrayList<ClubMember>();
                try {
                    for (String rgno : rgnos) {
                        ClubMember clubMember = new ClubMember();
                        clubMember.setClub_num(club.getClub_num());
                        clubMember.setRgno(Integer.parseInt(rgno));
                        clubMember.setSbj_sem(sbj_sem);
                        clubMember.setSbj_year(sbj_year);
                        clubMembers.add(clubMember);
                    }
                } catch(NumberFormatException ex) {
                    ex.printStackTrace();
                    logger.error(ex);
                    result = "學生參數錯誤";
                }
                if (result == null) {
                    Boolean b = clubMemberService.insert(sch_code, clubMembers);
                    result = b ? "新增成功" : "新增失敗";
                }
            } else {
                result = "參數錯誤";
            }
            jsonObject.addProperty("result", result);
            result = jsonObject.toString();
        }
        
        else if("delete".equals(op)) {
        	JsonObject jsonObject = new JsonObject();
        	Integer rgno = Integer.parseInt(request.getParameter("rgno"));
            Integer club_num= club.getClub_num();
            Boolean complete = clubMemberService.delete(sch_code, club_num, sbj_year, sbj_sem, rgno);
            if (complete) {
                jsonObject.addProperty("result","刪除成功" );
            } else {
                jsonObject.addProperty("result", "刪除項次錯誤");
            }
            result = jsonObject.toString();
        }
        
        else if("getStuByClsNo".equals(op)) {
        	String temp=request.getParameter("cls_num");
        	int length = temp.length();
        	String cls=temp.substring(0, length-2), num=temp.substring(length-2, length);
        	StuRegister student=stuRegisterService.getStuRegistersByYearAndSemAndClsCodeAndClsNo(sch_code, sbj_year, sbj_sem, cls, num);
        	if (student != null) {
        		Gson gson = new Gson();
                result = gson.toJson(student, new TypeToken<StuRegister>() {}.getType());
        	} else {
        		JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("error", "查無資料");
                result = jsonObject.toString();
        	}
        }
        else if("getStuByRegNo".equals(op)){
        	String reg_no=request.getParameter("reg_no");
        	StuRegister student=stuRegisterService.getStuRegisterByYearAndSemAndRegNo(sch_code, sbj_year, sbj_sem, reg_no);
        	if (student != null) {
        		Gson gson = new Gson();
                result = gson.toJson(student, new TypeToken<StuRegister>() {}.getType());
        	} else {
        		JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("error", "查無資料");
                result = jsonObject.toString();
        	}
        }
        response.getWriter().print(result);	
    }
}