package ntpc.ccai.clubmgt.dao;

import java.sql.Connection;
import java.util.List;

import ntpc.ccai.clubmgt.bean.ClubCategory;

/**
 * 社團類別DAO介面
 */
public interface ClubCategoryDao {
    
    /**
     * 取得所有社團類別
     * @param conn 
     * @return 含所有社團類別的List
     */
    List<ClubCategory> getAllClubCategories(Connection conn);
    
    /**
     * 依index取得社團類別
     * @param conn 
     * @param index 欲取得社團類別的cat_num
     * @return 目標社團類別
     */
    ClubCategory getClubCategoryByCatNum(Connection conn, Integer cat_num);
}
