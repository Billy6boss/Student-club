package ntpc.ccai.clubmgt.dao;

import java.sql.Connection;
import java.util.List;
import java.util.Set;

import ntpc.ccai.clubmgt.bean.ClubTeaching;

public interface ClubTeachingDao {
    Boolean insert(Connection conn, ClubTeaching clubTeaching);
    Boolean delete(Connection conn, ClubTeaching clubTeaching);
    Integer delete(Connection conn, Integer club_num, Integer sbj_year, Character sbj_sem);
    List<ClubTeaching> getClubTeachingByClubNumAndYearAndSem(Connection conn, Integer club_num, Integer sbj_year, Character sbj_sem);
    Integer copy(Connection conn, Integer from_year, Character from_sem, Integer to_year, Character to_sem, Set<Integer> club_nums);
    
    // need to rewrite
    Boolean chkctcode(Connection conn, Integer club_num, Integer sbj_year, Character sbj_sem, String ct_code);
    Boolean updatactcode(Connection conn, Integer club_num, Integer sbj_year, Character sbj_sem, String ct_code, String staff_code);
    List<ClubTeaching> getClubTeachingByStaffcodeAndIdno(Connection conn,String staffcode , String idno, Integer sbj_year, Character sbj_sem);
}
