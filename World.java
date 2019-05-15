import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class World {
	private HashMap<Coordinate, Patch> patchMap;
	
	private float globalTemp;
	private int whitePop;
	private int blackPop;
	private int totalPop;
	private int xSize;
	private int ySize;
	private int worldSize;
	private int sencerio;
	private float luminosity;
	
	public World() {
		patchMap = new HashMap<Coordinate, Patch>();
		setupWorld();
	}
	
	public void setupWorld() {
		xSize = Parameters.WORLD_SIZE_X;
		ySize = Parameters.WORLD_SIZE_Y;
		worldSize = xSize * ySize;
		
		for (int i = 0; i < xSize; i++) {
			for (int j = 0; j < ySize; j++) {
				patchMap.put(new Coordinate(i, j),  new Patch()); 
			}
		}
		
		globalTemp = Parameters.START_TEMP;
		luminosity = Parameters.LUMINOSITY;
		sencerio = Parameters.SENCERIO;
		
		setupDaisy();
	}
	
	public void setupDaisy() {
		int total_size = worldSize;
		int startWhite = (int) (total_size * Parameters.START_WHITE);
		int startBlack = (int) (total_size * Parameters.START_BLACK);
		Set<Coordinate> usedCoor = new HashSet<Coordinate>();
		Random rand = new Random();
		
		for (int i = 0; i < startWhite; i++) {
			int new_x = rand.nextInt(xSize);
			int new_y = rand.nextInt(ySize);
			while (usedCoor.contains(new Coordinate(new_x, new_y))) {
				new_x = rand.nextInt(xSize);
				new_y = rand.nextInt(ySize);
			}
			//System.out.println(patchMap.containsKey(new Coordinate(new_x, new_y)));
			patchMap.get(new Coordinate(new_x, new_y)).putDaisy(1, rand.nextInt(Parameters.DAISY_LIFE_EXPECTANCY));	
			usedCoor.add(new Coordinate(new_x, new_y));
		}
		
		for (int j = 0; j < startBlack; j++) {
			int new_x = rand.nextInt(xSize);
			int new_y = rand.nextInt(ySize);
			while (usedCoor.contains(new Coordinate(new_x, new_y))) {
				new_x = rand.nextInt(xSize);
				new_y = rand.nextInt(ySize);
			}
			patchMap.get(new Coordinate(new_x, new_y)).putDaisy(2, rand.nextInt(Parameters.DAISY_LIFE_EXPECTANCY));
			usedCoor.add(new Coordinate(new_x, new_y));
		}
	}
	
	public void updateTemp(Coordinate coor) {
		
		int x = coor.getX();
		int y = coor.getY();
		float duf = 0;
		int neighborNumber = 0;
			
		for (int i = x - 1; i < x + 2; i++) {
			for (int j = y - 1; j < y + 2; j++) {
				
				if (i == x && j == y) {
					continue;
				}
				
				Coordinate neighCoor = new Coordinate(i, j);
				if (patchMap.containsKey(neighCoor)) {
					neighborNumber += 1;
					duf += patchMap.get(neighCoor).getTemp() * Parameters.DIFFUSION_RATE / 8;
				}
			}
		}
			
		patchMap.get(coor).updateTemp(duf, neighborNumber);
		
	}
	
	public void updateOldDaisy(Coordinate coor) {
		patchMap.get(coor).checkDaisyDead();
	}
	
	public void reproduceDaisy(Coordinate coor) {
		Patch patch = patchMap.get(coor);
		ArrayList<Patch> emptyPatch = new ArrayList<Patch>();
		int x = coor.getX();
		int y = coor.getY();
		int patchType;
		Random rand = new Random();
		
		if ((patchType = patch.getType()) != 0) {
			
			for (int i = x - 1; i < x + 2; i++) {
				for (int j = y - 1; j < y + 2; j++) {
					
					if (i == x && j == y) {
						continue;
					}
					
					Coordinate neighCoor = new Coordinate(i, j);
					if (patchMap.containsKey(neighCoor) && 
							(patchMap.get(neighCoor).getType() == 0)) {
						emptyPatch.add(patchMap.get(neighCoor));
					}
				}
			}
			if (emptyPatch.size() != 0) {
				int reproduce_index = rand.nextInt(emptyPatch.size());
				float reproduce_prob = rand.nextFloat();
			
				if (patch.getSeedThreshold() >= reproduce_prob) {
					emptyPatch.get(reproduce_index).putDaisy(patchType, 0);
				}
			}
			
		}
	}
	
	public void updateTick() {
		globalTemp = 0;
		whitePop = 0;
		blackPop = 0;
		totalPop = 0;
		
		for (Coordinate coor : patchMap.keySet()) {
			patchMap.get(coor).updateLocalTemp();
			patchMap.get(coor).updateAge();
			
		}
		
		for (Coordinate coor : patchMap.keySet()) {
			updateTemp(coor);
			reproduceDaisy(coor);
			updateOldDaisy(coor);

		}
		
		for (Coordinate coor : patchMap.keySet()) {
			Patch patch = patchMap.get(coor);
			float temp = patch.getTemp();
			
			globalTemp += temp / worldSize;
			int type = patch.getType();
			if (type == 1) {
				whitePop += 1;
				totalPop += 1;
			}else if (type == 2) {
				blackPop += 1;
				totalPop += 1;
			}
		}
		
	}
	
	public void renderTick() {
		// String status = String.format("Luminosity: %f, Temp: %f, Population: %d ", luminosity, globalTemp, totalPop);	
		System.out.println(globalTemp);
		System.out.println(totalPop);
		System.out.println(whitePop);
		System.out.println(blackPop);
		System.out.println(" ");
	}
	
	public static void main(String[] args) {
        World world = new World();
        
        for (int i = 0; i < Parameters.ROUNDS; i++) {
        	world.updateTick();
        	world.renderTick();
        }
        
    }
}
