package ntpc.ccai.clubmgt.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.log4j.Logger;

import ntpc.ccai.clubmgt.bean.Club;
import ntpc.ccai.clubmgt.bean.ClubMember;
import ntpc.ccai.clubmgt.bean.ClubSem;
import ntpc.ccai.clubmgt.bean.StuBasis;
import ntpc.ccai.clubmgt.dao.ClubSemDao;
import ntpc.ccai.clubmgt.util.StringUtils;

public class ClubSemDaoImpl implements ClubSemDao {
    private static final String INSERT = "INSERT INTO stu_club_sem (club_num, sbj_year, sbj_sem, grade_one, grade_two, grade_three, club_room, selectable) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE stu_club_sem SET grade_one = ?, grade_two = ?, grade_three = ?, club_room = ?, selectable = ? WHERE club_num = ? AND sbj_year = ? AND sbj_sem = ?";
    private static final String DELETE = "DELETE FROM stu_club_sem WHERE club_num = ? AND sbj_year = ? AND sbj_sem = ?";
    private static final String SELECT = "SELECT * FROM stu_club_sem a JOIN stu_club b ON a.club_num = b.club_num ";
    private static final String SELECT_BY_CLUB_NUM_AND_YEAR_AND_SEM = SELECT + " WHERE a.club_num = ? AND a.sbj_year = ? AND a.sbj_sem = ?";
    private static final String SELECT_BY_YEAR_AND_SEM = SELECT + "WHERE a.sbj_year = ? AND a.sbj_sem = ?";
    private static final String SELECT_BY_CLUB_NAME_AND_YEAR_AND_SEM = SELECT + " WHERE b.club_name like ? AND a.sbj_year = ? AND a.sbj_sem = ?";
    private static final String SELECT_BY_CLUB_CODE_AND_YEAR_AND_SEM = SELECT + " WHERE b.club_code like ? AND a.sbj_year = ? AND a.sbj_sem = ?";
    private static final String SELECT_BY_CLUB_CATEGORY_AND_YEAR_AND_SEM = SELECT + " WHERE b.cat_num = ? AND a.sbj_year = ? AND a.sbj_sem = ?";
    private static final String SELECT_YEAR_AND_SEM = "SELECT UNIQUE sbj_year, sbj_sem FROM stu_club_sem ORDER BY sbj_year, sbj_sem";
    private static final String COPY = "INSERT INTO stu_club_sem (club_num, sbj_year, sbj_sem, grade_one, grade_two, grade_three, club_room, selectable) SELECT club_num, ?, ?, grade_one, grade_two, grade_three, club_room, selectable FROM stu_club_sem WHERE sbj_year = ? AND sbj_sem = ?";
    
    // need to rewrite
    private static final String SELECT_ALL = "SELECT * FROM STU_CLUB_SEM";
    private static final String SELECT_ALL_YEAR = "SELECT a.SBJ_YEAR , a.SBJ_SEM FROM STU_CLUB_SEM a GROUP BY a.SBJ_YEAR , a.SBJ_SEM" ;
    private static final String SELECT_CLUB_NAME_AND_YEAR_AND_SEM = "SELECT a.CLUB_NUM , a.SBJ_YEAR, a.SBJ_SEM , b.CLUB_NAME FROM STU_CLUB_SEM a JOIN STU_CLUB b ON a.CLUB_NUM = b.CLUB_NUM WHERE  SBJ_YEAR = ? AND SBJ_SEM = ? ";
    //社團績優 (query table)
    private static final String SELECT_CLUB_MEMBER_SCORE_BY_NUM_AND_YEAR_AND_SEM = "SELECT a.SBJ_YEAR,a.SBJ_SEM,e.CLS_CNAME,c.RGNO,d.CNAME,d.SEX,b.CLUB_SCORE,g.CLUB_NAME,b.ISSUE_CODE,b.ex_code FROM STU_CLUB_SEM a  " + 
                                                                                   "JOIN STU_CLUB g ON a.CLUB_NUM = g.CLUB_NUM " + 
                                                                                   "JOIN STU_CLUB_MEMBER b ON a.CLUB_NUM = b.CLUB_NUM AND a.SBJ_YEAR = b.SBJ_YEAR AND a.SBJ_SEM = b.SBJ_SEM  " + 
                                                                                   "JOIN STU_REGISTER c ON b.RGNO = c.RGNO AND a.SBJ_YEAR = c.SBJ_YEAR AND a.SBJ_SEM = c.SBJ_SEM " + 
                                                                                   "JOIN STU_BASIS d ON c.RGNO = d.RGNO " + 
                                                                                   "JOIN STU_CLASS e ON c.CLS_CODE = e.CLS_CODE AND a.SBJ_YEAR = e.SBJ_YEAR AND a.SBJ_SEM = e.SBJ_SEM " + 
                                                                                   "LEFT JOIN STU_CLUB_MEMBER_CADRE f ON b.RGNO = f.RGNO " + 
                                                                                   "LEFT JOIN common.STU_CLUB_CADRE h ON f.CADRE_NUM = h.CADRE_NUM " + 
                                                                                   "WHERE  a.CLUB_NUM = ? AND a.SBJ_YEAR = ? AND a.SBJ_SEM = ? " + 
                                                                                   "GROUP BY a.SBJ_YEAR,a.SBJ_SEM,e.CLS_CNAME,c.RGNO,d.CNAME,d.SEX,b.CLUB_SCORE,g.CLUB_NAME,b.ISSUE_CODE,b.ex_code " + 
                                                                                   "ORDER BY b.CLUB_SCORE DESC ";
  //社團績優 (pdf)
    private static final String SELECT_CLUB_MEMBER_SCORE_BY_NUM_AND_YEAR_AND_SEM_AND_RGNO = "SELECT a.CLUB_NUM,a.SBJ_YEAR,a.SBJ_SEM,e.CLS_CNAME,c.RGNO,d.CNAME,d.SEX,h.CADRE_NAME,b.CLUB_SCORE,g.CLUB_NAME,b.ISSUE_CODE,b.ex_code FROM STU_CLUB_SEM a  " + 
                                                                                            "JOIN STU_CLUB g ON a.CLUB_NUM = g.CLUB_NUM " + 
                                                                                            "JOIN STU_CLUB_MEMBER b ON a.CLUB_NUM = b.CLUB_NUM AND a.SBJ_YEAR = b.SBJ_YEAR AND a.SBJ_SEM = b.SBJ_SEM  " + 
                                                                                            "JOIN STU_REGISTER c ON b.RGNO = c.RGNO AND a.SBJ_YEAR = c.SBJ_YEAR AND a.SBJ_SEM = c.SBJ_SEM " + 
                                                                                            "JOIN STU_BASIS d ON c.RGNO = d.RGNO " + 
                                                                                            "JOIN STU_CLASS e ON c.CLS_CODE = e.CLS_CODE AND a.SBJ_YEAR = e.SBJ_YEAR AND a.SBJ_SEM = e.SBJ_SEM " + 
                                                                                            "LEFT JOIN STU_CLUB_MEMBER_CADRE f ON b.RGNO = f.RGNO " + 
                                                                                            "LEFT JOIN common.STU_CLUB_CADRE h ON f.CADRE_NUM = h.CADRE_NUM " + 
                                                                                            "WHERE  a.CLUB_NUM = ? AND a.SBJ_YEAR = ? AND a.SBJ_SEM = ? AND c.RGNO = ? " + 
                                                                                            "GROUP BY a.CLUB_NUM,a.SBJ_YEAR,a.SBJ_SEM,e.CLS_CNAME,c.RGNO,d.CNAME,d.SEX,h.CADRE_NAME,b.CLUB_SCORE,g.CLUB_NAME,b.ISSUE_CODE,b.ex_code " + 
                                                                                            "ORDER BY b.CLUB_SCORE DESC ";
 
    private static final Logger logger = Logger.getLogger(ClubSemDaoImpl.class);

    @Override
    public Boolean insert(Connection conn, ClubSem clubSem) {
        Boolean result = false;
        PreparedStatement ps = null;
        
        try {
            ps = conn.prepareStatement(INSERT);
            int i = 1;
            ps.setInt(i++, clubSem.getClub_num());
            ps.setInt(i++, clubSem.getSbj_year());
            ps.setString(i++, clubSem.getSbj_sem().toString());
            ps.setBoolean(i++, clubSem.getGrade_one());
            ps.setBoolean(i++, clubSem.getGrade_two());
            ps.setBoolean(i++, clubSem.getGrade_three());
            ps.setString(i++, clubSem.getClub_room());
            ps.setBoolean(i++, clubSem.getSelectable());
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
    public Boolean update(Connection conn, ClubSem clubSem) {
        Boolean result = false;
        PreparedStatement ps = null;
        
        try {
            ps = conn.prepareStatement(UPDATE);
            int i = 1;
            ps.setBoolean(i++, clubSem.getGrade_one());
            ps.setBoolean(i++, clubSem.getGrade_two());
            ps.setBoolean(i++, clubSem.getGrade_three());
            ps.setString(i++, clubSem.getClub_room());
            ps.setBoolean(i++, clubSem.getSelectable());
            ps.setInt(i++, clubSem.getClub_num());
            ps.setInt(i++, clubSem.getSbj_year());
            ps.setString(i++, clubSem.getSbj_sem().toString());
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
    public Boolean delete(Connection conn, Integer club_num, Integer sbj_year, Character sbj_sem) {
        Boolean result = false;
        PreparedStatement ps = null;
        
        try {
            ps = conn.prepareStatement(DELETE);
            int i = 1;
            ps.setInt(i++, club_num);
            ps.setInt(i++, sbj_year);
            ps.setString(i++, sbj_sem.toString());
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
    public ClubSem getClubSemByClubNumAndYearAndSem(Connection conn, Integer club_num, Integer sbj_year,
            Character sbj_sem) {
        ClubSem result = null;
        PreparedStatement ps = null;
        ResultSet rset = null;
        
        try {
            ps = conn.prepareStatement(SELECT_BY_CLUB_NUM_AND_YEAR_AND_SEM);
            int i = 1;
            ps.setInt(i++, club_num);
            ps.setInt(i++, sbj_year);
            ps.setString(i++, sbj_sem.toString());
            rset = ps.executeQuery();
            if (rset.next()) {
                result = new ClubSem();
                result.setClub_num(rset.getInt("club_num"));
                result.setSbj_year(rset.getInt("sbj_year"));
                result.setSbj_sem(StringUtils.toCharacter(rset.getString("sbj_sem")));
                result.setGrade_one(rset.getBoolean("grade_one"));
                result.setGrade_two(rset.getBoolean("grade_two"));
                result.setGrade_three(rset.getBoolean("grade_three"));
                result.setClub_room(rset.getString("club_room"));
                result.setSelectable(rset.getBoolean("selectable"));
                
                Club club = new Club();
                club.setClub_num(rset.getInt("club_num"));
                club.setCat_num(rset.getInt("cat_num"));
                club.setClub_name(rset.getString("club_name"));
                club.setClub_code(rset.getString("club_code"));
                club.setClub_info(rset.getString("club_info"));
                club.setSex(rset.getByte("sex"));
                club.setUrl(rset.getString("url"));
                result.setClub(club);
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
    public List<ClubSem> getClubSemsByClubNameAndYearAndSem(Connection conn, String club_name, Integer sbj_year,
            Character sbj_sem) {
        List<ClubSem> result = new ArrayList<ClubSem>();
        PreparedStatement ps = null;
        ResultSet rset = null;
        
        try {
            ps = conn.prepareStatement(SELECT_BY_CLUB_NAME_AND_YEAR_AND_SEM);
            int i = 1;
            ps.setString(i++, StringUtils.wrapWithPercentSign(club_name, 2));
            ps.setInt(i++, sbj_year);
            ps.setString(i++, sbj_sem.toString());
            rset = ps.executeQuery();
            while (rset.next()) {
                ClubSem clubSem = new ClubSem();
                clubSem.setClub_num(rset.getInt("club_num"));
                clubSem.setSbj_year(rset.getInt("sbj_year"));
                clubSem.setSbj_sem(StringUtils.toCharacter(rset.getString("sbj_sem")));
                clubSem.setGrade_one(rset.getBoolean("grade_one"));
                clubSem.setGrade_two(rset.getBoolean("grade_two"));
                clubSem.setGrade_three(rset.getBoolean("grade_three"));
                clubSem.setClub_room(rset.getString("club_room"));
                clubSem.setSelectable(rset.getBoolean("selectable"));
                
                Club club = new Club();
                club.setClub_num(rset.getInt("club_num"));
                club.setCat_num(rset.getInt("cat_num"));
                club.setClub_name(rset.getString("club_name"));
                club.setClub_code(rset.getString("club_code"));
                club.setClub_info(rset.getString("club_info"));
                club.setSex(rset.getByte("sex"));
                club.setUrl(rset.getString("url"));
                clubSem.setClub(club);
                
                result.add(clubSem);
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
    public List<ClubSem> getClubSemsByClubCodeAndYearAndSem(Connection conn, String club_code, Integer sbj_year,
            Character sbj_sem) {
        List<ClubSem> result = new ArrayList<ClubSem>();
        PreparedStatement ps = null;
        ResultSet rset = null;
        
        try {
            ps = conn.prepareStatement(SELECT_BY_CLUB_CODE_AND_YEAR_AND_SEM);
            int i = 1;
            ps.setString(i++, StringUtils.wrapWithPercentSign(club_code, 2));
            ps.setInt(i++, sbj_year);
            ps.setString(i++, sbj_sem.toString());
            rset = ps.executeQuery();
            while (rset.next()) {
                ClubSem clubSem = new ClubSem();
                clubSem.setClub_num(rset.getInt("club_num"));
                clubSem.setSbj_year(rset.getInt("sbj_year"));
                clubSem.setSbj_sem(StringUtils.toCharacter(rset.getString("sbj_sem")));
                clubSem.setGrade_one(rset.getBoolean("grade_one"));
                clubSem.setGrade_two(rset.getBoolean("grade_two"));
                clubSem.setGrade_three(rset.getBoolean("grade_three"));
                clubSem.setClub_room(rset.getString("club_room"));
                clubSem.setSelectable(rset.getBoolean("selectable"));
                
                Club club = new Club();
                club.setClub_num(rset.getInt("club_num"));
                club.setCat_num(rset.getInt("cat_num"));
                club.setClub_name(rset.getString("club_name"));
                club.setClub_code(rset.getString("club_code"));
                club.setClub_info(rset.getString("club_info"));
                club.setSex(rset.getByte("sex"));
                club.setUrl(rset.getString("url"));
                clubSem.setClub(club);
                
                result.add(clubSem);
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
    public List<ClubSem> getClubSemsByClubCategoryAndYearAndSem(Connection conn, Integer cat_num, Integer sbj_year,
            Character sbj_sem) {
        List<ClubSem> result = new ArrayList<ClubSem>();
        PreparedStatement ps = null;
        ResultSet rset = null;
        
        try {
            ps = conn.prepareStatement(SELECT_BY_CLUB_CATEGORY_AND_YEAR_AND_SEM);
            int i = 1;
            ps.setInt(i++, cat_num);
            ps.setInt(i++, sbj_year);
            ps.setString(i++, sbj_sem.toString());
            rset = ps.executeQuery();
            while (rset.next()) {
                ClubSem clubSem = new ClubSem();
                clubSem.setClub_num(rset.getInt("club_num"));
                clubSem.setSbj_year(rset.getInt("sbj_year"));
                clubSem.setSbj_sem(StringUtils.toCharacter(rset.getString("sbj_sem")));
                clubSem.setGrade_one(rset.getBoolean("grade_one"));
                clubSem.setGrade_two(rset.getBoolean("grade_two"));
                clubSem.setGrade_three(rset.getBoolean("grade_three"));
                clubSem.setClub_room(rset.getString("club_room"));
                clubSem.setSelectable(rset.getBoolean("selectable"));
                
                Club club = new Club();
                club.setClub_num(rset.getInt("club_num"));
                club.setCat_num(rset.getInt("cat_num"));
                club.setClub_name(rset.getString("club_name"));
                club.setClub_code(rset.getString("club_code"));
                club.setClub_info(rset.getString("club_info"));
                club.setSex(rset.getByte("sex"));
                club.setUrl(rset.getString("url"));
                clubSem.setClub(club);
                
                result.add(clubSem);
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
    public List<ClubSem> getClubSemsByYearAndSem(Connection conn, Integer sbj_year, Character sbj_sem) {
        List<ClubSem> result = new ArrayList<ClubSem>();
        PreparedStatement ps = null;
        ResultSet rset = null;
        
        try {
            ps = conn.prepareStatement(SELECT_BY_YEAR_AND_SEM);
            int i = 1;
            ps.setInt(i++, sbj_year);
            ps.setString(i++, sbj_sem.toString());
            rset = ps.executeQuery();
            while (rset.next()) {
                ClubSem clubSem = new ClubSem();
                clubSem.setClub_num(rset.getInt("club_num"));
                clubSem.setSbj_year(rset.getInt("sbj_year"));
                clubSem.setSbj_sem(StringUtils.toCharacter(rset.getString("sbj_sem")));
                clubSem.setGrade_one(rset.getBoolean("grade_one"));
                clubSem.setGrade_two(rset.getBoolean("grade_two"));
                clubSem.setGrade_three(rset.getBoolean("grade_three"));
                clubSem.setClub_room(rset.getString("club_room"));
                clubSem.setSelectable(rset.getBoolean("selectable"));
                
                Club club = new Club();
                club.setClub_num(rset.getInt("club_num"));
                club.setCat_num(rset.getInt("cat_num"));
                club.setClub_name(rset.getString("club_name"));
                club.setClub_code(rset.getString("club_code"));
                club.setClub_info(rset.getString("club_info"));
                club.setSex(rset.getByte("sex"));
                club.setUrl(rset.getString("url"));
                clubSem.setClub(club);
                
                result.add(clubSem);
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
    public Map<Integer, Set<Character>> getClubYearsAndSemesters(Connection conn) {
        Map<Integer, Set<Character>> result = new TreeMap<Integer, Set<Character>>();
        PreparedStatement ps = null;
        ResultSet rset = null;
        
        try {
            ps = conn.prepareStatement(SELECT_YEAR_AND_SEM);
            rset = ps.executeQuery();
            while (rset.next()) {
                Integer year = rset.getInt(1);
                if (result.containsKey(year)) {
                    result.get(year).add(StringUtils.toCharacter(rset.getString(2)));
                } else {
                    Set<Character> set = new TreeSet<Character>();
                    set.add(StringUtils.toCharacter(rset.getString(2)));
                    result.put(year, set);
                }
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

    // need to rewrite
    
    @Override
    public List<ClubSem> getClubSemList(Connection conn) {
        List<ClubSem> result = new ArrayList<ClubSem>();
        PreparedStatement ps = null;
        ResultSet rset = null;
        
        try {
            ps = conn.prepareStatement(SELECT_ALL);
            rset = ps.executeQuery();
                     
            while (rset.next()) {
                ClubSem clubSem = new ClubSem();
                clubSem.setClub_num(rset.getInt("club_num"));
                clubSem.setSbj_year(rset.getInt("sbj_year"));
                clubSem.setGrade_one(rset.getBoolean("grade_one"));
                clubSem.setGrade_two(rset.getBoolean("grade_two"));
                clubSem.setGrade_three(rset.getBoolean("grade_three"));
                clubSem.setClub_room(rset.getString("club_room"));
                clubSem.setSelectable(rset.getBoolean("selectable"));
                result.add(clubSem);
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
    public List<ClubSem> getClubYearList(Connection conn) {
        List<ClubSem> result = new ArrayList<ClubSem>();
        PreparedStatement ps = null;
        ResultSet rset = null;
        
        try {
            ps = conn.prepareStatement(SELECT_ALL_YEAR);
            rset = ps.executeQuery();
                     
            while (rset.next()) {
                ClubSem clubSem = new ClubSem();
                clubSem.setSbj_year(rset.getInt("sbj_year"));
                result.add(clubSem);
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
    public List<ClubSem> getClubDataList(Connection conn, Integer sbj_year, Character sbj_sem) {
        List<ClubSem> result = new ArrayList<ClubSem>();
        PreparedStatement ps = null;
        ResultSet rset = null;
        
        try {
            ps = conn.prepareStatement(SELECT_CLUB_NAME_AND_YEAR_AND_SEM);
            int i = 1;
            ps.setInt(i++, sbj_year);
            ps.setString(i++, sbj_sem.toString());
            rset = ps.executeQuery();                    
            while (rset.next()) {
                ClubSem clubSem = new ClubSem();
                clubSem.setClub_num(rset.getInt("club_num"));
                clubSem.setSbj_year(rset.getInt("sbj_year"));
                clubSem.setSbj_sem(StringUtils.toCharacter(rset.getString("sbj_sem")));
                
                Club club = new Club();
                club.setClub_name(rset.getString("club_name"));
                clubSem.setClub(club);
                
                result.add(clubSem);
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
    public List<ClubSem> getClubMenberScore(Connection conn, Integer club_num, Integer sbj_year, Character sbj_sem) {
        List<ClubSem> result = new ArrayList<ClubSem>();
        PreparedStatement ps = null;
        ResultSet rset = null;
        
        try {
            ps = conn.prepareStatement(SELECT_CLUB_MEMBER_SCORE_BY_NUM_AND_YEAR_AND_SEM);
            int i = 1;
            ps.setInt(i++, club_num);
            ps.setInt(i++, sbj_year);
            ps.setString(i++, sbj_sem.toString());
            rset = ps.executeQuery();                    
            while (rset.next()) {
                ClubSem clubSem = new ClubSem();
                clubSem.setSbj_year(rset.getInt("sbj_year"));
                clubSem.setSbj_sem(StringUtils.toCharacter(rset.getString("sbj_sem")));
                
                Club club = new Club();
                club.setClub_name(rset.getString("club_name"));
                clubSem.setClub(club);
                
                StuBasis stuBasis = new StuBasis();
                stuBasis.setCname(rset.getString("cname"));
                stuBasis.setRgno(rset.getInt("rgno"));
                clubSem.setStubasis(stuBasis);
                
                ClubMember clubMembers = new ClubMember();
                clubMembers.setClub_score(rset.getInt("club_score"));
                            
                if(rset.getString("issue_code") == null) {
                    clubMembers.setIssue_code("0");
                } else {                
                    clubMembers.setIssue_code(rset.getString("issue_code"));
                }
                if(rset.getString("ex_code") == null) {
                    clubMembers.setEx_code("0");
                } else {                
                    clubMembers.setEx_code(rset.getString("ex_code"));
                }
                
                clubSem.setClubMember(clubMembers);            
                
                result.add(clubSem);
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
    public List<ClubSem> getClubMenberScorePDF(Connection conn, Integer club_num, Integer sbj_year, Character sbj_sem,
            Integer rgno) {
        List<ClubSem> result = new ArrayList<ClubSem>();
        PreparedStatement ps = null;
        ResultSet rset = null;
        
        try {
            ps = conn.prepareStatement(SELECT_CLUB_MEMBER_SCORE_BY_NUM_AND_YEAR_AND_SEM_AND_RGNO);
            int i = 1;
            ps.setInt(i++, club_num);
            ps.setInt(i++, sbj_year);
            ps.setString(i++, sbj_sem.toString());
            ps.setInt(i++, rgno);
            rset = ps.executeQuery();                    
            while (rset.next()) {
                ClubSem clubSem = new ClubSem();
                clubSem.setClub_num(rset.getInt("club_num"));
                clubSem.setSbj_year(rset.getInt("sbj_year"));
                clubSem.setSbj_sem(StringUtils.toCharacter(rset.getString("sbj_sem")));
                
                Club club = new Club();
                club.setClub_name(rset.getString("club_name"));
                clubSem.setClub(club);
                
                StuBasis stuBasis = new StuBasis();
                stuBasis.setCname(rset.getString("cname"));
                stuBasis.setRgno(rset.getInt("rgno"));
                clubSem.setStubasis(stuBasis);
                
                ClubMember clubMember = new ClubMember();
                clubMember.setClub_score(rset.getInt("club_score"));
                clubMember.setIssue_code(rset.getString("issue_code"));
                clubMember.setEx_code(rset.getString("ex_code"));
                clubSem.setClubMember(clubMember);
                
                result.add(clubSem);
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
