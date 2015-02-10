import java.util.Random;

public class LotteryCalc implements Runnable {

	private Thread t;
	static Random rand = new Random();
	private String threadName;
	public static int numTickets = 0;
	private static boolean stop = false;
	public static int total = 0;
	public static int[] numberOfMatches = {0,0,0,0,0,0};
	
	public LotteryCalc(String name) {
		threadName = name;
		System.out.println("Creating " + threadName);
	}
	
	public void setWinning(int[] tempWin) {
		numberOfMatches = tempWin;
	}
	public int getTotal() {
		return total;
	}
	public int getNumTickets() {
		return numTickets;
	}
	public boolean isStopped() {
		if (stop) {
			return true;
		}
		else {
			return false;
		}
	}

	public void stop() {
		stop = true;
	}
	public boolean match(int[] winner, int[] ticket) {
		boolean tempBool = true;
		int counter = 0;
		for (int i = 0; i < winner.length; i++) {
			if (winner[i] != ticket[i]) {
				tempBool = false;
			}
			else {
				counter++;
			}
		}
		if (counter == 6) {
			total += 1000000000;
		}
		if (counter == 5 && ticket[5] != winner[5]) {
			total += 1000000;
		}
		else if (counter == 4 && ticket[5] == winner[5]) {
			total += 10000;
		}
		else if ((counter == 4 && ticket[5] != winner[5]) || (counter == 3 && ticket[5] == winner[5])) {
			total += 100;
		}
		else if ((counter == 3 && ticket[5] != winner[5]) || (counter == 2 && ticket[5] == winner[5])) {
			total += 7;
		}
		else if ((counter == 1 && ticket[5] == winner[5]) || (counter == 0 && ticket[5] == winner[5])) {
			total += 4;
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
				numTickets++;
				ticket = generateTicket();
				Thread.sleep(100);
				stop = match(lottery, ticket);
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

