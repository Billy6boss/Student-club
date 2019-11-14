package ntpc.ccai.clubmgt.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import ntpc.ccai.clubmgt.bean.Club;
import ntpc.ccai.clubmgt.bean.ClubTeaching;
import ntpc.ccai.clubmgt.bean.Staff;
import ntpc.ccai.clubmgt.dao.ClubTeachingDao;
import ntpc.ccai.clubmgt.util.StringUtils;

public class ClubTeachingDaoImpl implements ClubTeachingDao {
	private static final Logger logger = Logger.getLogger(ClubTeachingDaoImpl.class);
    private static final String INSERT = "INSERT INTO stu_club_teaching(club_num, sbj_year, sbj_sem, staff_code) VALUES(?, ?, ?, ?)";
    private static final String DELETE = "DELETE FROM stu_club_teaching WHERE club_num=? AND sbj_year=? AND sbj_sem=? AND staff_code=?";
    private static final String SELECT_BY_CLUB_NUM_AND_YEAR_AND_SEM = "SELECT * FROM stu_club_teaching a JOIN staff b ON a.staff_code = b.staff_code JOIN stu_club c ON a.club_num = c.club_num WHERE a.club_num=? AND a.sbj_year=? AND a.sbj_sem=?";
    private static final String DELETE_BY_CLUB_NUM_AND_YEAR_AND_SEM = "DELETE FROM stu_club_teaching WHERE club_num = ? AND sbj_year = ? AND sbj_sem = ?";
    private static final String COPY = "INSERT INTO stu_club_teaching(club_num, sbj_year, sbj_sem, staff_code) SELECT club_num, ?, ?, staff_code FROM stu_club_teaching WHERE sbj_year = ? AND sbj_sem = ?";
    
    // need to rewrite
    private static final String SELECT_CLUB_TEACHING_BY_STAFFCODE_AND_IDNO = "SELECT a.CLUB_NUM,a.SBJ_YEAR,a.SBJ_SEM,a.CT_CODE,b.CLUB_NAME,c.STAFF_CNAME,c.STAFF_CODE FROM STU_CLUB_TEACHING a " + 
                                                                             "JOIN STU_CLUB b ON a.CLUB_NUM = b.CLUB_NUM " + 
                                                                             "JOIN STAFF c ON a.STAFF_CODE = c.STAFF_CODE " + 
                                                                             "WHERE a.SBJ_YEAR = ? AND a.SBJ_SEM = ? AND a.STAFF_CODE = ? OR IDNO = ? ";
    private static final String SELECT_CLUB_TEACHING_CT_CODE_BY_CLUB_NUM_AND_YEAR_AND_SEM_AND_CT_CODE = "SELECT * FROM STU_CLUB_TEACHING a WHERE a.CLUB_NUM = ? AND a.SBJ_YEAR = ? AND a.SBJ_SEM = ? AND a.CT_CODE = ?";
    private static final String UPDATA_CLUB_TEACHING_CT_CODE = "UPDATE STU_CLUB_TEACHING SET CT_CODE=? WHERE club_num=? AND sbj_year=? AND sbj_sem=? AND staff_code=? ";
    
        @Override
	public Boolean insert(Connection conn, ClubTeaching clubTeaching) {
		Boolean result = false;
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(INSERT);
            int i = 1;
            ps.setInt(i++, clubTeaching.getClub_num());
            ps.setInt(i++, clubTeaching.getSbj_year());
            ps.setString(i++, clubTeaching.getSbj_sem().toString());
            ps.setString(i++, clubTeaching.getStaff_code());
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
	public Boolean delete(Connection conn, ClubTeaching clubTeaching) {
		Boolean result = false;
        PreparedStatement ps = null;
        
        try {
            ps = conn.prepareStatement(DELETE);
            int i = 1;
            ps.setInt(i++, clubTeaching.getClub_num());
            ps.setInt(i++, clubTeaching.getSbj_year());
            ps.setString(i++, clubTeaching.getSbj_sem().toString());
            ps.setString(i++, clubTeaching.getStaff_code());
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
	public List<ClubTeaching> getClubTeachingByClubNumAndYearAndSem(Connection conn, Integer club_num, Integer sbj_year,
			Character sbj_sem) {
		List<ClubTeaching> result = new ArrayList<ClubTeaching>();
        PreparedStatement ps = null;
        ResultSet rset = null;
        
        try {
            ps = conn.prepareStatement(SELECT_BY_CLUB_NUM_AND_YEAR_AND_SEM);
            ps.setInt(1, club_num);
            ps.setInt(2, sbj_year);
            ps.setString(3, sbj_sem.toString());
            rset = ps.executeQuery();
            while (rset.next()) {
            	ClubTeaching clubteaching = new ClubTeaching();
            	clubteaching.setClub_num(rset.getInt("club_num"));
            	clubteaching.setSbj_year(rset.getInt("sbj_year"));
            	clubteaching.setSbj_sem(StringUtils.toCharacter(rset.getString("sbj_sem")));
            	clubteaching.setStaff_code(rset.getString("staff_code"));
            	
            	Club club = new Club();
                club.setClub_num(rset.getInt("club_num"));
                club.setCat_num(rset.getInt("cat_num"));
                club.setClub_name(rset.getString("club_name"));
                club.setClub_code(rset.getString("club_code"));
                club.setClub_info(rset.getString("club_info"));
                club.setSex(rset.getByte("sex"));
                club.setUrl(rset.getString("url"));
                clubteaching.setClub(club);
                
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
                clubteaching.setStaff(staff);
                
                result.add(clubteaching);
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

    // need to rewrite
    
    @Override
    public List<ClubTeaching> getClubTeachingByStaffcodeAndIdno(Connection conn, String staffcode, String idno, Integer sbj_year, Character sbj_sem) {
        List<ClubTeaching> result = new ArrayList<ClubTeaching>();
        PreparedStatement ps = null;
        ResultSet rset = null;
        
        try {
            ps = conn.prepareStatement(SELECT_CLUB_TEACHING_BY_STAFFCODE_AND_IDNO);
            
            ps.setInt(1, sbj_year);
            ps.setString(2, sbj_sem.toString());
            ps.setString(3, staffcode);
            ps.setString(4, idno);
            
            rset = ps.executeQuery();
            while (rset.next()) {
                ClubTeaching clubteaching = new ClubTeaching();
                clubteaching.setClub_num(rset.getInt("club_num"));
                clubteaching.setSbj_year(rset.getInt("sbj_year"));
                clubteaching.setSbj_sem(StringUtils.toCharacter(rset.getString("sbj_sem")));
                if(rset.getString("ct_code") == null) {
                    clubteaching.setCt_code("0");
                } else {
                    clubteaching.setCt_code(rset.getString("ct_code"));
                }
                Club club = new Club();
                club.setClub_name(rset.getString("club_name"));
                
                Staff staff = new Staff();
                staff.setStaff_cname(rset.getString("staff_cname"));
                staff.setStaff_code(rset.getString("staff_code"));
                clubteaching.setClub(club);
                clubteaching.setStaff(staff);
                result.add(clubteaching);
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
    public Boolean chkctcode(Connection conn, Integer club_num, Integer sbj_year, Character sbj_sem, String ct_code) {
        List<ClubTeaching> result = new ArrayList<ClubTeaching>();
        PreparedStatement ps = null;
        ResultSet rset = null;
        Boolean tof = null;
        try {
            ps = conn.prepareStatement(SELECT_CLUB_TEACHING_CT_CODE_BY_CLUB_NUM_AND_YEAR_AND_SEM_AND_CT_CODE);
            ps.setInt(1, club_num);
            ps.setInt(2, sbj_year);
            ps.setString(3, sbj_sem.toString());
            ps.setString(4, ct_code);
            
            rset = ps.executeQuery();
            while (rset.next()) {
                ClubTeaching clubteaching = new ClubTeaching();
                clubteaching.setClub_num(rset.getInt("club_num"));
                clubteaching.setSbj_year(rset.getInt("sbj_year"));
                clubteaching.setSbj_sem(StringUtils.toCharacter(rset.getString("sbj_sem")));
                if(rset.getString("ct_code") == null) {
                    clubteaching.setCt_code("0");
                } else {
                    clubteaching.setCt_code(rset.getString("ct_code"));
                }

                result.add(clubteaching);
            }
            logger.debug("result.size ：" + result.size());
            if(result.size() > 0 ) {
                tof = false;
            } else {
                tof = true;
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
        return tof;
    }

    @Override
    public Boolean updatactcode(Connection conn, Integer club_num, Integer sbj_year, Character sbj_sem, String ct_code,
            String staff_code) {
        Boolean result = null;
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(UPDATA_CLUB_TEACHING_CT_CODE);
            int i = 1;
            logger.debug("updatactcode ct_code ：" + ct_code);
            logger.debug("updatactcode club_num ：" + club_num);
            logger.debug("updatactcode sbj_year ：" + sbj_year);
            logger.debug("updatactcode sbj_sem ： " + sbj_sem);
            logger.debug("updatactcode staff_code ： " + staff_code);
            
            ps.setString(i++, ct_code);
            ps.setInt(i++, club_num);
            ps.setInt(i++, sbj_year);
            ps.setString(i++, sbj_sem.toString());
            ps.setString(i++, staff_code);
            
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

}
