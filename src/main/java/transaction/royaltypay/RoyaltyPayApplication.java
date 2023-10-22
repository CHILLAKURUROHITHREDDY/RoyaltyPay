package transaction.royaltypay;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.Objects;

public class RoyaltyPayApplication extends Application {

    private static final Duration LOGO_DISPLAY_DURATION = Duration.seconds(1);
    private static final Duration FADE_OUT_DURATION = Duration.seconds(1);
    private static final Duration SCENE_TRANSITION_DELAY = Duration.seconds(6);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage openStage) {

        //Variables
        BorderPane bpLayout;
        FadeTransition logoFadeOutTransition;
        Image icon;
        ImageView logoView;
        PauseTransition logoDisplayTransition;


        //Variables properties
        icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("logo.png")));
        logoView = new ImageView(icon);
        logoView.setFitWidth(350);
        logoView.setFitHeight(350);


        //Layouts
        bpLayout = new BorderPane();
        bpLayout.setCenter(logoView);


        //Stage with scene
        Scene openScene = new Scene(bpLayout);
        UserFileClass userFileClass = new UserFileClass();
        if(userFileClass.string.charAt(0) == '0')
            openScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("dark.css")).toExternalForm());
        else if(userFileClass.string.charAt(0) == '1')
            openScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("light.css")).toExternalForm());

        openStage.setTitle("Royalty Pay");
        openStage.setWidth(500);
        openStage.setHeight(600);
        openStage.setResizable(false);
        openStage.getIcons().add(icon);
        openStage.show();


        //Scene switching
        logoDisplayTransition = new PauseTransition(LOGO_DISPLAY_DURATION);
        logoDisplayTransition.setOnFinished(event -> {
            openStage.setScene(openScene);

            Timeline fadeInTransition = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(openScene.getRoot().opacityProperty(), 0)),
                    new KeyFrame(FADE_OUT_DURATION, new KeyValue(openScene.getRoot().opacityProperty(), 1))
            );
            fadeInTransition.play();
        });

        logoDisplayTransition.play();

        logoFadeOutTransition = new FadeTransition(FADE_OUT_DURATION, logoView);
        logoFadeOutTransition.setFromValue(1);
        logoFadeOutTransition.setToValue(0);
        logoFadeOutTransition.setOnFinished(event -> {
            if(userFileClass.string.charAt(1) == '1'){

                Transaction transaction = new Transaction();
                transaction.home(openStage);
            }
            else{
                SignLogIn signLogIn =new SignLogIn();
                signLogIn.loginPage(openStage);
            }
        });

        Timeline sceneTransitionTimeline = new Timeline(
                new KeyFrame(LOGO_DISPLAY_DURATION, event -> logoFadeOutTransition.play())
        );

        sceneTransitionTimeline.setDelay(SCENE_TRANSITION_DELAY);
        sceneTransitionTimeline.play();

    }

}