import Controller.Controller;
import Model.StoreCommand;
import View.View;
import javafx.application.Application;
import javafx.stage.Stage;

public class Program extends Application {

    @Override
    public void start(Stage stage) {
        new Controller(
                new StoreCommand(),
                new View(stage)
        );
    }

    public static void main(String[] args) {
        launch(args);
    }

}