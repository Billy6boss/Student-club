package ntpc.ccai.clubmgt.service;

import java.sql.Connection;
import java.util.List;
//import org.apache.log4j.Logger;
import ntpc.ccai.clubmgt.bean.ClubSelectionClass;
import ntpc.ccai.clubmgt.bean.DBCon;
import ntpc.ccai.clubmgt.dao.ClubSelectionClassDao;
import ntpc.ccai.clubmgt.dao.impl.ClubSelectionClassDaoImpl;

public class ClubSelectionClassService {
	//private static final Logger logger = Logger.getLogger(ClubSelectionClassService.class);
    
    private ClubSelectionClassDao clubselectionclassDao;
    
    public ClubSelectionClassService() {
    	clubselectionclassDao = new ClubSelectionClassDaoImpl();
    }
    
    public ClubSelectionClassService(ClubSelectionClassDao clubselectionclassDao) {
        this.clubselectionclassDao = clubselectionclassDao;
    }
	public Boolean insert(Connection conn, ClubSelectionClass clubSelectionClass) {
		Boolean result = null;
        
        if (conn != null) {
            result = clubselectionclassDao.insert(conn, clubSelectionClass);
        } else {
            throw new IllegalStateException("No existing connection.");
        }
                
        return result;
	}
	public Boolean delete(String sch_code, ClubSelectionClass clubSelectionClass) {
		Boolean result = false;
		DBCon dbcon = null;
        Connection conn = null;
        
        try {
            dbcon = new DBCon(sch_code);
            conn = dbcon.getConnection();
            if (conn != null) {
                result = clubselectionclassDao.delete(conn, clubSelectionClass);
            }
        } finally {
            if (dbcon != null) {
                dbcon.closeCon();
            }
        }
        
        return result;
	}
	public List<ClubSelectionClass> getClubSelectionClassesByCsNum(String sch_code, Integer cs_num){
		List<ClubSelectionClass> result = null;
		DBCon dbcon = null;
        Connection conn = null;
        
        try {
            dbcon = new DBCon(sch_code);
            conn = dbcon.getConnection();
            if (conn != null) {
                result = clubselectionclassDao.getClubSelectionClassesByCsNum(conn, cs_num);
            }
        } finally {
            if (dbcon != null) {
                dbcon.closeCon();
            }
        }
        
        return result;
	}
	
	public Integer delete(Connection conn, Integer cs_num) {
	    Integer result = null;
        
        if (conn != null) {
            result = clubselectionclassDao.delete(conn, cs_num);
        } else {
            throw new IllegalStateException("No existing connection.");
        }
                
        return result;
	}
	
	public List<ClubSelectionClass> getClubSelectionClassesByCsNum(Connection conn, Integer cs_num){
        List<ClubSelectionClass> result = null;
        
        if (conn != null) {
            result = clubselectionclassDao.getClubSelectionClassesByCsNum(conn, cs_num);
        } else {
            throw new IllegalStateException("No existing connection.");
        }
        
        return result;
    }
}
