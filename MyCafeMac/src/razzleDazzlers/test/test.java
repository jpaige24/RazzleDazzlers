package razzleDazzlers.test;

import static org.junit.Assert.*;

import org.junit.Test;

import razzleDazzlers.util.Server;

public class test {

	@Test
	public void testGetAvgDishRating() {
		Server tester = new Server();
		String name = "Something not tasty";
		
		
		String date = "11132012";
		
		
		String userID1 = "000000000";
		String userID2 = "000000001";
		
		tester.insert(userID1, date, name, (float) 3.5);
		tester.insert(userID2, date, name, (float) 3.5);
		assertEquals("Result", tester.getAvgDishRating(name, date));
		fail("Not yet implemented");
	}

}
