package com.sfjs.security;

import java.security.SecureRandom;
import java.util.Base64;

public class TokenGenerator {
  private static final SecureRandom secureRandom = new SecureRandom();

  public static String generateToken(int byteLength) {
    byte[] randomBytes = new byte[byteLength];
    secureRandom.nextBytes(randomBytes);
    return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
  }
}
