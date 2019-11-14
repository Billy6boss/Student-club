package ntpc.ccai.clubmgt.dao;

import java.sql.Connection;

import ntpc.ccai.clubmgt.bean.StuBasis;

public interface StuBasisDao {
    StuBasis getStudentByRegno(Connection conn, String reg_no);
}
