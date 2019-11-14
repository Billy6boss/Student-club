package ntpc.ccai.clubmgt.bean;

public class StuRegister {
    private Integer reg_num; 
    private Integer sbj_year;
    private Character sbj_sem;
    private Integer rgno;
    private String cls_code;
    private String cls_no;
    private Character sts_code;
    private Character reg_flag;
    private Integer spd_num;
    private Character reenroll;
    private Character repeat;
    private String div_code;
    private Character sync_disable;
    
    private StuBasis stuBasis;
    private StuClass stuClass;
    
    public StuRegister() {}

    public StuRegister(Integer reg_num, Integer sbj_year, Character sbj_sem, Integer rgno, String cls_code,
            String cls_no, Character sts_code, Character reg_flag, Integer spd_num, Character reenroll,
            Character repeat, String div_code, Character sync_disable) {
        this.reg_num = reg_num;
        this.sbj_year = sbj_year;
        this.sbj_sem = sbj_sem;
        this.rgno = rgno;
        this.cls_code = cls_code;
        this.cls_no = cls_no;
        this.sts_code = sts_code;
        this.reg_flag = reg_flag;
        this.spd_num = spd_num;
        this.reenroll = reenroll;
        this.repeat = repeat;
        this.div_code = div_code;
        this.sync_disable = sync_disable;
    }
    
    public Integer getReg_num() {
        return reg_num;
    }
    public void setReg_num(Integer reg_num) {
        this.reg_num = reg_num;
    }
    public Integer getSbj_year() {
        return sbj_year;
    }
    public void setSbj_year(Integer sbj_year) {
        this.sbj_year = sbj_year;
    }
    public Character getSbj_sem() {
        return sbj_sem;
    }
    public void setSbj_sem(Character sbj_sem) {
        this.sbj_sem = sbj_sem;
    }
    public Integer getRgno() {
        return rgno;
    }
    public void setRgno(Integer rgno) {
        this.rgno = rgno;
    }
    public String getCls_code() {
        return cls_code;
    }
    public void setCls_code(String cls_code) {
        this.cls_code = cls_code;
    }
    public String getCls_no() {
        return cls_no;
    }
    public void setCls_no(String cls_no) {
        this.cls_no = cls_no;
    }
    public Character getSts_code() {
        return sts_code;
    }
    public void setSts_code(Character sts_code) {
        this.sts_code = sts_code;
    }
    public Character getReg_flag() {
        return reg_flag;
    }
    public void setReg_flag(Character reg_flag) {
        this.reg_flag = reg_flag;
    }
    public Integer getSpd_num() {
        return spd_num;
    }
    public void setSpd_num(Integer spd_num) {
        this.spd_num = spd_num;
    }
    public Character getReenroll() {
        return reenroll;
    }
    public void setReenroll(Character reenroll) {
        this.reenroll = reenroll;
    }
    public Character getRepeat() {
        return repeat;
    }
    public void setRepeat(Character repeat) {
        this.repeat = repeat;
    }
    public String getDiv_code() {
        return div_code;
    }
    public void setDiv_code(String div_code) {
        this.div_code = div_code;
    }
    public Character getSync_disable() {
        return sync_disable;
    }
    public void setSync_disable(Character sync_disable) {
        this.sync_disable = sync_disable;
    }

    
    
    public StuBasis getStuBasis() {
        return stuBasis;
    }

    public void setStuBasis(StuBasis stuBasis) {
        this.stuBasis = stuBasis;
    }

    public StuClass getStuClass() {
        return stuClass;
    }

    public void setStuClass(StuClass stuClass) {
        this.stuClass = stuClass;
    }

    @Override
    public String toString() {
        return "StuRegister [reg_num=" + reg_num + ", sbj_year=" + sbj_year + ", sbj_sem=" + sbj_sem + ", rgno=" + rgno
                + ", cls_code=" + cls_code + ", cls_no=" + cls_no + ", sts_code=" + sts_code + ", reg_flag=" + reg_flag
                + ", spd_num=" + spd_num + ", reenroll=" + reenroll + ", repeat=" + repeat + ", div_code=" + div_code
                + ", sync_disable=" + sync_disable + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 47;
        int result = 17;
        result = prime * result + ((cls_code == null) ? 0 : cls_code.hashCode());
        result = prime * result + ((cls_no == null) ? 0 : cls_no.hashCode());
        result = prime * result + ((div_code == null) ? 0 : div_code.hashCode());
        result = prime * result + ((reenroll == null) ? 0 : reenroll.hashCode());
        result = prime * result + ((reg_flag == null) ? 0 : reg_flag.hashCode());
        result = prime * result + ((reg_num == null) ? 0 : reg_num.hashCode());
        result = prime * result + ((repeat == null) ? 0 : repeat.hashCode());
        result = prime * result + ((rgno == null) ? 0 : rgno.hashCode());
        result = prime * result + ((sbj_sem == null) ? 0 : sbj_sem.hashCode());
        result = prime * result + ((sbj_year == null) ? 0 : sbj_year.hashCode());
        result = prime * result + ((spd_num == null) ? 0 : spd_num.hashCode());
        result = prime * result + ((sts_code == null) ? 0 : sts_code.hashCode());
        result = prime * result + ((sync_disable == null) ? 0 : sync_disable.hashCode());
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
        StuRegister other = (StuRegister) obj;
        if (cls_code == null) {
            if (other.cls_code != null)
                return false;
        } else if (!cls_code.equals(other.cls_code))
            return false;
        if (cls_no == null) {
            if (other.cls_no != null)
                return false;
        } else if (!cls_no.equals(other.cls_no))
            return false;
        if (div_code == null) {
            if (other.div_code != null)
                return false;
        } else if (!div_code.equals(other.div_code))
            return false;
        if (reenroll == null) {
            if (other.reenroll != null)
                return false;
        } else if (!reenroll.equals(other.reenroll))
            return false;
        if (reg_flag == null) {
            if (other.reg_flag != null)
                return false;
        } else if (!reg_flag.equals(other.reg_flag))
            return false;
        if (reg_num == null) {
            if (other.reg_num != null)
                return false;
        } else if (!reg_num.equals(other.reg_num))
            return false;
        if (repeat == null) {
            if (other.repeat != null)
                return false;
        } else if (!repeat.equals(other.repeat))
            return false;
        if (rgno == null) {
            if (other.rgno != null)
                return false;
        } else if (!rgno.equals(other.rgno))
            return false;
        if (sbj_sem == null) {
            if (other.sbj_sem != null)
                return false;
        } else if (!sbj_sem.equals(other.sbj_sem))
            return false;
        if (sbj_year == null) {
            if (other.sbj_year != null)
                return false;
        } else if (!sbj_year.equals(other.sbj_year))
            return false;
        if (spd_num == null) {
            if (other.spd_num != null)
                return false;
        } else if (!spd_num.equals(other.spd_num))
            return false;
        if (sts_code == null) {
            if (other.sts_code != null)
                return false;
        } else if (!sts_code.equals(other.sts_code))
            return false;
        if (sync_disable == null) {
            if (other.sync_disable != null)
                return false;
        } else if (!sync_disable.equals(other.sync_disable))
            return false;
        return true;
    }

    
}
