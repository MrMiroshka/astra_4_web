package ru.miroshka.astra4backclient.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Process {
    /***
     * pid: идентификатор процесса
     */
    private Long pid;
    /**
     * startTime: время и дата начала этого процесса
     */
    private ZonedDateTime startTime;
    /**
     * fullNameProcess: аргументы процесса, если аргументы недоступны то имя исполняемого файла
     */
    private String fullNameProcess;
    /***
     * fullNameProcessAstra: имя запущенной команды или исполняемого файла (с полными путями)
     */
    private String fullNameProcessAstra;
}
