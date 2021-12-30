package com.ifood.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/about")
public class About {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity about() {
        return new ResponseEntity("Serviço em funcionamento.", HttpStatus.OK);
    }

}
