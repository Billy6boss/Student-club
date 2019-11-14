package ntpc.ccai.clubmgt.service;

import java.sql.Connection;
import java.util.List;
//import org.apache.log4j.Logger;
import ntpc.ccai.clubmgt.bean.StuClass;
import ntpc.ccai.clubmgt.bean.DBCon;
import ntpc.ccai.clubmgt.dao.StuClassDao;
import ntpc.ccai.clubmgt.dao.impl.StuClassDaoImpl;

public class StuClassService {
	//private static final Logger logger = Logger.getLogger(StuClassService.class);
    
    private StuClassDao stuclassDao;
    
    public StuClassService() {
    	stuclassDao = new StuClassDaoImpl();
    }
    
    public StuClassService(StuClassDao stuclassDao) {
        this.stuclassDao = stuclassDao;
    }
    public List<StuClass> getStuClassesByYearAndSemAndGrade(String sch_code, Integer sbj_year, Character sbj_sem, Character grade) {
    	List<StuClass> result = null;
        DBCon dbcon = null;
        Connection conn = null;
        
        try {
            dbcon = new DBCon(sch_code);
            conn = dbcon.getConnection();
            if (conn != null) {
                result = stuclassDao.getStuClassesByYearAndSemAndGrade(conn, sbj_year, sbj_sem, grade);
            }
        } finally {
            if (dbcon != null) {
                dbcon.closeCon();
            }
        }
        
        return result;
    }
    
    public List<StuClass> getStuClassesByYearAndSem(String sch_code, Integer sbj_year, Character sbj_sem) {
    	List<StuClass> result = null;
        DBCon dbcon = null;
        Connection conn = null;
        
        try {
        	dbcon = new DBCon(sch_code);
        	conn = dbcon.getConnection();
        	if (conn != null) {
        		result = stuclassDao.getStuClassByYearAndSem(conn, sbj_year, sbj_sem);
        	}
        } finally {
        	if (dbcon != null) {
        		dbcon.closeCon();
        	}
        }
        return result;
    }
    
    public StuClass getStuClassByYearAndSemAndClsCode(String sch_code, Integer sbj_year, Character sbj_sem, String cls_code) {
    	StuClass result = null;
        DBCon dbcon = null;
        Connection conn = null;
        
        try {
            dbcon = new DBCon(sch_code);
            conn = dbcon.getConnection();
            if (conn != null) {
                result = stuclassDao.getStuClassByYearAndSemAndClsCode(conn, sbj_year, sbj_sem, cls_code);
            }
        } finally {
            if (dbcon != null) {
                dbcon.closeCon();
            }
        }
        
        return result;
    }
    
    // need to rewrite
    public List<StuClass> getClassClubByYearAndSemAndClsCode(String sch_code, Integer sbj_year, Character sbj_sem, String cls_code) {
        List<StuClass> result = null;
        DBCon dbcon = null;
        
        try {
            dbcon = new DBCon(sch_code);
            result = stuclassDao.getClassClubByYearAndSemAndClsCode(dbcon.getConnection(), sbj_year, sbj_sem , cls_code);
        } finally {
            if (dbcon != null) {
                dbcon.closeCon();
            }
        }
        
        return result;
    }
}
