import Controller.Controller;
import Model.Command.Store;
import View.View;
import javafx.application.Application;
import javafx.stage.Stage;

public class Program extends Application {

    @Override
    public void start(Stage stage) {
        new Controller(
                new Store(),
                new View(stage)
        );
    }

    public static void main(String[] args) {
        launch(args);
    }

}