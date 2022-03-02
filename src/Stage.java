import cycling.StageType;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Stage extends Race {

    StageType stage_type;
    String stage_name;
    String stage_description;
    double length;
    LocalDateTime start_time;
    public int stageID; // Every stage crated will assign int of size of stages array. i.e 0 --> X

    ArrayList<Segment> segments = new ArrayList<>();

    /**
     * CONSTRUCTOR
     */
    Stage(String race_name, String race_description, String stage_name,
          String stage_description, double length, LocalDateTime start_time, StageType type ) {
        super(race_name, race_description);
        this.stage_name = stage_name;
        this.stage_description = stage_description;
        this.length = length;
        this.start_time = start_time;
        this.stage_type = type;
        this.stageID = super.stages.size();
    }


    public Stage() {

    }
}
