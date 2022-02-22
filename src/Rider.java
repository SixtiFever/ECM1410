import java.util.ArrayList;
import java.util.Random;

public class Rider {

    /*
    Rider class to create instances of type Rider. To create and instance of type Rider,
    there is a createRider() method in the Team class. This method auto assigns the Rider to
    a team with a unique team ID.
    */


    Random r = new Random();

    // instance members
    String name;
    int year_of_birth;
    int team_id;
    final int RIDER_ID = r.nextInt(100,1000);

    // for storing individual rider score
    int elapsed_time;
    int mountain_points;
    int points;


    // structs
    static ArrayList<Rider> all_riders = new ArrayList<>();


    Rider(){

    }

}
