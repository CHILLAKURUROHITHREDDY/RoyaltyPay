package transaction.royaltypay;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.Objects;


public class ApplicationAlertBox {

    private boolean result = false;
    boolean alertBox(Label alertHeader, Label message) {

        //Variables
        Button ok, cancel;
        Image icon;
        HBox hLayout1, hLayout2;
        VBox vLayout;
        BorderPane borderPane;
        Scene alertScene;
        Stage alertStage;


        //Variables properties
        icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("logo.png")));
        message.setPadding(new Insets(0,0,0,10));
        ok = new Button("Ok");
        cancel = new Button("Cancel");
        ok.setMinWidth(70);
        cancel.setMinWidth(70);


        //Layouts
        hLayout1 = new HBox();
        hLayout1.getChildren().add(alertHeader);
        hLayout1.setPadding(new Insets(40,0,0,20));

        hLayout2 = new HBox();
        hLayout2.getChildren().addAll(ok, cancel);
        hLayout2.setAlignment(Pos.CENTER_RIGHT);
        hLayout2.setSpacing(10);
        hLayout2.setPadding(new Insets(10,10,10,0));


        vLayout = new VBox();
        vLayout.setAlignment(Pos.CENTER_LEFT);
        vLayout.setSpacing(20);
        vLayout.getChildren().addAll(new Separator(), message);

        borderPane = new BorderPane();
        borderPane.setTop(hLayout1);
        borderPane.setCenter(vLayout);
        borderPane.setBottom(hLayout2);
        borderPane.setId("alertBox");


        //Stage with scene
        alertScene = new Scene(borderPane, 350,200);
        UserFileClass userFileClass = new UserFileClass();
        if(userFileClass.string.charAt(0) == '0')
            alertScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("dark.css")).toExternalForm());
        else if(userFileClass.string.charAt(0) == '1')
            alertScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("light.css")).toExternalForm());

        alertStage = new Stage();
        alertStage.getIcons().add(icon);
        alertStage.setResizable(false);
        alertStage.setTitle("Alert box");
        alertStage.initModality(Modality.APPLICATION_MODAL);
        alertStage.setScene(alertScene);


        //Controller actions
        alertStage.setOnShowing(e ->{
            ok.setOnAction(event -> {
                setResult(true);
                alertStage.close();
             });

            cancel.setOnAction(event -> {
                setResult(false);
                alertStage.close();
            });
        });
        alertStage.showAndWait();

        return result;
    }

    void messageDisplay(String title, Label message){

        //Variables
        Image icon;
        Button close;
        VBox vLayout1, vLayout2;
        Scene messageScene;
        Stage messageStage;


        //Variables properties
        icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("logo.png")));
        close = new Button("close");
        close.setMinWidth(70);


        //Layouts
        vLayout1 = new VBox(message, close);
        vLayout1.setSpacing(30);
        vLayout1.setAlignment(Pos.CENTER);
        vLayout2 = new VBox(vLayout1);
        vLayout2.setAlignment(Pos.CENTER);


        //Stage with scene
        messageScene = new Scene(vLayout2, 200, 200);
        UserFileClass userFileClass = new UserFileClass();
        if(userFileClass.string.charAt(0) == '0')
            messageScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("dark.css")).toExternalForm());
        else if(userFileClass.string.charAt(0) == '1')
            messageScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("light.css")).toExternalForm());

        messageStage = new Stage();
        messageStage.getIcons().add(icon);
        messageStage.setTitle(title);
        messageStage.setScene(messageScene);
        messageStage.setResizable(false);
        messageStage.initModality(Modality.APPLICATION_MODAL);
        messageStage.show();


        //Controller actions
        close.setOnAction(e -> messageStage.close());
    }

    void setResult(Boolean result){

        this.result = result;
    }

}
