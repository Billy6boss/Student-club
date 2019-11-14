package ntpc.ccai.clubmgt.bean;

/**
 * 社團類別
 */
public class ClubCategory {
    private Integer cat_num;
    private String cat_name;
    
    public ClubCategory() {}
    public ClubCategory(Integer cat_num, String cat_name) {
        this.cat_num = cat_num;
        this.cat_name = cat_name;
    }
    public Integer getCat_num() {
        return cat_num;
    }
    public void setCat_num(Integer cat_num) {
        this.cat_num = cat_num;
    }
    public String getCat_name() {
        return cat_name;
    }
    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }
    @Override
    public int hashCode() {
        final int prime = 67;
        int result = 19;
        result = prime * result + ((cat_name == null) ? 0 : cat_name.hashCode());
        result = prime * result + ((cat_num == null) ? 0 : cat_num.hashCode());
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
        ClubCategory other = (ClubCategory) obj;
        if (cat_name == null) {
            if (other.cat_name != null)
                return false;
        } else if (!cat_name.equals(other.cat_name))
            return false;
        if (cat_num == null) {
            if (other.cat_num != null)
                return false;
        } else if (!cat_num.equals(other.cat_num))
            return false;
        return true;
    }
    
    
}
