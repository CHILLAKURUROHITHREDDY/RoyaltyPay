package transaction.royaltypay;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Objects;

public class UpdateUserIdPassword {

    void updateUserId(){

        //Variables
        Label userId, password, newUserId, confirmUserId;
        TextField userIdField, newUserIdField, confirmUserIdField;
        PasswordField passwordField;
        Image icon;
        Button ok, cancel;
        HBox hLayout1, hLayout2, hLayout3, hLayout4, hLayout5;
        VBox vLayout;
        Scene updateUserIdScene;
        Stage updateUserIdStage;


        //Variables properties
        icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("logo.png")));

        userId = new Label("User id : ");
        password = new Label("Password : ");
        newUserId = new Label("New user id : ");
        confirmUserId = new Label("Confirm user id : ");
        ok = new Button("Ok");
        cancel = new Button("Cancel");
        ok.setMinWidth(70);
        cancel.setMinWidth(70);

        userIdField = new TextField();
        userIdField.setId("field");
        userIdField.setMinSize(200,30);
        userIdField.setPromptText("Enter user id");

        passwordField = new PasswordField();
        passwordField.setId("field");
        passwordField.setMinSize(200,30);
        passwordField.setPromptText("Enter password");

        newUserIdField = new TextField();
        newUserIdField.setId("field");
        newUserIdField.setMinSize(200, 30);
        newUserIdField.setPromptText("Enter new user id");

        confirmUserIdField = new TextField();
        confirmUserIdField.setId("field");
        confirmUserIdField.setMinSize(200, 30);
        confirmUserIdField.setPromptText("Confirm user id");


        //Layouts
        hLayout1 = new HBox();
        hLayout1.setAlignment(Pos.CENTER_RIGHT);
        hLayout1.setPadding(new Insets(0,85,20,0));
        hLayout1.getChildren().addAll(userId, userIdField);

        hLayout2 = new HBox();
        hLayout2.setAlignment(Pos.CENTER_RIGHT);
        hLayout2.setPadding(new Insets(0,85,20,0));
        hLayout2.getChildren().addAll(password, passwordField);

        hLayout3 = new HBox();
        hLayout3.setAlignment(Pos.CENTER_RIGHT);
        hLayout3.setPadding(new Insets(0,85,20,0));
        hLayout3.getChildren().addAll(newUserId, newUserIdField);

        hLayout4 = new HBox();
        hLayout4.setAlignment(Pos.CENTER_RIGHT);
        hLayout4.setPadding(new Insets(0,85,20,0));
        hLayout4.getChildren().addAll(confirmUserId, confirmUserIdField);

        hLayout5 = new HBox();
        hLayout5.setAlignment(Pos.CENTER_RIGHT);
        hLayout5.setPadding(new Insets(0,105,0,95));
        hLayout5.getChildren().addAll(ok, cancel);
        hLayout5.setSpacing(20);

        vLayout = new VBox();
        vLayout.setAlignment(Pos.CENTER);
        vLayout.getChildren().addAll(hLayout1, hLayout2, hLayout3, hLayout4, hLayout5);


        //Stage with scenes
        updateUserIdScene = new Scene(vLayout, 400, 350);
        UserFileClass userFileClass = new UserFileClass();
        if(userFileClass.string.charAt(0) == '0')
            updateUserIdScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("dark.css")).toExternalForm());
        else if(userFileClass.string.charAt(0) == '1')
            updateUserIdScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("light.css")).toExternalForm());

        updateUserIdStage = new Stage();
        updateUserIdStage.setTitle("User id update");
        updateUserIdStage.setResizable(false);
        updateUserIdStage.initModality(Modality.APPLICATION_MODAL);
        updateUserIdStage.getIcons().add(icon);
        updateUserIdStage.setScene(updateUserIdScene);
        updateUserIdStage.show();


        //Controllers actions
        ok.setOnAction(e -> {

            UserFileClass userFileClassUserUpdate = new UserFileClass();
            if(userIdField.getText().equals(userFileClassUserUpdate.string.substring(2))){
                MySql mySql = new MySql();
                String result = mySql.valueQuery("SELECT password FROM royaltypay.pay WHERE userID = '"+userIdField.getText()+"'", "password");
                if(result.equals(passwordField.getText())){
                   if(!(newUserIdField.getText().isEmpty() || confirmUserIdField.getText().isEmpty())){
                       if(newUserIdField.getText().equals(confirmUserIdField.getText())){
                           result = mySql.valueQuery("SELECT userID FROM royaltypay.pay WHERE userID = '"+newUserIdField.getText()+"'", "userID");
                          if(result.equals(newUserIdField.getText())){
                              ApplicationAlertBox applicationAlertBox = new ApplicationAlertBox();
                              applicationAlertBox.messageDisplay("Message", new Label("Please try using a different user id"));
                          }
                          else{
                              mySql.write("UPDATE royaltypay.pay SET userID = '"+newUserIdField.getText()+"' WHERE userID = '"+userIdField.getText()+"'");
                              userFileClassUserUpdate.setString(userFileClassUserUpdate.string.substring(0,2)+newUserIdField.getText());
                              updateUserIdStage.close();
                          }
                       }
                       else{
                           ApplicationAlertBox applicationAlertBox = new ApplicationAlertBox();
                           applicationAlertBox.messageDisplay("Message", new Label("The user id conformation has failed"));
                       }
                   }
                   else {
                       ApplicationAlertBox applicationAlertBox = new ApplicationAlertBox();
                       applicationAlertBox.messageDisplay("Message", new Label("Please set userId to change"));
                   }
                }
                else {
                    ApplicationAlertBox applicationAlertBox = new ApplicationAlertBox();
                    applicationAlertBox.messageDisplay("Message", new Label("Please enter a valid password"));
                }
            }
            else{
                ApplicationAlertBox applicationAlertBox = new ApplicationAlertBox();
                applicationAlertBox.messageDisplay("UserId", new Label("Please enter a valid userId"));
            }
        });

        cancel.setOnAction(e -> updateUserIdStage.close());

    }

    void updatePassword(){

        //Variables
        Label userId, password, newPassword, confirmPassword;
        TextField userIdField;
        PasswordField passwordField, newPasswordField, confirmPasswordField;
        Image icon;
        Button ok, cancel;
        HBox hLayout1, hLayout2, hLayout3, hLayout4, hLayout5;
        VBox vLayout;
        Scene updatePasswordScene;
        Stage updatePasswordStage;


        //Variables properties
        icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("logo.png")));

        userId = new Label("User id : ");
        password = new Label("Password : ");
        newPassword = new Label("New password : ");
        confirmPassword = new Label("Confirm password : ");
        ok = new Button("Ok");
        cancel = new Button("Cancel");
        ok.setMinWidth(70);
        cancel.setMinWidth(70);


        userIdField = new TextField();
        userIdField.setId("field");
        userIdField.setMinSize(200,30);
        userIdField.setPromptText("Enter used id");

        passwordField = new PasswordField();
        passwordField.setId("field");
        passwordField.setMinSize(200,30);
        passwordField.setPromptText("Enter password");

        newPasswordField = new PasswordField();
        newPasswordField.setId("field");
        newPasswordField.setMinSize(200,30);
        newPasswordField.setPromptText("Enter new password");

        confirmPasswordField = new PasswordField();
        confirmPasswordField.setId("field");
        confirmPasswordField.setMinSize(200,30);
        confirmPasswordField.setPromptText("Confirm password");


        //Layouts
        hLayout1 = new HBox();
        hLayout1.setAlignment(Pos.CENTER_RIGHT);
        hLayout1.setPadding(new Insets(0,85,20,0));
        hLayout1.getChildren().addAll(userId, userIdField);

        hLayout2 = new HBox();
        hLayout2.setAlignment(Pos.CENTER_RIGHT);
        hLayout2.setPadding(new Insets(0,85,20,0));
        hLayout2.getChildren().addAll(password,passwordField);

        hLayout3 = new HBox();
        hLayout3.setAlignment(Pos.CENTER_RIGHT);
        hLayout3.setPadding(new Insets(0,85,20,0));
        hLayout3.getChildren().addAll(newPassword, newPasswordField);

        hLayout4 = new HBox();
        hLayout4.setAlignment(Pos.CENTER_RIGHT);
        hLayout4.setPadding(new Insets(0,85,20,0));
        hLayout4.getChildren().addAll(confirmPassword, confirmPasswordField);

        hLayout5 = new HBox();
        hLayout5.setAlignment(Pos.CENTER_RIGHT);
        hLayout5.setPadding(new Insets(0,105,0,95));
        hLayout5.getChildren().addAll(ok, cancel);
        hLayout5.setSpacing(20);

        vLayout = new VBox();
        vLayout.setAlignment(Pos.CENTER);
        vLayout.getChildren().addAll(hLayout1, hLayout2, hLayout3, hLayout4, hLayout5);


        //Stage with scene
        updatePasswordScene = new Scene(vLayout, 400, 350);
        UserFileClass userFileClass = new UserFileClass();
        if(userFileClass.string.charAt(0) == '0')
            updatePasswordScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("dark.css")).toExternalForm());
        else if(userFileClass.string.charAt(0) == '1')
            updatePasswordScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("light.css")).toExternalForm());

        updatePasswordStage = new Stage();
        updatePasswordStage.setTitle("Password update");
        updatePasswordStage.setResizable(false);
        updatePasswordStage.initModality(Modality.APPLICATION_MODAL);
        updatePasswordStage.getIcons().add(icon);
        updatePasswordStage.setScene(updatePasswordScene);
        updatePasswordStage.show();


        //Controller actions
        ok.setOnAction(e -> {

            UserFileClass userFileClassUserUpdate = new UserFileClass();
            if(userIdField.getText().equals(userFileClassUserUpdate.string.substring(2))){
                MySql mySql = new MySql();
                String result = mySql.valueQuery("SELECT password FROM royaltypay.pay WHERE userID = '"+userIdField.getText()+"'", "password");
                if(result.equals(passwordField.getText())){
                    if(!(newPasswordField.getText().isEmpty() || confirmPasswordField.getText().isEmpty())){
                        if(newPasswordField.getText().equals(confirmPasswordField.getText())){
                            mySql.write("UPDATE royaltypay.pay SET password = '"+newPasswordField.getText()+"' WHERE userID = '"+userIdField.getText()+"'");
                            updatePasswordStage.close();
                        }
                        else{
                            ApplicationAlertBox applicationAlertBox = new ApplicationAlertBox();
                            applicationAlertBox.messageDisplay("Message", new Label("The password conformation has\nfailed"));
                        }
                    }
                    else{
                        ApplicationAlertBox applicationAlertBox = new ApplicationAlertBox();
                        applicationAlertBox.messageDisplay("Message", new Label("Please set password to change"));
                    }
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

        cancel.setOnAction(e -> updatePasswordStage.close());
    }

}
