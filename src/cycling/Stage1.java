package cycling;

import cycling.Segment1;
import cycling.StageType;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

public class Stage1 {

    int stageID;
    String stageName;
    String stageDescription;
    double length;
    LocalDateTime startTime;
    StageType typeOfStage;

    ArrayList<Segment1> stageSegments = new ArrayList<>();
    ArrayList<Integer> riderResults = new ArrayList<>();
    ArrayList<Integer> rankedRiderTimes = new ArrayList<>();

    HashMap<Integer, LocalTime[]> riderTime = new HashMap<>();

    Stage1(String stageName, String stageDescription, StageType type){
        this.stageName = stageName;
        this.stageDescription = stageDescription;
        this.typeOfStage = type;
    }



}
