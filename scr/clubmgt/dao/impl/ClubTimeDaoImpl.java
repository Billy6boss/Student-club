package ntpc.ccai.clubmgt.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import ntpc.ccai.clubmgt.bean.ClubTime;
import ntpc.ccai.clubmgt.dao.ClubTimeDao;
import ntpc.ccai.clubmgt.util.StringUtils;

public class ClubTimeDaoImpl implements ClubTimeDao {
	private static final String INSERT = "INSERT INTO stu_club_time(club_num, sbj_year, sbj_sem, sday, speriod) VALUES(?, ?, ?, ?, ?)";
    private static final String DELETE = "DELETE FROM stu_club_time WHERE club_num = ? AND sbj_year = ? AND sbj_sem = ? AND sday = ? AND speriod = ?";
	private static final String SELECT_BY_CLUB_NUM_AND_YEAR_AND_SEM = "SELECT club_num, sbj_year, sbj_sem, sday, speriod  FROM stu_club_time WHERE club_num=? AND sbj_year=? AND sbj_sem=?";
    private static final String DELETE_BY_CLUB_NUM_AND_YEAR_AND_SEM = "DELETE FROM stu_club_time WHERE club_num = ? AND sbj_year = ? AND sbj_sem = ?";
	private static final String COPY = "INSERT INTO stu_club_time(club_num, sbj_year, sbj_sem, sday, speriod) SELECT club_num, ?, ?, sday, speriod FROM stu_club_time WHERE sbj_year = ? AND sbj_sem = ?";
    private static final Logger logger = Logger.getLogger(ClubTimeDaoImpl.class);
    
	@Override
	public Boolean insert(Connection conn, ClubTime clubTime) {
		Boolean result = false;
        PreparedStatement ps = null;
        
        try {
            ps = conn.prepareStatement(INSERT);
            int i = 1;
            ps.setInt(i++, clubTime.getClub_num());
            ps.setInt(i++, clubTime.getSbj_year());
            ps.setString(i++, clubTime.getSbj_sem().toString());
            ps.setByte(i++, clubTime.getSday());
            ps.setByte(i++, clubTime.getSperiod());
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
	public Boolean delete(Connection conn, ClubTime clubTime) {
		Boolean result = false;
        PreparedStatement ps = null;
        
        try {
            ps = conn.prepareStatement(DELETE);
            int i = 1;
            ps.setInt(i++, clubTime.getClub_num());
            ps.setInt(i++, clubTime.getSbj_year());
            ps.setString(i++, clubTime.getSbj_sem().toString());
            ps.setByte(i++, clubTime.getSday());
            ps.setByte(i++, clubTime.getSperiod());
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
	public List<ClubTime> getClubTimeByClubNumAndYearAndSem(Connection conn, Integer club_num, Integer sbj_year,
			Character sbj_sem) {
		List<ClubTime> result = new ArrayList<ClubTime>();
        PreparedStatement ps = null;
        ResultSet rset = null;
        
        try {
            ps = conn.prepareStatement(SELECT_BY_CLUB_NUM_AND_YEAR_AND_SEM);
            ps.setInt(1, club_num);
            ps.setInt(2, sbj_year);
            ps.setString(3, sbj_sem.toString());
            rset = ps.executeQuery();
            while (rset.next()) {
            	ClubTime clubTime = new ClubTime();
            	clubTime.setClub_num(rset.getInt("club_num"));
            	clubTime.setSbj_year(rset.getInt("sbj_year"));
            	clubTime.setSbj_sem(StringUtils.toCharacter(rset.getString("sbj_sem")));
            	clubTime.setSday(rset.getByte("sday"));
            	clubTime.setSperiod(rset.getByte("speriod"));
                result.add(clubTime);
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
    public Integer delete(Connection conn, Integer club_num, Integer sbj_year, Character sbj_sem) {
        Integer result = null;
        PreparedStatement ps = null;
        
        try {
            ps = conn.prepareStatement(DELETE_BY_CLUB_NUM_AND_YEAR_AND_SEM);
            int i = 1;
            ps.setInt(i++, club_num);
            ps.setInt(i++, sbj_year);
            ps.setString(i++, sbj_sem.toString());
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

    @Override
    public Integer copy(Connection conn, Integer from_year, Character from_sem, Integer to_year, Character to_sem, Set<Integer> club_nums) {
        Integer result = null;
        PreparedStatement ps = null;
        StringBuilder copy = new StringBuilder(COPY);
        if (club_nums != null) {
            copy.append(" AND club_num IN (");
            for (int i = 0, size = club_nums.size(); i < size; i++) {
                if (i == size - 1) {
                    copy.append("? ");
                } else {
                    copy.append("?, ");
                }
            }
            copy.append(")");
        }
        
        try {
            ps = conn.prepareStatement(copy.toString());
            int i = 1;
            ps.setInt(i++, to_year);
            ps.setString(i++, to_sem.toString());
            ps.setInt(i++, from_year);
            ps.setString(i++, from_sem.toString());
            if (club_nums != null) {
                for (Integer club_num : club_nums) {
                    ps.setInt(i++, club_num);
                }
            }
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
