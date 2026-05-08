package com.bookstore.tests;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public final class ApiHelper {
  private static final ObjectMapper MAPPER = new ObjectMapper();
  private static final HttpClient CLIENT = HttpClient.newBuilder()
    .connectTimeout(Duration.ofSeconds(10))
    .build();

  private ApiHelper() {}

  public static void waitForAppReady(String baseUrl) {
    String url = baseUrl + "/api/book";
    HttpRequest request = HttpRequest.newBuilder(URI.create(url)).GET().build();
    for (int i = 0; i < 30; i++) {
      try {
        HttpResponse<String> res = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        if (res.statusCode() >= 200 && res.statusCode() < 500) {
          return;
        }
      } catch (Exception ignored) {
        // Retry until timeout.
      }
      try {
        Thread.sleep(1000);
      } catch (InterruptedException interrupted) {
        Thread.currentThread().interrupt();
        break;
      }
    }
    throw new RuntimeException("App did not become ready at " + url);
  }

  public static void createUser(String baseUrl, String fullname, String email, String password) {
    try {
      Map<String, String> payload = new LinkedHashMap<>();
      payload.put("fullname", fullname);
      payload.put("email", email);
      payload.put("password", password);
      String body = MAPPER.writeValueAsString(payload);
      HttpRequest request = HttpRequest.newBuilder(URI.create(baseUrl + "/api/user/signup"))
        .header("Content-Type", "application/json")
        .POST(HttpRequest.BodyPublishers.ofString(body))
        .build();
      HttpResponse<String> res = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
      if (res.statusCode() != 201) {
        throw new RuntimeException("User signup failed: " + res.statusCode() + " " + res.body());
      }
    } catch (IOException | InterruptedException ex) {
      throw new RuntimeException("User signup failed", ex);
    }
  }

  public static BookData createBook(String baseUrl, String name, String price) {
    try {
      String boundary = "----SeleniumBoundary" + UUID.randomUUID();
      Map<String, String> fields = new LinkedHashMap<>();
      fields.put("name", name);
      fields.put("price", price);
      fields.put("category", "Testing");
      fields.put("title", "QA Book");
      fields.put("description", "Automated test data");
      fields.put("image", "test.jpg");

      String body = buildMultipartBody(fields, boundary);
      HttpRequest request = HttpRequest.newBuilder(URI.create(baseUrl + "/api/book"))
        .header("Content-Type", "multipart/form-data; boundary=" + boundary)
        .POST(HttpRequest.BodyPublishers.ofByteArray(body.getBytes(StandardCharsets.UTF_8)))
        .build();
      HttpResponse<String> res = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
      if (res.statusCode() != 201) {
        throw new RuntimeException("Book create failed: " + res.statusCode() + " " + res.body());
      }
      JsonNode node = MAPPER.readTree(res.body());
      JsonNode book = node.get("book");
      String id = book.get("_id").asText();
      String createdName = book.get("name").asText();
      String createdPrice = book.get("price").asText();
      return new BookData(id, createdName, createdPrice);
    } catch (IOException | InterruptedException ex) {
      throw new RuntimeException("Book create failed", ex);
    }
  }

  public static String uniqueEmail(String prefix) {
    return prefix + "+" + UUID.randomUUID().toString().replace("-", "") + "@example.com";
  }

  public static class BookData {
    public final String id;
    public final String name;
    public final String price;

    public BookData(String id, String name, String price) {
      this.id = id;
      this.name = name;
      this.price = price;
    }
  }

  private static String buildMultipartBody(Map<String, String> fields, String boundary) {
    StringBuilder sb = new StringBuilder();
    for (Map.Entry<String, String> entry : fields.entrySet()) {
      sb.append("--").append(boundary).append("\r\n");
      sb.append("Content-Disposition: form-data; name=\"")
        .append(entry.getKey()).append("\"\r\n\r\n");
      sb.append(entry.getValue()).append("\r\n");
    }
    sb.append("--").append(boundary).append("--\r\n");
    return sb.toString();
  }
}
