import cycling.StageType;
import java.util.ArrayList;

public class Race {

    final int RACE_ID = incrementRaceCount();  // race ID is one above the index
    static int race_count = 0;
    private String race_name;
    private String race_description;

    ArrayList<Team> race_teams = new ArrayList<>(); // hold race teams
    ArrayList<Stage> stages = new ArrayList<>();
    static ArrayList<Race> race_list = new ArrayList<>();


    /** CONSTRUCTOR */
    Race(String race_name, String race_description) {
        this.race_name = setRaceName(race_name);
        this.race_description = setRaceDescription(race_description);
        race_list.add(this);
    }

    Race(){

    }


    /** ACCESSORS */
    public String setRaceName(String name){return this.race_name = name;}
    public String setRaceDescription(String description){return this.race_description = description;}
    public String getRaceName() {return this.race_name;}
    public String getRaceDescription() {return this.race_description;}


    /** METHODS */
    // increment the race id. Occurs every instantiation.
    public int incrementRaceCount(){return ++Race.race_count;}


}


