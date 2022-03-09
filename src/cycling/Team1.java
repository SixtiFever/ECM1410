package cycling;

import java.util.ArrayList;

public class Team1 {

    /** INSTANCE MEMBERS */
    String teamName;
    String teamDescription;
    int teamID;

    ArrayList<Rider1> teamMembers = new ArrayList<>(); // list of riders of the team instance


    /** STATIC MEMBERS */
    static ArrayList<Team1> team_list = new ArrayList<>(); // list of all teams


    // name, description, adds to team_list, assigns team_ID as current size of ArrayList.
    Team1(String teamName, String teamDescription ){
        this.teamName = teamName;
        this.teamDescription = teamDescription;
    }


}
