package lan.zold.client;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.Builder;
import java.net.http.HttpResponse.BodyHandlers;

public class Client {
    HttpClient client;
    public Client() {
        System.out.println("Szerző: Sallai András, 2023");
        this.client = HttpClient.newHttpClient();        
    }

    public String get(String url) {
        HttpRequest request = genGetRequest(url);        
        return sendRequest(request);
    }
    private HttpRequest genGetRequest(String url, String... args) {
        Builder builder = HttpRequest.newBuilder();
        builder.uri(URI.create(url));
        if(args.length > 0) {
            String token = args[0];
            builder.header("Authorization", "Bearer " + token);            
        }
        return builder.build();
    }
    
    public String post(String url, String body, String... args) {
        HttpRequest request = this.genPostRequest(url, body, args);        
        return this.sendRequest(request);
    }
    private HttpRequest genPostRequest(String url, String body, String... args) {
        Builder builder = HttpRequest.newBuilder();
        builder.uri(URI.create(url));
        builder.header("Content-Type", "application/json");
        if(args.length > 0) {
            String token = args[0];
            builder.header("Authorization", "Bearer " + token);            
        }        
        builder.POST(HttpRequest.BodyPublishers.ofString(body));
        return builder.build();
    }

    public String put(String url, String body, String... args) {
        HttpRequest request = this.genPutRequest(url, body, args);
        return this.sendRequest(request);
    }
    private HttpRequest genPutRequest(String url, String body, String... args) {
        Builder builder = HttpRequest.newBuilder();
        builder.uri(URI.create(url));
        builder.header("Content-Type", "application/json");
        if(args.length > 0) {
            String token = args[0];
            builder.header("Authorization", "Bearer " + token);            
        }
        builder.PUT(HttpRequest.BodyPublishers.ofString(body));
        return builder.build();
    }

    public String delete(String url, String... args) {
        HttpRequest request = this.genDeleteRequest(url, args);
        return this.sendRequest(request);
    }
    private HttpRequest genDeleteRequest(String url, String... args) {
        Builder builder = HttpRequest.newBuilder();
        builder.uri(URI.create(url));
        builder.header("Content-Type", "application/json");
        if(args.length > 0) {
            String token = args[0];
            builder.header("Authorization", "Bearer " + token);            
        }
        builder.DELETE();
        return builder.build();
    }

    public String sendRequest(HttpRequest request) {
        String result = "";
        try {
            result = trySendRequest(request);                     
        } catch (IOException e) {
            System.err.println("Hiba! A lekérés sikertelen!");
            System.err.println(e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("Hiba! Megszakadt lekérés!");
            System.err.println(e.getMessage());            
        }
        return result;
    }
    private String trySendRequest(HttpRequest request) 
            throws IOException, InterruptedException {
        HttpResponse<String> response = 
        this.client.send(request, BodyHandlers.ofString());        
        return response.body();
    }
}
