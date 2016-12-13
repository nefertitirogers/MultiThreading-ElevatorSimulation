
public class ElevatorEvent {
	private int destination;
	private int expectedArrival;
	
	public ElevatorEvent() { 
		this.destination = 0;
		this.expectedArrival = 0; 
	}
	public ElevatorEvent(int destination, int expectedArrival) {
		this.destination = destination;
		this.expectedArrival = expectedArrival; 
	}
	public int getDestination() { return destination;}
	public int getExpectedArrival() { return expectedArrival;
	}
}
