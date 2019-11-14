package ntpc.ccai.clubmgt.service;
import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Set;

//import org.apache.log4j.Logger;
import ntpc.ccai.clubmgt.bean.StuRegister;
import ntpc.ccai.clubmgt.bean.DBCon;
import ntpc.ccai.clubmgt.dao.StuRegisterDao;
import ntpc.ccai.clubmgt.dao.impl.StuRegisterDaoImpl;

public class StuRegisterService {
	//private static final Logger logger = Logger.getLogger(StuRegisterService.class);
    
    private StuRegisterDao sturegisterDao;
    
    public StuRegisterService() {
    	sturegisterDao = new StuRegisterDaoImpl();
    }
    
    public StuRegisterService(StuRegisterDao sturegisterDao) {
        this.sturegisterDao = sturegisterDao;
    }
    
    
    
    public StuRegister getStuRegistersByYearAndSemAndClsCodeAndClsNo(String sch_code, Integer sbj_year, Character sbj_sem, String cls_code, String cls_no) {
    	StuRegister result = null;
        DBCon dbcon = null;
        Connection conn = null;
        
        try {
            dbcon = new DBCon(sch_code);
            conn = dbcon.getConnection();
            if (conn != null) {
                result = sturegisterDao.getStuRegistersByYearAndSemAndClsCodeAndClsNo(conn, sbj_year, sbj_sem, cls_code, cls_no);
            }
        } finally {
            if (dbcon != null) {
                dbcon.closeCon();
            }
        }
        
        return result;
    }
    
    public List<StuRegister> getStuRegistersByYearAndSemAndClsCode(String sch_code, Integer sbj_year, Character sbj_sem, String cls_code) {
        List<StuRegister> result = null;
        DBCon dbcon = null;
        Connection conn = null;
        
        try {
            dbcon = new DBCon(sch_code);
            conn = dbcon.getConnection();
            if (conn != null) {
                result = sturegisterDao.getStuRegistersByYearAndSemAndClsCode(conn, sbj_year, sbj_sem, cls_code);
            }
        } finally {
            if (dbcon != null) {
                dbcon.closeCon();
            }
        }
        
        return result;
    }
    
    public StuRegister getStuRegisterByYearAndSemAndRegNo(String sch_code, Integer sbj_year, Character sbj_sem, String reg_no) {
        StuRegister result = null;
        DBCon dbcon = null;
        Connection conn = null;
        
        try {
            dbcon = new DBCon(sch_code);
            conn = dbcon.getConnection();
            if (conn != null) {
                result = sturegisterDao.getStuRegisterByYearAndSemAndRegNo(conn, sbj_year, sbj_sem, reg_no);
            }
        } finally {
            if (dbcon != null) {
                dbcon.closeCon();
            }
        }
        
        return result;
    }
    
    public Map<Integer, Set<Character>> getYearAndSem(String sch_code) {
    	Map<Integer, Set<Character>> result = null;
        DBCon dbcon = null;
        Connection conn = null;
        
        try {
            dbcon = new DBCon(sch_code);
            conn = dbcon.getConnection();
            if (conn != null) {
                result = sturegisterDao.getYearAndSem(conn);
            }
        } finally {
            if (dbcon != null) {
                dbcon.closeCon();
            }
        }
        
        return result;
    }
    
    public Integer getRgnoByYearAndSemAndClsCodeAndClsNo(String sch_code, Integer sbj_year, Character sbj_sem, String cls_code, String cls_no) {
    	Integer result = null;
        DBCon dbcon = null;
        Connection conn = null;
        
        try {
            dbcon = new DBCon(sch_code);
            conn = dbcon.getConnection();
            if (conn != null) {
                result = sturegisterDao.getRgnoByYearAndSemAndClsCodeAndClsNo(conn, sbj_year, sbj_sem, cls_code, cls_no);
            }
        } finally {
            if (dbcon != null) {
                dbcon.closeCon();
            }
        }
        
        return result;
    }
}
