package com.github.renamrgb.challengeserver.application.services;

import com.github.renamrgb.challengeserver.application.dtos.ServerDTO;
import com.github.renamrgb.challengeserver.domain.entities.Server;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.google.common.base.Preconditions.*;

@Service
public class TranslateServer {

    public Server from(ServerDTO dto) {
        checkArgument(Objects.nonNull(dto), "Objeto nulo");
        Server server = new Server();

        server.setName(dto.getName());
        server.setIp(dto.getIp());
        server.setPort(dto.getPort());
        return server;
    }

    public ServerDTO to(Server server) {
        checkArgument(Objects.nonNull(server), "Objeto nulo");
        ServerDTO dto = new ServerDTO();

        dto.setId(server.getId());
        dto.setName(server.getName());
        dto.setPort(server.getPort());
        dto.setIp(server.getIp());

        return dto;
    }
}
