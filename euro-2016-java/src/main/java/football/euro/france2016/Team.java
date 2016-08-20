package football.euro.france2016;

import java.util.Comparator;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Team implements Comparator<Team>
{
    private SimpleIntegerProperty position;
    private SimpleStringProperty name;
    private SimpleIntegerProperty played;
    private SimpleIntegerProperty wins;
    private SimpleIntegerProperty draws;
    private SimpleIntegerProperty losses;
    private SimpleIntegerProperty goalsScored;
    private SimpleIntegerProperty goalsConceded;
    private SimpleIntegerProperty differenceGoals;
    private SimpleIntegerProperty points;
    
    public Team(String name)
    {
        this.position = new SimpleIntegerProperty(0);
        this.name = new SimpleStringProperty(name);
        this.played = new SimpleIntegerProperty(0);
        this.wins = new SimpleIntegerProperty(0);
        this.draws = new SimpleIntegerProperty(0);
        this.losses = new SimpleIntegerProperty(0);
        this.goalsScored = new SimpleIntegerProperty(0);
        this.goalsConceded = new SimpleIntegerProperty(0);
        this.differenceGoals = new SimpleIntegerProperty(0);
        this.points = new SimpleIntegerProperty(0);
    }
    
    public int getPosition()
    {
        return this.position.get();
    }

    public void setPosition(int position)
    {
        this.position.set(position);
    }    
    
    public String getName()
    {
        return this.name.get();
    }
    
    public int getPlayed()
    {
        return this.played.get();
    }

    public void incrementPlayed()
    {
        this.played.set(this.played.get() + 1);
    } 

    // vitorias
    public int getWins()
    {
        return this.wins.get();
    }

    public void incrementWins()
    {
        this.wins.set(this.wins.get() + 1);
    }

    // empates
    public int getDraws()
    {
        return this.draws.get();
    }

    public void incrementDraws()
    {
        this.draws.set(this.draws.get() + 1);
    }

    // derrotas
    public int getLosses()
    {
        return this.losses.get();
    }

    public void incrementLosses()
    {
        this.losses.set(this.losses.get() + 1);
    }

    // golos marcados
    public int getGoalsScored()
    {
        return this.goalsScored.get();
    }

    public void incrementGoalsScored(int goalsScored)
    {
        this.goalsScored.set(this.goalsScored.get() + goalsScored);
    }    
    
    // golos sofridos
    public int getGoalsConceded()
    {
        return this.goalsConceded.get();
    }

    public void incrementGoalsConceded(int goalsConceded)
    {
        this.goalsConceded.set(this.goalsConceded.get() + goalsConceded);
    }

    // diferenca de golos
    public int getDifferenceGoals()
    {
        return (this.goalsScored.get() - this.goalsConceded.get());
    }
   
    // pontos
    public int getPoints()
    {
        return this.points.get();
    }

    public void incrementOnePoint()
    {
        this.points.set(this.points.get() + 1);
    }

    public void incrementThreePoints()
    {
        this.points.set(this.points.get() + 3);
    }    
    
    public int compare(Team t, Team t1)
    {
        return t.getPosition() - t1.getPosition();
    }    
}
