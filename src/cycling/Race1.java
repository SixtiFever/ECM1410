package cycling;

import java.util.ArrayList;

public class Race1 {

    /** INSTANCE */
    String raceName;
    String raceDescription;
    int raceID;

    public ArrayList<Stage1> stagesInRace = new ArrayList<>();

    /** STATIC */
    static ArrayList<Race1> allRaces = new ArrayList<>();

    /** CONSTRUCTOR */
    Race1(String raceName, String raceDescription) {
        this.raceName = raceName;
        this.raceDescription = raceDescription;
        this.raceID = assignRaceID();
    }

    Race1() {

    }

    /** METHODS */

    public int assignRaceID(){
        return this.raceID = allRaces.size();
    }

    public double sumOfstages(){
        int i;
        double total = 0.0;
        for( i = 0; i < this.stagesInRace.size(); i++ ){
            total += this.stagesInRace.get(i).length;
        }
        return total;
    }

}
