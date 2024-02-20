package ru.miroshka.astra4backclient.repositories;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.miroshka.astra4backclient.entities.Ethernet;
import ru.miroshka.astra4backclient.exceptios.errors.GetInfoNetworkInterfaceServerException;
import ru.miroshka.astra4backclient.validators.IPv4ValidatorRegex;

import java.io.*;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

@Slf4j
@Component
public class EthernetRepository {

    private static final String allInfoNetworkInterfaces = "ip -detail link show ";
    private static final String[] requestInfoNetworkInterfaces = {"/bin/bash", "-c", allInfoNetworkInterfaces};

    @Data
    private class ModelNetworkInterfaceAdditionalOptions {
        private String id;
        private String physicalInterface;
        private Boolean isPhysicalInterface;
        private String macAddress;
        private String mtu;

        public ModelNetworkInterfaceAdditionalOptions() {
            this.id = new String();
            this.physicalInterface = new String();
            this.isPhysicalInterface = false;
            this.macAddress = new String();
            this.mtu = new String();
        }
    }

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
        //запрашиваем все сетевые интерфейсы в системе через NetworkInterface
        //P.S.: vlan на физ. интерфейсе не является child.
        try {
            NetworkInterface.networkInterfaces().filter(networkInterface ->
                    !networkInterface.getInterfaceAddresses().isEmpty()).filter(networkInterface ->
                    !networkInterface.isVirtual()).forEach(networkInterface ->
                    networkInterface.getInterfaceAddresses().forEach(interfaceAddress -> {
                        if (IPv4ValidatorRegex.isValid(interfaceAddress.getAddress().getHostAddress())) {
                            if ((!interfaceAddress.getAddress().isLoopbackAddress()) || (interfaceAddress.getAddress().isLoopbackAddress() && lo)) {
                                ModelNetworkInterfaceAdditionalOptions modelNIAO = parsInfoFromInterfaceAdditionalOptions(networkInterface.getDisplayName());
                                resultEthernetList.add(new Ethernet(networkInterface.getDisplayName(),
                                        interfaceAddress.getAddress().getHostAddress(),
                                        modelNIAO.getId(),
                                        modelNIAO.getIsPhysicalInterface(),
                                        modelNIAO.getPhysicalInterface(),
                                        modelNIAO.getMtu(),
                                        modelNIAO.getMacAddress()));
                            }
                        }
                    }));
        } catch (SocketException sExp) { //TODO:добавить нормальную обработку исключений
            log.error("проблемы с получением сетевых интерфейсов от ОС");
        }
        parsNetworkInterfaces(resultEthernetList);
        return resultEthernetList;
    }

    private ModelNetworkInterfaceAdditionalOptions parsInfoFromInterfaceAdditionalOptions(String nameNetworkInterface) {
        ModelNetworkInterfaceAdditionalOptions modelNIOptions = new ModelNetworkInterfaceAdditionalOptions();
        if (!nameNetworkInterface.isBlank()) {
            try {
                String processStr;
                requestInfoNetworkInterfaces[2] = allInfoNetworkInterfaces + nameNetworkInterface;
                java.lang.Process process = Runtime.getRuntime().exec(requestInfoNetworkInterfaces);

                try (BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                    String infoNI = input.readLine();
                    if (!infoNI.isBlank()) {
                        String[] tempListInfoNI = infoNI.split(":");
                        String[] tempNameNI = tempListInfoNI[1].split("@");
                        if (tempNameNI.length == 1) {
                            modelNIOptions.setPhysicalInterface(tempNameNI[0].trim());
                            modelNIOptions.setIsPhysicalInterface(true);
                            modelNIOptions.setId("");
                        } else if (tempNameNI.length == 2) {
                            modelNIOptions.setPhysicalInterface(tempNameNI[1].trim());
                            modelNIOptions.setIsPhysicalInterface(false);
                        }
                        String[] tempMtu = tempListInfoNI[2].split(" mtu ");
                        if (tempMtu.length > 1) {
                            modelNIOptions.setMtu(tempMtu[1].split(" ", 2)[0]);
                        }

                        while ((infoNI = input.readLine()) != null) {
                            if (!infoNI.isBlank()) {
                                String[] tempDataMas = infoNI.trim().split(" ", 6);
                                if (tempDataMas.length >= 3 && (tempDataMas[0].toLowerCase().equals("link/ether") || tempDataMas[0].toLowerCase().equals("link/loopback"))) {
                                    modelNIOptions.setMacAddress(tempDataMas[1]);
                                    continue;
                                }
                                if (tempDataMas.length >= 5 && (tempDataMas[0].toLowerCase().equals("vlan")) && (tempDataMas[3].toLowerCase().equals("id"))) {
                                    modelNIOptions.setId(tempDataMas[4]);
                                }
                            }
                        }
                    }

                } catch (IOException exp) {
                    throw new GetInfoNetworkInterfaceServerException("При получении дополнительной информации сетевого интерфейса  \'" + allInfoNetworkInterfaces + nameNetworkInterface + "\' на сервере, произошла ошибка.");
                }
            } catch (GetInfoNetworkInterfaceServerException exp) {
                throw exp;
            } catch (Exception exp) {
                throw new GetInfoNetworkInterfaceServerException("При получении дополнительной информации сетевого интерфейса  \'" + allInfoNetworkInterfaces + nameNetworkInterface + "\' на сервере, произошла ошибка. В методе - " + exp.getStackTrace()[0].getMethodName() + ". В классе - " + exp.getStackTrace()[0].getClassName() + ". Строка - " + exp.getStackTrace()[0].getLineNumber());
            }
        }
        return modelNIOptions;
    }

    private void parsNetworkInterfaces(List<Ethernet> resultEthernetList) {
        try (Scanner netInterfaces = new Scanner(new File("/etc/network/interfaces"))) {
            Ethernet ethernet = null;
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
                            if (ethernet.getBroadcast().isBlank()) {
                                ethernet.setBroadcast(stringArray[1]);
                            }
                            break;
                        case ("netmask"):
                            if (ethernet.getNetmask().isBlank()) {
                                ethernet.setNetmask(stringArray[1]);
                            }
                            break;
                        case ("network"):
                            if (ethernet.getNetwork().isBlank()) {
                                ethernet.setNetwork(stringArray[1]);
                            }
                            break;
                        case ("vlan_raw_device"):
                            if (ethernet.getPhysicalInterface().isBlank()) {
                                ethernet.setPhysicalInterface(stringArray[1]);
                            }
                            break;
                        case ("mtu"):
                            if (ethernet.getMtu().isBlank()) {
                                ethernet.setMtu(stringArray[1]);
                            }
                            break;
                        case ("gateway"):
                            if (ethernet.getGateway().isBlank()) {
                                ethernet.setGateway(stringArray[1]);
                            }
                            break;
                    }
                }

            }
        } catch (FileNotFoundException exp) {
            throw new RuntimeException(exp);
        }
    }


}
