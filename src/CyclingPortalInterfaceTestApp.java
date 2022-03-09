import cycling.*;

import java.io.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
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
public class CyclingPortalInterfaceTestApp implements Serializable {

	/**
	 * Test method.
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) throws InvalidNameException, IllegalNameException {





		MiniCyclingPortalInterface portal = new BadMiniCyclingPortal();
		// CyclingPortalInterface portal = new BadCyclingPortal();

		CyclingPortalInterfaceTestApp test = new CyclingPortalInterfaceTestApp();

		test.createTeam("XXX", "We are XXX...");
		test.createRider(1, "Jason",1994);

		test.createRace("Race A", "This is race A");
		Race1 raceObject = test.getRaceWithID(1);

		test.addStageToRace(1, "Stage A", "This is stage A", 50.0, LocalDateTime.now(), StageType.FLAT);
		test.addStageToRace(1, "Stage A", "This is stage A", 50.0, LocalDateTime.now(), StageType.FLAT);
		test.addStageToRace(1, "Stage A", "This is stage A", 50.0, LocalDateTime.now(), StageType.FLAT);

		test.addIntermediateSprintToStage(11, 10.0);
		test.addIntermediateSprintToStage(11, 10.0);
		test.addIntermediateSprintToStage(11, 10.0);

		Stage1 stage = raceObject.stagesInRace.get(0);

		test.registerRiderResultsInStage(11, 11, new LocalTime[]{LocalTime.of(10,00,00), LocalTime.of(12, 00,00), LocalTime.of(14,00,00)});
		System.out.println(stage.riderTime.get(11));
		System.out.println(stage.riderTime.get(11)[0]);
		System.out.println(stage.riderTime.get(11)[1]);
		System.out.println(stage.riderTime.get(11)[2]);


		assert (portal.getRaceIds().length == 0)
				: "Initial SocialMediaPlatform not empty as required or not returning an empty array.";

	}


	/** RACE METHODS */

	/** NEW
	 * creates a new race object and adds to allRaces.
	 * assigns raceID of the allRaces.size()
	 * */
	public int createRace(String name, String description) {
		Race1 race = new Race1(name, description);
		Race1.allRaces.add(race);
		race.raceID = Race1.allRaces.size();
		return race.raceID;
	}


	/** NEW
	 * adds a stage to the race -> Get race object via getRaceWithID(raceID)
	 * add to stages list in race object
	 * returns concat of raceID and stageList.size()
	 * */
	public int addStageToRace(int raceID, String stageName, String stageDescription, double length, LocalDateTime startTime, StageType type){
		Stage1 stage = new Stage1(stageName, stageDescription, type); // create a new stage object
		stage.length = length;
		stage.startTime = startTime;
		Race1 raceObject = getRaceWithID(raceID); // grab race to add to
		raceObject.stagesInRace.add(stage); // add to stages in race
		// Assign stage ID
		String stringRaceID = Integer.toString(raceID);
		String stringStageID = Integer.toString(raceObject.stagesInRace.size());
		String stageID = stringRaceID + stringStageID;
		stage.stageID = Integer.parseInt(stageID);
		return stage.stageID;
	}

	/** NEW
	 * Removes from static race_list member in race class
	 * */
	public void removeRaceByID(int raceID){
		Race1.allRaces.remove(raceID);
	}


	/** NEW
	 * iterates allRaces, extracts race with argument raceName. Removes from ArrayList.
	 * */
	public void removeRaceByName(String raceName){
		int i;
		for( i = 0; i < Race1.allRaces.size(); i++ ){
			if( raceName == Race1.allRaces.get(i).raceName){
				Race1.allRaces.remove(i);
				return;
			}
		}
		System.out.println("Race not found....");
		return;
	}


	/** NEW
	 *
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
		if(Race1.allRaces.size() < raceID){
			System.out.println("raceID exceeds number of races... Try another ID");
			return new int[0];
		}
		Race1 raceObject = getRaceWithID(raceID);
		int[] race_stages = new int[raceObject.stagesInRace.size()];
		try{
			race_stages = new int[raceObject.stagesInRace.size()];
			for(i = 0; i < race_stages.length; i++){
				race_stages[i] = raceObject.stagesInRace.get(i).stageID;
			}
		} catch (IndexOutOfBoundsException iob){
			System.out.println("Index issue: " + iob);
		}
		return race_stages;
	}


	/** NEW
	 * Iterated a nested for-loop in search of stageID. If found... removes
	 * */
	public void removeStageByID(int stageID) {
		int i,j;

		for( i = 0; i < Race1.allRaces.size(); i++ ) {
			Race1 raceObject = Race1.allRaces.get(i);
			for (j = 0; j < raceObject.stagesInRace.size(); j++) {
				if (stageID == raceObject.stagesInRace.get(j).stageID) {
					raceObject.stagesInRace.remove(j);
				}
			}
		}
	}


	/**  NEW
	 * nested for loop to iterate all races and then stage list until matched a stageID
	 * returns the length of that stage ID
	 * */
	public double getStageLength(int stageID) {
		int i,j;
		double length = 0.0;

		for( i = 0; i < Race1.allRaces.size(); i++) {
			Race1 raceObject = Race1.allRaces.get(i);
			for( j = 0; j < raceObject.stagesInRace.size(); j++ ){
				if( stageID == raceObject.stagesInRace.get(j).stageID ) {
					length = raceObject.stagesInRace.get(j).length;
					break;
				}
			}
		}
		return length;
	}



	/** NEW */

	/**
	 * argument = raceID --> find from race_list
	 * sumOfStages gives total length of all stages
	 * returns all as a String
	 * */
	public String viewRaceDetails(int raceID) {

		Race1 raceObject = getRaceWithID(raceID);

		return "Race ID : " + raceObject.raceID + " | Race name: " + raceObject.raceName + " | Race Description: " + raceObject.raceDescription
				+ " | Number of stages: " + raceObject.stagesInRace.size() + " | Total length: " + raceObject.sumOfstages();
	}


	/** NEW
	 *
	 * */
	public int addIntermediateSprintToStage(int stageID, double location) {
		Stage1 stageObject = locateStageObject(stageID);
		Segment1 segObject = new Segment1(location);
		stageObject.stageSegments.add(segObject);

		// creating segID via concatenation to stageIDs
		String segIDString = String.valueOf(stageID) + String.valueOf(stageObject.stageSegments.size());
		int segID = Integer.parseInt(segIDString);
		segObject.segmentID = segID;
		return segID;

	}



	/** NEW
	 *
	 * */
	public int addCategorisedClimb(int stageID, double location, SegmentType type, double averageGradient, double length ) {
		Stage1 stageObject = locateStageObject(stageID);  // grabbing correct stage object
		Segment1 segObject = new Segment1(location);  // instantiating segment instance
		segObject.segType = type;
		segObject.averageGradient = averageGradient;
		segObject.length = length;
		stageObject.stageSegments.add(segObject);  // adding segment to segment list in stage

		// createing segID via concatonation to stageID
		String segIDString = String.valueOf(stageID) + String.valueOf(stageObject.stageSegments.size());
		int segID = Integer.parseInt(segIDString);
		segObject.segmentID = segID;
		return segID;
	}


	/** NEW
	 * removes the segment from the first race, and first stage within the race.
	 * Can be altered via hardcoding
	 * */
	public void removeSegment(int segID) {
		int i,j,k;
		for( i = 0; i < Race1.allRaces.size(); i++ ){
			Race1 raceObject = Race1.allRaces.get(i);
			for( j = 0; j < raceObject.stagesInRace.size(); j++ ){
				Stage1 stageObject = raceObject.stagesInRace.get(j);
				for( k = 0; k < stageObject.stageSegments.size(); k++ ){
					Segment1 segObject = stageObject.stageSegments.get(k);
					if( segID == segObject.segmentID) {
						stageObject.stageSegments.remove(k);
					}
				}
			}
		}
	}



	/** NEW
	 * isolates stage object
	 * uses for loop to iterate stage segments, and add to new array of type int[]
	 * returns stageSegments[];
	 * */
	public int[] getStageSegments(int stageID){
		Stage1 stageObject = locateStageObject(stageID);
		int[] stageSegments = new int[stageObject.stageSegments.size()];
		int i;
		for(i = 0; i < stageObject.stageSegments.size(); i++) {
			stageSegments[i] = stageObject.stageSegments.get(i).segmentID;
		}
		return stageSegments;
	}


	/** NEW
	 * returns the number of stages for a race
	 * */
	public int getNumberOfStages(int raceID){
		Race1 raceObject = getRaceWithID(raceID);
		int noOfStages = raceObject.stagesInRace.size();
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


	/**
	 * extracts teamID's from the team objects in the teams list
	 * */
	public int[] getTeams() {
		int i;
		int[] teamIDArray = new int[Team1.team_list.size()];
		for( i = 0; i < Team1.team_list.size(); i++ ) {
			int teamID = Team1.team_list.get(i).teamID;
			teamIDArray[i] = teamID;
		}
		return teamIDArray;
	}


	/**
	 * iterate through team members array of a given teamID
	 * return riders of the team
	 */
	public int[] getTeamRiders(int teamID) {
		int i;
		Team1 teamObject = getObjectWithTeamID(teamID);
		int[] teamRiderIDs = new int[teamObject.teamMembers.size()];
		for( i = 0; i < teamRiderIDs.length; i++ ){
			teamRiderIDs[i] = teamObject.teamMembers.get(i).riderID;
		}
		return teamRiderIDs;
	}



	/** RESULT RECORDING */

	/**
	 * params: riderID, stageID, LocalTime array of times
	 * HashMap --> RiderID : Time to complete stage (seconds)
	 * */
	public void registerRiderResultsInStage(int stageID, int riderID, LocalTime[] checkpoints) {
		Stage1 stageObject = locateStageObject(stageID); // stage instance
		Rider1 riderObject = findRider(riderID); // rider instance
		Integer wrappedRiderID = riderObject.riderID;

		if (checkpoints.length > stageObject.stageSegments.size()){
			System.out.println("Checkpoint count exceeds segments... Reduce checkpoint input count.");
			return;
		}

		LocalTime[] internalTimes = new LocalTime[stageObject.stageSegments.size()];

		int i;
		for( i = 0; i < stageObject.stageSegments.size(); i++ ){
			internalTimes[i] = checkpoints[i];
		}

		// adding results to int[] array of the stage
		Long seconds = ChronoUnit.SECONDS.between( checkpoints[0] , checkpoints[checkpoints.length-1] );
		stageObject.riderResults.add(Math.toIntExact(seconds));
		stageObject.rankedRiderTimes.add(Math.toIntExact(seconds));
		Collections.sort(stageObject.riderResults);
		Collections.sort(stageObject.rankedRiderTimes);
		int indexOfResult = stageObject.riderResults.indexOf(Math.toIntExact(seconds));
		stageObject.riderResults.set(indexOfResult, riderID);
		stageObject.riderTime.put(wrappedRiderID, internalTimes);  // HashMap
	}


	/**
	 * returns the LocalTime[] checkpoint times of the rider in that stage
	 * */
	public LocalTime[] getRiderResultsInStage(int stageID, int riderID) {
		Stage1 stageObject = locateStageObject(stageID);
		if( stageObject.riderTime.get(riderID) == null ){
			return new LocalTime[0];
		}
		return stageObject.riderTime.get(riderID);
	}


	/**
	 * converts second into seconds of the day
	 * */
	public LocalTime getRiderAdjustedElapsedTimeInStage(int stageID, int riderID){
		Stage1 stageObject = locateStageObject(stageID);
		LocalTime[] riderTimes = stageObject.riderTime.get(riderID);
		Long seconds = ChronoUnit.SECONDS.between(riderTimes[0], riderTimes[riderTimes.length-1]);
		return LocalTime.ofSecondOfDay(seconds);
	}


	/**
	 * delete rider in stage HashMap
	 * */
	public void deleteRiderResultsInStage(int stageID, int riderID) {
		Stage1 stageObject = locateStageObject(stageID);
		stageObject.riderTime.remove(riderID);
	}


	/**
	 * getRidersRanks
	 *
	 * */
	public int[] getRidersRankInStage(int stageID){
		Stage1 stageObject = locateStageObject(stageID);
		int[] riderResulstsArray = new int[200];
		int i;
		for( i = 0; i < stageObject.riderResults.size(); i++ ){
			riderResulstsArray[i] = stageObject.riderResults.get(i);
		}
		return riderResulstsArray;
	}


	public int[] getRankedAdjustedElapsedTimeInStage(int stageID) {
		Stage1 stageObject = locateStageObject(stageID);
		int[] riderTimes = new int[stageObject.rankedRiderTimes.size()];
		int i;
		for( i = 0; i < stageObject.rankedRiderTimes.size(); i++ ) {
			riderTimes[i] = stageObject.rankedRiderTimes.get(i);
		}
		return riderTimes;
	}


	/** getRidersPointsInStage
	 * Allocate rider points based on their position in the rankedRiderTimes array.
	 * Check stageType and position --> Then allocate required points
	 * return the list
	 * */

	public int[] getRidersPointsInStage(int stageID) {
		Stage1 stageObject = locateStageObject(stageID);
		int[] riderPoints = new int[stageObject.rankedRiderTimes.size()];
		int i;
		for( i = 0; i < stageObject.rankedRiderTimes.indexOf(14); i++){
			riderPoints[i] = stageObject.rankedRiderTimes.get(i);
			return riderPoints;
		}
		return new int[0];
	}


	/**
	 * get mountain points
	 * */
	public int[] getRidersMountainPointsInStage(int stageID) {
		Stage1 stageObject = locateStageObject(stageID);
		int[] riderMountainPoints = new int[stageObject.rankedRiderTimes.size()];
		int i;
		for( i = 0; i < stageObject.rankedRiderTimes.indexOf(14); i++){
			riderMountainPoints[i] = stageObject.rankedRiderTimes.get(i);
			return riderMountainPoints;
		}
		return new int[0];
	}


	/**
	 * Clears everything
	 * */
	public void eraseCyclingPortal(){
		int i, j;
		for( i = 0; i < Race1.allRaces.size(); i++ ){
			Race1 raceObject = Race1.allRaces.get(i);
			for( j = 0; i < raceObject.stagesInRace.size(); j++ ){
				Stage1 stageObject = raceObject.stagesInRace.get(j);
				stageObject.rankedRiderTimes.clear();
				stageObject.stageSegments.clear();
				stageObject.riderResults.clear();
				stageObject.riderTime.clear();
				raceObject.stagesInRace.remove(j);
			}
		}
		Race1.allRaces.clear();
	}


	/**
	 * filename = RaceData.ser
	 * */
	public void saveCyclingPortal(String filename) throws IOException {

		FileOutputStream fileout = new FileOutputStream("cycling/RaceData.ser");
		ObjectOutputStream out = new ObjectOutputStream(fileout);
		out.writeObject(Race1.allRaces);
	}


	public void loadCyclingPortal(String filename) throws IOException {
		Race1 race = null;
		FileInputStream filein = new FileInputStream("/Users/JDSwift/Desktop/Assignment/src/RaceData.ser");
		ObjectInputStream in = new ObjectInputStream(filein);
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

	public Race1 getRaceWithID(int raceID) {
		int i;
		for( i = 0; i < Race1.allRaces.size(); i++ ){
			if( raceID == Race1.allRaces.get(i).raceID ){
				return Race1.allRaces.get(i);
			}
		}
		return null;
	}

	public Stage1 locateStageObject( int stageID ) {
		int i,j;
		for( i = 0; i < Race1.allRaces.size(); i++ ){
			Race1 raceObject = Race1.allRaces.get(i);
			for ( j = 0; j < raceObject.stagesInRace.size(); j++ ) {
				if( stageID == raceObject.stagesInRace.get(j).stageID ) {
					return raceObject.stagesInRace.get(j);
				} else {
					System.out.println("StageID not locatable...");
				}
			}
		}
		return null;
	}


	public Rider1 findRider(int riderID) {
		int i,j;
		for( i = 0; i < Team1.team_list.size(); i++ ){
			Team1 team = Team1.team_list.get(i);
			for( j = 0; j < team.teamMembers.size(); j++ ){
				if( riderID == team.teamMembers.get(j).riderID){
					return team.teamMembers.get(j);
				}
			}
		}
		return null;
	}




}
