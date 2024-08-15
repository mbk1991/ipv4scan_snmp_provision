package Provision;


import DAO.*;
import Prop.PropData;
import Resource.ResourceMib;
import Snmp.NCsnmp;
import Snmp.SnmpOption;
import Snmp.SnmpValueList;
import Util.NCutil;
import Util.itsNetID;

import java.io.UnsupportedEncodingException;
import java.util.*;

public class ProvisionMain {
    private DBUtils_mysql db;
    private static String db_url;
    private static String db_user;
    private static String db_pw;


    public ProvisionMain() {
        db_url = PropData.getDbUrl();
        db_user = PropData.getDbUser();
        db_pw = PropData.getDbPasswd();
    }

    public void go() {
        NCutil.setLogFileName(makeLogFileName());
        db = new DBUtils_mysql(db_url, db_user, db_pw);
        if (!db.connect_db()) {
            NCutil.log_msg("ERROR: DB connect fail. System exit...");
            System.exit(1);
        } else {
            this.execute();
        }
        db.close_db();
    }

    private void execute() {
        NCutil.log_msg("===== [start provisioning snmp device] =====");

        List<Map<String, String>> networkList = db.selectNetworkListToProvision();
        List<String> communityList = db.selectCommunitysToProvision();

        List<HostStatus> allHostStatus = new LinkedList<>();
        for (Map<String, String> n : networkList) {
            int flag = Integer.parseInt(n.get("flag"));
            List<HostStatus> networkHostStatusList = getNetworkHostStatusList(flag, n.get("start_ip"), n.get("end_ip"), Integer.parseInt(n.get("cidr")), communityList);
            if(networkHostStatusList != null){
                allHostStatus.addAll(networkHostStatusList);
            }
        }

        //set device list
        List<String> deviceList = db.selectManageDeviceIpList();




        //set managed host
        for(int i=0; i<allHostStatus.size(); i++){
            if(deviceList.contains(allHostStatus.get(i).getIp())){
                allHostStatus.get(i).setManaged(true);
            }
        }

        NCutil.log_msg("===== [provision host status] =====");
        NCutil.log_msg("|\tIP |\tPing |\tSNMP |\tCommunity |\tManaged");
        allHostStatus.forEach(
                host->{
                    NCutil.log_msg(String.format("|\t%s |\t%b |\t%b |\t%s |\t %b\n", host.getIp(), host.isReachable(), host.isSuccessSnmp(), host.getSnmpCommunity(), host.isManaged()));
                }
        );
        NCutil.log_msg("===========================================");


        //filtering snmp fail host
        NCutil.log_msg(String.format("allScanedHost size: %d", allHostStatus.size()));

        List<HostStatus> removeList = new ArrayList<>();
        for(int i=0; i<allHostStatus.size(); i++){
            if (!allHostStatus.get(i).isSuccessSnmp() ||
                    allHostStatus.get(i).isManaged()){
                removeList.add(allHostStatus.get(i));
            }
        }
        allHostStatus.removeAll(removeList);
        NCutil.log_msg(String.format("filtered Host size: %d", allHostStatus.size()));

        if(!allHostStatus.isEmpty()){
            db.insertProvisionHostList(allHostStatus);
        }
        NCutil.log_msg("===== [end provisioning snmp device] =====");
    }

    private List<HostStatus> getNetworkHostStatusList(int flag, String sIp, String eIp, int cidr, List<String> communityList) {
        List<String> networkIpList;
        if (flag == 1) {
            networkIpList = Inet4NetworkParser.getNetworkIpList(sIp, cidr);
        } else if (flag == 2){
            networkIpList = Inet4NetworkParser.getNetworkIpListRange(sIp, eIp, cidr);
        } else{
            return null;
        }

        List<HostStatus> hostStatusList = new ArrayList<>();
        for (String host : networkIpList) {
            HostStatus hostStatus = new HostStatus();
            hostStatus.setIp(host);
            hostStatus.setReachable(HostChecker.pingCheck(host, 500));
            Optional<String> settingedCommunity = HostChecker.snmpCheck(host, communityList);
            if (settingedCommunity.isPresent()) {
                hostStatus.setSuccessSnmp(true);
                hostStatus.setSnmpCommunity(settingedCommunity.get());
            }
            hostStatusList.add(hostStatus);
        }
        return hostStatusList;
    }

    private String makeLogFileName() {
        String osName = System.getProperty("os.name");
        String path = (osName.contains("Windows") || osName.contains("Mac")) ? "./log/" : "../log/";

        return new StringBuffer().append(path)
                .append("NC_")
                .append(this.getClass().getPackage().getName())
                .append("_")
                .append(NCutil.getCurrentTime("yyyyMMdd"))
                .append(".log")
                .toString();
    }

    public static void main(String[] args) {
        new ProvisionMain().go();
        System.exit(1);
    }
}
