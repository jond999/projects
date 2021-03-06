package football.ptleague.season1617;

import java.util.ArrayList;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Game
{
    private final int NUMBER_OF_TEAMS = 18 ;
    private final Team home;
    private final SimpleStringProperty nameHome;
    private final SimpleIntegerProperty goalsHome;
    private final Team away;
    private final SimpleStringProperty nameAway;
    private final SimpleIntegerProperty goalsAway;
    private final Teams teams;

    public Game(Teams teams, Team home, Team away)
    {
        this.teams = teams;
        
        this.home = home;
        this.nameHome = new SimpleStringProperty(this.home.getName());
        this.goalsHome = new SimpleIntegerProperty(0);
        
        this.away = away;
        this.nameAway = new SimpleStringProperty(this.away.getName());
        this.goalsAway = new SimpleIntegerProperty(0);
    }
    
    public Game(Teams teams, Team home, int goalsHome, Team away, int goalsAway)
    {
        this.teams = teams;
        
        this.home = home;
        this.nameHome = new SimpleStringProperty(this.home.getName());
        this.goalsHome = new SimpleIntegerProperty(goalsHome);
        
        this.away = away;
        this.nameAway = new SimpleStringProperty(away.getName());
        this.goalsAway = new SimpleIntegerProperty(goalsAway);
        
        if(this.goalsHome.getValue() > this.goalsAway.getValue())
        {
            this.win();
        }
        else if(this.goalsHome.getValue() < this.goalsAway.getValue())
        {
            this.loss();
        }
        else
        {
            this.draw();
        }
        
        this.updateClassification();
    }
    
    public Team getHome()
    {
        return this.home;
    }
    
    public String getNameHome()
    {
        return this.nameHome.get();
    }    
    
    public int getGoalsHome()
    {
        return this.goalsHome.get();
    }
    
    public void setGoalsHome(int goals)
    {
        this.goalsHome.set(goals);
    }
   
    public Team getAway()
    {
        return this.away;
    }

    public String getNameAway()
    {
        return this.nameAway.get();
    }
    
    public int getGoalsAway()
    {
        return this.goalsAway.getValue();
    }
    
    public void setGoalsAway(int goals)
    {
        this.goalsAway.set(goals);
    }    
    
    public void win()
    {
        this.updateTeams();
        
        home.incrementWins();
        away.incrementLosses();

        home.incrementThreePoints();        
    }
    
    public void loss()
    {
        this.updateTeams();
                
        home.incrementLosses();
        away.incrementWins();           
        
        away.incrementThreePoints();
    }

    public void draw()
    {
        this.updateTeams();
                
        home.incrementDraws();
        away.incrementDraws();
        
        home.incrementOnePoint();
        away.incrementOnePoint();
    }
    
    public void updateTeams()
    {
        home.incrementPlayed();
        away.incrementPlayed();
        
        home.incrementGoalsScored(getGoalsHome());
        away.incrementGoalsScored(getGoalsAway());
        home.incrementGoalsConceded(getGoalsAway());
        away.incrementGoalsConceded(getGoalsHome());        
    }

    public Team getFirst(ArrayList<Team> list)
    {
        int j;
        int position = 0;
        boolean first = false;
                
        if(list.size() == 1)
            return list.get(position);
        else if(list.size() > 1 && list.size() <= NUMBER_OF_TEAMS)
        {
            for(j = 0; j < list.size() - 1; j++)
            {
                first = true;
                
                for(int i = 0; i < list.size(); i++)
                {
                    if(i == j)
                        continue;
                    
                    if(list.get(j).getPoints() <  list.get(i).getPoints())
                    {
                        first = false;
                        break;
                    }
                    else if((list.get(j).getPoints() ==  list.get(i).getPoints()) &&
                             (list.get(j).getDifferenceGoals() <  list.get(i).getDifferenceGoals()))
                    {
                        first = false;
                        break;
                    }
                }
                
                if(first)
                {
                    position = j; 
                    break;
                }
            }
        
            position = j;
        }
    
        return list.get(position);
    }    
    
    public void updateClassification()
    {
        ArrayList<Team> aux = new ArrayList();
        ArrayList<Team> updated = new ArrayList();
        
        for(int i = 0; i < NUMBER_OF_TEAMS; i++)
            aux.add(this.teams.getTeam(i + 1));          
        
        for(int i = 0; i < NUMBER_OF_TEAMS; i++)
        {
            Team first = getFirst(aux);

            updated.add(first);
            aux.remove(first);
        }
        
        this.teams.getTeams().clear();
                
        for(int i = 0; i < NUMBER_OF_TEAMS; i++)
        {
            this.teams.getTeams().add(updated.get(i));
            this.teams.getTeams().get(i).setPosition(i + 1);
        }
    }
}
