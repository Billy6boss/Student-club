package ntpc.ccai.clubmgt.bean;

public class ClubSelectionClass {
    private Integer cs_num;
    private Integer sbj_year;
    private Character sbj_sem;
    private String cls_code;
    
    private ClubSelection clubSelection;
    private StuClass stuClass;
    
    public ClubSelectionClass() {}
    public ClubSelectionClass(Integer cs_num, Integer sbj_year, Character sbj_sem, String cls_code) {
        this.cs_num = cs_num;
        this.sbj_year = sbj_year;
        this.sbj_sem = sbj_sem;
        this.cls_code = cls_code;
    }
    
    public Integer getCs_num() {
        return cs_num;
    }
    public void setCs_num(Integer cs_num) {
        this.cs_num = cs_num;
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
    public String getCls_code() {
        return cls_code;
    }
    public void setCls_code(String cls_code) {
        this.cls_code = cls_code;
    }
    public ClubSelection getClubSelection() {
        return clubSelection;
    }
    public void setClubSelection(ClubSelection clubSelection) {
        this.clubSelection = clubSelection;
    }
    public StuClass getStuClass() {
        return stuClass;
    }
    public void setStuClass(StuClass stuClass) {
        this.stuClass = stuClass;
    }
    
    @Override
    public String toString() {
        return "ClubSelectionClass [cs_num=" + cs_num + ", sbj_year=" + sbj_year + ", sbj_sem=" + sbj_sem
                + ", cls_code=" + cls_code + "]";
    }
    @Override
    public int hashCode() {
        final int prime = 23;
        int result = 7;
        result = prime * result + ((cls_code == null) ? 0 : cls_code.hashCode());
        result = prime * result + ((cs_num == null) ? 0 : cs_num.hashCode());
        result = prime * result + ((sbj_sem == null) ? 0 : sbj_sem.hashCode());
        result = prime * result + ((sbj_year == null) ? 0 : sbj_year.hashCode());
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
        ClubSelectionClass other = (ClubSelectionClass) obj;
        if (cls_code == null) {
            if (other.cls_code != null)
                return false;
        } else if (!cls_code.equals(other.cls_code))
            return false;
        if (cs_num == null) {
            if (other.cs_num != null)
                return false;
        } else if (!cs_num.equals(other.cs_num))
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
        return true;
    }
    
    
}
