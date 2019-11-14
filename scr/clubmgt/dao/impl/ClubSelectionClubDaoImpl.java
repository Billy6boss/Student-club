package ntpc.ccai.clubmgt.dao.impl;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ntpc.ccai.clubmgt.bean.ClubSelectionClub;
import ntpc.ccai.clubmgt.dao.ClubSelectionClubDao;
import ntpc.ccai.clubmgt.util.StringUtils;

public class ClubSelectionClubDaoImpl implements ClubSelectionClubDao{
	private static final String SELECT_BY_CS_NUM = "SELECT * FROM STU_CLUB_SELECTION_CLUB WHERE CS_NUM = ?";
	private static final String INSERT = "INSERT INTO STU_CLUB_SELECTION_CLUB(CS_NUM, SBJ_YEAR, SBJ_SEM, CLUB_NUM) 	VALUES(?, ?, ?, ?)";
	private static final String DELETE = "DELETE FROM STU_CLUB_SELECTION_CLUB WHERE CS_NUM = ? AND SBJ_YEAR = ? AND SBJ_SEM = ? AND CLUB_NUM = ?";
	private static final String DELETE_BY_CS_NUM= "DELETE FROM STU_CLUB_SELECTION_CLUB WHERE CS_NUM = ?";
	private static final String UPDATE = "";
	private static final Logger logger = Logger.getLogger(ClubSelectionClubDaoImpl.class);
	
    @Override
    public Boolean insert(Connection conn, ClubSelectionClub clubSelectionClubBean) {
        return insertOrDelete(conn, clubSelectionClubBean, INSERT);
    }

    @Override
    public Boolean delete(Connection conn, ClubSelectionClub clubSelectionClubBean) {
        return insertOrDelete(conn, clubSelectionClubBean, DELETE);
    }
    @Override
    public Integer deleteByCsNum(Connection conn,Integer cs_num) {
    	int result = -1;
    	PreparedStatement ps = null;
    	
    	try {
    		ps = conn.prepareStatement(DELETE_BY_CS_NUM);
    		ps.setInt(1, cs_num);
    		result = ps.executeUpdate();
    	} catch (SQLException sqle) {
    		sqle.printStackTrace();
     	} finally {
     		if(ps != null) {
     			try {
     				ps.close();
     			} catch (SQLException e) {
                    logger.error(e);
                    e.printStackTrace();
                }
     		}
     		
     		if(conn != null ) {
     			try {
     			conn.close();
     			} catch (SQLException e) {
                    logger.error(e);
                    e.printStackTrace();
                }
     		}
     	}
    	
    	return result;
    }
    
    @Override
    public List<ClubSelectionClub> getClubSelectionClubByCsNum(Connection conn,int cs_num) {
    	List<ClubSelectionClub> clubResltList = new ArrayList<ClubSelectionClub>();
        PreparedStatement ps = null;
        ResultSet rset = null;
        
        try {
            ps = conn.prepareStatement(SELECT_BY_CS_NUM);
            ps.setInt(1, cs_num);
            rset = ps.executeQuery();
            while (rset.next()) {
            	ClubSelectionClub clubSelectionClub = new ClubSelectionClub();
            	clubSelectionClub.setCs_num(rset.getInt("cs_num"));
            	clubSelectionClub.setSbj_year(rset.getInt("sbj_year"));
                clubSelectionClub.setSbj_sem(StringUtils.toCharacter(rset.getString("sbj_sem")));
                clubSelectionClub.setClub_num(rset.getInt("club_num"));
                clubResltList.add(clubSelectionClub);
            }
        } catch (SQLException e) {
            logger.error(e);
            e.printStackTrace();
        } finally {
            if (rset != null) {
                try {
                    rset.close();
                } catch (SQLException e) {
                    logger.error(e);
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    logger.error(e);
                    e.printStackTrace();
                }
            }
        }
        return clubResltList;
    }
    
    private Boolean insertOrDelete(Connection conn, ClubSelectionClub clubSelectionClubBean, String str) {
        Boolean result = false;
        PreparedStatement ps = null;
        
        try {
            ps = conn.prepareStatement(str);
            int i = 1;
            ps.setInt(i++, clubSelectionClubBean.getCs_num());
            ps.setInt(i++, clubSelectionClubBean.getSbj_year());
            ps.setString(i++, clubSelectionClubBean.getSbj_sem().toString());
            ps.setInt(i++, clubSelectionClubBean.getClub_num());
            result = ps.executeUpdate() == 1;
        } catch (SQLException e) {
            logger.error(e);
            e.printStackTrace();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    logger.error(e);
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

}
