package com.github.renamrgb.challengeserver.application.controllers;

import com.github.renamrgb.challengeserver.application.services.VideoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("servers/{serverId}/videos")
public class VideoController {

    private final VideoService videoService;

    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @PostMapping
    public void save (@PathVariable String serverId,
                      @RequestParam MultipartFile video) {
        videoService.save(serverId, video);
    }

    @GetMapping(value = "/{videoId}/binary", produces = "video/mp4")
    public byte[] getBinaryByid(@PathVariable String serverId,
                                @PathVariable String videoId) {
        return videoService.getBinary(serverId, videoId);
    }
}

