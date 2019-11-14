package ntpc.ccai.clubmgt.dao;

import java.sql.Connection;
import java.util.List;

import ntpc.ccai.clubmgt.bean.Staff;

public interface StaffDao {
    List<Staff> getAllStaves(Connection conn);
    Staff getStaffByStaffCode(Connection conn, String staff_code);
}
