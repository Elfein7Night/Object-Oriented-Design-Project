package Controller;

import Model.StoreCommand;
import View.View;
import javafx.scene.control.Alert;

public class Controller {
    private final StoreCommand storeCommand;
    private final View view;


    public Controller(StoreCommand _storeCommand, View _view) {
        storeCommand = _storeCommand;
        view = _view;

    }


    private void alertForException(Exception exception, View view) {
        view.showAlert(Alert.AlertType.ERROR, exception.toString());
    }

}
