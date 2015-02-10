import java.util.Random;

public class Lottery{
	static Random rand = new Random();
	public static int[] numberOfMatches = {0,0,0,0,0,0};
	public static String winner = "";
	private static boolean stop = false;
	
	public static void main(String[] args) {
		  LotteryCalc R1 = new LotteryCalc( "Thread-1");
		  numberOfMatches = R1.generateTicket();
	      R1.start();
	   
	      LotteryCalc R2 = new LotteryCalc( "Thread-2");
	      R2.start();
		
	      LotteryCalc R3 = new LotteryCalc( "Thread-3");
	      R3.start();
	      
	      LotteryCalc R4 = new LotteryCalc( "Thread-4");
	      R4.start();
		
	      if (stop) {
	    	  System.out.println(winner + "here");
	    	  R1.stop();
	    	  R2.stop();
	    	  R3.stop();
	    	  R4.stop();
	      }
	     
		
	}

	public static class LotteryCalc implements Runnable {

		private Thread t;
		private String threadName;
		public LotteryCalc(String name) {
			threadName = name;
			System.out.println("Creating " + threadName);
		}

		public void stop() {
			stop = true;
		}
		public boolean match(int[] winner, int[] ticket) {
			boolean tempBool = true;
			for (int i = 0; i < winner.length; i++) {
				if (winner[i] != ticket[i]) {
					tempBool = false;
				}
			}
			return tempBool;
		}

		public int[] sort(int[] toSort) {
			int[] tempSort = new int[6];
			tempSort = toSort;
			try {
				if (tempSort.length != 1) {
					for (int i = 0; i < tempSort.length - 1; i++) {
						int index = i;
						for (int j = i + 1; j < tempSort.length; j++)
							if (tempSort[j] <= tempSort[index])
								index = j;

						int smallerJ = tempSort[index];
						tempSort[index] = tempSort[i];
						tempSort[i] = smallerJ;
					}
				}
			} catch (Exception ie) {
				System.out.println("here");
			}
			return tempSort;
		}

		public int[] generateTicket() {
			int[] ticket = new int[6];
			for (int i = 0; i < ticket.length - 1; i++) {
				ticket[i] = generateRandom(59) + 1;
				for (int x = 0; x < i; x++) {
					if (ticket[x] == ticket[i]) {
						ticket[i] = generateRandom(60) + 1;
						x = 0;
					}
				}
			}
			ticket[5] = 100;
			ticket = sort(ticket);
			ticket[5] = generateRandom(40);
			return ticket;
		}

		public int generateRandom(int size) {
			int tempOutPut = 0;
			tempOutPut = rand.nextInt(size);
			return tempOutPut;
		}

		@Override
		public void run() {
			System.out.println("Running " + threadName);
			int[] lottery = new int[6];
			lottery = numberOfMatches;
			try {
				int[] ticket = new int[6];
				ticket = generateTicket();
				stop = match(lottery, ticket);
				while (!stop) {
					System.out.println("not a winner" + " " + threadName);
					ticket = generateTicket();
					Thread.sleep(5);
				}
				stop = true;
			} catch (Exception e) {
				System.err.println(e);
			}
			System.out.println("Thread " + threadName + " exiting.");
		}

		public void start() {
			System.out.println("Starting " + threadName);
			if (t == null) {
				t = new Thread(this, threadName);
				t.start();
			}
		}
		
	}
}
