import java.util.ArrayList;
import java.util.Arrays;

public class MovieInput {

	public static void main(String [] args) {


		Integer layout[][]= new Integer[][] {
			{6,6},
			{3,5,5,3},
			{4,6,6,4},
			{2,8,8,2},
			{6,6}, 	
		};

		ArrayList<String> places = new ArrayList<String>
		(Arrays.asList("Smith 2", "Jones 5", "Davis 6","Willson 100","Johnson 3"
				,"William 4","Brown 8","Miller 12 "));

		//print movie theater tickets
		
		new AllotTickets().allocateMovieTickets(layout,places); 

	}



}
