package ntpc.ccai.clubmgt.dao;

import java.sql.Connection;
import java.util.List;

import ntpc.ccai.clubmgt.bean.ClubMemberCadre;

public interface ClubMemberCadreDao {
    Boolean insert(Connection conn, ClubMemberCadre clubMemberCadre);
    Boolean delete(Connection conn, ClubMemberCadre clubMemberCadre);
    Integer delete(Connection conn, Integer club_num, Integer sbj_year, Character sbj_sem, Integer rgno);
    Integer delete(Connection conn, Integer club_num, Integer sbj_year, Character sbj_sem);
    List<ClubMemberCadre> getClubMemberCadreByClubNumAndYearAndSemAndRgno(Connection conn, Integer club_num, Integer sbj_year, Character sbj_sem, Integer rgno);
    List<ClubMemberCadre> getClubMemberCadreByClubNumAndYearAndSem(Connection conn, Integer club_num, Integer sbj_year, Character sbj_sem);
    
    // need to rewrite
    List<ClubMemberCadre> getClubMemberCadreExperienceByRegnoOrIdno(Connection conn, String reg_no , String idno);
}
