package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class TicTacToe extends Application {
    private boolean xTurn = true;
    private boolean gameEnded = false;

    @Override
    public void start(Stage stage) {
        Label infoText = new Label("Turn: X");
        infoText.setFont(Font.font("MonoSpaced", 40));
        GridPane groupComponents = new GridPane();
        Button[][] buttons = new Button[3][3];
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                Button btn = new Button(" ");
                btn.setFont(Font.font("Monospaced", 40));
                btn.setPrefSize(100, 100);
                btn.setOnAction((event -> {
                    if (!gameEnded && btn.getText().equals(" ")) {
                        if (xTurn) {
                            btn.setText("X");
                            infoText.setText("Turn: O");
                        } else {
                            btn.setText("O");
                            infoText.setText("Turn: X");
                        }
                        xTurn = !xTurn;
                        if (checkWinner(buttons)) {
                            infoText.setText("The winner is " + (xTurn ? "0" : "X") + "!");
                            gameEnded = true;
                        } else if (isBordFull(buttons)) {
                            infoText.setText("It is a draw!");
                            gameEnded = true;
                        }
                    }
                }));
                buttons[row][col] = btn;
                groupComponents.add(btn, col, row);
            }
            groupComponents.setAlignment(Pos.CENTER);
        }
        Button restartButton = new Button("Play Again");
        restartButton.setFont(Font.font("Monospaced", 20));
        restartButton.setOnAction((event -> resetGame(buttons, infoText)));
        HBox bottomBox = new HBox(restartButton);
        bottomBox.setAlignment(Pos.CENTER);
        BorderPane layout = new BorderPane();
        layout.setTop(infoText);
        layout.setCenter(groupComponents);
        layout.setBottom(bottomBox);
        layout.setPadding(new Insets(20, 20, 20, 20));
        Scene view = new Scene(layout, 500, 450);
        stage.setTitle("TicTacToe");
        stage.setScene(view);
        stage.show();
    }

    private boolean checkWinner(Button[][] buttons) {
        for (int i = 0; i < 3; i++) {
            if (checkRow(buttons, i) || checkCol(buttons, i)) {
                return true;
            }
        }
        return checkDiagonals(buttons);
    }

    private boolean checkDiagonals(Button[][] buttons) {
        return (buttons[0][0].getText().equals(buttons[1][1].getText()) &&
                buttons[1][1].getText().equals(buttons[2][2].getText()) &&
                !buttons[0][0].getText().equals(" ")) ||
                (buttons[0][2].getText().equals(buttons[1][1].getText()) &&
                        buttons[1][1].getText().equals(buttons[2][0].getText()) &&
                        !buttons[0][2].getText().equals(" "));
    }

    private boolean checkCol(Button[][] buttons, int col) {
        return buttons[0][col].getText().equals(buttons[1][col].getText()) &&
                buttons[1][col].getText().equals(buttons[2][col].getText()) &&
                !buttons[0][col].getText().equals(" ");
    }

    private boolean checkRow(Button[][] buttons, int row) {
        return buttons[row][0].getText().equals(buttons[row][1].getText()) &&
                buttons[row][1].getText().equals(buttons[row][2].getText()) &&
                !buttons[row][0].getText().equals(" ");
    }

    private boolean isBordFull(Button[][] buttons) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (buttons[row][col].getText().equals(" ")) {
                    return false;
                }
            }
        }
        return true;
    }

    public void resetGame(Button[][] buttons, Label infoText) {
        xTurn = true;
        gameEnded = false;
        infoText.setText("Turn: X");
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setText(" ");
            }
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
