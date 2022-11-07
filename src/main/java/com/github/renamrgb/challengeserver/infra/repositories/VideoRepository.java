package com.github.renamrgb.challengeserver.infra.repositories;

import com.github.renamrgb.challengeserver.domain.entities.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VideoRepository extends JpaRepository<Video, UUID> {
}
