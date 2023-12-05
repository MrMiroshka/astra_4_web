package ru.miroshka.astra4backclient.converters;

import lombok.extern.slf4j.Slf4j;
import ru.miroshka.astra4backclient.dto.EthernetV4Dto;
import ru.miroshka.astra4backclient.entities.Ethernet;

import java.util.List;

@Slf4j
public class EthernetMapper {

    public static EthernetV4Dto entityToEthernetV4Dto(Ethernet ethernet){
        return new EthernetV4Dto(
                ethernet.getId(),
                ethernet.getTitle(),
                ethernet.getDisplayName(),
                ethernet.getIpV4()
        );

    }

    public static Ethernet ethernetDtoToEntity(EthernetV4Dto ethernetDto){
        return new Ethernet(
                ethernetDto.getId(),
                ethernetDto.getTitle(),
                ethernetDto.getDisplayName(),
                ethernetDto.getIpV4(),
                ""
        );
    }

    public static List<EthernetV4Dto> ethernetDtoListFromEthernetList(List<Ethernet> listEthernet){
        return listEthernet.stream().map(EthernetMapper::entityToEthernetV4Dto).toList();
    }

    public static List<Ethernet> ethernetListFromEthernetDtoList(List<EthernetV4Dto> listEthernetDto){
        return listEthernetDto.stream().map(EthernetMapper::ethernetDtoToEntity).toList();
    }
}
