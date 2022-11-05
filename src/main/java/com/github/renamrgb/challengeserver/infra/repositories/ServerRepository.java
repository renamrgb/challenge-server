package com.github.renamrgb.challengeserver.infra.repositories;

import com.github.renamrgb.challengeserver.domain.entities.Server;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ServerRepository extends JpaRepository<Server, UUID> {
}
