package ntpc.ccai.clubmgt.service;

import java.util.List;

import org.apache.log4j.Logger;

import ntpc.ccai.clubmgt.bean.ClubCategory;
import ntpc.ccai.clubmgt.bean.DBCon;
import ntpc.ccai.clubmgt.dao.ClubCategoryDao;
import ntpc.ccai.clubmgt.dao.impl.ClubCategoryDaoImpl;

/**
 * 社團類別Service
 */
public class ClubCategoryService {
    private static final Logger logger = Logger.getLogger(ClubCategoryService.class);
    
    private ClubCategoryDao clubCategoryDao;
    
    public ClubCategoryService() {
        clubCategoryDao = new ClubCategoryDaoImpl();
    }
    public ClubCategoryService(ClubCategoryDao clubCategoryDao) {
        this.clubCategoryDao = clubCategoryDao;
    }
    
    /**
     * 取得所有社團類別
     * @param pSchno 學校代號
     * @return 含所有社團類別的List
     */
    public List<ClubCategory> getAllClubCategory(String sch_code) {
        DBCon dbcon = new DBCon(sch_code);
        List<ClubCategory> result = null;
        
        try {
            result = clubCategoryDao.getAllClubCategories(dbcon.getConnection());
        } catch (Exception e) {
            logger.error("社團類別Service：取得連線錯誤" + e);
            e.printStackTrace();
        } finally {
            dbcon.closeCon();
        }
        return result;
    }
    
    /**
     * 依index取得社團類別
     * @param pSchno 學校代號
     * @param index 欲取得社團類別的index
     * @return 目標社團類別
     */
    public ClubCategory getClubCategoryByCatNum(String sch_code, Integer cat_num) {
        DBCon dbcon = new DBCon(sch_code);
        ClubCategory result = null;
        try {
            result = clubCategoryDao.getClubCategoryByCatNum(dbcon.getConnection(), cat_num);
        } catch (Exception e) {
            logger.error("社團類別Service：取得連線錯誤" + e);
            e.printStackTrace();
        } finally {
            dbcon.closeCon();
        }
        return result;
    }
}
