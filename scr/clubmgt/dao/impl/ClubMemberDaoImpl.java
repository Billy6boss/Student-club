package ntpc.ccai.clubmgt.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import ntpc.ccai.clubmgt.bean.Club;
import ntpc.ccai.clubmgt.bean.ClubCadre;
import ntpc.ccai.clubmgt.bean.ClubMember;
import ntpc.ccai.clubmgt.bean.ClubMemberCadre;
import ntpc.ccai.clubmgt.bean.StuBasis;
import ntpc.ccai.clubmgt.bean.StuClass;
import ntpc.ccai.clubmgt.bean.StuRegister;
import ntpc.ccai.clubmgt.dao.ClubMemberDao;
import ntpc.ccai.clubmgt.util.StringUtils;

public class ClubMemberDaoImpl implements ClubMemberDao {
	private static final Logger logger = Logger.getLogger(ClubMemberDaoImpl.class);
    private static final String INSERT = "INSERT INTO stu_club_member(club_num, sbj_year, sbj_sem, rgno, club_score) VALUES (?, ?, ?, ?, ?)";
    private static final String DELETE = "DELETE FROM stu_club_member WHERE club_num=? AND sbj_year=? AND sbj_sem=? AND rgno=?";
    private static final String SELECT_BY_CLUB_NUM_AND_YEAR_AND_SEM = "SELECT * FROM stu_club_member a "
                                                                    + "JOIN stu_register b ON a.sbj_year = b.sbj_year AND a.sbj_sem = b.sbj_sem AND a.rgno = b.rgno "
                                                                    + "JOIN stu_basis c ON b.rgno = c.rgno "
                                                                    + "JOIN stu_class d ON a.sbj_year = d.sbj_year AND a.sbj_sem = d.sbj_sem AND b.cls_code = d.cls_code "
                                                                    + "LEFT JOIN stu_club_member_cadre e ON a.club_num = e.club_num AND a.sbj_year = e.sbj_year AND a.sbj_sem = e.sbj_sem AND a.rgno = e.rgno "
                                                                    + "LEFT JOIN common.stu_club_cadre f ON e.cadre_num = f.cadre_num "
                                                                    + "WHERE a.club_num=? AND a.sbj_year=? AND a.sbj_sem=?";
    private static final String UPDATE ="UPDATE stu_club_member SET club_score=? WHERE club_num=? AND sbj_year=? AND sbj_sem=? AND rgno=?";
    private static final String DELETE_BY_CLUB_NUM_AND_YEAR_AND_SEM = "DELETE FROM stu_club_member WHERE club_num = ? AND sbj_year = ? AND sbj_sem = ?";
    private static final String COPY = "INSERT INTO stu_club_member(club_num, sbj_year, sbj_sem, rgno, club_score) SELECT club_num, ?, ?, rgno, club_score FROM stu_club_member WHERE sbj_year = ? AND sbj_sem = ?";
    
    // need to rewrite
  //查詢社團成員
    private static final String SELECT_CLUB_MEMBER_DATA_BY_NUM_AND_YEAR_AND_SEM = "SELECT g.CLUB_NAME,b.SBJ_YEAR,b.SBJ_SEM,b.RGNO,d.CNAME,e.CLS_CNAME,d.COM_ROAD,d.COM_TELNO,d.SEX,d.BIRTHDAY,d.EMAIL,h.CADRE_NAME,c.CLS_NO FROM STU_CLUB_SEM a " + 
                                                                                  "JOIN STU_CLUB g ON a.CLUB_NUM = g.CLUB_NUM " + 
                                                                                  "JOIN STU_CLUB_MEMBER b ON a.CLUB_NUM = b.CLUB_NUM AND a.SBJ_YEAR = b.SBJ_YEAR AND a.SBJ_SEM = b.SBJ_SEM " + 
                                                                                  "JOIN STU_REGISTER c ON b.RGNO = c.RGNO AND a.SBJ_YEAR = c.SBJ_YEAR AND a.SBJ_SEM = c.SBJ_SEM " + 
                                                                                  "JOIN STU_BASIS d ON c.RGNO = d.RGNO " + 
                                                                                  "JOIN STU_CLASS e ON c.CLS_CODE = e.CLS_CODE AND a.SBJ_YEAR = e.SBJ_YEAR AND a.SBJ_SEM = e.SBJ_SEM " + 
                                                                                  "LEFT JOIN STU_CLUB_MEMBER_CADRE f ON b.RGNO = f.RGNO " + 
                                                                                  "LEFT JOIN common.STU_CLUB_CADRE h ON f.CADRE_NUM = h.CADRE_NUM " +
                                                                                  "WHERE c.STS_CODE = '0' AND a.CLUB_NUM = ? AND a.SBJ_YEAR = ? AND a.SBJ_SEM = ? " + 
                                                                                  "GROUP BY g.CLUB_NAME,b.SBJ_YEAR,b.SBJ_SEM,b.RGNO,d.CNAME,e.CLS_CNAME,d.COM_ROAD,d.COM_TELNO,d.SEX,d.BIRTHDAY,d.EMAIL,h.CADRE_NAME,c.CLS_NO ";
    //查詢社團幹部成員
    private static final String SELECT_CLUB_CADRE_DATA_BY_NUM_AND_YEAR_AND_SEM = "SELECT g.CLUB_NAME,b.SBJ_YEAR,b.SBJ_SEM,b.RGNO,d.CNAME,e.CLS_CNAME,d.COM_ROAD,d.COM_TELNO,d.SEX,d.BIRTHDAY,d.EMAIL,h.CADRE_NAME,c.CLS_NO FROM STU_CLUB_SEM a " + 
                                                                                 "JOIN STU_CLUB g ON a.CLUB_NUM = g.CLUB_NUM " + 
                                                                                 "JOIN STU_CLUB_MEMBER b ON a.CLUB_NUM = b.CLUB_NUM and a.sbj_year = b.sbj_year and a.SBJ_SEM = b.sbj_sem " + 
                                                                                 "JOIN STU_REGISTER c ON b.RGNO = c.RGNO and b.sbj_year = c.sbj_year and b.sbj_sem = c.sbj_sem " + 
                                                                                 "JOIN STU_BASIS d ON c.RGNO = d.RGNO " + 
                                                                                 "JOIN STU_CLASS e ON c.CLS_CODE = e.CLS_CODE  and c.sbj_year = e.sbj_year and c.sbj_sem = e.sbj_sem " + 
                                                                                 "JOIN STU_CLUB_MEMBER_CADRE f ON b.CLUB_NUM = f.CLUB_NUM AND b.RGNO = f.RGNO  and b.SBJ_YEAR = f.SBJ_YEAR and b.sbj_sem = f.sbj_sem " + 
                                                                                 "JOIN common.STU_CLUB_CADRE h ON f.CADRE_NUM = h.CADRE_NUM " +
                                                                                 "WHERE c.STS_CODE = '0' AND a.CLUB_NUM = ? AND a.SBJ_YEAR = ? AND a.SBJ_SEM = ? " + 
                                                                                 "GROUP BY g.CLUB_NAME,b.SBJ_YEAR,b.SBJ_SEM,b.RGNO,d.CNAME,e.CLS_CNAME,d.COM_ROAD,d.COM_TELNO,d.SEX,d.BIRTHDAY,d.EMAIL,h.CADRE_NAME,c.CLS_NO ";
    //查詢社團成員成績
    private static final String SELECT_MEMBER_SCORE_BY_CLUB_NUM_AND_YEAR_AND_SEM = "SELECT e.CLS_CNAME,c.RGNO,d.CNAME,d.SEX,h.CADRE_NAME,b.CLUB_SCORE,g.CLUB_NAME FROM STU_CLUB_SEM a " + 
                                                                                   "JOIN STU_CLUB g ON a.CLUB_NUM = g.CLUB_NUM " + 
                                                                                   "JOIN STU_CLUB_MEMBER b ON a.CLUB_NUM = b.CLUB_NUM AND a.SBJ_YEAR = b.SBJ_YEAR AND a.SBJ_SEM = b.SBJ_SEM " + 
                                                                                   "JOIN STU_REGISTER c ON b.RGNO = c.RGNO AND a.SBJ_YEAR = c.SBJ_YEAR AND a.SBJ_SEM = c.SBJ_SEM " + 
                                                                                   "JOIN STU_BASIS d ON c.RGNO = d.RGNO " + 
                                                                                   "JOIN STU_CLASS e ON c.CLS_CODE = e.CLS_CODE AND a.SBJ_YEAR = e.SBJ_YEAR AND a.SBJ_SEM = e.SBJ_SEM " + 
                                                                                   "LEFT JOIN STU_CLUB_MEMBER_CADRE f ON b.RGNO = f.RGNO " + 
                                                                                   "LEFT JOIN common.STU_CLUB_CADRE h ON f.CADRE_NUM = h.CADRE_NUM " + 
                                                                                   "WHERE c.STS_CODE = '0' AND a.CLUB_NUM = ? AND a.SBJ_YEAR = ? AND a.SBJ_SEM = ? " + 
                                                                                   "GROUP BY e.CLS_CNAME,c.RGNO,d.CNAME,d.SEX,h.CADRE_NAME,b.CLUB_SCORE,g.CLUB_NAME " + 
                                                                                   "ORDER BY c.RGNO ";    
    private static final String SELECT_FONT_NUM_BY_CLUB_NUM_AND_YEAR_AND_SEM_AND_RGNO = "SELECT * FROM STU_CLUB_MEMBER a WHERE a.CLUB_NUM = ? AND a.SBJ_YEAR = ? AND a.SBJ_SEM = ?  AND a.RGNO = ? AND a.ISSUE_CODE IS NULL ORDER BY a.RGNO";
    //確認社團證明字號 及更新
    private static final String CHKDATE_ISSUE_CODE = "SELECT * FROM STU_CLUB_MEMBER a WHERE a.CLUB_NUM = ? AND a.SBJ_YEAR = ? AND a.SBJ_SEM = ? AND a.ISSUE_CODE = ? ORDER BY a.RGNO";   
    private static final String UPDATE_ISSUE_CODE = "UPDATE stu_club_member SET issue_code=? WHERE club_num=? AND sbj_year=? AND sbj_sem=? AND rgno=?";
    //確認社團績優字號 及更新
    private static final String CHKDATE_EX_CODE = "SELECT * FROM STU_CLUB_MEMBER a WHERE a.CLUB_NUM = ? AND a.SBJ_YEAR = ? AND a.SBJ_SEM = ? AND a.EX_CODE = ? ORDER BY a.RGNO";   
    private static final String UPDATE_EX_CODE = "UPDATE stu_club_member SET ex_code=? WHERE club_num=? AND sbj_year=? AND sbj_sem=? AND rgno=?";

	@Override
	public Boolean insert(Connection conn, ClubMember clubMember) {
		Boolean result = false;
        PreparedStatement ps = null;
        
        try {
            ps = conn.prepareStatement(INSERT);
            int i = 1;
            Integer score = clubMember.getClub_score();
            
            ps.setInt(i++, clubMember.getClub_num());
            ps.setInt(i++, clubMember.getSbj_year());
            ps.setString(i++, clubMember.getSbj_sem().toString());
            ps.setInt(i++, clubMember.getRgno());
            ps.setInt(i++, score == null ? 0 : score);
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
	public Boolean update(Connection conn, ClubMember clubMember) {
		Boolean result = false;
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(UPDATE);
            int i = 1;
            ps.setInt(i++, clubMember.getClub_score());
            ps.setInt(i++, clubMember.getClub_num());
            ps.setInt(i++, clubMember.getSbj_year());
            ps.setString(i++, clubMember.getSbj_sem().toString());
            ps.setInt(i++, clubMember.getRgno());
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
	public Boolean delete(Connection conn, Integer club_num, Integer sbj_year, Character sbj_sem, Integer rgno) {
		Boolean result = false;
        PreparedStatement ps = null;
        
        try {
            ps = conn.prepareStatement(DELETE);
            int i = 1;
            ps.setInt(i++, club_num);
            ps.setInt(i++, sbj_year);
            ps.setString(i++, sbj_sem.toString());
            ps.setInt(i++, rgno);
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
	public List<ClubMember> getClubMembersByClubNumAndYearAndSem(Connection conn, Integer club_num, Integer sbj_year,
			Character sbj_sem) {
		List<ClubMember> result = new ArrayList<ClubMember>();
		Map<Integer, ClubMember> map = new HashMap<Integer, ClubMember>();
        PreparedStatement ps = null;
        ResultSet rset = null;
        
        try {
            ps = conn.prepareStatement(SELECT_BY_CLUB_NUM_AND_YEAR_AND_SEM);
            ps.setInt(1, club_num);
            ps.setInt(2, sbj_year);
            ps.setString(3, sbj_sem.toString());
            rset = ps.executeQuery();
            while (rset.next()) {
            	ClubMember clubMember = new ClubMember();
            	clubMember.setClub_num(rset.getInt("club_num"));
            	clubMember.setSbj_year(rset.getInt("sbj_year"));
            	clubMember.setSbj_sem(StringUtils.toCharacter(rset.getString("sbj_sem")));
            	clubMember.setRgno(rset.getInt("rgno"));
            	clubMember.setClub_score(rset.getInt("club_score"));
            	
            	StuRegister stuRegister= new StuRegister();
                
            	stuRegister.setReg_num(rset.getInt("reg_num"));
            	stuRegister.setSbj_year(rset.getInt("sbj_year"));
            	stuRegister.setSbj_sem(StringUtils.toCharacter(rset.getString("sbj_sem")));
            	stuRegister.setRgno(rset.getInt("rgno"));
                stuRegister.setCls_code(rset.getString("cls_code"));
                stuRegister.setCls_no(rset.getString("cls_no"));
                stuRegister.setSts_code(StringUtils.toCharacter(rset.getString("sts_code")));
                stuRegister.setReg_flag(StringUtils.toCharacter(rset.getString("reg_flag")));
                stuRegister.setSpd_num(rset.getInt("spd_num"));
                stuRegister.setReenroll(StringUtils.toCharacter(rset.getString("reenroll")));
                stuRegister.setRepeat(StringUtils.toCharacter(rset.getString("repeat")));
                stuRegister.setDiv_code(rset.getString("div_code"));
                stuRegister.setSync_disable(StringUtils.toCharacter(rset.getString("sync_disable")));
                
                StuClass stuClass= new StuClass();
                stuClass.setSbj_year(rset.getInt("sbj_year"));
                stuClass.setSbj_sem(StringUtils.toCharacter(rset.getString("sbj_sem")));
                stuClass.setCls_code(rset.getString("cls_code"));
                stuClass.setCls_cname(rset.getString("cls_cname"));
                stuClass.setMat_code(StringUtils.toCharacter(rset.getString("mat_code")));
                stuClass.setDiv_code(rset.getString("div_code"));
                stuClass.setEdu_dep_code(rset.getString("edu_dep_code"));
                stuClass.setGrade(StringUtils.toCharacter(rset.getString("grade")));
                stuClass.setCls_seq(StringUtils.toCharacter(rset.getString("cls_seq")));
                stuClass.setUse_flag(StringUtils.toCharacter(rset.getString("use_flag")));
                stuRegister.setStuClass(stuClass);
                
                StuBasis stuBasis = new StuBasis();
                stuBasis.setRgno(rset.getInt("rgno"));
                stuBasis.setReg_no(rset.getString("reg_no"));
                stuBasis.setCname(rset.getString("cname"));
                stuBasis.setEname(rset.getString("ename"));
                stuBasis.setSex(rset.getByte("sex"));
                stuBasis.setNation(rset.getString("nation"));
                stuBasis.setBirthday(rset.getDate("birthday"));
                stuBasis.setBirthplace(rset.getString("birthplace"));
                stuBasis.setPassport(rset.getByte("passport"));
                stuBasis.setIdno(rset.getString("idno"));
                stuBasis.setOverseas(rset.getString("overseas"));
                stuBasis.setCmat_year(rset.getShort("cmat_year"));
                stuBasis.setMat_code(StringUtils.toCharacter(rset.getString("mat_code")));
                stuBasis.setDiv_code(rset.getString("div_code"));
                stuBasis.setOlddiv_code(rset.getString("olddiv_code"));
                stuBasis.setSts_code(rset.getString("sts_code"));
                stuBasis.setStpmod(StringUtils.toCharacter(rset.getString("stpmod")));
                stuBasis.setPer_city_code(rset.getString("per_city_code"));
                stuBasis.setPer_town_num(rset.getString("per_town_num"));
                stuBasis.setPer_village_num(rset.getString("per_village_num"));
                stuBasis.setPer_neighbor(rset.getShort("per_neighbor"));
                stuBasis.setPer_road(rset.getString("per_road"));
                stuBasis.setPer_telno(rset.getString("per_telno"));
                stuBasis.setCom_city_code(rset.getString("com_city_code"));
                stuBasis.setCom_town_num(rset.getString("com_town_num"));
                stuBasis.setCom_village_num(rset.getString("com_village_num"));
                stuBasis.setCom_neighbor(rset.getShort("com_neighbor"));
                stuBasis.setCom_road(rset.getString("com_road"));
                stuBasis.setCom_telno(rset.getString("com_telno"));
                stuBasis.setEmail(rset.getString("email"));
                stuBasis.setMobile_telno(rset.getString("mobile_telno"));
                stuBasis.setBlood_type(rset.getString("blood_type"));
                stuBasis.setPlace(StringUtils.toCharacter(rset.getString("place")));
                stuBasis.setNature(rset.getString("nature"));
                stuBasis.setSpecid(rset.getInt("specid"));
                stuBasis.setY1_pass(rset.getBigDecimal("y1_pass"));
                stuBasis.setY2_pass(rset.getBigDecimal("y2_pass"));
                stuBasis.setY3_pass(rset.getBigDecimal("y3_pass"));
                stuBasis.setY4_pass(rset.getBigDecimal("y4_pass"));
                stuBasis.setY5_pass(rset.getBigDecimal("y5_pass"));
                stuBasis.setY1_pu(rset.getBigDecimal("y1_pu"));
                stuBasis.setY2_pu(rset.getBigDecimal("y2_pu"));
                stuBasis.setY3_pu(rset.getBigDecimal("y3_pu"));
                stuBasis.setY4_pu(rset.getBigDecimal("y4_pu"));
                stuBasis.setY5_pu(rset.getBigDecimal("y5_pu"));
                stuBasis.setMg_school(rset.getString("mg_school"));
                stuBasis.setGrd_cname(rset.getString("grd_cname"));
                stuBasis.setGrd_relation(rset.getString("grd_relation"));
                stuBasis.setGrd_address(rset.getString("grd_address"));
                stuBasis.setGrd_telno_h(rset.getString("grd_telno_h"));
                stuBasis.setGrd_telno_o(rset.getString("grd_telno_o"));
                stuBasis.setGrd_telno_m(rset.getString("grd_telno_m"));
                stuBasis.setCou_addr(rset.getString("cou_addr"));
                stuBasis.setCou_telno(rset.getString("cou_telno"));
                stuBasis.setCou_phone(rset.getString("cou_phone"));
                stuBasis.setSch_code(rset.getString("sch_code"));
                stuBasis.setAdmission_code(rset.getString("admission_code"));
                stuBasis.setEdu_degree(rset.getString("edu_degree"));
                stuBasis.setMg_code(rset.getString("mg_code"));
                stuBasis.setInspection_num(rset.getString("inspection_num"));
                stuBasis.setInspection_date(rset.getDate("inspection_date"));
                stuRegister.setStuBasis(stuBasis);
            	
            	clubMember.setStuRegister(stuRegister);
            	
            	Integer cadre_num = rset.getInt("cadre_num");
            	if (cadre_num != 0) {
            	    ClubMemberCadre clubMemberCadre = new ClubMemberCadre();
                    clubMemberCadre.setClub_num(rset.getInt("club_num"));
                    clubMemberCadre.setSbj_year(rset.getInt("sbj_year"));
                    clubMemberCadre.setSbj_sem(StringUtils.toCharacter(rset.getString("sbj_sem")));
                    clubMemberCadre.setRgno(rset.getInt("rgno"));
                    clubMemberCadre.setCadre_num(rset.getInt("cadre_num"));

                    ClubCadre clubCadre = new ClubCadre();
                    clubCadre.setCadre_num(rset.getInt("cadre_num"));
                    clubCadre.setCadre_name(rset.getString("cadre_name"));
                    clubMemberCadre.setClubCadre(clubCadre);
                    
                    Integer rgno = clubMember.getRgno();
                    if (map.containsKey(rgno)) {
                        map.get(rgno).getClubMemberCadre().add(clubMemberCadre);
                    } else {
                        clubMember.getClubMemberCadre().add(clubMemberCadre);
                        map.put(rgno, clubMember);
                        result.add(clubMember);
                    }
            	} else {
            	    result.add(clubMember);
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
    
    /** 匯出社成員PDF
     * @param Pcon
     * @param club_num
     * @param sbj_year  
     * @param sbj_sem
     * @return List<ClubMember>
     * @throws Exception
     * @author tom
     */
    @Override
    public List<ClubMember> getClubMembersByPDFClubNumAndYearAndSem(Connection conn, Integer club_num, Integer sbj_year,
            Character sbj_sem) {
        List<ClubMember> result = new ArrayList<ClubMember>();
        PreparedStatement ps = null;
        ResultSet rset = null;
        
        try {
            ps = conn.prepareStatement(SELECT_CLUB_MEMBER_DATA_BY_NUM_AND_YEAR_AND_SEM);
            ps.setInt(1, club_num);
            ps.setInt(2, sbj_year);
            ps.setString(3, sbj_sem.toString());
            rset = ps.executeQuery();
            while (rset.next()) {                                       
                ClubMember clubMember = new ClubMember();
                clubMember.setSbj_year(rset.getInt("sbj_year"));
                clubMember.setSbj_sem(StringUtils.toCharacter(rset.getString("sbj_sem")));
                clubMember.setRgno(rset.getInt("rgno"));
                
                Club club = new Club();
                club.setClub_name(rset.getString("club_name")); 
                
                StuBasis stuBasis = new StuBasis();
                stuBasis.setCname(rset.getString("cname"));
                stuBasis.setCou_addr(rset.getString("com_road"));
                stuBasis.setCom_telno(rset.getString("com_telno"));
                stuBasis.setSex(rset.getByte("sex"));
                stuBasis.setBirthday(rset.getDate("birthday"));
                stuBasis.setEmail(rset.getString("email"));
                                                    
                ClubCadre clubCadre = new ClubCadre();
                clubCadre.setCadre_name(rset.getString("cadre_name"));

                StuClass stuClass = new StuClass();
                stuClass.setCls_cname(rset.getString("cls_cname"));
                
                StuRegister stuRegister = new StuRegister();
                stuRegister.setStuClass(stuClass);
                stuRegister.setStuBasis(stuBasis);
                stuRegister.setCls_no(rset.getString("cls_no"));
                
                clubMember.setClub(club);
                clubMember.setClubCadre(clubCadre);
                clubMember.setStuRegister(stuRegister);
                result.add(clubMember);
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
    /** 匯出社幹部成員PDF
     * @param Pcon
     * @param club_num
     * @param sbj_year  
     * @param sbj_sem
     * @return List<ClubMember>
     * @throws Exception
     * @author tom
     */
    @Override
    public List<ClubMember> getClubCadersByPDFClubNumAndYearAndSem(Connection conn, Integer club_num, Integer sbj_year,
            Character sbj_sem) {
        List<ClubMember> result = new ArrayList<ClubMember>();
        PreparedStatement ps = null;
        ResultSet rset = null;
        
        try {
            ps = conn.prepareStatement(SELECT_CLUB_CADRE_DATA_BY_NUM_AND_YEAR_AND_SEM);
            ps.setInt(1, club_num);
            ps.setInt(2, sbj_year);
            ps.setString(3, sbj_sem.toString());
            rset = ps.executeQuery();
            while (rset.next()) {                                       
                ClubMember clubMember = new ClubMember();
                clubMember.setSbj_year(rset.getInt("sbj_year"));
                clubMember.setSbj_sem(StringUtils.toCharacter(rset.getString("sbj_sem")));
                clubMember.setRgno(rset.getInt("rgno"));
                
                Club club = new Club();
                club.setClub_name(rset.getString("club_name")); 
                
                StuBasis stuBasis = new StuBasis();
                stuBasis.setCname(rset.getString("cname"));
                stuBasis.setCou_addr(rset.getString("com_road"));
                stuBasis.setCom_telno(rset.getString("com_telno"));
                stuBasis.setSex(rset.getByte("sex"));
                stuBasis.setBirthday(rset.getDate("birthday"));
                stuBasis.setEmail(rset.getString("email"));
                                                    
                ClubCadre clubCadre = new ClubCadre();
                clubCadre.setCadre_name(rset.getString("cadre_name"));

                StuClass stuClass = new StuClass();
                stuClass.setCls_cname(rset.getString("cls_cname"));
                
                StuRegister stuRegister = new StuRegister();
                stuRegister.setStuClass(stuClass);
                stuRegister.setStuBasis(stuBasis);
                stuRegister.setCls_no(rset.getString("cls_no"));
                
                clubMember.setClub(club);
                clubMember.setClubCadre(clubCadre);
                clubMember.setStuRegister(stuRegister);
                result.add(clubMember);
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
    public List<ClubMember> getClubMemberScoreByClubNumAndYearAndSem(Connection conn, Integer club_num,
            Integer sbj_year, Character sbj_sem) {
        List<ClubMember> result = new ArrayList<ClubMember>();
        PreparedStatement ps = null;
        ResultSet rset = null;
        
        try {
            ps = conn.prepareStatement(SELECT_MEMBER_SCORE_BY_CLUB_NUM_AND_YEAR_AND_SEM);
            ps.setInt(1, club_num);
            ps.setInt(2, sbj_year);
            ps.setString(3, sbj_sem.toString());
            rset = ps.executeQuery();
            while (rset.next()) {                                       
                ClubMember clubMember = new ClubMember();
                clubMember.setRgno(rset.getInt("rgno"));
                clubMember.setClub_score(rset.getInt("club_score"));
                
                Club club = new Club();
                club.setClub_name(rset.getString("club_name")); 
                
                StuBasis stuBasis = new StuBasis();
                stuBasis.setCname(rset.getString("cname"));
                stuBasis.setSex(rset.getByte("sex")); 
                
                ClubCadre clubCadre = new ClubCadre();              
                clubCadre.setCadre_name(rset.getString("cadre_name"));

                StuClass stuClass = new StuClass();
                stuClass.setCls_cname(rset.getString("cls_cname"));
                
                StuRegister stuRegister = new StuRegister();
                stuRegister.setStuClass(stuClass);
                stuRegister.setStuBasis(stuBasis);
                
                clubMember.setClub(club);
                clubMember.setClubCadre(clubCadre);
                clubMember.setStuRegister(stuRegister);
                result.add(clubMember);
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
    public List<ClubMember> getClubMemberFontNumByClubNumAndYearAndSemAndRgno(Connection conn, Integer club_num,
            Integer sbj_year, Character sbj_sem, Integer rgno) {
        List<ClubMember> result = new ArrayList<ClubMember>();
        PreparedStatement ps = null;
        ResultSet rset = null;
        
        try {
            ps = conn.prepareStatement(SELECT_FONT_NUM_BY_CLUB_NUM_AND_YEAR_AND_SEM_AND_RGNO);
            ps.setInt(1, club_num);
            ps.setInt(2, sbj_year);
            ps.setString(3, sbj_sem.toString());
            ps.setInt(4, rgno);
            rset = ps.executeQuery();
            while (rset.next()) {                                       
                ClubMember clubMember = new ClubMember();
                clubMember.setRgno(rset.getInt("rgno"));
                clubMember.setClub_score(rset.getInt("club_score"));
                
                result.add(clubMember);
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
    public Boolean chkdateissue_code(Connection conn, String issue_code, Integer club_num, Integer sbj_year,
            Character sbj_sem, Integer rgno) {
            List<ClubMember> result = new ArrayList<ClubMember>();
            PreparedStatement ps = null;
            ResultSet rset = null;
            Boolean chkvalue = null;
            try {
                ps = conn.prepareStatement(CHKDATE_ISSUE_CODE);
                ps.setInt(1, club_num);
                ps.setInt(2, sbj_year);
                ps.setString(3, sbj_sem.toString());
                ps.setString(4, issue_code);
                rset = ps.executeQuery();
                while (rset.next()) {                                       
                    ClubMember clubMember = new ClubMember();
                    clubMember.setRgno(rset.getInt("rgno"));
                    clubMember.setClub_score(rset.getInt("club_score"));
                    
                    result.add(clubMember);
                }
                
                if(result.size() >0 ) {
                    chkvalue = false ;
                } else {
                    chkvalue = true ;
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
            return chkvalue;
    }
    
    @Override
    public Boolean updateissue_code(Connection conn,String issue_code , Integer club_num, Integer sbj_year, Character sbj_sem,
            Integer rgno) {
        Boolean result = null;
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(UPDATE_ISSUE_CODE);
            int i = 1;
            ps.setString(i++, issue_code);
            ps.setInt(i++, club_num);
            ps.setInt(i++, sbj_year);
            ps.setString(i++, sbj_sem.toString());
            ps.setInt(i++, rgno);
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
    public Boolean chkdateex_code(Connection conn, String ex_code, Integer club_num, Integer sbj_year,
            Character sbj_sem, Integer rgno) {
        List<ClubMember> result = new ArrayList<ClubMember>();
        PreparedStatement ps = null;
        ResultSet rset = null;
        Boolean chkvalue = null;
        try {
            ps = conn.prepareStatement(CHKDATE_EX_CODE);
            ps.setInt(1, club_num);
            ps.setInt(2, sbj_year);
            ps.setString(3, sbj_sem.toString());
            ps.setString(4, ex_code);
            rset = ps.executeQuery();
            while (rset.next()) {                                       
                ClubMember clubMember = new ClubMember();
                clubMember.setRgno(rset.getInt("rgno"));
                clubMember.setClub_score(rset.getInt("club_score"));
                
                result.add(clubMember);
            }
            
            if(result.size() >0 ) {
                chkvalue = false ;
            } else {
                chkvalue = true ;
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
        return chkvalue;
    }

    @Override
    public Boolean updateex_code(Connection conn, String ex_code, Integer club_num, Integer sbj_year, Character sbj_sem,
            Integer rgno) {
        Boolean result = null;
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(UPDATE_EX_CODE);
            int i = 1;
            ps.setString(i++, ex_code);
            ps.setInt(i++, club_num);
            ps.setInt(i++, sbj_year);
            ps.setString(i++, sbj_sem.toString());
            ps.setInt(i++, rgno);
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
