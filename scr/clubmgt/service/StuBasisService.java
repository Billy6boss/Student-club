package ntpc.ccai.clubmgt.service;

import java.sql.Connection;

import ntpc.ccai.clubmgt.bean.StuBasis;
import ntpc.ccai.clubmgt.bean.DBCon;
import ntpc.ccai.clubmgt.dao.StuBasisDao;
import ntpc.ccai.clubmgt.dao.impl.StuBasisDaoImpl;

public class StuBasisService {
//    private static final Logger logger = Logger.getLogger(StuBasisService.class);
    
    private StuBasisDao stuBasisDao;
    
    public StuBasisService() {
        stuBasisDao = new StuBasisDaoImpl();
    };
    
    public StuBasisService(StuBasisDao stuBasisDao) {
        this.stuBasisDao = stuBasisDao;
    }
    
    public StuBasis getStudentByRegno(String sch_code, String reg_no) {
        StuBasis result = null;
        DBCon dbcon = null;
        Connection conn = null;
        
        try {
            dbcon = new DBCon(sch_code);
            conn = dbcon.getConnection();
            if (conn != null) {
                result = stuBasisDao.getStudentByRegno(conn, reg_no);
            }
        } finally {
            if (dbcon != null) {
                dbcon.closeCon();
            }
        }
        
        return result;
    }
}
