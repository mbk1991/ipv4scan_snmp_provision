package Provision;


public class HostStatus {
    private String ip;
    private boolean isReachable;
    private boolean isSuccessSnmp;
    private String snmpCommunity;

    private boolean isManaged;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public boolean isReachable() {
        return isReachable;
    }

    public void setReachable(boolean reachable) {
        isReachable = reachable;
    }

    public boolean isSuccessSnmp() {
        return isSuccessSnmp;
    }

    public void setSuccessSnmp(boolean successSnmp) {
        isSuccessSnmp = successSnmp;
    }

    public String getSnmpCommunity() {
        return snmpCommunity;
    }

    public void setSnmpCommunity(String snmpCommunity) {
        this.snmpCommunity = snmpCommunity;
    }

    public boolean isManaged() {
        return isManaged;
    }

    public void setManaged(boolean managed) {
        isManaged = managed;
    }
}
