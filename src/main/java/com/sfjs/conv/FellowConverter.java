package com.sfjs.conv;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.sfjs.dto.Account;
import com.sfjs.dto.Fellow;
import com.sfjs.dto.Role;
import com.sfjs.entity.FellowEntity;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class FellowConverter extends BaseConverter<FellowEntity, Fellow> {

  AccountConverter accountConverter;

  public FellowConverter(AccountConverter accountConverter) {
    super(FellowEntity.class, Fellow.class);
    this.accountConverter = accountConverter;
  }

  @Override
  public FellowEntity convertToEntity(Fellow src) {
    FellowEntity dest = new FellowEntity();

    dest.setName(src.getName());
    Account account = new Account();
    account.setEmail(src.getEmail());
    // TODO consider getting rid of role entities
    Role role = new Role();
    role.setName("FELLOW");
    account.setRoles(Set.of(role));
    // This is where the password gets encrypted
    System.out.println("convert to entity: " + account);
    dest.setAccount(accountConverter.convertToEntity(account));

    dest.setBetaTester(src.getBetaTester());
    if (src.getCollaborator() != null && src.getCollaborator().booleanValue()) {
      dest.setCollaborator(true);
      dest.setMessage(src.getMessage());
      if (src.getReferralPartner() != null && src.getReferralPartner().booleanValue()) {
        dest.setReferralPartner(true);
        dest.setReferralCode(src.getReferralCode());
      }
    }
    return dest;
  }

  @Override
  public Fellow convertToBody(FellowEntity src) {
    Fellow dest = super.convertToBody(src);
    dest.setId(src.getId());
    dest.setName(src.getName());
    dest.setEmail(src.getAccount().getEmail());
    dest.setBetaTester(src.getBetaTester());
    if (src.isCollaborator()) {
      dest.setCollaborator(true);
      dest.setMessage(src.getMessage());
      if (src.isReferralPartner()) {
        dest.setReferralPartner(true);
        dest.setReferralCode(src.getReferralCode());
      }
    }

    return dest;
  }
}
