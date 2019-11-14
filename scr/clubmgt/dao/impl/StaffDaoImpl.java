package ntpc.ccai.clubmgt.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ntpc.ccai.clubmgt.bean.Staff;
import ntpc.ccai.clubmgt.dao.StaffDao;
import ntpc.ccai.clubmgt.util.StringUtils;

public class StaffDaoImpl implements StaffDao {
    private static final String SELECT_ALL = "SELECT staff_code, staff_cname, staff_ename, div_code, grade_code, sex, ret_flag, tch_flag, "
                                           + "mon_flag, address, phone, email, idno, basic_hours FROM staff";
    private static final String SELECT_BY_STAFF_CODE = "SELECT staff_code, staff_cname, staff_ename, div_code, grade_code, sex, ret_flag, tch_flag, "
                                       + "mon_flag, address, phone, email, idno, basic_hours FROM staff WHERE staff_code = ?";
    private static final Logger logger = Logger.getLogger(StaffDaoImpl.class);

    @Override
    public List<Staff> getAllStaves(Connection conn) {
        List<Staff> list = new ArrayList<Staff>();
        PreparedStatement ps = null;
        ResultSet rset = null;
        
        try {
            ps = conn.prepareStatement(SELECT_ALL);
            rset = ps.executeQuery();
            while (rset.next()) {
                Staff staff = new Staff();
                staff.setStaff_code(rset.getString("staff_code"));
                staff.setStaff_cname(rset.getString("staff_cname"));
                staff.setStaff_ename(rset.getString("staff_ename"));
                staff.setDiv_code(rset.getString("div_code"));
                staff.setGrade_code(rset.getString("grade_code"));
                staff.setSex(rset.getByte("sex"));
                staff.setRet_flag(StringUtils.toCharacter(rset.getString("ret_flag")));
                staff.setTch_flag(StringUtils.toCharacter(rset.getString("tch_flag")));
                staff.setMon_flag(StringUtils.toCharacter(rset.getString("mon_flag")));
                staff.setAddress(rset.getString("address"));
                staff.setPhone(rset.getString("phone"));
                staff.setEmail(rset.getString("email"));
                staff.setIdno(rset.getString("idno"));
                staff.setBasic_hours(rset.getShort("basic_hours"));
                list.add(staff);
            }
        } catch (SQLException e) {
            logger.error(e);
            e.printStackTrace();
        } finally {
            if (rset != null) {
                try {
                    rset.close();
                } catch (SQLException e) {
                    logger.error(e);
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    logger.error(e);
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    @Override
    public Staff getStaffByStaffCode(Connection conn, String staff_code) {
        Staff staff = null;
        PreparedStatement ps = null;
        ResultSet rset = null;
        
        try {
            ps = conn.prepareStatement(SELECT_BY_STAFF_CODE);
            ps.setString(1, staff_code);
            rset = ps.executeQuery();
            if (rset.next()) {
                staff = new Staff();
                staff.setStaff_code(rset.getString("staff_code"));
                staff.setStaff_cname(rset.getString("staff_cname"));
                staff.setStaff_ename(rset.getString("staff_ename"));
                staff.setDiv_code(rset.getString("div_code"));
                staff.setGrade_code(rset.getString("grade_code"));
                staff.setSex(rset.getByte("sex"));
                staff.setRet_flag(StringUtils.toCharacter(rset.getString("ret_flag")));
                staff.setTch_flag(StringUtils.toCharacter(rset.getString("tch_flag")));
                staff.setMon_flag(StringUtils.toCharacter(rset.getString("mon_flag")));
                staff.setAddress(rset.getString("address"));
                staff.setPhone(rset.getString("phone"));
                staff.setEmail(rset.getString("email"));
                staff.setIdno(rset.getString("idno"));
                staff.setBasic_hours(rset.getShort("basic_hours"));
            }
        } catch (SQLException e) {
            logger.error(e);
            e.printStackTrace();
        } finally {
            if (rset != null) {
                try {
                    rset.close();
                } catch (SQLException e) {
                    logger.error(e);
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    logger.error(e);
                    e.printStackTrace();
                }
            }
        }
        return staff;
    }

}
