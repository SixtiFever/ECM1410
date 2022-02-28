import cycling.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.*;

/**
 * A short program to illustrate an app testing some minimal functionality of a
 * concrete implementation of the CyclingPortalInterface interface -- note you
 * will want to increase these checks, and run it on your CyclingPortal class
 * (not the BadCyclingPortal class).
 *
 * 
 * @author Diogo Pacheco
 * @version 1.0
 */
public class CyclingPortalInterfaceTestApp {

	/**
	 * Test method.
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) throws InvalidNameException, IllegalNameException {





		MiniCyclingPortalInterface portal = new BadMiniCyclingPortal();
		// CyclingPortalInterface portal = new BadCyclingPortal();

		CyclingPortalInterfaceTestApp test = new CyclingPortalInterfaceTestApp();

		// get race objects via index in the races_list

		test.createRace("Race 1", "Race description");
		test.createRace("Race 2", "Race description");
		test.addStageToRace(0);
		test.addStageToRace(0);
		test.addStageToRace(1);
		test.addStageToRace(1);
		Race temp = Race.race_list.get(0);
		Race temp1 = Race.race_list.get(1);
		System.out.println(temp.stages.size());
		System.out.println(temp1.stages.size());


		System.out.println(Race.race_count);
		System.out.println(Race.race_list.get(1).RACE_ID);






		assert (portal.getRaceIds().length == 0)
				: "Initial SocialMediaPlatform not empty as required or not returning an empty array.";

	}

	int createRace(String name, String description){
		Race race_object = new Race(name, description);
		return race_object.RACE_ID;
	}

	int addStageToRace(int race_id){
		Stage stage = new Stage(StageType.FLAT, "Stage1", "Stage description");
		if(Race.race_list.get(race_id) != null){  // if index isn't empty
			Race temp = Race.race_list.get(race_id); // store object in temp reference type for manipulation
			temp.stages.add(stage); // add the instantiated stage to the race object stages array.
		}
		int stage_id = Race.race_list.get(race_id).stages.size(); // stage id corresponds to array index
		return stage_id;
	}


}
