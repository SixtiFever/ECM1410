import cycling.StageType;

import java.time.LocalDateTime;
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

    HashMap<Integer, Long> riderTime = new HashMap<>();

    Stage1(String stageName, String stageDescription, StageType type){
        this.stageName = stageName;
        this.stageDescription = stageDescription;
        this.typeOfStage = type;
    }



}
