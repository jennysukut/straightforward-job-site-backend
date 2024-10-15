package com.sfjs.gql.resolvers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.sfjs.dto.NumericMetric;
import com.sfjs.entity.BusinessEntity;
import com.sfjs.entity.FellowEntity;
import com.sfjs.gql.schema.BusinessMetrics;
import com.sfjs.gql.schema.DonationMetrics;
import com.sfjs.gql.schema.FellowMetrics;
import com.sfjs.gql.schema.MetricsResult;
import com.sfjs.repo.BusinessRepository;
import com.sfjs.repo.FellowRepository;
import com.sfjs.svc.NumericMetricService;

@RestController
@EnableWebMvc
@Transactional
public class Metrics {

  @Autowired
  FellowRepository fellowRepository;

  @Autowired
  BusinessRepository businessRepository;

  @Autowired
  NumericMetricService numericMetricService;

  @QueryMapping(name = "metrics")
  public MetricsResult metrics() {
    MetricsResult result = new MetricsResult();
    {
      FellowMetrics fellowMetrics = new FellowMetrics();
      fellowMetrics.setSignups(fellowRepository.count());
      fellowMetrics.setBetaTesters(fellowRepository.count(getBetaTesterFellowExample()));
      fellowMetrics.setCollaborators(fellowRepository.count(getCollaboratorExample()));
      fellowMetrics.setReferralPartners(fellowRepository.count(getReferralPartnerExample()));
      result.setFellowMetrics(fellowMetrics);
    }
    {
      BusinessMetrics businessMetrics = new BusinessMetrics();
      businessMetrics.setSignups(businessRepository.count());
      businessMetrics.setBetaTesters(businessRepository.count(getBetaTesterBusinessExample()));
      result.setBusinessMetrics(businessMetrics);
    }
    {
      DonationMetrics donationMetrics = new DonationMetrics();
      NumericMetric fellowDonations = numericMetricService.findByName("CURRENT_FELLOW_DONATION");
      donationMetrics.setFellowDonations(fellowDonations.getMetric().toString());
      NumericMetric businessDonations = numericMetricService.findByName("CURRENT_BUSINESS_DONATION");
      donationMetrics.setBusinessDonations(businessDonations.getMetric().toString());
      donationMetrics.setTotalDonations(fellowDonations.getMetric().add(businessDonations.getMetric()).toString());
      result.setDonationMetrics(donationMetrics);
    }
    return result;
  }

  private Example<BusinessEntity> getBetaTesterBusinessExample() {
    BusinessEntity businessBetaTesterEntity = new BusinessEntity();
    businessBetaTesterEntity.setBetaTester(true);
    Example<BusinessEntity> example = Example.of(businessBetaTesterEntity);
    return example;
  }

  private Example<FellowEntity> getBetaTesterFellowExample() {
    FellowEntity fellowBetaTesterEntity = new FellowEntity();
    fellowBetaTesterEntity.setBetaTester(true);
    Example<FellowEntity> example = Example.of(fellowBetaTesterEntity);
    return example;
  }

  private Example<FellowEntity> getReferralPartnerExample() {
    FellowEntity fellowBetaTesterEntity = new FellowEntity();
    fellowBetaTesterEntity.setReferralPartner(true);
    Example<FellowEntity> example = Example.of(fellowBetaTesterEntity);
    return example;
  }

  private Example<FellowEntity> getCollaboratorExample() {
    FellowEntity fellowBetaTesterEntity = new FellowEntity();
    fellowBetaTesterEntity.setCollaborator(true);
    Example<FellowEntity> example = Example.of(fellowBetaTesterEntity);
    return example;
  }
}
