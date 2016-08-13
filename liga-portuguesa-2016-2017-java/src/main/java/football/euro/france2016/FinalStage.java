package football.euro.france2016;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class FinalStage
{
    private final String XML_FILE_NAME = "euro2016.xml";

    private GroupStage gamesGroups;
    private HashMap<String, Game> games8 = new HashMap<String, Game>();
    private HashMap<String, Game> games4 = new HashMap<String, Game>();
    private HashMap<String, Game> games2 = new HashMap<String, Game>();
    private HashMap<String, Game> games1 = new HashMap<String, Game>();
    
    public FinalStage()
    {
        this.gamesGroups = new GroupStage();
        
        if(this.gamesGroups.checkFinalized())
        {
            this.init8();

            if(this.checkFinalized8())
            {            
                this.init4();
                
                if(this.checkFinalized4())
                {
                    this.init2();

                    if(this.checkFinalized2())
                    {
                        this.init1();

                        if(this.checkFinalized1())
                        {
                            winner();
                        }
                    }
                }
            }
        }
    }

    public GroupStage getGamesGroups()
    {
        return this.gamesGroups;
    }
    
    public HashMap<String, Game> getGames8()
    {
        return this.games8;
    }
    
    public HashMap<String, Game> getGames4()
    {
        return this.games4;
    }

    public HashMap<String, Game> getGames2()
    {
        return this.games2;
    }

    public HashMap<String, Game> getGames1()
    {
        return this.games1;
    }    
    
    public void init8()
    {
        SAXBuilder builder = new SAXBuilder();  

        try
        {
            File file = new File(XML_FILE_NAME);
            Document document = (Document)builder.build(file);
                                    
            Element competition = document.getRootElement();
            
            Element stage = competition.getChild("FinalStage");
                        
            Element games = stage.getChild("Games8");
            
            for(Element game : games.getChildren())
            {
                Element teamHome = game.getChild("TeamHome");
                String teamHomeId = teamHome.getText();
                
                Element teamAway = game.getChild("TeamAway");
                String teamAwayId = teamAway.getText();                
                
                if(teamHomeId.length() == 2)
                {
                    int placeHome = Integer.parseInt(teamHomeId.substring(0, 1));
                    char groupHome = teamHomeId.charAt(1);

                    teamHome.setText(this.gamesGroups.getGroups().get(groupHome).getTeam(placeHome).getName());
                }
                
                if(teamAwayId.length() == 2)
                {
                    int placeAway = Integer.parseInt(teamAwayId.substring(0, 1));
                    char groupAway = teamAwayId.charAt(1);

                    teamAway.setText(this.gamesGroups.getGroups().get(groupAway).getTeam(placeAway).getName());              
                }
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
    }

    public boolean checkFinalized8()
    {
        SAXBuilder builder = new SAXBuilder();  

        try
        {                                   
            File file = new File(XML_FILE_NAME);
            Document document = (Document)builder.build(file);
                                    
            Element competition = document.getRootElement();
            
            Element stage = competition.getChild("FinalStage");
                        
            Element games = stage.getChild("Games8");
            
            // check if phase is final
            boolean check = true;
            
            for(Element game : games.getChildren())
            {    
                String finished = game.getAttributeValue("finished");

                if(Integer.parseInt(finished) == 0)
                {
                    check = false;

                    break;
                }
            }
            
            if(check)
                games.setAttribute("final", "1");                              

            String finished = games.getAttributeValue("final");
                    
            if(Integer.parseInt(finished) == 0)
                return false;
                     
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
    
    public void init4()
    {
        SAXBuilder builder = new SAXBuilder();  

        try
        {
            File file = new File(XML_FILE_NAME);
            Document document = (Document)builder.build(file);
                                    
            Element competition = document.getRootElement();
            
            Element stage = competition.getChild("FinalStage");
                        
            Element games4 = stage.getChild("Games4");
            Element games8 = stage.getChild("Games8");
            
            for(Element game : games4.getChildren())
            {
                Element teamHome = game.getChild("TeamHome");
                String teamHomeId = teamHome.getText();
                
                Element teamAway = game.getChild("TeamAway");
                String teamAwayId = teamAway.getText();                
                
                if(teamHomeId.length() == 1)
                {
                    for(Element gameBefore : games8.getChildren())
                    {
                        String id = gameBefore.getAttributeValue("id");
                                                
                        if(id.equals(teamHomeId))
                        {
                            String penalties = gameBefore.getAttributeValue("penalties");
                            Element teamHomeGameBefore = gameBefore.getChild("TeamHome");
                            Element teamAwayGameBefore = gameBefore.getChild("TeamAway");                            
                            
                            if(Integer.parseInt(penalties) == 0)
                            {                                
                                int goalsTeamHome = Integer.parseInt(teamHomeGameBefore.getAttributeValue("score"));
                                int goalsTeamAway = Integer.parseInt(teamAwayGameBefore.getAttributeValue("score"));
                            
                                if(goalsTeamHome > goalsTeamAway)
                                    teamHome.setText(teamHomeGameBefore.getText());
                                else if(goalsTeamHome < goalsTeamAway)
                                    teamHome.setText(teamAwayGameBefore.getText());
                            }
                            else if(Integer.parseInt(penalties) == 1)
                            {
                                int penaltiesGoalsTeamHome = Integer.parseInt(gameBefore.getAttributeValue("penaltiesHome"));
                                int penaltiesGoalsTeamAway = Integer.parseInt(gameBefore.getAttributeValue("penaltiesAway"));
                            
                                if(penaltiesGoalsTeamHome > penaltiesGoalsTeamAway)
                                   teamHome.setText(teamHomeGameBefore.getText());
                                else if(penaltiesGoalsTeamHome < penaltiesGoalsTeamAway)
                                    teamHome.setText(teamAwayGameBefore.getText());
                            }
                        }
                    }
                }
                
                if(teamAwayId.length() == 1)
                {
                    for(Element gameBefore : games8.getChildren())
                    {
                        String id = gameBefore.getAttributeValue("id");
                                                
                        if(id.equals(teamAwayId))
                        {
                            String penalties = gameBefore.getAttributeValue("penalties");
                            Element teamHomeGameBefore = gameBefore.getChild("TeamHome");
                            Element teamAwayGameBefore = gameBefore.getChild("TeamAway");                            
                            
                            if(Integer.parseInt(penalties) == 0)
                            {                                
                                int goalsTeamHome = Integer.parseInt(teamHomeGameBefore.getAttributeValue("score"));
                                int goalsTeamAway = Integer.parseInt(teamAwayGameBefore.getAttributeValue("score"));
                            
                                if(goalsTeamHome > goalsTeamAway)
                                    teamAway.setText(teamHomeGameBefore.getText());
                                else if(goalsTeamHome < goalsTeamAway)
                                    teamAway.setText(teamAwayGameBefore.getText());
                            }
                            else if(Integer.parseInt(penalties) == 1)
                            {
                                int penaltiesGoalsTeamHome = Integer.parseInt(gameBefore.getAttributeValue("penaltiesHome"));
                                int penaltiesGoalsTeamAway = Integer.parseInt(gameBefore.getAttributeValue("penaltiesAway"));
                            
                                if(penaltiesGoalsTeamHome > penaltiesGoalsTeamAway)
                                   teamAway.setText(teamHomeGameBefore.getText());
                                else if(penaltiesGoalsTeamHome < penaltiesGoalsTeamAway)
                                    teamAway.setText(teamAwayGameBefore.getText());
                            }
                        }
                    }                    
                }
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
    }    
    
    public boolean checkFinalized4()
    {
        SAXBuilder builder = new SAXBuilder();  

        try
        {                                   
            File file = new File(XML_FILE_NAME);
            Document document = (Document)builder.build(file);
                                    
            Element competition = document.getRootElement();
            
            Element stage = competition.getChild("FinalStage");
                        
            Element games = stage.getChild("Games4");
            
            // check if phase is final
            boolean check = true;
            
            for(Element game : games.getChildren())
            {    
                String finished = game.getAttributeValue("finished");

                if(Integer.parseInt(finished) == 0)
                {
                    check = false;

                    break;
                }
            }
            
            if(check)
                games.setAttribute("final", "1");                              

            String finished = games.getAttributeValue("final");
                    
            if(Integer.parseInt(finished) == 0)
                return false;
                     
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

    public void init2()
    {
        SAXBuilder builder = new SAXBuilder();  

        try
        {
            File file = new File(XML_FILE_NAME);
            Document document = (Document)builder.build(file);
                                    
            Element competition = document.getRootElement();
            
            Element stage = competition.getChild("FinalStage");
                        
            Element games2 = stage.getChild("Games2");
            Element games4 = stage.getChild("Games4");
            
            for(Element game : games2.getChildren())
            {
                Element teamHome = game.getChild("TeamHome");
                String teamHomeId = teamHome.getText();
                
                Element teamAway = game.getChild("TeamAway");
                String teamAwayId = teamAway.getText();                
                
                if(teamHomeId.length() == 1)
                {
                    for(Element gameBefore : games4.getChildren())
                    {
                        String id = gameBefore.getAttributeValue("id");
                                                
                        if(id.equals(teamHomeId))
                        {
                            String penalties = gameBefore.getAttributeValue("penalties");
                            Element teamHomeGameBefore = gameBefore.getChild("TeamHome");
                            Element teamAwayGameBefore = gameBefore.getChild("TeamAway");                            
                            
                            if(Integer.parseInt(penalties) == 0)
                            {                                
                                int goalsTeamHome = Integer.parseInt(teamHomeGameBefore.getAttributeValue("score"));
                                int goalsTeamAway = Integer.parseInt(teamAwayGameBefore.getAttributeValue("score"));
                            
                                if(goalsTeamHome > goalsTeamAway)
                                    teamHome.setText(teamHomeGameBefore.getText());
                                else if(goalsTeamHome < goalsTeamAway)
                                    teamHome.setText(teamAwayGameBefore.getText());
                            }
                            else if(Integer.parseInt(penalties) == 1)
                            {
                                int penaltiesGoalsTeamHome = Integer.parseInt(gameBefore.getAttributeValue("penaltiesHome"));
                                int penaltiesGoalsTeamAway = Integer.parseInt(gameBefore.getAttributeValue("penaltiesAway"));
                            
                                if(penaltiesGoalsTeamHome > penaltiesGoalsTeamAway)
                                   teamHome.setText(teamHomeGameBefore.getText());
                                else if(penaltiesGoalsTeamHome < penaltiesGoalsTeamAway)
                                    teamHome.setText(teamAwayGameBefore.getText());
                            }
                        }
                    }
                }
                
                if(teamAwayId.length() == 1)
                {
                    for(Element gameBefore : games4.getChildren())
                    {
                        String id = gameBefore.getAttributeValue("id");
                                                
                        if(id.equals(teamAwayId))
                        {
                            String penalties = gameBefore.getAttributeValue("penalties");
                            Element teamHomeGameBefore = gameBefore.getChild("TeamHome");
                            Element teamAwayGameBefore = gameBefore.getChild("TeamAway");                            
                            
                            if(Integer.parseInt(penalties) == 0)
                            {                                
                                int goalsTeamHome = Integer.parseInt(teamHomeGameBefore.getAttributeValue("score"));
                                int goalsTeamAway = Integer.parseInt(teamAwayGameBefore.getAttributeValue("score"));
                            
                                if(goalsTeamHome > goalsTeamAway)
                                    teamAway.setText(teamHomeGameBefore.getText());
                                else if(goalsTeamHome < goalsTeamAway)
                                    teamAway.setText(teamAwayGameBefore.getText());
                            }
                            else if(Integer.parseInt(penalties) == 1)
                            {
                                int penaltiesGoalsTeamHome = Integer.parseInt(gameBefore.getAttributeValue("penaltiesHome"));
                                int penaltiesGoalsTeamAway = Integer.parseInt(gameBefore.getAttributeValue("penaltiesAway"));
                            
                                if(penaltiesGoalsTeamHome > penaltiesGoalsTeamAway)
                                   teamAway.setText(teamHomeGameBefore.getText());
                                else if(penaltiesGoalsTeamHome < penaltiesGoalsTeamAway)
                                    teamAway.setText(teamAwayGameBefore.getText());
                            }
                        }
                    }                    
                }
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
    }

    public boolean checkFinalized2()
    {
        SAXBuilder builder = new SAXBuilder();  

        try
        {                                   
            File file = new File(XML_FILE_NAME);
            Document document = (Document)builder.build(file);
                                    
            Element competition = document.getRootElement();
            
            Element stage = competition.getChild("FinalStage");
                        
            Element games = stage.getChild("Games2");
            
            // check if phase is final
            boolean check = true;
            
            for(Element game : games.getChildren())
            {    
                String finished = game.getAttributeValue("finished");

                if(Integer.parseInt(finished) == 0)
                {
                    check = false;

                    break;
                }
            }
            
            if(check)
                games.setAttribute("final", "1");                              

            String finished = games.getAttributeValue("final");
                    
            if(Integer.parseInt(finished) == 0)
                return false;
                     
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

    public void init1()
    {
        SAXBuilder builder = new SAXBuilder();  

        try
        {
            File file = new File(XML_FILE_NAME);
            Document document = (Document)builder.build(file);
                                    
            Element competition = document.getRootElement();
            
            Element stage = competition.getChild("FinalStage");
                        
            Element games1 = stage.getChild("Games1");
            Element games2 = stage.getChild("Games2");
            Element game1 = games2.getChild("Game1");
            Element game2 = games2.getChild("Game2");            
            Element gameFinal = games1.getChild("GameFinal");
            
            Element teamHomeFinal = gameFinal.getChild("TeamHome");
            String teamHomeIdFinal = teamHomeFinal.getText();
            Element teamAwayFinal = gameFinal.getChild("TeamAway");
            String teamAwayIdFinal = teamAwayFinal.getText();            
                
            if(teamHomeIdFinal.length() == 1)
            {
                String penalties = game1.getAttributeValue("penalties");
                Element teamHomeGame1 = game1.getChild("TeamHome");
                Element teamAwayGame1 = game1.getChild("TeamAway");                            
                            
                if(Integer.parseInt(penalties) == 0)
                {                                
                    int goalsTeamHome = Integer.parseInt(teamHomeGame1.getAttributeValue("score"));
                    int goalsTeamAway = Integer.parseInt(teamAwayGame1.getAttributeValue("score"));
                            
                    if(goalsTeamHome > goalsTeamAway)
                        teamHomeFinal.setText(teamHomeGame1.getText());
                    else if(goalsTeamHome < goalsTeamAway)
                        teamHomeFinal.setText(teamAwayGame1.getText());
                }
                else if(Integer.parseInt(penalties) == 1)
                {
                    int penaltiesGoalsTeamHome = Integer.parseInt(game1.getAttributeValue("penaltiesHome"));
                    int penaltiesGoalsTeamAway = Integer.parseInt(game1.getAttributeValue("penaltiesAway"));

                    if(penaltiesGoalsTeamHome > penaltiesGoalsTeamAway)
                        teamHomeFinal.setText(teamHomeGame1.getText());
                    else if(penaltiesGoalsTeamHome < penaltiesGoalsTeamAway)
                        teamHomeFinal.setText(teamAwayGame1.getText());
                }                
            }
            
            if(teamAwayIdFinal.length() == 1)
            {
                String penalties = game2.getAttributeValue("penalties");
                Element teamHomeGame2 = game2.getChild("TeamHome");
                Element teamAwayGame2 = game2.getChild("TeamAway");                            
                            
                if(Integer.parseInt(penalties) == 0)
                {                                
                    int goalsTeamHome = Integer.parseInt(teamHomeGame2.getAttributeValue("score"));
                    int goalsTeamAway = Integer.parseInt(teamAwayGame2.getAttributeValue("score"));
                            
                    if(goalsTeamHome > goalsTeamAway)
                        teamAwayFinal.setText(teamHomeGame2.getText());
                    else if(goalsTeamHome < goalsTeamAway)
                        teamAwayFinal.setText(teamAwayGame2.getText());
                }
                else if(Integer.parseInt(penalties) == 1)
                {
                    int penaltiesGoalsTeamHome = Integer.parseInt(game2.getAttributeValue("penaltiesHome"));
                    int penaltiesGoalsTeamAway = Integer.parseInt(game2.getAttributeValue("penaltiesAway"));

                    if(penaltiesGoalsTeamHome > penaltiesGoalsTeamAway)
                        teamHomeFinal.setText(teamHomeGame2.getText());
                    else if(penaltiesGoalsTeamHome < penaltiesGoalsTeamAway)
                        teamHomeFinal.setText(teamAwayGame2.getText());
                }                
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
    }    

    public boolean checkFinalized1()
    {
        SAXBuilder builder = new SAXBuilder();  

        try
        {                                   
            File file = new File(XML_FILE_NAME);
            Document document = (Document)builder.build(file);
                                    
            Element competition = document.getRootElement();
            
            Element stage = competition.getChild("FinalStage");
                        
            Element games = stage.getChild("Games1");
            
            // check if phase is final
            boolean check = true;
            
            for(Element game : games.getChildren())
            {    
                String finished = game.getAttributeValue("finished");

                if(Integer.parseInt(finished) == 0)
                {
                    check = false;

                    break;
                }
            }
            
            if(check)
                games.setAttribute("final", "1");                              

            String finished = games.getAttributeValue("final");
                    
            if(Integer.parseInt(finished) == 0)
                return false;
                     
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

    public void winner()
    {
        SAXBuilder builder = new SAXBuilder();  

        try
        {
            File file = new File(XML_FILE_NAME);
            Document document = (Document)builder.build(file);
                                    
            Element competition = document.getRootElement();
            
            Element stage = competition.getChild("FinalStage");                        
            Element games1 = stage.getChild("Games1");
            Element winner = stage.getChild("Winner");
            Element gameFinal = games1.getChild("GameFinal");
                            
            if(Integer.parseInt(gameFinal.getAttributeValue("finished")) == 1 &&
                winner.getText().equals("?"))
            {               
                String penalties = gameFinal.getAttributeValue("penalties");
                Element teamHomeFinal = gameFinal.getChild("TeamHome");
                Element teamAwayFinal = gameFinal.getChild("TeamAway");                            
                            
                if(Integer.parseInt(penalties) == 0)
                {                                
                    int goalsTeamHome = Integer.parseInt(teamHomeFinal.getAttributeValue("score"));
                    int goalsTeamAway = Integer.parseInt(teamAwayFinal.getAttributeValue("score"));
                            
                    if(goalsTeamHome > goalsTeamAway)
                        winner.setText(teamHomeFinal.getText());
                    else if(goalsTeamHome < goalsTeamAway)
                        winner.setText(teamAwayFinal.getText());
                }
                else if(Integer.parseInt(penalties) == 1)
                {
                    int penaltiesGoalsTeamHome = Integer.parseInt(gameFinal.getAttributeValue("penaltiesHome"));
                    int penaltiesGoalsTeamAway = Integer.parseInt(gameFinal.getAttributeValue("penaltiesAway"));

                    if(penaltiesGoalsTeamHome > penaltiesGoalsTeamAway)
                        winner.setText(teamHomeFinal.getText());
                    else if(penaltiesGoalsTeamHome < penaltiesGoalsTeamAway)
                        winner.setText(teamAwayFinal.getText());
                }                
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
    }
}    
