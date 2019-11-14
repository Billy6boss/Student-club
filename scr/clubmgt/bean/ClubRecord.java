package ntpc.ccai.clubmgt.bean;

import java.util.Date;

public class ClubRecord {
    private Integer cr_num;
    private Integer club_num;
    private Integer sbj_year;
    private Character sbj_sem;
    private Integer rgno;
    private Date cr_sdate;
    private Date cr_edate;
    private String cr_detail;
    private Integer cr_hours;
    private String staff_code;
    private Date cr_time;
    private String level;
    private String cr_cadre;
    
    private StuRegister stuRegister;
    private Staff staff;
    
    public ClubRecord() {}
    public ClubRecord(Integer cr_num, Integer club_num, Integer sbj_year, Character sbj_sem, Integer rgno,
            Date cr_sdate, Date cr_edate, String cr_detail, Integer cr_hours, String staff_code, Date cr_time) {
        this.cr_num = cr_num;
        this.club_num = club_num;
        this.sbj_year = sbj_year;
        this.sbj_sem = sbj_sem;
        this.rgno = rgno;
        this.cr_sdate = cr_sdate;
        this.cr_edate = cr_edate;
        this.cr_detail = cr_detail;
        this.cr_hours = cr_hours;
        this.staff_code = staff_code;
        this.cr_time = cr_time;
    }
    
    public Integer getCr_num() {
        return cr_num;
    }
    public void setCr_num(Integer cr_num) {
        this.cr_num = cr_num;
    }
    public Integer getClub_num() {
        return club_num;
    }
    public void setClub_num(Integer club_num) {
        this.club_num = club_num;
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
    public Date getCr_sdate() {
        return cr_sdate;
    }
    public void setCr_sdate(Date cr_sdate) {
        this.cr_sdate = cr_sdate;
    }
    public Date getCr_edate() {
        return cr_edate;
    }
    public void setCr_edate(Date cr_edate) {
        this.cr_edate = cr_edate;
    }
    public String getCr_detail() {
        return cr_detail;
    }
    public void setCr_detail(String cr_detail) {
        this.cr_detail = cr_detail;
    }
    public Integer getCr_hours() {
        return cr_hours;
    }
    public void setCr_hours(Integer cr_hours) {
        this.cr_hours = cr_hours;
    }
    public String getStaff_code() {
        return staff_code;
    }
    public void setStaff_code(String staff_code) {
        this.staff_code = staff_code;
    }
    public Date getCr_time() {
        return cr_time;
    }
    public void setCr_time(Date cr_time) {
        this.cr_time = cr_time;
    }
    
    public StuRegister getStuRegister() {
        return stuRegister;
    }
    public void setStuRegister(StuRegister stuRegister) {
        this.stuRegister = stuRegister;
    }
    public Staff getStaff() {
        return staff;
    }
    public void setStaff(Staff staff) {
        this.staff = staff;
    }
    
    
    public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getCr_cadre() {
		return cr_cadre;
	}
	public void setCr_cadre(String cr_cadre) {
		this.cr_cadre = cr_cadre;
	}
	@Override
    public String toString() {
        return "ClubRecord [cr_num=" + cr_num + ", club_num=" + club_num + ", sbj_year=" + sbj_year + ", sbj_sem="
                + sbj_sem + ", rgno=" + rgno + ", cr_sdate=" + cr_sdate + ", cr_edate=" + cr_edate + ", cr_detail="
                + cr_detail + ", cr_hours=" + cr_hours + ", staff_code=" + staff_code + ", cr_time=" + cr_time + "]";
    }
    @Override
    public int hashCode() {
        final int prime = 41;
        int result = 11;
        result = prime * result + ((club_num == null) ? 0 : club_num.hashCode());
        result = prime * result + ((cr_detail == null) ? 0 : cr_detail.hashCode());
        result = prime * result + ((cr_edate == null) ? 0 : cr_edate.hashCode());
        result = prime * result + ((cr_hours == null) ? 0 : cr_hours.hashCode());
        result = prime * result + ((cr_num == null) ? 0 : cr_num.hashCode());
        result = prime * result + ((cr_sdate == null) ? 0 : cr_sdate.hashCode());
        result = prime * result + ((cr_time == null) ? 0 : cr_time.hashCode());
        result = prime * result + ((rgno == null) ? 0 : rgno.hashCode());
        result = prime * result + ((sbj_sem == null) ? 0 : sbj_sem.hashCode());
        result = prime * result + ((sbj_year == null) ? 0 : sbj_year.hashCode());
        result = prime * result + ((staff_code == null) ? 0 : staff_code.hashCode());
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
        ClubRecord other = (ClubRecord) obj;
        if (club_num == null) {
            if (other.club_num != null)
                return false;
        } else if (!club_num.equals(other.club_num))
            return false;
        if (cr_detail == null) {
            if (other.cr_detail != null)
                return false;
        } else if (!cr_detail.equals(other.cr_detail))
            return false;
        if (cr_edate == null) {
            if (other.cr_edate != null)
                return false;
        } else if (!cr_edate.equals(other.cr_edate))
            return false;
        if (cr_hours == null) {
            if (other.cr_hours != null)
                return false;
        } else if (!cr_hours.equals(other.cr_hours))
            return false;
        if (cr_num == null) {
            if (other.cr_num != null)
                return false;
        } else if (!cr_num.equals(other.cr_num))
            return false;
        if (cr_sdate == null) {
            if (other.cr_sdate != null)
                return false;
        } else if (!cr_sdate.equals(other.cr_sdate))
            return false;
        if (cr_time == null) {
            if (other.cr_time != null)
                return false;
        } else if (!cr_time.equals(other.cr_time))
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
        if (staff_code == null) {
            if (other.staff_code != null)
                return false;
        } else if (!staff_code.equals(other.staff_code))
            return false;
        return true;
    }
    
    
    // need to rewrite
    
    private Club club;

    public Club getClub() {
        return club;
    }
    public void setClub(Club club) {
        this.club = club;
    }
    
    
}
