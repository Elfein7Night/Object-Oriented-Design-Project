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
import java.util.Map;

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

    List list = new ArrayList();


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

//        initMenu();

        // TODO test blat !
        Product p1 = new Product("Elazar", "123", 12, 13, new Customer("BeniSela", "054366631", false));
        list.add(p1);
        showAllProducts(list);

        _stage.setTitle("Bought Products Manager");
        _stage.setScene(new Scene(this, WIDTH, HEIGHT));
        _stage.show();


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

    public void showAllProducts(List<Product> products) {
        TableView<Product> tableView = new TableView<>();
        TableColumn<Product, String> name = new TableColumn<>("Name");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        name.setMaxWidth(80);
        name.setMinWidth(80);

        TableColumn<Product, String> serialNum = new TableColumn<>("SerialNum");
        serialNum.setCellValueFactory(new PropertyValueFactory<>("serialNum"));
        serialNum.setMaxWidth(80);
        serialNum.setMinWidth(80);

        TableColumn<Product, Integer> storePrice = new TableColumn<>("StorePrice");
        serialNum.setCellValueFactory(new PropertyValueFactory<>("storePrice"));
        storePrice.setMaxWidth(80);
        storePrice.setMinWidth(80);

        TableColumn<Product, Integer> customerPrice = new TableColumn<>("CustomerPrice");
        serialNum.setCellValueFactory(new PropertyValueFactory<>("customerPrice"));
        customerPrice.setMaxWidth(100);
        customerPrice.setMinWidth(100);

        TableColumn<Product, String> customerName = new TableColumn<>("CustomerName");
        serialNum.setCellValueFactory(new PropertyValueFactory<>("name"));
        customerName.setMaxWidth(100);
        customerName.setMinWidth(100);

        TableColumn<Product, String> customerPhoneNumber = new TableColumn<>("CustomerPhoneNumber");
        serialNum.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        customerPhoneNumber.setMaxWidth(150);
        customerPhoneNumber.setMinWidth(150);

        TableColumn<Product, Boolean> subscribedStatus = new TableColumn<>("SubscribedStatus");
        serialNum.setCellValueFactory(new PropertyValueFactory<>("subscribedStatus"));
        subscribedStatus.setMaxWidth(110);
        subscribedStatus.setMinWidth(110);

        tableView.getColumns().addAll(name, serialNum, storePrice, customerPrice, customerName, customerPhoneNumber, subscribedStatus);
        tableView.getItems().addAll(products);
        tableView.getSortOrder().addAll(name);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        VBox result = new VBox(tableView);
        result.setAlignment(Pos.CENTER);
        result.setSpacing(5);
        setCenter(result);
    }
}
