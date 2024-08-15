package Prop;

import Util.NCutil;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropData {
    private static Properties prop;
    private static String propPath;
    private static String SERVER;
    private static String DB_DRIVER;
    private static String DB_URL;
    private static String DB_USER;
    private static String DB_PASSWD;
    private static String FAMSG_SOCKET_IP;
    private static int FAMSG_SOCKET_PORT;
    private static String WEBSOCKET_IP_PORT;
    private static int KEEP_DAY;
    private static int KEEP_WEEK;
    private static int KEEP_MONTH;
    private static int KEEP_YEAR;

    //snmp setting
    private static String SNMP_PORT;
    private static String SNMP_TIMEOUT;
    private static String SNMP_RETRIES;
    private static String SNMP_VER;
    private static String SNMP_COMMUNITY;
    private static String TRAP_COMMUNITY;
    private static String SNMP_V3_USERNAME;
    private static String SNMP_V3_AUTHPASS;
    private static String SNMP_V3_AUTHPROTOCOL;
    private static String SNMP_V3_PRIVPROTOCOL;

    //quartz schedule
    private static String FA_CHECKER_CRON;
    private static String FA_DEVICE_CHECK_CRON;
    private static String FA_MSG_PROCESS_HEALTH_CRON;
    private static String FA_MSG_PROCESS_CRON;
    private static String FA_SNMP_CHK_CRON;
    private static String ARP_CHK_CRON;
    private static String PERF_NODE_CRON;
    private static String PERF_CORE_CRON;
    private static String PERF_PORT_CRON;
    private static String PERF_FS_CRON;
    private static String PERF_DEV_ICMP_CRON;
    private static String PERF_TIME_NODE_CRON;
    private static String PERF_TIME_CORE_CRON;
    private static String PERF_TIME_PORT_CRON;
    private static String PERF_TIME_ICMP_CRON;
    private static String PROCESS_CHK_CRON;
    private static String CPU_MEM_AVG_CRON;
    private static String CPU_CORE_AVG_CRON;
    private static String CPU_MEM_RANK_CRON;
    private static String CONFIG_BACKUP_CRON;

    private static String DEVICE_PER_THREAD;

    static {
        String osName = System.getProperty("os.name");
        if (osName.contains("Windows") || osName.contains("Mac")) {
            propPath = "./conf/NC_Config.properties";
        } else {
            propPath = "../conf/NC_Config.properties";
        }
        PropData.setPropPath(propPath);
        try {
            PropData.readProperties();
        } catch (IOException e) {
            NCutil.log_msg("Error: Read Properties error. ");
            System.exit(-1);
        }
    }

    private PropData(){
        //no instance;
    }

    public static void setPropPath(String path) {
        propPath = path;
    }

    public static void readProperties() throws IOException {
        prop = new Properties();
        prop.load(new FileReader(propPath));

        loadCommonInfo();
        loadDBInfo();
        loadSnmpInfo();
        loadFaultCron();
        loadPerfCron();
    }

    private static void loadCommonInfo() {
        setKeepDay(Integer.parseInt(prop.getProperty("KEEP_DAYS_OF_5MIN")));
        setKeepWeek(Integer.parseInt(prop.getProperty("KEEP_DAYS_OF_30MIN")));
        setKeepMonth(Integer.parseInt(prop.getProperty("KEEP_DAYS_OF_60MIN")));
        setKeepYear(Integer.parseInt(prop.getProperty("KEEP_DAYS_OF_1DAY")));
        setWebsocketIpPort(prop.getProperty("WEB_SOCKET_IP"));
        setFamsgSocketIp(prop.getProperty("SOCKET_IP"));
        setFamsgSocketPort(prop.getProperty("SOCKET_PORT"));
        setDevicePerThread(prop.getProperty("DEVICE_PER_THREAD"));
    }

    private static void loadSnmpInfo() {
        setSnmpPort(prop.getProperty("SNMP_PORT"));
        setSnmpTimeout(prop.getProperty("SNMP_TIMEOUT"));
        setSnmpRetries(prop.getProperty("SNMP_RETRIES"));
        setSnmpVer(prop.getProperty("SNMP_VER"));
        setSnmpCommunity(prop.getProperty("SNMP_COMMUNITY"));
        setTrapCommunity(prop.getProperty("TRAP_COMMUNITY"));
        setSnmpV3Username(prop.getProperty("SNMP_V3_USERNAME"));
        setSnmpV3Authpass(prop.getProperty("SNMP_V3_AUTHPASS"));
        setSnmpV3Authprotocol(prop.getProperty("SNMP_V3_AUTHPROTOCOL"));
        setSnmpV3Privprotocol(prop.getProperty("SNMP_V3_PRIVPROTOCOL"));
    }

    private static void loadDBInfo() {
        setSERVER(prop.getProperty("SERVER"));
        setDbDriver(prop.getProperty("JDBC_DRIVER_MYSQL"));
        setDbUrl(prop.getProperty("JDBC_URL_THIN_MYSQL"));
        setDbUser(prop.getProperty("DB_USER_ID_MYSQL"));
        setDbPasswd(prop.getProperty("DB_USER_PW_MYSQL"));
    }

    private static void loadFaultCron() {
        setFaMsgProcessCron(prop.getProperty("FaMsgProcessCron"));
        setFaMsgProcessHealthCron(prop.getProperty("FaMsgProcessHealthCron"));
        setFaCheckerCron(prop.getProperty("FaCheckerCron"));
        setFaDeviceCheckCron(prop.getProperty("FaDeviceChkCron"));
        setFaSnmpChkCron(prop.getProperty("FaSnmpChkCron"));
        setArpChkCron(prop.getProperty("ArpChkCron"));
        setConfigBackupCron(prop.getProperty("ConfigBackupCron"));
    }

    private static void loadPerfCron() {
        setPerfNodeCron(prop.getProperty("PerfNodeCron"));
        setPerfCoreCron(prop.getProperty("PerfCoreCron"));
        setPerfPortCron(prop.getProperty("PerfPortCron"));
        setPerfFsCron(prop.getProperty("PerfFSCron"));
        setPerfDevIcmpCron(prop.getProperty("PerfDevICMPCron"));
        setProcessChkCron(prop.getProperty("ProcessChkCron"));
        setPerfTimeNodeCron(prop.getProperty("PerfTimeNodeCron"));
        setPerfTimeCoreCron(prop.getProperty("PerfTimeCoreCron"));
        setPerfTimePortCron(prop.getProperty("PerfTimePortCron"));
        setPerfTimeIcmpCron(prop.getProperty("PerfTimeICMPCron"));
        setCpuCoreAvgCron(prop.getProperty("CpuCoreAvgCron"));
        setCpuMemAvgCron(prop.getProperty("CpuMemAvgCron"));
        setCpuMemRankCron(prop.getProperty("CpuMemRankCron"));
    }

    public static String getSERVER() {
        return SERVER;
    }

    public static void setSERVER(String SERVER) {
        PropData.SERVER = SERVER;
    }

    public static String getDbDriver() {
        return DB_DRIVER;
    }

    public static void setDbDriver(String dbDriver) {
        DB_DRIVER = dbDriver;
    }

    public static String getDbUrl() {
        return DB_URL;
    }

    public static void setDbUrl(String dbUrl) {
        DB_URL = dbUrl;
    }

    public static String getDbUser() {
        return DB_USER;
    }

    public static void setDbUser(String dbUser) {
        DB_USER = dbUser;
    }

    public static String getDbPasswd() {
        return DB_PASSWD;
    }

    public static void setDbPasswd(String dbPasswd) {
        DB_PASSWD = dbPasswd;
    }

    public static String getFamsgSocketIp() {
        return FAMSG_SOCKET_IP;
    }

    public static void setFamsgSocketIp(String famsgSocketIp) {
        FAMSG_SOCKET_IP = famsgSocketIp;
    }

    public static int getFamsgSocketPort() {
        return FAMSG_SOCKET_PORT;
    }

    public static void setFamsgSocketPort(String famsgSocketPort) {
        FAMSG_SOCKET_PORT = Integer.parseInt(famsgSocketPort);
    }

    public static String getWebsocketIpPort() {
        return WEBSOCKET_IP_PORT;
    }

    public static void setWebsocketIpPort(String websocketIpPort) {
        WEBSOCKET_IP_PORT = websocketIpPort;
    }

    public static int getKeepDay() {
        return KEEP_DAY;
    }

    public static void setKeepDay(int keepDay) {
        KEEP_DAY = keepDay;
    }

    public static int getKeepWeek() {
        return KEEP_WEEK;
    }

    public static void setKeepWeek(int keepWeek) {
        KEEP_WEEK = keepWeek;
    }

    public static int getKeepMonth() {
        return KEEP_MONTH;
    }

    public static void setKeepMonth(int keepMonth) {
        KEEP_MONTH = keepMonth;
    }

    public static int getKeepYear() {
        return KEEP_YEAR;
    }

    public static void setKeepYear(int keepYear) {
        KEEP_YEAR = keepYear;
    }

    public static void setFamsgSocketPort(int famsgSocketPort) {
        FAMSG_SOCKET_PORT = famsgSocketPort;
    }

    public static String getSnmpPort() {
        return SNMP_PORT;
    }

    public static void setSnmpPort(String snmpPort) {
        SNMP_PORT = snmpPort;
    }

    public static String getSnmpTimeout() {
        return SNMP_TIMEOUT;
    }

    public static void setSnmpTimeout(String snmpTimeout) {
        SNMP_TIMEOUT = snmpTimeout;
    }

    public static String getSnmpRetries() {
        return SNMP_RETRIES;
    }

    public static void setSnmpRetries(String snmpRetries) {
        SNMP_RETRIES = snmpRetries;
    }

    public static String getSnmpVer() {
        return SNMP_VER;
    }

    public static void setSnmpVer(String snmpVer) {
        SNMP_VER = snmpVer;
    }

    public static String getSnmpCommunity() {
        return SNMP_COMMUNITY;
    }

    public static void setSnmpCommunity(String snmpCommunity) {
        SNMP_COMMUNITY = snmpCommunity;
    }

    public static String getSnmpV3Username() {
        return SNMP_V3_USERNAME;
    }

    public static void setSnmpV3Username(String snmpV3Username) {
        SNMP_V3_USERNAME = snmpV3Username;
    }

    public static String getSnmpV3Authpass() {
        return SNMP_V3_AUTHPASS;
    }

    public static void setSnmpV3Authpass(String snmpV3Authpass) {
        SNMP_V3_AUTHPASS = snmpV3Authpass;
    }

    public static String getSnmpV3Authprotocol() {
        return SNMP_V3_AUTHPROTOCOL;
    }

    public static void setSnmpV3Authprotocol(String snmpV3Authprotocol) {
        SNMP_V3_AUTHPROTOCOL = snmpV3Authprotocol;
    }

    public static String getSnmpV3Privprotocol() {
        return SNMP_V3_PRIVPROTOCOL;
    }

    public static void setSnmpV3Privprotocol(String snmpV3Privprotocol) {
        SNMP_V3_PRIVPROTOCOL = snmpV3Privprotocol;
    }

    public static String getTrapCommunity() {
        return TRAP_COMMUNITY;
    }

    public static void setTrapCommunity(String trapCommunity) {
        TRAP_COMMUNITY = trapCommunity;
    }

    public static String getFaCheckerCron() {
        return FA_CHECKER_CRON;
    }

    public static void setFaCheckerCron(String faCheckerCron) {
        FA_CHECKER_CRON = faCheckerCron;
    }

    public static String getFaDeviceCheckCron() {
        return FA_DEVICE_CHECK_CRON;
    }

    public static void setFaDeviceCheckCron(String faDeviceCheckCron) {
        FA_DEVICE_CHECK_CRON = faDeviceCheckCron;
    }

    public static String getFaMsgProcessHealthCron() {
        return FA_MSG_PROCESS_HEALTH_CRON;
    }

    public static void setFaMsgProcessHealthCron(String faMsgProcessHealthCron) {
        FA_MSG_PROCESS_HEALTH_CRON = faMsgProcessHealthCron;
    }

    public static String getFaMsgProcessCron() {
        return FA_MSG_PROCESS_CRON;
    }

    public static void setFaMsgProcessCron(String faMsgProcessCron) {
        FA_MSG_PROCESS_CRON = faMsgProcessCron;
    }

    public static String getFaSnmpChkCron() {
        return FA_SNMP_CHK_CRON;
    }

    public static void setFaSnmpChkCron(String faSnmpChkCron) {
        FA_SNMP_CHK_CRON = faSnmpChkCron;
    }

    public static String getArpChkCron() {
        return ARP_CHK_CRON;
    }

    public static void setArpChkCron(String arpChkCron) {
        ARP_CHK_CRON = arpChkCron;
    }

    public static String getPerfDevIcmpCron() {
        return PERF_DEV_ICMP_CRON;
    }

    public static void setPerfDevIcmpCron(String perfDevIcmpCron) {
        PERF_DEV_ICMP_CRON = perfDevIcmpCron;
    }

    public static String getPerfFsCron() {
        return PERF_FS_CRON;
    }

    public static void setPerfFsCron(String perfFsCron) {
        PERF_FS_CRON = perfFsCron;
    }

    public static String getPerfNodeCron() {
        return PERF_NODE_CRON;
    }

    public static void setPerfNodeCron(String perfNodeCron) {
        PERF_NODE_CRON = perfNodeCron;
    }

    public static String getPerfPortCron() {
        return PERF_PORT_CRON;
    }

    public static void setPerfPortCron(String perfPortCron) {
        PERF_PORT_CRON = perfPortCron;
    }

    public static String getPerfTimeIcmpCron() {
        return PERF_TIME_ICMP_CRON;
    }

    public static void setPerfTimeIcmpCron(String perfTimeIcmpCron) {
        PERF_TIME_ICMP_CRON = perfTimeIcmpCron;
    }

    public static String getPerfTimeNodeCron() {
        return PERF_TIME_NODE_CRON;
    }

    public static void setPerfTimeNodeCron(String perfTimeNodeCron) {
        PERF_TIME_NODE_CRON = perfTimeNodeCron;
    }

    public static String getPerfTimePortCron() {
        return PERF_TIME_PORT_CRON;
    }

    public static void setPerfTimePortCron(String perfTimePortCron) {
        PERF_TIME_PORT_CRON = perfTimePortCron;
    }

    public static String getProcessChkCron() {
        return PROCESS_CHK_CRON;
    }

    public static void setProcessChkCron(String processChkCron) {
        PROCESS_CHK_CRON = processChkCron;
    }

    public static String getPerfCoreCron() {
        return PERF_CORE_CRON;
    }

    public static void setPerfCoreCron(String perfCoreCron) {
        PERF_CORE_CRON = perfCoreCron;
    }

    public static String getPerfTimeCoreCron() {
        return PERF_TIME_CORE_CRON;
    }

    public static void setPerfTimeCoreCron(String perfTimeCoreCron) {
        PERF_TIME_CORE_CRON = perfTimeCoreCron;
    }

    public static String getCpuMemAvgCron() {
        return CPU_MEM_AVG_CRON;
    }

    public static void setCpuMemAvgCron(String cpuMemAvgCron) {
        CPU_MEM_AVG_CRON = cpuMemAvgCron;
    }

    public static String getCpuMemRankCron() {
        return CPU_MEM_RANK_CRON;
    }

    public static void setCpuMemRankCron(String cpuMemRankCron) {
        CPU_MEM_RANK_CRON = cpuMemRankCron;
    }

    public static String getConfigBackupCron() {
        return CONFIG_BACKUP_CRON;
    }

    public static void setConfigBackupCron(String configBackupCron) {
        CONFIG_BACKUP_CRON = configBackupCron;
    }

    public static String getCpuCoreAvgCron() {
        return CPU_CORE_AVG_CRON;
    }

    public static void setCpuCoreAvgCron(String cpuCoreAvgCron) {
        CPU_CORE_AVG_CRON = cpuCoreAvgCron;
    }

    public static String getDevicePerThread() {
        return DEVICE_PER_THREAD;
    }

    public static void setDevicePerThread(String devicePerThread) {
        DEVICE_PER_THREAD = devicePerThread;
    }
}




