package Controller;

import Model.Message;
import Model.MyException;
import Model.Product;
import Model.StoreCommand;
import View.View;
import View.CreateProductForm;
import View.OrderSelectForm;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextInputDialog;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
                if (storeCommand.initMap(OrderSelectForm.getSelected())) {
                    updateForSuccess("Loaded Map From File");
                }
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

        view.showProductBtn.setOnAction(event -> operateOnProduct(Operation.ShowProduct));

        view.showAllProductsBtn.setOnAction(event -> view.showProducts(storeCommand.getAllProducts()));

        view.deleteProductBtn.setOnAction(event -> operateOnProduct(Operation.DeleteProduct));

        view.deleteAllProductsBtn.setOnAction(event -> {
            storeCommand.deleteAllProducts();
            updateForSuccess("Deleted All Products");
            view.refreshPage();
        });

        view.sendMessageBtn.setOnAction(event -> {
            String message = getSingularUserInput("Enter Message To Send To Subscribers:", "Message");
            if (message == null) return;
            storeCommand.notifySubscriptions(message);
            updateForSuccess("Message Sent Successfully");
        });

        view.showProfitsBtn.setOnAction(event -> view.showStoreProfits(storeCommand.getProfits()));

        view.showSubscriptionsResponsesBtn.setOnAction(event -> {
                    List<Message> responses = storeCommand.getSubscriptionsResponses();
                    if (responses.isEmpty()) {
                        view.showAlert(AlertType.WARNING, "No Responses To Show...");
                    } else {
                        view.showSubscribersResponses(responses);
                    }
                }
        );
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
                    view.refreshPage();
                    updateForSuccess("Added Successfully!");
                } catch (Exception exception) {
                    alertForException(exception);
                }
            } else {
                view.showAlert(AlertType.ERROR, "Please Fill All Required Fields!");
            }
        };
    }

    enum Operation {DeleteProduct, ShowProduct}

    private void operateOnProduct(Operation operation) {
        String serialNum = getSingularUserInput("Enter the product's serialNum", "Serial Num");
        if (serialNum == null) return;
        Product product = storeCommand.getProduct(serialNum);
        if (product == null) {
            view.showAlert(AlertType.WARNING, "No Product With This Serial Number!");
            return;
        }
        switch (operation) {
            case DeleteProduct:
                storeCommand.deleteProduct(serialNum);
                view.refreshPage();
                updateForSuccess("Operation Completed Successfully");
                break;
            case ShowProduct:
                view.showProducts(Collections.singletonList(product));
                break;
        }
    }

    private void updateForSuccess(String message) {
        view.showAlert(AlertType.INFORMATION, message);
    }

    private void alertForException(Exception exception) {
        view.showAlert(AlertType.ERROR, exception.toString());
    }

    private String getSingularUserInput(String message, String expectedInput) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(null);
        dialog.setHeaderText(message);
        dialog.setContentText(expectedInput + ":");
        Optional<String> result = dialog.showAndWait();
        return result.map(String::valueOf).orElse(null);
    }

}
