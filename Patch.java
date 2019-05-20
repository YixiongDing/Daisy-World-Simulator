public class Patch{
    // Patch Temperature after diffusion
    private double temp;
    // Pre Diffusion Temperature
    private double preDifTemp;

    private Daisy currentDaisy;
    private double absorbLumin;

    public Patch(){ }
    /***********************Setter Methods**************************/
    public void setCurrentDaisy(Daisy newDaisy){
        if(newDaisy instanceof WhiteDaisy){
            //System.out.println("Setting white daisy");
            if(newDaisy.getAge() != 0) {
                int randomAge = (int) (Math.random() * (Parameters.DAISY_LIFE_EXPECTANCY + 1));
                newDaisy.updateAge(randomAge);
            }
            this.currentDaisy = newDaisy;
        }
        else if(newDaisy instanceof BlackDaisy){
            //System.out.println("setting black daisy");
            if(newDaisy.getAge()!=0){
                int randomAge = (int )(Math.random() * (Parameters.DAISY_LIFE_EXPECTANCY + 1));
                newDaisy.updateAge(randomAge);
            }
            this.currentDaisy = newDaisy;
        }
    }
    private void setGround(){
        this.currentDaisy = null;
    }

    /********************************Getter Methods************************************/
    public double getTemp(){
        return this.temp;
    }
    public double getPreDifTemp(){
        return this.preDifTemp;
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
    public double getSeedThreshold(){
        if(this.currentDaisy != null && this.currentDaisy.getAge() < Parameters.DAISY_LIFE_EXPECTANCY){
            double curTemp = getTemp();
            return ((curTemp*0.1457) - (0.0032*curTemp*curTemp) - 0.6443);
        }
        return 0.0;
    }
    /****Other Class Methods to interact with world****/

    public void checkDaisyDead(){
        if(this.currentDaisy != null && this.currentDaisy.getAge() >= Parameters.DAISY_LIFE_EXPECTANCY){
            this.currentDaisy = null;
        }
    }
    public void calAbosrbLumin(){
        this.absorbLumin = (1 - getAlbedo()) * Parameters.LUMINOSITY;
    }
    public void updateLocalTemp(){
    	calAbosrbLumin();
    	double localHeat;
        if(this.absorbLumin > 0){
            localHeat = (Math.log(this.absorbLumin) * 72) + 80;
        }
        else{
            localHeat = 80;
        }
        this.preDifTemp = (this.temp + localHeat) / 2;
    }
    public void updateTemp(double diffuseTemp, int NeighborNum){
        double curTemp = getPreDifTemp() * (1 - Parameters.DIFFUSION_RATE);
        double newTemp;
        newTemp = (1 + (8 - NeighborNum) / 8 ) * curTemp + diffuseTemp;
        //update patch temperature after diffusion
        this.temp = newTemp;
    }
	public void updateAge() {
		if(this.currentDaisy != null) {
			int newAge = this.currentDaisy.getAge() + 1;
			this.currentDaisy.updateAge(newAge);
		}
	}
}