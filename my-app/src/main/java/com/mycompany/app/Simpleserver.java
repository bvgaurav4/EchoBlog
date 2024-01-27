import spark.*;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.FindIterable;

import com.mongodb.client.ClientSession;

import org.bson.Document;

import java.util.Scanner;

public class Simpleserver {
    static MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017/");
    static MongoDatabase database = mongoClient.getDatabase("lol"); 
    static MongoCollection<Document> users = database.getCollection("lol"); 
    static MongoCollection<Document> mgs = database.getCollection("lol2"); 
    static MongoCollection<Document> blogs = database.getCollection("lol2"); 
    static MongoCollection<Document> grps = database.getCollection("lol2"); 
    static MongoCollection<Document> lol5 = database.getCollection("lol2"); 
    static ClientSession clientSession = mongoClient.startSession();

    public static void main(String[] args) {
        // Set the port for your server (default is 4567)
        Spark.port(4567);

        // Define a simple endpoint
        Spark.get("/hello", (request, response) -> "meow meow nigga");
        

        Spark.get("/greet/:name", (request, response) -> {
            String name = request.params(":name");
            return "Hello, " + name + "!";
        });

        Spark.get("/json", (request, response) -> {
            response.type("application/json");
            return "{\"message\": \"Hello, JSON!\"}";
        });
        
        Spark.get("/login", (request, response) -> {
            String username = request.queryParams("Email");
            String password = request.queryParams("LoginId");
            System.out.println(username+" "+password);
            Document filter = new Document("Email", username).append("LoginId", password);

            FindIterable<Document> result = users.find(filter);

            StringBuilder jsonBuilder = new StringBuilder();
            jsonBuilder.append("[");

            for (Document doc : result) {
                jsonBuilder.append(doc.toJson()).append(",");
            }

            if (jsonBuilder.length() > 1) {
                jsonBuilder.deleteCharAt(jsonBuilder.length() - 1);
            }

            jsonBuilder.append("]");

            String jsonString = jsonBuilder.toString();

            response.type("Boolean");
            
            return jsonString.equals("[]") ? "false" : "true";
        });
    }
}
 