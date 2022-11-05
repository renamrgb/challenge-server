package com.github.renamrgb.challengeserver.application.controllers;

import com.github.renamrgb.challengeserver.application.dtos.ServerDTO;
import com.github.renamrgb.challengeserver.application.services.ServerService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
public class ServerController {

    private final ServerService serverService;

    public ServerController(ServerService serverService) {
        this.serverService = serverService;
    }

    @PostMapping
    @RequestMapping(value = "server", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ServerDTO> save(@RequestBody ServerDTO request) {
        ServerDTO response = serverService.create(request);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{uuid}")
                .buildAndExpand(response.getId())
                .toUri();
        return ResponseEntity.created(uri).body(response);
    }


}
