import cycling.BadCyclingPortal;
import cycling.BadMiniCyclingPortal;
import cycling.CyclingPortalInterface;
import cycling.MiniCyclingPortalInterface;

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
	public static void main(String[] args) {
		System.out.println("The system compiled and started the execution...");



		MiniCyclingPortalInterface portal = new BadMiniCyclingPortal();
//		CyclingPortalInterface portal = new BadCyclingPortal();

		// instantiating team objects
		Team Flying_People = new Team();
		Team TeamB = new Team();
		Team TeamC = new Team();


		// creating team object data and appending to teams ArrayList
		Flying_People.createTeam("Flying People","We are a team");
		TeamB.createTeam("Team B", "We are team B");
		TeamC.createTeam("Team C", "We are team C");

		Flying_People.createRider("Jason", 1994);
		Flying_People.createRider("Alice", 2001);

		TeamB.createRider("Nat", 1999);
		TeamB.createRider("Rosie", 2003);

		System.out.println(Team.teams_objects);
		// System.out.println(Team.teams);
		System.out.println(Team.team_id);
		System.out.println(Flying_People.team_riders.get(0).team_id);
		System.out.println(TeamB.team_riders.get(1).RIDER_ID);
		System.out.println();
		System.out.println(Team.teams_objects);
		Team.removeTeam(1);
		System.out.println(Team.teams_objects);








		assert (portal.getRaceIds().length == 0)
				: "Initial SocialMediaPlatform not empty as required or not returning an empty array.";

	}
}
