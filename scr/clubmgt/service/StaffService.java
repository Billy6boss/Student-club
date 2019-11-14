package ntpc.ccai.clubmgt.service;

import java.sql.Connection;
import java.util.List;

import ntpc.ccai.clubmgt.bean.Staff;
import ntpc.ccai.clubmgt.bean.DBCon;
import ntpc.ccai.clubmgt.dao.StaffDao;
import ntpc.ccai.clubmgt.dao.impl.StaffDaoImpl;

public class StaffService {
//    private static final Logger logger = Logger.getLogger(StaffService.class);

    private StaffDao staffDao;
    
    public StaffService() {
        staffDao = new StaffDaoImpl();
    }
    
    public StaffService(StaffDao staffDao) {
        this.staffDao = staffDao;
    }
    
    public List<Staff> getAllStaves(String sch_code) {
        List<Staff> result = null;
        DBCon dbcon = null;
        Connection conn = null;
        
        try {
            dbcon = new DBCon(sch_code);
            conn = dbcon.getConnection();
            if (conn != null) {
                result = staffDao.getAllStaves(conn);
            }
        } finally {
            if (dbcon != null) {
                dbcon.closeCon();
            }
        }
        
        return result;
    }
    
    public Staff getStaffByStaffCode(String sch_code, String staff_code) {
        Staff result = null;
        DBCon dbcon = null;
        Connection conn = null;
        
        try {
            dbcon = new DBCon(sch_code);
            conn = dbcon.getConnection();
            if (conn != null) {
                result = staffDao.getStaffByStaffCode(conn, staff_code);
            }
        } finally {
            if (dbcon != null) {
                dbcon.closeCon();
            }
        }
        
        return result;
    }

}
