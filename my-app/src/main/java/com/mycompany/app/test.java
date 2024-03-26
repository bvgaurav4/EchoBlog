package com.mycompany.app;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class test extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create UI components
        Label titleLabel = new Label("beehive");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));

        Label homeLabel = new Label("Home");
        Label networkLabel = new Label("Network");
        Label productsLabel = new Label("Products");
        Label jobsLabel = new Label("Jobs");
        Label classifiedsLabel = new Label("Classifieds");
        Label pagesLabel = new Label("Pages");
        Label blogLabel = new Label("Blog");
        Label contactLabel = new Label("Contact");
        Label loginLabel = new Label("Login");

        HBox menuBar = new HBox(10);
        menuBar.getChildren().addAll(homeLabel, networkLabel, productsLabel, jobsLabel, classifiedsLabel, pagesLabel, blogLabel, contactLabel, loginLabel);
        menuBar.setAlignment(Pos.CENTER_RIGHT);

        Label topGearLabel = new Label("top gear");
        topGearLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        TextField searchField = new TextField();
        searchField.setPromptText("Search...");
        HBox searchBar = new HBox(10);
        searchBar.getChildren().addAll(topGearLabel, searchField);
        searchBar.setAlignment(Pos.CENTER_RIGHT);

        Image image1 = new Image("/logo.jpg"); 
        ImageView imageView1 = new ImageView(image1);
        imageView1.setFitWidth(200);
        imageView1.setPreserveRatio(true);

        Image image2 = new Image("/logo.jpg"); 
        ImageView imageView2 = new ImageView(image2);
        imageView2.setFitWidth(200);
        imageView2.setPreserveRatio(true);

        // Add your blog post content here
        Label blogPost1Title = new Label("Using Social Network Properly for Businesses");
        Label blogPost1Content = new Label("Benefits of social media for brand building. At vero eos et accusamus et iusto odio dignissimos ducimus qui...");
        Label blogPost1Link = new Label("Continue reading...");

        Label blogPost2Title = new Label("Few Challenges Farmers Face In Agriculture");
        Label blogPost2Content = new Label("Top issues impacting agriculture. At vero eos et iusto odio dignissimos ducimus qui blanditiis...");
        Label blogPost2Link = new Label("Continue reading...");

        VBox blogPostsContainer = new VBox(20);
        blogPostsContainer.getChildren().addAll(
                new HBox(10, imageView1, new VBox(10, blogPost1Title, blogPost1Content, blogPost1Link)),
                new HBox(10, imageView2, new VBox(10, blogPost2Title, blogPost2Content, blogPost2Link))
        );

        Label recentPostsLabel = new Label("Recent Posts");
        recentPostsLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        Label recentPost1 = new Label("Using Social Network Properly for Businesses");
        Label recentPost2 = new Label("Few Challenges Farmers Face In Agriculture");
        Label recentPost3 = new Label("Traveling Is Way Cheaper than You Think");
        Label recentPost4 = new Label("10 Recipes You Can Try At Home Anytime");
        Label recentPost5 = new Label("New Space Discovery Will Surely Shock You");

        VBox recentPostsContainer = new VBox(10);
        recentPostsContainer.getChildren().addAll(recentPostsLabel, recentPost1, recentPost2, recentPost3, recentPost4, recentPost5);

        Label recentCommentsLabel = new Label("Recent Comments");
        recentCommentsLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        VBox contentPane = new VBox(20);
        contentPane.getChildren().addAll(blogPostsContainer, recentPostsContainer, recentCommentsLabel);

        HBox topBar = new HBox(10);
        topBar.getChildren().addAll(titleLabel, menuBar);
        topBar.setAlignment(Pos.CENTER);

        VBox root = new VBox(10);
        root.getChildren().addAll(topBar, searchBar, contentPane);
        root.setPadding(new Insets(20));

        // Create and configure the scene
        Scene scene = new Scene(root, 800, 600);

        // Set the scene and show the stage
        primaryStage.setScene(scene);
        primaryStage.setTitle("Beehive");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}