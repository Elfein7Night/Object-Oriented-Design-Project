package Controller;

import Model.StoreCommand;
import View.View;
import View.CreateProductForm;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;

public class Controller {
    private final StoreCommand storeCommand;
    private final View view;
    private CreateProductForm createProductForm;


    public Controller(StoreCommand _storeCommand, View _view) {
        storeCommand = _storeCommand;
        view = _view;

        createProductForm = new CreateProductForm();
        createProductForm.addEventHandlerToSubmitButton(createProductHandler(createProductForm));

        view.addProductBtn.setOnAction(event -> {
            view.setCenter(createProductForm);
        });

    }

    private EventHandler<ActionEvent> createProductHandler(CreateProductForm createProductForm) {
        return event -> {
            //TODO
        };
    }

    private void alertForException(Exception exception, View view) {
        view.showAlert(Alert.AlertType.ERROR, exception.toString());
    }

}
