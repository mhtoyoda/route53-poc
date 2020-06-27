package br.com.stoom.route53poc.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DomainRequest {

    private String subdomain;
}
