package com.sfjs.gql.resolvers;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import com.sfjs.data.entity.ConversationEntity;
import com.sfjs.data.entity.MessageEntity;
import com.sfjs.gql.svc.ConversationService;
import com.sfjs.jpa.repo.ConversationRepository;

import graphql.schema.DataFetchingEnvironment;
import reactor.core.publisher.Flux;

@Controller
@Transactional
public class ConversationResolver {

  Logger logger = Logger.getLogger(getClass().getName());

  @Autowired
  private ConversationRepository conversationRepository;

  @Autowired
  private ConversationService conversationService;

  @QueryMapping(name = "getConversation")
  public Optional<ConversationEntity> getConversation(
    @Argument(name = "id") Long id,
    DataFetchingEnvironment environment) throws Exception {
    logger.info("Enter getConversation");
    return conversationRepository.findById(id);
  }

  @MutationMapping(name = "sendMessage")
  @PreAuthorize("hasAnyRole('ROLE_FELLOW', 'ROLE_BUSINESS')")
  public Optional<MessageEntity> sendMessage(
    @Argument(name = "conversationId") Long conversationId,
    @Argument(name = "text") List<String> text,
    DataFetchingEnvironment environment) throws Exception {
    logger.info("Enter sendMessage");
    return conversationService.sendMessage(conversationId, text, environment);
  }

  @SubscriptionMapping
  public Flux<MessageEntity> messages(
      @Argument(name = "conversationId") Long conversationId,
      DataFetchingEnvironment environment) throws Exception {
    logger.info("Enter messages: " + conversationId);
    return conversationService.messages(conversationId, environment);
  }

}
