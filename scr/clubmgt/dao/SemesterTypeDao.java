package ntpc.ccai.clubmgt.dao;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import ntpc.ccai.clubmgt.bean.SemesterType;

public interface SemesterTypeDao {
    SemesterType getSemesterTypeByDate(Connection conn, Date date);
    List<SemesterType> getSemesterTypesByYear(Connection conn, Integer sbj_year);
    List<SemesterType> getAllSemesterTypes(Connection conn);
}
