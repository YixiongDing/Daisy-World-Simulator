public class Patch{
    private float temp;
    private Daisy currentDaisy;
    private float absorbLumin;

    public Patch(){ }
    public Patch(float temp){
        this.temp = temp;
        setGround();
    }
    //Setter Methods
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
    public float getTemp(){
        return this.temp;
    }
    public Daisy getCurrentDaisy(){
        return this.currentDaisy;
    }
    public float getAlbedo(){
        if(this.currentDaisy == null){
            return Parameters.ALBEO_GROUND;
        }
        else{
            return this.currentDaisy.getAlbedo();
        }
    }
    public float getSeedThreshold(){
        if(this.currentDaisy != null){
            float curTemp = getTemp();
            float sqrCurTemp = curTemp * curTemp;
            float seedThreshold = (float) (curTemp * 0.1457 - (0.0032 * sqrCurTemp) - 0.6443);
            return seedThreshold;
        }
        return 0;
    }
    /****Other Class Methods to interact with world****/

    public boolean checkDaisyDead(){
        if(this.currentDaisy != null && this.currentDaisy.getAge() >= Parameters.DAISY_LIFE_EXPECTANCY){
            this.currentDaisy = null;
            return true;
        }
        return false;
    }
    public void calAbosrbLumin(){
        this.absorbLumin = (1 - getAlbedo()) * Parameters.LUMINOSITY;
    }
    public void updateLocalTemp(){
    	calAbosrbLumin();
    	float localHeat;
        if(this.absorbLumin > 0){
            localHeat = (float)(Math.log(this.absorbLumin) * 72 + 80);
        }
        else{
            localHeat = 80;
        }
        this.temp = (this.temp + localHeat)/2;
    }
    public void updateTemp(float diffuseTemp, int NeighborNum){
        float curTemp = getTemp() * Parameters.DIFFUSION_RATE;
        float newTemp;
        if (NeighborNum < 8){
            newTemp = (1 + (8 - NeighborNum) / 8 ) * curTemp + diffuseTemp;
        }
        else{
            newTemp = curTemp + diffuseTemp;
        }
        this.temp = newTemp;
    }
	public void updateAge() {
		if(this.currentDaisy != null) {
			int newAge = this.currentDaisy.getAge() + 1;
			this.currentDaisy.updateAge(newAge);
		}
	}
}