package View;

import javafx.geometry.Pos;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;

import java.util.Arrays;

public class OrderSelectForm extends Form {

    private final RadioButton alphabetical, reversedAlphabetical, insertOrder;

    public OrderSelectForm() {
        super("Submit Order");
        ToggleGroup toggleGroup = new ToggleGroup();
        alphabetical = new RadioButton("Alphabetical Order");
        reversedAlphabetical = new RadioButton("Reversed Alphabetical Order");
        insertOrder = new RadioButton("Insert Order");
        Arrays.asList(
                alphabetical,
                reversedAlphabetical,
                insertOrder
        ).forEach(radioButton -> {
            View.setCursorAsSelectInRegion(radioButton);
            radioButton.setToggleGroup(toggleGroup);
        });

        alphabetical.setSelected(true);

        getChildren().addAll(
                new Text("Please Select How To Sort The Products:"),
                alphabetical,
                reversedAlphabetical,
                insertOrder,
                submitButton
        );

        setSpacing(20);
        setMinWidth(View.MENU_WIDTH);
        setMaxWidth(View.MENU_WIDTH);
        setAlignment(Pos.CENTER_LEFT);
    }

    @Override
    public boolean isFormReady() {
        return alphabetical.isSelected() || reversedAlphabetical.isSelected() || insertOrder.isSelected();
    }

    public int getSelected() {
        if (insertOrder.isSelected()) return 3;
        if (reversedAlphabetical.isSelected()) return 2;
        return 1;
    }

}
