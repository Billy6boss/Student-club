package ntpc.ccai.clubmgt.dao;

import java.sql.Connection;
import java.util.List;

import ntpc.ccai.clubmgt.bean.ClubCadre;

public interface ClubCadreDao {

    /**
     * 取得所有社團幹部
     * @param conn 
     * @return 含所有社團幹部的List
     */
    List<ClubCadre> getAllClubCadres(Connection conn);
    
    /**
     * 依index取得社團幹部
     * @param conn 
     * @param index 欲取得社團幹部的index
     * @return 目標社團幹部
     */
    ClubCadre getClubCadreByCadreNum(Connection conn, Integer cadre_num);
}
