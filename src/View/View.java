package View;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class View extends BorderPane {
    public static final int WIDTH = 1100;
    public static final int HEIGHT = 650;
    private final Stage stage;
    private final Alert alert;
    private final Button addProductBtn;
    private final Button undoBtn;
    private final Button showProductBtn;
    private final Button showAllProductsBtn;
    private final Button deleteProductBtn;
    private final Button deleteAllProductsBtn;
    private final Button sendMessageBtn;
    private final Button showGainsBtn;
    private final Button showSubscriptionsResponsesBtn;

    public View(Stage _stage) {
        stage = _stage;
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

        Text menuTitle = new Text("Menu:");
        menuTitle.setFont(Font.font("Tahoma Bold", FontWeight.BOLD, 20));
        menuTitle.setFill(Color.GOLDENROD);
        menuTitle.setStroke(Color.BLACK);

        VBox buttons = new VBox(
                menuTitle,
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

        HBox menuBox = new HBox(buttons, new Separator(Orientation.VERTICAL));
        menuBox.setSpacing(15);
        menuBox.setPadding(new Insets(0,0,0,15));
        setLeft(menuBox);

        stage.setTitle("Bought Products Manager");
        stage.setScene(new Scene(this, WIDTH, HEIGHT));
        stage.show();
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
}
