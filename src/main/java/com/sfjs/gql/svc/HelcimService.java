package com.sfjs.gql.svc;

import java.io.IOException;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import reactor.core.publisher.Mono;

@Service
public class HelcimService {

  Logger logger = Logger.getLogger(HelcimService.class.getName());
  ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();

  @Autowired
  private OkHttpClient okHttpClient;

  @Autowired
  private Environment env;

  private String apiToken;
  private String apiUrl;

  @PostConstruct
  public void init() {
    apiToken = env.getProperty("helcim.api.token");
    apiUrl = env.getProperty("helcim.api.url");
  }

  static class CheckoutTokens {
    @Getter @Setter private String checkoutToken;
    @Getter @Setter private String secretToken;
  }

  public Mono<CheckoutTokens> initializeCheckout(String amount, String currency,
      String paymentType) {
    MediaType mediaType = MediaType.parse("application/json");
    String json;
//    try {
//      json = mapper.writeValueAsString(checkout);
//    } catch (JsonProcessingException e) {
      json = String.format("{\"paymentType\":\"%s\",\"amount\":\"%s\",\"currency\":\"%s\"}", paymentType,
          amount, currency);
//    }
    @SuppressWarnings("deprecation")
    RequestBody body = RequestBody.create(mediaType, json);
    Request request = new Request.Builder().url(apiUrl).post(body).addHeader("accept", "application/json")
        .addHeader("api-token", apiToken).addHeader("content-type", "application/json").build();

    return Mono.create(sink -> {
      okHttpClient.newCall(request).enqueue(new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
          sink.error(e);
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
          try (ResponseBody responseBody = response.body()) {
            if (!response.isSuccessful()) {
              sink.error(new IOException("Unexpected code " + response));
            } else {
              if (responseBody != null) {
                String body = responseBody.string();
                try {
                  CheckoutTokens checkoutResponse = mapper.readValue(body, CheckoutTokens.class);
                  sink.success(checkoutResponse);
                } catch (Exception e) {
                  sink.error(new IOException("Error parsing response body", e));
                }
              } else {
                sink.success(null);
              }
            }
          }
        }
      });
    });
  }
}