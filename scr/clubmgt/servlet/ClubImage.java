package ntpc.ccai.clubmgt.servlet;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ntpc.ccai.bean.UserData;
import ntpc.ccai.clubmgt.bean.Club;
import ntpc.ccai.clubmgt.util.FileUtils;

@WebServlet("/ClubSettings")
@MultipartConfig
public class ClubImage extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String ERROR = "/WEB-INF/jsp/ErrorPage.jsp"; // 失敗的 view
    private static final String STU_PHOTO_PATH = "/WEB-INF/stu_photo/";
    private static final Logger logger = Logger.getLogger(ClubImage.class);    
    
    // service
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String error_msg = null; // 錯誤訊息
        request.setCharacterEncoding("UTF-8"); 

        // 收集資料
        HttpSession session = request.getSession(); // 取得 session 物件

        // 設定資料
        UserData ud = (UserData) session.getAttribute("ud"); // 取得 session 中的物件

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
        Club club = (Club) session.getAttribute("club");
        String rgno = request.getParameter("rgno");
        String path = null;
        String filename = null;
        
        if (rgno != null) {
            path = request.getServletContext().getRealPath("/").replace("StuClub", "school_roll") + STU_PHOTO_PATH + sch_code;
            filename = rgno + ".jpg";
        } else {
            if (club != null) {
                path = request.getServletContext().getRealPath("/") + FileUtils.UPLOAD_PATH + sch_code;
                filename = club.getClub_num().toString();
            }
        }
        
        if (path != null && filename != null) {
            InputStream fis = null;
            
            try {
                File file = new File(path, filename);
                if (file.exists() && file.isFile() && file.canRead()) {
                    fis = new BufferedInputStream(new FileInputStream(file));
                }
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
                logger.error(ex);
            } 
            if (fis != null) {
                FileUtils.dump(fis, response.getOutputStream());
            }
        } 
    }
}