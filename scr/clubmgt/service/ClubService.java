package ntpc.ccai.clubmgt.service;

import java.sql.Connection;
import java.util.List;

import ntpc.ccai.clubmgt.bean.Club;
import ntpc.ccai.clubmgt.bean.DBCon;
import ntpc.ccai.clubmgt.dao.ClubDao;
import ntpc.ccai.clubmgt.dao.impl.ClubDaoImpl;

public class ClubService {
//    private static final Logger logger = Logger.getLogger(ClubService.class);
    
    private ClubDao clubDao;
    
    public ClubService() {
        clubDao = new ClubDaoImpl();
    }
    
    public ClubService(ClubDao clubDao) {
        this.clubDao = clubDao;
    }
    
    public Club insert(Connection conn, Club club) {
        Club result = null;
        
        if (conn != null) {
            result = clubDao.insert(conn, club);
        } else {
            throw new IllegalStateException("No existing connection.");
        }
        
        return result;
    }
    
    public Boolean update(Connection conn, Club club) {
        Boolean result = false;
        
        if (conn != null) {
            result = clubDao.update(conn, club);
        } else {
            throw new IllegalStateException("No existing connection.");
        }
        
        return result;
    }
    
    public Boolean delete(String sch_code, Integer club_num) {
        Boolean result = false;
        DBCon dbcon = null;
        Connection conn = null;
        
        try {
            dbcon = new DBCon(sch_code);
            conn = dbcon.getConnection();
            if (conn != null) {
                result = clubDao.delete(dbcon.getConnection(), club_num);
            }
        } finally {
            if (dbcon != null) {
                dbcon.closeCon();
            }
        }
        
        return result;
    }
    
    public List<Club> getAllClubs(String sch_code) {
        List<Club> result = null;
        DBCon dbcon = null;
        Connection conn = null;
        
        try {
            dbcon = new DBCon(sch_code);
            conn = dbcon.getConnection();
            if (conn != null) {
                result = clubDao.getAllClubs(conn);
            }
        } finally {
            if (dbcon != null) {
                dbcon.closeCon();
            }
        }
        
        return result;
    }
    
    public List<Club> getClubsByCategory(String sch_code, Integer cat_num) {
        List<Club> result = null;
        DBCon dbcon = null;
        Connection conn = null;
        
        try {
            dbcon = new DBCon(sch_code);
            conn = dbcon.getConnection();
            if (conn != null) {
                result = clubDao.getClubsByCategory(conn, cat_num);
            }
        } finally {
            if (dbcon != null) {
                dbcon.closeCon();
            }
        }
        
        return result;
    }
    
    public Club getClubByClubNum(String sch_code, Integer club_num) {
        Club result = null;
        DBCon dbcon = null;
        Connection conn = null;
        
        try {
            dbcon = new DBCon(sch_code);
            conn = dbcon.getConnection();
            if (conn != null) {
                result = clubDao.getClubByClubNum(conn, club_num);
            }
        } finally {
            if (dbcon != null) {
                dbcon.closeCon();
            }
        }
        
        return result;
    }
    
    public List<Club> getClubsByClubCode(String sch_code, String club_code) {
        List<Club> result = null;
        DBCon dbcon = null;
        Connection conn = null;
        
        try {
            dbcon = new DBCon(sch_code);
            conn = dbcon.getConnection();
            if (conn != null) {
                result = clubDao.getClubsByClubCode(conn, club_code);
            }
        } finally {
            if (dbcon != null) {
                dbcon.closeCon();
            }
        }
        
        return result;
    }
    
    public List<Club> getClubsByClubName(String sch_code, String club_name) {
        List<Club> result = null;
        DBCon dbcon = null;
        Connection conn = null;
        
        try {
            dbcon = new DBCon(sch_code);
            conn = dbcon.getConnection();
            if (conn != null) {
                result = clubDao.getClubsByClubName(conn, club_name);
            }
        } finally {
            if (dbcon != null) {
                dbcon.closeCon();
            }
        }
        
        return result;
    }
}
