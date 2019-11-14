package ntpc.ccai.clubmgt.bean;

public class ClubMemberCadre {
    private Integer club_num;
    private Integer sbj_year;
    private Character sbj_sem;
    private Integer rgno;
    private Integer cadre_num;
    
    private ClubCadre clubCadre;
    private ClubMember clubMember;
    
    public ClubMemberCadre() {}

    public ClubMemberCadre(Integer club_num, Integer sbj_year, Character sbj_sem, Integer rgno, Integer cadre_num) {
        this.club_num = club_num;
        this.sbj_year = sbj_year;
        this.sbj_sem = sbj_sem;
        this.rgno = rgno;
        this.cadre_num = cadre_num;
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

    public Integer getCadre_num() {
        return cadre_num;
    }

    public void setCadre_num(Integer cadre_num) {
        this.cadre_num = cadre_num;
    }
    
    public ClubCadre getClubCadre() {
        return clubCadre;
    }

    public void setClubCadre(ClubCadre clubCadre) {
        this.clubCadre = clubCadre;
    }

    public ClubMember getClubMember() {
        return clubMember;
    }

    public void setClubMember(ClubMember clubMember) {
        this.clubMember = clubMember;
    }

    @Override
    public String toString() {
        return "ClubMemberCadre [club_num=" + club_num + ", sbj_year=" + sbj_year + ", sbj_sem=" + sbj_sem + ", rgno="
                + rgno + ", cadre_num=" + cadre_num + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 47;
        int result = 13;
        result = prime * result + ((cadre_num == null) ? 0 : cadre_num.hashCode());
        result = prime * result + ((club_num == null) ? 0 : club_num.hashCode());
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
        ClubMemberCadre other = (ClubMemberCadre) obj;
        if (cadre_num == null) {
            if (other.cadre_num != null)
                return false;
        } else if (!cadre_num.equals(other.cadre_num))
            return false;
        if (club_num == null) {
            if (other.club_num != null)
                return false;
        } else if (!club_num.equals(other.club_num))
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

    // need to rewrite
    
    private StuBasis stuBasis;
    private StuClass stuClass;
    private Club club;

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

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }
}
