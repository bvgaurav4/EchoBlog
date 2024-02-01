import spark.*;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.FindIterable;

import com.mongodb.client.ClientSession;
import org.bson.types.ObjectId;
import java.util.List;

import org.bson.Document;
import org.bson.json.JsonParseException;

import org.bson.Document;
import java.util.ArrayList;
import java.util.Scanner;

public class Simpleserver {
    static MongoClient mongoClient;
    static MongoDatabase database;
    static MongoCollection<Document> users;
    static MongoCollection<Document> mgs;
    static MongoCollection<Document> blogs;
    static MongoCollection<Document> grps;
    static MongoCollection<Document> lol5;
    static ClientSession clientSession;


    public static void main(String[] args) {
        try {
            mongoClient = MongoClients.create("mongodb://localhost:27017/");
            database = mongoClient.getDatabase("lol");
            users = database.getCollection("lol");
            mgs = database.getCollection("lol2");
            blogs = database.getCollection("lol2");
            grps = database.getCollection("lol2");
            lol5 = database.getCollection("lol2");
            clientSession = mongoClient.startSession();
        } catch (Exception e) {
            System.out.println("Database not connected because the server is not running.");
            e.printStackTrace();
        }        
        // Set the port for your server (default is 4567)
        Spark.port(4567);

        // Define a simple endpoint
        Spark.get("/", (request, response) -> "hello love");

        Spark.get("/hello", (request, response) -> "hello love");
        
        Spark.get("/greet/:name", (request, response) -> {
            String name = request.params(":name");
            return "Hello, " + name + "!";
        });

        Spark.get("/json", (request, response) -> {
            response.type("application/json");
            return "{\"message\": \"Hello, JSON!\"}";
        });
        Spark.post("blog",(request,response)->{
            response.type("String");
            return "lol";
        });
        Spark.post("message",(request,response)->{
                        response.type("String");
            return "lol";
        });
        Spark.post("/create", (request, response) -> {
            try {
                String body = request.body();
                Document doc = Document.parse(body);

                if (!doc.containsKey("Email") || !doc.containsKey("Mobile")) {
                    response.type("error");
                    return "Missing Email or Mobile";
                }

                List<Document> orQueries = new ArrayList<>();
                orQueries.add(new Document("Email", doc.get("Email").toString()));
                orQueries.add(new Document("Mobile", doc.get("Mobile").toString()));

                Document filter = new Document("$or", orQueries);

                FindIterable<Document> result = users.find(filter);
                if (result.iterator().hasNext()) {
                    response.type("error");
                    return "user already exists";
                } else {
                    users.insertOne(doc);
                    response.type("String");
                    return "lol";
                }
            } catch (JsonParseException e) {
                response.type("error");
                return "Invalid JSON in request body";
            } catch (Exception e) {
                response.type("error");
                return "An error occurred: " + e.getMessage();
            }
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

            // response.type("Boolean");
            response.type("application/json");
            
            // return jsonString.equals("[]") ? "false" : "true";
            return jsonString;
        });
    }
}
 