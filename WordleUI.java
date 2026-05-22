import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.util.Scanner;

public class WordleUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(15);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #121212; -fx-padding: 20px;");

        Label title = new Label("WORDLE");
        title.setFont(Font.font("System", 30));
        title.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");

        GridPane grid = new GridPane(); //creates the base grid for the letters
        grid.setAlignment(Pos.CENTER); //keep the guess grid in the middle
        grid.setHgap(8); //proper horizontal spacing
        grid.setVgap(8); //proper vertical spacing

        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 5; col++) {
                Label tile = new Label();
                tile.setAlignment(Pos.CENTER);
                tile.setPrefSize(60, 60);
                tile.setStyle("-fx-border-color: #3a3a3c; -fx-border-width: 2px; -fx-font-size: 24px; -fx-text-fill: white; -fx-font-weight: bold;");
                grid.add(tile, col, row);
            }
        }

        // 2. Create a basic placeholder button for the keyboard
        Button enterBtn = new Button("ENTER");
        enterBtn.setPrefSize(100, 50);
        enterBtn.setStyle("-fx-background-color: #818384; -fx-text-fill: white; -fx-font-weight: bold;");

        root.getChildren().addAll(title, grid, enterBtn);

        Scene scene = new Scene(root, 450, 650);
        primaryStage.setTitle("JavaFX Wordle");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
        Scanner input = new Scanner(System.in);

    }
}
