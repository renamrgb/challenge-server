package com.github.renamrgb.challengeserver.application.dtos;

import java.io.Serializable;
import java.util.UUID;

public class ServerDTO implements Serializable {
    private UUID id;
    private String name;
    private String ip;
    private Integer port;

    public ServerDTO() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
