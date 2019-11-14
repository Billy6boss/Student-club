package ntpc.ccai.clubmgt.bean;

public class ClubTeaching {
    private Integer club_num;
    private Integer sbj_year;
    private Character sbj_sem;
    private String staff_code;
    private String ct_code;
    
    private Club club;
    private Staff staff;
    
    public ClubTeaching() {}
    public ClubTeaching(Integer club_num, Integer sbj_year, Character sbj_sem, String staff_code) {
        this.club_num = club_num;
        this.sbj_year = sbj_year;
        this.sbj_sem = sbj_sem;
        this.staff_code = staff_code;
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
    public String getStaff_code() {
        return staff_code;
    }
    public void setStaff_code(String staff_code) {
        this.staff_code = staff_code;
    }
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
    public String getCt_code() {
        return ct_code;
    }
    public void setCt_code(String ct_code) {
        this.ct_code = ct_code;
    }
    @Override
    public String toString() {
        return "ClubTeaching [club_num=" + club_num + ", sbj_year=" + sbj_year + ", sbj_sem=" + sbj_sem
                + ", staff_code=" + staff_code + ", ct_code=" + ct_code + ", club=" + club + ", staff=" + staff + "]";
    }
    @Override
    public int hashCode() {
        final int prime = 37;
        int result = 17;
        result = prime * result + ((club_num == null) ? 0 : club_num.hashCode());
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
        ClubTeaching other = (ClubTeaching) obj;
        if (club_num == null) {
            if (other.club_num != null)
                return false;
        } else if (!club_num.equals(other.club_num))
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
    
    

}
