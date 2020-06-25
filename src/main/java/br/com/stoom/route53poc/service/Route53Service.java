package br.com.stoom.route53poc.service;

import br.com.stoom.route53poc.exception.DomainAlreadyExisException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.route53.AmazonRoute53;
import com.amazonaws.services.route53.AmazonRoute53ClientBuilder;
import com.amazonaws.services.route53.model.*;
import br.com.stoom.route53poc.config.Route53Config;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class Route53Service {

    @Autowired
    private Route53Config route53Config;

    public void createSubDomain(String alias) throws DomainAlreadyExisException {

        final AmazonRoute53 amazonRoute53 = AmazonRoute53ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(route53Config.getAccessKey(), route53Config.getSecretKey())))
                .withRegion(Regions.US_EAST_1)
                .build();

        ChangeResourceRecordSetsRequest changeResourceRecordSetsRequest = new ChangeResourceRecordSetsRequest();

        changeResourceRecordSetsRequest.setHostedZoneId( route53Config.getAwsZoneId());

        ChangeBatch changeBatch = new ChangeBatch();
        List<Change> changes = new ArrayList<Change>();
        Change aChange = new Change();
        aChange.setAction( ChangeAction.CREATE );
        ResourceRecordSet resourceRecordSet = new ResourceRecordSet();

        resourceRecordSet.setName( alias+"."+route53Config.getAwsDomain() );

        resourceRecordSet.setTTL( 300L );
        resourceRecordSet.setType( "A" );
        List<ResourceRecord> resourceRecords = new ArrayList<ResourceRecord>();
        ResourceRecord aRecord = new ResourceRecord();

        aRecord.setValue( route53Config.getAwsIP() );

        resourceRecords.add( aRecord );
        resourceRecordSet.setResourceRecords( resourceRecords );
        aChange.setResourceRecordSet( resourceRecordSet );
        changes.add( aChange );
        changeBatch.setChanges( changes );
        changeBatch.setComment( "Just a test change..." );
        changeResourceRecordSetsRequest.setChangeBatch( changeBatch );

        try {
            amazonRoute53.changeResourceRecordSets( changeResourceRecordSetsRequest );
        }catch (Exception e) {
            log.error("Error - {}", e.getMessage());
            throw new DomainAlreadyExisException(e.getMessage());
        }

    }
}
