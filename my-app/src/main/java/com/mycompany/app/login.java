import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.layout.StackPane;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Pos;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javafx.scene.layout.HBox;

public class login extends Application {
    public boolean sending(String email, String password) {
        // This constructor is only used to create a new instance of the class
        String url = "http://localhost:4567/";
        boolean lol = false;
        try{
        url = url + "login?Email=" + email + "&LoginId=" + password;
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
        lol =response.toString().equals("true");
        System.out.println(response.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return lol;
    }
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaFX Login Form");
        HBox card = new HBox(10); 
        HBox cards = new HBox(0);
        ImageView img = new ImageView(new Image("logo.jpg"));
        img.setFitHeight(100);
        img.setFitWidth(100);
        cards.getChildren().addAll(img);
        cards.setPadding(new Insets(10));
        card.setPadding(new Insets(10));
        StackPane root = new StackPane();
        Rectangle rect1 = new Rectangle(600, 800, Color.web("#FFF",0.7));
        Rectangle rect = new Rectangle(600, 800, Color.web("#8F00FF",0.7));
        HBox mail = new HBox(10);
        mail.setPadding(new Insets(100));
        Label nameLabel = new Label("Email:");
        TextField nameInput = new TextField();
        mail.getChildren().addAll(nameLabel,nameInput);
        nameInput.setPromptText("Email");
        nameInput.setPrefWidth(200);

        HBox pass = new HBox(10);
        pass.setPadding(new Insets(10));

        Label passLabel = new Label("Password:");
        PasswordField passInput = new PasswordField();
        passInput.setPromptText("Password");
        passInput.setPrefWidth(200);
        pass.getChildren().addAll(passLabel,passInput);

        HBox buttons = new HBox(10);
        buttons.setPadding(new Insets(10));

        Button createButton = new Button("Create Account");
        Button loginButton = new Button("Login");
        buttons.getChildren().addAll(createButton,loginButton);



        root.setId("gridpane");
        loginButton.setOnAction(e -> {
            System.out.println("Username: " + nameInput.getText());
            System.out.println("Password: " + passInput.getText());
            boolean lol = sending(nameInput.getText(), passInput.getText());
            System.out.println(lol);
            // Create a new Stage for the second window

            // Stage secondStage = new Stage();
            if(lol == true) {
            Messaging myapp = new Messaging(nameInput.getText(),passInput.getText());
            // pages myapp = new pages();
            // Blogpage myapp = new Blogpage(nameInput.getText(),passInput.getText());
            System.out.println("Username: " + nameInput.getText());
            try {
                // myapp.start(primaryStage,nameInput.getText(),passInput.getText());
                myapp.start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            }
        });
        createButton.setOnAction(e -> {
            createAccount myapp = new createAccount();
            try {
                myapp.start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }        });

       
       
       
       
        card.getChildren().addAll(rect);
        VBox lol = new VBox(10);
        lol.getChildren().addAll(card,mail,pass,buttons);
        root.getChildren().addAll(card,lol);
        StackPane.setMargin(lol, new Insets(50, 0, 0, 0));

        // root.getChildren().addAll(rect, gridPane);
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.show();
        card.setAlignment(Pos.CENTER);

        // For the HBoxes
        cards.setAlignment(Pos.CENTER);
        card.setAlignment(Pos.CENTER);   
        mail.setAlignment(Pos.CENTER);
        pass.setAlignment(Pos.CENTER);
        buttons.setAlignment(Pos.CENTER);
        lol.setAlignment(Pos.CENTER);
        root.setAlignment(Pos.CENTER); // Add this line
    }

    public static void main(String[] args) {
        launch(args);
    }
}
