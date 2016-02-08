package com.dsp.livemusic.control;

import com.dsp.livemusic.view.PlayerViewModel;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;

/**
 * Created by Xabush Semrie on 2/5/2016.
 * Server Application entry point.
 */
public class MainApp extends Application{

    private State state;
    private String name;
    private PlayerViewModel playerVM;
    private Stage primaryStage;

    public static void main(String[] args) throws Exception
    {
            launch(args);
    }


    @Override
    public void start(Stage stage) throws Exception {
        this.primaryStage = stage;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(new File("res/livemp.fxml").toURI().toURL());
        VBox root = (VBox)loader.load();
//        root.getStylesheets().add(new File("res/rootlayout.css").toURI().toURL().toExternalForm());
        Scene scene = new Scene(root,872,584);
        primaryStage.setScene(scene);
//        initMove(scene);
        PlayerViewModel viewModel = loader.getController();

        viewModel.setMainApp(this);

    }


}
