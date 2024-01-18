package ru.miroshka.astra4backclient.repositories;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.miroshka.astra4backclient.entities.Ethernet;
import ru.miroshka.astra4backclient.validators.IPv4ValidatorRegex;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

@Slf4j
@Component
public class EthernetRepository {

    public void addNetworkInterfaceV4() {
        getNetworkInterfacesV4(false);
    }


    /**
     * Возвращает список сетевых интерфейсов
     *
     * @param lo - если true то учитывает сетевой интерфейс localhost = "127.0.0.1"
     */
    public List<Ethernet> getNetworkInterfacesV4(boolean lo) {
        List<Ethernet> resultEthernetList = new ArrayList<>();
        try {
            NetworkInterface.networkInterfaces().filter(networkInterface -> !networkInterface.getInterfaceAddresses().isEmpty())
                    .filter(networkInterface -> !networkInterface.isVirtual())
                    .forEach(networkInterface -> networkInterface.getInterfaceAddresses().forEach(interfaceAddress -> {
                        if (IPv4ValidatorRegex.isValid(interfaceAddress.getAddress().getHostAddress())) {
                            if ((!interfaceAddress.getAddress().isLoopbackAddress()) || (interfaceAddress.getAddress().isLoopbackAddress() && lo)) {
                                resultEthernetList.add(
                                        new Ethernet(networkInterface.getDisplayName(), interfaceAddress.getAddress().getHostAddress()));
                            }
                        }
                    }));
        } catch (SocketException sExp) { //TODO:добавить нормальную обработку исключений
            log.error("проблемы с получением сетевых интерфейсов от ОС");
        }
        parsNetworkInterfaces(resultEthernetList);
        return resultEthernetList;
    }

    private List<Ethernet> parsNetworkInterfaces(List<Ethernet> resultEthernetList) {
        try (Scanner netInterfaces = new Scanner(new File("/etc/network/interfaces"/*"file.txt"*/))) {
            Ethernet ethernet = null;
            //boolean isInterface = false;
            while (netInterfaces.hasNext()) {
                String parseString = netInterfaces.nextLine();
                if (parseString.contains("iface")) {
                    String[] stringArray = parseString.trim().replaceAll("[\\s]{2,}", " ").split(" ", 3);
                    ethernet = null;
                    if (!stringArray[1].equals("lo")) {

                        for (int i = 0; i < resultEthernetList.size(); i++) {
                            if (resultEthernetList.get(i).getTitle().equalsIgnoreCase(stringArray[1])) {
                                ethernet = resultEthernetList.get(i);
                                ethernet.setInMemory(false);
                                break;
                            }
                        }
                    }
                } else if (!Objects.isNull(ethernet)) {
                    String[] stringArray = parseString.trim().replaceAll("[\\s]{2,}", " ").split(" ", 3);
                    switch (stringArray[0].toLowerCase()) {
                        case ("broadcast"):
                            ethernet.setBroadcast(stringArray[1]);
                            break;
                        case ("netmask"):
                            ethernet.setNetmask(stringArray[1]);
                            break;
                        case ("network"):
                            ethernet.setNetwork(stringArray[1]);
                            break;
                        case ("vlan_raw_device"):
                            ethernet.setPhysicalInterface(stringArray[1]);
                            break;
                        case ("mtu"):
                            ethernet.setMtu(stringArray[1]);
                            break;
                        case ("gateway"):
                            ethernet.setGateway(stringArray[1]);
                            break;
                    }
                }

            }
        } catch (FileNotFoundException exp) {
            throw new RuntimeException(exp);
        }
        return resultEthernetList;
    }

}
