package football.ptleague.season1617;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public final class Championship
{
    private final String XML_FILE_NAME = "portuguese-league-2016-2017.xml";
    
    private Teams teams;
    private HashMap<String, Game> gamesMap;
    
    public Championship()
    {
        this.teams = new Teams();
        this.init();
    }

    public HashMap<String, Game> getGames()
    {
        return this.gamesMap;
    }
    
    public void init()
    {             
        SAXBuilder builder = new SAXBuilder();  

        try
        {                                     
            File file = new File(XML_FILE_NAME);
            Document document = (Document)builder.build(file);
                                    
            Element competition = document.getRootElement();
            
            Element games = competition.getChild("Games");            
            
            int numOfGames = 0;
                        
            for(Element matchday : games.getChildren())
                for(Element game : matchday.getChildren())
                    numOfGames++;
       
            this.gamesMap = new HashMap<String, Game>(numOfGames);
                                                
            // create games
            for(Element matchday : games.getChildren())
            {                  
                String matchdayId = matchday.getAttributeValue("id");

                for(Element game : matchday.getChildren())
                {                   
                    String teamHomeName = game.getChildText("TeamHome");
                    String teamAwayName = game.getChildText("TeamAway");
                    int teamHomeScore = Integer.parseInt(game.getChild("TeamHome").getAttributeValue("score"));
                    int teamAwayScore = Integer.parseInt(game.getChild("TeamAway").getAttributeValue("score"));                    

                    Team teamHome = this.teams.getTeam(teamHomeName);
                    Team teamAway = this.teams.getTeam(teamAwayName);
                                        
                    String gameId = matchdayId.concat(game.getAttributeValue("id"));                    

                    if(Integer.parseInt(game.getAttributeValue("started")) == 1)
                    {                                       
                        this.gamesMap.put( gameId, // key
                                        new Game(this.teams, teamHome, teamHomeScore, teamAway, teamAwayScore) // value
                                      );
                    }
                    else
                    {                       
                        this.gamesMap.put ( gameId, // key
                                        new Game(this.teams, teamHome, teamAway) // value
                                       );
                    }                    
                }                
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
    }
    
    public ObservableList<Team> createDataClassification()
    {
        ObservableList<Team> list = FXCollections.observableArrayList();

        for(int i = 0; i < this.teams.getNumberOfTeams(); i++)
            list.add(this.teams.getTeam(i + 1));
        
        return list;
    }    

    public ObservableList<Game> createDataGames(String matchday)
    {        
        ObservableList<Game> list = FXCollections.observableArrayList();
        SAXBuilder builder = new SAXBuilder();  

        try
        {
            File file = new File(XML_FILE_NAME);
            Document document = (Document)builder.build(file);
                                                
            Element competition = document.getRootElement();
            
            Element games = competition.getChild("Games");            
            
            for(Element matchdayElement : games.getChildren()) 
            {
                String matchdayId = matchdayElement.getAttributeValue("id");
            
                for(Element game : matchdayElement.getChildren())
                {
                    String id = game.getAttributeValue("id");                        
                    String gameId = matchdayId.concat(id);                    
                    
                    if(matchday.equals(matchdayId))
                        list.add(this.getGames().get(gameId));
                }
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
        
        return list;
    }
}    
