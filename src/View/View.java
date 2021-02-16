package View;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    public static final int BUTTON_WIDTH = 200;
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

    public View(Stage _stage) {
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

        initMenu();

        _stage.setTitle("Bought Products Manager");
        _stage.setScene(new Scene(this, WIDTH, HEIGHT));
        _stage.show();
    }

    private void initMenu() {
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
            ((Button) button).setMinWidth(BUTTON_WIDTH);
            ((Button) button).setMaxWidth(BUTTON_WIDTH);
        });
        buttons.getChildren().add(0, menuTitle);

        HBox menuBox = new HBox(buttons, new Separator(Orientation.VERTICAL));
        menuBox.setSpacing(15);
        menuBox.setPadding(new Insets(0, 0, 0, 15));
        setLeft(menuBox);
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
}
