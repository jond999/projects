package football.ptleague.season1617;

import java.util.ArrayList;

public class Teams
{
    private char id;
    private ArrayList<Team> teams;
    private final int NUMBER_OF_TEAMS = 18;
        
    public Teams(   char id, 
                    String name1, String name2, String name3, String name4, String name5, String name6,
                    String name7, String name8, String name9, String name10, String name11, String name12,
                    String name13, String name14, String name15, String name16, String name17, String name18
                )
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

        this.teams.add(4, new Team(name5));
        this.teams.get(4).setPosition(5);        

        this.teams.add(5, new Team(name6));
        this.teams.get(5).setPosition(6);
        
        this.teams.add(6, new Team(name7));
        this.teams.get(6).setPosition(7);
        
        this.teams.add(7, new Team(name8));
        this.teams.get(7).setPosition(8);
        
        this.teams.add(8, new Team(name9));
        this.teams.get(8).setPosition(9);

        this.teams.add(9, new Team(name10));
        this.teams.get(9).setPosition(10);
        
        this.teams.add(10, new Team(name11));
        this.teams.get(10).setPosition(11);
        
        this.teams.add(11, new Team(name12));
        this.teams.get(11).setPosition(12);
        
        this.teams.add(12, new Team(name13));
        this.teams.get(12).setPosition(13);

        this.teams.add(13, new Team(name14));
        this.teams.get(13).setPosition(14);
        
        this.teams.add(14, new Team(name15));
        this.teams.get(14).setPosition(15);
        
        this.teams.add(15, new Team(name16));
        this.teams.get(15).setPosition(16);
        
        this.teams.add(16, new Team(name17));
        this.teams.get(16).setPosition(17);

        this.teams.add(17, new Team(name18));
        this.teams.get(17).setPosition(18);
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
