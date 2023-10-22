package transaction.royaltypay;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.Objects;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class Transaction {

    Boolean result = false;

    void home(Stage homeStage){

        //Variables
        Label homeLabel, userName;
        Button sendAmount, receiveAmount, checkAmount;
        BorderPane bpLayout;
        VBox vLayout1, vLayout2;
        Scene homeScene;


        //Variables properties
        homeLabel = new Label("Royalty Pay");
        homeLabel.setId("homeLabel");
        userName = new Label();
        userName.setId("userName");

        sendAmount = new Button("Send Amount");
        sendAmount.setId("homeButtons");
        sendAmount.setMinSize(500, 50);
        receiveAmount = new Button("Receive Amount");
        receiveAmount.setId("homeButtons");
        receiveAmount.setMinSize(500, 50);
        checkAmount = new Button("Check Amount");
        checkAmount.setId("homeButtons");
        checkAmount.setMinSize(500, 50);


        //Layouts
        vLayout1 = new VBox(sendAmount, receiveAmount, checkAmount);
        vLayout1.setSpacing(5);
        vLayout1.setAlignment(Pos.CENTER);
        bpLayout = new BorderPane();
        ApplicationMenuBar applicationMenuBar = new ApplicationMenuBar(homeLabel, homeStage, false);
        MySql mySql = new MySql();
        UserFileClass userFileClass = new UserFileClass();
        String user = mySql.valueQuery("SELECT username FROM royaltypay.pay WHERE userID = '"+userFileClass.string.substring(2)+"'", "username");
        userName.setText(user);
        vLayout2 = new VBox(applicationMenuBar.vMenuLayout, userName);
        vLayout2.setAlignment(Pos.CENTER);
        vLayout2.setSpacing(30);
        bpLayout.setTop(vLayout2);
        bpLayout.setCenter(vLayout1);


        //Stage with scene
        homeScene = new Scene(bpLayout);
        if(userFileClass.string.charAt(0) == '0')
            homeScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("dark.css")).toExternalForm());
        else if(userFileClass.string.charAt(0) == '1')
            homeScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("light.css")).toExternalForm());
        homeStage.setScene(homeScene);


        //Controller actions
        sendAmount.setOnAction(e -> sendAmount());

        receiveAmount.setOnAction(e -> receiveAmount());

        checkAmount.setOnAction(e -> checkAmount());

    }

    void sendAmount(){

        //Variables
        Label userId, amount;
        TextField userIdField, amountField;
        Image icon;
        Button send, cancel;
        HBox hLayout1, hLayout2, hLayout3;
        VBox vLayout;
        Scene sendAmountScene;
        Stage sendAmountStage;


        //Variables properties
        icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("logo.png")));

        userId = new Label("User id : ");
        amount = new Label("Amount : ");
        send = new Button("Ok");
        cancel = new Button("Cancel");
        send.setMinWidth(70);
        cancel.setMinWidth(70);

        userIdField = new TextField();
        userIdField.setId("field");
        userIdField.setMinSize(200,30);
        userIdField.setPromptText("Enter receiver used id");

        amountField = new TextField();
        amountField.setId("field");
        amountField.setTextFormatter(format());

        amountField.setMinSize(200,30);
        amountField.setPromptText("Enter amount");


        //Layouts
        hLayout1 = new HBox();
        hLayout1.setAlignment(Pos.CENTER_RIGHT);
        hLayout1.setPadding(new Insets(0,85,20,0));
        hLayout1.getChildren().addAll(userId, userIdField);

        hLayout2 = new HBox();
        hLayout2.setAlignment(Pos.CENTER_RIGHT);
        hLayout2.setPadding(new Insets(0,85,20,0));
        hLayout2.getChildren().addAll(amount, amountField);

        hLayout3 = new HBox();
        hLayout3.setAlignment(Pos.CENTER_RIGHT);
        hLayout3.setPadding(new Insets(0,105,0,95));
        hLayout3.getChildren().addAll(send, cancel);
        hLayout3.setSpacing(20);

        vLayout = new VBox();
        vLayout.setAlignment(Pos.CENTER);
        vLayout.getChildren().addAll(hLayout1, hLayout2, hLayout3);


        //Stage with scene
        sendAmountScene = new Scene(vLayout, 400, 350);
        UserFileClass userFileClass = new UserFileClass();
        if(userFileClass.string.charAt(0) == '0')
            sendAmountScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("dark.css")).toExternalForm());
        else if(userFileClass.string.charAt(0) == '1')
            sendAmountScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("light.css")).toExternalForm());

        sendAmountStage = new Stage();
        sendAmountStage.setTitle("Amount sending");
        sendAmountStage.setResizable(false);
        sendAmountStage.initModality(Modality.APPLICATION_MODAL);
        sendAmountStage.getIcons().add(icon);
        sendAmountStage.setScene(sendAmountScene);
        sendAmountStage.show();


        //Controller actions
        send.setOnAction(e -> {

            UserFileClass userFileClassSend = new UserFileClass();
            if(!userFileClassSend.string.substring(2).equals(userIdField.getText())){
                MySql mySql = new MySql();
                String result = mySql.valueQuery("SELECT userID FROM royaltypay.pay WHERE userID = '"+userIdField.getText()+"'", "userID");
                if(result.equals(userIdField.getText())){

                    if(!(amountField.getText().isEmpty())){
                        paying(sendAmountStage, userFileClassSend.string.substring(2), userIdField.getText(), amountField.getText(), true);
                    }
                    else{
                        ApplicationAlertBox applicationAlertBox = new ApplicationAlertBox();
                        applicationAlertBox.messageDisplay("Message", new Label("Please enter a valid amount"));
                    }
                }
                else{

                    ApplicationAlertBox applicationAlertBox = new ApplicationAlertBox();
                    applicationAlertBox.messageDisplay("Message", new Label("Please enter a valid receiver user id"));
                }
            }
            else {

                ApplicationAlertBox applicationAlertBox = new ApplicationAlertBox();
                applicationAlertBox.messageDisplay("Message", new Label("The entered receiver ID and sender ID are the same"));
            }
        });

        cancel.setOnAction(e -> sendAmountStage.close());

    }

    void receiveAmount(){

        //Variables
        Label userId, amount;
        TextField userIdField, amountField;
        Image icon;
        Button send, cancel;
        HBox hLayout1, hLayout2, hLayout3;
        VBox vLayout;
        Scene sendAmountScene;
        Stage sendAmountStage;


        //Variables properties
        icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("logo.png")));

        userId = new Label("User id : ");
        amount = new Label("Amount : ");
        send = new Button("Ok");
        cancel = new Button("Cancel");
        send.setMinWidth(70);
        cancel.setMinWidth(70);

        userIdField = new TextField();
        userIdField.setId("field");
        userIdField.setMinSize(200,30);
        userIdField.setPromptText("Enter sender used id");

        amountField = new TextField();
        amountField.setId("field");
        amountField.setTextFormatter(format());

        amountField.setMinSize(200,30);
        amountField.setPromptText("Enter amount");


        //Layouts
        hLayout1 = new HBox();
        hLayout1.setAlignment(Pos.CENTER_RIGHT);
        hLayout1.setPadding(new Insets(0,85,20,0));
        hLayout1.getChildren().addAll(userId, userIdField);

        hLayout2 = new HBox();
        hLayout2.setAlignment(Pos.CENTER_RIGHT);
        hLayout2.setPadding(new Insets(0,85,20,0));
        hLayout2.getChildren().addAll(amount, amountField);

        hLayout3 = new HBox();
        hLayout3.setAlignment(Pos.CENTER_RIGHT);
        hLayout3.setPadding(new Insets(0,105,0,95));
        hLayout3.getChildren().addAll(send, cancel);
        hLayout3.setSpacing(20);

        vLayout = new VBox();
        vLayout.setAlignment(Pos.CENTER);
        vLayout.getChildren().addAll(hLayout1, hLayout2, hLayout3);


        //Stage with scene
        sendAmountScene = new Scene(vLayout, 400, 350);
        UserFileClass userFileClass = new UserFileClass();
        if(userFileClass.string.charAt(0) == '0')
            sendAmountScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("dark.css")).toExternalForm());
        else if(userFileClass.string.charAt(0) == '1')
            sendAmountScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("light.css")).toExternalForm());
        sendAmountStage = new Stage();
        sendAmountStage.setTitle("Amount receiving");
        sendAmountStage.setResizable(false);
        sendAmountStage.initModality(Modality.APPLICATION_MODAL);
        sendAmountStage.getIcons().add(icon);
        sendAmountStage.setScene(sendAmountScene);
        sendAmountStage.show();


        //Controller actions
        send.setOnAction(e -> {
            UserFileClass userFileClassReceive = new UserFileClass();
            if(!userFileClassReceive.string.substring(2).equals(userIdField.getText())){
                MySql mySql = new MySql();
                String result = mySql.valueQuery("SELECT userID FROM royaltypay.pay WHERE userID = '"+userIdField.getText()+"'", "userID");
                if(result.equals(userIdField.getText())){
                    if(!(amountField.getText().isEmpty())){
                        paying(sendAmountStage, userIdField.getText(), userFileClassReceive.string.substring(2), amountField.getText(), false);
                    }
                    else{
                        ApplicationAlertBox applicationAlertBox = new ApplicationAlertBox();
                        applicationAlertBox.messageDisplay("Message", new Label("Please enter a valid amount"));
                    }
                }
                else {
                    ApplicationAlertBox applicationAlertBox = new ApplicationAlertBox();
                    applicationAlertBox.messageDisplay("Message", new Label("Please enter a valid sender user id"));
                }
            }
            else{
                ApplicationAlertBox applicationAlertBox = new ApplicationAlertBox();
                applicationAlertBox.messageDisplay("Message", new Label("The entered sender ID and receiver ID are the same"));
            }
        });

        cancel.setOnAction(e -> sendAmountStage.close());

    }

    void paying(Stage  payingStage, String sendId, String receiveId, String amountPaying, boolean user){

        //Variables
        Label userId, amount, password;
        Button send, cancel;
        PasswordField passwordField;
        HBox hLayout1, hLayout2;
        VBox vLayout;
        Scene payingScene;


        //Variables properties
        if(user)
            userId = new Label("User id : "+receiveId);
        else
            userId = new Label("User id : "+sendId);
        amount = new Label("Amount : "+amountPaying);
        password = new Label("Password : ");

        passwordField = new PasswordField();
        passwordField.setId("field");
        passwordField.setMinSize(200,30);
        passwordField.setPromptText("Enter password");

        send =new Button("send");
        cancel = new Button("Cancel");
        send.setMinWidth(70);
        cancel.setMinWidth(70);


        //Layouts
        hLayout1 = new HBox();
        hLayout1.setAlignment(Pos.CENTER_RIGHT);
        hLayout1.setPadding(new Insets(0,85,20,0));
        hLayout1.getChildren().addAll(password, passwordField);

        hLayout2 = new HBox();
        hLayout2.setAlignment(Pos.CENTER_RIGHT);
        hLayout2.setPadding(new Insets(0,105,0,95));
        hLayout2.getChildren().addAll(send, cancel);
        hLayout2.setSpacing(20);

        vLayout = new VBox(userId, amount, hLayout1, hLayout2);
        vLayout.setAlignment(Pos.CENTER);
        vLayout.setSpacing(20);


        //Stage with scene
        payingScene =  new Scene(vLayout, 400, 350);
        UserFileClass userFileClass = new UserFileClass();
        if(userFileClass.string.charAt(0) == '0')
            payingScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("dark.css")).toExternalForm());
        else if(userFileClass.string.charAt(0) == '1')
            payingScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("light.css")).toExternalForm());

        payingStage.setScene(payingScene);


        //Controller actions
        send.setOnAction(e -> {

            MySql mySql = new MySql();
            String result = mySql.valueQuery("SELECT password FROM royaltypay.pay WHERE userID = '"+sendId+"'","password");
            if(result.equals(passwordField.getText())){

                result = mySql.valueQuery("SELECT amount FROM royaltypay.pay WHERE userID = '"+sendId+"'", "amount");
                double senderAmount = Double.parseDouble(result);
                double amountPay = Double.parseDouble(amountPaying);
                if(amountPay <= senderAmount){

                    mySql.write("UPDATE royaltypay.pay SET amount = "+(senderAmount-amountPay)+" WHERE userId = '"+sendId+"'");
                    result = mySql.valueQuery("SELECT amount FROM royaltypay.pay WHERE userID = '"+receiveId+"'", "amount");
                    double receiverAmount = Double.parseDouble(result);
                    mySql.write("UPDATE royaltypay.pay SET amount = "+(receiverAmount+amountPay)+" WHERE userId = '"+receiveId+"'");
                    payingStage.close();
                    ApplicationAlertBox applicationAlertBox = new ApplicationAlertBox();
                    applicationAlertBox.messageDisplay("Status", new Label(sendId+"\nsuccessfully sent "+amountPaying+"RS\nto "+receiveId));
                }
                else {
                    ApplicationAlertBox applicationAlertBox = new ApplicationAlertBox();
                    applicationAlertBox.messageDisplay("Balance", new Label("The amount entered is insufficient"));
                }
            }
            else{
                ApplicationAlertBox applicationAlertBox = new ApplicationAlertBox();
                applicationAlertBox.messageDisplay("Message", new Label("The password entered is incorrect"));
            }
        });
        cancel.setOnAction(e -> payingStage.close());
    }

    Boolean password(){

        //Variables
        Label password;
        PasswordField passwordField;
        Button ok, cancel;
        Image icon;
        HBox hLayout1, hLayout2;
        VBox vLayout;
        Scene passwordScene;
        Stage passwordStage;


        //Variables properties
        icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("logo.png")));

        password = new Label("Password : ");

        passwordField = new PasswordField();
        passwordField.setId("field");
        passwordField.setMinSize(200,30);
        passwordField.setPromptText("Enter password");

        ok = new Button("Ok");
        cancel = new Button("Cancel");
        ok.setMinWidth(70);
        cancel.setMinWidth(70);


        //Layouts
        hLayout1 = new HBox();
        hLayout1.setAlignment(Pos.CENTER_RIGHT);
        hLayout1.setPadding(new Insets(0,85,20,0));
        hLayout1.getChildren().addAll(password, passwordField);

        hLayout2 = new HBox();
        hLayout2.setAlignment(Pos.CENTER_RIGHT);
        hLayout2.setPadding(new Insets(0,105,0,95));
        hLayout2.getChildren().addAll(ok, cancel);
        hLayout2.setSpacing(20);

        vLayout = new VBox(hLayout1, hLayout2);
        vLayout.setAlignment(Pos.CENTER);
        vLayout.setSpacing(20);


        //Stage with scene
        passwordScene =  new Scene(vLayout, 400, 350);
        UserFileClass userFileClass = new UserFileClass();
        if(userFileClass.string.charAt(0) == '0')
            passwordScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("dark.css")).toExternalForm());
        else if(userFileClass.string.charAt(0) == '1')
            passwordScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("light.css")).toExternalForm());

        passwordStage = new Stage();
        passwordStage.setTitle("Password verification");
        passwordStage.setResizable(false);
        passwordStage.initModality(Modality.APPLICATION_MODAL);
        passwordStage.getIcons().add(icon);
        passwordStage.setScene(passwordScene);


        //Controller actions
        passwordStage.setOnShowing(e ->{
            ok.setOnAction(event -> {
                UserFileClass userFileClassPassword = new UserFileClass();
                String temp = userFileClassPassword.string.substring(2);
                MySql mySql = new MySql();
                temp = mySql.valueQuery("SELECT password FROM royaltypay.pay WHERE userID = '"+temp+"'", "password");
                if(temp.equals(passwordField.getText())){
                    setResult(true);
                    passwordStage.close();
                }
                else{
                    setResult(false);
                    ApplicationAlertBox applicationAlertBox = new ApplicationAlertBox();
                    applicationAlertBox.messageDisplay("Message", new Label("The password entered is incorrect"));
                }
            });

            cancel.setOnAction(event -> {
                setResult(false);
                passwordStage.close();
            });
        });
        passwordStage.showAndWait();

        return result;
    }

    void checkAmount() {

        if(password()){

            UserFileClass userFileClass = new UserFileClass();
            MySql mySql = new MySql();
            String amount = mySql.valueQuery("SELECT amount FROM royaltypay.pay WHERE userID = '"+userFileClass.string.substring(2)+"'", "amount");
            ApplicationAlertBox applicationAlertBox = new ApplicationAlertBox();
            applicationAlertBox.messageDisplay("Balance", new Label("Your account balance\n"+amount+"RS"));

        }
    }

    void setResult(Boolean result){
        this.result = result;
    }

    TextFormatter<String> format(){

        UnaryOperator<TextFormatter.Change> filter = change -> {

            String text = change.getText();
            if(Pattern.matches("[0-9]*", text)){
                return change;
            }
            return  null;
        };
        return new TextFormatter<>(filter);

    }

}
