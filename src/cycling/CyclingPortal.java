package cycling;

import java.io.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;

public class CyclingPortal implements Serializable, MiniCyclingPortalInterface {

    /** RACE METHODS */

    /** createRace()
     * <p>
     * creates a new race object and adds to allRaces.
     * raceID = current allRaces.size()
     * </p>
     * @author Jason Swift
     * */
    @Override
    public int createRace(String name, String description) throws IllegalNameException, InvalidNameException {
        Race1 race = new Race1(name, description);
        try{
            Race1.allRaces.add(race);
        } catch ( Exception exc){
            System.out.println("Race already registered: " + exc);
            return 0;
        }
        race.raceID = Race1.allRaces.size();
        return race.raceID;
    }

    /** getRaceIDs()
     * <p>
     * returns array of raceIDs currently on platform
     * if none, return null.
     * </p>
     * @author Jason Swift
     * */
    @Override
    public int[] getRaceIds() {
        if (Race1.allRaces.size() == 0) {
            System.out.println("No races active on platform.");
            return null;
        }
        int[] raceIDs = new int[Race1.allRaces.size()];
        int i;
        for( i = 0; i < Race1.allRaces.size(); i++ ) {
            raceIDs[i] = Race1.allRaces.get(i).raceID;
        }
        return raceIDs;
    }


    /** addStageToRace()
     * <p>
     * adds a stage to the race -> Get race object via getRaceWithID(raceID)
     * add to stages list in race object
     * returns concatenation of raceID and stageList.size() e.g 12 --> RaceID = 1, stageList.size() = 2
     * </p>
     * @author Jason Swift
     * */
    @Override
    public int addStageToRace(int raceID, String stageName, String stageDescription, double length, LocalDateTime startTime, StageType type)
            throws IDNotRecognisedException, IllegalNameException, InvalidNameException, InvalidLengthException {
        Stage1 stage = new Stage1(stageName, stageDescription, type); // create a new stage object
        stage.length = length;
        stage.startTime = startTime;
        Race1 raceObject;
        try {
            raceObject = getRaceWithID(raceID); // grab race to add to
        } catch (Exception exc){
            System.out.println("Invalid race ID:" + exc);
            return 0;
        }
        raceObject.stagesInRace.add(stage); // add to stages in race
        // Assign stage ID
        String stringRaceID = Integer.toString(raceID);
        String stringStageID = Integer.toString(raceObject.stagesInRace.size());
        String stageID = stringRaceID + stringStageID;
        stage.stageID = Integer.parseInt(stageID);
        return stage.stageID;
    }

    /** removeRaceById()
     * <p>
     * Removes from static race_list member in race class
     * </p>
     * @author Jason Swift
     * */
    @Override
    public void removeRaceById(int raceID) throws IDNotRecognisedException {
        Race1.allRaces.remove(raceID);
    }


    /** removeRaceByName()
     * <p>
     * iterates allRaces, extracts race with argument raceName. Removes from ArrayList.
     * </p>
     * @author Jason Swift
     * */
    public void removeRaceByName(String raceName) throws NameNotRecognisedException {
        int i;
        for(i = 0; i < Race1.allRaces.size(); i++ ){
            if( raceName == Race1.allRaces.get(i).raceName){
                Race1.allRaces.remove(i);
                return;
            }
        }
        System.out.println("Race not found....");
    }


    /** getRaceStages()
     * <p>
     * Takes in raceID and returns an array of stageID from that race.
     * Uses if statement to ensure that raceID exists. If invalid raceID is input, returns out
     * of method with empty array.
     * Creates local raceObject located in race_list at index of the argument raceID.
     * Then iterates through the race_stages.length and appends all to new array.
     * new array is returned
     * </p>
     * @author Jason Swift
     * */
    @Override
    public int[] getRaceStages(int raceID) throws IDNotRecognisedException {
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


    /** removeStageById()
     * <p>
     * Iterated a nested for-loop in search of stageID. If found... removes
     * </p>
     * @author Jason Swift
     * */
    @Override
    public void removeStageById(int stageID) throws IDNotRecognisedException {
        int i,j;
        try{
            for(i = 0; i < Race1.allRaces.size(); i++ ) {
                Race1 raceObject = Race1.allRaces.get(i);
                for (j = 0; j < raceObject.stagesInRace.size(); j++) {
                    if (stageID == raceObject.stagesInRace.get(j).stageID) {
                        raceObject.stagesInRace.remove(j);
                        return;
                    }
                }
            }
        } catch (Exception exc) {
            System.out.println("Stage ID not found: " + exc);
        }
    }


    /**  getStageLength()
     * <p>
     * nested for loop to iterate all races and then stage list until matched a stageID
     * returns the length of that stage ID
     * </p>
     * @author Jason Swift
     * */
    @Override
    public double getStageLength(int stageID) throws IDNotRecognisedException {
        int i,j;
        double length = 0.0;

        try {
            for(i = 0; i < Race1.allRaces.size(); i++) {
                Race1 raceObject = Race1.allRaces.get(i);
                for( j = 0; j < raceObject.stagesInRace.size(); j++ ){
                    if( stageID == raceObject.stagesInRace.get(j).stageID ) {
                        length = raceObject.stagesInRace.get(j).length;
                        break;
                    }
                }
            }
            return length;
        } catch (Exception exc){
            System.out.println("Stage ID not found: " + exc);
            return 0.0;
        }
    }




    /** viewRaceDetails()
     * <p>
     * argument = raceID --> find from race_list
     * sumOfStages gives total length of all stages
     * returns all as a String
     * </p>
     * @author Jason Swift
     * */
    @Override
    public String viewRaceDetails(int raceID) throws IDNotRecognisedException {
        Race1 raceObject;
        try{
            raceObject = getRaceWithID(raceID);
            return "Race ID : " + raceObject.raceID + " | Race name: " + raceObject.raceName + " | Race Description: " + raceObject.raceDescription
                    + " | Number of stages: " + raceObject.stagesInRace.size() + " | Total length: " + raceObject.sumOfstages();
        } catch (Exception exc) {
            return "Invalid raceID: " + exc;
        }
    }


    /** addIntermediateSprintToStage()
     * <p>
     * instantiates segment object and adds to stage object,
     * identified by the stageID argument.
     * returns a segment ID
     * </p>
     * @author Jason Swift
     * */
    @Override
    public int addIntermediateSprintToStage(int stageID, double location) throws IDNotRecognisedException,
            InvalidLocationException, InvalidStageStateException, InvalidStageTypeException {
        try{
            Stage1 stageObject = locateStageObject(stageID);
            Segment1 segObject = new Segment1(location);
            stageObject.stageSegments.add(segObject);

            // creating segID via concatenation to stageIDs
            String segIDString = String.valueOf(stageID) + String.valueOf(stageObject.stageSegments.size());
            int segID = Integer.parseInt(segIDString);
            segObject.segmentID = segID;
            return segID;
        } catch (Exception exc){
            System.out.println("Stage ID error: " + exc);
            return 0;
        }
    }



    /** addCategorizedClimbToStage()
     * <p>
     * instantiates segment object, and sets fields based on
     * arguments. Then appends to the segments of the stage object,
     * as selected by the stageID that was input.
     * returns a segment ID
     * </p>
     * @author Jason Swift
     * */
    @Override
    public int addCategorizedClimbToStage(int stageID, Double location, SegmentType type, Double averageGradient, Double length )
            throws IDNotRecognisedException, InvalidLocationException, InvalidStageStateException,
            InvalidStageTypeException {
        Stage1 stageObject = locateStageObject(stageID);  // grabbing correct stage object
        Segment1 segObject = new Segment1(location);  // instantiating segment instance
        segObject.segType = type;
        segObject.averageGradient = averageGradient;
        segObject.length = length;
        stageObject.stageSegments.add(segObject);  // adding segment to segment list in stage

        // creating segID via concat to stageID
        String segIDString = String.valueOf(stageID) + String.valueOf(stageObject.stageSegments.size());
        int segID = Integer.parseInt(segIDString);
        segObject.segmentID = segID;
        return segID;
    }


    /** removeSegment()
     * <p>
     * removes the segment from the first race, and first stage within the race.
     * Can be altered via hardcoding
     * </p>
     * @author Jason Swift
     * */
    @Override
    public void removeSegment(int segID) throws IDNotRecognisedException {
        int i,j,k;
        for(i = 0; i < Race1.allRaces.size(); i++ ){
            Race1 raceObject = Race1.allRaces.get(i);
            for( j = 0; j < raceObject.stagesInRace.size(); j++ ){
                Stage1 stageObject = raceObject.stagesInRace.get(j);
                for( k = 0; k < stageObject.stageSegments.size(); k++ ){
                    Segment1 segObject = stageObject.stageSegments.get(k);
                    if( segID == segObject.segmentID) {
                        stageObject.stageSegments.remove(k);
                        return;
                    }
                }
            }
        }
        System.out.println("Segment ID not found.");
    }



    /** getStageSegments()
     * <p>
     * grab stage object
     * uses for loop to iterate stage segments, and add to new array of type int[]
     * returns stageSegments[];
     * </p>
     * @author Jason Swift
     * */
    @Override
    public int[] getStageSegments(int stageID) throws IDNotRecognisedException {
        Stage1 stageObject = locateStageObject(stageID);
        int[] stageSegments = new int[stageObject.stageSegments.size()];
        int i;
        for(i = 0; i < stageObject.stageSegments.size(); i++) {
            stageSegments[i] = stageObject.stageSegments.get(i).segmentID;
        }
        return stageSegments;
    }


    /** getNumberOfStages()
     * <p>
     * returns the number of stages for a race
     * </p>
     * @author Jason Swift
     * */
    @Override
    public int getNumberOfStages(int raceID) throws IDNotRecognisedException {
        Race1 raceObject = getRaceWithID(raceID);
        int noOfStages = raceObject.stagesInRace.size();
        return noOfStages;
    }


    /** concludeStagePreparation()
     * <p>
     * I don't understand this method
     * </p>
     * @author Jason Swift
     * */
    @Override
    public void concludeStagePreparation(int stageID) throws IDNotRecognisedException {
        Stage1 stageObject = Race1.allRaces.get(0).stagesInRace.get(stageID);
    }




    /**
     *  TEAMS
     * */




    /** createTeam()
     * <p>
     * takes in name and description, and instantiates team object with arguments.
     * checks for whether team name already exists
     * adds team to the team list
     * returns a team ID
     * </p>
     * @author Jason Swift
     * */
    @Override
    public int createTeam(String teamName, String teamDescription) throws IllegalNameException, InvalidNameException {
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



    /** removeTeam()
     * <p>
     * remove team by ID
     * iterates for loop, comparing Team ID. Removes if equal.
     * If not found, throws error
     * </p>
     * @author Jason Swift
     * */
    @Override
    public void removeTeam(int teamID) throws IDNotRecognisedException {
        int i;
        for(i = 0; i < Team1.team_list.size(); i++ ){
            if( teamID == Team1.team_list.get(i).teamID){
                Team1.team_list.remove(i);
                return;
            } else {
                System.out.println("Team ID not recognised...");
                return;
            }
        }
    }



    /** createRider()
     * <p>
     * instantiates a rider and allocates it to the team based on teamID argument
     * returns a unique riderID via method assignRiderID()
     * </p>
     * @author Jason Swift
     * */
    @Override
    public int createRider(int teamID, String riderName, int yearOfBirth) throws IDNotRecognisedException, IllegalArgumentException {

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


    /** removeRider()
     * <p>
     * iterate all riderIDs in all teams.
     * returns the rider object to be removed
     * </p>
     * @author Jason Swift
     * */
    @Override
    public void removeRider(int riderID) throws IDNotRecognisedException {
        int i;
        int j;
        for(i = 0; i < Team1.team_list.size(); i++) {  // iterate team_list
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


    /** getTeams()
     * <p>
     * extracts teamID's from the team objects in the teams list
     * </p>
     * @author Jason Swift
     * */
    @Override
    public int[] getTeams()  {
        int i;
        int[] teamIDArray = new int[Team1.team_list.size()];
        for(i = 0; i < Team1.team_list.size(); i++ ) {
            int teamID = Team1.team_list.get(i).teamID;
            teamIDArray[i] = teamID;
        }
        return teamIDArray;
    }


    /** getTeamRiders()
     * <p>
     * iterate through team members array of a given teamID
     * return riders of the team
     * </p>
     * @author Jason Swift
     */
    @Override
    public int[] getTeamRiders(int teamID) throws IDNotRecognisedException {
        int i;
        Team1 teamObject = getObjectWithTeamID(teamID);
        int[] teamRiderIDs = new int[teamObject.teamMembers.size()];
        for( i = 0; i < teamRiderIDs.length; i++ ){
            teamRiderIDs[i] = teamObject.teamMembers.get(i).riderID;
        }
        return teamRiderIDs;
    }



    /** RESULT RECORDING */

    /** registerRiderResultsInStage()
     * <p>
     * params: riderID, stageID, LocalTime array of times
     * HashMap --> RiderID : Time to complete stage (seconds)
     * </p>
     * @author Jason Swift
     * */
    @Override
    public void registerRiderResultsInStage(int stageID, int riderID, LocalTime[] checkpoints) throws IDNotRecognisedException, DuplicatedResultException, InvalidCheckpointsException,
            InvalidStageStateException {
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


    /** getRiderResultsInStage()
     * <p>
     * returns the LocalTime[] checkpoint times of the rider in that stage
     * </p>
     * @author Jason Swift
     * */
    @Override
    public LocalTime[] getRiderResultsInStage(int stageID, int riderID) throws IDNotRecognisedException {
        Stage1 stageObject = locateStageObject(stageID);
        if( stageObject.riderTime.get(riderID) == null ){
            return new LocalTime[0];
        }
        return stageObject.riderTime.get(riderID);
    }

    /** getRidersGeneralClassificationRank()
     * <p>
     * returns the LocalTime[] checkpoint times of the rider in that stage
     * </p>
     * @author Jason Swift
     * */
    public LocalTime[] getRidersGeneralClassificationRank(int stageID, int riderID) throws IDNotRecognisedException {
        Stage1 stageObject = locateStageObject(stageID);
        if( stageObject.riderTime.get(riderID) == null ){
            return new LocalTime[0];
        }
        return stageObject.riderTime.get(riderID);
    }


    /** getRiderAdjustedElapsedTime()
     * <p>
     * converts second into seconds of the day
     * </p>
     * @author Jason Swift
     * */
    @Override
    public LocalTime getRiderAdjustedElapsedTimeInStage(int stageID, int riderID) throws IDNotRecognisedException {
        Stage1 stageObject = locateStageObject(stageID);
        LocalTime[] riderTimes = stageObject.riderTime.get(riderID);
        Long seconds = ChronoUnit.SECONDS.between(riderTimes[0], riderTimes[riderTimes.length-1]);
        return LocalTime.ofSecondOfDay(seconds);
    }


    /** deleteRiderResultsInStage()
     * <p>
     * delete rider in stage HashMap
     * </p>
     * @author Jason Swift
     * */
    @Override
    public void deleteRiderResultsInStage(int stageID, int riderID) throws IDNotRecognisedException {
        Stage1 stageObject = locateStageObject(stageID);
        stageObject.riderTime.remove(riderID);
    }


    /** getRidersRankInStage()
     * <p>
     * getRidersRanks
     * </p>
     * @author Jason Swift
     * */
    @Override
    public int[] getRidersRankInStage(int stageID) throws IDNotRecognisedException {
        Stage1 stageObject = locateStageObject(stageID);
        int[] riderResulstsArray = new int[200];
        int i;
        for( i = 0; i < stageObject.riderResults.size(); i++ ){
            riderResulstsArray[i] = stageObject.riderResults.get(i);
        }
        return riderResulstsArray;
    }


    /** getRankedAdjustedElapsedTimeInStage()
     * @author Jason Swift
     * */
    @Override
    public int[] getRankedAdjustedElapsedTimesInStage(int stageID) throws IDNotRecognisedException {
        Stage1 stageObject = locateStageObject(stageID);
        int[] riderTimes = new int[stageObject.rankedRiderTimes.size()];
        int i;
        for( i = 0; i < stageObject.rankedRiderTimes.size(); i++ ) {
            riderTimes[i] = stageObject.rankedRiderTimes.get(i);
        }
        return riderTimes;
    }


    /** getRidersPointsInStage()
     * <p>
     * Allocate rider points based on their position in the rankedRiderTimes array.
     * Check stageType and position --> Then allocate required points
     * return the list
     * </p>
     * @author Jason Swift
     * */
    @Override
    public int[] getRidersPointsInStage(int stageID) throws IDNotRecognisedException {
        Stage1 stageObject = locateStageObject(stageID);
        int[] riderPoints = new int[stageObject.rankedRiderTimes.size()];
        int i;
        for( i = 0; i < stageObject.rankedRiderTimes.indexOf(14); i++){
            riderPoints[i] = stageObject.rankedRiderTimes.get(i);
            return riderPoints;
        }
        return new int[0];
    }


    /** getRidersMountainPointsInStage()
     * <p>
     * get mountain points
     * </p>
     * @author Jason Swift
     * */
    @Override
    public int[] getRidersMountainPointsInStage(int stageID) throws IDNotRecognisedException {
        Stage1 stageObject = locateStageObject(stageID);
        int[] riderMountainPoints = new int[stageObject.rankedRiderTimes.size()];
        int i;
        for( i = 0; i < stageObject.rankedRiderTimes.indexOf(14); i++){
            riderMountainPoints[i] = stageObject.rankedRiderTimes.get(i);
            return riderMountainPoints;
        }
        return new int[0];
    }


    /** eraseCyclingPortal()
     * <p>
     * Clears everything
     * </p>
     * @author Jason Swift
     * */
    @Override
    public void eraseCyclingPortal(){
        int i, j;
        for(i = 0; i < Race1.allRaces.size(); i++ ){
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


    /** saveCyclingPortal()
     * @author Jason Swift
     * */
    @Override
    public void saveCyclingPortal(String filename) throws IOException {

        FileOutputStream fileout = new FileOutputStream("cycling/RaceData.ser");
        ObjectOutputStream out = new ObjectOutputStream(fileout);
        out.writeObject(Race1.allRaces);
    }


    /** loadCyclingPortal()
     * @author Jason Swift
     * */
    @Override
    public void loadCyclingPortal(String filename) throws IOException {
        Race1 race = null;
        FileInputStream filein = new FileInputStream("/Users/JDSwift/Desktop/Assignment/src/RaceData.ser");
        ObjectInputStream in = new ObjectInputStream(filein);
    }







    /** EXTRA METHODS */


    /** assignRiderID()
     * <p>
     * get teamObject from teamID --> Assign teamID as first element of riderID
     * get riderID from teamMembers ArrayList.size() --> Assign string int as second element.
     * cast back to int in return. This bypasses arithematic operators to ensure concatenation, not addition.
     * </p>
     * @author Jason Swift
     * */
    public int assignRiderID(int teamID){
        Team1 teamObject = getObjectWithTeamID(teamID);
        String riderID = Integer.toString(teamObject.teamMembers.size());
        return Integer.parseInt( teamID + riderID);
    }


    /** checkForName()
     * <p>
     * Boolean method
     * iterated static team list, comparing team names with argument
     * </p>
     * @author Jason Swift
     * */
    boolean checkForName(String teamName){
        int i;
        for(i = 0; i < Team1.team_list.size(); i++ ){
            if(teamName == Team1.team_list.get(i).teamName){
                System.out.println("Name already used...: " + teamName);
                return true;
            }
        }
        return false;
    }


    /** getObjectWithTeamID
     * <p>
     * param: teamID
     * returns the team object that has the ID that matches with the argument.
     * </p>
     * @author Jason Swift
     * */
    public Team1 getObjectWithTeamID(int teamID){
        int i;
        for(i = 0; i < Team1.team_list.size(); i++ ){
            if( teamID == Team1.team_list.get(i).teamID){
                return Team1.team_list.get(i);
            }
        }
        return null;
    }

    /** getRaceWithID()
     * <p>
     * returns race object that has a raceID that matches
     * the argument
     * </p>
     * @author Jason Swift
     * */
    public Race1 getRaceWithID(int raceID) {
        int i;
        for(i = 0; i < Race1.allRaces.size(); i++ ){
            if( raceID == Race1.allRaces.get(i).raceID ){
                return Race1.allRaces.get(i);
            }
        }
        return null;
    }

    /** locateStageObject()
     * <p>
     * nested for loop to locate the stageID that
     * matches the argument
     * </p>
     * @author Jason Swift
     * */
    public Stage1 locateStageObject(int stageID ) {
        int i,j;
        for(i = 0; i < Race1.allRaces.size(); i++ ){
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


    /** findRider()
     * <p>
     * param: riderID
     * compares all rider IDs and returns
     * the object with the matched riderID
     * </p>
     * @author Jason Swift
     * */
    public Rider1 findRider(int riderID) {
        int i,j;
        for(i = 0; i < Team1.team_list.size(); i++ ){
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
