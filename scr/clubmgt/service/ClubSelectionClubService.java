
package ntpc.ccai.clubmgt.service;

import java.sql.Connection;
import java.util.List;
//import org.apache.log4j.Logger;
import ntpc.ccai.clubmgt.bean.ClubSelectionClub;
import ntpc.ccai.clubmgt.bean.DBCon;
import ntpc.ccai.clubmgt.dao.ClubSelectionClubDao;
import ntpc.ccai.clubmgt.dao.impl.ClubSelectionClubDaoImpl;

public class ClubSelectionClubService {
    
    private ClubSelectionClubDao clubselectionClubDao;
    
    public ClubSelectionClubService() {
    	clubselectionClubDao = new ClubSelectionClubDaoImpl();
    }
    
    public ClubSelectionClubService(ClubSelectionClubDao clubselectionClubDao) {
        this.clubselectionClubDao = clubselectionClubDao;
    }
    
	public Boolean insert(Connection conn, ClubSelectionClub clubSelectionClub) {
		Boolean result = null;
        
        if (conn != null) {
            result = clubselectionClubDao.insert(conn, clubSelectionClub);
        } else {
            throw new IllegalStateException("No existing connection.");
        }
                
        return result;
	}
	public Boolean delete(String sch_code, ClubSelectionClub clubSelectionClub) {
		Boolean result = false;
		DBCon dbcon = null;
        Connection conn = null;
        
        try {
            dbcon = new DBCon(sch_code);
            conn = dbcon.getConnection();
            if (conn != null) {
                result = clubselectionClubDao.delete(conn, clubSelectionClub);
            }
        } finally {
            if (dbcon != null) {
                dbcon.closeCon();
            }
        }
        
        return result;
	}
	public List<ClubSelectionClub> getClubSelectionClassesByCsNum(String sch_code, Integer cs_num){
		List<ClubSelectionClub> result = null;
		DBCon dbcon = null;
        Connection conn = null;
        
        try {
            dbcon = new DBCon(sch_code);
            conn = dbcon.getConnection();
            if (conn != null) {
                result = clubselectionClubDao.getClubSelectionClubByCsNum(conn, cs_num);
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
            result = clubselectionClubDao.deleteByCsNum(conn, cs_num);
        } else {
            throw new IllegalStateException("No existing connection.");
        }
                
        return result;
	}
	
	public List<ClubSelectionClub> getClubSelectionClassesByCsNum(Connection conn, Integer cs_num){
        List<ClubSelectionClub> result = null;
        
        if (conn != null) {
            result = clubselectionClubDao.getClubSelectionClubByCsNum(conn, cs_num);
        } else {
            throw new IllegalStateException("No existing connection.");
        }
        
        return result;
    }
}
