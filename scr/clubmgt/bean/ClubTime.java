package ntpc.ccai.clubmgt.bean;

public class ClubTime {
    private Integer club_num;
    private Integer sbj_year;
    private Character sbj_sem;
    private Byte sday;
    private Byte speriod;
    
    public ClubTime() {}
    public ClubTime(Integer club_num, Integer sbj_year, Character sbj_sem, Byte sday, Byte speriod) {
        this.club_num = club_num;
        this.sbj_year = sbj_year;
        this.sbj_sem = sbj_sem;
        this.sday = sday;
        this.speriod = speriod;
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
    public Byte getSday() {
        return sday;
    }
    public void setSday(Byte sday) {
        this.sday = sday;
    }
    public Byte getSperiod() {
        return speriod;
    }
    public void setSperiod(Byte speriod) {
        this.speriod = speriod;
    }
    
    @Override
    public String toString() {
        return "ClubTime [club_num=" + club_num + ", sbj_year=" + sbj_year + ", sbj_sem=" + sbj_sem + ", sday=" + sday
                + ", speriod=" + speriod + "]";
    }
    @Override
    public int hashCode() {
        final int prime = 59;
        int result = 11;
        result = prime * result + ((club_num == null) ? 0 : club_num.hashCode());
        result = prime * result + ((sbj_sem == null) ? 0 : sbj_sem.hashCode());
        result = prime * result + ((sbj_year == null) ? 0 : sbj_year.hashCode());
        result = prime * result + ((sday == null) ? 0 : sday.hashCode());
        result = prime * result + ((speriod == null) ? 0 : speriod.hashCode());
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
        ClubTime other = (ClubTime) obj;
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
        if (sday == null) {
            if (other.sday != null)
                return false;
        } else if (!sday.equals(other.sday))
            return false;
        if (speriod == null) {
            if (other.speriod != null)
                return false;
        } else if (!speriod.equals(other.speriod))
            return false;
        return true;
    }
    
    

}
