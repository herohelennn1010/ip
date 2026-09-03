package sophon.ui;

import javafx.geometry.Pos;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class DialogBox extends HBox {
    private Label text;
    private ImageView profilePicture;

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
}
