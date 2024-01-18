package ru.miroshka.astra4backclient.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProcessAstra4Dto {
    /***
     * pid: идентификатор процесса
     */
    private Long pid;

    /**
     * startTime: время и дата начала этого процесса
     */
    private String startTime;

    /**
     * nameProcess: имя исполняемого файла
     */
    private String nameProcess;

    /**
     * fullNameProcess: имя исполняемого файла c полным путем
     */
    private String fullNameProcess;

    /***
     * nameProcessAstra: имя исполняемого файла (с настройками для конкретного канала)
     */
    private String nameProcessAstra;

    /***
     * fullNameProcessAstra: имя исполняемого файла
     * (с полными путями, с настройками для конкретного канала)
     */
    private String fullNameProcessAstra;

}
