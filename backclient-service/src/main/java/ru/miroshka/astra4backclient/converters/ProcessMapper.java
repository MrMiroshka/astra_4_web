package ru.miroshka.astra4backclient.converters;

import lombok.extern.slf4j.Slf4j;
import ru.miroshka.astra4backclient.dto.ProcessAstra4Dto;
import ru.miroshka.astra4backclient.entities.Process;

@Slf4j
public class ProcessMapper {
    public static ProcessAstra4Dto entityToProcessAstra4Dto(Process process) throws Exception {
        /*String[] tempFullNamesProcessAstra = (process.getFullNameProcessAstra()).split(" ");
        tempFullNamesProcessAstra[0].substring(tempFullNamesProcessAstra[0].lastIndexOf("/"), tempFullNamesProcessAstra.length-1);
        if (tempFullNamesProcessAstra.length==2){
            return new ProcessAstra4Dto(
                    process.getPid(),
                    process.getStartTime(),
                    process.getStartTime(),
                    tempFullNamesProcessAstra[0].substring(tempFullNamesProcessAstra[0].lastIndexOf("/"), tempFullNamesProcessAstra.length - 1),
                    tempFullNamesProcessAstra[0],
                    tempFullNamesProcessAstra[1].substring(tempFullNamesProcessAstra[1].lastIndexOf("/"), tempFullNamesProcessAstra.length - 1),
                    tempFullNamesProcessAstra[1]
            );
        }else{
            throw new Exception("При конвертировании процесса в процесс \"Астры\" произошла ошибка. Не правильные пути процессов");
        }*/
        return null;
    }
}
