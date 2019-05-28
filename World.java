/**
 * World class for wrapping all the elements in the experiment.
 *
 * @author Shenglan Yu<shenglany1@student.unimelb.edu.au> - 808600
 * @author Haohua Wu<haohuaw@student.unimelb.edu.au> - 927081
 *
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class World {
	// hash map for all the patchs
	private HashMap<Coordinate, Patch> patchMap;
	// csv handler for writing records
	private CsvHandler csv;
	
	private double globalTemp;
	private int whitePop;
	private int blackPop;
	private int redPop;
	private int totalPop;
	private int xSize;
	private int ySize;
	private int worldSize;
	private Scenario scenario;
	private int tick;
	// random generator with seed
	Random rand;
	
	public World(CsvHandler csv) {
		patchMap = new HashMap<Coordinate, Patch>();
		this.csv = csv;
		this.tick = 0;
		this.rand = new Random(1234567);
		setupWorld();
	}
	
	/**
	 * Initialize the world
	 */
	public void setupWorld() {
		xSize = Parameters.WORLD_SIZE_X;
		ySize = Parameters.WORLD_SIZE_Y;
		worldSize = xSize * ySize;
		globalTemp = 0;
		scenario = Parameters.SCENARIO;
		
		// initialize all patches
		for (int i = 0; i < xSize; i++) {
			for (int j = 0; j < ySize; j++) {
				patchMap.put(new Coordinate(i, j),  new Patch());
			}
		}
		
		// initialize all daisyes
		setupDaisy();
		
		// initialize all patches' temperature and global temperature
		double totalTemp = 0;
		for (Coordinate coor : patchMap.keySet()) {
			patchMap.get(coor).updateLocalTemp();
			totalTemp += patchMap.get(coor).getTemp();
		}
		globalTemp = totalTemp / worldSize;
		
		updateObservers();
		renderTick();
		writeCsv();
		
	}
	
	/**
	 * Create a bunch of daisy randomly based on the start population
	 */
	public void setupDaisy() {
		int startWhite = (int) Math.round((worldSize * Parameters.START_WHITE));
		int startBlack = (int) Math.round((worldSize * Parameters.START_BLACK));
		int startRed = (int) Math.round((worldSize * Parameters.START_RED));

		Set<Coordinate> usedCoor = new HashSet<Coordinate>();

		// create black daisy
		for (int j = 0; j < startBlack; j++) {
			int new_x = rand.nextInt(xSize);
			int new_y = rand.nextInt(ySize);

			// ensure daisy are born at different cells
			while (usedCoor.contains(new Coordinate(new_x, new_y))) {
				new_x = rand.nextInt(xSize);
				new_y = rand.nextInt(ySize);
			}

			patchMap.get(new Coordinate(new_x, new_y)).setCurrentDaisy(new BlackDaisy(1));
			usedCoor.add(new Coordinate(new_x, new_y));
		}

		// create white daisy 
		for (int i = 0; i < startWhite; i++) {
			int new_x = rand.nextInt(xSize);
			int new_y = rand.nextInt(ySize);
			
			// ensure daisy are born at different cells
			while (usedCoor.contains(new Coordinate(new_x, new_y))) {
				new_x = rand.nextInt(xSize);
				new_y = rand.nextInt(ySize);
			}
	
			patchMap.get(new Coordinate(new_x, new_y)).setCurrentDaisy(new WhiteDaisy(1));
			usedCoor.add(new Coordinate(new_x, new_y));
		}
		// create red daisy
		for (int k = 0; k < startRed; k++) {
			int new_x = rand.nextInt(xSize);
			int new_y = rand.nextInt(ySize);

			// ensure daisy are born at different cells
			while (usedCoor.contains(new Coordinate(new_x, new_y))) {
				new_x = rand.nextInt(xSize);
				new_y = rand.nextInt(ySize);
			}

			patchMap.get(new Coordinate(new_x, new_y)).setCurrentDaisy(new RedDaisy(1));
			usedCoor.add(new Coordinate(new_x, new_y));
		}
	}
	
	/**
	 * Diffuse heat to eight neighbours
	 * @param patch
	 */
	public void diffuseTemperature(Patch clonePatch, Coordinate coor) {
		int x = coor.getX();
		int y = coor.getY();
		double heatForDiffuse = clonePatch.getTemp() * Parameters.DIFFUSION_RATE;
		double diffuseEachShare = heatForDiffuse / 8;
		double totalOutHeat = 0;
			
		for (int i = x - 1; i < x + 2; i++) {
			for (int j = y - 1; j < y + 2; j++) {
	
				// itself
				if (i == x && j == y) {
					continue;
				}
				
				Coordinate neighborCoor = new Coordinate(i, j);
				if (patchMap.containsKey(neighborCoor)) {
					patchMap.get(neighborCoor).diffuseTemp(diffuseEachShare);
					totalOutHeat += diffuseEachShare;
				}
			}
		}
		
		// reduce self heat
		patchMap.get(coor).diffuseTemp(-totalOutHeat);
		
	}
	
	/**
	 * Checks whether global heat is conserved after each update
	 * @param clone
	 */
	public void checkHeatEqual(HashMap<Coordinate, Patch> clone) {
		Double originalHeat = 0.0;
		Double updatedHeat = 0.0;
		
		for (Coordinate coor : patchMap.keySet()) {
			originalHeat += clone.get(coor).getTemp();
			updatedHeat += patchMap.get(coor).getTemp();
		}
	
		if (Math.abs(originalHeat - updatedHeat) >= 0.000000001) {
			System.out.println("Heat NOT conserved.");
		}
		
	}
	
	/**
	 * Find neighbor patches without daisy
	 * @param coor
	 * @return patch list
	 */
	public ArrayList<Patch> getEmptyNeighbors(Coordinate coor){
		int x = coor.getX();
		int y = coor.getY();
		ArrayList<Patch> emptyPatch = new ArrayList<>();
		
		// find empty patches
		for (int i = x - 1; i < x + 2; i++) {
			for (int j = y - 1; j < y + 2; j++) {
				
				if (i == x && j == y) {
					continue;
				}
				
				Coordinate neighCoor = new Coordinate(i, j);
				if (patchMap.containsKey(neighCoor) && 
						(patchMap.get(neighCoor).getCurrentDaisy() == null)) {
					emptyPatch.add(patchMap.get(neighCoor));
				}
			}
		}
		
		return emptyPatch;
	}
	
	/**
	 * Sprount daisy to empty neighbor patches based on Netlogo model
	 * @param patch
	 * @param coor
	 */
	public void sproutDaisy(Patch patch, Coordinate coor) {
		Double seedThreshold = patch.getSeedThreshold();
		double reproduceProb = rand.nextDouble();
		
		// sprount based on seedThreshold
		if (seedThreshold > reproduceProb) {
			ArrayList<Patch> emptyNeighbors = getEmptyNeighbors(coor);
			
			// randomly sprount to one of the empty neighbours
			if (emptyNeighbors.size() != 0) {
				int sprountIndex = rand.nextInt(emptyNeighbors.size());
				
				if (patch.getCurrentDaisy() instanceof BlackDaisy) {
					emptyNeighbors.get(sprountIndex).setCurrentDaisy(new BlackDaisy(0));
				}
				else if(patch.getCurrentDaisy() instanceof WhiteDaisy){
					emptyNeighbors.get(sprountIndex).setCurrentDaisy(new WhiteDaisy(0));
				}
				else if(patch.getCurrentDaisy() instanceof RedDaisy){
					emptyNeighbors.get(sprountIndex).setCurrentDaisy(new RedDaisy(0));
				}
			}
		}
	}
	
	/**
	 * Clones all the patches. This function is used for synchronized update
	 * @return copied patches
	 */
	public HashMap<Coordinate, Patch> clonePatches(){
		HashMap<Coordinate, Patch> clone = new HashMap<>();
		for (Coordinate coor : patchMap.keySet()) {
			clone.put(coor, new Patch(patchMap.get(coor)));
		}
		return clone;
	}
	
	/**
	 * Forward one tick, update status.
	 */
	public void updateTick() {
		// update each pathches' local temperature
		for (Coordinate coor : patchMap.keySet()) {
			patchMap.get(coor).updateLocalTemp();	
		}
		
		// create a copy of patches at current state
		HashMap<Coordinate, Patch> clone = clonePatches();
		
		// diffuse heat to neighbours, use clone to archieve synchronized update
		for (Coordinate coor : clone.keySet()) {
			diffuseTemperature(clone.get(coor), coor);
		}
		
		// check whether global heat is conserved
		checkHeatEqual(clone);
		
		// update daisy status, including old, dead and sprout
		ArrayList<Coordinate> daisySet = getCoorWithDaisy();
		while (!daisySet.isEmpty()) {
			int daisyIndex = rand.nextInt(daisySet.size());
			Coordinate cur = daisySet.get(daisyIndex);
			daisySet.remove(daisyIndex);
			Patch patch = patchMap.get(cur);
			patch.updateAge();
			if (patch.checkDaisyDead()) {
				continue;
			} else {
				// sprount daisy based, new born daisy can not sprount in this tick
				sproutDaisy(patch, cur);
			}
		}
		
		updateObservers();
		this.tick += 1;
		
		if ( scenario == Scenario.RAMP_UP_RAMP_DOWN) {
			updateLuminosity();
		}
	}
	
	/**
	 * Get a list of coordinates which have daisy on
	 * @return coordinate list
	 */
	public ArrayList<Coordinate> getCoorWithDaisy(){
		ArrayList<Coordinate> daisyCoor = new ArrayList<>();
		for (Coordinate coor : patchMap.keySet()) {
			if (patchMap.get(coor).getCurrentDaisy() != null) {
				daisyCoor.add(coor);
			}
		}
		return daisyCoor;
	}
	
	/**
	 * Update luminosity in rump-up and down mode based on Netlogo formula
	 */
	public void updateLuminosity() {
		if (tick > 200 && tick <= 400) {
			Parameters.LUMINOSITY += 0.005;
		} else if (tick > 600 && tick <= 850) {
			Parameters.LUMINOSITY -= 0.0025;
		}
		// preserve only four decimal
		String roundFour = String.format("%.4f", Parameters.LUMINOSITY);
		Parameters.LUMINOSITY = Double.parseDouble(roundFour);
	}
	
	/**
	 * Update observer status
	 */
	public void updateObservers() {
		double totalTemp = 0;
		int totalPop = 0;
		int whitePop = 0;
		int blackPop = 0;
		int redPop = 0;
		
		// enumerate to collect status
		for (Coordinate coor : patchMap.keySet()) {
			Patch patch = patchMap.get(coor);
			double temp = patch.getTemp();
			
			totalTemp += temp;
			Daisy type = patch.getCurrentDaisy();
			if (type instanceof WhiteDaisy) {
				whitePop += 1;
				totalPop += 1;
			}else if (type instanceof BlackDaisy) {
				blackPop += 1;
				totalPop += 1;
			}
			else if (type instanceof RedDaisy){
				redPop += 1;
				totalPop += 1;
			}
		}
		
		this.globalTemp = totalTemp / worldSize;
		this.whitePop = whitePop;
		this.blackPop = blackPop;
		this.redPop = redPop;
		this.totalPop = totalPop;
	}
	
	/**
	 * Print system status
	 */
	public void renderTick() {
		String observer = String.format("Global temperature: %.4f, Total population: %d, "
				+ "White Daisy population: %d, Black Daisy population: %d, Red Daisy population: %d",
				globalTemp, totalPop, whitePop,blackPop,redPop);
		System.out.println(observer);
	}
	
	/**
	 * Write status to csv file
	 */
	public void writeCsv() {
		List<String> line =Arrays.asList(String.valueOf(tick) 
				, String.valueOf(whitePop), String.valueOf(blackPop)
				, String.valueOf(redPop), String.valueOf(totalPop), String.valueOf(globalTemp));
		this.csv.writeLine(line);
	}
	
	/**
	 * Save all the record
	 */
	public void flush() {
		this.csv.saveFile();
    System.out.println("");
	}
	
	/**
	 * The main function takes in parameters from command line and execute experiments
	 * @param args - name : experiment name
	 * 			   - params: in order [START_WHITE] [START_BLACK] [ALBEDO_WHITE]
	 * 				 [ALBEDO_BLACK] [ALBEDO_GROUND] [LUMINOSITY] [SENCERIO]
	 */
	public static void main(String[] args) {
    if (args.length == 0) {
      return;
    }

		String name = args[0];
		String dir = "experiments/";
		
		// Comment line, skip
		if (name.contains("#")) {
			return;
		}
		
		Parameters.setupParameters(args);
		CsvHandler csv = new CsvHandler(dir + name);
		
        World world = new World(csv);
        
        for (int i = 0; i < Parameters.ROUNDS; i++) {
        	world.updateTick();
        	world.renderTick();
        	world.writeCsv();
        }
        
        world.flush();
        
    }
}
