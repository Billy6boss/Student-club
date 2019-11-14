package ntpc.ccai.clubmgt.bean;

public class Staff {
    private String staff_code;
    private String staff_cname;
    private String staff_ename;
    private String div_code;
    private String grade_code;
    private Byte sex;
    private Character ret_flag;
    private Character tch_flag;
    private Character mon_flag;
    private String address;
    private String phone;
    private String email;
    private String idno;
    private Short basic_hours;
    
    public Staff() {}
    public Staff(String staff_code, String staff_cname, String staff_ename, String div_code, String grade_code,
            Byte sex, Character ret_flag, Character tch_flag, Character mon_flag, String address, String phone,
            String email, String idno, Short basic_hours) {
        this.staff_code = staff_code;
        this.staff_cname = staff_cname;
        this.staff_ename = staff_ename;
        this.div_code = div_code;
        this.grade_code = grade_code;
        this.sex = sex;
        this.ret_flag = ret_flag;
        this.tch_flag = tch_flag;
        this.mon_flag = mon_flag;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.idno = idno;
        this.basic_hours = basic_hours;
    }
    
    public String getStaff_code() {
        return staff_code;
    }
    public void setStaff_code(String staff_code) {
        this.staff_code = staff_code;
    }
    public String getStaff_cname() {
        return staff_cname;
    }
    public void setStaff_cname(String staff_cname) {
        this.staff_cname = staff_cname;
    }
    public String getStaff_ename() {
        return staff_ename;
    }
    public void setStaff_ename(String staff_ename) {
        this.staff_ename = staff_ename;
    }
    public String getDiv_code() {
        return div_code;
    }
    public void setDiv_code(String div_code) {
        this.div_code = div_code;
    }
    public String getGrade_code() {
        return grade_code;
    }
    public void setGrade_code(String grade_code) {
        this.grade_code = grade_code;
    }
    public Byte getSex() {
        return sex;
    }
    public void setSex(Byte sex) {
        this.sex = sex;
    }
    public Character getRet_flag() {
        return ret_flag;
    }
    public void setRet_flag(Character ret_flag) {
        this.ret_flag = ret_flag;
    }
    public Character getTch_flag() {
        return tch_flag;
    }
    public void setTch_flag(Character tch_flag) {
        this.tch_flag = tch_flag;
    }
    public Character getMon_flag() {
        return mon_flag;
    }
    public void setMon_flag(Character mon_flag) {
        this.mon_flag = mon_flag;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getIdno() {
        return idno;
    }
    public void setIdno(String idno) {
        this.idno = idno;
    }
    public Short getBasic_hours() {
        return basic_hours;
    }
    public void setBasic_hours(Short basic_hours) {
        this.basic_hours = basic_hours;
    }
    
    @Override
    public String toString() {
        return "Staff [staff_code=" + staff_code + ", staff_cname=" + staff_cname + ", staff_ename=" + staff_ename
                + ", div_code=" + div_code + ", grade_code=" + grade_code + ", sex=" + sex + ", ret_flag=" + ret_flag
                + ", tch_flag=" + tch_flag + ", mon_flag=" + mon_flag + ", address=" + address + ", phone=" + phone
                + ", email=" + email + ", idno=" + idno + ", basic_hours=" + basic_hours + "]";
    }
    @Override
    public int hashCode() {
        final int prime = 23;
        int result = 17;
        result = prime * result + ((address == null) ? 0 : address.hashCode());
        result = prime * result + ((basic_hours == null) ? 0 : basic_hours.hashCode());
        result = prime * result + ((div_code == null) ? 0 : div_code.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((grade_code == null) ? 0 : grade_code.hashCode());
        result = prime * result + ((idno == null) ? 0 : idno.hashCode());
        result = prime * result + ((mon_flag == null) ? 0 : mon_flag.hashCode());
        result = prime * result + ((phone == null) ? 0 : phone.hashCode());
        result = prime * result + ((ret_flag == null) ? 0 : ret_flag.hashCode());
        result = prime * result + ((sex == null) ? 0 : sex.hashCode());
        result = prime * result + ((staff_cname == null) ? 0 : staff_cname.hashCode());
        result = prime * result + ((staff_code == null) ? 0 : staff_code.hashCode());
        result = prime * result + ((staff_ename == null) ? 0 : staff_ename.hashCode());
        result = prime * result + ((tch_flag == null) ? 0 : tch_flag.hashCode());
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
        Staff other = (Staff) obj;
        if (address == null) {
            if (other.address != null)
                return false;
        } else if (!address.equals(other.address))
            return false;
        if (basic_hours == null) {
            if (other.basic_hours != null)
                return false;
        } else if (!basic_hours.equals(other.basic_hours))
            return false;
        if (div_code == null) {
            if (other.div_code != null)
                return false;
        } else if (!div_code.equals(other.div_code))
            return false;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        if (grade_code == null) {
            if (other.grade_code != null)
                return false;
        } else if (!grade_code.equals(other.grade_code))
            return false;
        if (idno == null) {
            if (other.idno != null)
                return false;
        } else if (!idno.equals(other.idno))
            return false;
        if (mon_flag == null) {
            if (other.mon_flag != null)
                return false;
        } else if (!mon_flag.equals(other.mon_flag))
            return false;
        if (phone == null) {
            if (other.phone != null)
                return false;
        } else if (!phone.equals(other.phone))
            return false;
        if (ret_flag == null) {
            if (other.ret_flag != null)
                return false;
        } else if (!ret_flag.equals(other.ret_flag))
            return false;
        if (sex == null) {
            if (other.sex != null)
                return false;
        } else if (!sex.equals(other.sex))
            return false;
        if (staff_cname == null) {
            if (other.staff_cname != null)
                return false;
        } else if (!staff_cname.equals(other.staff_cname))
            return false;
        if (staff_code == null) {
            if (other.staff_code != null)
                return false;
        } else if (!staff_code.equals(other.staff_code))
            return false;
        if (staff_ename == null) {
            if (other.staff_ename != null)
                return false;
        } else if (!staff_ename.equals(other.staff_ename))
            return false;
        if (tch_flag == null) {
            if (other.tch_flag != null)
                return false;
        } else if (!tch_flag.equals(other.tch_flag))
            return false;
        return true;
    }
    
    

}
