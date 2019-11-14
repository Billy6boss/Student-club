package ntpc.ccai.bean;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import ntpc.bean.DBCon;
import ntpc.bean.UserInfo;

/**
 * 系統功能表
 * @author vivi
 */
public class MisFunctionList {
    
    private ArrayList<MisFunction> arrayList = new ArrayList<MisFunction>(0); // 資料集合
    
    // constructor
    public MisFunctionList() {}
    
    /** 共有幾個物件資料
     * @return 物件個數
     */
    public int size() {
        return this.arrayList.size();
    }

    /** 取得第pIndex個物件
     * @param pIndex 物件索引
     * @return null:無符合條件資料, others:第pIndex個物件
     */
    public MisFunction get(int pIndex) {
        MisFunction result = null;

        if (pIndex < this.arrayList.size()) {
            result = this.arrayList.get(pIndex);
        }
        return result;
    }

    /** 加入一個物件
     * @param pObj 物件資料
     */
    public void add(MisFunction pObj) {
        if (this.arrayList == null) {
            this.arrayList = new ArrayList<MisFunction>(0);
        }
        this.arrayList.add(pObj);
    }

    /** 依功能群組取得物件
     * @param pFunc_group 功能群組
     * @return null:無符合條件資料, others:物件集合
     */
    public MisFunctionList getByFunc_group(int pFunc_group) {
        MisFunctionList result = null;

        for (int i=0 ; i<this.arrayList.size() ; i++){
            MisFunction misFunction = this.arrayList.get(i);
            if (misFunction.getFunc_group() == pFunc_group) {
                if (result == null) {
                    result = new MisFunctionList();
                }
                result.add(misFunction);
            }
        }
        return result;
    }

    /** [職員]檢查有否登入權限，有權限則撈到function list
     * @param pUserInfo 
     * @param URI 系統首頁連結
     * @param ip 登入者ip
     * @return result=0或size=0表示沒有權限 ; result>0或size>0表示有權限
     * @throws Exception     
     */
    public int checkFunctionList (UserInfo pUserInfo, String URI, String ip) throws Exception {
        int result = 0;        
        ResultSet rs = null;
        PreparedStatement ps = null;
//        int sys_num = 0;
        DBCon dbc = new DBCon(pUserInfo.getSid());
        
        String select = " SELECT b.*, c.STAFF_CODE "
                      + " FROM MIS_SYSTEM a JOIN MIS_FUNCTION b ON a.SYS_NUM = b.SYS_NUM "
                      + "              LEFT JOIN MIS_POWER c ON b.FUNC_NUM = c.FUNC_NUM AND c.staff_code = ? AND 'sch' = ? "                                  
                      + "              LEFT JOIN STAFF d ON c.STAFF_CODE = d.STAFF_CODE AND d.RET_FLAG IS NULL "
                      + " WHERE a.SYS_LINK = ? "                      
                      + " ORDER BY b.FUNC_GROUP, b.FUNC_ORDER, b.FUNC_NUM ";
        try {
            ps = dbc.getConnection().prepareStatement(select);
            ps.setString(1, pUserInfo.getUid());
            ps.setString(2, pUserInfo.getRole());
            ps.setString(3, URI);            
            rs = ps.executeQuery();
            while (rs.next()) {
                if (rs.getString("staff_code") != null) {
                    MisFunction misFunc = new MisFunction();
                    misFunc.getDataFromResultSetInfo(rs);
                    this.arrayList.add(misFunc);
                    result++ ;
                }
//                sys_num = rs.getInt("sys_num");
            }
         
            // mis_weblog登入檢查紀錄
//            String org_data = "";
//            if(result == 0){
//                org_data = "resutl=False|sys_num=" + sys_num;
//            }else{
//                org_data = "resutl=Success|sys_num=" + sys_num;
//            }
            //WebLog.addLog(dbc.getConnection(), String.valueOf(pUserInfo.getUid()), ip, "mis_system", "sys_link=" + URI, "Login", org_data);
        } catch (Exception ex) {
            throw ex;            
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            dbc.closeCon(); // 關閉 Connection
        }
     
        return result;
    }
    
    /** [教師]檢查有否登入權限，有權限則撈到function list
     * @param pUserInfo 
     * @param URI 系統首頁連結
     * @param ip 登入者ip
     * @return result=0或size=0表示沒有權限 ; result>0或size>0表示有權限
     * @throws Exception     
     */
    public int checkTeaFunctionList (UserInfo pUserInfo, String URI, String ip) throws Exception {
        int result = 0;        
        ResultSet rs = null;
        PreparedStatement ps = null;
//        int sys_num = 0;
        DBCon dbc = new DBCon(pUserInfo.getSid());
        
        String select = " SELECT b.*, c.STAFF_CODE "
                      + " FROM MIS_SYSTEM_TEA a JOIN MIS_FUNCTION_TEA b ON a.SYS_NUM = b.SYS_NUM "
                      + "                  LEFT JOIN STAFF c ON c.STAFF_CODE = ? AND 'tea' = ? AND c.RET_FLAG IS NULL "
                      + " WHERE a.SYS_LINK = ? "
                      + " ORDER BY b.FUNC_GROUP, b.FUNC_ORDER, b.FUNC_NUM ";
        try {
            ps = dbc.getConnection().prepareStatement(select);
            ps.setString(1, pUserInfo.getUid());
            ps.setString(2, pUserInfo.getRole());
            ps.setString(3, URI);            
            rs = ps.executeQuery();
            while (rs.next()) {
                if (rs.getString("staff_code") != null) {
                    MisFunction misFunc = new MisFunction();
                    misFunc.getDataFromResultSetInfo(rs);
                    this.arrayList.add(misFunc);
                    result++ ;
                }
//                sys_num = rs.getInt("sys_num");
            }
         
            // mis_weblog登入檢查紀錄
//            String org_data = "";
//            if(result == 0){
//                org_data = "resutl=False|sys_num=" + sys_num;
//            }else{
//                org_data = "resutl=Success|sys_num=" + sys_num;
//            }
            //WebLog.addLog(dbc.getConnection(), String.valueOf(pUserInfo.getUid()), ip, "mis_system_tea", "sys_link=" + URI, "Login", org_data);
        } catch (Exception ex) {
            throw ex;            
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            dbc.closeCon(); // 關閉 Connection
        }
     
        return result;
    }
    
    /** [學生]檢查有否登入權限，有權限則撈到function list
     * @param pUserInfo 
     * @param URI 系統首頁連結
     * @param ip 登入者ip
     * @return result=0或size=0表示沒有權限 ; result>0或size>0表示有權限
     * @throws Exception     
     */
    public int checkStuFunctionList (UserInfo pUserInfo, String URI, String ip) throws Exception {
        int result = 0;        
        ResultSet rs = null;
        PreparedStatement ps = null;
//        int sys_num = 0;
        DBCon dbc = new DBCon(pUserInfo.getSid());
        
        String select = " SELECT b.*, c.RGNO "
                      + " FROM MIS_SYSTEM_STU a JOIN MIS_FUNCTION_STU b ON a.SYS_NUM = b.SYS_NUM "
                      + "                  LEFT JOIN STU_BASIS c ON c.RGNO = ? AND 'std' = ? AND c.STS_CODE = 0 "
                      + " WHERE a.SYS_LINK = ? "
                      + " ORDER BY b.FUNC_GROUP, b.FUNC_ORDER, b.FUNC_NUM ";
        try {
            ps = dbc.getConnection().prepareStatement(select);
            ps.setInt(   1, Integer.parseInt(pUserInfo.getUid()));
            ps.setString(2, pUserInfo.getRole());
            ps.setString(3, URI);  
            
            rs = ps.executeQuery();
            while (rs.next()) {
                if (rs.getString("rgno") != null) {
                    MisFunction misFunc = new MisFunction();
                    misFunc.getDataFromResultSetInfo(rs);
                    this.arrayList.add(misFunc);
                    result++ ;
                }
//                sys_num = rs.getInt("sys_num");
            }
         
            // mis_weblog登入檢查紀錄
//            String org_data = "";
//            if(result == 0){
//                org_data = "resutl=False|sys_num=" + sys_num;
//            }else{
//                org_data = "resutl=Success|sys_num=" + sys_num;
//            }
            //WebLog.addLog(dbc.getConnection(), String.valueOf(pUserInfo.getUid()), ip, "mis_system_stu", "sys_link=" + URI, "Login", org_data);
        } catch (Exception ex) {
            throw ex;            
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            dbc.closeCon(); // 關閉 Connection
        }
     
        return result;
    }

    /** 檢查是否有使用權限
     * @param Pfunc_link
     * @return
     */
    public MisFunction getByFunction(String Pfunc_link) {
        MisFunction result = null;
        
        if (this.arrayList != null) {
            for (int i=0 ; i<this.arrayList.size() ; i++) {
                MisFunction sfd = this.arrayList.get(i);

                if (sfd.getFunc_link().equals(Pfunc_link)) {
                    result = sfd;
                    break;
                }
            }
        }
        return result;
    }
}
