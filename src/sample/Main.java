package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.sql.Connection;

public class Main extends Application {

    private Label userIdLabel;
    private TextField userIdField;
    private Label usernameLabel;
    private Label passwordLabel;
    private TextField usernameField;
    private PasswordField passwordField;

    private Label mainLabel;

    private Button btnLogin;
    private Button btnSignUp;
    private Button btnBack;
    private Button btnSignup;

    private AnchorPane anchorPane;

    private Database database;
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("login");

        Connection connection = ConnectionConfig.getConnection();
        database = new Database(connection);
        database.createTable();

        anchorPane = new AnchorPane();
        primaryStage.setScene(new Scene(anchorPane, 300, 275));
        components();


        primaryStage.show();
    }

    public void components()
    {
        mainLabel = new Label("Login");
        mainLabel.setLayoutX(120);
        mainLabel.setLayoutY(10);
        mainLabel.setStyle("-fx-font-size: 2.5em;-fx-text-fill: green");

        usernameLabel = new Label("Username or userId");
        usernameLabel.setLayoutX(10);
        usernameLabel.setLayoutY(60);


        usernameField = new TextField();
        usernameField.setLayoutX(160);
        usernameField.setLayoutY(60);
        usernameField.setStyle("-fx-background-radius: 20px");
        usernameField.setPrefWidth(100);

        passwordLabel = new Label("Password");
        passwordLabel.setLayoutX(10);
        passwordLabel.setLayoutY(100);

        passwordField = new PasswordField();
        passwordField.setLayoutX(160);
        passwordField.setLayoutY(100);
        passwordField.setStyle("-fx-background-radius: 20px");
        passwordField.setPrefWidth(100);

        anchorPane.getChildren().addAll(mainLabel,usernameLabel,usernameField,passwordLabel,passwordField);

        btnLogin = new Button("Login");
        btnLogin.setLayoutX(220);
        btnLogin.setLayoutY(140);
        btnLogin.setStyle("-fx-background-radius: 20px;-fx-background-color: black;-fx-text-fill: white");
        btnLogin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (usernameField.getText().isEmpty() || passwordField.getText().isEmpty())
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR,"some fields are empty fill it");
                    alert.showAndWait();
                }
                else if (database.checkUserById(usernameField.getText().trim(),passwordField.getText()) || database.checkUserByName(usernameField.getText().trim(),passwordField.getText()))
                {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION,"logged in successfully");
                    alert.showAndWait();
                }
                else
                {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION,"incorrect username or password");
                    alert.showAndWait();
                }
            }
        });

        btnSignUp = new Button("Sign up");
        btnSignUp.setLayoutX(150);
        btnSignUp.setLayoutY(140);
        btnSignUp.setStyle("-fx-background-radius: 20px;-fx-background-color: black;-fx-text-fill: white");
        btnSignUp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                anchorPane.getChildren().removeAll(usernameLabel,usernameField,mainLabel,passwordLabel,passwordField,btnLogin,btnSignUp);
                makeSignUpPage();


            }
        });

        anchorPane.getChildren().addAll(btnLogin,btnSignUp);

    }

    public void makeSignUpPage()
    {
        mainLabel.setText("SignUp");

        userIdLabel =  new Label("User Id");
        userIdLabel.setLayoutX(10);
        userIdLabel.setLayoutY(100);

        userIdField = new TextField("");
        userIdField.setLayoutX(160);
        userIdField.setLayoutY(100);
        userIdField.setStyle("-fx-background-radius: 20px");
        userIdField.setPrefWidth(80);

        usernameLabel = new Label("User Name");
        usernameLabel.setLayoutX(10);
        usernameLabel.setLayoutY(140);

        usernameField = new TextField();
        usernameField.setLayoutX(160);
        usernameField.setLayoutY(140);
        usernameField.setPrefWidth(80);
        usernameField.setStyle("-fx-background-radius: 20px");

        passwordLabel = new Label("Password");
        passwordLabel.setLayoutX(10);
        passwordLabel.setLayoutY(180);

        passwordField = new PasswordField();
        passwordField.setLayoutX(160);
        passwordField.setLayoutY(180);
        passwordField.setPrefWidth(80);
        passwordField.setStyle("-fx-background-radius: 20px");

        anchorPane.getChildren().addAll(mainLabel,userIdLabel,userIdField,usernameLabel,usernameField,passwordLabel,passwordField);

        btnSignup = new Button("Sign Up");
        btnSignup.setLayoutX(200);
        btnSignup.setLayoutY(220);
        btnSignup.setStyle("-fx-background-radius: 20px;-fx-background-color: black;-fx-text-fill: white");
        btnSignup.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (usernameField.getText().isEmpty() || passwordField.getText().isEmpty() || userIdField.getText().isEmpty())
                {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION,"Same Fields are empty");
                    alert.showAndWait();
                }
                else if (database.isPresent(userIdField.getText()))
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR,"We have already a person regarding this user id");
                    alert.showAndWait();
                }
                else
                {
                    database.addData(userIdField.getText(),usernameField.getText(),passwordField.getText());
                    userIdField.clear();
                    usernameField.clear();
                    passwordField.clear();
                }
            }
        });

        btnBack = new Button("Back");
        btnBack.setLayoutX(150);
        btnBack.setLayoutY(220);
        btnBack.setStyle("-fx-background-radius: 20px;-fx-background-color: black;-fx-text-fill: white");
        btnBack.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                anchorPane.getChildren().removeAll(mainLabel,userIdLabel,userIdField,usernameLabel,usernameField,passwordLabel,passwordField,btnSignup,btnBack);
                components();
            }
        });

        anchorPane.getChildren().addAll(btnSignup,btnBack);

    }


    public static void main(String[] args) {
        launch(args);
    }
}
