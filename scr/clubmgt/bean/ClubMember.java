package ntpc.ccai.clubmgt.bean;

import java.util.HashSet;
import java.util.Set;

public class ClubMember {
    private Integer club_num;
    private Integer sbj_year;
    private Character sbj_sem;
    private Integer rgno;
    private Integer club_score;
    private String issue_code;
    private String ex_code;
    
    private StuRegister stuRegister;
    private Set<ClubMemberCadre> clubMemberCadre = new HashSet<ClubMemberCadre>(0);

    public ClubMember() {}
    
    public ClubMember(Integer club_num, Integer sbj_year, Character sbj_sem, Integer rgno, Integer club_score,
            String issue_code, String ex_code) {
        this.club_num = club_num;
        this.sbj_year = sbj_year;
        this.sbj_sem = sbj_sem;
        this.rgno = rgno;
        this.club_score = club_score;
        this.issue_code = issue_code;
        this.ex_code = ex_code;
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

    public Integer getClub_score() {
        return club_score;
    }

    public void setClub_score(Integer club_score) {
        this.club_score = club_score;
    }

    public StuRegister getStuRegister() {
        return stuRegister;
    }

    public void setStuRegister(StuRegister stuRegister) {
        this.stuRegister = stuRegister;
    }

    public Set<ClubMemberCadre> getClubMemberCadre() {
        return clubMemberCadre;
    }

    public void setClubMemberCadre(Set<ClubMemberCadre> clubMemberCadre) {
        this.clubMemberCadre = clubMemberCadre;
    }

    public String getIssue_code() {
        return issue_code;
    }

    public void setIssue_code(String issue_code) {
        this.issue_code = issue_code;
    }

    public String getEx_code() {
        return ex_code;
    }

    public void setEx_code(String ex_code) {
        this.ex_code = ex_code;
    }

    @Override
    public int hashCode() {
        final int prime = 29;
        int result = 13;
        result = prime * result + ((club_num == null) ? 0 : club_num.hashCode());
        result = prime * result + ((club_score == null) ? 0 : club_score.hashCode());
        result = prime * result + ((rgno == null) ? 0 : rgno.hashCode());
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
        ClubMember other = (ClubMember) obj;
        if (club_num == null) {
            if (other.club_num != null)
                return false;
        } else if (!club_num.equals(other.club_num))
            return false;
        if (club_score == null) {
            if (other.club_score != null)
                return false;
        } else if (!club_score.equals(other.club_score))
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
        return true;
    }

    @Override
    public String toString() {
        return "ClubMember [club_num=" + club_num + ", sbj_year=" + sbj_year + ", sbj_sem=" + sbj_sem + ", rgno=" + rgno
                + ", club_score=" + club_score + ", issue_code=" + issue_code + ", ex_code=" + ex_code + "]";
    }
    
    // need to rewrite
    
    private Club club;
    private ClubCadre clubCadre;

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public ClubCadre getClubCadre() {
        return clubCadre;
    }

    public void setClubCadre(ClubCadre clubCadre) {
        this.clubCadre = clubCadre;
    }
    
}
