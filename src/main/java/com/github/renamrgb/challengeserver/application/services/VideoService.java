package com.github.renamrgb.challengeserver.application.services;

import com.github.renamrgb.challengeserver.application.services.exceptions.ResourceNotfoundException;
import com.github.renamrgb.challengeserver.domain.entities.Server;
import com.github.renamrgb.challengeserver.domain.entities.Video;
import com.github.renamrgb.challengeserver.infra.repositories.ServerRepository;
import com.github.renamrgb.challengeserver.infra.repositories.VideoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.UUID;

import static com.github.renamrgb.challengeserver.domain.StandardMessages.*;

@Service
public class VideoService {

    private final ServerRepository serverRepository;
    private final VideoRepository videoRepository;
    private final TranslateVideo translateVideo;

    public VideoService(ServerRepository serverRepository, VideoRepository videoRepository, TranslateVideo translateVideo) {
        this.serverRepository = serverRepository;
        this.videoRepository = videoRepository;
        this.translateVideo = translateVideo;
    }

    @Transactional
    public void save(String serverId, MultipartFile file) {
        try {
            UUID uuid = UUID.fromString(serverId);
            Optional<Server> optionalServer = serverRepository.findById(uuid);
            Server server = optionalServer.orElseThrow(() -> new ResourceNotfoundException(RESOURCE_NOT_FOUND));
            Video video = translateVideo.from(server, file);
            videoRepository.save(video);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotfoundException(DATABASE_INTEGRATION_VIOLATION);
        }
    }

    @Transactional
    public byte[] getBinary(String serverId, String videoId) {
        Video video = verifyExistsServerAndVideo(serverId, videoId);
        byte[] bytes = translateVideo.toBinary(video);
        return bytes;
    }

    private Video verifyExistsServerAndVideo(String serverId, String videoId) {
        Optional<Server> optionalServer = serverRepository.findById(UUID.fromString(serverId));
        Server server = optionalServer.orElseThrow(() -> new ResourceNotfoundException(RESOURCE_NOT_FOUND));

        return server.getVideos()
                .stream()
                .filter(video -> video.getId().equals(UUID.fromString(videoId)))
                .findFirst()
                .orElseThrow(() -> new ResourceNotfoundException(RESOURCE_NOT_FOUND));
    }
}
