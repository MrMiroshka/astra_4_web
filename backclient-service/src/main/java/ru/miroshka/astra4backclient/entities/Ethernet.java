package ru.miroshka.astra4backclient.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ethernet {
    private Long id;
    private String title;
    private String displayName;
    private String ipV4;
    private String ipV6;
}
