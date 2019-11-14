package ntpc.ccai.clubmgt.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ntpc.ccai.clubmgt.bean.Club;
import ntpc.ccai.clubmgt.bean.ClubCadre;
import ntpc.ccai.clubmgt.bean.ClubMemberCadre;
import ntpc.ccai.clubmgt.bean.StuBasis;
import ntpc.ccai.clubmgt.bean.StuClass;
import ntpc.ccai.clubmgt.dao.ClubMemberCadreDao;
import ntpc.ccai.clubmgt.util.StringUtils;

public class ClubMemberCadreDaoImpl implements ClubMemberCadreDao {
    private static final Logger logger = Logger.getLogger(ClubMemberCadreDaoImpl.class);
    private static final String INSERT = "INSERT INTO stu_club_member_cadre (club_num, sbj_year, sbj_sem, rgno, cadre_num) VALUES (?, ?, ?, ?, ?)";
    private static final String DELETE_BY_PRIMARY_KEY = "DELETE FROM stu_club_member_cadre WHERE club_num = ? AND sbj_year= ? AND sbj_sem = ? AND rgno = ? AND cadre_num = ?";
    private static final String SELECT_BY_RGNO = "SELECT club_num, sbj_year, sbj_sem, rgno, cadre_num FROM stu_club_member_cadre WHERE club_num = ? AND sbj_year= ? AND sbj_sem = ? AND rgno = ?";
    private static final String SELECT = "SELECT club_num, sbj_year, sbj_sem, rgno, cadre_num FROM stu_club_member_cadre WHERE club_num = ? AND sbj_year= ? AND sbj_sem = ?";
    private static final String DELETE_BY_RGNO = "DELETE FROM stu_club_member_cadre WHERE club_num = ? AND sbj_year= ? AND sbj_sem = ? AND rgno = ?";
    private static final String DELETE_BY_CLUB_NUM_YEAR_AND_SEM = "DELETE FROM stu_club_member_cadre WHERE club_num = ? AND sbj_year= ? AND sbj_sem = ?";
    // need to rewrite
    private static final String SELECT_CLUB_MEMBER_CADER_EXPERIENCE_BY_REGNO_OR_IDNO = "SELECT d.RGNO,d.CNAME,e.CLS_CNAME,d.SEX,d.BIRTHDAY,d.REG_NO,d.CMAT_YEAR,g.CLUB_NAME,f.SBJ_YEAR,f.SBJ_SEM,h.CADRE_NAME FROM STU_CLUB_SEM a " + 
            "JOIN STU_CLUB g ON a.CLUB_NUM = g.CLUB_NUM " + 
            "JOIN STU_CLUB_MEMBER b ON a.CLUB_NUM = b.CLUB_NUM AND a.SBJ_YEAR = b.SBJ_YEAR AND a.SBJ_SEM = b.SBJ_SEM " + 
            "JOIN STU_REGISTER c ON b.RGNO = c.RGNO AND a.SBJ_YEAR = c.SBJ_YEAR AND a.SBJ_SEM = c.SBJ_SEM " + 
            "JOIN STU_BASIS d ON c.RGNO = d.RGNO " + 
            "JOIN STU_CLASS e ON c.CLS_CODE = e.CLS_CODE AND a.SBJ_YEAR = e.SBJ_YEAR AND a.SBJ_SEM = e.SBJ_SEM " + 
            "LEFT JOIN STU_CLUB_MEMBER_CADRE f ON b.RGNO = f.RGNO " + 
            "LEFT JOIN common.STU_CLUB_CADRE h ON f.CADRE_NUM = h.CADRE_NUM " + 
            "WHERE d.REG_NO = ? OR d.IDNO = ? " + 
            "GROUP BY d.RGNO,d.CNAME,e.CLS_CNAME,d.SEX,d.BIRTHDAY,d.REG_NO,d.CMAT_YEAR,g.CLUB_NAME,f.SBJ_YEAR,f.SBJ_SEM,h.CADRE_NAME ";

    
    
    @Override
    public Boolean insert(Connection conn, ClubMemberCadre clubMemberCadre) {
        Boolean result = false;
        PreparedStatement ps = null;
        
        try {
            ps = conn.prepareStatement(INSERT);
            int i = 1;
            ps.setInt(i++, clubMemberCadre.getClub_num());
            ps.setInt(i++, clubMemberCadre.getSbj_year());
            ps.setString(i++, clubMemberCadre.getSbj_sem().toString());
            ps.setInt(i++, clubMemberCadre.getRgno());
            ps.setInt(i++, clubMemberCadre.getCadre_num());
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
    public Boolean delete(Connection conn, ClubMemberCadre clubMemberCadre) {
        Boolean result = false;
        PreparedStatement ps = null;
        
        try {
            ps = conn.prepareStatement(DELETE_BY_PRIMARY_KEY);
            int i = 1;
            ps.setInt(i++, clubMemberCadre.getClub_num());
            ps.setInt(i++, clubMemberCadre.getSbj_year());
            ps.setString(i++, clubMemberCadre.getSbj_sem().toString());
            ps.setInt(i++, clubMemberCadre.getRgno());
            ps.setInt(i++, clubMemberCadre.getCadre_num());
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
    public List<ClubMemberCadre> getClubMemberCadreByClubNumAndYearAndSemAndRgno(Connection conn, Integer club_num,
            Integer sbj_year, Character sbj_sem, Integer rgno) {
        List<ClubMemberCadre> result = new ArrayList<ClubMemberCadre>();
        PreparedStatement ps = null;
        ResultSet rset = null;
        
        try {
            ps = conn.prepareStatement(SELECT_BY_RGNO);
            int i = 1;
            ps.setInt(i++, club_num);
            ps.setInt(i++, sbj_year);
            ps.setString(i++, sbj_sem.toString());
            ps.setInt(i++, rgno);
            rset = ps.executeQuery();
            while (rset.next()) {
                ClubMemberCadre clubMemberCadre = new ClubMemberCadre();
                clubMemberCadre.setClub_num(rset.getInt("club_num"));
                clubMemberCadre.setSbj_year(rset.getInt("sbj_year"));
                clubMemberCadre.setSbj_sem(StringUtils.toCharacter(rset.getString("sbj_sem")));
                clubMemberCadre.setRgno(rset.getInt("rgno"));
                clubMemberCadre.setCadre_num(rset.getInt("cadre_num"));
                result.add(clubMemberCadre);
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
	public Integer delete(Connection conn, Integer club_num, Integer sbj_year, Character sbj_sem, Integer rgno) {
	    Integer result = null;
        PreparedStatement ps = null;
        
        try {
            ps = conn.prepareStatement(DELETE_BY_RGNO);
            int i = 1;
            ps.setInt(i++, club_num);
            ps.setInt(i++, sbj_year);
            ps.setString(i++, sbj_sem.toString());
            ps.setInt(i++, rgno);
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
	public Integer delete(Connection conn, Integer club_num, Integer sbj_year, Character sbj_sem) {
	    Integer result = null;
        PreparedStatement ps = null;
        
        try {
            ps = conn.prepareStatement(DELETE_BY_CLUB_NUM_YEAR_AND_SEM);
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
    public List<ClubMemberCadre> getClubMemberCadreByClubNumAndYearAndSem(Connection conn, Integer club_num,
            Integer sbj_year, Character sbj_sem) {
        List<ClubMemberCadre> result = new ArrayList<ClubMemberCadre>();
        PreparedStatement ps = null;
        ResultSet rset = null;
        
        try {
            ps = conn.prepareStatement(SELECT);
            int i = 1;
            ps.setInt(i++, club_num);
            ps.setInt(i++, sbj_year);
            ps.setString(i++, sbj_sem.toString());
            rset = ps.executeQuery();
            while (rset.next()) {
                ClubMemberCadre clubMemberCadre = new ClubMemberCadre();
                clubMemberCadre.setClub_num(rset.getInt("club_num"));
                clubMemberCadre.setSbj_year(rset.getInt("sbj_year"));
                clubMemberCadre.setSbj_sem(StringUtils.toCharacter(rset.getString("sbj_sem")));
                clubMemberCadre.setRgno(rset.getInt("rgno"));
                clubMemberCadre.setCadre_num(rset.getInt("cadre_num"));
                result.add(clubMemberCadre);
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
    public List<ClubMemberCadre> getClubMemberCadreExperienceByRegnoOrIdno(Connection conn, String reg_no,
            String idno) {
        List<ClubMemberCadre> result = new ArrayList<ClubMemberCadre>();
        PreparedStatement ps = null;
        ResultSet rset = null;
        
        try {
            ps = conn.prepareStatement(SELECT_CLUB_MEMBER_CADER_EXPERIENCE_BY_REGNO_OR_IDNO);
            ps.setString(1, reg_no);
            ps.setString(2, idno);            
            rset = ps.executeQuery();
            while (rset.next()) {
                ClubMemberCadre clubMemberCadre = new ClubMemberCadre();
                clubMemberCadre.setSbj_year(rset.getInt("sbj_year"));
                clubMemberCadre.setSbj_sem(StringUtils.toCharacter(rset.getString("sbj_sem")));
                
                StuBasis stuBasis = new StuBasis();
                stuBasis.setCname(rset.getString("cname"));
                stuBasis.setSex(rset.getByte("sex"));
                stuBasis.setBirthday(rset.getDate("birthday"));
                stuBasis.setReg_no(rset.getString("reg_no"));
                stuBasis.setCmat_year(rset.getShort("cmat_year"));               
                stuBasis.setRgno(rset.getInt("rgno"));
                
                StuClass stuClass = new StuClass();
                stuClass.setCls_cname(rset.getString("cls_cname"));
                
                Club club = new Club();
                club.setClub_name(rset.getString("club_name"));
                
                ClubCadre clubCadre = new ClubCadre();
                clubCadre.setCadre_name(rset.getString("cadre_name"));
                
                clubMemberCadre.setStuBasis(stuBasis);
                clubMemberCadre.setStuClass(stuClass);
                clubMemberCadre.setClub(club);
                clubMemberCadre.setClubCadre(clubCadre);
                result.add(clubMemberCadre);
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
