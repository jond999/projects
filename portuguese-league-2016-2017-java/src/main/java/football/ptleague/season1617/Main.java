package football.ptleague.season1617;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class Main extends Application
{
    private final String XML_FILE_NAME = "portuguese-league-2016-2017.xml";
    private final Championship ch = new Championship();
    
    private TableView<Team> tv_classification;
    private final HashMap<String, TableView<Game>> tv_games = new HashMap<String, TableView<Game>>();

    private ObservableList<Team> ol_dataClassification;
    private final HashMap<String, ObservableList<Game>> ol_dataGames = new HashMap<String, ObservableList<Game>>();
    
    public static void main(String[] args)
    {        
        launch(args);
    }

    @Override
    public void start(Stage stage)
    {
        SAXBuilder builder = new SAXBuilder();  
        
        javafx.scene.Group root = new javafx.scene.Group();
        Scene scene = new Scene(root, 1500, 800);

        stage.setScene(scene);
        stage.setTitle("PORTUGUESE LEAGUE 2016/2017");
        stage.setMaximized(false);
                
        final VBox vbox = new VBox();

        vbox.setSpacing(5);
        vbox.setPadding(new Insets(5, 0, 0, 10));               
        
        try
        {
            File file = new File(XML_FILE_NAME);
            Document document = (Document)builder.build(file);
                                    
            Element competition = document.getRootElement();
            
            Element games = competition.getChild("Games");                                                         
            
            this.tv_classification = new TableView<Team>();
            
            for(Element matchday : games.getChildren())
                this.tv_games.put(matchday.getAttributeValue("id"), new TableView<Game>());
            
            this.createClassificationTable();
            
            Label labelClassification = new Label("Classification");
            labelClassification.setFont(new Font("Arial", 20));
            
            vbox.getChildren().add(labelClassification);
            vbox.getChildren().add(this.tv_classification);          
            
            for(Element matchday : games.getChildren())
            {
                String matchdayId = matchday.getAttributeValue("id");
                this.createResultsTable(matchdayId);
                
                Label label = new Label("Matchday " + matchdayId);
                label.setFont(new Font("Arial", 20));
          
                vbox.getChildren().add(label);
                vbox.getChildren().add(this.tv_games.get(matchdayId));
            }
        }
        catch(JDOMException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        ScrollBar sc = new ScrollBar();   
                
        sc.setLayoutX(scene.getWidth() - sc.getWidth());
        sc.setMin(0);
        sc.setOrientation(Orientation.VERTICAL);
        sc.setPrefHeight(scene.getHeight());
        sc.setMax(15000);        

        sc.valueProperty().addListener(new ChangeListener<Number>()
        {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val)
            {
                vbox.setLayoutY(-new_val.doubleValue());
            }
        });        
        
        ((javafx.scene.Group) scene.getRoot()).getChildren().addAll(vbox, sc);
        
        stage.setScene(scene);
        stage.show();
    }
    
    void createClassificationTable()
    {        
        this.tv_classification.setEditable(false);

        TableColumn positionCol = new TableColumn("Pos");
        positionCol.setMinWidth(50);
        positionCol.setCellValueFactory(new PropertyValueFactory<Team, Integer>("position"));

        TableColumn nameCol = new TableColumn("Name");
        nameCol.setMinWidth(50);
        nameCol.setCellValueFactory(new PropertyValueFactory<Team, String>("name"));

        TableColumn playedCol = new TableColumn("P");
        playedCol.setMinWidth(50);
        playedCol.setCellValueFactory(new PropertyValueFactory<Team, Integer>("played"));
        
        TableColumn winsCol = new TableColumn("W");
        winsCol.setMinWidth(50);
        winsCol.setCellValueFactory(new PropertyValueFactory<Team, Integer>("wins"));

        TableColumn drawsCol = new TableColumn("D");
        drawsCol.setMinWidth(50);
        drawsCol.setCellValueFactory(new PropertyValueFactory<Team, Integer>("draws"));

        TableColumn lossesCol = new TableColumn("L");
        lossesCol.setMinWidth(50);
        lossesCol.setCellValueFactory(new PropertyValueFactory<Team, Integer>("losses"));

        TableColumn goalsScoredCol = new TableColumn("GS");
        goalsScoredCol.setMinWidth(50);
        goalsScoredCol.setCellValueFactory(new PropertyValueFactory<Team, Integer>("goalsScored"));

        TableColumn goalsConcededCol = new TableColumn("GC");
        goalsConcededCol.setMinWidth(50);
        goalsConcededCol.setCellValueFactory(new PropertyValueFactory<Team, Integer>("goalsConceded"));

        TableColumn differenceGoalsCol = new TableColumn("+/-");
        differenceGoalsCol.setMinWidth(50);
        differenceGoalsCol.setCellValueFactory(new PropertyValueFactory<Team, Integer>("differenceGoals"));

        TableColumn pointsCol = new TableColumn("Pts");
        pointsCol.setMinWidth(50);
        pointsCol.setCellValueFactory(new PropertyValueFactory<Team, Integer>("points"));
        
        this.ol_dataClassification = this.ch.createDataClassification();
        
        Collections.sort(this.ol_dataClassification, new Team(""));        
                
        this.tv_classification.setFixedCellSize(93);
        this.tv_classification.setItems(this.ol_dataClassification);
        
        this.tv_classification.getColumns().addAll(
            positionCol,
            nameCol,
            playedCol,
            winsCol,
            drawsCol,
            lossesCol,
            goalsScoredCol,
            goalsConcededCol,
            differenceGoalsCol,
            pointsCol
        );
    }
    
    void createResultsTable(String matchday)
    {
        this.tv_games.get(matchday).setEditable(false);

        TableColumn homeCol = new TableColumn("Home");
        homeCol.setMinWidth(50);
        homeCol.setCellValueFactory(new PropertyValueFactory<Game, String>("nameHome"));

        TableColumn goalsHomeCol = new TableColumn("Score");
        goalsHomeCol.setMinWidth(50);
        goalsHomeCol.setCellValueFactory(new PropertyValueFactory<Game, Integer>("goalsHome"));        

        TableColumn awayCol = new TableColumn("Away");
        awayCol.setMinWidth(50);
        awayCol.setCellValueFactory(new PropertyValueFactory<Game, String>("nameAway"));

        TableColumn goalsAwayCol = new TableColumn("Score");
        goalsAwayCol.setMinWidth(50);
        goalsAwayCol.setCellValueFactory(new PropertyValueFactory<Game, Integer>("goalsAway"));                
        
        this.ol_dataGames.put(matchday, this.ch.createDataGames(matchday));

        this.tv_games.get(matchday).setItems(this.ol_dataGames.get(matchday));   

        this.tv_games.get(matchday).getColumns().addAll(
            homeCol,
            goalsHomeCol,
            awayCol,
            goalsAwayCol
        );        
    }
}
    