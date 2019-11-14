package ntpc.ccai.clubmgt.dao;

import java.sql.Connection;
import java.util.List;
import java.util.Set;

import ntpc.ccai.clubmgt.bean.ClubTime;

public interface ClubTimeDao {
    Boolean insert(Connection conn, ClubTime clubTime);
    Boolean delete(Connection conn, ClubTime clubTime);
    Integer delete(Connection conn, Integer club_num, Integer sbj_year, Character sbj_sem);
    List<ClubTime> getClubTimeByClubNumAndYearAndSem(Connection conn, Integer club_num, Integer sbj_year, Character sbj_sem);
    Integer copy(Connection conn, Integer from_year, Character from_sem, Integer to_year, Character to_sem, Set<Integer> club_nums);
}
