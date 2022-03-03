import java.util.ArrayList;

public class Rider1 {

    int yearOfBirth;
    String riderName;
    int riderID;

    Rider1(String riderName, int yearOfBirth){
        this.riderName = riderName;
        this.yearOfBirth = yearOfBirth;
    }


    public static void addRiderToTeam(int teamID, Rider1 rider) {
        int i;
        for( i = 0; i < Team1.team_list.size(); i ++) {
            if(teamID == Team1.team_list.get(i).teamID){
                Team1 teamObject = Team1.team_list.get(i);
                teamObject.teamMembers.add(rider);
                break;
            }
            if (i > Team1.team_list.size()){
                System.out.println("Team ID not found in team list.");
                return;
            }
        }
        return;
    }


    /**
     * if teamID matches value of teamID in teams_list --> return true. else false;
     * */
    public static boolean checkForTeamID(int teamID){
        int i;
        for( i = 0; i < Team1.team_list.size(); i++ ){
            if(Team1.team_list.contains(Team1.team_list.get(i))){  // if team ID already in use
                Team1 teamObject = Team1.team_list.get(i);
                if( teamObject.teamID == teamID ){
                    return true;
                }
            }
        }
        return false;
    }




}
