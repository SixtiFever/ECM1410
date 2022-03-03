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

		test.createTeam("Team A", "We are team A!");
		test.createTeam("Team B", "We are team B!");

		Team1 team1 = Team1.team_list.get(0);

		System.out.println(team1.teamMembers.size());
		System.out.println();
		test.createRider(1, "Jason", 1994);
		test.createRider(1, "Jason", 1994);
		System.out.println();
		for( int i = 0; i < team1.teamMembers.size(); i++ ){
			System.out.println(team1.teamMembers.get(i).riderID);
		}

		test.removeRider(11);
		System.out.println();
		for( int i = 0; i < team1.teamMembers.size(); i++ ){
			System.out.println(team1.teamMembers.get(i).riderID);
		}

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
	 * removes race by its name
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


	/**
	 * instantiates a segment object
	 * pulls the stageObject at relevant index
	 * adds segment object to segments struct in Stage instance ArrayList.
	 * */
	public int addIntermediateSprintToStage(int stageID, double location){
		Segment segmentObject = new Segment(stageID, SegmentType.SPRINT ,location );
		Stage stageObject = Race.race_list.get(0).stages.get(stageID);
		stageObject.segments.add(segmentObject);
		return stageObject.segments.size();
	}


	/**
	 * Adds to race 1 (race_list[0]) at specified stage ID
	 * Adds to segment list of that stage
	 * Returns the size of the segments list... which correlates to the segment ID
	 * */
	public int addCategorizedClimbToStage(int stageID, double location, SegmentType type, double averageGradient, double length){
		// stage object to add to
		Stage stageObject = Race.race_list.get(0).stages.get(stageID);  // since index and stageID are the same
		Segment segmentObject = new Segment(stageID, type, location);
		segmentObject.averageGradient = averageGradient;
		segmentObject.length = length;
		stageObject.segments.add(segmentObject); // adding new segment to segment struct in the stage
		return stageObject.segments.size();
	}


	/**
	 * removes the segment from the first race, and first stage within the race.
	 * Can be altered via hardcoding
	 * */
	public void removeSegment(int segmentID) {
		Stage stageObject = Race.race_list.get(0).stages.get(0);
		stageObject.segments.remove(segmentID);
		return;
	}


	/**
	 * isolates stage object
	 * uses for loop to iterate stage segments, and add to new array of type int[]
	 * returns stageSegments[];
	 * */
	public int[] getStageSegments(int stageID){
		Stage stageObject = Race.race_list.get(0).stages.get(stageID);
		int[] stageSegments = new int[stageObject.segments.size()];
		int i;
		for(i = 0; i < stageObject.segments.size(); i++) {
			stageSegments[i] = stageObject.segments.get(i).stageID;
		}
		return stageSegments;
	}


	/**
	 * returns the number of stages for a race
	 * */
	public int getNumberOfStages(int raceID){
		Race raceObject = Race.race_list.get(raceID);
		int noOfStages = raceObject.stages.size();
		return noOfStages;
	}


	/**
	 * ridiculously vague method.... No idea what to put in here.
	 * */
	public void concludeStagePreparation(int stageID){
		Stage stageObject = Race.race_list.get(0).stages.get(stageID);
	}


	/**
	 *  TEAMS
	 * */

	public int createTeam(String teamName, String teamDescription){
		Team1 team = new Team1(teamName, teamDescription);
		int teamID = 0;

		if(!checkForName(teamName)){  // nested method to check names
			Team1.team_list.add(team);
			team.teamID = Team1.team_list.size();
			teamID = team.teamID;
		} else {
			return 0;
		}

		return teamID;
	}



	/**
	 * remove team by ID
	 * iterates for loop, comparing Team ID. Removes if equal.
	 * If not found, throws error
	 * */
	public void removeTeam(int teamID) {
		int i;
		for( i = 0; i < Team1.team_list.size(); i++ ){
			if( teamID == Team1.team_list.get(i).teamID){
				Team1.team_list.remove(i);
				return;
			} else {
				System.out.println("Team ID not recognised...");
				return;
			}
		}
	}



	public int createRider(int teamID, String riderName, int yearOfBirth){

		Rider1 rider = new Rider1(riderName, yearOfBirth); // instantiate new rider
		if(!Rider1.checkForTeamID(teamID)){  //
			System.out.println("Team ID not found in team list");
			return 0;
		} else {
			Rider1.addRiderToTeam(teamID, rider); // add rider to team if team ID arg matches teamID in list of teams
			rider.riderID = assignRiderID(teamID);
			return assignRiderID(teamID); // Assign a riderID to the rider.
		}
	}


	/**
	 * iterate all riderIDs in all teams.
	 * returns the rider object to be removed
	 * */
	public void removeRider(int riderID){
		int i;
		int j;
		for( i = 0; i < Team1.team_list.size(); i++) {  // iterate team_list
			Team1 teamObject = Team1.team_list.get(i);  // grab team object at index

			for( j = 0; j < teamObject.teamMembers.size(); j++ ){  // iterate object instance Rider Array
				if(riderID == teamObject.teamMembers.get(j).riderID){  // grab rider object with rider id
					Rider1 riderObject = teamObject.teamMembers.get(j);
					teamObject.teamMembers.remove(j); // remove rider object from team members
					return;
				}
			}
			if(i == Team1.team_list.size()){
				System.out.println("Rider ID not found...");
				return;
			}
		}
	}

	/** EXTRA METHODS */


	/**
	 * get teamObject from teamID --> Assign teamID as first element of riderID
	 * get riderID from teamMembers ArrayList.size() --> Assign string int as second element.
	 * cast back to int in return. This bypasses arithematic operators to ensure concatination, not addition.
	 * */
	public int assignRiderID(int teamID){
		Team1 teamObject = getObjectWithTeamID(teamID);
		String riderID = Integer.toString(teamObject.teamMembers.size());
		return Integer.parseInt( teamID + riderID);
	}


	boolean checkForName(String teamName){
		int i;
		for( i = 0; i < Team1.team_list.size(); i++ ){
			if(teamName == Team1.team_list.get(i).teamName){
				System.out.println("Name already used...: " + teamName);
				return true;
			}
		}
		return false;
	}

	public Team1 getObjectWithTeamID(int teamID){
		int i;
		for( i = 0; i < Team1.team_list.size(); i++ ){
			if( teamID == Team1.team_list.get(i).teamID){
				return Team1.team_list.get(i);
			}
		}
		return null;
	}




}
