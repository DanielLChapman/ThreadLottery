import java.util.Random;

public class LotteryCalc implements Runnable {
	private Thread t;
	private String threadName;
	static Random rand = new Random();

	public LotteryCalc(String name) {
		threadName = name;
		System.out.println("Creating " + threadName);
	}

	public static boolean match(int[] winner, int[] ticket) {
		boolean tempBool = true;
		for (int i = 0; i < winner.length; i++) {
			if (winner[i] != ticket[i]) {
				tempBool = false;
			}
		}
		return tempBool;
	}

	public static int[] sort(int[] toSort) {
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

	public static int[] generateTicket() {
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

	public static int generateRandom(int size) {
		int tempOutPut = 0;
		tempOutPut = rand.nextInt(size);
		return tempOutPut;
	}

	@Override
	public void run() {
		System.out.println("Running " + threadName);
		int[] lottery = new int[6];
		lottery = generateTicket();
		try {
			int[] ticket = new int[6];
			ticket = generateTicket();
			while (!match(lottery, ticket)) {
				System.out.println("not a winner" + " " + threadName);
				ticket = generateTicket();
				Thread.sleep(5);
			}
			System.out.println("winner " + threadName);
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
