package ru.miroshka.astra4backclient.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EthernetV4Dto {
    private Long id;
    private String title;
    private String displayName;
    private String ipV4;
}
