package ntpc.ccai.bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;

import ntpc.ccai.clubmgt.util.StringUtils;

/**
 * 學生基本的資料
 * @author Aimee
 *
 */
public class StuBasisData {
    private Logger logger = Logger.getLogger(this.getClass());
    
    private int rgno = 0;         // 學生序號 
    private String reg_no = "";   // 學生學號
    private String cname = "";    // 學生中文姓名 
    private String ename = "";    // 學生英文姓名  
    private String sex = "";      // 性別
    private String birthday = ""; // 生日
    private String idno = "";     // 身分證號碼
    private int cmat_year = 0;    // 課程標準年度 
    private String adg_code = "";  // 部別代碼
    private String adg_cname = ""; // 部別名稱
    private String mat_code = "";  // 學制代碼
    private String mat_cname = ""; // 學制名稱
    private String div_code = ""; // 科別代碼
    private String sts_code = ""; // 在學狀態代碼
    private String sts_cname = ""; // 在學狀態名稱
    
    
    /**
     * 取得資料
     * @param rs ResultSet 物件
     * @return 筆數
     * @throws SQLException
     * @author
     */
    public int getDataFromResultAdgMatSet(ResultSet rs) throws SQLException{
        int result = 0;
        try{
            this.adg_code  = StringUtils.trim(rs.getString("adg_code"));
            this.adg_cname = StringUtils.trim(rs.getString("adg_cname"));
            this.mat_code  = StringUtils.trim(rs.getString("mat_code"));
            this.mat_cname = StringUtils.trim(rs.getString("mat_cname"));
        }catch (SQLException ex){
            this.logger.error(ex);
            throw ex;
        }
        return result;
    }
    
    
    /**
     * 取得資料
     * @param rs ResultSet 物件
     * @return 筆數
     * @throws SQLException
     * @author
     */
    public int getDataFromResultSet(ResultSet rs) throws SQLException{
        int result = 0;
        try{
            this.rgno   = rs.getInt("rgno");
            this.reg_no = StringUtils.trim(rs.getString("reg_no"));
            this.cname  = StringUtils.trim(rs.getString("cname"));
            this.ename  = StringUtils.trim(rs.getString("ename"));
            this.sex    = StringUtils.trim(rs.getString("sex"));
            this.idno   = StringUtils.trim(rs.getString("idno"));
            this.cmat_year   = rs.getInt("cmat_year");
            this.mat_code    = StringUtils.trim(rs.getString("mat_code"));
            this.div_code    = StringUtils.trim(rs.getString("div_code"));
            this.sts_code    = StringUtils.trim(rs.getString("sts_code"));
        }catch (SQLException ex){
            this.logger.error(ex);
            throw ex;
        }
        return result;
    }
    

    
    /**
     * @param Pcon
     * @return
     * @throws Exception
     */
    public int queryData(Connection Pcon) throws Exception {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int result = 0;

        String select = " SELECT  a.*, c.ADG_CODE, c.ADG_CNAME, d.MAT_CNAME "
                      + " FROM STU_BASIS a, MATRIC_OF_ADMIN b , ADMIN_GROUP c , matric d "
                      + " WHERE a.rgno = ? "
                      + "   AND a.MAT_CODE = b.MAT_CODE"
                      + "   AND b.ADG_CODE = c.ADG_CODE"
                      + "   AND b.MAT_CODE = d.MAT_CODE" ;
        try {
            pstmt = Pcon.prepareStatement(select);
            pstmt.setInt(1, this.rgno);
            rs = pstmt.executeQuery();
            if (rs.next()) {
               this.getDataFromResultSet(rs);
               this.getDataFromResultAdgMatSet(rs);

                result = 1;
            } // End of if (rs.next()) {

        } catch (Exception ex) {
            throw ex;
        } finally {
            if (rs != null) rs.close();
            if (pstmt!= null) pstmt.close();
        }

        return result;
    }
    
    public int getRgno() {
        return rgno;
    }
    
    public void setRgno(int rgno) {
        this.rgno = rgno;
    }
    
    public String getReg_no() {
        return reg_no;
    }
    
    public void setReg_no(String reg_no) {
        this.reg_no = reg_no;
    }
    public String getCname() {
        return cname;
    }
    
    public void setCname(String cname) {
        this.cname = cname;
    }
    
    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEname() {
        return ename;
    }
    
    public void setEname(String ename) {
        this.ename = ename;
    }
    
    public String getSex() {
        return sex;
    }
    
    public void setSex(String sex) {
        this.sex = sex;
    }
    
    public String getIdno() {
        return idno;
    }
    
    public void setIdno(String idno) {
        this.idno = idno;
    }
    
    public int getCmat_year() {
        return cmat_year;
    }
    
    public void setCmat_year(int cmat_year) {
        this.cmat_year = cmat_year;
    }
    
    public String getMat_code() {
        return mat_code;
    }
    
    public void setMat_code(String mat_code) {
        this.mat_code = mat_code;
    }
    
    public String getMat_cname() {
        return mat_cname;
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

    public void setMat_cname(String mat_cname) {
        this.mat_cname = mat_cname;
    }

    public String getDiv_code() {
        return div_code;
    }
    
    public void setDiv_code(String div_code) {
        this.div_code = div_code;
    }
    
    public String getSts_code() {
        return sts_code;
    }
    
    public void setSts_code(String sts_code) {
        this.sts_code = sts_code;
    }

    public String getSts_cname() {
        return sts_cname;
    }

    public void setSts_cname(String sts_cname) {
        this.sts_cname = sts_cname;
    }
      
}
