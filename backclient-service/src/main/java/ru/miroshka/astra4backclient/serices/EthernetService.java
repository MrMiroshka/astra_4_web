package ru.miroshka.astra4backclient.serices;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.miroshka.astra4backclient.entities.Ethernet;
import ru.miroshka.astra4backclient.repositories.EthernetRepository;

import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class EthernetService {
    private final EthernetRepository ethernetRepository;

    public List<Ethernet> getNetworkInterfacesV4() {
        return ethernetRepository.getNetworkInterfacesV4();
    }

}
