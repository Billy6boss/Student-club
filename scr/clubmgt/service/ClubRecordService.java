package ntpc.ccai.clubmgt.service;

import java.sql.Connection;
import java.util.List;

//import org.apache.log4j.Logger;

import ntpc.ccai.clubmgt.bean.ClubRecord;
import ntpc.ccai.clubmgt.bean.DBCon;
import ntpc.ccai.clubmgt.dao.ClubRecordDao;
import ntpc.ccai.clubmgt.dao.impl.ClubRecordDaoImpl;

public class ClubRecordService {
	//private static final Logger logger = Logger.getLogger(ClubRecordService.class);
	private ClubRecordDao clubRecordDao;
    
    public ClubRecordService() {
    	clubRecordDao = new ClubRecordDaoImpl();
    }
    
    public ClubRecordService(ClubRecordDao clubRecordDao) {
        this.clubRecordDao = clubRecordDao;
    }
	public ClubRecord insert(String sch_code, ClubRecord clubRecord) {
		ClubRecord result = null;
        DBCon dbcon = null;
        Connection conn = null;
        
        try {
            dbcon = new DBCon(sch_code);
            conn = dbcon.getConnection();
            if (conn != null) {
                result = clubRecordDao.insert(conn, clubRecord);
            }
        } finally {
            if (dbcon != null) {
                dbcon.closeCon();
            }
        }
        
        return result;
	}
	public Boolean update(String sch_code, ClubRecord clubRecord) {
		Boolean result = false;
		DBCon dbcon = null;
        Connection conn = null;
        
        try {
            dbcon = new DBCon(sch_code);
            conn = dbcon.getConnection();
            if (conn != null) {
                result = clubRecordDao.update(conn, clubRecord);
            }
        } finally {
            if (dbcon != null) {
                dbcon.closeCon();
            }
        }
        
        return result;
    }
	public Boolean delete(String sch_code, Integer cr_num) {
		Boolean result = false;
		DBCon dbcon = null;
        Connection conn = null;
        
        try {
            dbcon = new DBCon(sch_code);
            conn = dbcon.getConnection();
            if (conn != null) {
                result = clubRecordDao.delete(conn, cr_num);
            }
        } finally {
            if (dbcon != null) {
                dbcon.closeCon();
            }
        }
        
        return result;
    }
	public List<ClubRecord> getClubRecordsByClubNumAndYearAndSem(String sch_code, Integer club_num, Integer sbj_year, Character sbj_sem){
		List<ClubRecord> result = null;
		DBCon dbcon = null;
        Connection conn = null;
        
        try {
            dbcon = new DBCon(sch_code);
            conn = dbcon.getConnection();
            if (conn != null) {
                result = clubRecordDao.getClubRecordsByClubNumAndYearAndSem(conn, club_num, sbj_year, sbj_sem);
            }
        } finally {
            if (dbcon != null) {
                dbcon.closeCon();
            }
        }
        
        return result;
    }

	public Integer delete(Connection conn, Integer club_num, Integer sbj_year, Character sbj_sem) {
	    Integer result = null;
        
        if (conn != null) {
            result = clubRecordDao.delete(conn, club_num, sbj_year, sbj_sem);
        } else {
            throw new IllegalStateException("No existing connection.");
        }
                
        return result;
	}
	
	// need to rewrite
	
	public List<ClubRecord> getClubRecordsByRegnoAndIdno(String sch_code, String reg_no, String idno){
        List<ClubRecord> result = null;
        DBCon dbcon = null;
        
        try {
            dbcon = new DBCon(sch_code);
            result = clubRecordDao.getClubRecordsByRegnoAndIdno(dbcon.getConnection(), reg_no, idno);
        } finally {
            if (dbcon != null) {
                dbcon.closeCon();
            }
        }
        
        return result;
    }
}
