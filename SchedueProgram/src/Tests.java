import static org.junit.Assert.*;

import org.junit.Test;

public class Tests {

	@Test
	public void BasicTest1() {
		Schedule test = new Schedule();
		test.insert(0);
		test.insert(1);
		test.insert(2);
		test.insert(3);
		test.insert(4);
		test.insert(5);
		test.insert(6);
		
		test.get(1).requires(test.get(0));
		test.get(2).requires(test.get(1));
		test.get(2).requires(test.get(6));
		test.get(3).requires(test.get(2));
		test.get(3).requires(test.get(5));
		test.get(4).requires(test.get(3));
		test.get(6).requires(test.get(1));
		
		assertEquals(16, test.finish());
	}
	
	@Test
	public void BasicTest2() {
		Schedule schedule = new Schedule();
		schedule.insert(8); 					   //adds job 0 with time 8
		Schedule.Job j1 = schedule.insert(3); 	   //adds job 1 with time 3
		schedule.insert(5); 					   //adds job 2 with time 5
		assertEquals(schedule.finish(), 8); 	   //should return 8, since job 0 takes time 8 to complete.
		/* Note it is not the earliest completion time of any job, but the earliest the entire set can complete. */
		schedule.get(0).requires(schedule.get(2)); //job 2 must precede job 0
		schedule.finish(); 						   //should return 13 (job 0 cannot start until time 5)
		schedule.get(0).requires(j1); 			   //job 1 must precede job 0
		schedule.finish(); 						   //should return 13
		assertEquals(schedule.get(0).start(), 5);  //should return 5
		assertEquals(j1.start(), 0); 			   //should return 0
		schedule.get(2).start(); 				   //should return 0
		j1.requires(schedule.get(2));              //job 2 must precede job 1
		assertEquals(schedule.finish(), 16);       //should return 16
		assertEquals(schedule.get(0).start(), 8);  //should return 8
		assertEquals(schedule.get(1).start(), 5);  //should return 5
		assertEquals(schedule.get(2).start(), 0);  //should return 0
		schedule.get(1).requires(schedule.get(0)); //job 0 must precede job 1 (creates loop)
		assertEquals(schedule.finish(), -1); 	   //should return -1
		assertEquals(schedule.get(0).start(), -1); //should return -1
		assertEquals(schedule.get(1).start(), -1); //should return -1
		assertEquals(schedule.get(2).start(), 0);  //should return 0
	}
	
	@Test
	public void wholeGraphIsCycle() {
		Schedule schedule = new Schedule();
		schedule.insert(1);
		schedule.insert(2).requires(schedule.get(0));
		schedule.insert(3).requires(schedule.get(1));
		schedule.insert(4).requires(schedule.get(2));
		schedule.get(0).requires(schedule.get(3));
		assertEquals(-1, schedule.get(0).start());
		assertEquals(-1, schedule.get(1).start());
		assertEquals(-1, schedule.get(2).start());
		assertEquals(-1, schedule.get(3).start());
		assertEquals(-1, schedule.finish());
		
	}
	
	@Test
	public void EfficiencyTest10000() {
		int testSize = 10000;
		Schedule schedule = new Schedule();
		schedule.insert(0);
		for (int i = 1; i<testSize; i++) {
			schedule.insert(i).requires(schedule.get(i-1));
		}
		int startTime = 0;
		for (int i = 0; i<testSize; i++) {
			assertEquals(startTime, schedule.get(i).start());
			startTime+=i;
		}
	}
	
	/*
	@Test
	public void EfficiencyTest10000000() {
		int testSize = 10000000;
		Schedule schedule = new Schedule();
		schedule.insert(1);
		for (int i = 1; i<testSize; i++) {
			schedule.insert(1).requires(schedule.get(i-1));
		}
		int startTime = 0;
		for (int i = 0; i<testSize; i++) {
			assertEquals(startTime, schedule.get(i).start());
			startTime++;
		}
	}
	*/
}
