package com.dsp.livemusic.view;

import com.dsp.livemusic.control.MainApp;
import com.dsp.livemusic.model.SongModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * Created by Xabush on 5/9/2015 19:19.
 * Project: neXt Player
 */
public class PlayerViewModel {

    //Sliders and Progress Bars
    @FXML
    private Slider volumSlider;
    @FXML
    private Slider seekSlider;
    @FXML
    private ProgressBar volumePbar;
    @FXML
    private ProgressBar seekPbar;

    //Buttons
    @FXML
    private Button prevButton;
    @FXML
    private Button playButton;
    @FXML
    private Button nextButton;
    @FXML
    private Button stopButton;
    @FXML
    private Button repeatButton;
    @FXML
    private Button settingButton;
    @FXML
    private Button addButton;
    @FXML
    private Button closeButton;

    //ImageView
    @FXML
    private ImageView albumArt;

    //Labels
    @FXML
    private Label albumLabel;
    @FXML
    private Label artistLabel;
    @FXML
    private Label titleLabel;
    @FXML
    private Label elapsedLabel;
    @FXML
    private Label remainingLabel;

    //Hbox layouts
    @FXML
    private HBox statusBar;
    @FXML
    private HBox playBackBox;
    @FXML
    private HBox rsBox;
    @FXML
    private HBox miscBox;
    @FXML
    private HBox seekBox;

    //VBoxes
    @FXML
    private VBox rootBox;
    @FXML
    private VBox metaBox;

    //Stackpanes
    @FXML
    private StackPane volumePane;
    @FXML
    private StackPane seekPane;

    //GridPane
    @FXML
    private GridPane gridPane;

    //TitlePane
    @FXML
    private TitledPane nodePane;

    //SplitPane
    @FXML
    private SplitPane splitPane;

    //TableView
    @FXML
    private TableView<SongModel> tableView;

    //TableColumns
    @FXML
    private TableColumn<SongModel, String> numCol;
    @FXML
    private TableColumn<SongModel, String> nameCol;
    @FXML
    private TableColumn<SongModel, String> timeCol;
    @FXML
    private TableColumn<SongModel, String> artistCol;
    @FXML
    private TableColumn<SongModel, String> albumCol;
    @FXML
    private TableColumn<SongModel, String> genreCol;
    @FXML
    private TableColumn<SongModel, String> yearCol;

    //App classes
    private MainApp mainApp;



    @FXML
    private void initialize()
    {

    }

    public void setMainApp(MainApp app)
    {
        this.mainApp = app;
    }

    @FXML
    public void handlePlay(){

    }

    @FXML
    public void handleOpen()
    {

    }

    @FXML
    public void handleStop()
    {

    }
}