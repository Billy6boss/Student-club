package ntpc.ccai.clubmgt.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ntpc.ccai.clubmgt.bean.ClubSelectionClass;
import ntpc.ccai.clubmgt.dao.ClubSelectionClassDao;
import ntpc.ccai.clubmgt.util.StringUtils;

public class ClubSelectionClassDaoImpl implements ClubSelectionClassDao {
    private static final String INSERT = "INSERT INTO stu_club_selection_class(cs_num, sbj_year, sbj_sem, cls_code) VALUES (?, ?, ?, ?)";
    private static final String DELETE = "DELETE FROM stu_club_selection_class WHERE cs_num = ? AND sbj_year = ? AND sbj_sem = ? AND cls_code = ?";
    private static final String SELECT_BY_CS_NUM = "SELECT cs_num, sbj_year, sbj_sem, cls_code FROM stu_club_selection_class WHERE cs_num = ?";
    private static final String DELETE_BY_CS_NUM = "DELETE FROM stu_club_selection_class WHERE cs_num = ?";
    private static final Logger logger = Logger.getLogger(ClubSelectionClassDaoImpl.class);

    @Override
    public Boolean insert(Connection conn, ClubSelectionClass clubSelectionClass) {
        return insertOrDelete(conn, clubSelectionClass, INSERT);
    }

    @Override
    public Boolean delete(Connection conn, ClubSelectionClass clubSelectionClass) {
        return insertOrDelete(conn, clubSelectionClass, DELETE);
    }
    
    private Boolean insertOrDelete(Connection conn, ClubSelectionClass clubSelectionClass, String str) {
        Boolean result = false;
        PreparedStatement ps = null;
        
        try {
            ps = conn.prepareStatement(str);
            int i = 1;
            ps.setInt(i++, clubSelectionClass.getCs_num());
            ps.setInt(i++, clubSelectionClass.getSbj_year());
            ps.setString(i++, clubSelectionClass.getSbj_sem().toString());
            ps.setString(i++, clubSelectionClass.getCls_code());
            result = ps.executeUpdate() == 1;
        } catch (SQLException e) {
            logger.error(e);
            e.printStackTrace();
        } finally {
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

    @Override
    public List<ClubSelectionClass> getClubSelectionClassesByCsNum(Connection conn, Integer cs_num) {
        List<ClubSelectionClass> result = new ArrayList<ClubSelectionClass>();
        PreparedStatement ps = null;
        ResultSet rset = null;
        
        try {
            ps = conn.prepareStatement(SELECT_BY_CS_NUM);
            ps.setInt(1, cs_num);
            rset = ps.executeQuery();
            while (rset.next()) {
                ClubSelectionClass clubSelectionClass = new ClubSelectionClass();
                clubSelectionClass.setCs_num(rset.getInt("cs_num"));
                clubSelectionClass.setSbj_year(rset.getInt("sbj_year"));
                clubSelectionClass.setSbj_sem(StringUtils.toCharacter(rset.getString("sbj_sem")));
                clubSelectionClass.setCls_code(rset.getString("cls_code"));
                result.add(clubSelectionClass);
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

    @Override
    public Integer delete(Connection conn, Integer cs_num) {
        Integer result = null;
        PreparedStatement ps = null;
        
        try {
            ps = conn.prepareStatement(DELETE_BY_CS_NUM);
            ps.setInt(1, cs_num);
            result = ps.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
            e.printStackTrace();
        } finally {
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
