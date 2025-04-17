package com.sfjs.gql.svc;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sfjs.data.entity.AccountEntity;
import com.sfjs.data.entity.BusinessEntity;
import com.sfjs.data.entity.MessageEntity;
import com.sfjs.jpa.repo.ConversationRepository;
import com.sfjs.jpa.repo.MessageRepository;
import com.sfjs.security.AuthorizationService;

import graphql.schema.DataFetchingEnvironment;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Service
@Transactional
public class ConversationService {

  Logger logger = Logger.getLogger(getClass().getName());

  @Autowired
  private AuthorizationService authorizationService;

  @Autowired
  private MessageRepository messageRepository;

  @Autowired
  private ConversationRepository conversationRepository;

  private final Sinks.Many<MessageEntity> messageSink = Sinks.many().multicast().onBackpressureBuffer();

  public Optional<MessageEntity> sendMessage(
      @Argument(name = "conversationId") Long conversationId,
      @Argument(name = "text") List<String> text,
      DataFetchingEnvironment environment) throws Exception {
      logger.info("Enter sendMessage");

      MessageEntity message = new MessageEntity();
      message.setText(text);
      Optional<AccountEntity> account = authorizationService.getAccount();
      if (account.isPresent()) {
        BusinessEntity business = account.get().getBusiness();
        message.setFromBusiness(business != null);
      }
      return conversationRepository.findById(conversationId).map(convo -> {
        message.setConversation(convo);
        MessageEntity savedMessage = messageRepository.save(message);
        this.messageSink.tryEmitNext(savedMessage);
        return savedMessage;
      });
    }

  public Flux<MessageEntity> messages(
      @Argument(name = "conversationId") Long conversationId,
      DataFetchingEnvironment environment) throws Exception {
    return messageSink.asFlux().filter(message -> message.getConversation().getId().equals(conversationId));
  }

}
