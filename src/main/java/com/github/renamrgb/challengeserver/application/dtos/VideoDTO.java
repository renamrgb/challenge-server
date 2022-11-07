package com.github.renamrgb.challengeserver.application.dtos;

import java.io.Serializable;
import java.util.UUID;

public class VideoDTO implements Serializable {
    private UUID id;
    private String description;
    private Long sizeInBytes;

    public VideoDTO() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getSizeInBytes() {
        return sizeInBytes;
    }

    public void setSizeInBytes(Long sizeInBytes) {
        this.sizeInBytes = sizeInBytes;
    }
}
