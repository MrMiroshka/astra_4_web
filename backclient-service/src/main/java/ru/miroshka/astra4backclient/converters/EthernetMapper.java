package ru.miroshka.astra4backclient.converters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import ru.miroshka.astra4backclient.dto.EthernetV4Dto;
import ru.miroshka.astra4backclient.entities.Ethernet;

import java.util.List;

@Slf4j
public class EthernetMapper {

    public static EthernetV4Dto entityToEthernetV4Dto(Ethernet ethernet) {
        String isPhysicalInterface;
        String fullName;
        if (!ethernet.getPhysicalInterface().isEmpty()) {
            isPhysicalInterface = "false";
            fullName = ethernet.getPhysicalInterface() + ":" + ethernet.getTitle() + ":" + ethernet.getIpV4();
        } else {
            isPhysicalInterface = "true";
            fullName = ethernet.getTitle() + ":" + ethernet.getIpV4();
        }
        return new EthernetV4Dto(
                ethernet.getTitle(),
                fullName,
                ethernet.getIpV4(),
                ethernet.getPhysicalInterface(),
                ethernet.getNetmask(),
                ethernet.getNetwork(),
                ethernet.getBroadcast(),
                ethernet.getMtu(),
                ethernet.getGateway(),
                isPhysicalInterface
                );

    }

    public static Ethernet ethernetDtoToEntity(EthernetV4Dto ethernetDto) {
        return new Ethernet(
                ethernetDto.getTitle(),
                ethernetDto.getIpV4(),
                "",
                ethernetDto.getPhysicalInterface(),
                ethernetDto.getNetmask(),
                ethernetDto.getNetwork(),
                ethernetDto.getBroadcast(),
                ethernetDto.getMtu(),
                ethernetDto.getGateway()
        );
    }

    public static EthernetV4Dto jsonToEthernetV4Dto(String jsonNetworkInterface) throws JSONException {
        JSONObject json = new JSONObject(jsonNetworkInterface);
        return new EthernetV4Dto(

        );
    }

    public static List<EthernetV4Dto> ethernetDtoListFromEthernetList(List<Ethernet> listEthernet) {
        return listEthernet.stream().map(EthernetMapper::entityToEthernetV4Dto).toList();
    }

    public static List<Ethernet> ethernetListFromEthernetDtoList(List<EthernetV4Dto> listEthernetDto) {
        return listEthernetDto.stream().map(EthernetMapper::ethernetDtoToEntity).toList();
    }
}
