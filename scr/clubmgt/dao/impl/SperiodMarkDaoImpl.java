package ntpc.ccai.clubmgt.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ntpc.ccai.clubmgt.bean.SperiodMark;
import ntpc.ccai.clubmgt.dao.SperiodMarkDao;
import ntpc.ccai.clubmgt.util.StringUtils;

public class SperiodMarkDaoImpl implements SperiodMarkDao {
    private static final String SELECT_ALL = "SELECT speriod, sprd_mark, sprd_code, sprd_name, adg_code, sprd_order FROM speriod_mark ORDER BY sprd_order";
    private static final String SELECT_BY_SPERIOD = "SELECT speriod, sprd_mark, sprd_code, sprd_name, adg_code, sprd_order FROM speriod_mark WHERE speriod = ?";
    private static final Logger logger = Logger.getLogger(SperiodMarkDaoImpl.class);
    
    public List<SperiodMark> getAllSperiodMarks(Connection conn) {
        List<SperiodMark> result = new ArrayList<SperiodMark>();
        PreparedStatement ps = null;
        ResultSet rset = null;
        
        try {
            ps = conn.prepareStatement(SELECT_ALL);
            rset = ps.executeQuery();
            while (rset.next()) {
                SperiodMark speriodMark = new SperiodMark();
                speriodMark.setSperiod(rset.getShort("speriod"));
                speriodMark.setSprd_mark(rset.getString("sprd_mark"));
                speriodMark.setSprd_code(rset.getString("sprd_code"));
                speriodMark.setSprd_name(rset.getString("sprd_name"));
                speriodMark.setAdg_code(StringUtils.toCharacter(rset.getString("adg_code")));
                speriodMark.setSprd_order(rset.getShort("sprd_order"));
                result.add(speriodMark);
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
        return result;
    }

    public SperiodMark getSperiodMarkBySperiod(Connection conn, Short speriod) {
        SperiodMark result = null;
        PreparedStatement ps = null;
        ResultSet rset = null;
        
        try {
            ps = conn.prepareStatement(SELECT_BY_SPERIOD);
            ps.setShort(1, speriod);
            rset = ps.executeQuery();
            if (rset.next()) {
                result = new SperiodMark();
                result.setSperiod(rset.getShort("speriod"));
                result.setSprd_mark(rset.getString("sprd_mark"));
                result.setSprd_code(rset.getString("sprd_code"));
                result.setSprd_name(rset.getString("sprd_name"));
                result.setAdg_code(StringUtils.toCharacter(rset.getString("adg_code")));
                result.setSprd_order(rset.getShort("sprd_order"));
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
        return result;
    }

}
