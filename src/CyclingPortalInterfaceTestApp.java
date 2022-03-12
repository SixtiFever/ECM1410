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
	public static void main(String[] args) throws InvalidNameException, IllegalNameException, IDNotRecognisedException, InvalidLengthException, InvalidStageStateException, InvalidLocationException, InvalidStageTypeException, DuplicatedResultException, InvalidCheckpointsException {





		MiniCyclingPortalInterface portal = new BadMiniCyclingPortal();
		// CyclingPortalInterface portal = new BadCyclingPortal();

		// CyclingPortalInterfaceTestApp test = new CyclingPortalInterfaceTestApp();

		CyclingPortal test = new CyclingPortal();

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






}
