package razzleDazzlers.test;

import static org.junit.Assert.*;

import org.junit.Test;

import razzleDazzlers.util.Server;

public class test {
	
	private static final double DELTA = 1e-15;
	Server tester = new Server();
	String name1 = "Dish1";
	String name2 = "Dish2";
	String date1 = "11/13/2012";
	String date2 = "11/14/2012";
	String userID1 = "000000000";
	String userID2 = "000000001";
	
	@Test
	public void testInsert() {
		
		/*testing insert*/
		if(!tester.check(userID1, date1, name1)){
			tester.insert(userID1, date1, name1, (float) 3.5);			
		}else{
			System.out.println("Instance " + userID1 + " " + date1 + " " + name1 + "exists already");
		}
		
		if(!tester.check(userID2, date1, name1)){
			tester.insert(userID2, date1, name1, (float) 4.5);			
		}else{
			System.out.println("Instance " + userID2 + " " + date1 + " " + name1 + "exists already");
		}
		
		if(!tester.check(userID1, date1, name2)){
			tester.insert(userID1, date1, name2, (float) 4.0);			
		}else{
			System.out.println("Instance " + userID1 + " " + date1 + " " + name2 + "exists already");
		}
		
		if(!tester.check(userID2, date2, name1)){
			tester.insert(userID2, date2, name1, (float) 4.0);			
		}else{
			System.out.println("Instance " + userID2 + " " + date2 + " " + name1 + "exists already");
		}
		
		if(!tester.check(userID1, date2, name2)){
			tester.insert(userID1, date2, name2, (float) 3.5);			
		}else{
			System.out.println("Instance " + userID1 + " " + date2 + " " + name2 + "exists already");
		}
		
		if(!tester.check(userID2, date2, name2)){
			tester.insert(userID2, date2, name2, (float) 4.5);			
		}else{
			System.out.println("Instance " + userID2 + " " + date2 + " " + name2 + "exists already");
		}
		
	}

	@Test
	public void testGetAvgDishRating() {

		/*testing getAvgDishRating*/
		assertEquals((float) 4, tester.getAvgDishRating(name1, date1), DELTA);
		assertEquals((float) 4, tester.getAvgDishRating(name1, date2), DELTA);
				
	}
	
	@Test
	public void testUserDishRating() {
		
		/*testing getUserDishRating*/
		assertEquals((float) 3.5, tester.getUserDishRating(name1, date1, userID1), DELTA);
		assertEquals((float) 4.0, tester.getUserDishRating(name2, date1, userID1), DELTA);
		
		
	}
	
	@Test
	public void testDayRating() {
		
		/*testing getDayRating*/
		assertEquals((float) 4, tester.getDayRating(date1), DELTA);
		assertEquals((float) 4, tester.getDayRating(date2), DELTA);	
		
	}
	
	@Test
	public void testUpdate() {
		
		/*testing update*/
		tester.update(userID1, date1, name1, (float) 4.0);
		tester.update(userID2, date1, name1, (float) 4.0);
		assertEquals((float) 4.0, tester.getDayRating(date1), DELTA);
		
		// now update the ratings back:
		tester.update(userID1, date1, name1, (float) 3.5);
		tester.update(userID2, date1, name1, (float) 4.5);
	}
	
	@Test
	public void testCheck() {
		
		/*testing Check*/
		assertEquals(true,tester.check(userID1, date1, name1));
		assertEquals(true,tester.check(userID2, date1, name1));
		assertEquals(false,tester.check(userID1, date2, name1));
		
	}

}

