package ntpc.ccai.clubmgt.dao;

import java.sql.Connection;
import java.util.List;
import java.util.Set;

import ntpc.ccai.clubmgt.bean.ClubMember;

public interface ClubMemberDao {
    Boolean insert(Connection conn, ClubMember clubMember);
    Boolean update(Connection conn, ClubMember clubMember);
    Boolean delete(Connection conn, Integer club_num, Integer sbj_year, Character sbj_sem, Integer rgno);
    Integer delete(Connection conn, Integer club_num, Integer sbj_year, Character sbj_sem);
    List<ClubMember> getClubMembersByClubNumAndYearAndSem(Connection conn, Integer club_num, Integer sbj_year, Character sbj_sem);
    Integer copy(Connection conn, Integer from_year, Character from_sem, Integer to_year, Character to_sem, Set<Integer> club_nums);
    
    // need to rewrite
    Boolean chkdateissue_code(Connection conn, String issue_code ,Integer club_num, Integer sbj_year, Character sbj_sem, Integer rgno);
    Boolean updateissue_code(Connection conn, String issue_code ,Integer club_num, Integer sbj_year, Character sbj_sem, Integer rgno);
    Boolean chkdateex_code(Connection conn, String ex_code ,Integer club_num, Integer sbj_year, Character sbj_sem, Integer rgno);
    Boolean updateex_code(Connection conn, String ex_code ,Integer club_num, Integer sbj_year, Character sbj_sem, Integer rgno);
    List<ClubMember> getClubMembersByPDFClubNumAndYearAndSem(Connection conn, Integer club_num, Integer sbj_year, Character sbj_sem);
    List<ClubMember> getClubCadersByPDFClubNumAndYearAndSem(Connection conn, Integer club_num, Integer sbj_year, Character sbj_sem);
    List<ClubMember> getClubMemberScoreByClubNumAndYearAndSem(Connection conn, Integer club_num, Integer sbj_year, Character sbj_sem);
    List<ClubMember> getClubMemberFontNumByClubNumAndYearAndSemAndRgno(Connection conn, Integer club_num, Integer sbj_year, Character sbj_sem, Integer rgno);
}
