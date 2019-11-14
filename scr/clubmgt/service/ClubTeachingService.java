package ntpc.ccai.clubmgt.service;

import java.sql.Connection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//import org.apache.log4j.Logger;
import ntpc.ccai.clubmgt.bean.ClubTeaching;
import ntpc.ccai.clubmgt.bean.DBCon;
import ntpc.ccai.clubmgt.dao.ClubTeachingDao;
import ntpc.ccai.clubmgt.dao.impl.ClubTeachingDaoImpl;

public class ClubTeachingService {
	//private static final Logger logger = Logger.getLogger(ClubTeachingService.class);
    
    private ClubTeachingDao clubTeachingDao;
    
    public ClubTeachingService() {
        clubTeachingDao = new ClubTeachingDaoImpl();
    }
    
    public ClubTeachingService(ClubTeachingDao clubteachingDao) {
        this.clubTeachingDao = clubteachingDao;
    }
	
	public Boolean insert(Connection conn, ClubTeaching clubTeaching) {
		Boolean result = false;
		
        if (conn != null) {
            result = clubTeachingDao.insert(conn, clubTeaching);
        } else {
            throw new IllegalStateException("No existing connection.");
        }
                
        return result;
	}
	public Boolean delete(Connection conn, ClubTeaching clubTeaching) {
		Boolean result = false;
        
		if (conn != null) {
            result = clubTeachingDao.delete(conn, clubTeaching);
        } else {
            throw new IllegalStateException("No existing connection.");
        }
                
        return result;
	}
	public List<ClubTeaching> getClubTeachingByClubNumAndYearAndSem(String sch_code, Integer club_num, Integer sbj_year, Character sbj_sem){
		List<ClubTeaching> result = null;
        DBCon dbcon = null;
        Connection conn = null;
        
        try {
            dbcon = new DBCon(sch_code);
            conn = dbcon.getConnection();
            if (conn != null) {
                result = clubTeachingDao.getClubTeachingByClubNumAndYearAndSem(conn, club_num, sbj_year, sbj_sem);
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
            result = clubTeachingDao.delete(conn, club_num, sbj_year, sbj_sem);
        } else {
            throw new IllegalStateException("No existing connection.");
        }
                
        return result;
	}
	
	public Boolean update(Connection conn, Set<ClubTeaching> clubTeachings, List<ClubTeaching> current) {
	    Boolean result = false;
        
        if (conn != null) {
            Set<ClubTeaching> teachings = new HashSet<ClubTeaching>(current);   // put existing records to a map.
            
            if (clubTeachings != null && !clubTeachings.isEmpty()) {
                for (ClubTeaching clubTeaching : clubTeachings) {                   // check if it already exists for each new record.
                    if (teachings.contains(clubTeaching)) {              
                        teachings.remove(clubTeaching);                             // if exists, ignore it and remove it from the map.
                        result = true;
                    } else {                                                               
                        result = clubTeachingDao.insert(conn, clubTeaching);        // if not, then insert.
                    }
                    
                    if (!result) {
                        break;
                    }
                }
            } else {
                result = true;
            }
            if (result) {
                for (ClubTeaching clubTeaching : teachings) {                   // delete records that is not in the new ones.
                    result = delete(conn, clubTeaching);
                    if (!result) {
                        break;
                    }
                }
            }
        } else {
            throw new IllegalStateException("No existing connection.");
        } 
        
        return result;
	}
    
    public Integer copy(Connection conn, Integer from_year, Character from_sem, Integer to_year, Character to_sem, Set<Integer> club_nums) {
        Integer result = null;

        if (conn != null) {
            result = clubTeachingDao.copy(conn, from_year, from_sem, to_year, to_sem, club_nums);
        } else {
            throw new IllegalStateException("No existing connection.");
        }

        return result;
    }
	
	
	// need to rewrite
	public List<ClubTeaching> getClubTeachingByStaffcodeAndIdno(String sch_code,String staffcode, String idno, Integer sbj_year, Character sbj_sem){
        List<ClubTeaching> result = null;
        DBCon dbcon = null;
        
        try {
            dbcon = new DBCon(sch_code);
            result = clubTeachingDao.getClubTeachingByStaffcodeAndIdno(dbcon.getConnection(), staffcode,idno, sbj_year, sbj_sem);
        } finally {
            if (dbcon != null) {
                dbcon.closeCon();
            }
        }
        
        return result;
    }
	
	public Boolean chkctcode(String sch_code, Integer club_num, Integer sbj_year, Character sbj_sem, String ct_code) {
        Boolean result = null;
        DBCon dbcon = null;
        
        try {
            dbcon = new DBCon(sch_code);
            result = clubTeachingDao.chkctcode(dbcon.getConnection(),  club_num,  sbj_year,  sbj_sem,  ct_code);
        } finally {
            if (dbcon != null) {
                dbcon.closeCon();
            }
        }
                
        return result;
    }
    public Boolean updatactcode(String sch_code, Integer club_num, Integer sbj_year, Character sbj_sem, String ct_code, String staff_code) {
        Boolean result = null;
        DBCon dbcon = null;
        
        try {
            dbcon = new DBCon(sch_code);
            result = clubTeachingDao.updatactcode(dbcon.getConnection(),  club_num,  sbj_year,  sbj_sem,  ct_code,staff_code);
        } finally {
            if (dbcon != null) {
                dbcon.closeCon();
            }
        }
                
        return result;
    }
}
