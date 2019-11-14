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
import ntpc.ccai.clubmgt.bean.ClubRecord;
import ntpc.ccai.clubmgt.bean.Staff;
import ntpc.ccai.clubmgt.bean.StuBasis;
import ntpc.ccai.clubmgt.bean.StuClass;
import ntpc.ccai.clubmgt.bean.StuRegister;
import ntpc.ccai.clubmgt.dao.ClubRecordDao;
import ntpc.ccai.clubmgt.util.StringUtils;

public class ClubRecordDaoImpl implements ClubRecordDao {
    private static final String INSERT = "INSERT INTO stu_club_record(club_num, sbj_year, sbj_sem, rgno, cr_sdate, cr_edate, cr_detail, cr_hours, staff_code, cr_time, level, cadre_name) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE stu_club_record SET club_num = ?, sbj_year = ?, sbj_sem = ?, rgno = ?, cr_sdate = ?, cr_edate = ?, cr_detail = ?, cr_hours = ?, staff_code = ?, cr_time = ? WHERE cr_num = ?";
    private static final String DELETE = "DELETE FROM stu_club_record WHERE cr_num = ?";
    private static final String SELECT_BY_CLUB_NUM_AND_YEAR_AND_SEM = "SELECT * FROM stu_club_record a JOIN stu_register b ON a.sbj_year = b.sbj_year AND a.sbj_sem = b.sbj_sem AND a.rgno = b.rgno "
                                                                    + "JOIN stu_basis c ON b.rgno = c.rgno JOIN stu_class d ON a.sbj_year = d.sbj_year AND a.sbj_sem = d.sbj_sem AND b.cls_code = d.cls_code "
                                                                    + "JOIN staff e ON a.staff_code = e.staff_code "
                                                                    + "WHERE a.club_num = ? AND a.sbj_year = ? AND a.sbj_sem = ?";
    private static final String DELETE_BY_CLUB_NUM_AND_YEAR_AND_SEM = "DELETE FROM stu_club_record WHERE club_num = ? AND sbj_year = ? AND sbj_sem = ?";
    
    // need to rewrite
    private static final String SELECT_CLUB_RECORD_BY_REGNO_RO_IDNO = "SELECT a.RGNO,b.CNAME,f.CLS_CNAME,b.BIRTHDAY,b.REG_NO,b.CMAT_YEAR,b.SEX,a.SBJ_YEAR,a.SBJ_SEM,d.CLUB_NAME,a.CR_SDATE,a.CR_EDATE,a.CR_DETAIL,a.CR_HOURS,e.STAFF_CNAME,a.CR_TIME,a.CR_NUM FROM STU_CLUB_RECORD a " + 
            "JOIN STU_BASIS b ON a.RGNO = b.RGNO " + 
            "JOIN STU_REGISTER c ON b.RGNO = c.RGNO AND a.SBJ_YEAR = c.SBJ_YEAR AND a.SBJ_SEM = c.SBJ_SEM " + 
            "JOIN STU_CLUB d ON a.CLUB_NUM = d.CLUB_NUM " + 
            "JOIN STAFF e ON a.STAFF_CODE = e.STAFF_CODE " + 
            "JOIN STU_CLASS f ON c.CLS_CODE = f.CLS_CODE " + 
            "WHERE b.REG_NO = ? OR b.IDNO = ? " + 
            "GROUP BY a.RGNO,b.CNAME,f.CLS_CNAME,b.BIRTHDAY,b.REG_NO,b.CMAT_YEAR,b.SEX,a.SBJ_YEAR,a.SBJ_SEM,d.CLUB_NAME,a.CR_SDATE,a.CR_EDATE,a.CR_DETAIL,a.CR_HOURS,e.STAFF_CNAME,a.CR_TIME,a.CR_NUM " + 
            "ORDER BY a.CR_NUM ";
    private static final Logger logger = Logger.getLogger(ClubRecordDaoImpl.class);
    
    @Override
    public ClubRecord insert(Connection conn, ClubRecord clubRecord) {
        PreparedStatement ps = null;
        ResultSet rset = null;
        
        try {
            ps = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            int i = 1;
            ps.setInt(i++, clubRecord.getClub_num());
            ps.setInt(i++, clubRecord.getSbj_year());
            ps.setString(i++, clubRecord.getSbj_sem().toString());
            ps.setInt(i++, clubRecord.getRgno());
            ps.setDate(i++, new java.sql.Date(clubRecord.getCr_sdate().getTime()));
            ps.setDate(i++, new java.sql.Date(clubRecord.getCr_edate().getTime()));
            ps.setString(i++, clubRecord.getCr_detail());
            ps.setInt(i++, clubRecord.getCr_hours());
            ps.setString(i++, clubRecord.getStaff_code());
            ps.setTimestamp(i++, new java.sql.Timestamp(clubRecord.getCr_time().getTime()));
            ps.setString(i++, clubRecord.getLevel());
            ps.setString(i++, clubRecord.getCr_cadre());
            int j = ps.executeUpdate();
            if (j == 1) {
                rset = ps.getGeneratedKeys();
                if (rset.next()) {
                    clubRecord.setCr_num(rset.getInt(1));
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
        return clubRecord;
    }

    @Override
    public Boolean update(Connection conn, ClubRecord clubRecord) {
        Boolean result = false;
        PreparedStatement ps = null;
        
        try {
            ps = conn.prepareStatement(UPDATE);
            int i = 1;
            ps.setInt(i++, clubRecord.getClub_num());
            ps.setInt(i++, clubRecord.getSbj_year());
            ps.setString(i++, clubRecord.getSbj_sem().toString());
            ps.setInt(i++, clubRecord.getRgno());
            ps.setDate(i++, new java.sql.Date(clubRecord.getCr_sdate().getTime()));
            ps.setDate(i++, new java.sql.Date(clubRecord.getCr_edate().getTime()));
            ps.setString(i++, clubRecord.getCr_detail());
            ps.setInt(i++, clubRecord.getCr_hours());
            ps.setString(i++, clubRecord.getStaff_code());
            ps.setTimestamp(i++, new java.sql.Timestamp(clubRecord.getCr_time().getTime()));
            ps.setInt(i++, clubRecord.getCr_num());
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
    public Boolean delete(Connection conn, Integer cr_num) {
        Boolean result = false;
        PreparedStatement ps = null;
        
        try {
            ps = conn.prepareStatement(DELETE);
            ps.setInt(1, cr_num);
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
    public List<ClubRecord> getClubRecordsByClubNumAndYearAndSem(Connection conn, Integer club_num, Integer sbj_year,
            Character sbj_sem) {
        List<ClubRecord> result = new ArrayList<ClubRecord>();
        PreparedStatement ps = null;
        ResultSet rset = null;
        
        try {
            ps = conn.prepareStatement(SELECT_BY_CLUB_NUM_AND_YEAR_AND_SEM);
            int i = 1;
            ps.setInt(i++, club_num);
            ps.setInt(i++, sbj_year);
            ps.setString(i++, sbj_sem.toString());
            rset = ps.executeQuery();
            while (rset.next()) {
                ClubRecord clubRecord = new ClubRecord();
                clubRecord.setCr_num(rset.getInt("cr_num"));
                clubRecord.setClub_num(rset.getInt("club_num"));
                clubRecord.setSbj_year(rset.getInt("sbj_year"));
                clubRecord.setSbj_sem(StringUtils.toCharacter(rset.getString("sbj_sem")));
                clubRecord.setRgno(rset.getInt("rgno"));
                clubRecord.setCr_sdate(rset.getDate("cr_sdate"));
                clubRecord.setCr_edate(rset.getDate("cr_edate"));
                clubRecord.setCr_detail(rset.getString("cr_detail"));
                clubRecord.setCr_hours(rset.getInt("cr_hours"));
                clubRecord.setStaff_code(rset.getString("staff_code"));
                clubRecord.setCr_time(rset.getTimestamp("cr_time"));
                clubRecord.setCr_cadre(rset.getString("cadre_name"));
                
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
                
                clubRecord.setStaff(staff);
                
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
                
                clubRecord.setStuRegister(stuRegister);
                
                result.add(clubRecord);
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

    // need to rewrite
    
    @Override
    public List<ClubRecord> getClubRecordsByRegnoAndIdno(Connection conn, String reg_no, String idno) {
        List<ClubRecord> result = new ArrayList<ClubRecord>();
        PreparedStatement ps = null;
        ResultSet rset = null;
        
        try {
            ps = conn.prepareStatement(SELECT_CLUB_RECORD_BY_REGNO_RO_IDNO);
            int i = 1;
            ps.setString(i++, reg_no);
            ps.setString(i++, idno);
            rset = ps.executeQuery();
            while (rset.next()) {
                ClubRecord clubRecord = new ClubRecord();
                clubRecord.setSbj_year(rset.getInt("sbj_year"));
                clubRecord.setSbj_sem(StringUtils.toCharacter(rset.getString("sbj_sem")));
                clubRecord.setCr_sdate(rset.getDate("cr_sdate"));
                clubRecord.setCr_edate(rset.getDate("cr_edate"));
                clubRecord.setCr_detail(rset.getString("cr_detail"));
                clubRecord.setCr_hours(rset.getInt("cr_hours"));
                clubRecord.setCr_time(rset.getTimestamp("cr_time"));
                clubRecord.setRgno(rset.getInt("rgno"));
                
                Staff staff = new Staff();

                staff.setStaff_cname(rset.getString("staff_cname"));         
                clubRecord.setStaff(staff);
                
                StuRegister stuRegister= new StuRegister();                           
                StuClass stuClass= new StuClass();
                
                stuClass.setCls_cname(rset.getString("cls_cname"));
                stuRegister.setStuClass(stuClass);
                
                StuBasis stuBasis = new StuBasis();

                stuBasis.setReg_no(rset.getString("reg_no"));
                stuBasis.setCname(rset.getString("cname"));
                stuBasis.setSex(rset.getByte("sex"));
                stuBasis.setBirthday(rset.getDate("birthday"));
                stuBasis.setCmat_year(rset.getShort("cmat_year"));                            
                stuRegister.setStuBasis(stuBasis);              
                clubRecord.setStuRegister(stuRegister);
                
                Club club = new Club();
                club.setClub_name(rset.getString("club_name"));
                clubRecord.setClub(club);
                
                result.add(clubRecord);
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
