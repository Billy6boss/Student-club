package ntpc.ccai.clubmgt.dao;

import java.sql.Connection;
import java.util.List;

import ntpc.ccai.clubmgt.bean.Club;

public interface ClubDao {
    Club insert(Connection conn, Club club);
    Boolean delete(Connection conn, Integer club_num);
    Boolean update(Connection conn, Club club);
    List<Club> getAllClubs(Connection conn);
    List<Club> getClubsByCategory(Connection conn, Integer cat_num);
    Club getClubByClubNum(Connection conn, Integer club_num);
    List<Club> getClubsByClubCode(Connection conn, String club_code);
    List<Club> getClubsByClubName(Connection conn, String club_name);
}
