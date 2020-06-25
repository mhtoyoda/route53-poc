package com.toyoda.route53poc.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class Route53Config {

    @Value("${aws.accessKey}")
    private String accessKey;

    @Value("${aws.secretKey}")
    private String secretKey;

    @Value("${aws.zoneId}")
    private String awsZoneId;

    @Value("${aws.domain}")
    private String awsDomain;

    @Value("${aws.ip}")
    private String awsIP;

}
