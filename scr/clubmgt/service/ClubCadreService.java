package ntpc.ccai.clubmgt.service;

import java.sql.Connection;
import java.util.List;

import ntpc.ccai.clubmgt.bean.ClubCadre;
import ntpc.ccai.clubmgt.bean.DBCon;
import ntpc.ccai.clubmgt.dao.ClubCadreDao;
import ntpc.ccai.clubmgt.dao.impl.ClubCadreDaoImpl;

public class ClubCadreService {
//    private static final Logger logger = Logger.getLogger(ClubCadreService.class);
    
    private ClubCadreDao clubCadreDao;
    
    public ClubCadreService() {
        clubCadreDao = new ClubCadreDaoImpl();
    }
    
    public ClubCadreService(ClubCadreDao clubCadreDao) {
        this.clubCadreDao = clubCadreDao;
    }
    
    public List<ClubCadre> getAllClubCadres(String sch_code) {
        List<ClubCadre> result = null;
        DBCon dbcon = null;
        Connection conn = null;
        
        try {
            dbcon = new DBCon(sch_code);
            conn = dbcon.getConnection();
            if (conn != null) {
                result = clubCadreDao.getAllClubCadres(conn);
            }
        } finally {
            if (dbcon != null) {
                dbcon.closeCon();
            }
        }
        
        return result;
    }
    
    public ClubCadre getClubCadreByCadreNum(String sch_code, Integer cadre_num) {
        ClubCadre result = null;
        DBCon dbcon = null;
        Connection conn = null;
        
        try {
            dbcon = new DBCon(sch_code);
            conn = dbcon.getConnection();
            if (conn != null) {
                result = clubCadreDao.getClubCadreByCadreNum(conn, cadre_num);
            }
        } finally {
            if (dbcon != null) {
                dbcon.closeCon();
            }
        }
        
        return result;
    }

}
