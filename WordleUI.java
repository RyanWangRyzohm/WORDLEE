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
import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class WordleUI extends Application {

    // Track where the user is currently typing
    private static int currentRow = 0; // instance variable that tracks the current row typing.
    private static int currentCol = 0; // instance variable that tracks where the typed letter is within the row.
    private static String answerWord; // the word that is the answer.
    private Label[][] boardTiles = new Label[6][5]; // Store blank tile 2d array to update them later.
    private static int greens = 0;

    @Override
    public void start(Stage primaryStage) {
        // Declaration of the frame and the answer word.
        // Used AI to generate a few of the words
        String[] words = {"shard", "slate", "crane", "lucky", "learn", "court", "snack", "crate", "plant", "train", "beach", "goofy", "eager", "earth", "inbox", "fizzy", "paver", "smite", "gnome", "wench", "crazy", "crypt", "gypsy", "igloo", "slang", "sahur", "wrong", "onion", "young", "drunk", "funky", "aptly", "hence", "voice", "trust", "fiery", "oxide", "robin", "surge", "below", "couch", "agree", "truck", "steer", "round", "taboo", "daisy", "fiber", "zesty", "clown", "boing", "quack", "bloop", "squid", "jazzy", "mango", "bacon", "spork", "toots", "fluff", "moose", "goose", "quirk", "wacky", "ninja", "banjo", "buzzy", "jumpy", "girth", "dizzy", "swoop", "bling", "doink", "snort", "floop", "gravy", "munch", "zoink", "scoff", "derpy", "cluck", "woozy", "toast", "prank", "chonk", "smirk", "vroom", "rhyme", "yacht", "stoic", "elude", "vodka", "cacao", "llama", "nymph", "pixel", "queue", "hotel"};
        int random = (int) (Math.random() * words.length);
        
        answerWord = words[random];
        VBox root = new VBox(34); // vertical box with 34 px spacing.
        root.setAlignment(Pos.CENTER); // center frame
        root.setStyle("-fx-background-color: #121212; -fx-padding: 20px;"); // set color and padding amount in pixels.
        // Add and format the label (initialize label, set its font and set the color and bolding).
        Label title = new Label("WORDY"); // set the title of the program
        title.setFont(Font.font("System", 30)); // set the title font to System size 30
        title.setStyle("-fx-text-fill: white; -fx-font-weight: bold;"); // set the title style to white and bold
        // creates and formats the grid
        GridPane grid = new GridPane(); // creates new grid object
        grid.setAlignment(Pos.CENTER); // centers the grid object
        grid.setHgap(8); // sets the horizontal gap/interval to 8
        grid.setVgap(8); // sets the vertical gap/interval to 8
        
        // creates all the tiles using a 2d array style nested for loop
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 5; col++) {
                Label tile = new Label();
                // individual tile formatting
                tile.setAlignment(Pos.CENTER); 
                tile.setPrefSize(60, 60);
                tile.setStyle("-fx-border-color: #3a3a3c; -fx-border-width: 2px; -fx-font-size: 24px; -fx-text-fill: white; -fx-font-weight: bold;");
                // adds to the array of tiles to be used later
                boardTiles[row][col] = tile; // Save to array
                grid.add(tile, col, row);
            }
        }

        Button enterBtn = new Button("ENTER"); //creation of the ENTER button
        enterBtn.setPrefSize(100, 50);
        enterBtn.setStyle("-fx-background-color: #818384; -fx-text-fill: white; -fx-font-weight: bold;");

        root.getChildren().addAll(title, grid, enterBtn);

        Scene scene = new Scene(root, 450, 650);

        // typer and remover
        scene.setOnKeyPressed(event -> {
            if (currentRow >= 6)
            {
                if(greens!=5)
                {
                    showPopup("Sorry, out of guesses, the word is: "+answerWord);
                }
                return; // added to prevent crashing
            }
            
            KeyCode code = event.getCode(); // get the current key press and the letter

            // Handle Backspace/removal of letters
            if (code == KeyCode.BACK_SPACE && currentCol > 0) {
                currentCol--;
                boardTiles[currentRow][currentCol].setText("");
            }
            // Handle Letters (A-Z) and ensure the code doesn't go out of bounds
            else if (code.isLetterKey() && currentCol < 5 && currentRow < 6) {
                String letter = code.toString(); // code.toString() always returns the uppercase letter
                boardTiles[currentRow][currentCol].setText(letter);
                currentCol++;
            }
            // the enter button detector
            else if (code == KeyCode.ENTER)
            {
                if (currentCol < 5)
                {
                    showPopup("Word too short");
                }
                else
                {
                    lockGuess(boardTiles, currentRow);
                }
            }
        });
        // the enter button/lock answer button action
        enterBtn.setOnAction(event -> {
            // Call method to check the guess here
            if (currentCol < 5) {
                showPopup("Not Enough Letters");
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
        // Precondition: Only check if the user has typed all 5 letters
        // Locks the guess in place and moves to the next row 

        String[] guess = new String[5];
        boolean[] matched = new boolean[5]; // tracks which ones are matched. Starts with all as false.

        for (int i = 0; i<5; i++)
            {
                guess[i] = guessor[row][i].getText();
            }

        String target = answerWord.toUpperCase(); // Ensure case matches code.toString()
        greens=0;

        
        for (int i = 0; i < 5; i++) {
            Label tile = guessor[row][i];

            // Find exact matches (green)
            if (guess[i].equals(String.valueOf(target.charAt(i)))) {
                tile.setStyle("-fx-background-color: #538d4e; -fx-text-fill: white; -fx-font-size: 24px; -fx-font-weight: bold;");
                greens++;
                matched[i] = true;
            }
        }
        // Find letters in wrong spot (yellow) and letters not contained (gray)
        for (int i = 0; i < 5; i++)
            {
                Label tile = guessor[row][i];
                String letter = tile.getText();
                boolean foundYellow = false;
                
                if (letter.equals(String.valueOf(target.charAt(i))))
                {
                    continue; //skips if already green
                }    
                
                // 2. Partial Match (Yellow)
                for (int j = 0; j < 5; j++)
                {
                    if (!matched[j] && guess[i].equals(String.valueOf(target.charAt(j))))
                    {
                        matched [j] = true;
                        foundYellow = true;
                        break;
                    }
                }

                if (foundYellow)
                {
                    tile.setStyle("-fx-background-color: #b59f3b; -fx-text-fill: white; -fx-font-size: 24px; -fx-font-weight: bold;"); // yellow
                }
                else
                {
                    tile.setStyle("-fx-background-color: #3a3a3c; -fx-text-fill: white; -fx-font-size: 24px; -fx-font-weight: bold;"); // grey
                }
            }


        
        if(greens==5)
        {
            showPopup("Good Job");
        }

        // Move to the next row and reset column for the next guess
        currentRow++;
        currentCol = 0;
    }
    
    public static void showPopup(String message)
    // This method makes a pop-up message appear -- easy to reuse!
    {
        Label popupLabel = new Label(message);
        popupLabel.setStyle("-fx-background-color: #3a3a3c; -fx-text-fill: white; -fx-font-size: 24px; -fx-font-weight: bold;-fx-padding: 20px;"); 
        Stage popup = new Stage();

        VBox layout = new VBox(popupLabel);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #121212; -fx-padding: 20px;");

        Scene popupScene = new Scene(layout, 300, 150);

        popup.setScene(popupScene);
        popup.show();

        //make the popup automatically close after 1.5 seconds

        // Used AI to learn how to set a timer and an event that takes place at the timer's end.
        PauseTransition timer = new PauseTransition(Duration.seconds(1.5));
        timer.setOnFinished(event -> popup.close());
        timer.play();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
