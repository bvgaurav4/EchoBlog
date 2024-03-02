import javafx.scene.layout.Region;
public class Card extends Region {

    public Card() {
        setPrefSize(300, 200);
        getStyleClass().add("card");
    }
    // public void addContent(Node content) {
    //     getChildren().add(content);
    // }
}