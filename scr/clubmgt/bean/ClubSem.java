package ntpc.ccai.clubmgt.bean;

import java.util.HashSet;
import java.util.Set;

public class ClubSem {
    private Integer club_num;
    private Integer sbj_year;
    private Character sbj_sem;
    private Boolean grade_one;
    private Boolean grade_two;
    private Boolean grade_three;
    private String club_room;
    private Boolean selectable;
    
    private Club club;
    private Set<ClubMember> clubMembers = new HashSet<ClubMember>(0);
    
    public ClubSem() {}
    public ClubSem(Integer club_num, Integer sbj_year, Character sbj_sem, Boolean grade_one, Boolean grade_two,
            Boolean grade_three, String club_room, Boolean selectable) {
        this.club_num = club_num;
        this.sbj_year = sbj_year;
        this.sbj_sem = sbj_sem;
        this.grade_one = grade_one;
        this.grade_two = grade_two;
        this.grade_three = grade_three;
        this.club_room = club_room;
        this.selectable = selectable;
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
    public Boolean getGrade_one() {
        return grade_one;
    }
    public void setGrade_one(Boolean grade_one) {
        this.grade_one = grade_one;
    }
    public Boolean getGrade_two() {
        return grade_two;
    }
    public void setGrade_two(Boolean grade_two) {
        this.grade_two = grade_two;
    }
    public Boolean getGrade_three() {
        return grade_three;
    }
    public void setGrade_three(Boolean grade_three) {
        this.grade_three = grade_three;
    }
    public String getClub_room() {
        return club_room;
    }
    public void setClub_room(String club_room) {
        this.club_room = club_room;
    }
    public Boolean getSelectable() {
        return selectable;
    }
    public void setSelectable(Boolean selectable) {
        this.selectable = selectable;
    }
    public Club getClub() {
        return club;
    }
    public void setClub(Club club) {
        this.club = club;
    }    
    public Set<ClubMember> getClubMembers() {
        return clubMembers;
    }
    public void setClubMembers(Set<ClubMember> clubMembers) {
        this.clubMembers = clubMembers;
    }
    @Override
    public String toString() {
        return "ClubSem [club_num=" + club_num + ", sbj_year=" + sbj_year + ", sbj_sem=" + sbj_sem + ", grade_one="
                + grade_one + ", grade_two=" + grade_two + ", grade_three=" + grade_three + ", club_room=" + club_room
                + ", selectable=" + selectable + "]";
    }
    @Override
    public int hashCode() {
        final int prime = 29;
        int result = 19;
        result = prime * result + ((club_num == null) ? 0 : club_num.hashCode());
        result = prime * result + ((club_room == null) ? 0 : club_room.hashCode());
        result = prime * result + ((grade_one == null) ? 0 : grade_one.hashCode());
        result = prime * result + ((grade_three == null) ? 0 : grade_three.hashCode());
        result = prime * result + ((grade_two == null) ? 0 : grade_two.hashCode());
        result = prime * result + ((sbj_sem == null) ? 0 : sbj_sem.hashCode());
        result = prime * result + ((sbj_year == null) ? 0 : sbj_year.hashCode());
        result = prime * result + ((selectable == null) ? 0 : selectable.hashCode());
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
        ClubSem other = (ClubSem) obj;
        if (club_num == null) {
            if (other.club_num != null)
                return false;
        } else if (!club_num.equals(other.club_num))
            return false;
        if (club_room == null) {
            if (other.club_room != null)
                return false;
        } else if (!club_room.equals(other.club_room))
            return false;
        if (grade_one == null) {
            if (other.grade_one != null)
                return false;
        } else if (!grade_one.equals(other.grade_one))
            return false;
        if (grade_three == null) {
            if (other.grade_three != null)
                return false;
        } else if (!grade_three.equals(other.grade_three))
            return false;
        if (grade_two == null) {
            if (other.grade_two != null)
                return false;
        } else if (!grade_two.equals(other.grade_two))
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
        if (selectable == null) {
            if (other.selectable != null)
                return false;
        } else if (!selectable.equals(other.selectable))
            return false;
        return true;
    }
    
    
    
    // need to be removed
    private StuBasis stubasis;
    private ClubMember clubMember;

    public StuBasis getStubasis() {
        return stubasis;
    }
    public void setStubasis(StuBasis stubasis) {
        this.stubasis = stubasis;
    }
    public ClubMember getClubMember() {
        return clubMember;
    }
    public void setClubMember(ClubMember clubMember) {
        this.clubMember = clubMember;
    }
}
