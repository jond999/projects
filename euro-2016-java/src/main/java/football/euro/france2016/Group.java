package football.euro.france2016;

import java.util.ArrayList;

public class Group
{
    private char id;
    private ArrayList<Team> teams;
    private final int NUMBER_OF_TEAMS = 4 ;
        
    public Group(char id, String name1, String name2, String name3, String name4)
    {
        this.id = id;
        
        this.teams = new ArrayList<Team>();
        
        this.teams.add(0, new Team(name1));
        this.teams.get(0).setPosition(1);
        
        this.teams.add(1, new Team(name2));
        this.teams.get(1).setPosition(2);
        
        this.teams.add(2, new Team(name3));
        this.teams.get(2).setPosition(3);
        
        this.teams.add(3, new Team(name4));
        this.teams.get(3).setPosition(4);
    }
    
    public char getId()
    {
        return this.id;
    }
    
    public Team getTeam(int position)
    {
        return this.teams.get(position - 1);
    }

    public Team getTeam(String name)
    {
        Team x;
       
        for(int i = 0; i < NUMBER_OF_TEAMS; i++)
        {
            x = this.teams.get(i);
            
            if(x.getName().equals(name))
                return x;
        }

        return null;
    }    
    
    public ArrayList<Team> getTeams()
    {
        return this.teams;
    }
}
