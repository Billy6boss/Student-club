package ntpc.ccai.clubmgt.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import ntpc.ccai.clubmgt.bean.ClubMember;
import ntpc.ccai.clubmgt.bean.ClubMemberCadre;
import ntpc.ccai.clubmgt.bean.DBCon;
import ntpc.ccai.clubmgt.dao.ClubMemberDao;
import ntpc.ccai.clubmgt.dao.impl.ClubMemberDaoImpl;

public class ClubMemberService {
    private static final Logger logger = Logger.getLogger(ClubMemberService.class);

    private ClubMemberDao clubMemberDao;
    private ClubMemberCadreService clubMemberCadreService = new ClubMemberCadreService();

    public ClubMemberService() {
        clubMemberDao = new ClubMemberDaoImpl();
    }

    public ClubMemberService(ClubMemberDao clubmemberDao) {
        this.clubMemberDao = clubmemberDao;
    }

    public Boolean insert(String sch_code, List<ClubMember> clubMembers) {
        Boolean result = false;
        DBCon dbcon = null;
        Connection conn = null;

        try {
            dbcon = new DBCon(sch_code);
            conn = dbcon.getConnection();

            if (conn != null) {
                conn.setAutoCommit(false);
                for (ClubMember clubMember : clubMembers) {
                    result = clubMemberDao.insert(conn, clubMember);
                    if (result) {
                    	Set<ClubMemberCadre> clubMemberCadres = clubMember.getClubMemberCadre();
                        if (!clubMemberCadres.isEmpty()) {
                        	result = clubMemberCadreService.update(conn, clubMemberCadres, new ArrayList<>());
                        	if (!result) {
                        		break;
                        	}
                        }
                    } else {
                    	break;
                    }
                }
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

    public Boolean updateScoreAndCadre(String sch_code, List<ClubMember> clubMembers) {
        Boolean result = false;
        DBCon dbcon = null;
        Connection conn = null;

        try {
            dbcon = new DBCon(sch_code);
            conn = dbcon.getConnection();
            if (conn != null) {
                conn.setAutoCommit(false);
                for (ClubMember clubMember : clubMembers) {
                    result = clubMemberDao.update(conn, clubMember)
                            && clubMemberCadreService.update(conn, clubMember.getClubMemberCadre(),
                                    clubMemberCadreService.getClubMemberCadreByClubNumAndYearAndSemAndRgno(sch_code,
                                            clubMember.getClub_num(), clubMember.getSbj_year(), clubMember.getSbj_sem(),
                                            clubMember.getRgno()));
                    if (!result) {
                        break;
                    }
                }

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
                conn.rollback();
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

    public Boolean delete(String sch_code, Integer club_num, Integer sbj_year, Character sbj_sem, Integer rgno) {
        Boolean result = false;
        DBCon dbcon = null;
        Connection conn = null;

        try {
            dbcon = new DBCon(sch_code);
            conn = dbcon.getConnection();

            if (conn != null) {
                conn.setAutoCommit(false);
                Integer i = clubMemberCadreService.delete(conn, club_num, sbj_year, sbj_sem, rgno);
                result = i != null && i >= 0;

                if (result) {
                    result = clubMemberDao.delete(conn, club_num, sbj_year, sbj_sem, rgno);
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
                conn.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
                logger.error(e);
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

    public List<ClubMember> getClubMembersByClubNumAndYearAndSem(String sch_code, Integer club_num, Integer sbj_year,
            Character sbj_sem) {
        List<ClubMember> result = null;
        DBCon dbcon = null;
        Connection conn = null;

        try {
            dbcon = new DBCon(sch_code);
            conn = dbcon.getConnection();
            if (conn != null) {
                result = clubMemberDao.getClubMembersByClubNumAndYearAndSem(conn, club_num, sbj_year, sbj_sem);
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

        if (conn != null) {
            result = clubMemberCadreService.delete(conn, club_num, sbj_year, sbj_sem);
            
            if (result != null && result >= 0) {
                result = clubMemberDao.delete(conn, club_num, sbj_year, sbj_sem);
            }

        } else {
            throw new IllegalStateException("No existing connection.");
        }

        return result;
    }
    
    public Integer copy(Connection conn, Integer from_year, Character from_sem, Integer to_year, Character to_sem, Set<Integer> club_nums) {
        Integer result = null;

        if (conn != null) {
            result = clubMemberDao.copy(conn, from_year, from_sem, to_year, to_sem, club_nums);
        } else {
            throw new IllegalStateException("No existing connection.");
        }

        return result;
    }

    // need to rewrite

    // 社團成員
    public List<ClubMember> getClubMembersByPDFClubNumAndYearAndSem(String sch_code, Integer club_num, Integer sbj_year,
            Character sbj_sem) {
        List<ClubMember> result = null;
        DBCon dbcon = null;

        try {
            dbcon = new DBCon(sch_code);
            result = clubMemberDao.getClubMembersByPDFClubNumAndYearAndSem(dbcon.getConnection(), club_num, sbj_year,
                    sbj_sem);
        } finally {
            if (dbcon != null) {
                dbcon.closeCon();
            }
        }

        return result;
    }

    // 社團幹部成員
    public List<ClubMember> getClubCadersByPDFClubNumAndYearAndSem(String sch_code, Integer club_num, Integer sbj_year,
            Character sbj_sem) {
        List<ClubMember> result = null;
        DBCon dbcon = null;

        try {
            dbcon = new DBCon(sch_code);
            result = clubMemberDao.getClubCadersByPDFClubNumAndYearAndSem(dbcon.getConnection(), club_num, sbj_year,
                    sbj_sem);
        } finally {
            if (dbcon != null) {
                dbcon.closeCon();
            }
        }

        return result;
    }

    // 社團成員成績
    public List<ClubMember> getClubMemberScoreByClubNumAndYearAndSem(String sch_code, Integer club_num,
            Integer sbj_year, Character sbj_sem) {
        List<ClubMember> result = null;
        DBCon dbcon = null;

        try {
            dbcon = new DBCon(sch_code);
            result = clubMemberDao.getClubMemberScoreByClubNumAndYearAndSem(dbcon.getConnection(), club_num, sbj_year,
                    sbj_sem);
        } finally {
            if (dbcon != null) {
                dbcon.closeCon();
            }
        }

        return result;
    }

    // 確認社團學生證明字號 是否存在
    public List<ClubMember> getClubMemberFontNumByClubNumAndYearAndSemAndRgno(String sch_code, Integer club_num,
            Integer sbj_year, Character sbj_sem, Integer rgno) {
        List<ClubMember> result = null;
        DBCon dbcon = null;

        try {
            dbcon = new DBCon(sch_code);
            result = clubMemberDao.getClubMemberFontNumByClubNumAndYearAndSemAndRgno(dbcon.getConnection(), club_num,
                    sbj_year, sbj_sem, rgno);
        } finally {
            if (dbcon != null) {
                dbcon.closeCon();
            }
        }

        return result;
    }

    public Boolean chkdateissue_code(String sch_code, String issue_code, Integer club_num, Integer sbj_year,
            Character sbj_sem, Integer rgno) {
        Boolean result = null;
        DBCon dbcon = null;

        try {
            dbcon = new DBCon(sch_code);
            result = clubMemberDao.chkdateissue_code(dbcon.getConnection(), issue_code, club_num, sbj_year, sbj_sem,
                    rgno);
        } finally {
            if (dbcon != null) {
                dbcon.closeCon();
            }
        }

        return result;
    }

    public Boolean updateissue_code(String sch_code, String issue_code, Integer club_num, Integer sbj_year,
            Character sbj_sem, Integer rgno) {
        Boolean result = null;
        DBCon dbcon = null;

        try {
            dbcon = new DBCon(sch_code);
            result = clubMemberDao.updateissue_code(dbcon.getConnection(), issue_code, club_num, sbj_year, sbj_sem,
                    rgno);
        } finally {
            if (dbcon != null) {
                dbcon.closeCon();
            }
        }

        return result;
    }

    public Boolean chkdateex_code(String sch_code, String ex_code, Integer club_num, Integer sbj_year,
            Character sbj_sem, Integer rgno) {
        Boolean result = null;
        DBCon dbcon = null;

        try {
            dbcon = new DBCon(sch_code);
            result = clubMemberDao.chkdateex_code(dbcon.getConnection(), ex_code, club_num, sbj_year, sbj_sem, rgno);
        } finally {
            if (dbcon != null) {
                dbcon.closeCon();
            }
        }

        return result;
    }

    public Boolean updateex_code(String sch_code, String ex_code, Integer club_num, Integer sbj_year, Character sbj_sem,
            Integer rgno) {
        Boolean result = null;
        DBCon dbcon = null;

        try {
            dbcon = new DBCon(sch_code);
            result = clubMemberDao.updateex_code(dbcon.getConnection(), ex_code, club_num, sbj_year, sbj_sem, rgno);
        } finally {
            if (dbcon != null) {
                dbcon.closeCon();
            }
        }

        return result;
    }
}
