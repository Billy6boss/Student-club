package ntpc.ccai.clubmgt.dao;

import java.sql.Connection;
import java.util.List;

import ntpc.ccai.clubmgt.bean.ClubSelectionClass;

public interface ClubSelectionClassDao {
    Boolean insert(Connection conn, ClubSelectionClass clubSelectionClass);
    Boolean delete(Connection conn, ClubSelectionClass clubSelectionClass);
    Integer delete(Connection conn, Integer cs_num);
    List<ClubSelectionClass> getClubSelectionClassesByCsNum(Connection conn, Integer cs_num);
}
