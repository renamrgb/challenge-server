package com.github.renamrgb.challengeserver.application.services;

import com.github.renamrgb.challengeserver.application.dtos.ServerDTO;
import com.github.renamrgb.challengeserver.application.services.exceptions.ResourceNotfoundException;
import com.github.renamrgb.challengeserver.domain.entities.Server;
import com.github.renamrgb.challengeserver.infra.repositories.ServerRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.github.renamrgb.challengeserver.domain.StandardMessages.*;

@Service
public class ServerService {

    private final ServerRepository serverRepository;
    private final TranslateServer translateServer;

    public ServerService(ServerRepository serverRepository, TranslateServer translateServer) {
        this.serverRepository = serverRepository;
        this.translateServer = translateServer;
    }

    @Transactional
    public ServerDTO create(ServerDTO dto) {
        Server server = translateServer.from(dto);
        server = serverRepository.save(server);
        return translateServer.to(server);
    }

    public void delete(String serverId) {
        try {
            UUID uuid = UUID.fromString(serverId);
            serverRepository.deleteById(uuid);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotfoundException(RESOURCE_NOT_FOUND);
        }
    }
}
