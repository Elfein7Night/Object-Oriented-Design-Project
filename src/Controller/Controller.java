package Controller;

import Model.MyException;
import Model.StoreCommand;
import View.View;
import View.CreateProductForm;
import View.OrderSelectForm;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;

public class Controller {
    private final StoreCommand storeCommand;
    private final View view;

    public Controller(StoreCommand _storeCommand, View _view) {
        storeCommand = _storeCommand;
        view = _view;

        OrderSelectForm OrderSelectForm = new OrderSelectForm();
        view.replaceLeft(OrderSelectForm);
        OrderSelectForm.addEventHandlerToSubmitButton(event -> {
            if (OrderSelectForm.isFormReady()) {
                storeCommand.initMap(OrderSelectForm.getSelected());
                view.initMenu();
            }
        });

        view.addProductBtn.setOnAction(event -> {
            CreateProductForm createProductForm = new CreateProductForm();
            createProductForm.addEventHandlerToSubmitButton(createProductHandler(createProductForm));
            view.setCenter(createProductForm);
        });

        view.undoBtn.setOnAction(event -> {
            try {
                storeCommand.undoAdd();
                updateForSuccess("Operation Completed Successfully");
            } catch (MyException e) {
                alertForException(e);
            }
        });

        view.showProductBtn.setOnAction(event -> {/*TODO*/});

        view.showAllProductsBtn.setOnAction(event -> view.showAllProducts(storeCommand.getAllProducts()));

        view.deleteProductBtn.setOnAction(event -> {/*TODO*/});

        view.deleteAllProductsBtn.setOnAction(event -> {
            storeCommand.deleteAllProducts();
            updateForSuccess("Deleted All Products");
        });

        view.sendMessageBtn.setOnAction(event -> {/*TODO*/});

        view.showGainsBtn.setOnAction(event -> {/*TODO*/});

        view.showSubscriptionsResponsesBtn.setOnAction(event -> {/*TODO*/});
    }

    private EventHandler<ActionEvent> createProductHandler(CreateProductForm createProductForm) {
        return event -> {
            if (createProductForm.isFormReady()) {
                try {
                    storeCommand.addProduct(
                            createProductForm.getProductName(),
                            createProductForm.getSerialNum(),
                            createProductForm.getStorePrice(),
                            createProductForm.getCustomerPrice(),
                            createProductForm.getCustomerName(),
                            createProductForm.getCustomerPhoneNum(),
                            createProductForm.getCustomerSubscription()
                    );
                    updateForSuccess("Added Successfully!");
                } catch (Exception exception) {
                    alertForException(exception);
                }
            } else {
                view.showAlert(Alert.AlertType.ERROR, "Please Fill All Required Fields!");
            }
        };
    }

    private void updateForSuccess(String message) {
        view.showAlert(Alert.AlertType.INFORMATION, message);
    }

    private void alertForException(Exception exception) {
        view.showAlert(Alert.AlertType.ERROR, exception.toString());
    }

}
