package ru.miroshka.astra4backclient.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@AllArgsConstructor
public class Ethernet {
    private String title;
    private String vlanId;
    private String ipV4;
    private String ipV6;
    private String physicalInterface;
    private String netmask;
    private String network;
    private String broadcast;
    private String mtu;
    private String gateway;

    private String macAddress;
    private Boolean isPhysicalInterface;
    private Boolean inMemory;

    public Ethernet(String title, String ipV4) {
        this.title = title;
        if (this.vlanId.isBlank()) {
            this.vlanId = parseVlanIdFromTitle(title);
        }
        this.ipV4 = ipV4;
        this.ipV6 = "";
        this.netmask = "";
        this.broadcast = "";
        this.network = "";
        this.physicalInterface = "";
        this.mtu = "";
        this.gateway = "";
        this.macAddress = "";
        this.isPhysicalInterface = true;
        this.inMemory = true;
    }


    public Ethernet(String title, String ipV4, String vlanId) {
        this.title = title;
        this.vlanId = vlanId;
        this.ipV4 = ipV4;
        this.ipV6 = "";
        this.netmask = "";
        this.broadcast = "";
        this.network = "";
        this.physicalInterface = "";
        this.mtu = "";
        this.gateway = "";
        this.macAddress = "";
        this.isPhysicalInterface = false;
        this.inMemory = true;
    }


    public Ethernet(String title, String ipV4, String vlanId, boolean isPhysicalInterface, String physicalInterface, String mtu, String macAddress) {
        this.title = title;
        this.vlanId = vlanId;
        this.ipV4 = ipV4;
        this.ipV6 = "";
        this.netmask = "";
        this.broadcast = "";
        this.network = "";
        this.physicalInterface = physicalInterface;
        this.mtu = mtu;
        this.gateway = "";
        this.macAddress = macAddress;
        this.isPhysicalInterface = isPhysicalInterface;
        this.inMemory = true;
    }

    private String parseVlanIdFromTitle(String strForParse) {
        String vlanIdStr = "";
        Pattern pattern = Pattern.compile("vlan[0-9]+");
        Matcher matcher = pattern.matcher(strForParse);
        while (matcher.find()) {
            vlanIdStr = matcher.group();

            break;
        }
        return vlanIdStr;
    }
}
