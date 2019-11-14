package ntpc.ccai.clubmgt.service;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import ntpc.ccai.clubmgt.bean.SemesterType;
import ntpc.ccai.clubmgt.bean.DBCon;
import ntpc.ccai.clubmgt.dao.SemesterTypeDao;
import ntpc.ccai.clubmgt.dao.impl.SemesterTypeDaoImpl;

public class SemesterTypeService {
    private SemesterTypeDao semesterTypeDao;
    
    public SemesterTypeService() {
        semesterTypeDao = new SemesterTypeDaoImpl();
    }
    
    public SemesterTypeService(SemesterTypeDao semesterTypeDao) {
        this.semesterTypeDao = semesterTypeDao;
    }
    
    public SemesterType getSemesterTypeByDate(String sch_code, Date date) {
        SemesterType result = null;
        DBCon dbcon = null;
        Connection conn = null;
        
        try {
            dbcon = new DBCon(sch_code);
            conn = dbcon.getConnection();
            if (conn != null) {
                result = semesterTypeDao.getSemesterTypeByDate(conn, date);
            }
        } finally {
            if (dbcon != null) {
                dbcon.closeCon();
            }
        }
        
        return result;
    }
    
    public List<SemesterType> getSemesterTypesByYear(String sch_code, Integer sbj_year) {
        List<SemesterType> result = null;
        DBCon dbcon = null;
        Connection conn = null;
        
        try {
            dbcon = new DBCon(sch_code);
            conn = dbcon.getConnection();
            if (conn != null) {
                result = semesterTypeDao.getSemesterTypesByYear(conn, sbj_year);
            }
        } finally {
            if (dbcon != null) {
                dbcon.closeCon();
            }
        }
        
        return result;
    }
    
    public List<SemesterType> getAllSemesterTypes(String sch_code) {
        List<SemesterType> result = null;
        DBCon dbcon = null;
        Connection conn = null;
        
        try {
            dbcon = new DBCon(sch_code);
            conn = dbcon.getConnection();
            if (conn != null) {
                result = semesterTypeDao.getAllSemesterTypes(conn);
            }
        } finally {
            if (dbcon != null) {
                dbcon.closeCon();
            }
        }
        
        return result;
    }
}
