package ntpc.ccai.clubmgt.servlet;

import java.io.IOException;
import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.ibm.db2.jcc.am.re;

import ntpc.ccai.bean.UserData;
import ntpc.ccai.clubmgt.bean.Club;
import ntpc.ccai.clubmgt.bean.ClubCategory;
import ntpc.ccai.clubmgt.service.ClubMemberService;
import ntpc.ccai.clubmgt.service.ClubSelectionService;
import ntpc.ccai.clubmgt.service.ClubSemService;
import ntpc.ccai.clubmgt.service.ClubService;
import ntpc.ccai.clubmgt.service.StuClassService;
import ntpc.ccai.clubmgt.bean.ClubSelection;
import ntpc.ccai.clubmgt.bean.ClubSelectionClass;
import ntpc.ccai.clubmgt.bean.ClubSelectionClub;
import ntpc.ccai.clubmgt.bean.ClubSem;
import ntpc.ccai.clubmgt.bean.StuClass;

import ntpc.ccai.clubmgt.util.StringUtils;

@WebServlet("/ClubSelectionMethod")
public class ClubSelectionMethod extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String MANAGEMENT_SERVLET = "/StuClub/ClubMgt";
    private static final String INIT = "/WEB-INF/jsp/ClubMgt/ClubSelectionMethod.jsp"; //社團選社設定
    private static final String ERROR = "/WEB-INF/jsp/ErrorPage.jsp"; // 失敗的 view
    private static final Logger logger = Logger.getLogger(ClubSelectionMethod.class);    
    
    private  ClubSelectionService clubSelectionService = new ClubSelectionService();
    private  ClubService clubservice = new ClubService();
    private  ClubSemService clubSemService = new ClubSemService();
    private  ClubMemberService clubMemberService = new ClubMemberService();
    private  StuClassService stuClassService = new StuClassService();
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
    

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
        String targetURL;
        
        HttpSession session = request.getSession();
        
        UserData ud = (UserData) session.getAttribute("ud"); // 取得 session 中的物件
        String sch_code = ud.getSch_code();
        Integer sbj_year = (Integer) session.getAttribute("sbj_year");
        Character sbj_sem = (Character) session.getAttribute("sbj_sem");
        
        if (error_msg == null) {
        //清單頁面
        	List<ClubSelection> clubselection = clubSelectionService.getClubSelectionsByYearAndSemGroups(sch_code,sbj_year,sbj_sem);
        	
        
        //詳細設定頁面
            List<ClubSem> clubsems  = clubSemService.getClubSemsByYearAndSem(sch_code, sbj_year, sbj_sem);
            //List<StuClass> stuclass = stuClassService.getStuClassesByYearAndSem(sch_code, sbj_year, sbj_sem);
            List<StuClass> stuclassG1 = stuClassService.getStuClassesByYearAndSemAndGrade(sch_code, sbj_year, sbj_sem, '1');
            List<StuClass> stuclassG2 = stuClassService.getStuClassesByYearAndSemAndGrade(sch_code, sbj_year, sbj_sem, '2');
            List<StuClass> stuclassG3 = stuClassService.getStuClassesByYearAndSemAndGrade(sch_code, sbj_year, sbj_sem, '3');
            
            
            
            
            if(clubselection != null && clubsems != null ) {
            	request.setAttribute("clubselection", clubselection);
            	request.setAttribute("clubsems", clubsems);
            	//request.setAttribute("stuclass", stuclass);
            	request.setAttribute("stuclassG1", stuclassG1);
            	request.setAttribute("stuclassG2", stuclassG2);
            	request.setAttribute("stuclassG3", stuclassG3);
            	
            } else {
            	error_msg ="Clubselection or club or class == null";
            }
        }
        
        
        try {
            request.setAttribute("ud", ud);
//            request.setAttribute("clubSelections", clubSelectionService.getClubSelectionsByClubNumAndYearAndSem(sch_code, club_num, sbj_year, sbj_sem));
            targetURL = INIT;
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error(ex);
            error_msg = "社團選社設定：載入資料發生錯誤";
            request.setAttribute("error_msg", error_msg); 
            targetURL = ERROR;
        }
        
        request.getRequestDispatcher(targetURL).forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String error_msg = null;
        String targetURL = null;
        boolean postResult = false;
        
        HttpSession session = request.getSession();
        UserData ud = (UserData) session.getAttribute("ud"); // 取得 session 中的物件
        String sch_code = ud.getSch_code();
        Integer sbj_year = (Integer) session.getAttribute("sbj_year");
        Character sbj_sem = (Character) session.getAttribute("sbj_sem");
        
        
        try {
        
        //抓取資料----------------------------------------------------
        String[] club_nums = request.getParameterValues("club_num"); 
        
        String[] grade_one = request.getParameterValues("grade_one_limit");
        String[] grade_two = request.getParameterValues("grade_two_limit");
        String[] grade_three = request.getParameterValues("grade_three_limit");
        
        
        Character cs_method = StringUtils.toCharacter( request.getParameter("cs_method"));
        Integer cs_lower_limit;
        Integer grade_limit;
        Integer class_limit;
        
        
        //條件篩選----------------------------------------------------
        if(cs_method.equals('0')) {
        	cs_lower_limit = Integer.parseInt(request.getParameter("cs_lower_limit"));
        } else {
        	cs_lower_limit = null;
        }
        
        Integer total_limit = Integer.parseInt(request.getParameter("total_limit"));
        
        if(request.getParameter("grade_limit") == null)
        	grade_limit = null ;//選填年級限制
        else 
        	grade_limit = Integer.parseInt(request.getParameter("grade_limit"));
        
        if(request.getParameter("class_limit") == null)
        	class_limit = null;//選填年級限制
        else
        	class_limit = Integer.parseInt(request.getParameter("class_limit"));//optional
        
        //格式調整
        Date cs_sdate = format.parse(request.getParameter("cs_sdate"));
        Date cs_edate = format.parse(request.getParameter("cs_edate"));
        
        
        	
        	ClubSelection clubselection = new ClubSelection();
        	clubselection.setSbj_year(sbj_year);
        	clubselection.setSbj_sem(sbj_sem);
        	clubselection.setInuse(false);//Need to rewrite
        	clubselection.setCs_sdate(cs_sdate);
        	clubselection.setCs_edate(cs_edate);
        	clubselection.setCs_method(cs_method);
        	clubselection.setCs_lower_limit(cs_lower_limit);
        	clubselection.setTotal_limit(total_limit);
        	clubselection.setGrade_limit(grade_limit);
        	clubselection.setClass_limit(class_limit);
        	
        	Set<ClubSelectionClass> set = clubselection.getClubSelectionClasses();
        	for (String cls_code : grade_one) {
        		ClubSelectionClass clubSelectionClass = new ClubSelectionClass();
        		clubSelectionClass.setCls_code(cls_code);
        		clubSelectionClass.setSbj_year(sbj_year);
        		clubSelectionClass.setSbj_sem(sbj_sem);
        		set.add(clubSelectionClass);
        	}
        	
        	Set<ClubSelectionClub> clubSelectionClubSet = clubselection.getClubSelectionClubs();
        	for (String thisClubNum : club_nums) {
        		ClubSelectionClub clubSelectionClubBean = new ClubSelectionClub();
        		clubSelectionClubBean.setClub_num(Integer.parseInt(thisClubNum));
        		clubSelectionClubBean.setSbj_year(sbj_year);
        		clubSelectionClubBean.setSbj_sem(sbj_sem);
        		clubSelectionClubSet.add(clubSelectionClubBean);
        		
        	}
        
        
        	if(clubSelectionService.insert(sch_code, clubselection)) {
        		targetURL = INIT;
        		postResult = true;
        	}

        } catch(IllegalArgumentException iae) {
        	iae.printStackTrace();
        	error_msg = "IllegalArgumentException　填值錯誤，請重新填寫";
        	
        } catch(Exception e){
        	e.printStackTrace();
        	error_msg = "Exception 系統錯誤";
        }
        if(error_msg == null) {
        	request.setAttribute("result",postResult);
        	request.getRequestDispatcher(targetURL).forward(request, response);
    	} else {
    		targetURL = ERROR;
    		request.setAttribute("error_msg", error_msg);
    		request.getRequestDispatcher(targetURL).forward(request, response);
    	}
    }
}