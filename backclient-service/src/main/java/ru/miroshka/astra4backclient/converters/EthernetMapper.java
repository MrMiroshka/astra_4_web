package ru.miroshka.astra4backclient.converters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.miroshka.astra4backclient.dto.EthernetV4Dto;
import ru.miroshka.astra4backclient.entities.Ethernet;

import java.util.List;

@Slf4j
@Component
public class EthernetMapper {

    public static EthernetV4Dto entityToEthernetV4Dto(Ethernet ethernet) {
        String fullName;
        if (ethernet.getIsPhysicalInterface()) {
            fullName = ethernet.getTitle() + ":" + ethernet.getIpV4();
        } else {
            fullName = ethernet.getPhysicalInterface() + ":" + ethernet.getTitle() + ":" + ethernet.getIpV4();
        }

        return new EthernetV4Dto(
                ethernet.getTitle(),
                ethernet.getVlanId(),
                fullName,
                ethernet.getIpV4(),
                ethernet.getPhysicalInterface(),
                ethernet.getNetmask(),
                ethernet.getNetwork(),
                ethernet.getBroadcast(),
                ethernet.getMtu(),
                ethernet.getGateway(),
                ethernet.getIsPhysicalInterface(),
                ethernet.getInMemory().toString()
        );

    }

    public static Ethernet ethernetDtoToEntity(EthernetV4Dto ethernetDto) {
        return new Ethernet(
                ethernetDto.getTitle(),
                ethernetDto.getVlanId(),
                ethernetDto.getIpV4(),
                "",
                ethernetDto.getPhysicalInterface(),
                ethernetDto.getNetmask(),
                ethernetDto.getNetwork(),
                ethernetDto.getBroadcast(),
                ethernetDto.getMtu(),
                ethernetDto.getGateway(),
                "",
                ethernetDto.getIsPhysicalInterface(),
                true
        );
    }


    public static List<EthernetV4Dto> ethernetDtoListFromEthernetList(List<Ethernet> listEthernet) {
        return listEthernet.stream().map(EthernetMapper::entityToEthernetV4Dto).toList();
    }

    public static List<Ethernet> ethernetListFromEthernetDtoList(List<EthernetV4Dto> listEthernetDto) {
        return listEthernetDto.stream().map(EthernetMapper::ethernetDtoToEntity).toList();
    }

}
