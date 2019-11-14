package ntpc.ccai.bean;

import java.sql.Connection;
import java.sql.DriverManager;

/** 資料庫類別(db2)
 * @author ptchen
 */
public class DBCon {
// 國教署DB連線
 	private String     mySource    = "jdbc:db2://hsadb.ntut.edu.tw:50000/SCHOOL02";
// 	private String     mySource    = "jdbc:db2://hsatestdb.ntut.edu.tw:50000/SCHOOL01";  // 測試DB
 	private String     mySchema    = "COMMON";
 	private String     myPreSchema = "HS"; 
 	private String     myUID       = "ntutop";
 	private String     myPassword  = "taipei@tech!";

    private Connection connection = null;

    /** 建構子: 無參數，使用預設schema: COMMON
     */
    public DBCon() {}

    /** 建構子
     * @param pSchno 學校代碼，與目前操作的Schema有關(currentSchema)
     */
    public DBCon(String pSchno) {
        this.mySchema = this.myPreSchema + pSchno;
    }

    /** 建立資料庫連線，若連線已存在時，則先關閉現有連線，重新建立新連線
     * @throws Exception
     */
    public void BuildConnection() throws Exception {
        try {
            if (this.connection != null) {
                this.closeCon();
            }
            Class.forName("com.ibm.db2.jcc.DB2Driver");
            this.connection = DriverManager.getConnection(this.mySource + ":currentSchema=" + this.mySchema + ";", this.myUID, this.myPassword);
        } catch (Exception ex) {
            throw ex;
        }
    }

    /** 取得已連線的Connection物件，若連線不存在時則建立新連線
     * @return Connection物件
     * @throws 
     */
    public Connection getConnection() throws Exception {
        if (this.connection == null) {
            try {
                BuildConnection();
            } catch (Exception ex) {
                throw ex;
            }
        }
        return this.connection;
    }

    /** 關閉資料庫連線
     */
    public void closeCon() {
        try {
            if (this.connection != null) {
                this.connection.close();
            }
        } catch (Exception ex) {}
    }

}
