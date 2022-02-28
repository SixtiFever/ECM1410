import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Team {

    static ArrayList<Team> teams_objects = new ArrayList<>();

    /** FIELDS */
   // private String team_name;
    private String team_description;
    private String team_name;
    private final int id = setTeamID();  // increments by 1;
    static int team_id = 1;  // increment by 1 with each instantiation

    // stores the objects riders
    ArrayList<Rider> team_riders = new ArrayList<>();

    // constructor
    Team(){
        teams_objects.add(this);
    }

    /**
     * Create team
     * */
    // sets team object data and adds to teams ArrayList

    public int createTeam(String team_name, String team_description){
        if(this == null ){
        this.team_name = team_name;
        this.team_description = team_description;
        }
        return this.id;
    }




    /**
     * Create rider
     * */
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


    /**
     * Remove team from the teams ArrayList
     * team is starts at 1
     * */
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


    /** Add team to teams list */
    static void addToTeamsObjectList(Team obj_name){
        teams_objects.add(obj_name);
    }


    /** Get teams
     * Returns a list of team_ids
     * */
    public static int[] getTeams(){
        int[] teams = new int[Team.teams_objects.size()];
        if(teams_objects.size() < 1 ){
            System.out.println("Empty list");
        }

        for(int i = 0; i < teams.length; i++ ){
            teams[i] = Team.teams_objects.get(i).getID();
        }

        return teams;
    }


    /** Get team riders
     * Returns a list of rider ID's in a certain team
     * Use Arrays.toString(TeamName.getTeamRiders());
     * */
    int[] getTeamRiders(){

        int[] team_rider_ids = new int[this.team_riders.size()];

        int i;
        for( i = 0; i < team_rider_ids.length; i++){
            team_rider_ids[i] = this.team_riders.get(i).RIDER_ID;
        }
        return team_rider_ids;
    }

    /**
     * removeRider
     * Takes in rider id, iterated through
     * */
    public void removeRider(int rider_id){
        try {
            for(int i = 0; i < this.team_riders.size(); i++ ){
                if(rider_id == this.team_riders.get(i).RIDER_ID){
                    this.team_riders.remove(i);
                    return;
                }
            }
        } catch (IndexOutOfBoundsException e){
            System.out.println("Index issue: " + e);
        }
    }


    /**
     * ACCESSORS
     * */

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

    private int setTeamID(){
        int id = team_id++;
        return id;
    }

    public int getID(){
        return this.id;
    }

}
