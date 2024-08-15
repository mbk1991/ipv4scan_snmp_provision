package Provision;

import DAO.*;
import Util.NCutil;
import com.serotonin.bacnet4j.type.constructed.DateTime;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBUtils_mysql {
    private String sJDBCDriver;
    private String sJDBCUrl;
    private String sJDBCUser;
    private String sJDBCPasswd;

    protected Connection con = null;


    public DBUtils_mysql(String host, String port, String SID, String dbUser, String dbPasswd) {
        sJDBCDriver = "com.mysql.jdbc.Driver";
        sJDBCUrl = "jdbc:mysql://" + host + ":" + port + ":" + SID;
        sJDBCUser = dbUser;
        sJDBCPasswd = dbPasswd;
    }

    public DBUtils_mysql(String url, String dbUser, String dbPasswd) {
        sJDBCDriver = "com.mysql.jdbc.Driver";
        sJDBCUrl = url;
        sJDBCUser = dbUser;
        sJDBCPasswd = dbPasswd;
    }

    public boolean connect_db() {
        try {
            Class.forName(sJDBCDriver).newInstance();
            con = DriverManager.getConnection(sJDBCUrl, sJDBCUser, sJDBCPasswd);
            return true;
        } catch (Exception e) {
            NCutil.log_msg("ERROR: connect_db(): " + e.getMessage());
            return false;
        }
    }

    public void close_db() {
        try {
            // con.rollback();
            con.close();
        } catch (Exception e) {
        }
    }

    public List<Map<String, String>> selectNetworkListToProvision() {
        String selectQuery = new StringBuffer()
                .append(" SELECT * FROM NC_R_PROVISION_IP_T ")
                .toString();

        List<Map<String, String>> result = new ArrayList<>();
        try (Statement ps = con.createStatement();
             ResultSet rs = ps.executeQuery(selectQuery)) {
            while (rs.next()) {
                Map<String, String> m = new HashMap<>();
                m.put("flag", rs.getString("flag"));
                m.put("start_ip", rs.getString("start_ip"));
                m.put("end_ip", rs.getString("end_ip"));
                m.put("cidr", rs.getString("cidr"));
                result.add(m);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public List<String> selectCommunitysToProvision() {
        String selectQuery = new StringBuffer()
                .append(" SELECT * FROM NC_R_PROVISION_SNMP_COMMUNITY_T ")
                .toString();

        List<String> result = new ArrayList<>();
        try (Statement ps = con.createStatement();
             ResultSet rs = ps.executeQuery(selectQuery)) {
            while (rs.next()) {
                result.add(rs.getString("community"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public List<String> selectManageDeviceIpList() {
        String selectQuery = new StringBuffer()
                .append(" SELECT IFIP FROM NC_R_DEVICE_T ")
                .toString();

        List<String> result = new ArrayList<>();
        try (Statement ps = con.createStatement();
             ResultSet rs = ps.executeQuery(selectQuery)) {
            while (rs.next()) {
                result.add(rs.getString("IFIP"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public void insertProvisionHostList(List<HostStatus> allHostStatus) {
        String deleteQuery = new StringBuffer()
                .append(" DELETE FROM NC_R_PROVISION_LIST_T ")
                .toString();

        String insertQuery = new StringBuffer()
                .append(" INSERT INTO NC_R_PROVISION_LIST_T VALUES(?,?,?,?,?) ")
                .toString();

        try (Statement del = con.createStatement();
             PreparedStatement ins = con.prepareStatement(insertQuery)) {
            con.setAutoCommit(false);
            del.execute(deleteQuery);
            for (HostStatus h : allHostStatus) {
                ins.setString(1, h.getIp());
                ins.setInt(2, h.isReachable() ? 1 : 0);
                ins.setInt(3, h.isSuccessSnmp() ? 1 : 0);
                ins.setString(4, h.getSnmpCommunity());
                ins.setString(5, timeStamp("yyyyMMddHHmmss"));
                ins.addBatch();

                ins.executeBatch();
            }
            con.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private String timeStamp(String format) {
        return LocalDateTime.now(ZoneId.of("Asia/Seoul")).format(DateTimeFormatter.ofPattern(format));
    }
}
