package football.ptleague.season1617;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public final class Teams
{
    private final String XML_FILE_NAME = "portuguese-league-2016-2017.xml";
    private int numberOfTeams;
    private ArrayList<Team> teams;
        
    public Teams()
    {        
        SAXBuilder builder = new SAXBuilder();  

        try
        {                                                             
            File file = new File(XML_FILE_NAME);
            Document document = (Document)builder.build(file);
                                    
            Element competition = document.getRootElement();
            
            Element championship = competition.getChild("Championship");                                   
    
            for(Element team : championship.getChildren())
            {
                this.addTeam(team.getText());
                this.numberOfTeams++;
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
        
    public Team getTeam(int position)
    {
        return this.teams.get(position - 1);
    }

    public Team getTeam(String name)
    {
        Team x;
       
        for(int i = 0; i < this.numberOfTeams; i++)
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
    
    public void addTeam(String name)
    {
        if(this.teams == null)
            this.teams = new ArrayList<Team>();
            
        this.teams.add(new Team(name));
    }

    public int getNumberOfTeams()
    {
        return this.numberOfTeams;
    }    
}
