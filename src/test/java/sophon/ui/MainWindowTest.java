package sophon.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sophon.Sophon;

public class MainWindowTest extends ApplicationTest {
    @TempDir
    private Path temporaryDirectory;

    private VBox dialogContainer;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(MainWindow.class.getResource("/view/MainWindow.fxml"));
        Parent root = loader.load();
        MainWindow mainWindow = loader.getController();
        mainWindow.setSophon(new Sophon(temporaryDirectory.toString(), "tasks.txt"), stage);

        stage.setScene(new Scene(root));
        stage.show();
        dialogContainer = lookup("#dialogContainer").queryAs(VBox.class);
    }

    @Test
    public void start_displaysGreeting() {
        Set<String> messages = getDisplayedMessages();

        assertEquals(1, dialogContainer.getChildren().size());
        assertTrue(messages.stream().anyMatch(message -> message.contains("Hi. I'm Sophon.")));
    }

    @Test
    public void sendTodo_displaysUserInputAndResponse() {
        clickOn("#userInput").write("todo read book");
        clickOn("#sendButton");
        WaitForAsyncUtils.waitForFxEvents();

        Set<String> messages = getDisplayedMessages();
        assertEquals(3, dialogContainer.getChildren().size());
        assertTrue(messages.contains("todo read book"));
        assertTrue(messages.stream().anyMatch(message -> message.contains("Recorded. A new task")));
    }

    @Test
    public void sendBlankInput_doesNotAddDialog() {
        clickOn("#userInput").write("   ");
        clickOn("#sendButton");
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals(1, dialogContainer.getChildren().size());
    }

    private Set<String> getDisplayedMessages() {
        return lookup(".label").queryAllAs(Label.class).stream()
                .map(Label::getText)
                .collect(Collectors.toSet());
    }
}
