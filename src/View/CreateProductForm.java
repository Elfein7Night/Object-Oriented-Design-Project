package View;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;


public class CreateProductForm extends Form {
    private final TextField productName,
            serialNum,
            storePrice,
            customerPrice,
            customerName,
            customerPhoneNum;
    private final CheckBox customerSubscription;

    public String getProductName() {
        return productName.getText();
    }

    public String getSerialNum() {
        return serialNum.getText();
    }

    public int getStorePrice() {
        return storePrice.getText().isEmpty() ? 0 :
                Integer.parseInt(storePrice.getText());
    }

    public int getCustomerPrice() {
        return customerPrice.getText().isEmpty() ? 0 :
                Integer.parseInt(customerPrice.getText());
    }

    public String getCustomerName() {
        return customerName.getText();
    }

    public String getCustomerPhoneNum() {
        return customerPhoneNum.getText();
    }

    public boolean getCustomerSubscription() {
        return customerSubscription.isSelected();
    }


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
                new Text("Please Fill All Required Fields: ( * - required )"),
                new Text("Product:"),
                View.getAlignedTextField("Product Name: ", productName),
                View.getAlignedTextField("* Serial Number: ", serialNum),
                View.getAlignedTextField("Store Price: ", storePrice),
                View.getAlignedTextField("Customer Price: ", customerPrice),
                new Text("Customer:"),
                View.getAlignedTextField("Customer Name: ", customerName),
                View.getAlignedTextField("Customer Phone Number: ", customerPhoneNum),
                customerSubscriptionBox,
                submitBox
        );

        setPadding(new Insets(0, View.PADDING, 0, View.PADDING));
        setMinWidth(View.WIDTH - View.PADDING);
        setMaxWidth(View.WIDTH - View.PADDING);
        setAlignment(Pos.CENTER_LEFT);
        setSpacing(10);
    }

//    public void clear() {
//        Arrays.asList(
//                productName,
//                serialNum,
//                storePrice,
//                customerPrice,
//                customerName,
//                customerPhoneNum
//        ).forEach(textField -> textField.setText(""));
//    }

    @Override
    public boolean isFormReady() {
        return !serialNum.getText().trim().isEmpty();
    }
}
