package ntpc.ccai.clubmgt.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import ntpc.ccai.clubmgt.bean.StuBasis;
import ntpc.ccai.clubmgt.dao.StuBasisDao;
import ntpc.ccai.clubmgt.util.StringUtils;

public class StuBasisDaoImpl implements StuBasisDao {
    private static final String SELECT_BY_REGNO = "SELECT rgno, reg_no, cname, ename, sex, nation, birthday, birthplace, passport, idno, "
                                                + "overseas, cmat_year, mat_code, div_code, olddiv_code, sts_code, stpmod, per_city_code, "
                                                + "per_town_num, per_village_num, per_neighbor, per_road, per_telno, com_city_code, "
                                                + "com_town_num, com_village_num, com_neighbor, com_road, com_telno, email, mobile_telno, "
                                                + "blood_type, place, nature, specid, y1_pass, y2_pass, y3_pass, y4_pass, y5_pass, "
                                                + "y1_pu, y2_pu, y3_pu, y4_pu, y5_pu, mg_school, grd_cname, grd_relation, grd_address, "
                                                + "grd_telno_h, grd_telno_o, grd_telno_m, cou_addr, cou_telno, cou_phone, sch_code, "
                                                + "admission_code, edu_degree, mg_code, inspection_num, inspection_date FROM stu_basis "
                                                + "WHERE reg_no = ?";
    private static final Logger logger = Logger.getLogger(StuBasisDaoImpl.class);

    @Override
    public StuBasis getStudentByRegno(Connection conn, String reg_no) {
        StuBasis result = null;
        PreparedStatement ps = null;
        ResultSet rset = null;
        
        try {
            ps = conn.prepareStatement(SELECT_BY_REGNO);
            ps.setString(1, reg_no);
            rset = ps.executeQuery();
            if (rset.next()) {
                result = new StuBasis();
                result.setRgno(rset.getInt("rgno"));
                result.setReg_no(rset.getString("reg_no"));
                result.setCname(rset.getString("cname"));
                result.setEname(rset.getString("ename"));
                result.setSex(rset.getByte("sex"));
                result.setNation(rset.getString("nation"));
                result.setBirthday(rset.getDate("birthday"));
                result.setBirthplace(rset.getString("birthplace"));
                result.setPassport(rset.getByte("passport"));
                result.setIdno(rset.getString("idno"));
                result.setOverseas(rset.getString("overseas"));
                result.setCmat_year(rset.getShort("cmat_year"));
                result.setMat_code(StringUtils.toCharacter(rset.getString("mat_code")));
                result.setDiv_code(rset.getString("div_code"));
                result.setOlddiv_code(rset.getString("olddiv_code"));
                result.setSts_code(rset.getString("sts_code"));
                result.setStpmod(StringUtils.toCharacter(rset.getString("stpmod")));
                result.setPer_city_code(rset.getString("per_city_code"));
                result.setPer_town_num(rset.getString("per_town_num"));
                result.setPer_village_num(rset.getString("per_village_num"));
                result.setPer_neighbor(rset.getShort("per_neighbor"));
                result.setPer_road(rset.getString("per_road"));
                result.setPer_telno(rset.getString("per_telno"));
                result.setCom_city_code(rset.getString("com_city_code"));
                result.setCom_town_num(rset.getString("com_town_num"));
                result.setCom_village_num(rset.getString("com_village_num"));
                result.setCom_neighbor(rset.getShort("com_neighbor"));
                result.setCom_road(rset.getString("com_road"));
                result.setCom_telno(rset.getString("com_telno"));
                result.setEmail(rset.getString("email"));
                result.setMobile_telno(rset.getString("mobile_telno"));
                result.setBlood_type(rset.getString("blood_type"));
                result.setPlace(StringUtils.toCharacter(rset.getString("place")));
                result.setNature(rset.getString("nature"));
                result.setSpecid(rset.getInt("specid"));
                result.setY1_pass(rset.getBigDecimal("y1_pass"));
                result.setY2_pass(rset.getBigDecimal("y2_pass"));
                result.setY3_pass(rset.getBigDecimal("y3_pass"));
                result.setY4_pass(rset.getBigDecimal("y4_pass"));
                result.setY5_pass(rset.getBigDecimal("y5_pass"));
                result.setY1_pu(rset.getBigDecimal("y1_pu"));
                result.setY2_pu(rset.getBigDecimal("y2_pu"));
                result.setY3_pu(rset.getBigDecimal("y3_pu"));
                result.setY4_pu(rset.getBigDecimal("y4_pu"));
                result.setY5_pu(rset.getBigDecimal("y5_pu"));
                result.setMg_school(rset.getString("mg_school"));
                result.setGrd_cname(rset.getString("grd_cname"));
                result.setGrd_relation(rset.getString("grd_relation"));
                result.setGrd_address(rset.getString("grd_address"));
                result.setGrd_telno_h(rset.getString("grd_telno_h"));
                result.setGrd_telno_o(rset.getString("grd_telno_o"));
                result.setGrd_telno_m(rset.getString("grd_telno_m"));
                result.setCou_addr(rset.getString("cou_addr"));
                result.setCou_telno(rset.getString("cou_telno"));
                result.setCou_phone(rset.getString("cou_phone"));
                result.setSch_code(rset.getString("sch_code"));
                result.setAdmission_code(rset.getString("admission_code"));
                result.setEdu_degree(rset.getString("edu_degree"));
                result.setMg_code(rset.getString("mg_code"));
                result.setInspection_num(rset.getString("inspection_num"));
                result.setInspection_date(rset.getDate("inspection_date"));
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
