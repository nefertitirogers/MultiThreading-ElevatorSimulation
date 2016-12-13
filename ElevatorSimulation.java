
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ElevatorSimulation {

	int lenSim;
	int rateSimTime;
	List<List<PassengerArrival>> behaviorsOfFloors;

	public ElevatorSimulation(int lenSim, int rateSimTime, List<List<PassengerArrival>> behaviorsOfFloors) {
		this.lenSim = lenSim;
		this.rateSimTime = rateSimTime;
		this.behaviorsOfFloors = behaviorsOfFloors;
	}

	public void start() throws InterruptedException {
		// Initializes clock
		new SimClock();

		BuildingManager bm = new BuildingManager();
		Thread[] threads = new Thread[5];
		Elevator[] elevators = new Elevator[5];
		for (int i = 0; i < 5; i++) {
			Elevator elevator = new Elevator(i, bm);
			threads[i] = new Thread(elevator);
			threads[i].start();
			elevators[i] = elevator;
		}
		int currentTime = SimClock.getTime();
		while(currentTime < lenSim) {
			// Counting people requesting
			for (int i = 0; i< behaviorsOfFloors.size(); i++) {
				List<PassengerArrival> behaviors = behaviorsOfFloors.get(i);
				for (PassengerArrival pa:behaviors) {
					if (pa.getExpectedTimeOfArrival() == currentTime) {
						bm.increasePassengersRequests(i, pa.getDestinationFloor(), pa.getNumPassengers());
                        System.out.println(String.format("At %d, %d passengers enter floor %d, request to floor %d",
								currentTime,
								pa.getNumPassengers(),
								i,
								pa.getDestinationFloor()
								));
						pa.setExpectedTimeOfArrival(currentTime + pa.getTimePeriod());
					}
				}
			}

			TimeUnit.MILLISECONDS.sleep(rateSimTime);
			SimClock.tick();
			currentTime = SimClock.getTime();
		}
		// stop threads and print
		for (int i = 0; i < 5; i++) {
			threads[i].interrupt();
		}
		// print stats until all threads are killed
		TimeUnit.SECONDS.sleep(1);
		for (int i = 0; i < 5; i++) {
			System.out.println(String.format("For floor %d, total passengers requested to different floors are %s", i, Arrays.toString(bm.getTotalPassengersRequests(i))));
			System.out.println(String.format("For floor %d, total passengers arrived from different floors are %s", i, Arrays.toString(bm.getTotalArrivedPassengers(i))));
		}
		for (int i = 0; i < 5; i++) {
			System.out.println(String.format(
					"For elevator %d, total loaded passengers: %d, total unloaded passengers: %d",
					i,
					elevators[i].getTotalLoadedPassengers(),
					elevators[i].getTotalUnloadedPassengers()
			));
		}
	}
}
