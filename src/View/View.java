package View;

import Model.Customer;
import Model.MyException;
import Model.Product;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class View extends BorderPane {
    public static final int WIDTH = 1100;
    public static final int HEIGHT = 650;
    public static final int MENU_WIDTH = 200;
    private final Alert alert;
    public final Button addProductBtn;
    public final Button undoBtn;
    public final Button showProductBtn;
    public final Button showAllProductsBtn;
    public final Button deleteProductBtn;
    public final Button deleteAllProductsBtn;
    public final Button sendMessageBtn;
    public final Button showGainsBtn;
    public final Button showSubscriptionsResponsesBtn;

    public View(Stage _stage) throws MyException {
        super();

        alert = new Alert(Alert.AlertType.ERROR);

        addProductBtn = new Button("Add Product");
        undoBtn = new Button("Undo Last Add");
        showProductBtn = new Button("Show A Product");
        showAllProductsBtn = new Button("Show All Products");
        deleteProductBtn = new Button("Delete A Product");
        deleteAllProductsBtn = new Button("Delete All Products");
        sendMessageBtn = new Button("Send Message To Subscribers");
        showGainsBtn = new Button("Show Store Gains");
        showSubscriptionsResponsesBtn = new Button("Show Subscriptions Responses");

//        testTableView();

        _stage.setTitle("Bought Products Manager");
        _stage.setScene(new Scene(this, WIDTH, HEIGHT));
        _stage.show();
    }

//    private void test(){
//        TextArea textarea = new TextArea();
//        textarea.appendText("");
//
//        List<Message> messages = new ArrayList<>();
//
//        messages.forEach(message -> textarea.appendText(message.getMsg()));
//    }

    private void testTableView() throws MyException {
        List<Product> list = new ArrayList<>();
        Product p1 = new Product("Elazar", "123", 12, 21, new Customer("BeniSela", "054366631", false));
        list.add(p1);
        showAllProducts(list);
    }

    public void initMenu() {
        Text menuTitle = new Text("Menu:");
        menuTitle.setFont(Font.font("Tahoma Bold", FontWeight.BOLD, 20));
        menuTitle.setFill(Color.GOLDENROD);
        menuTitle.setStroke(Color.BLACK);

        VBox buttons = new VBox(
                addProductBtn,
                undoBtn,
                showProductBtn,
                showAllProductsBtn,
                deleteProductBtn,
                deleteAllProductsBtn,
                sendMessageBtn,
                showGainsBtn,
                showSubscriptionsResponsesBtn
        );
        buttons.setSpacing(15);
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().forEach(button -> {
            ((Button) button).setMinWidth(MENU_WIDTH);
            ((Button) button).setMaxWidth(MENU_WIDTH);
        });
        buttons.getChildren().add(0, menuTitle);

        replaceLeft(buttons);
    }

    public void replaceLeft(Pane pane) {
        HBox left = new HBox(pane, new Separator(Orientation.VERTICAL));
        left.setSpacing(15);
        left.setPadding(new Insets(0, 0, 0, 15));
        setLeft(left);
    }

    public static HBox getAlignedTextField(String name, TextField textField) {
        HBox hb = new HBox();
        Text txt = new Text(name);
        hb.getChildren().addAll(txt, textField);
        hb.setAlignment(Pos.CENTER_RIGHT);
        HBox.setMargin(txt, new Insets(10, 0, 10, 0));
        return hb;
    }

    static void setCursorAsSelectInRegion(Region region) {
        region.setCursor(Cursor.HAND);
    }

    public void showAlert(Alert.AlertType type, String message) {
        alert.setAlertType(type);
        alert.setContentText(message);
        alert.setHeaderText(null);
        alert.show();
    }

    @SuppressWarnings("unchecked")
    public void showAllProducts(List<Product> products) {
        TableView<Product> tableView = new TableView<>();

        TableColumn<Product, String> serialNum = new TableColumn<>("Serial #");
        serialNum.setCellValueFactory(new PropertyValueFactory<>("serialNum"));

        TableColumn<Product, String> name = new TableColumn<>("Product Name");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Product, Integer> storePrice = new TableColumn<>("Store Price");
        storePrice.setCellValueFactory(new PropertyValueFactory<>("storePrice"));

        TableColumn<Product, Integer> customerPrice = new TableColumn<>("Customer Price");
        customerPrice.setCellValueFactory(new PropertyValueFactory<>("customerPrice"));

        TableColumn<Product, String> customerName = new TableColumn<>("Customer Name");
        customerName.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Product, String> customerPhoneNumber = new TableColumn<>("Customer Phone #");
        customerPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        TableColumn<Product, Boolean> subscribedStatus = new TableColumn<>("Subscribed");
        subscribedStatus.setCellValueFactory(new PropertyValueFactory<>("subscribedStatus"));
        subscribedStatus.setMinWidth(80);
        subscribedStatus.setMaxWidth(80);

        tableView.getColumns().addAll(
                serialNum,
                name,
                storePrice,
                customerPrice,
                customerName,
                customerPhoneNumber,
                subscribedStatus
        );
        tableView.getItems().addAll(products);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        VBox result = new VBox(tableView);
        result.setAlignment(Pos.CENTER);
        result.setSpacing(5);
        setCenter(result);
    }
}
