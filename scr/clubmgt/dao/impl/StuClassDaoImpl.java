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
import ntpc.ccai.clubmgt.bean.StuBasis;
import ntpc.ccai.clubmgt.bean.StuClass;
import ntpc.ccai.clubmgt.dao.StuClassDao;
import ntpc.ccai.clubmgt.util.StringUtils;

public class StuClassDaoImpl implements StuClassDao {
	private static final Logger logger = Logger.getLogger(StuClassDaoImpl.class);
	private static final String SELECT_BY_YEAR_AND_SEM ="SELECT sbj_year, sbj_sem, cls_code, cls_cname, mat_code, div_code, edu_dep_code, grade, cls_seq, use_flag FROM stu_class WHERE sbj_year=? AND sbj_sem=?";
	private static final String WHERE_GRADE = " AND grade=?";
	private static final String WHERE_ClsCode = " AND cls_code=?";
	private static final String SELECT_CLASS_CLUB_BY_YEAR_AND_SEM_AND_CLSCODE = "SELECT g.CLUB_NAME,b.SBJ_YEAR,b.SBJ_SEM,b.RGNO,d.CNAME,e.CLS_CNAME,d.SEX,h.CADRE_NAME FROM STU_CLUB_SEM a " + 
            "JOIN STU_CLUB g ON a.CLUB_NUM = g.CLUB_NUM " + 
            "JOIN STU_CLUB_MEMBER b ON a.CLUB_NUM = b.CLUB_NUM AND a.SBJ_YEAR = b.SBJ_YEAR AND a.SBJ_SEM = b.SBJ_SEM " + 
            "JOIN STU_REGISTER c ON b.RGNO = c.RGNO AND a.SBJ_YEAR = c.SBJ_YEAR AND a.SBJ_SEM = c.SBJ_SEM " + 
            "JOIN STU_BASIS d ON c.RGNO = d.RGNO " + 
            "JOIN STU_CLASS e ON c.CLS_CODE = e.CLS_CODE AND a.SBJ_YEAR = e.SBJ_YEAR AND a.SBJ_SEM = e.SBJ_SEM " + 
            "LEFT JOIN STU_CLUB_MEMBER_CADRE f ON b.RGNO = f.RGNO " + 
            "LEFT JOIN common.STU_CLUB_CADRE h ON f.CADRE_NUM = h.CADRE_NUM " + 
            "WHERE c.STS_CODE = '0' AND e.CLS_CODE = ? AND a.SBJ_YEAR = ? AND a.SBJ_SEM = ? " + 
            "GROUP BY g.CLUB_NAME,b.SBJ_YEAR,b.SBJ_SEM,b.RGNO,d.CNAME,e.CLS_CNAME,d.SEX,h.CADRE_NAME " + 
            "ORDER BY b.RGNO ";

	public List<StuClass> getStuClassesByYearAndSemAndGrade(Connection conn, Integer sbj_year, Character sbj_sem,
			Character grade) {
		List<StuClass> result = new ArrayList<StuClass>();
        PreparedStatement ps = null;
        ResultSet rset = null;
        
        try {
            ps = conn.prepareStatement(SELECT_BY_YEAR_AND_SEM + WHERE_GRADE);
            ps.setInt(1, sbj_year);
            ps.setString(2, sbj_sem.toString());
            ps.setString(3, grade.toString());
            rset = ps.executeQuery();
            while (rset.next()) { 
            	StuClass stuclass = new StuClass();
            	stuclass.setCls_code(rset.getString("cls_code"));
            	stuclass.setSbj_year(rset.getInt("sbj_year"));
            	stuclass.setSbj_sem(StringUtils.toCharacter(rset.getString("sbj_sem")));
            	stuclass.setCls_cname(rset.getString("cls_cname"));
            	stuclass.setCls_seq(StringUtils.toCharacter(rset.getString("cls_seq")));
            	stuclass.setDiv_code(rset.getString("div_code"));
            	stuclass.setEdu_dep_code(rset.getString("edu_dep_code"));
            	stuclass.setGrade(StringUtils.toCharacter(rset.getString("grade")));
            	stuclass.setMat_code(StringUtils.toCharacter(rset.getString("mat_code")));
            	stuclass.setUse_flag(StringUtils.toCharacter(rset.getString("use_flag")));
                result.add(stuclass);
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
	public List<StuClass> getStuClassByYearAndSem(Connection conn, Integer sbj_year, Character sbj_sem){
		List<StuClass> result = new ArrayList<StuClass>();
		PreparedStatement ps = null;
		ResultSet rst = null;
		
		try {
			ps = conn.prepareStatement(SELECT_BY_YEAR_AND_SEM);
			ps.setInt(1, sbj_year);
			ps.setString(2, sbj_sem.toString());
			rst = ps.executeQuery();
			while (rst.next()) {
				StuClass stuclass  = new StuClass();
				stuclass.setCls_code(rst.getString("cls_code"));
            	stuclass.setSbj_year(rst.getInt("sbj_year"));
            	stuclass.setSbj_sem(StringUtils.toCharacter(rst.getString("sbj_sem")));
            	stuclass.setCls_cname(rst.getString("cls_cname"));
            	stuclass.setCls_seq(StringUtils.toCharacter(rst.getString("cls_seq")));
            	stuclass.setDiv_code(rst.getString("div_code"));
            	stuclass.setEdu_dep_code(rst.getString("edu_dep_code"));
            	stuclass.setGrade(StringUtils.toCharacter(rst.getString("grade")));
            	stuclass.setMat_code(StringUtils.toCharacter(rst.getString("mat_code")));
            	stuclass.setUse_flag(StringUtils.toCharacter(rst.getString("use_flag")));
                result.add(stuclass);		
			}
		}catch(SQLException sqlEX){
			logger.error(sqlEX);
			sqlEX.printStackTrace();
		} finally {
			if(rst != null) {
				try {
					rst.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					logger.error(e);
					e.printStackTrace();
				}
			}
			if(ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					logger.error(e);
				}
			}
			
		}
		return result;
	}

	public StuClass getStuClassByYearAndSemAndClsCode(Connection conn, Integer sbj_year, Character sbj_sem,
			String cls_code) {
		StuClass result = null;
        PreparedStatement ps = null;
        ResultSet rset = null;
        
        try {
            ps = conn.prepareStatement(SELECT_BY_YEAR_AND_SEM + WHERE_ClsCode);
            ps.setInt(1, sbj_year);
            ps.setString(2, sbj_sem.toString());
            ps.setString(3, cls_code);
            rset = ps.executeQuery();
            if (rset.next()) {
                result = new StuClass();
                result.setCls_code(rset.getString("cls_code"));
                result.setSbj_year(rset.getInt("sbj_year"));
                result.setSbj_sem(StringUtils.toCharacter(rset.getString("sbj_sem")));
                result.setCls_cname(rset.getString("cls_cname"));
                result.setCls_seq(StringUtils.toCharacter(rset.getString("cls_seq")));
            	result.setDiv_code(rset.getString("div_code"));
            	result.setEdu_dep_code(rset.getString("edu_dep_code"));
            	result.setGrade(StringUtils.toCharacter(rset.getString("grade")));
            	result.setMat_code(StringUtils.toCharacter(rset.getString("mat_code")));
            	result.setUse_flag(StringUtils.toCharacter(rset.getString("use_flag")));
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
    public List<StuClass> getClassClubByYearAndSemAndClsCode(Connection conn, Integer sbj_year, Character sbj_sem,
            String cls_code) {
        List<StuClass> result = new ArrayList<StuClass>();
        PreparedStatement ps = null;
        ResultSet rset = null;      
        try {
            ps = conn.prepareStatement(SELECT_CLASS_CLUB_BY_YEAR_AND_SEM_AND_CLSCODE);
            logger.debug("getClassClubByYearAndSemAndClsCode sbj_year : "+ sbj_year);
            logger.debug("getClassClubByYearAndSemAndClsCode sbj_sem : "+ sbj_sem.toString());
            logger.debug("getClassClubByYearAndSemAndClsCode cls_code : "+ cls_code);
            ps.setString(1, cls_code);
            ps.setInt(2, sbj_year);
            ps.setString(3, sbj_sem.toString());            
            rset = ps.executeQuery();
            while (rset.next()) {
                StuClass stuclass = new StuClass();
                stuclass.setSbj_year(rset.getInt("sbj_year"));
                stuclass.setSbj_sem(StringUtils.toCharacter(rset.getString("sbj_sem")));
                stuclass.setCls_cname(rset.getString("cls_cname"));
                
                StuBasis stuBasis = new StuBasis();
                stuBasis.setRgno(rset.getInt("rgno"));
                stuBasis.setCname(rset.getString("cname"));
                stuBasis.setSex(rset.getByte("sex"));
                
                Club club = new Club();
                club.setClub_name(rset.getString("club_name"));
                
                ClubCadre clubCadre = new ClubCadre();
                clubCadre.setCadre_name(rset.getString("cadre_name"));
                
                stuclass.setStuBasis(stuBasis);
                stuclass.setClub(club);
                stuclass.setClubCadre(clubCadre);
                result.add(stuclass);
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
