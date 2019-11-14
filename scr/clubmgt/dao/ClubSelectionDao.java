package ntpc.ccai.clubmgt.dao;

import java.sql.Connection;
import java.util.List;

import ntpc.ccai.clubmgt.bean.ClubSelection;

public interface ClubSelectionDao {
    ClubSelection insert(Connection conn, ClubSelection clubSelection);
    Boolean update(Connection conn, ClubSelection clubSelection);
    Boolean delete(Connection conn, Integer cs_num);
    Integer delete(Connection conn, Integer club_num, Integer sbj_year, Character sbj_sem);
    List<ClubSelection> getClubSelectionsByClubNumAndYearAndSem(Connection conn, Integer club_num, Integer sbj_year, Character sbj_sem);
    List<ClubSelection> getClubSelectionsByYearAndSemGroups(Connection conn, Integer sbj_year,Character sbj_sem);
    
    // need to rewrite
    List<ClubSelection> getClubSelectionsOnlineByYearAndSem(Connection conn, Integer sbj_year, Character sbj_sem);
}
