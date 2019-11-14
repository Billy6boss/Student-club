package ntpc.ccai.clubmgt.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ntpc.ccai.clubmgt.bean.ClubCategory;
import ntpc.ccai.clubmgt.dao.ClubCategoryDao;

/**
 * 社團類別DAO JDBC實作
 */
public class ClubCategoryDaoImpl implements ClubCategoryDao {
    private static final String SELECT_ALL = "SELECT cat_num, cat_name FROM common.stu_club_category";
    private static final String SELECT_BY_CAT_NUM = "SELECT cat_num, cat_name FROM common.stu_club_category where cat_num = ?";
    
    private static final Logger logger = Logger.getLogger(ClubCategoryDaoImpl.class); 

    
    /**
     * 取得所有社團類別
     * @param conn 
     * @return 含所有社團類別的List
     */
    @Override
    public List<ClubCategory> getAllClubCategories(Connection conn) {
        List<ClubCategory> list = new ArrayList<ClubCategory>();
        PreparedStatement ps = null;
        ResultSet rset = null;
        
        try {
            ps = conn.prepareStatement(SELECT_ALL);
            rset = ps.executeQuery();
            while (rset.next()) {
                list.add(new ClubCategory(rset.getInt(1), rset.getString(2)));
            }
        } catch (SQLException e) {
            logger.error("社團類別DAO：全查詢錯誤" + e);
            e.printStackTrace();
        } finally {
            if (rset != null) {
                try {
                    rset.close();
                } catch (SQLException e) {
                    logger.error("社團類別DAO: ResultSet關閉錯誤" + e);
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    logger.error("社團類別DAO: PreparedStatement關閉錯誤" + e);
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    /**
     * 依index取得社團類別
     * @param conn 
     * @param index 欲取得社團類別的cat_num
     * @return 目標社團類別
     */
    @Override
    public ClubCategory getClubCategoryByCatNum(Connection conn, Integer cat_num) {
        ClubCategory clubCategory = null;
        PreparedStatement ps = null;
        ResultSet rset = null;
        
        try {
            ps = conn.prepareStatement(SELECT_BY_CAT_NUM);
            ps.setInt(1, cat_num);
            rset = ps.executeQuery();
            if (rset.next()) {
                clubCategory = new ClubCategory(rset.getInt(1), rset.getString(2));
            }
        } catch (SQLException e) {
            logger.error("社團類別DAO: 依cat_num查詢錯誤" + e);
            e.printStackTrace();
        } finally {
            if (rset != null) {
                try {
                    rset.close();
                } catch (SQLException e) {
                    logger.error("社團類別DAO: ResultSet關閉錯誤" + e);
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    logger.error("社團類別DAO: PreparedStatement關閉錯誤" + e);
                    e.printStackTrace();
                }
            }
        }
        return clubCategory;
    }

}
