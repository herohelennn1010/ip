package sophon.ui;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
    @FXML
    private Label text;

    @FXML
    private ImageView profilePicture;

    /**
     * Creates a dialog box with the given text and avatar image.
     *
     * @param s text to show.
     * @param i avatar image to show.
     */
    private DialogBox(String s, Image i) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        text.setText(s);
        profilePicture.setImage(i);
    }

    private void flip() {
        this.setAlignment(Pos.TOP_LEFT);
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        FXCollections.reverse(tmp);
        this.getChildren().setAll(tmp);
    }

    /**
     * Creates a dialog box for messages from the user.
     *
     * @param s text to show.
     * @param i avatar image to show.
     * @return dialog box for a user message.
     */
    public static DialogBox getUserDialog(String s, Image i) {
        return new DialogBox(s, i);
    }

    /**
     * Creates a dialog box for messages from Sophon.
     *
     * @param s text to show.
     * @param i avatar image to show.
     * @return dialog box for a Sophon message.
     */
    public static DialogBox getSophonDialog(String s, Image i) {
        var db = new DialogBox(s, i);
        db.flip();
        return db;
    }
}
