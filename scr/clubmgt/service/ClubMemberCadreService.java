package ntpc.ccai.clubmgt.service;

import java.sql.Connection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ntpc.ccai.clubmgt.bean.ClubMemberCadre;
import ntpc.ccai.clubmgt.bean.DBCon;
import ntpc.ccai.clubmgt.dao.ClubMemberCadreDao;
import ntpc.ccai.clubmgt.dao.impl.ClubMemberCadreDaoImpl;

public class ClubMemberCadreService {
//    private static final Logger logger = Logger.getLogger(ClubMemberCadreService.class);
    private ClubMemberCadreDao clubMemberCadreDao;
    
    public ClubMemberCadreService() {
        clubMemberCadreDao = new ClubMemberCadreDaoImpl();
    }
    
    public ClubMemberCadreService(ClubMemberCadreDao clubMemberCadreDao) {
        this.clubMemberCadreDao = clubMemberCadreDao;
    }
    
    public Boolean update(Connection conn, Set<ClubMemberCadre> clubMemberCadres, List<ClubMemberCadre> current) {
        Boolean result = false;
        
        if (conn != null) {
            Set<ClubMemberCadre> cadres = new HashSet<ClubMemberCadre>(current);       // put existing records to a set.
            
            if (clubMemberCadres != null && !clubMemberCadres.isEmpty()) {
                for (ClubMemberCadre clubMemberCadre : clubMemberCadres) {                 // check if it already exists for each new record.
                    if (cadres.contains(clubMemberCadre)) {                             
                        cadres.remove(clubMemberCadre);                                    // if exists, ignore it and remove it from the set.
                        result = true; 
                    } else {                                                               
                        result = clubMemberCadreDao.insert(conn, clubMemberCadre);         // if not, then insert.
                    }
                    
                    if (!result) {
                        break;
                    }
                }
            } else {
                result = true;
            } 
            if (result) {
                for (ClubMemberCadre clubMemberCadre : cadres) {                        // delete records that is not in the new ones.
                    result = delete(conn, clubMemberCadre);
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
    
    public Boolean delete(Connection conn, ClubMemberCadre clubMemberCadre) {
        Boolean result = false;
        
        if (conn != null) {
            result = clubMemberCadreDao.delete(conn, clubMemberCadre);
        } else {
            throw new IllegalStateException("No existing connection.");
        } 
        
        return result;
    }
    
    
    public List<ClubMemberCadre> getClubMemberCadreByClubNumAndYearAndSemAndRgno(String sch_code, Integer club_num, Integer sbj_year, Character sbj_sem, Integer rgno) {
        List<ClubMemberCadre> result = null;
        DBCon dbcon = null;
        Connection conn = null;
        
        try {
            dbcon = new DBCon(sch_code);
            conn = dbcon.getConnection();
            if (conn != null) {
                result = clubMemberCadreDao.getClubMemberCadreByClubNumAndYearAndSemAndRgno(conn, club_num, sbj_year, sbj_sem, rgno);
            }
        } finally {
            if (dbcon != null) {
                dbcon.closeCon();
            }
        }
        
        return result;
    }
    
    public Integer delete(Connection conn, Integer club_num, Integer sbj_year, Character sbj_sem, Integer rgno) {
        Integer result = null;
        
        if (conn != null) {
            result = clubMemberCadreDao.delete(conn, club_num, sbj_year, sbj_sem, rgno);
        } else {
            throw new IllegalStateException("No existing connection.");
        }
                
        return result;
    }
    
    public Integer delete(Connection conn, Integer club_num, Integer sbj_year, Character sbj_sem) {
        Integer result = null;
        
        if (conn != null) {
            result = clubMemberCadreDao.delete(conn, club_num, sbj_year, sbj_sem);
        } else {
            throw new IllegalStateException("No existing connection.");
        }
                
        return result;
    }
    
    public List<ClubMemberCadre> getClubMemberCadreByClubNumAndYearAndSem(Connection conn, Integer club_num, Integer sbj_year, Character sbj_sem) {
        List<ClubMemberCadre> result = null;
        
        if (conn != null) {
            result = clubMemberCadreDao.getClubMemberCadreByClubNumAndYearAndSem(conn, club_num, sbj_year, sbj_sem);
        } else {
            throw new IllegalStateException("No existing connection.");
        }
        
        return result;
    }
    
    
    
    // need to rewrite
    public List<ClubMemberCadre> getClubMemberCadreExperienceByRegnoOrIdno(String sch_code, String reg_no , String idno) {
        List<ClubMemberCadre> result = null;
        DBCon dbcon = null;
        
        try {
            dbcon = new DBCon(sch_code);
            result = clubMemberCadreDao.getClubMemberCadreExperienceByRegnoOrIdno(dbcon.getConnection(), reg_no , idno);
        } finally {
            if (dbcon != null) {
                dbcon.closeCon();
            }
        }
                
        return result;
    }
}
