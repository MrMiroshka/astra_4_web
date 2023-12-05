package ru.miroshka.astra4backclient.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/channels")
public class ChannelController {
    @GetMapping("/find/all")
    @ResponseStatus(HttpStatus.OK)
    public String getAllChannels(@RequestParam Integer a) throws Exception {
        log.info("Запрос всех каналов");
        if (a==1){throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Все пропало!");}
        return "Запрос всех каналов";
        //return ChannelMapper.channelDtoListFromChannelList()
    }

    @GetMapping("/find")
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

    @GetMapping("/find/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String findById(@PathVariable Long id){
        log.info("Запрос канала с id - " + id);
        return "Запрос канала с id - " + id;
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void create()
    //        (@RequestBody TicketDto ticketDto,  @RequestHeader String username)
    {

    }

    @PutMapping("/update/{Id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateChannel(){

    }


}
