package ntpc.ccai.clubmgt.bean;

public class StuClass {
    private Integer sbj_year;
    private Character sbj_sem;
    private String cls_code;
    private String cls_cname;
    private Character mat_code;
    private String div_code;
    private String edu_dep_code;
    private Character grade;
    private Character cls_seq;
    private Character use_flag;
    
    public StuClass() {}

    public StuClass(Integer sbj_year, Character sbj_sem, String cls_code, String cls_cname, Character mat_code,
            String div_code, String edu_dep_code, Character grade, Character cls_seq, Character use_flag) {
        this.sbj_year = sbj_year;
        this.sbj_sem = sbj_sem;
        this.cls_code = cls_code;
        this.cls_cname = cls_cname;
        this.mat_code = mat_code;
        this.div_code = div_code;
        this.edu_dep_code = edu_dep_code;
        this.grade = grade;
        this.cls_seq = cls_seq;
        this.use_flag = use_flag;
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

    public String getCls_cname() {
        return cls_cname;
    }

    public void setCls_cname(String cls_cname) {
        this.cls_cname = cls_cname;
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

    public String getEdu_dep_code() {
        return edu_dep_code;
    }

    public void setEdu_dep_code(String edu_dep_code) {
        this.edu_dep_code = edu_dep_code;
    }

    public Character getGrade() {
        return grade;
    }

    public void setGrade(Character grade) {
        this.grade = grade;
    }

    public Character getCls_seq() {
        return cls_seq;
    }

    public void setCls_seq(Character cls_seq) {
        this.cls_seq = cls_seq;
    }

    public Character getUse_flag() {
        return use_flag;
    }

    public void setUse_flag(Character use_flag) {
        this.use_flag = use_flag;
    }

    @Override
    public String toString() {
        return "StuClass [sbj_year=" + sbj_year + ", sbj_sem=" + sbj_sem + ", cls_code=" + cls_code + ", cls_cname="
                + cls_cname + ", mat_code=" + mat_code + ", div_code=" + div_code + ", edu_dep_code=" + edu_dep_code
                + ", grade=" + grade + ", cls_seq=" + cls_seq + ", use_flag=" + use_flag + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 43;
        int result = 13;
        result = prime * result + ((cls_cname == null) ? 0 : cls_cname.hashCode());
        result = prime * result + ((cls_code == null) ? 0 : cls_code.hashCode());
        result = prime * result + ((cls_seq == null) ? 0 : cls_seq.hashCode());
        result = prime * result + ((div_code == null) ? 0 : div_code.hashCode());
        result = prime * result + ((edu_dep_code == null) ? 0 : edu_dep_code.hashCode());
        result = prime * result + ((grade == null) ? 0 : grade.hashCode());
        result = prime * result + ((mat_code == null) ? 0 : mat_code.hashCode());
        result = prime * result + ((sbj_sem == null) ? 0 : sbj_sem.hashCode());
        result = prime * result + ((sbj_year == null) ? 0 : sbj_year.hashCode());
        result = prime * result + ((use_flag == null) ? 0 : use_flag.hashCode());
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
        StuClass other = (StuClass) obj;
        if (cls_cname == null) {
            if (other.cls_cname != null)
                return false;
        } else if (!cls_cname.equals(other.cls_cname))
            return false;
        if (cls_code == null) {
            if (other.cls_code != null)
                return false;
        } else if (!cls_code.equals(other.cls_code))
            return false;
        if (cls_seq == null) {
            if (other.cls_seq != null)
                return false;
        } else if (!cls_seq.equals(other.cls_seq))
            return false;
        if (div_code == null) {
            if (other.div_code != null)
                return false;
        } else if (!div_code.equals(other.div_code))
            return false;
        if (edu_dep_code == null) {
            if (other.edu_dep_code != null)
                return false;
        } else if (!edu_dep_code.equals(other.edu_dep_code))
            return false;
        if (grade == null) {
            if (other.grade != null)
                return false;
        } else if (!grade.equals(other.grade))
            return false;
        if (mat_code == null) {
            if (other.mat_code != null)
                return false;
        } else if (!mat_code.equals(other.mat_code))
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
        if (use_flag == null) {
            if (other.use_flag != null)
                return false;
        } else if (!use_flag.equals(other.use_flag))
            return false;
        return true;
    }
    
    
    // need to rewrite
    
    private Club club;
    private StuBasis stuBasis;
    private ClubCadre clubCadre;

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public StuBasis getStuBasis() {
        return stuBasis;
    }

    public void setStuBasis(StuBasis stuBasis) {
        this.stuBasis = stuBasis;
    }

    public ClubCadre getClubCadre() {
        return clubCadre;
    }

    public void setClubCadre(ClubCadre clubCadre) {
        this.clubCadre = clubCadre;
    }
    
    
}
