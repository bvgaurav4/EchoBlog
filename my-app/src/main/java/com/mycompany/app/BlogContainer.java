import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.control.*;

public class BlogContainer extends BorderPane {
    private VBox textContainer;
    private Text titleLabel;
    private Text contentText;
    private String email;
    private ImageView imageView;

    public BlogContainer() {
        super();

        textContainer = new VBox();
        textContainer.setSpacing(5);

        Button button = new Button("like");
        Button commnet = new Button("comment");

        titleLabel = new Text();
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;-fx-fill: #8F00FF;");

        contentText = new Text();

        textContainer.getChildren().addAll(titleLabel, contentText);
        textContainer.getChildren().addAll(button, commnet);
        imageView = new ImageView();
        imageView.setFitWidth(200);
        imageView.setPreserveRatio(true);

        setPadding(new Insets(10));
        setCenter(textContainer);
        setLeft(imageView);

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
}
