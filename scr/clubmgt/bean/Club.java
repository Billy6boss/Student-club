package ntpc.ccai.clubmgt.bean;

public class Club {
    private Integer club_num;
    private Integer cat_num;
    private String club_name;
    private String club_code;
    private String club_info;
    private Byte sex;
    private String url;
    
    private ClubCategory clubCategory;
    
    public Club() {}
    public Club(Integer club_num, Integer cat_num, String club_name, String club_code, String club_info, Byte sex,
            String url) {
        this.club_num = club_num;
        this.cat_num = cat_num;
        this.club_name = club_name;
        this.club_code = club_code;
        this.club_info = club_info;
        this.sex = sex;
        this.url = url;
    }
    
    public Integer getClub_num() {
        return club_num;
    }
    public void setClub_num(Integer club_num) {
        this.club_num = club_num;
    }
    public Integer getCat_num() {
        return cat_num;
    }
    public void setCat_num(Integer cat_num) {
        this.cat_num = cat_num;
    }
    public String getClub_name() {
        return club_name;
    }
    public void setClub_name(String club_name) {
        this.club_name = club_name;
    }
    public String getClub_code() {
        return club_code;
    }
    public void setClub_code(String club_code) {
        this.club_code = club_code;
    }
    public String getClub_info() {
        return club_info;
    }
    public void setClub_info(String club_info) {
        this.club_info = club_info;
    }
    public Byte getSex() {
        return sex;
    }
    public void setSex(Byte sex) {
        this.sex = sex;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    
    public ClubCategory getClubCategory() {
        return clubCategory;
    }
    public void setClubCategory(ClubCategory clubCategory) {
        this.clubCategory = clubCategory;
    }
    @Override
    public String toString() {
        return "Club [club_num=" + club_num + ", cat_num=" + cat_num + ", club_name=" + club_name + ", club_code="
                + club_code + ", club_info=" + club_info + ", sex=" + sex + ", url=" + url + "]";
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 7;
        result = prime * result + ((cat_num == null) ? 0 : cat_num.hashCode());
        result = prime * result + ((club_code == null) ? 0 : club_code.hashCode());
        result = prime * result + ((club_info == null) ? 0 : club_info.hashCode());
        result = prime * result + ((club_name == null) ? 0 : club_name.hashCode());
        result = prime * result + ((club_num == null) ? 0 : club_num.hashCode());
        result = prime * result + ((sex == null) ? 0 : sex.hashCode());
        result = prime * result + ((url == null) ? 0 : url.hashCode());
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
        Club other = (Club) obj;
        if (cat_num == null) {
            if (other.cat_num != null)
                return false;
        } else if (!cat_num.equals(other.cat_num))
            return false;
        if (club_code == null) {
            if (other.club_code != null)
                return false;
        } else if (!club_code.equals(other.club_code))
            return false;
        if (club_info == null) {
            if (other.club_info != null)
                return false;
        } else if (!club_info.equals(other.club_info))
            return false;
        if (club_name == null) {
            if (other.club_name != null)
                return false;
        } else if (!club_name.equals(other.club_name))
            return false;
        if (club_num == null) {
            if (other.club_num != null)
                return false;
        } else if (!club_num.equals(other.club_num))
            return false;
        if (sex == null) {
            if (other.sex != null)
                return false;
        } else if (!sex.equals(other.sex))
            return false;
        if (url == null) {
            if (other.url != null)
                return false;
        } else if (!url.equals(other.url))
            return false;
        return true;
    }
    
}
