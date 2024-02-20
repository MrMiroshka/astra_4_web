package ru.miroshka.astra4backclient.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EthernetV4Dto {
    private String title;
    private String vlanId;
    private String fullName;
    private String ipV4;
    private String physicalInterface;
    private String netmask;
    private String network;
    private String broadcast;
    private String mtu;
    private String gateway;
    private Boolean isPhysicalInterface;
    private String inMemory;

    public EthernetV4Dto(String title, String ipV4) {
        this.title = title;
        this.vlanId= "";
        this.ipV4 = ipV4;
        this.fullName = "";
        this.physicalInterface = "";
        this.netmask = "";
        this.network = "";
        this.broadcast = "";
        this.mtu = "";
        this.gateway = "";
        this.isPhysicalInterface = true;
        this.inMemory = "";
    }

    public EthernetV4Dto(String title, String ipV4, String vlanId) {
        this.title = title;
        this.vlanId = vlanId;
        this.ipV4 = ipV4;
        this.fullName = "";
        this.physicalInterface = "";
        this.netmask = "";
        this.network = "";
        this.broadcast = "";
        this.mtu = "";
        this.gateway = "";
        this.isPhysicalInterface = false;
        this.inMemory = "";
    }
}
