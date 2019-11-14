package ntpc.ccai.clubmgt.service;

import java.sql.Connection;
import java.util.List;
//import org.apache.log4j.Logger;
import ntpc.ccai.clubmgt.bean.SperiodMark;
import ntpc.ccai.clubmgt.bean.DBCon;
import ntpc.ccai.clubmgt.dao.SperiodMarkDao;
import ntpc.ccai.clubmgt.dao.impl.SperiodMarkDaoImpl;

public class SperiodMarkService {
	//private static final Logger logger = Logger.getLogger(SperiodMarkService.class);
    
    private SperiodMarkDao speriodmarkDao;
    
    public SperiodMarkService() {
    	speriodmarkDao = new SperiodMarkDaoImpl();
    }
    
    public SperiodMarkService(SperiodMarkDao speriodmarkDao) {
        this.speriodmarkDao = speriodmarkDao;
    }
	
	
	public List<SperiodMark> getAllSperiodMarks(String sch_code){
		List<SperiodMark> result = null;
        DBCon dbcon = null;
        Connection conn = null;
        
        try {
            dbcon = new DBCon(sch_code);
            conn = dbcon.getConnection();
            if (conn != null) {
                result = speriodmarkDao.getAllSperiodMarks(conn);
            }
        } finally {
            if (dbcon != null) {
                dbcon.closeCon();
            }
        }
        
        return result;
	}
	public SperiodMark getSperiodMarkBySperiod(String sch_code, Short speriod) {
		SperiodMark result = null;
        DBCon dbcon = null;
        Connection conn = null;
        
        try {
            dbcon = new DBCon(sch_code);
            conn = dbcon.getConnection();
            if (conn != null) {
                result = speriodmarkDao.getSperiodMarkBySperiod(conn, speriod);
            }
        } finally {
            if (dbcon != null) {
                dbcon.closeCon();
            }
        }
        
        return result;
	}

}
