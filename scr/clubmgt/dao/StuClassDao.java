package ntpc.ccai.clubmgt.dao;

import java.sql.Connection;
import java.util.List;

import ntpc.ccai.clubmgt.bean.StuClass;

public interface StuClassDao {
	List<StuClass> getStuClassByYearAndSem(Connection conn, Integer sbj_year,Character sbj_sem);
    List<StuClass> getStuClassesByYearAndSemAndGrade(Connection conn, Integer sbj_year, Character sbj_sem, Character grade);
    StuClass getStuClassByYearAndSemAndClsCode(Connection conn, Integer sbj_year, Character sbj_sem, String cls_code);
    
    
    // need to rewrite
    List<StuClass> getClassClubByYearAndSemAndClsCode(Connection conn, Integer sbj_year, Character sbj_sem, String cls_code);
}
