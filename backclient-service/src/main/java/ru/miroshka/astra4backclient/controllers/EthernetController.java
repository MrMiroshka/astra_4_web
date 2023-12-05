package ru.miroshka.astra4backclient.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.miroshka.astra4backclient.converters.EthernetMapper;
import ru.miroshka.astra4backclient.dto.EthernetV4Dto;
import ru.miroshka.astra4backclient.serices.EthernetService;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/interfaces")
public class EthernetController {
    private final EthernetService ethernetService;

    @GetMapping("/find/all")
    @ResponseStatus(HttpStatus.OK)
    public List<EthernetV4Dto> getAllNetworkInterfaces(@RequestHeader HttpHeaders headers) {

        log.info(new StringBuilder().append("Запрос всех сетевых интерфейсов от - ")
                .append("Host:").append(headers.getFirst("host")).append("; ")
                .append("App:").append(headers.getFirst("user-agent")).append(";")
                .toString());

        return EthernetMapper.ethernetDtoListFromEthernetList(ethernetService.getNetworkInterfacesV4());
    }
}
