package br.com.stoom.route53poc.controller;

import br.com.stoom.route53poc.dto.DomainRequest;
import br.com.stoom.route53poc.exception.DomainAlreadyExisException;
import br.com.stoom.route53poc.service.Route53Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Route53Controller {

    @Autowired
    private Route53Service route53Service;

    @PostMapping(value="/route", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createAlias(@RequestBody DomainRequest domainRequest){
        try {
            route53Service.createSubDomain(domainRequest.getSubdomain());
            return ResponseEntity.ok("ok");
        } catch (DomainAlreadyExisException e) {
            return ResponseEntity.status(400).build();
        }
    }
}
