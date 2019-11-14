package ntpc.ccai.clubmgt.bean;

import java.math.BigDecimal;
import java.util.Date;

public class StuBasis {
    private Integer rgno;
    private String reg_no;
    private String cname;
    private String ename;
    private Byte sex;
    private String nation;
    private Date birthday;
    private String birthplace;
    private Byte passport;
    private String idno;
    private String overseas;
    private Short cmat_year;
    private Character mat_code;
    private String div_code;
    private String olddiv_code;
    private String sts_code;
    private Character stpmod;
    private String per_city_code;
    private String per_town_num;
    private String per_village_num;
    private Short per_neighbor;
    private String per_road;
    private String per_telno;
    private String com_city_code;
    private String com_town_num;
    private String com_village_num;
    private Short com_neighbor;
    private String com_road;
    private String com_telno;
    private String email;
    private String mobile_telno;
    private String blood_type;
    private Character place;
    private String nature;
    private Integer specid;
    private BigDecimal y1_pass;
    private BigDecimal y2_pass;
    private BigDecimal y3_pass;
    private BigDecimal y4_pass;
    private BigDecimal y5_pass;
    private BigDecimal y1_pu;
    private BigDecimal y2_pu;
    private BigDecimal y3_pu;
    private BigDecimal y4_pu;
    private BigDecimal y5_pu;
    private String mg_school;
    private String grd_cname;
    private String grd_relation;
    private String grd_address;
    private String grd_telno_h;
    private String grd_telno_o;
    private String grd_telno_m;
    private String cou_addr;
    private String cou_telno;
    private String cou_phone;
    private String sch_code;
    private String admission_code;
    private String edu_degree;
    private String mg_code;
    private String inspection_num;
    private Date inspection_date;
    
    public StuBasis() {}
    public StuBasis(Integer rgno, String reg_no, String cname, String ename, Byte sex, String nation, Date birthday,
            String birthplace, Byte passport, String idno, String overseas, Short cmat_year, Character mat_code,
            String div_code, String olddiv_code, String sts_code, Character stpmod, String per_city_code,
            String per_town_num, String per_village_num, Short per_neighbor, String per_road, String per_telno,
            String com_city_code, String com_town_num, String com_village_num, Short com_neighbor, String com_road,
            String com_telno, String email, String mobile_telno, String blood_type, Character place, String nature,
            Integer specid, BigDecimal y1_pass, BigDecimal y2_pass, BigDecimal y3_pass, BigDecimal y4_pass,
            BigDecimal y5_pass, BigDecimal y1_pu, BigDecimal y2_pu, BigDecimal y3_pu, BigDecimal y4_pu,
            BigDecimal y5_pu, String mg_school, String grd_cname, String grd_relation, String grd_address,
            String grd_telno_h, String grd_telno_o, String grd_telno_m, String cou_addr, String cou_telno,
            String cou_phone, String sch_code, String admission_code, String edu_degree, String mg_code,
            String inspection_num, Date inspection_date) {
        this.rgno = rgno;
        this.reg_no = reg_no;
        this.cname = cname;
        this.ename = ename;
        this.sex = sex;
        this.nation = nation;
        this.birthday = birthday;
        this.birthplace = birthplace;
        this.passport = passport;
        this.idno = idno;
        this.overseas = overseas;
        this.cmat_year = cmat_year;
        this.mat_code = mat_code;
        this.div_code = div_code;
        this.olddiv_code = olddiv_code;
        this.sts_code = sts_code;
        this.stpmod = stpmod;
        this.per_city_code = per_city_code;
        this.per_town_num = per_town_num;
        this.per_village_num = per_village_num;
        this.per_neighbor = per_neighbor;
        this.per_road = per_road;
        this.per_telno = per_telno;
        this.com_city_code = com_city_code;
        this.com_town_num = com_town_num;
        this.com_village_num = com_village_num;
        this.com_neighbor = com_neighbor;
        this.com_road = com_road;
        this.com_telno = com_telno;
        this.email = email;
        this.mobile_telno = mobile_telno;
        this.blood_type = blood_type;
        this.place = place;
        this.nature = nature;
        this.specid = specid;
        this.y1_pass = y1_pass;
        this.y2_pass = y2_pass;
        this.y3_pass = y3_pass;
        this.y4_pass = y4_pass;
        this.y5_pass = y5_pass;
        this.y1_pu = y1_pu;
        this.y2_pu = y2_pu;
        this.y3_pu = y3_pu;
        this.y4_pu = y4_pu;
        this.y5_pu = y5_pu;
        this.mg_school = mg_school;
        this.grd_cname = grd_cname;
        this.grd_relation = grd_relation;
        this.grd_address = grd_address;
        this.grd_telno_h = grd_telno_h;
        this.grd_telno_o = grd_telno_o;
        this.grd_telno_m = grd_telno_m;
        this.cou_addr = cou_addr;
        this.cou_telno = cou_telno;
        this.cou_phone = cou_phone;
        this.sch_code = sch_code;
        this.admission_code = admission_code;
        this.edu_degree = edu_degree;
        this.mg_code = mg_code;
        this.inspection_num = inspection_num;
        this.inspection_date = inspection_date;
    }
    
    public Integer getRgno() {
        return rgno;
    }
    public void setRgno(Integer rgno) {
        this.rgno = rgno;
    }
    public String getReg_no() {
        return reg_no;
    }
    public void setReg_no(String reg_no) {
        this.reg_no = reg_no;
    }
    public String getCname() {
        return cname;
    }
    public void setCname(String cname) {
        this.cname = cname;
    }
    public String getEname() {
        return ename;
    }
    public void setEname(String ename) {
        this.ename = ename;
    }
    public Byte getSex() {
        return sex;
    }
    public void setSex(Byte sex) {
        this.sex = sex;
    }
    public String getNation() {
        return nation;
    }
    public void setNation(String nation) {
        this.nation = nation;
    }
    public Date getBirthday() {
        return birthday;
    }
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
    public String getBirthplace() {
        return birthplace;
    }
    public void setBirthplace(String birthplace) {
        this.birthplace = birthplace;
    }
    public Byte getPassport() {
        return passport;
    }
    public void setPassport(Byte passport) {
        this.passport = passport;
    }
    public String getIdno() {
        return idno;
    }
    public void setIdno(String idno) {
        this.idno = idno;
    }
    public String getOverseas() {
        return overseas;
    }
    public void setOverseas(String overseas) {
        this.overseas = overseas;
    }
    public Short getCmat_year() {
        return cmat_year;
    }
    public void setCmat_year(Short cmat_year) {
        this.cmat_year = cmat_year;
    }
    public Character getMat_code() {
        return mat_code;
    }
    public void setMat_code(Character mat_code) {
        this.mat_code = mat_code;
    }
    public String getDiv_code() {
        return div_code;
    }
    public void setDiv_code(String div_code) {
        this.div_code = div_code;
    }
    public String getOlddiv_code() {
        return olddiv_code;
    }
    public void setOlddiv_code(String olddiv_code) {
        this.olddiv_code = olddiv_code;
    }
    public String getSts_code() {
        return sts_code;
    }
    public void setSts_code(String sts_code) {
        this.sts_code = sts_code;
    }
    public Character getStpmod() {
        return stpmod;
    }
    public void setStpmod(Character stpmod) {
        this.stpmod = stpmod;
    }
    public String getPer_city_code() {
        return per_city_code;
    }
    public void setPer_city_code(String per_city_code) {
        this.per_city_code = per_city_code;
    }
    public String getPer_town_num() {
        return per_town_num;
    }
    public void setPer_town_num(String per_town_num) {
        this.per_town_num = per_town_num;
    }
    public String getPer_village_num() {
        return per_village_num;
    }
    public void setPer_village_num(String per_village_num) {
        this.per_village_num = per_village_num;
    }
    public Short getPer_neighbor() {
        return per_neighbor;
    }
    public void setPer_neighbor(Short per_neighbor) {
        this.per_neighbor = per_neighbor;
    }
    public String getPer_road() {
        return per_road;
    }
    public void setPer_road(String per_road) {
        this.per_road = per_road;
    }
    public String getPer_telno() {
        return per_telno;
    }
    public void setPer_telno(String per_telno) {
        this.per_telno = per_telno;
    }
    public String getCom_city_code() {
        return com_city_code;
    }
    public void setCom_city_code(String com_city_code) {
        this.com_city_code = com_city_code;
    }
    public String getCom_town_num() {
        return com_town_num;
    }
    public void setCom_town_num(String com_town_num) {
        this.com_town_num = com_town_num;
    }
    public String getCom_village_num() {
        return com_village_num;
    }
    public void setCom_village_num(String com_village_num) {
        this.com_village_num = com_village_num;
    }
    public Short getCom_neighbor() {
        return com_neighbor;
    }
    public void setCom_neighbor(Short com_neighbor) {
        this.com_neighbor = com_neighbor;
    }
    public String getCom_road() {
        return com_road;
    }
    public void setCom_road(String com_road) {
        this.com_road = com_road;
    }
    public String getCom_telno() {
        return com_telno;
    }
    public void setCom_telno(String com_telno) {
        this.com_telno = com_telno;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getMobile_telno() {
        return mobile_telno;
    }
    public void setMobile_telno(String mobile_telno) {
        this.mobile_telno = mobile_telno;
    }
    public String getBlood_type() {
        return blood_type;
    }
    public void setBlood_type(String blood_type) {
        this.blood_type = blood_type;
    }
    public Character getPlace() {
        return place;
    }
    public void setPlace(Character place) {
        this.place = place;
    }
    public String getNature() {
        return nature;
    }
    public void setNature(String nature) {
        this.nature = nature;
    }
    public Integer getSpecid() {
        return specid;
    }
    public void setSpecid(Integer specid) {
        this.specid = specid;
    }
    public BigDecimal getY1_pass() {
        return y1_pass;
    }
    public void setY1_pass(BigDecimal y1_pass) {
        this.y1_pass = y1_pass;
    }
    public BigDecimal getY2_pass() {
        return y2_pass;
    }
    public void setY2_pass(BigDecimal y2_pass) {
        this.y2_pass = y2_pass;
    }
    public BigDecimal getY3_pass() {
        return y3_pass;
    }
    public void setY3_pass(BigDecimal y3_pass) {
        this.y3_pass = y3_pass;
    }
    public BigDecimal getY4_pass() {
        return y4_pass;
    }
    public void setY4_pass(BigDecimal y4_pass) {
        this.y4_pass = y4_pass;
    }
    public BigDecimal getY5_pass() {
        return y5_pass;
    }
    public void setY5_pass(BigDecimal y5_pass) {
        this.y5_pass = y5_pass;
    }
    public BigDecimal getY1_pu() {
        return y1_pu;
    }
    public void setY1_pu(BigDecimal y1_pu) {
        this.y1_pu = y1_pu;
    }
    public BigDecimal getY2_pu() {
        return y2_pu;
    }
    public void setY2_pu(BigDecimal y2_pu) {
        this.y2_pu = y2_pu;
    }
    public BigDecimal getY3_pu() {
        return y3_pu;
    }
    public void setY3_pu(BigDecimal y3_pu) {
        this.y3_pu = y3_pu;
    }
    public BigDecimal getY4_pu() {
        return y4_pu;
    }
    public void setY4_pu(BigDecimal y4_pu) {
        this.y4_pu = y4_pu;
    }
    public BigDecimal getY5_pu() {
        return y5_pu;
    }
    public void setY5_pu(BigDecimal y5_pu) {
        this.y5_pu = y5_pu;
    }
    public String getMg_school() {
        return mg_school;
    }
    public void setMg_school(String mg_school) {
        this.mg_school = mg_school;
    }
    public String getGrd_cname() {
        return grd_cname;
    }
    public void setGrd_cname(String grd_cname) {
        this.grd_cname = grd_cname;
    }
    public String getGrd_relation() {
        return grd_relation;
    }
    public void setGrd_relation(String grd_relation) {
        this.grd_relation = grd_relation;
    }
    public String getGrd_address() {
        return grd_address;
    }
    public void setGrd_address(String grd_address) {
        this.grd_address = grd_address;
    }
    public String getGrd_telno_h() {
        return grd_telno_h;
    }
    public void setGrd_telno_h(String grd_telno_h) {
        this.grd_telno_h = grd_telno_h;
    }
    public String getGrd_telno_o() {
        return grd_telno_o;
    }
    public void setGrd_telno_o(String grd_telno_o) {
        this.grd_telno_o = grd_telno_o;
    }
    public String getGrd_telno_m() {
        return grd_telno_m;
    }
    public void setGrd_telno_m(String grd_telno_m) {
        this.grd_telno_m = grd_telno_m;
    }
    public String getCou_addr() {
        return cou_addr;
    }
    public void setCou_addr(String cou_addr) {
        this.cou_addr = cou_addr;
    }
    public String getCou_telno() {
        return cou_telno;
    }
    public void setCou_telno(String cou_telno) {
        this.cou_telno = cou_telno;
    }
    public String getCou_phone() {
        return cou_phone;
    }
    public void setCou_phone(String cou_phone) {
        this.cou_phone = cou_phone;
    }
    public String getSch_code() {
        return sch_code;
    }
    public void setSch_code(String sch_code) {
        this.sch_code = sch_code;
    }
    public String getAdmission_code() {
        return admission_code;
    }
    public void setAdmission_code(String admission_code) {
        this.admission_code = admission_code;
    }
    public String getEdu_degree() {
        return edu_degree;
    }
    public void setEdu_degree(String edu_degree) {
        this.edu_degree = edu_degree;
    }
    public String getMg_code() {
        return mg_code;
    }
    public void setMg_code(String mg_code) {
        this.mg_code = mg_code;
    }
    public String getInspection_num() {
        return inspection_num;
    }
    public void setInspection_num(String inspection_num) {
        this.inspection_num = inspection_num;
    }
    public Date getInspection_date() {
        return inspection_date;
    }
    public void setInspection_date(Date inspection_date) {
        this.inspection_date = inspection_date;
    }
    
    @Override
    public String toString() {
        return "StuBasis [rgno=" + rgno + ", reg_no=" + reg_no + ", cname=" + cname + ", ename=" + ename + ", sex="
                + sex + ", nation=" + nation + ", birthday=" + birthday + ", birthplace=" + birthplace + ", passport="
                + passport + ", idno=" + idno + ", overseas=" + overseas + ", cmat_year=" + cmat_year + ", mat_code="
                + mat_code + ", div_code=" + div_code + ", olddiv_code=" + olddiv_code + ", sts_code=" + sts_code
                + ", stpmod=" + stpmod + ", per_city_code=" + per_city_code + ", per_town_num=" + per_town_num
                + ", per_village_num=" + per_village_num + ", per_neighbor=" + per_neighbor + ", per_road=" + per_road
                + ", per_telno=" + per_telno + ", com_city_code=" + com_city_code + ", com_town_num=" + com_town_num
                + ", com_village_num=" + com_village_num + ", com_neighbor=" + com_neighbor + ", com_road=" + com_road
                + ", telno=" + com_telno + ", email=" + email + ", mobile_telno=" + mobile_telno + ", blood_type="
                + blood_type + ", place=" + place + ", nature=" + nature + ", specid=" + specid + ", y1_pass=" + y1_pass
                + ", y2_pass=" + y2_pass + ", y3_pass=" + y3_pass + ", y4_pass=" + y4_pass + ", y5_pass=" + y5_pass
                + ", y1_pu=" + y1_pu + ", y2_pu=" + y2_pu + ", y3_pu=" + y3_pu + ", y4_pu=" + y4_pu + ", y5_pu=" + y5_pu
                + ", mg_school=" + mg_school + ", grd_cname=" + grd_cname + ", grd_relation=" + grd_relation
                + ", grd_address=" + grd_address + ", grd_telno_h=" + grd_telno_h + ", grd_telno_o=" + grd_telno_o
                + ", grd_telno_m=" + grd_telno_m + ", cou_addr=" + cou_addr + ", cou_telno=" + cou_telno
                + ", cou_phone=" + cou_phone + ", sch_code=" + sch_code + ", admission_code=" + admission_code
                + ", edu_degree=" + edu_degree + ", mg_code=" + mg_code + ", inspection_num=" + inspection_num
                + ", inspection_date=" + inspection_date + "]";
    }
    @Override
    public int hashCode() {
        final int prime = 19;
        int result = 5;
        result = prime * result + ((admission_code == null) ? 0 : admission_code.hashCode());
        result = prime * result + ((birthday == null) ? 0 : birthday.hashCode());
        result = prime * result + ((birthplace == null) ? 0 : birthplace.hashCode());
        result = prime * result + ((blood_type == null) ? 0 : blood_type.hashCode());
        result = prime * result + ((cmat_year == null) ? 0 : cmat_year.hashCode());
        result = prime * result + ((cname == null) ? 0 : cname.hashCode());
        result = prime * result + ((com_city_code == null) ? 0 : com_city_code.hashCode());
        result = prime * result + ((com_neighbor == null) ? 0 : com_neighbor.hashCode());
        result = prime * result + ((com_road == null) ? 0 : com_road.hashCode());
        result = prime * result + ((com_telno == null) ? 0 : com_telno.hashCode());
        result = prime * result + ((com_town_num == null) ? 0 : com_town_num.hashCode());
        result = prime * result + ((com_village_num == null) ? 0 : com_village_num.hashCode());
        result = prime * result + ((cou_addr == null) ? 0 : cou_addr.hashCode());
        result = prime * result + ((cou_phone == null) ? 0 : cou_phone.hashCode());
        result = prime * result + ((cou_telno == null) ? 0 : cou_telno.hashCode());
        result = prime * result + ((div_code == null) ? 0 : div_code.hashCode());
        result = prime * result + ((edu_degree == null) ? 0 : edu_degree.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((ename == null) ? 0 : ename.hashCode());
        result = prime * result + ((grd_address == null) ? 0 : grd_address.hashCode());
        result = prime * result + ((grd_cname == null) ? 0 : grd_cname.hashCode());
        result = prime * result + ((grd_relation == null) ? 0 : grd_relation.hashCode());
        result = prime * result + ((grd_telno_h == null) ? 0 : grd_telno_h.hashCode());
        result = prime * result + ((grd_telno_m == null) ? 0 : grd_telno_m.hashCode());
        result = prime * result + ((grd_telno_o == null) ? 0 : grd_telno_o.hashCode());
        result = prime * result + ((idno == null) ? 0 : idno.hashCode());
        result = prime * result + ((inspection_date == null) ? 0 : inspection_date.hashCode());
        result = prime * result + ((inspection_num == null) ? 0 : inspection_num.hashCode());
        result = prime * result + ((mat_code == null) ? 0 : mat_code.hashCode());
        result = prime * result + ((mg_code == null) ? 0 : mg_code.hashCode());
        result = prime * result + ((mg_school == null) ? 0 : mg_school.hashCode());
        result = prime * result + ((mobile_telno == null) ? 0 : mobile_telno.hashCode());
        result = prime * result + ((nation == null) ? 0 : nation.hashCode());
        result = prime * result + ((nature == null) ? 0 : nature.hashCode());
        result = prime * result + ((olddiv_code == null) ? 0 : olddiv_code.hashCode());
        result = prime * result + ((overseas == null) ? 0 : overseas.hashCode());
        result = prime * result + ((passport == null) ? 0 : passport.hashCode());
        result = prime * result + ((per_city_code == null) ? 0 : per_city_code.hashCode());
        result = prime * result + ((per_neighbor == null) ? 0 : per_neighbor.hashCode());
        result = prime * result + ((per_road == null) ? 0 : per_road.hashCode());
        result = prime * result + ((per_telno == null) ? 0 : per_telno.hashCode());
        result = prime * result + ((per_town_num == null) ? 0 : per_town_num.hashCode());
        result = prime * result + ((per_village_num == null) ? 0 : per_village_num.hashCode());
        result = prime * result + ((place == null) ? 0 : place.hashCode());
        result = prime * result + ((reg_no == null) ? 0 : reg_no.hashCode());
        result = prime * result + ((rgno == null) ? 0 : rgno.hashCode());
        result = prime * result + ((sch_code == null) ? 0 : sch_code.hashCode());
        result = prime * result + ((sex == null) ? 0 : sex.hashCode());
        result = prime * result + ((specid == null) ? 0 : specid.hashCode());
        result = prime * result + ((stpmod == null) ? 0 : stpmod.hashCode());
        result = prime * result + ((sts_code == null) ? 0 : sts_code.hashCode());
        result = prime * result + ((y1_pass == null) ? 0 : y1_pass.hashCode());
        result = prime * result + ((y1_pu == null) ? 0 : y1_pu.hashCode());
        result = prime * result + ((y2_pass == null) ? 0 : y2_pass.hashCode());
        result = prime * result + ((y2_pu == null) ? 0 : y2_pu.hashCode());
        result = prime * result + ((y3_pass == null) ? 0 : y3_pass.hashCode());
        result = prime * result + ((y3_pu == null) ? 0 : y3_pu.hashCode());
        result = prime * result + ((y4_pass == null) ? 0 : y4_pass.hashCode());
        result = prime * result + ((y4_pu == null) ? 0 : y4_pu.hashCode());
        result = prime * result + ((y5_pass == null) ? 0 : y5_pass.hashCode());
        result = prime * result + ((y5_pu == null) ? 0 : y5_pu.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        StuBasis other = (StuBasis) obj;
        if (admission_code == null) {
            if (other.admission_code != null)
                return false;
        } else if (!admission_code.equals(other.admission_code))
            return false;
        if (birthday == null) {
            if (other.birthday != null)
                return false;
        } else if (!birthday.equals(other.birthday))
            return false;
        if (birthplace == null) {
            if (other.birthplace != null)
                return false;
        } else if (!birthplace.equals(other.birthplace))
            return false;
        if (blood_type == null) {
            if (other.blood_type != null)
                return false;
        } else if (!blood_type.equals(other.blood_type))
            return false;
        if (cmat_year == null) {
            if (other.cmat_year != null)
                return false;
        } else if (!cmat_year.equals(other.cmat_year))
            return false;
        if (cname == null) {
            if (other.cname != null)
                return false;
        } else if (!cname.equals(other.cname))
            return false;
        if (com_city_code == null) {
            if (other.com_city_code != null)
                return false;
        } else if (!com_city_code.equals(other.com_city_code))
            return false;
        if (com_neighbor == null) {
            if (other.com_neighbor != null)
                return false;
        } else if (!com_neighbor.equals(other.com_neighbor))
            return false;
        if (com_road == null) {
            if (other.com_road != null)
                return false;
        } else if (!com_road.equals(other.com_road))
            return false;
        if (com_telno == null) {
            if (other.com_telno != null)
                return false;
        } else if (!com_telno.equals(other.com_telno))
            return false;
        if (com_town_num == null) {
            if (other.com_town_num != null)
                return false;
        } else if (!com_town_num.equals(other.com_town_num))
            return false;
        if (com_village_num == null) {
            if (other.com_village_num != null)
                return false;
        } else if (!com_village_num.equals(other.com_village_num))
            return false;
        if (cou_addr == null) {
            if (other.cou_addr != null)
                return false;
        } else if (!cou_addr.equals(other.cou_addr))
            return false;
        if (cou_phone == null) {
            if (other.cou_phone != null)
                return false;
        } else if (!cou_phone.equals(other.cou_phone))
            return false;
        if (cou_telno == null) {
            if (other.cou_telno != null)
                return false;
        } else if (!cou_telno.equals(other.cou_telno))
            return false;
        if (div_code == null) {
            if (other.div_code != null)
                return false;
        } else if (!div_code.equals(other.div_code))
            return false;
        if (edu_degree == null) {
            if (other.edu_degree != null)
                return false;
        } else if (!edu_degree.equals(other.edu_degree))
            return false;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        if (ename == null) {
            if (other.ename != null)
                return false;
        } else if (!ename.equals(other.ename))
            return false;
        if (grd_address == null) {
            if (other.grd_address != null)
                return false;
        } else if (!grd_address.equals(other.grd_address))
            return false;
        if (grd_cname == null) {
            if (other.grd_cname != null)
                return false;
        } else if (!grd_cname.equals(other.grd_cname))
            return false;
        if (grd_relation == null) {
            if (other.grd_relation != null)
                return false;
        } else if (!grd_relation.equals(other.grd_relation))
            return false;
        if (grd_telno_h == null) {
            if (other.grd_telno_h != null)
                return false;
        } else if (!grd_telno_h.equals(other.grd_telno_h))
            return false;
        if (grd_telno_m == null) {
            if (other.grd_telno_m != null)
                return false;
        } else if (!grd_telno_m.equals(other.grd_telno_m))
            return false;
        if (grd_telno_o == null) {
            if (other.grd_telno_o != null)
                return false;
        } else if (!grd_telno_o.equals(other.grd_telno_o))
            return false;
        if (idno == null) {
            if (other.idno != null)
                return false;
        } else if (!idno.equals(other.idno))
            return false;
        if (inspection_date == null) {
            if (other.inspection_date != null)
                return false;
        } else if (!inspection_date.equals(other.inspection_date))
            return false;
        if (inspection_num == null) {
            if (other.inspection_num != null)
                return false;
        } else if (!inspection_num.equals(other.inspection_num))
            return false;
        if (mat_code == null) {
            if (other.mat_code != null)
                return false;
        } else if (!mat_code.equals(other.mat_code))
            return false;
        if (mg_code == null) {
            if (other.mg_code != null)
                return false;
        } else if (!mg_code.equals(other.mg_code))
            return false;
        if (mg_school == null) {
            if (other.mg_school != null)
                return false;
        } else if (!mg_school.equals(other.mg_school))
            return false;
        if (mobile_telno == null) {
            if (other.mobile_telno != null)
                return false;
        } else if (!mobile_telno.equals(other.mobile_telno))
            return false;
        if (nation == null) {
            if (other.nation != null)
                return false;
        } else if (!nation.equals(other.nation))
            return false;
        if (nature == null) {
            if (other.nature != null)
                return false;
        } else if (!nature.equals(other.nature))
            return false;
        if (olddiv_code == null) {
            if (other.olddiv_code != null)
                return false;
        } else if (!olddiv_code.equals(other.olddiv_code))
            return false;
        if (overseas == null) {
            if (other.overseas != null)
                return false;
        } else if (!overseas.equals(other.overseas))
            return false;
        if (passport == null) {
            if (other.passport != null)
                return false;
        } else if (!passport.equals(other.passport))
            return false;
        if (per_city_code == null) {
            if (other.per_city_code != null)
                return false;
        } else if (!per_city_code.equals(other.per_city_code))
            return false;
        if (per_neighbor == null) {
            if (other.per_neighbor != null)
                return false;
        } else if (!per_neighbor.equals(other.per_neighbor))
            return false;
        if (per_road == null) {
            if (other.per_road != null)
                return false;
        } else if (!per_road.equals(other.per_road))
            return false;
        if (per_telno == null) {
            if (other.per_telno != null)
                return false;
        } else if (!per_telno.equals(other.per_telno))
            return false;
        if (per_town_num == null) {
            if (other.per_town_num != null)
                return false;
        } else if (!per_town_num.equals(other.per_town_num))
            return false;
        if (per_village_num == null) {
            if (other.per_village_num != null)
                return false;
        } else if (!per_village_num.equals(other.per_village_num))
            return false;
        if (place == null) {
            if (other.place != null)
                return false;
        } else if (!place.equals(other.place))
            return false;
        if (reg_no == null) {
            if (other.reg_no != null)
                return false;
        } else if (!reg_no.equals(other.reg_no))
            return false;
        if (rgno == null) {
            if (other.rgno != null)
                return false;
        } else if (!rgno.equals(other.rgno))
            return false;
        if (sch_code == null) {
            if (other.sch_code != null)
                return false;
        } else if (!sch_code.equals(other.sch_code))
            return false;
        if (sex == null) {
            if (other.sex != null)
                return false;
        } else if (!sex.equals(other.sex))
            return false;
        if (specid == null) {
            if (other.specid != null)
                return false;
        } else if (!specid.equals(other.specid))
            return false;
        if (stpmod == null) {
            if (other.stpmod != null)
                return false;
        } else if (!stpmod.equals(other.stpmod))
            return false;
        if (sts_code == null) {
            if (other.sts_code != null)
                return false;
        } else if (!sts_code.equals(other.sts_code))
            return false;
        if (y1_pass == null) {
            if (other.y1_pass != null)
                return false;
        } else if (!y1_pass.equals(other.y1_pass))
            return false;
        if (y1_pu == null) {
            if (other.y1_pu != null)
                return false;
        } else if (!y1_pu.equals(other.y1_pu))
            return false;
        if (y2_pass == null) {
            if (other.y2_pass != null)
                return false;
        } else if (!y2_pass.equals(other.y2_pass))
            return false;
        if (y2_pu == null) {
            if (other.y2_pu != null)
                return false;
        } else if (!y2_pu.equals(other.y2_pu))
            return false;
        if (y3_pass == null) {
            if (other.y3_pass != null)
                return false;
        } else if (!y3_pass.equals(other.y3_pass))
            return false;
        if (y3_pu == null) {
            if (other.y3_pu != null)
                return false;
        } else if (!y3_pu.equals(other.y3_pu))
            return false;
        if (y4_pass == null) {
            if (other.y4_pass != null)
                return false;
        } else if (!y4_pass.equals(other.y4_pass))
            return false;
        if (y4_pu == null) {
            if (other.y4_pu != null)
                return false;
        } else if (!y4_pu.equals(other.y4_pu))
            return false;
        if (y5_pass == null) {
            if (other.y5_pass != null)
                return false;
        } else if (!y5_pass.equals(other.y5_pass))
            return false;
        if (y5_pu == null) {
            if (other.y5_pu != null)
                return false;
        } else if (!y5_pu.equals(other.y5_pu))
            return false;
        return true;
    }
    
    
}
