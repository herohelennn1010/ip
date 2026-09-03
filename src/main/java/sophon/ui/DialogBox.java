package sophon.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Shows one chat message with an avatar.
 */
public class DialogBox extends HBox {
    private Label text;
    private ImageView profilePicture;

    /**
     * Creates a dialog box with the given text and avatar image.
     *
     * @param s text to show.
     * @param i avatar image to show.
     */
    public DialogBox(String s, Image i) {
        text = new Label(s);
        profilePicture = new ImageView(i);

        //Styling the dialog box
        text.setWrapText(true);
        profilePicture.setFitWidth(100.0);
        profilePicture.setFitHeight(100.0);
        this.setAlignment(Pos.TOP_RIGHT);

        this.getChildren().addAll(text, profilePicture);
    }

    private void flip() {
        this.setAlignment(Pos.TOP_LEFT);
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        FXCollections.reverse(tmp);
        this.getChildren().setAll(tmp);
    }

    public static DialogBox getUserDialog(String s, Image i) {
        return new DialogBox(s, i);
    }

    public static DialogBox getSophonDialog(String s, Image i) {
        var db = new DialogBox(s, i);
        db.flip();
        return db;
    }
}
