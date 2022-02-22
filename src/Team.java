import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Team {

     /* STATICS */
    // data structures for holding team data
    static ArrayList<Team> teams_objects = new ArrayList<>();
    // static ArrayList<Integer> teams = new ArrayList<>();

    /* INSTANCES */
    // instance members
    private String team_name;
    private String team_description;
    private final int id = setTeamID();  // increments by 1;

    static int team_id = 1;  // increment by 1 with each instantiation



    // stores the objects riders
    ArrayList<Rider> team_riders = new ArrayList<>();



    // constructor
    Team(){
        teams_objects.add(this);
    }


    // sets team object data and adds to teams ArrayList
    public int createTeam(String team_name, String team_description){
        this.team_name = team_name;
        this.team_description = team_description;

        // teams.add(this.id);
        return this.id;
    }


    // creating rider
    public int createRider(String rider_name, int yob){

        Rider rider = new Rider(); // instantiate a rider object

        rider.team_id = this.id; // set rider members
        rider.name = rider_name;
        rider.year_of_birth = yob;

        team_riders.add(rider); // add to team_riders list
        Rider.all_riders.add(rider); // add to all_riders list

        // return rider ID
        Random r = new Random();
        return rider.RIDER_ID;
    }


    // remove team from team ArrayList
    static public void removeTeam(int team_id){
        int id = team_id;
        try {
            for (int i = 0; i < Team.teams_objects.size(); i++) {
                if (Team.teams_objects.get(i).id == id) {
                    Team.teams_objects.remove(i);
                }
            }
        } catch (IndexOutOfBoundsException iob){
            System.out.println("Index issue... : " + iob);
        }
    }



    // get rider data from Rider ArrayList



    static void addToTeamsObjectList(Team obj_name){
        teams_objects.add(obj_name);
    }


    /* METHODS */

    /* - creates a team object
    - Set's instance members team_name, description, ID.
    - Adds to ArrayList of type Team, which stores each Team Object, and can be used to access object instance members */
    /*
    static public int createTeam(String team_name, String team_description){
        Team team = new Team(team_name, team_description);
        teams.add(team);
        System.out.println(team.team_name);
        System.out.println(team.team_description);
        System.out.println(team.id);
        return team.id;
    }

     */



/*
    // Returns the specified instance of Rider.
    public Rider createRider(String team_id, String name, int yob){
        Rider rider = new Rider(name, yob);
        rider.team_name = this.team_name;
        rider.team_id = this.id;
        System.out.printf("Name: %s | YOB: %s | Team ID: %s.%n", rider.name, rider.year_of_birth, this.id);
        // team_riders_list.add(rider);
        return rider;
    }

 */



    // generate team ID
    private int setTeamID(){
        int id = team_id++;
        return id;
    }

    public int getID(){
        return this.id;
    }



    /* GETTERS */

    // get team name
    public String getName(){
        String name = this.team_name;
        return name;
    }

    // get description
    public String getDescription(){
        String description = this.team_description;
        return description;
    }

}
