package br.com.stoom.route53poc.controller;

import br.com.stoom.route53poc.exception.DomainAlreadyExisException;
import br.com.stoom.route53poc.service.Route53Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Route53Controller {

    @Autowired
    private Route53Service route53Service;

    @GetMapping(value="/route", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createAlias(@RequestParam("alias") String alias){
        try {
            route53Service.createSubDomain(alias);
            return ResponseEntity.ok("ok");
        } catch (DomainAlreadyExisException e) {
            return ResponseEntity.status(400).build();
        }
    }
}
