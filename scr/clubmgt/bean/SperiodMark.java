package ntpc.ccai.clubmgt.bean;

public class SperiodMark {
    private Short speriod;
    private String sprd_mark;
    private String sprd_code;
    private String sprd_name;
    private Character adg_code;
    private Short sprd_order;
    
    public SperiodMark() {}
    public SperiodMark(Short speriod, String sprd_mark, String sprd_code, String sprd_name, Character adg_code,
            Short sprd_order) {
        this.speriod = speriod;
        this.sprd_mark = sprd_mark;
        this.sprd_code = sprd_code;
        this.sprd_name = sprd_name;
        this.adg_code = adg_code;
        this.sprd_order = sprd_order;
    }
    
    public Short getSperiod() {
        return speriod;
    }
    public void setSperiod(Short speriod) {
        this.speriod = speriod;
    }
    public String getSprd_mark() {
        return sprd_mark;
    }
    public void setSprd_mark(String sprd_mark) {
        this.sprd_mark = sprd_mark;
    }
    public String getSprd_code() {
        return sprd_code;
    }
    public void setSprd_code(String sprd_code) {
        this.sprd_code = sprd_code;
    }
    public String getSprd_name() {
        return sprd_name;
    }
    public void setSprd_name(String sprd_name) {
        this.sprd_name = sprd_name;
    }
    public Character getAdg_code() {
        return adg_code;
    }
    public void setAdg_code(Character adg_code) {
        this.adg_code = adg_code;
    }
    public Short getSprd_order() {
        return sprd_order;
    }
    public void setSprd_order(Short sprd_order) {
        this.sprd_order = sprd_order;
    }
    
    @Override
    public String toString() {
        return "SperiodMark [speriod=" + speriod + ", sprd_mark=" + sprd_mark + ", sprd_code=" + sprd_code
                + ", sprd_name=" + sprd_name + ", adg_code=" + adg_code + ", sprd_order=" + sprd_order + "]";
    }
    @Override
    public int hashCode() {
        final int prime = 53;
        int result = 13;
        result = prime * result + ((adg_code == null) ? 0 : adg_code.hashCode());
        result = prime * result + ((speriod == null) ? 0 : speriod.hashCode());
        result = prime * result + ((sprd_code == null) ? 0 : sprd_code.hashCode());
        result = prime * result + ((sprd_mark == null) ? 0 : sprd_mark.hashCode());
        result = prime * result + ((sprd_name == null) ? 0 : sprd_name.hashCode());
        result = prime * result + ((sprd_order == null) ? 0 : sprd_order.hashCode());
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
        SperiodMark other = (SperiodMark) obj;
        if (adg_code == null) {
            if (other.adg_code != null)
                return false;
        } else if (!adg_code.equals(other.adg_code))
            return false;
        if (speriod == null) {
            if (other.speriod != null)
                return false;
        } else if (!speriod.equals(other.speriod))
            return false;
        if (sprd_code == null) {
            if (other.sprd_code != null)
                return false;
        } else if (!sprd_code.equals(other.sprd_code))
            return false;
        if (sprd_mark == null) {
            if (other.sprd_mark != null)
                return false;
        } else if (!sprd_mark.equals(other.sprd_mark))
            return false;
        if (sprd_name == null) {
            if (other.sprd_name != null)
                return false;
        } else if (!sprd_name.equals(other.sprd_name))
            return false;
        if (sprd_order == null) {
            if (other.sprd_order != null)
                return false;
        } else if (!sprd_order.equals(other.sprd_order))
            return false;
        return true;
    }
    
}
