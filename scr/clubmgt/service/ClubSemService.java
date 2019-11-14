package ntpc.ccai.clubmgt.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import ntpc.ccai.clubmgt.bean.Club;
import ntpc.ccai.clubmgt.bean.ClubSem;
import ntpc.ccai.clubmgt.bean.ClubTeaching;
import ntpc.ccai.clubmgt.bean.ClubTime;
import ntpc.ccai.clubmgt.bean.DBCon;
import ntpc.ccai.clubmgt.dao.ClubSemDao;
import ntpc.ccai.clubmgt.dao.impl.ClubSemDaoImpl;

public class ClubSemService {
    private static final Logger logger = Logger.getLogger(ClubSemService.class);

    private ClubSemDao clubSemDao;
    private ClubService clubService = new ClubService();
    private ClubTeachingService clubTeachingService = new ClubTeachingService();
    private ClubTimeService clubTimeService = new ClubTimeService();
    private ClubSelectionService clubSelectionService = new ClubSelectionService();
    private ClubMemberService clubMemberService = new ClubMemberService();
    private ClubRecordService clubRecordService = new ClubRecordService();

    public ClubSemService() {
        clubSemDao = new ClubSemDaoImpl();
    }

    public ClubSemService(ClubSemDao clubSemDao) {
        this.clubSemDao = clubSemDao;
    }

    public Boolean insert(String sch_code, ClubSem clubSem, Set<ClubTeaching> clubTeachings, Set<ClubTime> clubTimes) {
        Boolean result = false;
        DBCon dbcon = null;
        Connection conn = null;

        try {
            dbcon = new DBCon(sch_code);
            conn = dbcon.getConnection();
            if (conn != null) {
                conn.setAutoCommit(false);
                
                Club club = clubSem.getClub();
                Integer club_num = club.getClub_num();
                
                if (club_num != null) {
                    result = clubService.update(conn, club);
                } else {
                    club = clubService.insert(conn, club);
                    result = club != null && club.getClub_num() != null;
                }
                        
                if (result) { 
                    club_num = club.getClub_num();
                    clubSem.setClub_num(club_num);
                    result = clubSemDao.insert(conn, clubSem);

                    if (result) {
                        if (clubTeachings != null) { 
                            for (ClubTeaching clubTeaching : clubTeachings) {
                                clubTeaching.setClub_num(club_num);
                                result = clubTeachingService.insert(conn, clubTeaching);
                                if (!result) {
                                    break;
                                }
                            }
                        }

                        if (result && clubTimes != null) {
                            for (ClubTime clubTime : clubTimes) {
                                clubTime.setClub_num(club_num);
                                result = clubTimeService.insert(conn, clubTime);
                                if (!result) {
                                    break;
                                }
                            }
                        }
                    }
                }

                if (result) {
                    conn.commit();
                } else {
                    conn.rollback();
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

    public Boolean update(String sch_code, ClubSem clubSem, Set<ClubTeaching> clubTeachings, Set<ClubTime> clubTimes) {
        Boolean result = false;
        DBCon dbcon = null;
        Connection conn = null;

        try {
            dbcon = new DBCon(sch_code);
            conn = dbcon.getConnection();
            if (conn != null) {
                conn.setAutoCommit(false);
                result = clubService.update(conn, clubSem.getClub());
                if (result) {
                    result = clubSemDao.update(conn, clubSem);

                    if (result) {
                        result = clubTeachingService.update(conn, clubTeachings,
                                clubTeachingService.getClubTeachingByClubNumAndYearAndSem(sch_code,
                                        clubSem.getClub_num(), clubSem.getSbj_year(), clubSem.getSbj_sem()));

                        if (result) {
                            result = clubTimeService.update(conn, clubTimes, clubTimeService.getClubTimeByClubNumAndYearAndSem(sch_code,
                                        clubSem.getClub_num(), clubSem.getSbj_year(), clubSem.getSbj_sem()));
                        }
                    }
                }

                if (result) {
                    conn.commit();
                } else {
                    conn.rollback();
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

    public Boolean delete(String sch_code, Integer club_num, Integer sbj_year, Character sbj_sem) {
        Boolean result = false;
        DBCon dbcon = null;
        Connection conn = null;

        try {
            dbcon = new DBCon(sch_code);
            conn = dbcon.getConnection();

            if (conn != null) {
                conn.setAutoCommit(false);
                Integer i = clubTimeService.delete(conn, club_num, sbj_year, sbj_sem);
                result = i != null && i >= 0;
                
                if (result) {
                    i = clubSelectionService.delete(conn, club_num, sbj_year, sbj_sem);
                    result = i != null && i >= 0;
                    
                    if (result) {
                        i = clubRecordService.delete(conn, club_num, sbj_year, sbj_sem);
                        result = i != null && i >= 0;
                        
                        if (result) {
                            i = clubMemberService.delete(conn, club_num, sbj_year, sbj_sem);
                            result = i != null && i >= 0;
                            
                            if (result) {
                                i = clubTeachingService.delete(conn, club_num, sbj_year, sbj_sem);
                                result = i != null && i >= 0;
                                
                                if (result) {
                                    result = clubSemDao.delete(conn, club_num, sbj_year, sbj_sem); 
                                }
                            }
                        }
                    }
                }
            }
            if (result) {
                conn.commit();
            } else {
                conn.rollback();
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

    public ClubSem getClubSemByClubNumAndYearAndSem(String sch_code, Integer club_num, Integer sbj_year,
            Character sbj_sem) {
        ClubSem result = null;
        DBCon dbcon = null;
        Connection conn = null;

        try {
            dbcon = new DBCon(sch_code);
            conn = dbcon.getConnection();
            if (conn != null) {
                result = clubSemDao.getClubSemByClubNumAndYearAndSem(conn, club_num, sbj_year, sbj_sem);
            }
        } finally {
            if (dbcon != null) {
                dbcon.closeCon();
            }
        }

        return result;
    }

    public List<ClubSem> getClubSemsByClubNameAndYearAndSem(String sch_code, String club_name, Integer sbj_year,
            Character sbj_sem) {
        List<ClubSem> result = null;
        DBCon dbcon = null;
        Connection conn = null;

        try {
            dbcon = new DBCon(sch_code);
            conn = dbcon.getConnection();
            if (conn != null) {
                result = clubSemDao.getClubSemsByClubNameAndYearAndSem(conn, club_name, sbj_year, sbj_sem);
            }
        } finally {
            if (dbcon != null) {
                dbcon.closeCon();
            }
        }

        return result;
    }

    public List<ClubSem> getClubSemsByClubCodeAndYearAndSem(String sch_code, String club_code, Integer sbj_year,
            Character sbj_sem) {
        List<ClubSem> result = null;
        DBCon dbcon = null;
        Connection conn = null;

        try {
            dbcon = new DBCon(sch_code);
            conn = dbcon.getConnection();
            if (conn != null) {
                result = clubSemDao.getClubSemsByClubCodeAndYearAndSem(conn, club_code, sbj_year, sbj_sem);
            }
        } finally {
            if (dbcon != null) {
                dbcon.closeCon();
            }
        }

        return result;
    }

    public List<ClubSem> getClubSemsByClubCategoryAndYearAndSem(String sch_code, Integer cat_num, Integer sbj_year,
            Character sbj_sem) {
        List<ClubSem> result = null;
        DBCon dbcon = null;
        Connection conn = null;

        try {
            dbcon = new DBCon(sch_code);
            conn = dbcon.getConnection();
            if (conn != null) {
                result = clubSemDao.getClubSemsByClubCategoryAndYearAndSem(conn, cat_num, sbj_year, sbj_sem);
            }
        } finally {
            if (dbcon != null) {
                dbcon.closeCon();
            }
        }

        return result;
    }

    public List<ClubSem> getClubSemsByYearAndSem(String sch_code, Integer sbj_year, Character sbj_sem) {
        List<ClubSem> result = null;
        DBCon dbcon = null;
        Connection conn = null;

        try {
            dbcon = new DBCon(sch_code);
            conn = dbcon.getConnection();
            if (conn != null) {
                result = clubSemDao.getClubSemsByYearAndSem(conn, sbj_year, sbj_sem);
            }
        } finally {
            if (dbcon != null) {
                dbcon.closeCon();
            }
        }

        return result;
    }
    
    public Map<Integer, Set<Character>> getClubYearsAndSemesters(String sch_code) {
        Map<Integer, Set<Character>> result = null;
        DBCon dbcon = null;
        Connection conn = null;

        try {
            dbcon = new DBCon(sch_code);
            conn = dbcon.getConnection();
            if (conn != null) {
                result = clubSemDao.getClubYearsAndSemesters(conn);
            }
        } finally {
            if (dbcon != null) {
                dbcon.closeCon();
            }
        }

        return result;
    }
    
    public Integer copy(Connection conn, Integer from_year, Character from_sem, Integer to_year, Character to_sem, Set<Integer> club_nums) {
        Integer result = null;

        if (conn != null) {
            result = clubSemDao.copy(conn, from_year, from_sem, to_year, to_sem, club_nums);
        } else {
            throw new IllegalStateException("No existing connection.");
        }

        return result;
    }
    
    public Integer copy(String sch_code, Integer from_year, Character from_sem, Integer to_year, Character to_sem, Set<Integer> club_nums, boolean member, boolean teaching, boolean time) {
        Integer result = null;
        Integer i = 0;
        DBCon dbcon = null;
        Connection conn = null;

        try {
            dbcon = new DBCon(sch_code);
            conn = dbcon.getConnection();
            if (conn != null) {
                conn.setAutoCommit(false);
                result = copy(conn, from_year, from_sem, to_year, to_sem, club_nums);
                if (result != null && result > 0) {
                    if (member) {
                        i = clubMemberService.copy(conn, from_year, from_sem, to_year, to_sem, club_nums);
                    }
                    
                    if (i != null && i >= 0) {
                        if (teaching) {
                            i = clubTeachingService.copy(conn, from_year, from_sem, to_year, to_sem, club_nums);
                        } 
                        
                        if (i != null && i >= 0) {
                            if (time) {
                                i = clubTimeService.copy(conn, from_year, from_sem, to_year, to_sem, club_nums);
                            }
                        }
                    }
                }
                
                if (result != null && result > 0 && i != null && i >= 0) {
                    conn.commit();
                } else {
                    conn.rollback();
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
    
    
    
    // need to rewrite
    public List<ClubSem> getClubYearList(String sch_code){
        List<ClubSem> result = null;
        DBCon dbcon = null;
        
        try {
            dbcon = new DBCon(sch_code);
            result = clubSemDao.getClubYearList(dbcon.getConnection());
        } finally {
            if (dbcon != null) {
                dbcon.closeCon();
            }
        }
        
        return result;
    }
    
    public List<ClubSem> getClubSemList(String sch_code){
        List<ClubSem> result = null;
        DBCon dbcon = null;
        
        try {
            dbcon = new DBCon(sch_code);
            result = clubSemDao.getClubSemList(dbcon.getConnection());
        } finally {
            if (dbcon != null) {
                dbcon.closeCon();
            }
        }
        
        return result;
    }
    
    public List<ClubSem> getClubDataList(String sch_code , Integer sbj_year, Character sbj_sem){
        List<ClubSem> result = null;
        DBCon dbcon = null;
        
        try {
            dbcon = new DBCon(sch_code);
            
            logger.debug(sbj_year);
            
            result = clubSemDao.getClubDataList(dbcon.getConnection(), sbj_year, sbj_sem);
        } finally {
            if (dbcon != null) {
                dbcon.closeCon();
            }
        }
        
        return result;
    }
    public List<ClubSem> getClubMenberScore(String sch_code , Integer club_num, Integer sbj_year, Character sbj_sem){
        List<ClubSem> result = null;
        DBCon dbcon = null;
        
        try {
            dbcon = new DBCon(sch_code);
            
            logger.debug(sbj_year);
            
            result = clubSemDao.getClubMenberScore(dbcon.getConnection(), club_num, sbj_year, sbj_sem);
        } finally {
            if (dbcon != null) {
                dbcon.closeCon();
            }
        }
        
        return result;
    }
    
    public List<ClubSem> getClubMenberScorePDF(String sch_code , Integer club_num, Integer sbj_year, Character sbj_sem,Integer rgno){
        List<ClubSem> result = null;
        DBCon dbcon = null;
        
        try {
            dbcon = new DBCon(sch_code);
            
            logger.debug(sbj_year);
            
            result = clubSemDao.getClubMenberScorePDF(dbcon.getConnection(), club_num, sbj_year, sbj_sem,rgno);
        } finally {
            if (dbcon != null) {
                dbcon.closeCon();
            }
        }
        
        return result;
    }
}
