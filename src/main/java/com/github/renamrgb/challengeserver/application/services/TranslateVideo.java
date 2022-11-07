package com.github.renamrgb.challengeserver.application.services;

import com.github.renamrgb.challengeserver.domain.entities.Server;
import com.github.renamrgb.challengeserver.domain.entities.Video;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Service
public class TranslateVideo {

    public Video from(Server server, MultipartFile file) {
        Video video = new Video();
        video.setServer(server);
        video.setBase64(convertToBase64(file));
        video.setDescription(file.getOriginalFilename());
        return video;
    }


    private String convertToBase64(MultipartFile video) {
        try {
            byte[] bytes = video.getBytes();
            return Base64.getEncoder().encodeToString(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] toBinary(Video video) {
       return Base64.getDecoder().decode(video.getBase64());
    }
}
