import cycling.StageType;

public class Stage extends Race {

    StageType stage_type;
    int stageID = super.stages.size(); // Every stage crated will assign int of size of stages array. i.e 0 --> X

    /**
     * CONSTRUCTOR
     */
    Stage(StageType stage_type, String race_name, String race_description) {
        super(race_name, race_description);
        this.stage_type = stage_type;
    }


}
