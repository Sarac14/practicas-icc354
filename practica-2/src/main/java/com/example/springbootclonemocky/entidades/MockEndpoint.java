package com.example.springbootclonemocky.entidades;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

@Entity
public class MockEndpoint implements Serializable {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;
     private String endpointPath;
     private String method;

     private Integer responseCode;
     private String contentType;
     private String responseBody;
     private String endpointName;
     private String description;
     private Integer expirationTime;
     private Integer responseDelay;
     private String secretToken;

     /*@OneToMany(mappedBy="mockEndpoint", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
     private Set<HttpHeader> headers;*/

    @Column(length = 10000)
    private String headers;

    private String url;
     @ManyToOne
     @JoinColumn(name="user_id")
     private Usuario user;

     public Long getId() {
         return id;
     }

     public void setId(Long id) {
         this.id = id;
     }

     public String getEndpointPath() {
         return endpointPath;
     }

     public void setEndpointPath(String endpointPath) {
         this.endpointPath = endpointPath;
     }

     public String getMethod() {
         return method;
     }

     public void setMethod(String method) {
         this.method = method;
     }

     public Usuario getUser() {
         return user;
     }

     public void setUser(Usuario user) {
         this.user = user;
     }



     public Integer getResponseCode() {
         return responseCode;
     }

     public void setResponseCode(Integer responseCode) {
         this.responseCode = responseCode;
     }

     public String getContentType() {
         return contentType;
     }

     public void setContentType(String contentType) {
         this.contentType = contentType;
     }

     public String getResponseBody() {
         return responseBody;
     }

     public void setResponseBody(String responseBody) {
         this.responseBody = responseBody;
     }

     public String getEndpointName() {
         return endpointName;
     }

     public void setEndpointName(String endpointName) {
         this.endpointName = endpointName;
     }

     public String getDescription() {
         return description;
     }

     public void setDescription(String description) {
         this.description = description;
     }

     public Integer getExpirationTime() {
         return expirationTime;
     }

     public void setExpirationTime(Integer expirationTime) {
         this.expirationTime = expirationTime;
     }

     public Integer getResponseDelay() {
         return responseDelay;
     }

     public void setResponseDelay(Integer responseDelay) {
         this.responseDelay = responseDelay;
     }

     public String getSecretToken() {
         return secretToken;
     }

     public void setSecretToken(String secretToken) {
         this.secretToken = secretToken;
     }

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public Map<String, String> getHeaders() {
        try {
            return objectMapper.readValue(headers, Map.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error deserializando los headers", e);
        }
    }

    public void setHeaders(Map<String, String> headers) {
        try {
            this.headers = objectMapper.writeValueAsString(headers);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializando los headers", e);
        }
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
