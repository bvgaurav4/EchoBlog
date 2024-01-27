import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;

import org.bson.Document;

import java.util.Scanner;

public class server {
    public static void main(String[] args) {
            try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017/")) {
            MongoDatabase database = mongoClient.getDatabase("lol"); // replace with your database name
            if (database == null) {
                System.out.println("Database not found");
                return;
            }
            System.out.println("Connected to the database " + database.getName());

            // Get the collection
            MongoCollection<Document> collection = database.getCollection("lol"); // replace with your collection name
            if (collection == null) {
                System.out.println("Collection not found");
                return;
            }
            System.out.println("Collection found: " + collection);
            long count = collection.countDocuments();
            if (count == 0) {
                System.out.println("Collection is empty");
            } else {
                System.out.println("Collection found with " + count + " documents");
            }

            // Print all documents in the collection
            for (Document doc : collection.find()) { // Removed limit(10)
                System.out.println(doc.toJson()); // Print the entire document as JSON
            }

            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("Type 'quit' to exit");
                String input = scanner.nextLine();
                if ("quit".equalsIgnoreCase(input)) {
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred while connecting to the database");
            e.printStackTrace();
        }
    }
}