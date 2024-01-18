package ru.miroshka.astra4backclient.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class Ethernet {
    private String title;
    private String ipV4;
    private String ipV6;
    private String physicalInterface;
    private String netmask;
    private String network;
    private String broadcast;
    private String mtu;
    private String gateway;
    private Boolean inMemory;

    public Ethernet(String title, String ipV4) {
        this.title = title;
        this.ipV4 = ipV4;
        this.ipV6 = "";
        this.netmask = "";
        this.broadcast = "";
        this.network = "";
        this.physicalInterface = "";
        this.mtu="";
        this.gateway="";
        this.inMemory = true;
    }

}
