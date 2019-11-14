package ntpc.ccai.clubmgt.service;

import java.sql.Connection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//import org.apache.log4j.Logger;
import ntpc.ccai.clubmgt.bean.ClubTime;
import ntpc.ccai.clubmgt.bean.DBCon;
import ntpc.ccai.clubmgt.dao.ClubTimeDao;
import ntpc.ccai.clubmgt.dao.impl.ClubTimeDaoImpl;

public class ClubTimeService {
	//private static final Logger logger = Logger.getLogger(ClubService.class);
    
    private ClubTimeDao clubTimeDao;
    
    public ClubTimeService() {
    	clubTimeDao = new ClubTimeDaoImpl();
    }
    
    public ClubTimeService(ClubTimeDao clubTimeDao) {
        this.clubTimeDao = clubTimeDao;
    }
    public Boolean insert(Connection conn, ClubTime clubTime) {
    	Boolean result = false;
    	
        if (conn != null) {
            result = clubTimeDao.insert(conn, clubTime);
        } else {
            throw new IllegalStateException("No existing connection.");
        }
                
        return result;
    }
    public Boolean delete(Connection conn, ClubTime clubTime) {
    	Boolean result = false;
        
    	if (conn != null) {
            result = clubTimeDao.delete(conn, clubTime);
        } else {
            throw new IllegalStateException("No existing connection.");
        }
                
        return result;
    }
    public List<ClubTime> getClubTimeByClubNumAndYearAndSem(String sch_code, Integer club_num, Integer sbj_year, Character sbj_sem){
    	List<ClubTime> result = null;
        DBCon dbcon = null;
        Connection conn = null;
        
        try {
            dbcon = new DBCon(sch_code);
            conn = dbcon.getConnection();
            if (conn != null) {
                result = clubTimeDao.getClubTimeByClubNumAndYearAndSem(conn, club_num, sbj_year, sbj_sem);
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
            result = clubTimeDao.delete(conn, club_num, sbj_year, sbj_sem);
        } else {
            throw new IllegalStateException("No existing connection.");
        }
                
        return result;
    }
    
    public Boolean update(Connection conn, Set<ClubTime> clubTimes, List<ClubTime> current) {
        Boolean result = false;
        
        if (conn != null) {
            Set<ClubTime> times = new HashSet<ClubTime>(current);               // put existing records to a map.
            if (clubTimes != null && !clubTimes.isEmpty()) {
                for (ClubTime clubTime : clubTimes) {                 // check if it already exists for each new record.
                    if (times.contains(clubTime)) {              
                        times.remove(clubTime);                     // if exists, ignore it and remove it from the map.
                        result = true;
                    } else {                                                               
                        result = clubTimeDao.insert(conn, clubTime);         // if not, then insert.
                    }
                    
                    if (!result) {
                        break;
                    }
                }
            } else {
                result = true;
            }
            if (result) {
                for (ClubTime clubTime : times) {              // delete records that is not in the new ones.
                    result = delete(conn, clubTime);
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
            result = clubTimeDao.copy(conn, from_year, from_sem, to_year, to_sem, club_nums);
        } else {
            throw new IllegalStateException("No existing connection.");
        }

        return result;
    }
}
