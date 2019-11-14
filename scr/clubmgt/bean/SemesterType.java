package ntpc.ccai.clubmgt.bean;

import java.util.Date;

public class SemesterType {
    private Integer sem_seq;
    private Integer sbj_year;
    private Character sbj_sem;
    private Character adg_code;
    private Date sem_sdate;
    private Date sem_edate;
    private Integer rollcall_num;
    
    public SemesterType() {}
    
    public SemesterType(Integer sem_seq, Integer sbj_year, Character sbj_sem, Character adg_code, Date sem_sdate,
            Date sem_edate, Integer rollcall_num) {
        this.sem_seq = sem_seq;
        this.sbj_year = sbj_year;
        this.sbj_sem = sbj_sem;
        this.adg_code = adg_code;
        this.sem_sdate = sem_sdate;
        this.sem_edate = sem_edate;
        this.rollcall_num = rollcall_num;
    }
    
    public Integer getSem_seq() {
        return sem_seq;
    }
    public void setSem_seq(Integer sem_seq) {
        this.sem_seq = sem_seq;
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
    public Character getAdg_code() {
        return adg_code;
    }
    public void setAdg_code(Character adg_code) {
        this.adg_code = adg_code;
    }
    public Date getSem_sdate() {
        return sem_sdate;
    }
    public void setSem_sdate(Date sem_sdate) {
        this.sem_sdate = sem_sdate;
    }
    public Date getSem_edate() {
        return sem_edate;
    }
    public void setSem_edate(Date sem_edate) {
        this.sem_edate = sem_edate;
    }
    public Integer getRollcall_num() {
        return rollcall_num;
    }
    public void setRollcall_num(Integer rollcall_num) {
        this.rollcall_num = rollcall_num;
    }
    @Override
    public int hashCode() {
        final int prime = 37;
        int result = 19;
        result = prime * result + ((adg_code == null) ? 0 : adg_code.hashCode());
        result = prime * result + ((rollcall_num == null) ? 0 : rollcall_num.hashCode());
        result = prime * result + ((sbj_sem == null) ? 0 : sbj_sem.hashCode());
        result = prime * result + ((sbj_year == null) ? 0 : sbj_year.hashCode());
        result = prime * result + ((sem_edate == null) ? 0 : sem_edate.hashCode());
        result = prime * result + ((sem_sdate == null) ? 0 : sem_sdate.hashCode());
        result = prime * result + ((sem_seq == null) ? 0 : sem_seq.hashCode());
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
        SemesterType other = (SemesterType) obj;
        if (adg_code == null) {
            if (other.adg_code != null)
                return false;
        } else if (!adg_code.equals(other.adg_code))
            return false;
        if (rollcall_num == null) {
            if (other.rollcall_num != null)
                return false;
        } else if (!rollcall_num.equals(other.rollcall_num))
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
        if (sem_edate == null) {
            if (other.sem_edate != null)
                return false;
        } else if (!sem_edate.equals(other.sem_edate))
            return false;
        if (sem_sdate == null) {
            if (other.sem_sdate != null)
                return false;
        } else if (!sem_sdate.equals(other.sem_sdate))
            return false;
        if (sem_seq == null) {
            if (other.sem_seq != null)
                return false;
        } else if (!sem_seq.equals(other.sem_seq))
            return false;
        return true;
    }
    
    @Override
    public String toString() {
        return "SemesterType [sem_seq=" + sem_seq + ", sbj_year=" + sbj_year + ", sbj_sem=" + sbj_sem + ", adg_code="
                + adg_code + ", sem_sdate=" + sem_sdate + ", sem_edate=" + sem_edate + ", rollcall_num=" + rollcall_num
                + "]";
    }
}
