import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class WordleUI extends Application {

    // Track where the user is currently typing
    private static int currentRow = 0;
    private static int currentCol = 0;
    private static String answerWord;
    private Label[][] boardTiles = new Label[6][5]; // Store tiles to update them later

    @Override
    public void start(Stage primaryStage) {
        //
        answerWord="funds";
        VBox root = new VBox(34); //
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #121212; -fx-padding: 20px;");

        Label title = new Label("WORDLE");
        title.setFont(Font.font("System", 30));
        title.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(8);
        grid.setVgap(8);

        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 5; col++) {
                Label tile = new Label();
                tile.setAlignment(Pos.CENTER);
                tile.setPrefSize(60, 60);
                tile.setStyle("-fx-border-color: #3a3a3c; -fx-border-width: 2px; -fx-font-size: 24px; -fx-text-fill: white; -fx-font-weight: bold;");

                boardTiles[row][col] = tile; // Save to array
                grid.add(tile, col, row);
            }
        }

        Button enterBtn = new Button("ENTER");
        enterBtn.setPrefSize(100, 50);
        enterBtn.setStyle("-fx-background-color: #818384; -fx-text-fill: white; -fx-font-weight: bold;");

        root.getChildren().addAll(title, grid, enterBtn);

        Scene scene = new Scene(root, 450, 650);

        // --- KEYBOARD LISTENER ---
        scene.setOnKeyPressed(event -> {
            KeyCode code = event.getCode();

            // Handle Backspace
            if (code == KeyCode.BACK_SPACE && currentCol > 0) {
                currentCol--;
                boardTiles[currentRow][currentCol].setText("");
            }
            // Handle Letters (A-Z) and ensure we don't go out of bounds
            else if (code.isLetterKey() && currentCol < 5 && currentRow < 6) {
                String letter = code.toString(); // code.toString() always returns the uppercase letter
                boardTiles[currentRow][currentCol].setText(letter);
                currentCol++;
            }
            // Note: You can add Enter key logic here (code == KeyCode.ENTER)
        });
        enterBtn.setOnAction(event -> {
            // Call your method to check the guess here
            if (currentCol < 5) {
                System.out.println("Word too short!");

            }
            else {
                lockGuess(boardTiles, currentRow);
            }
        });
        primaryStage.setTitle("JavaFX Wordle");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void lockGuess(Label[][] guessor, int row){
        // Only check if the user has typed all 5 letters


        String target = answerWord.toUpperCase(); // Ensure case matches code.toString()
        int greens=0;
        for (int i = 0; i < 5; i++) {
            Label tile = guessor[row][i];
            String letter = tile.getText();

            // 1. Exact Match (Green)
            if (letter.equals(String.valueOf(target.charAt(i)))) {
                tile.setStyle("-fx-background-color: #538d4e; -fx-text-fill: white; -fx-font-size: 24px; -fx-font-weight: bold;");
                greens++;
            }
            // 2. Partial Match (Yellow)
            else if (target.contains(letter)) {
                tile.setStyle("-fx-background-color: #b59f3b; -fx-text-fill: white; -fx-font-size: 24px; -fx-font-weight: bold;");
            }
            // 3. No Match (Grey)
            else {
                tile.setStyle("-fx-background-color: #3a3a3c; -fx-text-fill: white; -fx-font-size: 24px; -fx-font-weight: bold;");
            }
        }
        if(greens==5){
            Label win = new Label("God Joob");
            win.setPrefSize(100, 50);
            win.setStyle("-fx-background-color: #818384; -fx-text-fill: white; -fx-font-weight: bold;");
        }

        // Move to the next row and reset column for the next guess
        currentRow++;
        currentCol = 0;
    }
    public static void main(String[] args) {
        launch(args);
    }
}
