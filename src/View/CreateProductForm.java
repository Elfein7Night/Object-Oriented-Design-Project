package View;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.util.Arrays;

public class CreateProductForm extends Form {
    public final TextField productName,
            serialNum,
            storePrice,
            customerPrice,
            customerName,
            customerPhoneNum;
    public final CheckBox customerSubscription;

    public CreateProductForm() {
        super("Submit Product");
        productName = new TextField();
        serialNum = new TextField();
        storePrice = new TextField();
        customerPrice = new TextField();
        customerName = new TextField();
        customerPhoneNum = new TextField();
        customerSubscription = new CheckBox("Customer Subscription");

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

    @Override
    public boolean isFormReady() {
        return !(productName.getText().trim().isEmpty() ||
                serialNum.getText().trim().isEmpty() ||
                customerName.getText().trim().isEmpty() ||
                customerPhoneNum.getText().trim().isEmpty()
        );
    }
}
