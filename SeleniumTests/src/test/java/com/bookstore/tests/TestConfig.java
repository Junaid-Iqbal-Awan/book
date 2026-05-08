package com.bookstore.tests;

public final class TestConfig {
  private TestConfig() {}

  public static String getBaseUrl() {
    String raw = System.getenv("APP_BASE_URL");
    if (raw == null || raw.trim().isEmpty()) {
      raw = "http://localhost:5001";
    }
    if (raw.endsWith("/")) {
      raw = raw.substring(0, raw.length() - 1);
    }
    return raw;
  }
}
