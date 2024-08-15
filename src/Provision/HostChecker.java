package Provision;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;
import java.util.Optional;

import Provision.snmp.SnmpUtil;
import Provision.snmp.enums.MIB_MGMT;
import Provision.snmp.enums.SnmpVersion;

public class HostChecker {

    private HostChecker(){
        //no instance
    }

    public static boolean pingCheck(String host, int timeout) {
        try {
            return InetAddress.getByName(host).isReachable(timeout);
        } catch (IOException e) {
            return false;
        }
    }

    public static boolean portCheck(String host, int port) {
        try (Socket socket = new Socket(host, port)) {
            return socket.isConnected();
        } catch (IOException e) {
            return false;
        }
    }

    public static Optional<String> snmpCheck(String host, List<String> communitys) {
        for (String community : communitys) {
            try{
                String s = SnmpUtil.get(host, SnmpVersion.SNMP_VERSION_2C.value(), community, MIB_MGMT.SYS_UP_TIME.oid(), 2, 500);
                if(s != null && !s.equals("")){
                    return Optional.of(community);
                }
            }catch(Exception e){
                continue;
            }
        }
        return Optional.empty();
    }

}
