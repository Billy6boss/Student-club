package ntpc.ccai.clubmgt.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import ntpc.ccai.clubmgt.bean.SemesterType;
import ntpc.ccai.clubmgt.dao.SemesterTypeDao;
import ntpc.ccai.clubmgt.util.StringUtils;

public class SemesterTypeDaoImpl implements SemesterTypeDao {
    private static final String SELECT = "SELECT sem_seq, sbj_year, sbj_sem, adg_code, sem_sdate, sem_edate, rollcall_num FROM semester_type ";
    private static final String SELECT_BY_DATE = SELECT + " WHERE ? BETWEEN sem_sdate AND sem_edate ";
    private static final String SELECT_BY_YEAR = SELECT + " WHERE sbj_year = ? ";
    private static final String ORDER = " ORDER BY sbj_year, sbj_sem";
    private static final Logger logger = Logger.getLogger(SemesterTypeDaoImpl.class);

    @Override
    public SemesterType getSemesterTypeByDate(Connection conn, Date date) {
        SemesterType result = null;
        PreparedStatement ps = null;
        ResultSet rset = null;
        
        try {
            ps = conn.prepareStatement(SELECT_BY_DATE);
            ps.setTimestamp(1, new java.sql.Timestamp(date.getTime()));
            rset = ps.executeQuery();
            if (rset.next()) {
                result = new SemesterType();
                result.setSem_seq(rset.getInt("sem_seq"));
                result.setSbj_year(rset.getInt("sbj_year"));
                result.setSbj_sem(StringUtils.toCharacter(rset.getString("sbj_sem")));
                result.setAdg_code(StringUtils.toCharacter(rset.getString("adg_code")));
                result.setSem_sdate(rset.getDate("sem_sdate"));
                result.setSem_edate(rset.getDate("sem_edate"));
                result.setRollcall_num(rset.getInt("rollcall_num"));
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
    public List<SemesterType> getSemesterTypesByYear(Connection conn, Integer sbj_year) {
        List<SemesterType> result = new ArrayList<SemesterType>();
        PreparedStatement ps = null;
        ResultSet rset = null;
        
        try {
            ps = conn.prepareStatement(SELECT_BY_YEAR + ORDER);
            ps.setInt(1, sbj_year);
            rset = ps.executeQuery();
            while (rset.next()) {
                SemesterType semesterType = new SemesterType();
                semesterType.setSem_seq(rset.getInt("sem_seq"));
                semesterType.setSbj_year(rset.getInt("sbj_year"));
                semesterType.setSbj_sem(StringUtils.toCharacter(rset.getString("sbj_sem")));
                semesterType.setAdg_code(StringUtils.toCharacter(rset.getString("adg_code")));
                semesterType.setSem_sdate(rset.getDate("sem_sdate"));
                semesterType.setSem_edate(rset.getDate("sem_edate"));
                semesterType.setRollcall_num(rset.getInt("rollcall_num"));
                result.add(semesterType);
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
    public List<SemesterType> getAllSemesterTypes(Connection conn) {
        List<SemesterType> result = new ArrayList<SemesterType>();
        PreparedStatement ps = null;
        ResultSet rset = null;
        
        try {
            ps = conn.prepareStatement(SELECT + ORDER);
            rset = ps.executeQuery();
            while (rset.next()) {
                SemesterType semesterType = new SemesterType();
                semesterType.setSem_seq(rset.getInt("sem_seq"));
                semesterType.setSbj_year(rset.getInt("sbj_year"));
                semesterType.setSbj_sem(StringUtils.toCharacter(rset.getString("sbj_sem")));
                semesterType.setAdg_code(StringUtils.toCharacter(rset.getString("adg_code")));
                semesterType.setSem_sdate(rset.getDate("sem_sdate"));
                semesterType.setSem_edate(rset.getDate("sem_edate"));
                semesterType.setRollcall_num(rset.getInt("rollcall_num"));
                result.add(semesterType);
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
