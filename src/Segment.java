import cycling.SegmentType;

    public class Segment extends Stage{

        int stageID;
        SegmentType segType;
        double location;
        double averageGradient;
        double length;

        Segment(int stageID, SegmentType segType, double location){
            super();
            this.stageID = stageID;
            this.segType = segType;
            this.location = location;
        }

        Segment(){

        }



}
