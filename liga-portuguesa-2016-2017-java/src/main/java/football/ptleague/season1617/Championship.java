// TODO: TO BE FIXED
package football.ptleague.season1617;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class Championship
{
    private final String XML_FILE_NAME = "portuguese-league-2016-2017.xml";
    
    private HashMap<Character, Group> groups = new HashMap<Character, Group>();
    private HashMap<String, Game> games = new HashMap<String, Game>();
    
    public Championship()
    {
        this.init();
    }
    
    public boolean checkFinalized()
    {
        SAXBuilder builder = new SAXBuilder();  

        try
        {                                   
            File file = new File(XML_FILE_NAME);
            Document document = (Document)builder.build(file);
                                    
            Element competition = document.getRootElement();
            
            Element stage = competition.getChild("GroupStage");
                        
            Element games = stage.getChild("Games");
            
            for(Element group : games.getChildren())
            {
                // check if group is final
                boolean check = true;
                
                for(Element game : group.getChildren())
                {                
                    String finished = game.getAttributeValue("finished");
                    
                    if(Integer.parseInt(finished) == 0)
                    {
                        check = false;
                        
                        break;
                    }
                }
                
                if(check)
                    group.setAttribute("final", "1");                  
            }

            for(Element group : games.getChildren())
            {
                String finished = group.getAttributeValue("final");
                    
                if(Integer.parseInt(finished) == 0)
                    return false;
            }
            
            XMLOutputter xmlOutput = new XMLOutputter();

            xmlOutput.setFormat(Format.getPrettyFormat());
            xmlOutput.output(document, new FileWriter(XML_FILE_NAME));
        }
       
        catch(JDOMException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        
        return true;
    }
       
    public HashMap<Character, Group> getGroups()
    {
        return this.groups;
    }
    
    public HashMap<String, Game> getGames()
    {
        return this.games;
    }
    
    public void init()
    {
        SAXBuilder builder = new SAXBuilder();  

        try
        {                                     
            File file = new File(XML_FILE_NAME);
            Document document = (Document)builder.build(file);
                                    
            Element competition = document.getRootElement();
            
            Element stage = competition.getChild("GroupStage");
                                    
            Element groups = stage.getChild("Groups");
            Element games = stage.getChild("Games");            
            
            // create groups and teams
            for(Element group : groups.getChildren())
            {
                char id = group.getAttributeValue("id").charAt(0);
                ArrayList<String> teams = new ArrayList<String>();
                
                for(Element team : group.getChildren())
                    teams.add(team.getText());
               
                this.groups.put(id, new Group(id, teams.get(0), teams.get(1), teams.get(2), teams.get(3))); 
            }
            
            for(Element groupElement : games.getChildren())
            {
                String groupId = groupElement.getAttributeValue("id");
                Group group = this.groups.get(groupId.charAt(0));
                
                for(Element game : groupElement.getChildren())
                {                
                    String teamHomeName = game.getChildText("TeamHome");
                    String teamAwayName = game.getChildText("TeamAway");
                    int teamHomeScore = Integer.parseInt(game.getChild("TeamHome").getAttributeValue("score"));
                    int teamAwayScore = Integer.parseInt(game.getChild("TeamAway").getAttributeValue("score"));                    
                    
                    Team teamHome = group.getTeam(teamHomeName);
                    Team teamAway = group.getTeam(teamAwayName);
                    
                    String gameId = groupId.concat(game.getAttributeValue("id"));

                    if(Integer.parseInt(game.getAttributeValue("started")) == 1)
                        this.games.put( gameId, // key
                                        new Game(group, teamHome, teamHomeScore, teamAway, teamAwayScore) // value
                                      );
                    else
                        this.games.put ( gameId, // key
                                        new Game(group, teamHome, teamAway) // value
                                       );
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
    
    public ObservableList<Team> createDataClassification(char group)
    {
        ObservableList<Team> list = FXCollections.observableArrayList();
        SAXBuilder builder = new SAXBuilder();  

        try
        {
            File file = new File(XML_FILE_NAME);
            Document document = (Document)builder.build(file);
                                  
            Element competition = document.getRootElement();
            
            Element groupStage = competition.getChild("GroupStage");            
                        
            Element groups = groupStage.getChild("Groups");            
                        
            for(Element groupElement : groups.getChildren())
            {
                char id = groupElement.getAttributeValue("id").charAt(0);
                
                if(id == group)
                    for(Element team : groupElement.getChildren())
                        list.add(this.getGroups().get(id).getTeam(team.getText()));                    
                else
                    continue;
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

    public ObservableList<Game> createDataGames(char group)
    {
        ObservableList<Game> list = FXCollections.observableArrayList();
        SAXBuilder builder = new SAXBuilder();  

        try
        {
            File file = new File(XML_FILE_NAME);
            Document document = (Document)builder.build(file);
                                                
            Element competition = document.getRootElement();
            
            Element groupStage = competition.getChild("GroupStage");            
                                    
            Element games = groupStage.getChild("Games");            
                        
            for(Element groupElement : games.getChildren())
            {
                String id = groupElement.getAttributeValue("id");
                char groupId = id.charAt(0);
                                
                if(groupId == group)
                    for(Element game : groupElement.getChildren())
                    {
                        String gameId = id.concat(game.getAttributeValue("id"));
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
