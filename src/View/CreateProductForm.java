package View;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.Arrays;

public class CreateProductForm extends VBox {
    public final TextField productName,
            serialNum,
            storePrice,
            customerPrice,
            customerName,
            customerPhoneNum;
    public final CheckBox customerSubscription;
    private final Button submitButton;

    public CreateProductForm() {
        super();
        productName = new TextField();
        serialNum = new TextField();
        storePrice = new TextField();
        customerPrice = new TextField();
        customerName = new TextField();
        customerPhoneNum = new TextField();
        customerSubscription = new CheckBox("Customer Subscription");
        submitButton = new Button("Submit Product");

        HBox customerSubscriptionBox = new HBox(customerSubscription);
        customerSubscriptionBox.setAlignment(Pos.CENTER);

        HBox submitBox = new HBox(submitButton);
        submitBox.setAlignment(Pos.CENTER);

        getChildren().addAll(
                new Text("Please Fill All Fields:"),
                new Text("Product:"),
                View.getAlignedTextField("Product Name: ", productName),
                View.getAlignedTextField("Serial Number: ", serialNum),
                View.getAlignedTextField("Store Price: ", storePrice),
                View.getAlignedTextField("Customer Price: ", customerPrice),
                new Text("Customer:"),
                View.getAlignedTextField("Customer Name: ", customerName),
                View.getAlignedTextField("Customer Phone Number: ", customerPhoneNum),
                customerSubscriptionBox,
                submitBox
        );

        setMinWidth(850);
        setMaxWidth(850);
        setAlignment(Pos.CENTER_LEFT);
        setSpacing(10);
        setPadding(new Insets(0, 250, 0, 250));
    }

    public void addEventHandlerToSubmitButton(EventHandler<ActionEvent> eventHandler) {
        submitButton.setOnAction(eventHandler);
        setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                submitButton.fire();
            }
        });
    }

    public boolean getAllFilled() {
        return !(productName.getText().trim().isEmpty() ||
                serialNum.getText().trim().isEmpty() ||
                customerName.getText().trim().isEmpty() ||
                customerPhoneNum.getText().trim().isEmpty()
        );
    }

    public void clear() {
        Arrays.asList(
                productName,
                serialNum,
                storePrice,
                customerPrice,
                customerName,
                customerPhoneNum
        ).forEach(textField -> textField.setText(""));
    }

}
