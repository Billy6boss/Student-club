package ntpc.ccai.bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.apache.log4j.Logger;

import ntpc.ccai.clubmgt.util.StringUtils;

/**
 * @author Aimee
 *
 */
public class MisSystemData {
    private Logger logger = Logger.getLogger(this.getClass());
    
    public MisSystemData() {}   
    
    private int              sys_num    = 0;
    private String           sys_cname  = "";
    private String           sys_link   = "";
    private int              sys_order  = 0 ;
    private int              sbj_year   = 0 ;
    private String           sbj_sem    = "";
    
    /**
     * @param pcon
     * @return
     * @throws Exception
     */
    public int queryData(Connection pcon) throws Exception {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int result = 0;

        String select = " SELECT * FROM MIS_SYSTEM a"
                      + " WHERE a.SYS_NUM =  ? OR a.SYS_LINK = ?";

        try {
            pstmt = pcon.prepareStatement(select);
            pstmt.setInt(1, this.sys_num);
            pstmt.setString(2, this.sys_link);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                this.getDataFromResultSet(rs);
                result = 1;
            }
        } catch (Exception ex) {
            logger.error("\nSQL ERROR:" + select);
            logger.error("\n sys_num:" + this.sys_num);
            logger.error("\n sys_link:" + this.sys_link);
            throw ex;
        } finally {
            if (rs != null)
                rs.close();
            if (pstmt != null)
                pstmt.close();
        }
        return result;
    }    
    
    // 從 ResultSet 取得資料
    public int getDataFromResultSet(ResultSet Prs) throws Exception {
        if (Prs != null) {
            this.sys_num = Prs.getInt("sys_num");
            this.sys_cname = StringUtils.trim(Prs.getString("sys_cname"));
            this.sys_link = StringUtils.trim(Prs.getString("sys_link"));
            this.sys_order = Prs.getInt("sys_order");
            this.sbj_year = Prs.getInt("sbj_year");
            this.sbj_sem = StringUtils.trim(Prs.getString("sbj_sem"));
            return 1;
        } // End of if (Prs != null) {
        return 0;
    }
    
    public int getSys_num() {
        return sys_num;
    }
    
    public void setSys_num(int sys_num) {
        this.sys_num = sys_num;
    }
    
    public String getSys_cname() {
        return sys_cname;
    }
    
    public void setSys_cname(String sys_cname) {
        this.sys_cname = sys_cname;
    }
    
    public String getSys_link() {
        return sys_link;
    }
    
    public void setSys_link(String sys_link) {
        this.sys_link = sys_link;
    }
    
    public int getSys_order() {
        return sys_order;
    }
    
    public void setSys_order(int sys_order) {
        this.sys_order = sys_order;
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
}