package ntpc.ccai.bean; // package

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import ntpc.bean.MisFunctionList;
import ntpc.ccai.clubmgt.util.StringUtils;

/** 使用者登入資料類別
 * @author vivi
 */
public class UserData {

    // attribute
    private String              sch_code    = "";                       // 學校代碼
    private String              sch_cname   = "";                       // 學校名稱    
    private String              sch_zipcode = "";                       // 學校郵遞區號    
    private String              sch_address = "";                       // 學校地址
    private String              sch_telno   = "";                       // 學校電話
    
    private String              staff_code  = "";                       // 使用者
    private String              staff_cname = "";                       // 使用者姓名
    private String              role_code   = "";                       // 角色代碼
    private String              role_cname  = "";                       // 角色名稱
    private String              ip_address  = "";                       // IP位置
    private String              email       = "";                       // email
    
    // 來自資料表「mis_system」by Aimee
    private int                 sbj_year    = 0;                        // 目前學年度
    private String              sbj_sem     = "";                       // 目前學期

    private String              adg_code    = "";  
    private String              adg_cname   = ""; 
    
    private MisFunctionList     user_func   = new MisFunctionList();    // 使用者可用功能
   
    /**
     * 從 ResultSet 取得資料
     * @throws Exception 
     */
    public int getDataFromResultSet(ResultSet Prs) throws Exception {

        if (Prs != null) {
            this.sch_code         = StringUtils.trim(Prs.getString("sch_code"));
            this.sch_cname        = StringUtils.trim(Prs.getString("sch_cname"));
            this.staff_code       = StringUtils.trim(Prs.getString("staff_code"));
            this.staff_cname      = StringUtils.trim(Prs.getString("staff_cname"));

            this.sch_zipcode      = StringUtils.trim(Prs.getString("sch_zipcode"));
            this.sch_address      = StringUtils.trim(Prs.getString("sch_address"));
            this.sch_telno        = StringUtils.trim(Prs.getString("sch_telno"));
            return 1;
        }
        return 0;
    }
    
    /**
     * 查詢使用者(學生)-基本資料表
     * @param pCon :資料庫連線
     * @return result :筆數
     * @throws Exception
     */
    public int queryDataStu(Connection pCon, String sch_code, String rgno) throws Exception {
        int index = 1;
        int result = 0;
        ResultSet rs = null;
        PreparedStatement pstmt = null;   
        
        String select = " SELECT a.rgno AS staff_code, a.cname AS staff_cname, b.sch_code, b.sch_cname, b.SCH_ZIPCODE, b.SCH_ADDRESS, b.SCH_TELNO "
                      + " FROM stu_basis a, vschool b "
                      + " WHERE a.rgno = ? "
                      + " AND b.sch_code = ? ";  
        
        try {
            pstmt = pCon.prepareStatement(select);
            pstmt.setString(index++, rgno);
            pstmt.setString(index++, sch_code);
            rs = pstmt.executeQuery();            
            if (rs.next()) {
                this.getDataFromResultSet(rs);
                result++;
            }
        } catch(Exception ex) {
            throw ex;
        } finally {
            if (rs != null)     rs.close();
            if (pstmt != null)  pstmt.close();
        } 
        return result;
    }
    
    /**
     * 查詢使用者-基本資料表
     * @param pCon :資料庫連線
     * @return result :筆數
     * @throws Exception
     */
    public int queryData(Connection pCon, String sch_code, String staff_code) throws Exception {
        int index = 1;
        int result = 0;
        ResultSet rs = null;
        PreparedStatement pstmt = null;   
        
        String select = " SELECT a.staff_code, a.staff_cname, b.sch_code, b.sch_cname, b.SCH_ZIPCODE, b.SCH_ADDRESS, b.SCH_TELNO "
                      + " FROM staff a, vschool b "
                      + " WHERE a.staff_code = ? "
                      + " AND b.sch_code = ? ";  
        
        try {
            pstmt = pCon.prepareStatement(select);
            pstmt.setString(index++, staff_code);
            pstmt.setString(index++, sch_code);
            rs = pstmt.executeQuery();            
            if (rs.next()) {
                this.getDataFromResultSet(rs);
                result++;
            }
        } catch(Exception ex) {
            throw ex;
        } finally {
            if (rs != null)     rs.close();
            if (pstmt != null)  pstmt.close();
        } 
        return result;
    }
    
    public String getSch_code() {
        return sch_code;
    }    
    public void setSch_code(String sch_code) {
        this.sch_code = sch_code;
    }      
    public String getSch_cname() {
        return sch_cname;
    }    
    public void setSch_cname(String sch_cname) {
        this.sch_cname = sch_cname;
    }    
    public String getStaff_code() {
        return staff_code;
    }    
    public void setStaff_code(String staff_code) {
        this.staff_code = staff_code;
    }        
    public String getStaff_cname() {
        return staff_cname;
    }    
    public void setStaff_cname(String staff_cname) {
        this.staff_cname = staff_cname;
    }
    public String getRole_code() {
        return role_code;
    }    
    public void setRole_code(String role_code) {
        this.role_code = role_code;
    }    
    public String getRole_cname() {
        return role_cname;
    }    
    public void setRole_cname(String role_cname) {
        this.role_cname = role_cname;
    }
    public String getIp_address() {
        return ip_address;
    }    
    public void setIp_address(String ip_address) {
        this.ip_address = ip_address;
    }       
    public String getEmail() {
        return email;
    }    
    public void setEmail(String email) {
        this.email = email;
    }  
    public int getSbj_year() {
        return sbj_year;
    }    
    public void setSbj_year(int sbj_year) {
        this.sbj_year = sbj_year;
    }
    public String getSbj_sem() {
        return sbj_sem;
    }    
    
    public void setSbj_sem(String sbj_sem) {
        this.sbj_sem = sbj_sem;
    }  
    public MisFunctionList getUser_func() {
        return user_func;
    }    
    public void setUser_func(MisFunctionList user_func) {
        this.user_func = user_func;
    }

    public String getAdg_code() {
        return adg_code;
    }

    public void setAdg_code(String adg_code) {
        this.adg_code = adg_code;
    }

    public String getAdg_cname() {
        return adg_cname;
    }

    public void setAdg_cname(String adg_cname) {
        this.adg_cname = adg_cname;
    }

    public String getSch_zipcode() {
        return sch_zipcode;
    }

    public void setSch_zipcode(String sch_zipcode) {
        this.sch_zipcode = sch_zipcode;
    }

    public String getSch_address() {
        return sch_address;
    }

    public void setSch_address(String sch_address) {
        this.sch_address = sch_address;
    }

    public String getSch_telno() {
        return sch_telno;
    }

    public void setSch_telno(String sch_telno) {
        this.sch_telno = sch_telno;
    }   
}