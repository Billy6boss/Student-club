package ntpc.ccai.clubmgt.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ntpc.ccai.clubmgt.bean.ClubCadre;
import ntpc.ccai.clubmgt.dao.ClubCadreDao;

public class ClubCadreDaoImpl implements ClubCadreDao {
    private static final String SELECT_ALL = "SELECT cadre_num, cadre_name FROM common.stu_club_cadre";
    private static final String SELECT_BY_CADRE_NUM = "SELECT cadre_num, cadre_name FROM common.stu_club_cadre WHERE cadre_num = ?";
    private static final Logger logger = Logger.getLogger(ClubCadreDaoImpl.class);
    
    @Override
    public List<ClubCadre> getAllClubCadres(Connection conn) {
        List<ClubCadre> result = new ArrayList<ClubCadre>();
        PreparedStatement ps = null;
        ResultSet rset = null;
        
        try {
            ps = conn.prepareStatement(SELECT_ALL);
            rset = ps.executeQuery();
            while (rset.next()) {
                result.add(new ClubCadre(rset.getInt(1), rset.getString(2)));
            }
        } catch (SQLException e) {
            logger.error(e);
            e.printStackTrace();
        } finally {
            if (rset != null) {
                try {
                    rset.close();
                } catch (SQLException e) {
                    logger.error(e);
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    logger.error(e);
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    @Override
    public ClubCadre getClubCadreByCadreNum(Connection conn, Integer cadre_num) {
        ClubCadre result = null;
        PreparedStatement ps = null;
        ResultSet rset = null;
        
        try {
            ps = conn.prepareStatement(SELECT_BY_CADRE_NUM);
            ps.setInt(1, cadre_num);
            rset = ps.executeQuery();
            if (rset.next()) {
                result = new ClubCadre(rset.getInt(1), rset.getString(2));
            }
        } catch (SQLException e) {
            logger.error(e);
            e.printStackTrace();
        } finally {
            if (rset != null) {
                try {
                    rset.close();
                } catch (SQLException e) {
                    logger.error(e);
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    logger.error(e);
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

}
