package com.github.renamrgb.challengeserver.application.controllers;

import com.github.renamrgb.challengeserver.application.dtos.ServerDTO;
import com.github.renamrgb.challengeserver.application.services.ServerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class ServerController {

    private final ServerService serverService;

    public ServerController(ServerService serverService) {
        this.serverService = serverService;
    }

    @PostMapping
    @RequestMapping(value = "servers", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ServerDTO> save(@RequestBody ServerDTO request) {
        ServerDTO response = serverService.create(request);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{uuid}")
                .buildAndExpand(response.getId())
                .toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @PutMapping(value = "servers/{serverId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ServerDTO update(@PathVariable String serverId, @RequestBody ServerDTO dto) {
        return serverService.update(serverId, dto);
    }

    @DeleteMapping(value = "servers/{serverId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String serverId) {
        serverService.delete(serverId);
    }

    @GetMapping(value = "servers/{serverId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ServerDTO findById(@PathVariable String serverId) {
        return serverService.findById(serverId);
    }

    @GetMapping(value = "servers")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<ServerDTO> findAll() {
        return serverService.findAll();
    }
}
