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

import ntpc.ccai.clubmgt.bean.StuBasis;
import ntpc.ccai.clubmgt.bean.StuClass;
import ntpc.ccai.clubmgt.bean.StuRegister;
import ntpc.ccai.clubmgt.dao.StuRegisterDao;
import ntpc.ccai.clubmgt.util.StringUtils;

public class StuRegisterDaoImpl implements StuRegisterDao {
	private static final Logger logger = Logger.getLogger(StuRegisterDaoImpl.class);	
	private static final String SELECT = "SELECT * FROM stu_register sr JOIN stu_basis sb ON sr.rgno = sb.rgno JOIN stu_class sc ON sc.sbj_year = sr.sbj_year AND sc.sbj_sem = sr.sbj_sem AND sc.cls_code = sr.cls_code ";
	private static final String SELECT_RGNO_BY_CLS_CODE_AND_CLS_NO = "SELECT rgno FROM stu_register sr WHERE sr.sbj_year = ? AND sr.sbj_sem = ? AND sr.cls_code = ? AND sr.cls_no = ? ";
	private static final String SELECT_BY_CLS_CODE_AND_CLS_NO = SELECT + " WHERE sr.sbj_year = ? AND sr.sbj_sem = ? AND sr.cls_code = ? AND sr.cls_no = ?";
	private static final String SELECT_BY_CLS_CODE = SELECT + " WHERE sr.sbj_year = ? AND sr.sbj_sem = ? AND sr.cls_code = ?";
	private static final String SELECT_BY_REG_NO = SELECT + " WHERE sr.sbj_year = ? AND sr.sbj_sem = ? AND sb.reg_no = ?";
	private static final String SELECT_BY_RGNO = SELECT + " WHERE sr.sbj_year = ? AND sr.sbj_sem = ? AND sb.rgno = ?";
	private static final String SELECT_DISTINCT_YEAR_AND_SEM = "SELECT DISTINCT sbj_year, sbj_sem FROM stu_register";
	
	@Override
	public StuRegister getStuRegistersByYearAndSemAndClsCodeAndClsNo(Connection conn, Integer sbj_year,
			Character sbj_sem, String cls_code, String cls_no) {
		StuRegister result = null;
        PreparedStatement ps = null;
        ResultSet rset = null;
        
        try {
            ps = conn.prepareStatement(SELECT_BY_CLS_CODE_AND_CLS_NO);
            int i = 1;
            ps.setInt(i++, sbj_year);
            ps.setString(i++, sbj_sem.toString());
            ps.setString(i++, cls_code);
            ps.setString(i++, cls_no);
            rset = ps.executeQuery();
            if (rset.next()) {
            	result = new StuRegister();
            	
            	result.setReg_num(rset.getInt("reg_num"));
            	result.setSbj_year(rset.getInt("sbj_year"));
            	result.setSbj_sem(StringUtils.toCharacter(rset.getString("sbj_sem")));
            	result.setRgno(rset.getInt("rgno"));
            	result.setCls_code(rset.getString("cls_code"));
            	result.setCls_no(rset.getString("cls_no"));
            	result.setSts_code(StringUtils.toCharacter(rset.getString("sts_code")));
            	result.setReg_flag(StringUtils.toCharacter(rset.getString("reg_flag")));
            	result.setSpd_num(rset.getInt("spd_num"));
            	result.setReenroll(StringUtils.toCharacter(rset.getString("reenroll")));
            	result.setRepeat(StringUtils.toCharacter(rset.getString("repeat")));
            	result.setDiv_code(rset.getString("div_code"));
            	result.setSync_disable(StringUtils.toCharacter(rset.getString("sync_disable")));
            	
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
            	result.setStuClass(stuClass);
            	
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
                result.setStuBasis(stuBasis);
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
    public List<StuRegister> getStuRegistersByYearAndSemAndClsCode(Connection conn, Integer sbj_year, Character sbj_sem,
            String cls_code) {
        List<StuRegister> result = new ArrayList<StuRegister>();
        PreparedStatement ps = null;
        ResultSet rset = null;
        
        try {
            ps = conn.prepareStatement(SELECT_BY_CLS_CODE);
            int i = 1;
            ps.setInt(i++, sbj_year);
            ps.setString(i++, sbj_sem.toString());
            ps.setString(i++, cls_code);
            rset = ps.executeQuery();
            while (rset.next()) {
                StuRegister stuRegister = new StuRegister();
                
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
                
                result.add(stuRegister);
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
    public StuRegister getStuRegisterByYearAndSemAndRegNo(Connection conn, Integer sbj_year, Character sbj_sem,
            String reg_no) {
        StuRegister result = null;
        PreparedStatement ps = null;
        ResultSet rset = null;
        
        try {
            ps = conn.prepareStatement(SELECT_BY_REG_NO);
            int i = 1;
            ps.setInt(i++, sbj_year);
            ps.setString(i++, sbj_sem.toString());
            ps.setString(i++, reg_no);
            rset = ps.executeQuery();
            if (rset.next()) {
                result = new StuRegister();
                
                result.setReg_num(rset.getInt("reg_num"));
                result.setSbj_year(rset.getInt("sbj_year"));
                result.setSbj_sem(StringUtils.toCharacter(rset.getString("sbj_sem")));
                result.setRgno(rset.getInt("rgno"));
                result.setCls_code(rset.getString("cls_code"));
                result.setCls_no(rset.getString("cls_no"));
                result.setSts_code(StringUtils.toCharacter(rset.getString("sts_code")));
                result.setReg_flag(StringUtils.toCharacter(rset.getString("reg_flag")));
                result.setSpd_num(rset.getInt("spd_num"));
                result.setReenroll(StringUtils.toCharacter(rset.getString("reenroll")));
                result.setRepeat(StringUtils.toCharacter(rset.getString("repeat")));
                result.setDiv_code(rset.getString("div_code"));
                result.setSync_disable(StringUtils.toCharacter(rset.getString("sync_disable")));
                
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
                result.setStuClass(stuClass);
                
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
                result.setStuBasis(stuBasis);
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
    public StuRegister getStuRegisterByYearAndSemAndRgno(Connection conn, Integer sbj_year, Character sbj_sem,
            Integer rgno) {
        StuRegister result = null;
        PreparedStatement ps = null;
        ResultSet rset = null;
        
        try {
            ps = conn.prepareStatement(SELECT_BY_RGNO);
            int i = 1;
            ps.setInt(i++, sbj_year);
            ps.setString(i++, sbj_sem.toString());
            ps.setInt(i++, rgno);
            rset = ps.executeQuery();
            if (rset.next()) {
                result = new StuRegister();
                
                result.setReg_num(rset.getInt("reg_num"));
                result.setSbj_year(rset.getInt("sbj_year"));
                result.setSbj_sem(StringUtils.toCharacter(rset.getString("sbj_sem")));
                result.setRgno(rset.getInt("rgno"));
                result.setCls_code(rset.getString("cls_code"));
                result.setCls_no(rset.getString("cls_no"));
                result.setSts_code(StringUtils.toCharacter(rset.getString("sts_code")));
                result.setReg_flag(StringUtils.toCharacter(rset.getString("reg_flag")));
                result.setSpd_num(rset.getInt("spd_num"));
                result.setReenroll(StringUtils.toCharacter(rset.getString("reenroll")));
                result.setRepeat(StringUtils.toCharacter(rset.getString("repeat")));
                result.setDiv_code(rset.getString("div_code"));
                result.setSync_disable(StringUtils.toCharacter(rset.getString("sync_disable")));
                
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
                result.setStuClass(stuClass);
                
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
                result.setStuBasis(stuBasis);
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
	public Map<Integer, Set<Character>> getYearAndSem(Connection conn) {
		Map<Integer, Set<Character>> result = new TreeMap<Integer, Set<Character>>();
        PreparedStatement ps = null;
        ResultSet rset = null;
        
        try {
            ps = conn.prepareStatement(SELECT_DISTINCT_YEAR_AND_SEM);
            rset = ps.executeQuery();
            while (rset.next()) {
                Integer year = rset.getInt("sbj_year");
                Character sem = StringUtils.toCharacter(rset.getString("sbj_sem"));
                if (result.containsKey(year)) {
                	result.get(year).add(sem);
                } else {
                    Set<Character> set = new TreeSet<Character>();
                    set.add(sem);
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
	public Integer getRgnoByYearAndSemAndClsCodeAndClsNo(Connection conn, Integer sbj_year, Character sbj_sem,
			String cls_code, String cls_no) {
		Integer result = null;
        PreparedStatement ps = null;
        ResultSet rset = null;
        
        try {
            ps = conn.prepareStatement(SELECT_RGNO_BY_CLS_CODE_AND_CLS_NO);
            int i = 1;
            ps.setInt(i++, sbj_year);
            ps.setString(i++, sbj_sem.toString());
            ps.setString(i++, cls_code);
            ps.setString(i++, cls_no);
            rset = ps.executeQuery();
            if (rset.next()) {
            	result = rset.getInt(1);
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
