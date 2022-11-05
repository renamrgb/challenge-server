package com.github.renamrgb.challengeserver.application.services;

import com.github.renamrgb.challengeserver.application.dtos.ServerDTO;
import com.github.renamrgb.challengeserver.application.services.exceptions.ResourceNotfoundException;
import com.github.renamrgb.challengeserver.domain.entities.Server;
import com.github.renamrgb.challengeserver.infra.repositories.ServerRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityNotFoundException;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.github.renamrgb.challengeserver.domain.StandardMessages.*;

@Service
public class ServerService {

    private final ServerRepository serverRepository;
    private final TranslateServer translateServer;
    private final RestTemplate restTemplate;

    public ServerService(ServerRepository serverRepository, TranslateServer translateServer, RestTemplate restTemplate) {
        this.serverRepository = serverRepository;
        this.translateServer = translateServer;
        this.restTemplate = restTemplate;
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
        try {
            String baseUrl = "http://"
                    .concat(entity.getIp())
                    .concat(":")
                    .concat(String.valueOf(entity.getPort()));
            URI uri = new URI(baseUrl);

            ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
            return true;
        } catch (HttpClientErrorException e) {
            System.out.println(e);
            return true;
        } catch (Exception e) {
           return false;
        }
    }

    private Server getExistByIdOrThrow(String serverId) {
        UUID uuid = UUID.fromString(serverId);
        Optional<Server> optionalServer = serverRepository.findById(uuid);
        return optionalServer
                .orElseThrow(() -> new ResourceNotfoundException(RESOURCE_NOT_FOUND));
    }
}
