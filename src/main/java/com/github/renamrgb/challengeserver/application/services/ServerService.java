package com.github.renamrgb.challengeserver.application.services;

import com.github.renamrgb.challengeserver.application.dtos.ServerDTO;
import com.github.renamrgb.challengeserver.application.services.exceptions.ResourceNotfoundException;
import com.github.renamrgb.challengeserver.domain.entities.Server;
import com.github.renamrgb.challengeserver.infra.repositories.ServerRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @Transactional
    public ServerDTO update(String serverId, ServerDTO dto) {
        try {
            UUID uuid = UUID.fromString(serverId);
            Server referenceById = serverRepository.getReferenceById(uuid);
            Server entity = translateServer.copyDtoToEntity(dto, referenceById);
            entity = serverRepository.save(entity);

            return translateServer.to(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotfoundException(RESOURCE_NOT_FOUND);
        }
    }

    public void delete(String serverId) {
        try {
            UUID uuid = UUID.fromString(serverId);
            serverRepository.deleteById(uuid);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotfoundException(RESOURCE_NOT_FOUND);
        }
    }

    @Transactional(readOnly = true)
    public ServerDTO findById(String serverId) {
        Server entity = getExistByIdOrThrow(serverId);
        return translateServer.to(entity);
    }

    @Transactional(readOnly = true)
    public List<ServerDTO> findAll() {
        List<Server> serverList = serverRepository.findAll();
        return serverList
                .stream()
                .map(translateServer::to)
                .collect(Collectors.toList());
    }

    public String checkServer(String serverId) {
        Server entity = getExistByIdOrThrow(serverId);
        Boolean isUp = isUp(entity);
        if (isUp) {
            return "UP";
        }
        return "DOWN";
    }

    private Boolean isUp(Server entity) {
        String fullUri = getFullPathUri(entity);

        HttpRequest request = HttpRequest
                .newBuilder()
                .GET()
                .timeout(Duration.ofSeconds(3))
                .uri(URI.create(fullUri)).build();

        HttpClient httpClient = HttpClient
                .newBuilder()
                .connectTimeout(Duration.ofSeconds(3))
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private String getFullPathUri(Server entity) {
        return "http://"
                .concat(entity.getIp())
                .concat(":")
                .concat(String.valueOf(entity.getPort()));
    }

    private Server getExistByIdOrThrow(String serverId) {
        UUID uuid = UUID.fromString(serverId);
        Optional<Server> optionalServer = serverRepository.findById(uuid);
        return optionalServer
                .orElseThrow(() -> new ResourceNotfoundException(RESOURCE_NOT_FOUND));
    }
}
