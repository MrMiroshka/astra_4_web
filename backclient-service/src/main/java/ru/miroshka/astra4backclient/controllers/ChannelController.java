package ru.miroshka.astra4backclient.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.miroshka.astra4backclient.entities.Process;
import ru.miroshka.astra4backclient.exceptios.errors.ResourceNotFoundException;
import ru.miroshka.astra4backclient.services.ChannelService;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/channels")
public class ChannelController {
    private final ChannelService channelService;

    @GetMapping("/find/all")
    @ResponseStatus(HttpStatus.OK)
    public Optional<List<Process>> getAllChannels() throws Exception {
        List<Process> listProcess = channelService.getProcessAstraChannels();
        return Optional.ofNullable(listProcess.isEmpty() ? null : listProcess);
    }

    @GetMapping("/find/all_pages")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String getChannels(
            //public Page<String> getChannels(
            @RequestParam(name = "p", defaultValue = "1") Integer page,
            @RequestParam(name = "idTicket", required = false) Long idTicket,
            @RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize
    ) {
        if (page < 1) {
            page = 1;
        }
        return "вернул что-то";
        //return commentService.find(idTicket, page, pageSize).map(p -> commentConverter.entityToDtoRequest(p));
    }

    @GetMapping("/find")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String findById(
            @RequestParam(required = false, name = "pid", defaultValue = "-1") Long pid,
            @RequestParam String nameChannel
    ) {
        if (pid <= 0) {
            pid = -1L;
        }
        log.info("Запрос канала с id - " + pid + " nameChannel - " + nameChannel);
        return "Запрос канала с id - " + pid + " nameChannel - " + nameChannel;
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void create()
    //        (@RequestBody TicketDto ticketDto,  @RequestHeader String username)
    {

    }

    @PutMapping("/update/{Id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateChannel() {

    }


}
