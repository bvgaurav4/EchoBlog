package com.mycompany.app;

import spark.*;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import java.util.HashSet;
import java.util.Set;
import com.mongodb.client.FindIterable;

import java.util.concurrent.ConcurrentHashMap;

import com.mongodb.client.ClientSession;
import java.util.List;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.bson.Document;
import org.bson.json.JsonParseException;

import java.util.ArrayList;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class Server {
    private ConcurrentHashMap<String, ClientHandler> clients = new ConcurrentHashMap<>();
    private ServerSocket serverSocket;
    private static MongoClient mongoClient;
    private static MongoDatabase database;
    private static MongoCollection<Document> users;
    private static MongoCollection<Document> mgs;
    private static MongoCollection<Document> blogs;
    private static MongoCollection<Document> conns;
    private static MongoCollection<Document> lol5;
    private static ClientSession clientSession;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void serverStart() {

        try {

            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();

                System.out.println("New Friend Connected");
                ClientHandler clientHandler = new ClientHandler(socket);
                JsonObject obj = new JsonObject();
                obj.addProperty("name", "Server");
                System.out.println(clientHandler.name + " joined");
                clients.put(clientHandler.name, clientHandler);
                System.out.println(clientHandler + " is added to the list of clients.");
                System.out.println("Total clients: " + clients);
                obj.addProperty("message", clients.toString());
                Thread thread = new Thread(clientHandler);
                thread.start();
                System.out.println(obj.toString());
                clientHandler.buffWriter.write(obj.toString());
                clientHandler.buffWriter.newLine();
                clientHandler.buffWriter.flush();
                clientHandler.boradcastMessage(obj.toString());
            }
        } catch (IOException e) {
            System.out.println("Server is closed");
        }
    }

    public void closerServer() {

        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        try {
            mongoClient = MongoClients.create("mongodb://127.0.0.1:27018");
            database = mongoClient.getDatabase("lol");
            users = database.getCollection("lol");
            mgs = database.getCollection("messages");
            blogs = database.getCollection("blogs");
            conns = database.getCollection("connections");
            lol5 = database.getCollection("requests");
            clientSession = mongoClient.startSession();
        } catch (Exception e) {
            System.out.println("Database not connected because the server is not running.");
            e.printStackTrace();
        }
        Spark.port(4567);
        Spark.get("/hello", (request, response) -> "Za Warudo!");

        Spark.get("/greet/:name", (request, response) -> {
            String name = request.params(":name");
            return "Hello, " + name + "!";
        });
        Spark.post("/search", (request, response) -> {
            String lol = request.body();
            Document filter = Document.parse(lol);
            FindIterable<Document> result = users.find(filter);
            System.out.println(result);

            FindIterable<Document> result1 = users.find(filter);
            List<JsonObject> combinedResults = new ArrayList<>();
            StringBuilder jsonBuilder = new StringBuilder();
            jsonBuilder.append("[");
            for (Document document : result) {
                System.out.println(document.toJson());
                JsonObject jsonObject = JsonParser.parseString(document.toJson()).getAsJsonObject();
                combinedResults.add(jsonObject);
            }
            jsonBuilder.append("]");
            System.out.println(combinedResults);
            return combinedResults.toString();
        });

        ////// all are about messages

        Spark.get("message", (request, response) -> {
            String Email = request.queryParams("Email");
            String email2 = request.queryParams("Email1");
            Document filter = new Document("source", Email).append("target", email2);
            FindIterable<Document> result = mgs.find(filter);
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

            response.type("String");
            return jsonString;
        });
        Spark.post("/sending", (request, response) -> {
            try {
                String body = request.body();
                Document doc = Document.parse(body);
                mgs.insertOne(doc);
                System.out.println(doc);
                response.type("String");
                System.out.println("DONE");
                response.type("String");
                return "true";
            } catch (JsonParseException e) {
                response.type("error");
                return "false";
            } catch (Exception e) {
                response.type("error");
                return "false";
            }
        });
        Spark.get("/edit_mgs", (request, response) -> {
            String Email = request.queryParams("Email");
            String Email1 = request.queryParams("Email1");
            String content = request.queryParams("content");
            Document filter = new Document("source", Email).append("target", Email1);
            Document update = new Document("$set", new Document("content", content));
            mgs.updateOne(filter, update);
            response.type("String");
            return "true";
        });
        Spark.get("/delete_mgs", (request, response) -> {
            String Email = request.queryParams("Email");
            String Email1 = request.queryParams("Email1");
            Document filter = new Document("source", Email).append("target", Email1);
            mgs.deleteOne(filter);
            response.type("String");
            return "true";
        });

        ///// all are about blogs

        Spark.get("/blog", (request, response) -> {
            String Email = request.queryParams("Email");
            Document filter = new Document("Email", Email);
            FindIterable<Document> result = blogs.find(filter);
            FindIterable<Document> result2 = blogs.find();
            response.type("String");
            StringBuilder jsonBuilder = new StringBuilder();
            jsonBuilder.append("[");
            for (Document doc : result2) {
                jsonBuilder.append(doc.toJson()).append(",");
            }
            if (jsonBuilder.length() > 1) {
                jsonBuilder.deleteCharAt(jsonBuilder.length() - 1);
            }
            jsonBuilder.append("]");
            String jsonString = jsonBuilder.toString();
            return jsonString;
        });
        Spark.post("/post_blog", (request, response) -> {
            try {
                String body = request.body();
                Document doc = Document.parse(body);
                Set<String> likes = new HashSet<>();
                doc.append("likes", likes.toString());
                blogs.insertOne(doc);
                response.type("String");
                return "true";
            } catch (JsonParseException e) {
                response.type("error");
                return "Invalid JSON in request body";
            } catch (Exception e) {
                response.type("error");
                return "An error occurred: " + e.getMessage();
            }
        });

        Spark.get("/edit_blog", (request, response) -> {
            String Email = request.queryParams("Email");
            String title = request.queryParams("title");
            String content = request.queryParams("content");
            Document filter = new Document("Email", Email);
            Document update = new Document("$set", new Document("title", title).append("content", content));
            blogs.updateOne(filter, update);
            response.type("String");
            return "true";
        });
        Spark.get("/delete_blog", (request, response) -> {
            String Email = request.queryParams("Email");
            Document filter = new Document("Email", Email);
            blogs.deleteOne(filter);
            response.type("String");
            return "true";
        });
        Spark.post("/like", (request, response) -> {
            String requestBody = request.body();
            Document editing = Document.parse(requestBody);
            String email = request.queryParams("Email");
            String title = request.queryParams("title");
            System.out.println(email + " " + title);

            Document filter = new Document("email", email).append("title", title);
            System.out.println(blogs.find(filter).first());

            List<String> likes = (List<String>) blogs.find(filter).first().get("likes");
            likes.add((String) editing.get("likes"));

            Document update = new Document("$addToSet", new Document("likes", new Document("$each", likes)));
            System.out.println(update);
            System.out.println();

            blogs.updateOne(filter, update);

            response.type("text/plain");
            return "true";
        });

        ////// related to users connection and informations

        Spark.get("/my_profile", (request, response) -> {
            String Email = request.queryParams("Email");
            Document filter = new Document("Email", Email);
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

            response.type("String");
            return jsonString;
        });

        ////// all are about connections

        Spark.get("/connections", (request, response) -> {
            String Email = request.queryParams("Email");
            System.out.println(Email);
            Document filter = new Document("source", Email);
            FindIterable<Document> result = conns.find(filter);
            List<JsonObject> combinedResults = new ArrayList<>();
            StringBuilder jsonBuilder = new StringBuilder();
            jsonBuilder.append("[");
            for (Document document : result) {
                System.out.println(document);
                JsonObject jsonObject = JsonParser.parseString(document.toJson()).getAsJsonObject();
                combinedResults.add(jsonObject);
            }
            jsonBuilder.append("]");
            System.out.println(combinedResults);
            response.type("String");
            return combinedResults.toString();
        });
        Spark.get("/connect", (request, response) -> {
            String Email = request.queryParams("Email");
            String Email1 = request.queryParams("Email1");
            Document doc = new Document("Email", Email).append("Email1", Email1);
            conns.insertOne(doc);
            response.type("String");
            return "true";
        });

        Spark.get("/disconnect", (request, response) -> {
            String Email = request.queryParams("Email");
            String Email1 = request.queryParams("Email1");
            Document doc = new Document("Email", Email).append("Email1", Email1);
            conns.deleteOne(doc);
            response.type("String");
            return "true";
        });

        ////// all are about entering to application

        Spark.post("/create", (request, response) -> {
            try {
                String body = request.body();
                Document doc = Document.parse(body);

                if (!doc.containsKey("Email") || !doc.containsKey("Mobile") || !doc.containsKey("LoginId")
                        || !doc.containsKey("Username")) {
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
                    return "true";
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
            System.out.println(username + " " + password);
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
        ServerSocket serverSocket = new ServerSocket(1234);
        Server server = new Server(serverSocket);
        server.serverStart();
    }
}