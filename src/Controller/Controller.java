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
    private final CreateProductForm createProductForm;


    public Controller(StoreCommand _storeCommand, View _view) {
        storeCommand = _storeCommand;
        view = _view;

        createProductForm = new CreateProductForm();
        createProductForm.addEventHandlerToSubmitButton(createProductHandler(createProductForm));

        view.addProductBtn.setOnAction(event -> view.setCenter(createProductForm));

    }

    private EventHandler<ActionEvent> createProductHandler(CreateProductForm createProductForm) {
        return event -> {
            if (createProductForm.getAllFilled()) {
                try {
                    int storePrice = createProductForm.storePrice.getText().isEmpty() ? 0 :
                            Integer.parseInt(createProductForm.storePrice.getText());
                    int customerPrice = createProductForm.customerPrice.getText().isEmpty() ? 0 :
                            Integer.parseInt(createProductForm.customerPrice.getText());

                    storeCommand.addProduct(
                            createProductForm.productName.getText(),
                            createProductForm.serialNum.getText(),
                            storePrice,
                            customerPrice,
                            createProductForm.customerName.getText(),
                            createProductForm.customerPhoneNum.getText(),
                            createProductForm.customerSubscription.isSelected()
                    );
                    updateForSuccess();
                    createProductForm.clear();
                } catch (Exception exception) {
                    alertForException(exception);
                }
            } else {
                view.showAlert(Alert.AlertType.ERROR, "Please Fill All Fields!");
            }
        };
    }

    private void updateForSuccess() {
        view.showAlert(Alert.AlertType.INFORMATION, "Added Successfully!");
//        view.refreshCurrentView();
    }

    private void alertForException(Exception exception) {
        view.showAlert(Alert.AlertType.ERROR, exception.toString());
    }

}
