package com.mycompany.app;

import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.shape.Rectangle;


public class MessageContainer extends BorderPane {
    private VBox textContainer;
    private Text titleLabel;
    private Text contentText;
    private Text email;
    private ImageView imageView;
    public MessageContainer() {
        super();

        textContainer = new VBox();
        textContainer.setSpacing(5);
        textContainer.setMaxWidth(500);


        titleLabel = new Text();
        titleLabel.setWrappingWidth(500);
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;-fx-fill: #8F00FF;");

        contentText = new Text();
        contentText.setWrappingWidth(500);

        textContainer.getChildren().addAll(titleLabel, contentText);


        setPadding(new Insets(10));
        setCenter(textContainer);
        setLeft(email);

        Rectangle borderRect = new Rectangle();
        borderRect.setFill(null);
        borderRect.setStroke(Color.web("#8F00FF", 0.7));
        borderRect.setStrokeWidth(1.5);
        setBorder(Border.EMPTY);

        setMaxWidth(Double.MAX_VALUE);
    }

    public void setTitle(String title) {
        titleLabel.setText(title);

    }

    public void setContent(String content) {
        contentText.setText(content);
    }

    public void setImage(String imageUrl) {
        Image image = new Image(imageUrl);
        imageView.setImage(image);
    }
    public void setemail(String email) {
        this.email.setText(email);
    }
}
