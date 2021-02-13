package Controller;

import Model.Store;
import View.View;
import javafx.scene.control.Alert;

public class Controller {
    private final Store store;
    private final View view;


    public Controller(Store _store, View _view) {
        store = _store;
        view = _view;

    }


    private void alertForException(Exception exception, View view) {
        view.showAlert(Alert.AlertType.ERROR, exception.toString());
    }

}
