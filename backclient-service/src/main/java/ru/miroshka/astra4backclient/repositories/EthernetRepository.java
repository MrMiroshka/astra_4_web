package ru.miroshka.astra4backclient.repositories;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.miroshka.astra4backclient.entities.Ethernet;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

@Slf4j
@Component
public class EthernetRepository {
    public List<Ethernet> getNetworkInterfacesV4() {
        List<Ethernet> resultEthernetList = new ArrayList<>();
        try {

            Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
            long id =1;
            while (nets.hasMoreElements()) {
                NetworkInterface netint = nets.nextElement();
                if (!netint.isVirtual() && !netint.getInterfaceAddresses().isEmpty()) {
                    Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
                    for (InetAddress inetAddress : Collections.list(inetAddresses)) {
                        if (inetAddress.getAddress().length == 4) {
                            resultEthernetList.add(new Ethernet(id++, netint.getDisplayName(), netint.getName(), inetAddress.getHostAddress(), ""));
                        }
                    }
                }
            }
        }catch (SocketException sExp){
            log.error("проблемы с получением сетевых интерфейсов от ОС");
        }
        return resultEthernetList;
    }

}
