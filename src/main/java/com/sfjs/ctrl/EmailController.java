package com.sfjs.ctrl;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sfjs.dto.EmailMessage;

@RestController
@EnableWebMvc
@Transactional
public class EmailController {

  @Autowired
  private JavaMailSender mailSender;
  Logger logger = Logger.getLogger(EmailController.class.getName());
  ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();

  @RequestMapping(path = "/email", method = RequestMethod.POST)
  public SimpleMailMessage send(@RequestBody EmailMessage requestBody) {
    logger.info("Request body: " + requestBody);
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(requestBody.getTo());
    message.setSubject(requestBody.getSubject());
    message.setText(requestBody.getText());

    try {
      mailSender.send(message);
      return message;
    } catch (Exception ex) {
      logger.log(Level.SEVERE, "An error occurred while performing the operation", ex);
      throw ex;
    }
  }
}
