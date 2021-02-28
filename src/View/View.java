package View;

import Model.Observer.Message;
import Model.Pair;
import Model.Product;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
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

import java.util.List;

public class View extends BorderPane {

    public static final int WIDTH = 1100;
    public static final int HEIGHT = 650;
    public static final int MENU_WIDTH = 200;
    public static final int PADDING = 250;

    private final Alert alert;
    public final Button addProductBtn;
    public final Button undoBtn;
    public final Button showProductBtn;
    public final Button showAllProductsBtn;
    public final Button deleteProductBtn;
    public final Button deleteAllProductsBtn;
    public final Button sendMessageBtn;
    public final Button showProfitsBtn;
    public final Button showSubscriptionsResponsesBtn;

    public enum Page {ProductsTable, Profits, AddProduct, Else}

    private Page currentPage;

    public void setCurrentPage(Page page) {
        currentPage = page;
    }

    public View(Stage _stage) {
        super();

        alert = new Alert(AlertType.ERROR);
        alert.setHeaderText(null);

        addProductBtn = new Button("Add Product");
        undoBtn = new Button("Revert to Before Last Add");
        showProductBtn = new Button("Show A Product");
        showAllProductsBtn = new Button("Show All Products");
        deleteProductBtn = new Button("Delete A Product");
        deleteAllProductsBtn = new Button("Delete All Products");
        sendMessageBtn = new Button("Send Message To Subscribers");
        showProfitsBtn = new Button("Show Store Profits");
        showSubscriptionsResponsesBtn = new Button("Show Subscriptions Responses");

        String title = "Bought Products Handler";

        setCenter(getPrettyText(title, 36));
        setBackground(new Background(new BackgroundFill(Color.CADETBLUE, new CornerRadii(0), new Insets(0, 0, 0, 0))));

        setCurrentPage(Page.Else);

        _stage.setTitle(title);
        _stage.setScene(new Scene(this, WIDTH, HEIGHT));
        _stage.show();
    }

    private Text getPrettyText(String msg, int size) {
        Text text = new Text(msg);
        text.setFont(Font.font("Tahoma Bold", FontWeight.BOLD, size));
        text.setFill(Color.GOLDENROD);
        text.setStroke(Color.BLACK);
        return text;
    }

    public void initMenu() {
        Text menuTitle = getPrettyText("Menu:", 24);

        VBox buttons = new VBox(
                addProductBtn,
                undoBtn,
                showProductBtn,
                showAllProductsBtn,
                deleteProductBtn,
                deleteAllProductsBtn,
                showProfitsBtn,
                sendMessageBtn,
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

    public void showAlert(AlertType type, String message) {
        alert.setAlertType(type);
        alert.setContentText(message);
        alert.show();
    }

    public void refreshPage() {
        switch (currentPage) {
            case AddProduct:
            case ProductsTable:
                showAllProductsBtn.fire();
                break;
            case Profits:
                showProfitsBtn.fire();
                break;
            default: {
                // do nothing
            }
        }
    }

    /*
        Build and set TableView to show the products.
        Values are filled with PropertyValueFactory using Product's getters.
     */
    @SuppressWarnings("unchecked")
    public void showProducts(List<Product> products, boolean single) {
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
        customerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));

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

        String title = single ? "Single Product Table:" : "All Products Table:";
        VBox result = new VBox(getPrettyText(title, 30), tableView);
        result.setAlignment(Pos.CENTER);
        result.setSpacing(5);
        setCenter(result);

        setCurrentPage(Page.ProductsTable);
    }

    @SuppressWarnings("unchecked")
    public void showStoreProfits(List<Pair<String, Integer>> profits) {
        TableView<Pair<String, Integer>> tableView = new TableView<>();

        TableColumn<Pair<String, Integer>, String> serialNum = new TableColumn<>("Serial #");
        serialNum.setCellValueFactory(new PropertyValueFactory<>("first"));

        TableColumn<Pair<String, Integer>, Integer> profit = new TableColumn<>("Profit");
        profit.setCellValueFactory(new PropertyValueFactory<>("second"));

        tableView.getItems().addAll(profits);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.getColumns().addAll(serialNum, profit);

        //  Get profit for each product, then sum.
        Text totalProfit = new Text(
                "Total Store Profit: " +
                        profits.stream()
                                .map(Pair::getSecond)
                                .mapToInt(Integer::intValue)
                                .sum()
        );

        totalProfit.setFont(Font.font("Tahoma Bold", FontWeight.BOLD, 16));
        HBox totalBox = new HBox(totalProfit);
        totalBox.setAlignment(Pos.CENTER);

        HBox titleBox = new HBox(getPrettyText("Profits: ", 30));
        titleBox.setAlignment(Pos.CENTER);

        VBox result = new VBox(titleBox, tableView, totalBox);
        result.setAlignment(Pos.CENTER);
        result.setSpacing(15);
        result.setPadding(new Insets(0, PADDING, 0, PADDING));
        result.setMinWidth(View.WIDTH - PADDING);
        result.setMaxWidth(View.WIDTH - PADDING);
        result.setAlignment(Pos.CENTER_LEFT);
        setCenter(result);

        setCurrentPage(Page.Profits);
    }

    public void showSubscribersResponses(List<Message> messages) {
        TextArea textarea = new TextArea();
        textarea.setEditable(false);
        textarea.setStyle(
                "-fx-control-inner-background:#5F9EA0; " +
                        "-fx-highlight-fill: #000000; " +
                        "-fx-highlight-text-fill: #5F9EA0; " +
                        "-fx-text-fill: #000000; " +
                        "-fx-font-size: 14; "
        );
        setCenter(textarea);
        currentPage = Page.Else;

        textarea.appendText("> Responses:\n");

        new Thread(() -> messages.forEach(message -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                showAlert(AlertType.ERROR, e.toString());
                e.printStackTrace();
                System.exit(1);
            }

            Platform.runLater(() -> textarea.appendText(message.toString()));

            if (message.equals(messages.get(messages.size() - 1))) {
                Platform.runLater(() -> textarea.appendText("> End of Responses...\n"));
            }
        })).start();
    }

}
