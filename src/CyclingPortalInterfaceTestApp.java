import cycling.*;

import java.time.LocalDateTime;
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
		test.addStageToRace(0,"Race 1", "First race", "Stage 1",
				"First stage" , 20.0, LocalDateTime.now(), StageType.FLAT);
		test.addStageToRace(0,"Race 1", "First race", "Stage 1",
				"First stage" , 20.0, LocalDateTime.now(), StageType.FLAT);
		test.addStageToRace(0,"Race 1", "First race", "Stage 1",
				"First stage" , 20.0, LocalDateTime.now(), StageType.FLAT);

		System.out.println(test.viewRaceDetails(0));
		System.out.println(test.viewRaceDetails(1));
		System.out.println(Race.race_list);






		assert (portal.getRaceIds().length == 0)
				: "Initial SocialMediaPlatform not empty as required or not returning an empty array.";

	}

	int createRace(String name, String description){
		Race race_object = new Race(name, description);
		return race_object.RACE_ID;
	}

	/**
	 * Removes from static race_list member in race class
	 * */
	public void removeRaceByID(int raceID){
		Race.race_list.remove(raceID);
	}

	int addStageToRace(int race_id, String raceName, String raceDescription, String stageName,
					   String stageDescription, double length, LocalDateTime startTime, StageType type){
		Stage stage = new Stage(raceName, raceDescription,
				stageName, stageDescription, length, startTime, type);
		if(Race.race_list.get(race_id) != null){  // if index isn't empty
			Race temp = Race.race_list.get(race_id); // store object in temp reference type for manipulation
			temp.stages.add(stage); // add the instantiated stage to the instance stages array.
		}
		stage.stageID = Race.race_list.get(race_id).stages.size();
		return stage.stageID;
	}

	/**
	 * getRaceStages
	 * Takes in raceID and returns an array of stageID from that race.
	 * Uses if statement to ensure that raceID exists. If invalid raceID is input, returns out
	 * of method with empty array.
	 * Creates local raceObject located in race_list at index of the argument raceID.
	 * Then iterates through the race_stages.length and appends all to new array.
	 * new array is returned
	 * */
	public int[] getRaceStages(int raceID){
		int i;
		if(Race.race_list.size() < raceID){
			System.out.println("raceID exceeds number of races... Try another ID");
			return new int[0];
		}
		Race raceObject = Race.race_list.get(raceID);
		int[] race_stages = new int[raceObject.stages.size()];
		try{
			race_stages = new int[raceObject.stages.size()];
			for(i = 0; i < race_stages.length; i++){
				race_stages[i] = raceObject.stages.get(i).stageID;
			}
		} catch (IndexOutOfBoundsException iob){
			System.out.println("Index issue: " + iob);
		}
		return race_stages;
	}


	/**
	 * By default, removes stages from the race at [0] in races_list.
	 * Hardcode this to remove ID from stages from a different race
	 * */
	public void removeStageByID(int stageID) {
		try{
			Race raceObject = Race.race_list.get(0);
			raceObject.stages.remove(stageID);
		} catch (IndexOutOfBoundsException iob){
			System.out.println("Index issue: " + iob);
		}
		return;
	}


	/**
	 * Default: operates on race at index race_list[0]. Can change in method if desired
	 *
	 * */
	public double getStageLength(int stageID){
		double stage_length = 0.0;
		try {
			Race raceObject = Race.race_list.get(0);
			Stage stageObject = raceObject.stages.get(stageID);
			stage_length = stageObject.length;
		} catch(IndexOutOfBoundsException iob){
			System.out.println("Index issue: " + iob);
		}
		return stage_length;
	}

	/**
	 * returns a string of the race details
	 * nested in try-catch to catch index issues
	 * */
	public String viewRaceDetails(int raceID){
		Race raceObject;
		String raceName = "";
		String raceDescription = "";
		int ID = 0;
		int noOfStages = 0;
		double totalLength = 0.0;
		try {
			raceObject = Race.race_list.get(raceID);
			raceName = raceObject.getRaceName();
			raceDescription = raceObject.getRaceDescription();
			ID = raceObject.RACE_ID;
			noOfStages = raceObject.stages.size();
			totalLength = 0.0;
			for(int i = 0; i < noOfStages; i++){
				totalLength = totalLength + raceObject.stages.get(i).length;
			}
		} catch (IndexOutOfBoundsException iob){
			System.out.println("Index issues in race_list: " + iob);
		}
		return "Race Name: " + raceName + " | Race Description: " + raceDescription + " | Race ID: " + ID +
				" | Number of stages: " + noOfStages + " | Sum of stages: " + totalLength;
	}

	/**
	 *
	 * */
	public void removeRaceByName(String raceName){
		int i;
		for( i = 0; i < Race.race_list.size(); i++ ){
			if( raceName == Race.race_list.get(i).getRaceName()){
				Race.race_list.remove(i);
			}
			if( raceName != Race.race_list.get(i).getRaceName() && i == Race.race_list.size() ) {
				System.out.println("Race name couldn't be found");
				break;
			}
		}
	}

}
