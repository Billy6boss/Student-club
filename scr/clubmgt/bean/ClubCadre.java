package ntpc.ccai.clubmgt.bean;

public class ClubCadre {
    private Integer cadre_num;
    private String cadre_name;
    
    public ClubCadre() {}
    public ClubCadre(Integer cadre_num, String cadre_name) {
        this.cadre_num = cadre_num;
        this.cadre_name = cadre_name;
    }
    public Integer getCadre_num() {
        return cadre_num;
    }
    public void setCadre_num(Integer cadre_num) {
        this.cadre_num = cadre_num;
    }
    public String getCadre_name() {
        return cadre_name;
    }
    public void setCadre_name(String cadre_name) {
        this.cadre_name = cadre_name;
    }
    @Override
    public String toString() {
        return "ClubCadre [cadre_num=" + cadre_num + ", cadre_name=" + cadre_name + "]";
    }
    @Override
    public int hashCode() {
        final int prime = 71;
        int result = 29;
        result = prime * result + ((cadre_name == null) ? 0 : cadre_name.hashCode());
        result = prime * result + ((cadre_num == null) ? 0 : cadre_num.hashCode());
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
        ClubCadre other = (ClubCadre) obj;
        if (cadre_name == null) {
            if (other.cadre_name != null)
                return false;
        } else if (!cadre_name.equals(other.cadre_name))
            return false;
        if (cadre_num == null) {
            if (other.cadre_num != null)
                return false;
        } else if (!cadre_num.equals(other.cadre_num))
            return false;
        return true;
    }
    
    
}
