package ru.miroshka.astra4backclient.validators;

import org.springframework.stereotype.Component;
import ru.miroshka.astra4backclient.dto.EthernetV4Dto;
import ru.miroshka.astra4backclient.exceptios.errors.ValidationException;

import java.util.ArrayList;
import java.util.List;

@Component
public class EthernetInterfaceValidator {
    public void validate(EthernetV4Dto ethernetV4Dto) {
        List<String> errors = new ArrayList<>();
        if (ethernetV4Dto.getTitle().isBlank()) {
            errors.add("Имя сетевого интерфейса не может иметь пустое название");
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }
}
