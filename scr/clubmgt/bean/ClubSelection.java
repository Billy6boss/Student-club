package ntpc.ccai.clubmgt.bean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class ClubSelection {
    private Integer cs_num;
    private Integer sbj_year;
    private Character sbj_sem;
    private Boolean inuse;
    private Date cs_sdate;
    private Date cs_edate;
    private Character cs_method;
    private Integer cs_lower_limit;
    private Integer total_limit;
    private Integer grade_limit;
    private Integer class_limit;
    
    private ClubSem clubSem;
    private Set<ClubSelectionClass> clubSelectionClasses = new HashSet<ClubSelectionClass>(0);
    private Set<ClubSelectionClub> clubSelectionClubs = new HashSet<ClubSelectionClub>(0);
    
    
    
    public ClubSelection() {}
    public ClubSelection(Integer cs_num, Integer sbj_year, Character sbj_sem, Boolean inuse, Date cs_sdate,
	        Date cs_edate, Character cs_method, Integer cs_lower_limit, Integer total_limit, Integer grade_limit,
	        Integer class_limit) {
		this.cs_num = cs_num;
		this.sbj_year = sbj_year;
		this.sbj_sem = sbj_sem;
		this.inuse = inuse;
		this.cs_sdate = cs_sdate;
		this.cs_edate = cs_edate;
		this.cs_method = cs_method;
		this.cs_lower_limit = cs_lower_limit;
		this.total_limit = total_limit;
		this.grade_limit = grade_limit;
		this.class_limit = class_limit;
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
    public Boolean getInuse() {
        return inuse;
    }
    public void setInuse(Boolean inuse) {
        this.inuse = inuse;
    }
    public Date getCs_sdate() {
        return cs_sdate;
    }
    public void setCs_sdate(Date cs_sdate) {
        this.cs_sdate = cs_sdate;
    }
    public Date getCs_edate() {
        return cs_edate;
    }
    public void setCs_edate(Date cs_edate) {
        this.cs_edate = cs_edate;
    }
    public Character getCs_method() {
        return cs_method;
    }
    public void setCs_method(Character cs_method) {
        this.cs_method = cs_method;
    }
    public Integer getCs_lower_limit() {
        return cs_lower_limit;
    }
    public void setCs_lower_limit(Integer cs_lower_limit) {
        this.cs_lower_limit = cs_lower_limit;
    }
    public Integer getTotal_limit() {
        return total_limit;
    }
    public void setTotal_limit(Integer total_limit) {
        this.total_limit = total_limit;
    }
    public Integer getGrade_limit() {
        return grade_limit;
    }
    public void setGrade_limit(Integer grade_limit) {
        this.grade_limit = grade_limit;
    }
    public Integer getClass_limit() {
        return class_limit;
    }
    public void setClass_limit(Integer class_limit) {
        this.class_limit = class_limit;
    }
    public ClubSem getClubSem() {
        return clubSem;
    }
    public void setClubSem(ClubSem clubSem) {
        this.clubSem = clubSem;
    }
    public Set<ClubSelectionClass> getClubSelectionClasses() {
        return clubSelectionClasses;
    }
    public void setClubSelectionClasses(Set<ClubSelectionClass> clubSelectionClasses) {
        this.clubSelectionClasses = clubSelectionClasses;
    }
    public Set<ClubSelectionClub> getClubSelectionClubs() {
		return clubSelectionClubs;
	}
	public void setClubSelectionClubs(Set<ClubSelectionClub> clubSelectionClubs) {
		this.clubSelectionClubs = clubSelectionClubs;
	}
	@Override
    public String toString() {
        return "ClubSelection [cs_num=" + cs_num + ", sbj_year=" + sbj_year + ", sbj_sem="
                + sbj_sem + ", inuse=" + inuse + ", cs_sdate=" + cs_sdate + ", cs_edate=" + cs_edate + ", cs_method="
                + cs_method + ", cs_lower_limit=" + cs_lower_limit + ", total_limit=" + total_limit + ", grade_limit="
                + grade_limit + ", class_limit=" + class_limit + "]";
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 19;
        result = prime * result + ((class_limit == null) ? 0 : class_limit.hashCode());
        result = prime * result + ((cs_edate == null) ? 0 : cs_edate.hashCode());
        result = prime * result + ((cs_lower_limit == null) ? 0 : cs_lower_limit.hashCode());
        result = prime * result + ((cs_method == null) ? 0 : cs_method.hashCode());
        result = prime * result + ((cs_num == null) ? 0 : cs_num.hashCode());
        result = prime * result + ((cs_sdate == null) ? 0 : cs_sdate.hashCode());
        result = prime * result + ((grade_limit == null) ? 0 : grade_limit.hashCode());
        result = prime * result + ((inuse == null) ? 0 : inuse.hashCode());
        result = prime * result + ((sbj_sem == null) ? 0 : sbj_sem.hashCode());
        result = prime * result + ((sbj_year == null) ? 0 : sbj_year.hashCode());
        result = prime * result + ((total_limit == null) ? 0 : total_limit.hashCode());
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
        ClubSelection other = (ClubSelection) obj;
        if (class_limit == null) {
            if (other.class_limit != null)
                return false;
        } else if (!class_limit.equals(other.class_limit))
            return false;
        if (cs_edate == null) {
            if (other.cs_edate != null)
                return false;
        } else if (!cs_edate.equals(other.cs_edate))
            return false;
        if (cs_lower_limit == null) {
            if (other.cs_lower_limit != null)
                return false;
        } else if (!cs_lower_limit.equals(other.cs_lower_limit))
            return false;
        if (cs_method == null) {
            if (other.cs_method != null)
                return false;
        } else if (!cs_method.equals(other.cs_method))
            return false;
        if (cs_num == null) {
            if (other.cs_num != null)
                return false;
        } else if (!cs_num.equals(other.cs_num))
            return false;
        if (cs_sdate == null) {
            if (other.cs_sdate != null)
                return false;
        } else if (!cs_sdate.equals(other.cs_sdate))
            return false;
        if (grade_limit == null) {
            if (other.grade_limit != null)
                return false;
        } else if (!grade_limit.equals(other.grade_limit))
            return false;
        if (inuse == null) {
            if (other.inuse != null)
                return false;
        } else if (!inuse.equals(other.inuse))
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
        if (total_limit == null) {
            if (other.total_limit != null)
                return false;
        } else if (!total_limit.equals(other.total_limit))
            return false;
        return true;
    }
    
    // need to rewrite
    private Club club;
    private Staff staff;

    public Club getClub() {
        return club;
    }
    public void setClub(Club club) {
        this.club = club;
    }
    public Staff getStaff() {
        return staff;
    }
    public void setStaff(Staff staff) {
        this.staff = staff;
    }
}
