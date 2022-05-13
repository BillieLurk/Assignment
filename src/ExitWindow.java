package src;

import java.util.Optional;

import javafx.scene.control.ButtonType;

public class ExitWindow extends InputDialog {

    public ExitWindow() {
        super(AlertType.CONFIRMATION);
        setTitle("Warning!");
        setHeaderText("Unsaved changes, exit anyway?");
    }

    public boolean spawnExitWindow() {
        Optional<ButtonType> result = showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }
}