package ntpc.ccai.bean;

import java.sql.ResultSet;

import ntpc.ccai.clubmgt.util.StringUtils;

public class MisFunction {    
    // attribute
    private int func_num = 0; 
    private String func_cname = "";
    private String func_link = "";
    private int func_group = 0;
    private int func_order = 0;
    private String dep_code = "";
    private String adg_code = "";
    private int sys_num =  0;
    
    /** 從 ResultSet 取得資料
     * @param pRs ResultSet物件
     * @return 1:設定成功, 0:設定失敗
     * @throws Exception
     */
    public int getDataFromResultSetInfo(ResultSet pRs) throws Exception {
        if (pRs != null) {
            this.func_num = pRs.getInt("func_num");
            this.func_cname = StringUtils.trim(pRs.getString("func_cname"));
            this.func_link = StringUtils.trim(pRs.getString("func_link"));           
            this.func_group = pRs.getInt("func_group");
            this.func_order = pRs.getInt("func_order");
            this.dep_code = pRs.getString("dep_code");
            this.adg_code = pRs.getString("adg_code");
            this.sys_num = pRs.getInt("sys_num");

            return 1;
        }
        return 0;
    }
    
    
    //getter & setter 
    public int getFunc_num() {
        return func_num;
    }
    
    public void setFunc_num(int func_num) {
        this.func_num = func_num;
    }
    
    public String getFunc_cname() {
        return func_cname;
    }
    
    public void setFunc_cname(String func_cname) {
        this.func_cname = func_cname;
    }
    
    public String getFunc_link() {
        return func_link;
    }
    
    public void setFunc_link(String func_link) {
        this.func_link = func_link;
    }

    public int getFunc_group() {
        return func_group;
    }

    public void setFunc_group(int func_group) {
        this.func_group = func_group;
    }

    public int getFunc_order() {
        return func_order;
    }
    
    public void setFunc_order(int func_order) {
        this.func_order = func_order;
    }

    public String getDep_code() {
        return dep_code;
    }

    public void setDep_code(String dep_code) {
        this.dep_code = dep_code;
    }

    public String getAdg_code() {
        return adg_code;
    }

    public void setAdg_code(String adg_code) {
        this.adg_code = adg_code;
    }
    
    public int getSys_num() {
        return sys_num;
    }

    public void setSys_num(int sys_num) {
        this.sys_num = sys_num;
    }

}
