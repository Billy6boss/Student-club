package ntpc.ccai.clubmgt.dao;

import java.sql.Connection;
import java.util.List;

import ntpc.ccai.clubmgt.bean.ClubRecord;

public interface ClubRecordDao {
    ClubRecord insert(Connection conn, ClubRecord clubRecord);
    Boolean update(Connection conn, ClubRecord clubRecord);
    Boolean delete(Connection conn, Integer cr_num);
    Integer delete(Connection conn, Integer club_num, Integer sbj_year, Character sbj_sem);
    List<ClubRecord> getClubRecordsByClubNumAndYearAndSem(Connection conn, Integer club_num, Integer sbj_year, Character sbj_sem);
    
    // need to rewrite
    List<ClubRecord> getClubRecordsByRegnoAndIdno(Connection conn, String reg_no , String idno);
}
