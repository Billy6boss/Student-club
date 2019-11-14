package ntpc.ccai.clubmgt.dao;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ntpc.ccai.clubmgt.bean.ClubSem;

public interface ClubSemDao {
    Boolean insert(Connection conn, ClubSem clubSem);
    Boolean update(Connection conn, ClubSem clubSem);
    Boolean delete(Connection conn, Integer club_num, Integer sbj_year, Character sbj_sem);
    ClubSem getClubSemByClubNumAndYearAndSem(Connection conn, Integer club_num, Integer sbj_year, Character sbj_sem);
    List<ClubSem> getClubSemsByClubNameAndYearAndSem(Connection conn, String club_name, Integer sbj_year, Character sbj_sem);
    List<ClubSem> getClubSemsByClubCodeAndYearAndSem(Connection conn, String club_code, Integer sbj_year, Character sbj_sem);
    List<ClubSem> getClubSemsByClubCategoryAndYearAndSem(Connection conn, Integer cat_num, Integer sbj_year, Character sbj_sem);
    List<ClubSem> getClubSemsByYearAndSem(Connection conn, Integer sbj_year, Character sbj_sem);
    Map<Integer, Set<Character>> getClubYearsAndSemesters(Connection conn);
    Integer copy(Connection conn, Integer from_year, Character from_sem, Integer to_year, Character to_sem, Set<Integer> club_nums);
    
    // need to rewrite
    List<ClubSem> getClubSemList(Connection conn); //step1 學期
    List<ClubSem> getClubYearList(Connection conn); //step1 學年
    List<ClubSem> getClubDataList(Connection conn, Integer sbj_year, Character sbj_sem); //step2
    List<ClubSem> getClubMenberScore(Connection conn, Integer club_num, Integer sbj_year, Character sbj_sem); //json table
    List<ClubSem> getClubMenberScorePDF(Connection conn, Integer club_num, Integer sbj_year, Character sbj_sem, Integer rgno);
}
