package ru.miroshka.astra4backclient.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.miroshka.astra4backclient.converters.EthernetMapper;
import ru.miroshka.astra4backclient.dto.EthernetV4Dto;
import ru.miroshka.astra4backclient.services.EthernetService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/interfaces")
public class EthernetController {
    private final EthernetService ethernetService;

    /**
     * Возвращает список сетевых интерфейсов
     * @param headers - для сбора данных запрашиваемого клиента
     * @param lo - если 'true' то учитывает сетевой интерфейс localhost = "127.0.0.1", по умолчанию 'false'
     */
    @GetMapping("/find/all")
    @ResponseStatus(HttpStatus.OK)
    public List<EthernetV4Dto> getAllNetworkInterfaces(@RequestHeader HttpHeaders headers,
                                                       @RequestParam(name = "lo", defaultValue = "false") boolean lo) {

        log.info(new StringBuilder().append("Запрос всех сетевых интерфейсов от - ")
                .append("Host:").append(headers.getFirst("host")).append("; ")
                .append("App:").append(headers.getFirst("user-agent")).append(";")
                .toString());

        return EthernetMapper.ethernetDtoListFromEthernetList(ethernetService.getNetworkInterfacesV4(lo));
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void addNetworkInterface(@RequestHeader HttpHeaders headers,
                                     @RequestBody String jsonNetworkInterface) throws JSONException {

        log.info(new StringBuilder().append("Добавление сетевого интерфейса от - ")
                .append("Host:").append(headers.getFirst("host")).append("; ")
                .append("App:").append(headers.getFirst("user-agent")).append(";")
                .toString());

        ethernetService.addNetworkInterfaceV4();
    }
}
