package transaction.royaltypay;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.Objects;


public class ApplicationMenuBar {

    VBox vMenuLayout;

    ApplicationMenuBar(Label headTitle, Stage stage, Boolean disable) {

        //Variables
        Menu menu;
        MenuBar applicationMenu;
        MenuItem themes, passwordSaver, userIdChange, passwordChange, delete, logout;


        //Variables properties
        themes = new MenuItem("");
        themes.setId("menuItem");
        UserFileClass userFileClass = new UserFileClass();
        if(userFileClass.string.charAt(0) == '0')
            themes.setText("Light");
        else if(userFileClass.string.charAt(0) == '1')
            themes.setText("Dark");

        passwordSaver = new MenuItem("");
        passwordSaver.setId("menuItem");
        if(userFileClass.string.charAt(1) =='0')
            passwordSaver.setText("Save Password");
        else if(userFileClass.string.charAt(1) == '1')
            passwordSaver.setText("Remove Password");
        passwordSaver.setDisable(disable);

        userIdChange = new MenuItem("Update user Id");
        userIdChange.setId("menuItem");
        userIdChange.setDisable(disable);

        passwordChange = new MenuItem("Update password");
        passwordChange.setId("menuItem");
        passwordChange.setDisable(disable);

        delete = new MenuItem("Delete account");
        delete.setId("menuItem");
        delete.setDisable(disable);

        logout = new MenuItem("Log out");
        logout.setId("menuItem");
        logout.setDisable(disable);

        menu = new Menu("_RP");
        menu.setId("menuItem");
        menu.getItems().addAll(themes, passwordSaver, userIdChange, passwordChange, delete, logout);

        applicationMenu = new MenuBar();
        applicationMenu.setId("menuStyle");
        applicationMenu.setPadding(new Insets(5,0,5,5));
        applicationMenu.getMenus().add(menu);


        //Layouts
        vMenuLayout = new VBox();
        vMenuLayout.setAlignment(Pos.CENTER);
        vMenuLayout.setSpacing(30);
        vMenuLayout.getChildren().addAll(applicationMenu, headTitle);


        //Controller actions
        themes.setOnAction(e ->{
            Scene scene = stage.getScene();
            UserFileClass userFileClassTheme = new UserFileClass();
            if(userFileClassTheme.string.charAt(0) == '1'){
                themes.setText("Light");
                userFileClassTheme.setString("0"+userFileClassTheme.string.substring(1));
                scene.getStylesheets().remove(Objects.requireNonNull(getClass().getResource("light.css")).toExternalForm());
                scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("dark.css")).toExternalForm());
            }
            else if(userFileClassTheme.string.charAt(0) == '0') {
                themes.setText("Dark");
                userFileClassTheme.setString("1"+userFileClassTheme.string.substring(1));
                scene.getStylesheets().remove(Objects.requireNonNull(getClass().getResource("dark.css")).toExternalForm());
                scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("light.css")).toExternalForm());
            }
            stage.setScene(scene);
        });

        passwordSaver.setOnAction(e -> {

            UserFileClass userFileClassPasswordSaver = new UserFileClass();
            if(userFileClassPasswordSaver.string.charAt(1) == '0'){
                passwordSaver.setText("Remove Password");
                userFileClassPasswordSaver.setString(userFileClassPasswordSaver.string.charAt(0)+"1"+userFileClassPasswordSaver.string.substring(2));

            }
            else if (userFileClassPasswordSaver.string.charAt(1) == '1') {
                passwordSaver.setText("Save Password");
                userFileClassPasswordSaver.setString(userFileClassPasswordSaver.string.charAt(0)+"0"+userFileClassPasswordSaver.string.substring(2));

            }
        });

        userIdChange.setOnAction(e -> {
            UpdateUserIdPassword updateUserIdPassword = new UpdateUserIdPassword();
            updateUserIdPassword.updateUserId();
        });

        passwordChange.setOnAction(e -> {
            UpdateUserIdPassword updateUserIdPassword = new UpdateUserIdPassword();
            updateUserIdPassword.updatePassword();
        });

        delete.setOnAction(e -> {

            ApplicationAlertBox applicationAlertBox = new ApplicationAlertBox();
            if(applicationAlertBox.alertBox(new Label("Deleting account"), new Label("Please confirm your account deletion request"))){
                Transaction transaction = new Transaction();
                if(transaction.password()){
                    UserFileClass userFileClassDelete = new UserFileClass();
                    MySql mySql = new MySql();
                    int sno = Integer.parseInt(mySql.valueQuery("SELECT sno FROM royaltypay.pay WHERE userID = '"+userFileClassDelete.string.substring(2)+"'", "sno"));
                    mySql.write("DELETE FROM royaltypay.pay WHERE userId = '"+userFileClassDelete.string.substring(2)+"'");
                    mySql.write("UPDATE royaltypay.pay SET sno = sno-1 WHERE sno > '"+sno+"'");
                    SignLogIn signLogIn = new SignLogIn();
                    signLogIn.loginPage(stage);
                }
            }
        });

        logout.setOnAction(e -> {

            ApplicationAlertBox applicationAlertBox = new ApplicationAlertBox();
            if(applicationAlertBox.alertBox(new Label("Logout"), new Label("Please confirm your account to proceed with\nthe log out process"))){

                UserFileClass userFileClassLogout = new UserFileClass();
                userFileClassLogout.setString(userFileClassLogout.string.charAt(0)+"0 ");
                SignLogIn signLogIn = new SignLogIn();
                signLogIn.loginPage(stage);
            }
        });

    }
}
