import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.JsonObject;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonArray;
import javafx.scene.paint.Color;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;


public class Blogpage extends Application {
    private String email;
    private String password;

    public Blogpage(String emailn, String passwordn) {
        this.email = emailn;
        this.password = passwordn;
    }
    public Blogpage() {
        this.email = "email";
        this.password = "password";
    }
    
    public String blog(String Email) {
        // This constructor is only used to create a new instance of the class
        String url = "http://localhost:4567/";
        String lol = "";
        try{
        url = url + "blog?Email=" + Email;
        System.out.println(url);
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        lol =response.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return lol;
    }
    @Override
    public void start(Stage primaryStage) {
        VBox blogContainerWrapper = new VBox();
        blogContainerWrapper.setSpacing(20);
        blogContainerWrapper.setPadding(new Insets(20));
        JsonArray blogs = new JsonArray();
        String Email = "";
        String lollol= blog(Email);
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(lollol);

        Lol l = new Lol();
        VBox root = new VBox();
        root.setSpacing(50);
        root.getChildren().addAll(l);


        if(element.isJsonArray()){
            blogs = element.getAsJsonArray();
        }
        for(int i=0;i<blogs.size();i++)
        {
            JsonObject blog = blogs.get(i).getAsJsonObject();
            BlogContainer blogContainer = new BlogContainer();

            blogContainer.setTitle(blog.get("title").getAsString());
            blogContainer.setContent(blog.get("content").getAsString());
            blogContainer.setImage("logo.jpg"); 
            blogContainer.setPrefWidth(600);
            blogContainerWrapper.getChildren().add(blogContainer);
        }
        
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(blogContainerWrapper);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setPrefWidth(600);
        // scrollPane.setStyle("-fx-background-color: t/ransparent;");
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

        VBox vbox1 = new VBox();
        vbox1.setSpacing(20);
        vbox1.setPadding(new Insets(20));
        for(int i=0;i<5;i++)
        {
            container container = new container(200, 100, "Hello World", primaryStage);
            vbox1.getChildren().add(container);
        }
        HBox hbox = new HBox(vbox1);
        // hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(50);
        root.getChildren().add(hbox);
        // hbox.setPadding(new Insets(50));
        hbox.getChildren().add(scrollPane);

        hbox.setId("gridpane");
        Scene scene = new Scene(root, 600, 300, Color.TRANSPARENT);
                scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

        primaryStage.setTitle("Blog Containers with Scrollbar");
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
