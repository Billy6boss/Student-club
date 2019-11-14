package ntpc.ccai.clubmgt.dao;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ntpc.ccai.clubmgt.bean.StuRegister;

public interface StuRegisterDao {
    StuRegister getStuRegistersByYearAndSemAndClsCodeAndClsNo(Connection conn, Integer sbj_year, Character sbj_sem, String cls_code, String cls_no);
    List<StuRegister> getStuRegistersByYearAndSemAndClsCode(Connection conn, Integer sbj_year, Character sbj_sem, String cls_code);
    StuRegister getStuRegisterByYearAndSemAndRegNo(Connection conn, Integer sbj_year, Character sbj_sem, String reg_no);
    StuRegister getStuRegisterByYearAndSemAndRgno(Connection conn, Integer sbj_year, Character sbj_sem, Integer rgno);
    Map<Integer, Set<Character>> getYearAndSem(Connection conn);
    Integer getRgnoByYearAndSemAndClsCodeAndClsNo(Connection conn, Integer sbj_year, Character sbj_sem, String cls_code, String cls_no);
}
