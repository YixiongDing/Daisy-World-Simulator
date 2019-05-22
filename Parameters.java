/**
 * Parameter class for setting up all the parameters for the experiment.
 *
 * @author Shenglan Yu<shenglany1@student.unimelb.edu.au> - 808600
 * TODO: FILL your name and id
 *
 */
public class Parameters {

	public static double ALBEDO_WHITE = 0.75;

	public static double ALBEDO_BLACK = 0.25;

	public static double ALBEDO_RED = 0.5;

	public static double ALBEDO_GROUND = 0.4;

	public static double LUMINOSITY = 0.8;
	
	public static double START_WHITE = 0.2;
	
	public static double START_BLACK = 0.2;
	
	public static Sencerio SENCERIO = Sencerio.STABLE;
	
	// Below parameters are considered as fixed in every experiments
	public static double DIFFUSION_RATE = 0.5;

	public static int DAISY_LIFE_EXPECTANCY = 25;

	public static int WORLD_SIZE_X = 29;

	public static int WORLD_SIZE_Y = 29;

	public static int ROUNDS = 30;
	
	/**
	 * This function setup all params. 
	 * @param params See main function for the schema
	 */
	public static void setupParameters(String[] params) {
		START_WHITE = Double.parseDouble(params[1]);
		START_BLACK = Double.parseDouble(params[2]);
		ALBEDO_WHITE = Double.parseDouble(params[3]);
		ALBEDO_BLACK = Double.parseDouble(params[4]);
		ALBEDO_GROUND = Double.parseDouble(params[5]);
		LUMINOSITY = Double.parseDouble(params[6]);
		if (Integer.parseInt(params[7]) == 1) {
			SENCERIO = Sencerio.STABLE;
		} else {
			SENCERIO = Sencerio.RAMP_UP_RAMP_DOWN;
		}
	}

}
