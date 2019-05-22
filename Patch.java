import java.util.Random;

/**
 * Patch class representing one cell in the experiment.
 *
 * @author Shenglan Yu<shenglany1@student.unimelb.edu.au> - 808600
 * TODO: FILL your name and id
 *
 */
public class Patch{
	
    // Patch Temperature
    private double temp;
    // Daisy on the patch
    private Daisy currentDaisy;
    private double absorbLumin;

    public Patch(){ 
    	currentDaisy = null;
    	temp = 0;
    	absorbLumin = 0;
    }
    
    // clone constructor
    public Patch(Patch another){
    	this.temp = another.temp;
    	this.absorbLumin = another.absorbLumin;
    	if (another.currentDaisy != null) {
    		this.currentDaisy = new Daisy(another.currentDaisy);
    	} else {
    		this.currentDaisy = null;
    	}
    }
    
    /***********************Setter Methods**************************/
    
    /**
     * Create new daisy on this patch
     * @param newDaisy
     */
    public void setCurrentDaisy(Daisy newDaisy){
    	Random rand = new Random();
    	
    	// build daisy
        if(newDaisy instanceof WhiteDaisy){
        	
            if(newDaisy.getAge() != 0) {
                int randomAge = rand.nextInt(Parameters.DAISY_LIFE_EXPECTANCY);
                newDaisy.updateAge(randomAge);
            }
            this.currentDaisy = newDaisy;
        }
        else if(newDaisy instanceof BlackDaisy){
            
            if(newDaisy.getAge() != 0){
                int randomAge = rand.nextInt(Parameters.DAISY_LIFE_EXPECTANCY);
                newDaisy.updateAge(randomAge);
            }
            this.currentDaisy = newDaisy;
        }
    }
    
    /********************************Getter Methods************************************/
    public double getTemp(){
        return this.temp;
    }
    
    public Daisy getCurrentDaisy(){
        return this.currentDaisy;
    }
    
    public double getAlbedo(){
        if(this.currentDaisy == null){
            return Parameters.ALBEDO_GROUND;
        }
        else{
            return this.currentDaisy.getAlbedo();
        }
    }
    
    /**
     * Calculate seed threshold based on the Netlogo model formula.
     * @return
     */
    public double getSeedThreshold(){
        if(this.currentDaisy != null && this.currentDaisy.getAge() < Parameters.DAISY_LIFE_EXPECTANCY){
            double curTemp = getTemp();
            return ((curTemp * 0.1457) - (0.0032 * curTemp * curTemp) - 0.6443);
        }
        return 0.0;
    }
    
    /****Other Class Methods to interact with world****/
    
    /**
     * Check whether daisy is dead, and remove the daisy if dead
     */
    public boolean checkDaisyDead(){
        if(this.currentDaisy != null && this.currentDaisy.getAge() >= Parameters.DAISY_LIFE_EXPECTANCY){
            this.currentDaisy = null;
            return true;
        }
        return false;
    }
    
    /**
     * Check how much luminosity the pathch absorbs
     */
    public void calAbosrbLumin(){
        this.absorbLumin = (1 - getAlbedo()) * Parameters.LUMINOSITY;
    }
    
    /**
     * Update local temperature based on Netlogo model formula
     */
    public void updateLocalTemp(){
    	calAbosrbLumin();
    	double localHeat;
    	
    	// calculate local heating
        if(this.absorbLumin > 0){
            localHeat = (Math.log(this.absorbLumin) * 72) + 80;
        }
        else{
            localHeat = 80;
        }
        
        this.temp = (this.temp + localHeat) / 2;
    }
    
    /**
     * Update diffuse heat. Positive amount means heat coming in. Negative means out.
     * @param amount
     */
    public void diffuseTemp(double amount) {
    	this.temp += amount;
    }
    
    /**
     * Forward daisy's age
     */
	public void updateAge() {
		if(this.currentDaisy != null) {
			int newAge = this.currentDaisy.getAge() + 1;
			this.currentDaisy.updateAge(newAge);
		}
	}
}