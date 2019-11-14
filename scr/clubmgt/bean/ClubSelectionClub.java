package ntpc.ccai.clubmgt.bean;

public class ClubSelectionClub {
	private Integer cs_num;
	private Integer sbj_year;
	private Character sbj_sem;
	private Integer club_num;
	
	private ClubSelection clubSelection;
	private Club Club;
	
	public ClubSelectionClub() {};
	
	public ClubSelectionClub(Integer cs_num, Integer sbj_year, Character sbj_sem, Integer club_num) {
        this.cs_num = cs_num;
        this.sbj_year = sbj_year;
        this.sbj_sem = sbj_sem;
        this.club_num = club_num;
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
	public Integer getClub_num() {
		return club_num;
	}
	public void setClub_num(Integer club_num) {
		this.club_num = club_num;
	}
	public ClubSelection getClubSelection() {
		return clubSelection;
	}

	public void setClubSelection(ClubSelection clubSelection) {
		this.clubSelection = clubSelection;
	}

	public Club getClub() {
		return Club;
	}

	public void setClub(Club club) {
		Club = club;
	}

	@Override
	public String toString() {
		return "ClubSelectionClub [cs_num=" + cs_num + ", sbj_year=" + sbj_year + ", sbj_sem=" + sbj_sem + ", club_num="
		        + club_num + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 41;
		int result = 11;
		result = prime * result + ((club_num == null) ? 0 : club_num.hashCode());
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
		ClubSelectionClub other = (ClubSelectionClub) obj;
		if (club_num == null) {
			if (other.club_num != null)
				return false;
		} else if (!club_num.equals(other.club_num))
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
