import java.util.*;

public class Lottery{
	static Random rand = new Random();
	public static int[] numberOfMatches = {0,0,0,0,0,0};
	public static int numTickets = 0;
	private static boolean stop = false;
	public static int total = 0;
	public static ArrayList<LotteryCalc> collection = new ArrayList<LotteryCalc>();
	
	public static void main(String[] args) {
		  LotteryCalc R1 = new LotteryCalc( "Thread-1");
		  numberOfMatches = R1.generateTicket();
		  R1.setWinning(numberOfMatches);
	      R1.start();
	      collection.add(R1);
	      
	      LotteryCalc R2 = new LotteryCalc( "Thread-2");
	      R2.start();
	      collection.add(R2);
	      
	      LotteryCalc R3 = new LotteryCalc( "Thread-3");
	      R3.start();
	      collection.add(R3);
	      
	      LotteryCalc R4 = new LotteryCalc( "Thread-4");
	      R4.start();
	      collection.add(R4);
	      
	      LotteryCalc R5 = new LotteryCalc( "Thread-5");
	      R5.start();
	      collection.add(R5);
	      
	      LotteryCalc R6 = new LotteryCalc( "Thread-6");
	      R6.start();
	      collection.add(R6);
	      
	      while (!R1.isStopped() && !R2.isStopped() && !R3.isStopped() && !R4.isStopped() && !R5.isStopped() && !R6.isStopped()) {
	    	  total = 0;
	    	  numTickets = 0;
	    	  for (int i = 0; i < collection.size(); i++) {
	    		  total = total + collection.get(i).getTotal();
	    		  numTickets += collection.get(i).getNumTickets();
	    	  }
	    	  try {
	    	  Thread.sleep(100);
	    	  } catch (Exception ie) {}
	    	  System.out.println("Not a winner, total: " + total + " number of tickets: " + numTickets);
	      }
	    	  R2.stop();
	    	  R3.stop();
	    	  R4.stop();
	    	  R5.stop();
	    	  R6.stop();

	     
		
	}
}
