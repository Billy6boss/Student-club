package ntpc.ccai.clubmgt.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import ntpc.ccai.clubmgt.bean.ClubSelection;
import ntpc.ccai.clubmgt.bean.ClubSelectionClass;
import ntpc.ccai.clubmgt.bean.ClubSelectionClub;
import ntpc.ccai.clubmgt.bean.DBCon;
import ntpc.ccai.clubmgt.dao.ClubSelectionDao;
import ntpc.ccai.clubmgt.dao.impl.ClubSelectionDaoImpl;

public class ClubSelectionService {
    private static final Logger logger = Logger.getLogger(ClubSelectionService.class);
    private ClubSelectionDao clubSelectionDao;
    private ClubSelectionClassService clubSelectionClassService = new ClubSelectionClassService();
    private ClubSelectionClubService clubSelectionClubService = new ClubSelectionClubService();
    
    public ClubSelectionService() {
        clubSelectionDao = new ClubSelectionDaoImpl();
    }
    
    public ClubSelectionService(ClubSelectionDao clubSelectionDao) {
        this.clubSelectionDao = clubSelectionDao;
    }
    
    //List insert
    public boolean insert(String sch_code, ClubSelection selection) {
    	
    	DBCon dbcon =null;
    	Connection conn = null;
    	boolean result = false;
    	
    	
    	try {
    		dbcon = new DBCon(sch_code);
    		conn = dbcon.getConnection();
    		
    		if(conn != null) {
    			conn.setAutoCommit(false);
    			
    				selection = clubSelectionDao.insert(conn, selection);
    				result = selection != null && selection.getCs_num() != null;
    				
    				//if SQL error ? break : inset cs_num to clubselectioncalss
    				if(result) {
      					int cs_num = selection.getCs_num();
      					
      					for(ClubSelectionClass clubSelectionClass : selection.getClubSelectionClasses()) {
      						clubSelectionClass.setCs_num(cs_num);
      						result = clubSelectionClassService.insert(conn, clubSelectionClass);
      						
      						if (!result) {
      							break;
      						}
      					}
      					if(result) {
	      					for(ClubSelectionClub clubSelectionClub : selection.getClubSelectionClubs()) {
	      						clubSelectionClub.setCs_num(cs_num);
	      						result = clubSelectionClubService.insert(conn, clubSelectionClub);
	      						
	      						if(!result) {
	      							break;
	      						}
	      					}
      					}
    				}
      					
    			
    			//if SQL error ? conn rollback : conn commit
    			if (!result) {
                    conn.rollback();
                } else {
                    conn.commit();
                }
    		}
    	} catch (SQLException ex) {
            logger.error(ex);
            ex.printStackTrace();

            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException e) {
                logger.error(e);
                e.printStackTrace();
            }
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                } catch (SQLException e) {
                    logger.error(e);
                    e.printStackTrace();
                }
            }
            if (dbcon != null) {
                dbcon.closeCon();
            }
        }
    	
    	return result;
    }
    
    public Boolean update(String sch_code, ClubSelection clubSelection) {
        Boolean result = false;
        DBCon dbcon = null;
        Connection conn = null;
        
        try {
            dbcon = new DBCon(sch_code);
            conn = dbcon.getConnection();
            if (conn != null) {
                result = clubSelectionDao.update(conn, clubSelection);
            }
        } finally {
            if (dbcon != null) {
                dbcon.closeCon();
            }
        }
        
        return result;
    }
    
    public Boolean delete(String sch_code, Integer cs_num) {
        Boolean result = false;
        DBCon dbcon = null;
        Connection conn = null;
        
        try {
            dbcon = new DBCon(sch_code);
            conn = dbcon.getConnection();
            
            if (conn != null) {
                conn.setAutoCommit(false);
                Integer i = clubSelectionClassService.delete(conn, cs_num);
                result =  i != null && i >= 0;
                
                if (result) {
                    result = clubSelectionDao.delete(conn, cs_num);
                } else {
                    conn.rollback();
                }
                
                if (result) {
                    conn.commit();
                } else {
                    conn.rollback();   
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            logger.error(ex);
            
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException e) {
                logger.error(e);
                e.printStackTrace();
            }
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                } catch (SQLException e) {
                    logger.error(e);
                    e.printStackTrace();
                }
            }
            if (dbcon != null) {
                dbcon.closeCon();
            }
        }
        
        return result;
    }
    
    public List<ClubSelection> getClubSelectionsByClubNumAndYearAndSem(String sch_code, Integer club_num, Integer sbj_year, Character sbj_sem) {
        List<ClubSelection> result = null;
        DBCon dbcon = null;
        Connection conn = null;
        
        try {
            dbcon = new DBCon(sch_code);
            conn = dbcon.getConnection();
            if (conn != null) {
                result = clubSelectionDao.getClubSelectionsByClubNumAndYearAndSem(conn, club_num, sbj_year, sbj_sem);
            }
        } finally {
            if (dbcon != null) {
                dbcon.closeCon();
            }
        }
        
        return result;
    }
    
    public Integer delete(Connection conn, Integer club_num, Integer sbj_year, Character sbj_sem) {
        Integer result = null;
        List<ClubSelection> clubSelections = null;
        
        if (conn != null) {
            clubSelections = clubSelectionDao.getClubSelectionsByClubNumAndYearAndSem(conn, club_num, sbj_year, sbj_sem);
            
            if (clubSelections.size() > 0) {
                for (ClubSelection clubSelection : clubSelections) {
                    result = clubSelectionClassService.delete(conn, clubSelection.getCs_num());
                   
                    if (result == null || result < 0) {
                        break;
                    }
                }
                
                if (result != null && result >= 0) {
                    result = clubSelectionDao.delete(conn, club_num, sbj_year, sbj_sem);
                }
            } else {
                result = 0;
            }
            
        } else {
            throw new IllegalStateException("No existing connection.");
        }
                
        return result;
    }
    
    public List<ClubSelection> getClubSelectionsByYearAndSemGroups(String sch_code, Integer sbj_year, Character sbj_sem){
    	List<ClubSelection> result = null;
        DBCon dbcon = null;
        try {
            dbcon = new DBCon(sch_code);
            result = clubSelectionDao.getClubSelectionsByYearAndSemGroups(dbcon.getConnection(), sbj_year, sbj_sem);
        } finally {
            if (dbcon != null) {
                dbcon.closeCon();
            }
        }
        
        return result;
    }
    
    // need to rewrite
    public List<ClubSelection> getClubSelectionsOnlineByYearAndSem(String sch_code, Integer sbj_year, Character sbj_sem) {
        List<ClubSelection> result = null;
        DBCon dbcon = null;
        
        try {
            dbcon = new DBCon(sch_code);
            result = clubSelectionDao.getClubSelectionsOnlineByYearAndSem(dbcon.getConnection(), sbj_year, sbj_sem);
        } finally {
            if (dbcon != null) {
                dbcon.closeCon();
            }
        }
        
        return result;
    }
}
