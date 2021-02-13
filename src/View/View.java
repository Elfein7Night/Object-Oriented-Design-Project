package View;

import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class View {
    private final Stage stage;
    private final Alert alert;

    public View(Stage _stage) {
        stage = _stage;
        alert = new Alert(Alert.AlertType.ERROR);
    }


    static void setCursorAsSelectInRegion(Region region) {
        region.setCursor(Cursor.HAND);
    }

    public void showAlert(Alert.AlertType type, String message) {
        alert.setAlertType(type);
        alert.setContentText(message);
        alert.setHeaderText(null);
        alert.show();
    }
}
