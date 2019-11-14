package ntpc.ccai.clubmgt.dao;

import java.sql.Connection;
import java.util.List;

import ntpc.ccai.clubmgt.bean.ClubSelectionClub;

public interface ClubSelectionClubDao {

	Boolean insert(Connection conn, ClubSelectionClub clubSelectionClubBean);
	Boolean delete(Connection conn, ClubSelectionClub clubSelectionClubBean);
	List<ClubSelectionClub> getClubSelectionClubByCsNum(Connection conn, int cs_num);
	Integer deleteByCsNum(Connection conn, Integer cs_num);
		
}