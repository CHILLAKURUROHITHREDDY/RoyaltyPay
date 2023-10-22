package transaction.royaltypay;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.Objects;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;


public class SignLogIn {

    double widthSize(double label){

        return 100 - label;
    }

    void loginPage(Stage loginStage) {

        //Variables
        Label loginLabel, userId, password;
        TextField userIdField;
        PasswordField passwordField;
        Button login, signUp;
        HBox hLayout1, hLayout2, hLayout3;
        VBox vLayout;
        BorderPane bpLayout;


        //Variables properties
        loginLabel = new Label("Log in");
        loginLabel.setId("headTitle");

        userId = new Label("User id : ");
        password = new Label("Password : ");

        userIdField = new TextField();
        userIdField.setId("field");
        userIdField.setPromptText("Enter your user id");
        userIdField.setMinSize(200, 30);

        passwordField = new PasswordField();
        passwordField.setId("field");
        passwordField.setPromptText("Enter your password");
        passwordField.setMinSize(200, 30);

        login = new Button("Log in");
        login.setMinWidth(70);

        signUp = new Button("Sign up");
        signUp.setMinWidth(70);


        //Layouts
        hLayout1 = new HBox();
        hLayout1.setAlignment(Pos.CENTER);
        hLayout1.getChildren().addAll(userId, userIdField);
        hLayout1.setSpacing(widthSize(47.0));

        hLayout2 = new HBox();
        hLayout2.setAlignment(Pos.CENTER);
        hLayout2.getChildren().addAll(password, passwordField);
        hLayout2.setSpacing(widthSize(60.0));

        hLayout3 = new HBox();
        hLayout3.setAlignment(Pos.CENTER);
        hLayout3.setPadding(new Insets(0,0,0, 101));
        hLayout3.getChildren().addAll(login, signUp);
        hLayout3.setSpacing(20);

        vLayout = new VBox();
        vLayout.setAlignment(Pos.CENTER);
        vLayout.setSpacing(20);
        vLayout.getChildren().addAll(hLayout1, hLayout2, hLayout3);

        bpLayout = new BorderPane();
        bpLayout.setCenter(vLayout);
        ApplicationMenuBar applicationMenuBar = new ApplicationMenuBar(loginLabel, loginStage, true);
        bpLayout.setTop(applicationMenuBar.vMenuLayout);


       //Stage and Scene
        Scene loginScene = new Scene(bpLayout,500, 600);
        UserFileClass userFileClass = new UserFileClass();
        if(userFileClass.string.charAt(0) == '0')
            loginScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("dark.css")).toExternalForm());
        else if(userFileClass.string.charAt(0) == '1')
            loginScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("light.css")).toExternalForm());
        loginStage.setScene(loginScene);


        //Controller actions
        login.setOnAction(e -> {
            MySql mySql  = new MySql();
            String result = mySql.valueQuery("SELECT userID FROM royaltypay.pay WHERE userID = '"+userIdField.getText()+"'", "userID");
            if(result.equals(userIdField.getText())){

                if (passwordField.getText().equals(mySql.valueQuery("SELECT password FROM royaltypay.pay WHERE userID = '"+userIdField.getText()+"'", "password"))){

                    UserFileClass userFileClass1 = new UserFileClass();
                    userFileClass1.setString(userFileClass1.string.substring(0,2)+userIdField.getText());
                    Transaction transaction = new Transaction();
                    transaction.home(loginStage);
                }
                else {
                    ApplicationAlertBox applicationAlertBox = new ApplicationAlertBox();
                    applicationAlertBox.messageDisplay("Message", new Label("Please enter a valid password"));
                }
            }
            else{
                ApplicationAlertBox applicationAlertBox = new ApplicationAlertBox();
                applicationAlertBox.messageDisplay("Message", new Label("Please enter a valid user id"));
            }

        });

        signUp.setOnAction(e -> signUpPage(loginStage));


    }

    void signUpPage(Stage signUpStage){

        //Variables
        Label signUpLabel, customerName, bankName, accountNumber , ifscCode, phoneNumber,  userId, password;
        TextField customerNameField, accountNumberField, ifscCodeField, phoneNumberField, userIdField;
        PasswordField passwordField;
        Button back, submit;
        ComboBox<String> bankNameCombo;
        HBox hLayout1, hLayout2, hLayout3, hLayout4, hLayout5, hLayout6, hLayout7, hLayout8;
        VBox vLayout;
        BorderPane bpLayout;


        //Variables properties
        signUpLabel = new Label("Sign Up");
        signUpLabel.setId("headTitle");
        customerName = new Label("Customer name : ");
        bankName = new Label("Bank Name : ");
        accountNumber = new Label("Account number : ");
        ifscCode = new Label("IFSC Code : ");
        phoneNumber = new Label("Phone number : ");
        userId = new Label("User id : ");
        password = new Label("Password : ");

        customerNameField = new TextField();
        customerNameField.setId("field");
        customerNameField.setMinSize(200, 30);
        customerNameField.setPromptText("Enter customer name");
        customerNameField.setTextFormatter(format("[a-zA-Z]*"));

        bankNameCombo = new ComboBox<>();
        bankNameCombo.setId("field");
        bankNameCombo.getItems().addAll("Andhra Bank", "Axis Bank", "Bank of India", "Canara Bank", "HDFC Bank", "ICICI Bank", "Love Bank", "Union Bank");
        bankNameCombo.setMinSize(200,30);
        bankNameCombo.setPromptText("Select bank name");

        accountNumberField = new TextField();
        accountNumberField.setId("field");
        accountNumberField.setMinSize(200, 30);
        accountNumberField.setPromptText("Enter account number");
        accountNumberField.setTextFormatter(format("[0-9]*"));

        ifscCodeField = new TextField();
        ifscCodeField.setId("field");
        ifscCodeField.setMinSize(200,30);
        ifscCodeField.setPromptText("Enter IFSC code");
        ifscCodeField.setTextFormatter(format("[0-9]*"));

        phoneNumberField = new TextField();
        phoneNumberField.setId("field");
        phoneNumberField.setMinSize(200,30);
        phoneNumberField.setPromptText("Enter phone number");
        phoneNumberField.setTextFormatter(format("[0-9]*"));

        userIdField = new TextField();
        userIdField.setId("field");
        userIdField.setMinSize(200,30);
        userIdField.setPromptText("Enter used id");

        passwordField = new PasswordField();
        passwordField.setId("field");
        passwordField.setMinSize(200,30);
        passwordField.setPromptText("Enter password");

        back = new Button("Back");
        back.setMinWidth(70);
        submit = new Button("Submit");
        submit.setMinWidth(70);

        TextField finalAccountNumberField = accountNumberField;
        TextField finalIfscCodeField = ifscCodeField;
        TextField finalPhoneNumberField = phoneNumberField;


        //Layouts
        hLayout1 = new HBox();
        hLayout1.setAlignment(Pos.CENTER);
        hLayout1.getChildren().addAll(customerName, customerNameField);
        hLayout1.setSpacing(widthSize(94.0));

        hLayout2 = new HBox();
        hLayout2.setAlignment(Pos.CENTER);
        hLayout2.getChildren().addAll(bankName, bankNameCombo);
        hLayout2.setSpacing(widthSize(70.0));

        hLayout3 = new HBox();
        hLayout3.setAlignment(Pos.CENTER);
        hLayout3.getChildren().addAll(accountNumber, accountNumberField);
        hLayout3.setSpacing(widthSize(98.0));

        hLayout4 = new HBox();
        hLayout4.setAlignment(Pos.CENTER);
        hLayout4.getChildren().addAll(ifscCode, ifscCodeField);
        hLayout4.setSpacing(widthSize(64.0));

        hLayout5 = new HBox();
        hLayout5.setAlignment(Pos.CENTER);
        hLayout5.getChildren().addAll(phoneNumber, phoneNumberField);
        hLayout5.setSpacing(widthSize(88.0));

        hLayout6 = new HBox();
        hLayout6.setAlignment(Pos.CENTER);
        hLayout6.getChildren().addAll(userId,userIdField);
        hLayout6.setSpacing(widthSize(47.0));

        hLayout7 = new HBox();
        hLayout7.setAlignment(Pos.CENTER);
        hLayout7.getChildren().addAll(password, passwordField);
        hLayout7.setSpacing(widthSize(60.0));

        hLayout8 = new HBox();
        hLayout8.setAlignment(Pos.CENTER);
        hLayout8.getChildren().addAll(back, submit);
        hLayout8.setPadding(new Insets(0,0,0, 100));
        hLayout8.setSpacing(20);

        vLayout = new VBox();
        vLayout.setAlignment(Pos.CENTER);
        vLayout.setSpacing(20);
        vLayout.getChildren().addAll(hLayout1, hLayout2, hLayout3, hLayout4, hLayout5, hLayout6, hLayout7, hLayout8);

        bpLayout = new BorderPane();
        bpLayout.setCenter(vLayout);
        ApplicationMenuBar applicationMenuBar = new ApplicationMenuBar(signUpLabel, signUpStage, true);
        bpLayout.setTop(applicationMenuBar.vMenuLayout);


        //Stage with scene
        Scene signInScene = new Scene(bpLayout,500, 600);
        UserFileClass userFileClass = new UserFileClass();
        if(userFileClass.string.charAt(0) == '0')
            signInScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("dark.css")).toExternalForm());
        else if(userFileClass.string.charAt(0) == '1')
            signInScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("light.css")).toExternalForm());
        signUpStage.setScene(signInScene);


        //Controllers actions
        back.setOnAction(e -> loginPage(signUpStage));

        submit.setOnAction(e -> {

            boolean checkEmpty = !(customerNameField.getText()).isEmpty();
            checkEmpty = checkEmpty && (bankNameCombo.getValue()) != null;
            checkEmpty = checkEmpty && !(finalAccountNumberField.getText()).isEmpty();
            checkEmpty = checkEmpty && !(finalIfscCodeField.getText()).isEmpty();
            checkEmpty = checkEmpty && !(finalPhoneNumberField.getText()).isEmpty();
            checkEmpty = checkEmpty && !(userIdField.getText()).isEmpty();
            checkEmpty = checkEmpty && !(passwordField.getText()).isEmpty();
            if(checkEmpty){

                MySql mySql = new MySql();
                String result = mySql.valueQuery("SELECT userID FROM royaltypay.pay Where userID = '"+userIdField.getText()+"'", "userID");
                if(result.equals(userIdField.getText())){

                    ApplicationAlertBox applicationAlertBox = new ApplicationAlertBox();
                    applicationAlertBox.messageDisplay("Message", new Label("Please try using a different user id"));
                }
                else{

                    MySql mySql1 = new MySql();
                    String result1 = mySql1.valueQuery("SELECT sno FROM royaltypay.pay", "sno");
                    if(result1.equals("0")){
                        String value = "INSERT INTO royaltypay.pay VALUES (1, '"+userIdField.getText()+"', '"+passwordField.getText()+"', '"+customerNameField.getText()+"', '"+bankNameCombo.getValue()+"', "+accountNumberField.getText()+", "+ifscCodeField.getText()+", 100000, "+passwordField.getText()+")";
                        mySql.write(value);
                        loginPage(signUpStage);
                    }
                    else {
                        int sno = Integer.parseInt(mySql.valueQuery("SELECT MAX(sno) AS max_value FROM royaltypay.pay", "max_value"));
                        ++sno;
                        String value = "INSERT INTO royaltypay.pay VALUES ("+sno+", '"+userIdField.getText()+"', '"+passwordField.getText()+"', '"+customerNameField.getText()+"', '"+bankNameCombo.getValue()+"', "+accountNumberField.getText()+", "+ifscCodeField.getText()+", 100000, "+passwordField.getText()+")";
                        mySql.write(value);
                        loginPage(signUpStage);
                    }
                }
            }
            else{

                ApplicationAlertBox applicationAlertBox = new ApplicationAlertBox();
                applicationAlertBox.messageDisplay("Message",new Label("Please ensure that\nall the required fields are filled"));
            }
        });
    }

    TextFormatter<String> format(String pattern){

        UnaryOperator<TextFormatter.Change> filter = change -> {

            String text = change.getText();
            if(Pattern.matches(pattern, text)){
                return change;
            }
            return  null;
        };
        return new TextFormatter<>(filter);

    }

}

