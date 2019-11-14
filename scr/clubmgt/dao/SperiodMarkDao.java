package ntpc.ccai.clubmgt.dao;

import java.sql.Connection;
import java.util.List;

import ntpc.ccai.clubmgt.bean.SperiodMark;

public interface SperiodMarkDao {
    List<SperiodMark> getAllSperiodMarks(Connection conn);
    SperiodMark getSperiodMarkBySperiod(Connection conn, Short speriod);
}
