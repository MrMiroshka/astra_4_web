package ru.miroshka.astra4backclient.repositories;

import com.sun.jdi.IntegerValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.miroshka.astra4backclient.entities.Process;
import ru.miroshka.astra4backclient.exceptios.errors.GetProcessServerException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@Component
public class ProcessRepository {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM d HH:mm:ss yyyy", Locale.ENGLISH);
    private static final String[] requestCountProcess = {"/bin/bash", "-c", "ps ax | grep astra.generated_instance | wc -l"};
    private static final String[] requestProcessList = {"/bin/bash", "-c", "ps -eo pid,lstart,cmd | grep astra.generated_instance"};

    public List<Process> getProcesses() {
        final List<Process> listProcess = new ArrayList<>();
        final int countProcess;
        //BufferedReader input = null;
        try {
            String processStr;
            java.lang.Process process = Runtime.getRuntime().exec(requestCountProcess);
            try (BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                processStr = input.readLine();
            } catch (IOException exp) {
                throw new GetProcessServerException("При получении списка запущенных процессов \'astra\' на сервере, произошла ошибка.");
            }

            if (processStr != null) {

                countProcess = Integer.valueOf(processStr) - 2;
                if (0 < countProcess) {
                    int iteratorCountProcess = 0;
                    process = Runtime.getRuntime().exec(requestProcessList);
                    try (BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                        if (countProcess < 100) {
                            while ((processStr = input.readLine()) != null) {
                                iteratorCountProcess++;
                                String[] tempStringProcessInfo = ((processStr.trim()).replaceAll("[\\s]{2,}", " ")).split(" ");
                                listProcess.add(getProcessByString(tempStringProcessInfo));
                                if (iteratorCountProcess == countProcess) {
                                    break;
                                }
                            }
                        } else {
                            while ((processStr = input.readLine()) != null) {
                                iteratorCountProcess++;
                                String[] tempStringProcessInfo = (customReplaceAll(processStr.trim(), "  ", " ").split(" "));
                                listProcess.add(getProcessByString(tempStringProcessInfo));
                                if (iteratorCountProcess == countProcess) {
                                    break;
                                }
                            }
                        }
                    }catch (IOException exp) {
                        throw new GetProcessServerException("При получении списка запущенных процессов \'astra\' на сервере, произошла ошибка.");
                    }
                }

                BufferedReader inputErrors = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                String tempStrErrors;
                StringBuilder stackTraceStrErrors = new StringBuilder();
                while ((tempStrErrors = inputErrors.readLine()) != null) {
                    stackTraceStrErrors.append(tempStrErrors);
                }
                if (stackTraceStrErrors.length() != 0) {
                    throw new GetProcessServerException("Ошибка на сервере при выполнении запроса \'Find all process \'astra\'!\'");
                }

            }

        } catch (NumberFormatException exp) {
            throw new GetProcessServerException("При получении количества запущенных процессов \'astra\' на сервере, произошла ошибка.");
        } catch (GetProcessServerException exp) {
            throw exp;
        } catch (Exception exp) {
            throw new GetProcessServerException("При получении списка запущенных процессов \'astra\' на сервере, произошла ошибка.");
        }
        return listProcess;
    }


    private static Process getProcessByString(String[] tempStringProcessInfo) {
        StringBuilder builder = new StringBuilder();
        for (int i = 1; i < 6; i++) {
            if (i != 1) {
                builder.append(" ");
            }
            builder.append(tempStringProcessInfo[i]);
        }

        LocalDateTime localDateTime = LocalDateTime.parse(builder.toString(), formatter);
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());

        return new Process(
                Long.parseLong(tempStringProcessInfo[0]),
                zonedDateTime,
                tempStringProcessInfo[6],
                tempStringProcessInfo[7]
        );

    }


    private static String customReplaceAll(String str, String oldStr, String newStr) {

        if ("".equals(str) || "".equals(oldStr) || oldStr.equals(newStr)) {
            return str;
        }
        if (newStr == null) {
            newStr = "";
        }
        final int strLength = str.length();
        final int oldStrLength = oldStr.length();
        StringBuilder builder = new StringBuilder(str);

        for (int i = 0; i < strLength; i++) {
            int index = builder.indexOf(oldStr, i);

            if (index == -1) {
                if (i == 0) {
                    return str;
                }
                return builder.toString();
            }
            builder = builder.replace(index, index + oldStrLength, newStr);

        }
        return builder.toString();
    }

    public Process getProcessByPid() {
        return new Process();
    }
}
