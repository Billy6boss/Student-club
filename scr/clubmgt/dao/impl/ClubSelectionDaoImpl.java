package ntpc.ccai.clubmgt.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

import ntpc.ccai.clubmgt.bean.Club;
import ntpc.ccai.clubmgt.bean.ClubSelection;
import ntpc.ccai.clubmgt.bean.ClubSem;
import ntpc.ccai.clubmgt.bean.Staff;
import ntpc.ccai.clubmgt.dao.ClubSelectionDao;
import ntpc.ccai.clubmgt.util.StringUtils;

public class ClubSelectionDaoImpl implements ClubSelectionDao {
    private static final String INSERT = "INSERT INTO stu_club_selection (sbj_year, sbj_sem, inuse, cs_sdate, cs_edate, cs_method, cs_lower_limit, total_limit, grade_limit, class_limit) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE stu_club_selection SET sbj_year=?, sbj_sem=?, inuse=?, cs_sdate=?, cs_edate=?, cs_method=?, cs_lower_limit=?, total_limit=?, grade_limit=?, class_limit=? WHERE cs_num=?";
    private static final String DELETE = "DELETE FROM stu_club_selection WHERE cs_num=?";
    private static final String SELECT_BY_CLUB_NUM_AND_YEAR_AND_SEM = "SELECT cs_num, sbj_year, sbj_sem, inuse, cs_sdate, cs_edate, cs_method, cs_lower_limit, total_limit, grade_limit, class_limit FROM stu_club_selection WHERE club_num=? AND sbj_year=? AND sbj_sem=?";
    private static final String DELETE_BY_CLUB_NUM_AND_YEAR_AND_SEM = "DELETE FROM stu_club_selection WHERE sbj_year = ? AND sbj_sem = ?";
    
    // need to rewrite
    private static final String SELECT_CLUB_SELECTION_BY_YEAR_AND_SEM = "SELECT a.CS_NUM,e.CLUB_NAME,d.STAFF_CNAME,b.CLUB_ROOM,a.CS_LOWER_LIMIT,a.TOTAL_LIMIT,b.GRADE_ONE,b.GRADE_TWO,b.GRADE_THREE FROM STU_CLUB_SELECTION a " + 
            "JOIN STU_CLUB_SEM b ON a.SBJ_YEAR = b.SBJ_YEAR AND a.SBJ_SEM = b.SBJ_SEM AND a.CLUB_NUM = b.CLUB_NUM " + 
            "JOIN STU_CLUB e ON b.CLUB_NUM = e.CLUB_NUM " + 
            "JOIN STU_CLUB_TEACHING c ON b.CLUB_NUM = c.CLUB_NUM AND b.SBJ_YEAR = c.SBJ_YEAR AND b.SBJ_SEM = c.SBJ_SEM " + 
            "JOIN STAFF d ON c.STAFF_CODE = d.STAFF_CODE " + 
            "WHERE b.SELECTABLE = '1' AND a.SBJ_YEAR = ? AND a.SBJ_SEM = ? " + 
            "GROUP BY a.CS_NUM,e.CLUB_NAME,d.STAFF_CNAME,b.CLUB_ROOM,a.CS_LOWER_LIMIT,a.TOTAL_LIMIT,b.GRADE_ONE,b.GRADE_TWO,b.GRADE_THREE " + 
            "ORDER BY a.CS_NUM";
    private static final String SELECT_CLUB_SELECTION_BY_YEAR_AND_SEM_GROUP_BY_LIMITSETTING="SELECT sbj_year,sbj_sem,inuse,CS_SDATE,CS_EDATE,CS_METHOD,CS_LOWER_LIMIT,TOTAL_LIMIT,GRADE_LIMIT,CLASS_LIMIT,count(*) as ClubNumbers " +
            "from STU_CLUB_SELECTION " +
    		"where SBJ_YEAR = ? and SBJ_SEM = ? " +
    		"group by sbj_year,sbj_sem,inuse,CS_SDATE,CS_EDATE,CS_METHOD,CS_LOWER_LIMIT,TOTAL_LIMIT,GRADE_LIMIT,CLASS_LIMIT";
    private static final Logger logger = Logger.getLogger(ClubSelectionDaoImpl.class);

    @Override
    public ClubSelection insert(Connection conn, ClubSelection clubSelection) {
    	PreparedStatement ps = null;
        ResultSet rset = null;
        
        try {
            ps = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            int i = 1;
            ps.setInt(i++, clubSelection.getSbj_year());
            ps.setString(i++, clubSelection.getSbj_sem().toString());
            ps.setBoolean(i++, clubSelection.getInuse());
            ps.setTimestamp(i++, new java.sql.Timestamp(clubSelection.getCs_sdate().getTime()));
            ps.setTimestamp(i++, new java.sql.Timestamp(clubSelection.getCs_edate().getTime()));
            ps.setString(i++, clubSelection.getCs_method().toString());
            ps.setObject(i++, clubSelection.getCs_lower_limit());
            ps.setObject(i++, clubSelection.getTotal_limit());
            ps.setObject(i++, clubSelection.getGrade_limit());
            ps.setObject(i++, clubSelection.getClass_limit());
            int j = ps.executeUpdate();
            if (j == 1) {
                rset = ps.getGeneratedKeys();	
                if (rset.next()) {
                	clubSelection.setCs_num(rset.getInt(1));
                }
            } else {
            	clubSelection = null;
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
        return clubSelection;
    }

    @Override
    public Boolean update(Connection conn, ClubSelection clubSelection) {
    	Boolean result = false;
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(UPDATE);
            int i = 1;
            ps.setInt(i++, clubSelection.getSbj_year());
            ps.setString(i++, clubSelection.getSbj_sem().toString());
            ps.setBoolean(i++, clubSelection.getInuse());
            ps.setTimestamp(i++, new java.sql.Timestamp(clubSelection.getCs_sdate().getTime()));
            ps.setTimestamp(i++, new java.sql.Timestamp(clubSelection.getCs_edate().getTime()));
            ps.setString(i++, clubSelection.getCs_method().toString());
            ps.setInt(i++, clubSelection.getCs_lower_limit());
            ps.setInt(i++, clubSelection.getTotal_limit());
            ps.setInt(i++, clubSelection.getGrade_limit());
            ps.setInt(i++, clubSelection.getClass_limit());
            ps.setInt(i++, clubSelection.getCs_num());
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
    public Boolean delete(Connection conn, Integer cs_num) {
    	Boolean result = false;
        PreparedStatement ps = null;
        
        try {
            ps = conn.prepareStatement(DELETE);
            ps.setInt(1, cs_num);
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
    public List<ClubSelection> getClubSelectionsByClubNumAndYearAndSem(Connection conn, Integer club_num,
            Integer sbj_year, Character sbj_sem) {
    	List<ClubSelection> result = new ArrayList<ClubSelection>();
        PreparedStatement ps = null;
        ResultSet rset = null;
        
        try {
            ps = conn.prepareStatement(SELECT_BY_CLUB_NUM_AND_YEAR_AND_SEM);
            ps.setInt(1, club_num);
            ps.setInt(2, sbj_year);
            ps.setString(3, sbj_sem.toString());
            rset = ps.executeQuery();
            while (rset.next()) {
            	ClubSelection clubselection = new ClubSelection();
            	clubselection.setCs_num(rset.getInt("cs_num"));
            	clubselection.setSbj_year(rset.getInt("sbj_year"));
            	clubselection.setSbj_sem(StringUtils.toCharacter(rset.getString("sbj_sem")));
            	clubselection.setInuse(rset.getBoolean("inuse"));
            	clubselection.setCs_sdate(rset.getTimestamp("cs_sdate")); 
            	clubselection.setCs_edate(rset.getTimestamp("cs_edate"));
            	clubselection.setCs_method(StringUtils.toCharacter(rset.getString("cs_method")));
            	clubselection.setCs_lower_limit(rset.getInt("cs_lower_limit"));
            	clubselection.setTotal_limit(rset.getInt("total_limit"));
            	clubselection.setGrade_limit(rset.getInt("grade_limit"));
            	clubselection.setClass_limit(rset.getInt("class_limit"));
                result.add(clubselection);
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

    //Todo
    @Override
    public List<ClubSelection> getClubSelectionsByYearAndSemGroups(Connection conn, Integer sbj_year,Character sbj_sem) {
        List<ClubSelection> result = new ArrayList<ClubSelection>();
        PreparedStatement ps = null;
        ResultSet rset = null;
        
        try {
            ps = conn.prepareStatement(SELECT_CLUB_SELECTION_BY_YEAR_AND_SEM_GROUP_BY_LIMITSETTING);
            ps.setInt(1, sbj_year);
            ps.setString(2, sbj_sem.toString());
            rset = ps.executeQuery();
            while (rset.next()) {
                ClubSelection clubselection = new ClubSelection();
                //clubselection.setCs_num(rset.getInt("cs_num"));
                clubselection.setInuse(rset.getBoolean("inuse"));
                clubselection.setSbj_year(rset.getInt("sbj_year"));
                clubselection.setSbj_sem(StringUtils.toCharacter(rset.getString("sbj_sem")));
                clubselection.setCs_sdate(rset.getDate("cs_sdate"));
                clubselection.setCs_edate(rset.getDate("cs_edate"));
                clubselection.setCs_method(StringUtils.toCharacter((rset.getString("cs_method"))));
                clubselection.setCs_lower_limit(rset.getInt("cs_lower_limit"));
                clubselection.setTotal_limit(rset.getInt("total_limit"));
                clubselection.setGrade_limit(rset.getInt("grade_limit"));
                clubselection.setClass_limit(rset.getInt("class_limit"));

                
                result.add(clubselection);
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
    
    
    // need to rewrite 
    @Override
    public List<ClubSelection> getClubSelectionsOnlineByYearAndSem(Connection conn, Integer sbj_year,
            Character sbj_sem) {
        List<ClubSelection> result = new ArrayList<ClubSelection>();
        PreparedStatement ps = null;
        ResultSet rset = null;
        
        try {
            ps = conn.prepareStatement(SELECT_CLUB_SELECTION_BY_YEAR_AND_SEM);
            ps.setInt(1, sbj_year);
            ps.setString(2, sbj_sem.toString());
            rset = ps.executeQuery();
            while (rset.next()) {
                ClubSelection clubselection = new ClubSelection();
                clubselection.setCs_num(rset.getInt("cs_num"));
                clubselection.setCs_lower_limit(rset.getInt("cs_lower_limit"));
                clubselection.setTotal_limit(rset.getInt("total_limit"));
                
                Club club = new Club();
                club.setClub_name(rset.getString("club_name"));
                
                Staff staff = new Staff();
                staff.setStaff_cname(rset.getString("staff_cname"));
                
                ClubSem clubSem = new ClubSem();
                clubSem.setClub_room(rset.getString("club_room"));
                clubSem.setGrade_one((StringUtils.toCharacter(rset.getString("grade_one"))).equals('1')? true : false);
                clubSem.setGrade_two((StringUtils.toCharacter(rset.getString("grade_two"))).equals('1')? true : false);
                clubSem.setGrade_three((StringUtils.toCharacter(rset.getString("grade_three"))).equals('1')? true : false);
                
                clubselection.setClub(club);
                clubselection.setStaff(staff);
                clubselection.setClubSem(clubSem);
                
                result.add(clubselection);
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
