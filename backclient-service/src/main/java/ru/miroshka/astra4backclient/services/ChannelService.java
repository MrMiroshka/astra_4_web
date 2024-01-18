package ru.miroshka.astra4backclient.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.miroshka.astra4backclient.entities.Ethernet;
import ru.miroshka.astra4backclient.entities.Process;
import ru.miroshka.astra4backclient.repositories.EthernetRepository;
import ru.miroshka.astra4backclient.repositories.ProcessRepository;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChannelService {
    private final ProcessRepository processRepository;


    public List<Process> getProcessAstraChannels()  {
        return processRepository.getProcesses();
    }

    public Process getProcessByPid(){
        return processRepository.getProcessByPid();
    }
}
