package com.github.renamrgb.challengeserver.application.services;

import com.github.renamrgb.challengeserver.application.dtos.ServerDTO;
import com.github.renamrgb.challengeserver.domain.entities.Server;
import com.github.renamrgb.challengeserver.infra.repositories.ServerRepository;
import org.springframework.stereotype.Service;

@Service
public class ServerService {

    private final ServerRepository serverRepository;
    private final TranslateServer translateServer;

    public ServerService(ServerRepository serverRepository, TranslateServer translateServer) {
        this.serverRepository = serverRepository;
        this.translateServer = translateServer;
    }

    public ServerDTO create(ServerDTO dto) {
        Server server = translateServer.from(dto);
        server = serverRepository.save(server);
        return translateServer.to(server);
    }
}
