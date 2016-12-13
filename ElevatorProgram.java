
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class ElevatorProgram {
    public static void main(String[] args) throws InterruptedException {
   

        int lenSim = 0;
        int rateSimTime = 1;
        List<List<PassengerArrival>> behaviorsOfFloors = new ArrayList();

        try {
        	
            BufferedReader br = new BufferedReader(new FileReader("/ElevatorProgram/ElevatorConfig.txt"));
            try {
                String line = br.readLine();
                lenSim = Integer.parseInt(line);
                line = br.readLine();
                rateSimTime = Integer.parseInt(line);
                
                line = br.readLine();
                while (line != null) {
                    String[] groups = line.split(";");
                    List<PassengerArrival> behaviors = new ArrayList();
                    for (String group : groups) {
                        if (!group.isEmpty()) {
                            String[] vals = group.trim().split(" ");
                            PassengerArrival pa = new PassengerArrival(
                                    Integer.parseInt(vals[0]),
                                    Integer.parseInt(vals[1]),
                                    Integer.parseInt(vals[2]),
                                    Integer.parseInt(vals[2])
                            );
                            behaviors.add(pa);
                        }
                    }
                    behaviorsOfFloors.add(behaviors);
                    line = br.readLine();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                br.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        ElevatorSimulation simulation = new ElevatorSimulation(lenSim, rateSimTime, behaviorsOfFloors);
        simulation.start();
        return;
    }
}
