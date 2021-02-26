package Controller;

import Model.*;
import Model.Command.*;
import Model.Observer.Message;
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
    private final Store store;
    private final View view;

    public Controller(Store _store, View _view) {
        store = _store;
        view = _view;

        OrderSelectForm OrderSelectForm = new OrderSelectForm();
        view.replaceLeft(OrderSelectForm);
        OrderSelectForm.addEventHandlerToSubmitButton(event -> {
            if (OrderSelectForm.isFormReady()) {
                if (store.initMap(OrderSelectForm.getSelected())) {
                    updateForSuccess("Loaded Products From File");
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
                new RevertToBeforeLastAddCommand(store).execute();
                updateForSuccess("Operation Completed Successfully");
                view.refreshPage();
            } catch (MyException e) {
                alertForException(e);
            }
        });

        view.showProductBtn.setOnAction(event -> operateOnProduct(Operation.ShowProduct));

        view.showAllProductsBtn.setOnAction(event -> view.showProducts(new GetAllProductsCommand(store).get()));

        view.deleteProductBtn.setOnAction(event -> operateOnProduct(Operation.DeleteProduct));

        view.deleteAllProductsBtn.setOnAction(event -> {
            new DeleteAllProductsCommand(store).execute();
            updateForSuccess("Deleted All Products");
            view.refreshPage();
        });

        view.showProfitsBtn.setOnAction(event -> view.showStoreProfits(new GetProfitsCommand(store).get()));

        view.sendMessageBtn.setOnAction(event -> {
            String message = getSingularUserInput("Enter Message To Send To Subscribers:", "Message");
            if (message == null) return;

            new NotifySubscriptionsCommand(store, message).execute();
            updateForSuccess("Message Sent Successfully");
        });

        view.showSubscriptionsResponsesBtn.setOnAction(event -> {
                    List<Message> responses = new GetSubscriptionsResponsesCommand(store).get();
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
                    new AddProductCommand(
                            store,
                            createProductForm.getProductName(),
                            createProductForm.getSerialNum(),
                            createProductForm.getStorePrice(),
                            createProductForm.getCustomerPrice(),
                            createProductForm.getCustomerName(),
                            createProductForm.getCustomerPhoneNum(),
                            createProductForm.getCustomerSubscription()
                    ).execute();

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
        String serialNum = getSingularUserInput("Enter The Product's Serial Number", "Serial Num");
        if (serialNum == null) return;

        Product product = new GetProductCommand(store, serialNum).get();
        if (product == null) {
            view.showAlert(AlertType.WARNING, "No Product With This Serial Number!");
            return;
        }

        switch (operation) {
            case DeleteProduct:
                new DeleteProductCommand(store, serialNum).execute();
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
