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
import ntpc.ccai.clubmgt.bean.ClubCategory;
import ntpc.ccai.clubmgt.dao.ClubDao;
import ntpc.ccai.clubmgt.util.StringUtils;

public class ClubDaoImpl implements ClubDao {
    private static final String INSERT = "INSERT INTO stu_club (cat_num, club_name, club_code, club_info, sex, url) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String DELETE = "DELETE FROM stu_club WHERE club_num = ?";
    private static final String UPDATE = "UPDATE stu_club SET cat_num = ?, club_name = ?, club_code = ?, club_info = ?, sex = ?, url = ? WHERE club_num = ?";
    private static final String SELECT_ALL = "SELECT club_num, a.cat_num, club_name, club_code, club_info, sex, url, b.cat_name FROM stu_club a join common.stu_club_category b on a.cat_num = b.cat_num ";
    private static final String SELECT_BY_CATEGORY = SELECT_ALL + " WHERE cat_num = ?";
    private static final String SELECT_BY_CLUB_NUM = SELECT_ALL + " WHERE club_num = ?";
    private static final String SELECT_BY_CLUB_CODE = SELECT_ALL + " WHERE club_code like ?";
    private static final String SELECT_BY_CLUB_NAME = SELECT_ALL + " WHERE club_name like ?";
    private static final Logger logger = Logger.getLogger(ClubDaoImpl.class);

    @Override
    public Club insert(Connection conn, Club club) {
        PreparedStatement ps = null;
        ResultSet rset = null;
        
        try {
            ps = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            int i = 1;
            ps.setInt(i++, club.getCat_num());
            ps.setString(i++, club.getClub_name());
            ps.setString(i++, club.getClub_code());
            ps.setString(i++, club.getClub_info());
            ps.setByte(i++, club.getSex());
            ps.setString(i++, club.getUrl());
            int j = ps.executeUpdate();
            if (j == 1) {
                rset = ps.getGeneratedKeys();
                if (rset.next()) {
                    club.setClub_num(rset.getInt(1));
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
        return club;
    }

    @Override
    public Boolean delete(Connection conn, Integer club_num) {
        Boolean result = false;
        PreparedStatement ps = null;
        
        try {
            ps = conn.prepareStatement(DELETE);
            ps.setInt(1, club_num);
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
    public Boolean update(Connection conn, Club club) {
        Boolean result = false;
        PreparedStatement ps = null;
        
        try {
            ps = conn.prepareStatement(UPDATE);
            int i = 1;
            ps.setInt(i++, club.getCat_num());
            ps.setString(i++, club.getClub_name());
            ps.setString(i++, club.getClub_code());
            ps.setString(i++, club.getClub_info());
            ps.setByte(i++, club.getSex());
            ps.setString(i++, club.getUrl());
            ps.setInt(i++, club.getClub_num());
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
    public List<Club> getAllClubs(Connection conn) {
        List<Club> result = new ArrayList<Club>();
        PreparedStatement ps = null;
        ResultSet rset = null;
        
        try {
            ps = conn.prepareStatement(SELECT_ALL);
            rset = ps.executeQuery();
            while (rset.next()) {
                Club club = new Club();
                club.setClub_num(rset.getInt("club_num"));
                club.setCat_num(rset.getInt("cat_num"));
                club.setClub_name(rset.getString("club_name"));
                club.setClub_code(rset.getString("club_code"));
                club.setClub_info(rset.getString("club_info"));
                club.setSex(rset.getByte("sex"));
                club.setUrl(rset.getString("url"));
                
                ClubCategory clubCategory = new ClubCategory();
                clubCategory.setCat_num(rset.getInt("cat_num"));
                clubCategory.setCat_name(rset.getString("cat_name"));
                club.setClubCategory(clubCategory);
                
                result.add(club);
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
    public List<Club> getClubsByCategory(Connection conn, Integer cat_num) {
        List<Club> result = new ArrayList<Club>();
        PreparedStatement ps = null;
        ResultSet rset = null;
        
        try {
            ps = conn.prepareStatement(SELECT_BY_CATEGORY);
            ps.setInt(1, cat_num);
            rset = ps.executeQuery();
            while (rset.next()) {
                Club club = new Club();
                club.setClub_num(rset.getInt("club_num"));
                club.setCat_num(rset.getInt("cat_num"));
                club.setClub_name(rset.getString("club_name"));
                club.setClub_code(rset.getString("club_code"));
                club.setClub_info(rset.getString("club_info"));
                club.setSex(rset.getByte("sex"));
                club.setUrl(rset.getString("url"));
                
                ClubCategory clubCategory = new ClubCategory();
                clubCategory.setCat_num(rset.getInt("cat_num"));
                clubCategory.setCat_name(rset.getString("cat_name"));
                club.setClubCategory(clubCategory);
                
                result.add(club);
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
    public Club getClubByClubNum(Connection conn, Integer club_num) {
        Club result = null;
        PreparedStatement ps = null;
        ResultSet rset = null;
        
        try {
            ps = conn.prepareStatement(SELECT_BY_CLUB_NUM);
            ps.setInt(1, club_num);
            rset = ps.executeQuery();
            if (rset.next()) {
                result = new Club();
                result.setClub_num(rset.getInt("club_num"));
                result.setCat_num(rset.getInt("cat_num"));
                result.setClub_name(rset.getString("club_name"));
                result.setClub_code(rset.getString("club_code"));
                result.setClub_info(rset.getString("club_info"));
                result.setSex(rset.getByte("sex"));
                result.setUrl(rset.getString("url"));
                
                ClubCategory clubCategory = new ClubCategory();
                clubCategory.setCat_num(rset.getInt("cat_num"));
                clubCategory.setCat_name(rset.getString("cat_name"));
                result.setClubCategory(clubCategory);
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
    public List<Club> getClubsByClubCode(Connection conn, String club_code) {
        List<Club> result = new ArrayList<Club>();
        PreparedStatement ps = null;
        ResultSet rset = null;
        
        try {
            ps = conn.prepareStatement(SELECT_BY_CLUB_CODE);
            ps.setString(1, StringUtils.wrapWithPercentSign(club_code, 2));
            rset = ps.executeQuery();
            while (rset.next()) {
                Club club = new Club();
                club.setClub_num(rset.getInt("club_num"));
                club.setCat_num(rset.getInt("cat_num"));
                club.setClub_name(rset.getString("club_name"));
                club.setClub_code(rset.getString("club_code"));
                club.setClub_info(rset.getString("club_info"));
                club.setSex(rset.getByte("sex"));
                club.setUrl(rset.getString("url"));
                
                ClubCategory clubCategory = new ClubCategory();
                clubCategory.setCat_num(rset.getInt("cat_num"));
                clubCategory.setCat_name(rset.getString("cat_name"));
                club.setClubCategory(clubCategory);
                
                result.add(club);
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
    public List<Club> getClubsByClubName(Connection conn, String club_name) {
        List<Club> result = new ArrayList<Club>();
        PreparedStatement ps = null;
        ResultSet rset = null;
        
        try {
            ps = conn.prepareStatement(SELECT_BY_CLUB_NAME);
            ps.setString(1, StringUtils.wrapWithPercentSign(club_name, 2));
            rset = ps.executeQuery();
            while (rset.next()) {
                Club club = new Club();
                club.setClub_num(rset.getInt("club_num"));
                club.setCat_num(rset.getInt("cat_num"));
                club.setClub_name(rset.getString("club_name"));
                club.setClub_code(rset.getString("club_code"));
                club.setClub_info(rset.getString("club_info"));
                club.setSex(rset.getByte("sex"));
                club.setUrl(rset.getString("url"));
                
                ClubCategory clubCategory = new ClubCategory();
                clubCategory.setCat_num(rset.getInt("cat_num"));
                clubCategory.setCat_name(rset.getString("cat_name"));
                club.setClubCategory(clubCategory);
                
                result.add(club);
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
