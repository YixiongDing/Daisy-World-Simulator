

public class Patch{
    private float temp;
    private int xAsis;
    private int yAsis;
    private float groudAlbedo;
    private int patchType;
    private Daisy currentDaisy;

    private float absorbLumin;

    public Patch(){

    }
    public Patch(float temp){
        this.temp = temp;
    }
    public Patch(float temp, float gAlbedo, int type){
        this.temp = temp;
        this.groudAlbedo = gAlbedo;
        this.patchType = type;
        setCurrentDaisy();
    }
    //Setter Methods
    private void setCurrentDaisy(){
        if (this.patchType == 0){
            this.currentDaisy = null;
        }
        else if(this.patchType == 1){
            int randomAge = (int)(Math.random() * (Parameters.DAISY_LIFE_EXPECTANCY + 1));
            this.currentDaisy = new WhiteDaisy(randomAge, Parameters.ALBEDO_WHITE);
        }
        else if(this.patchType == 2){
            int randomAge = (int )(Math.random() * (Parameters.DAISY_LIFE_EXPECTANCY + 1));
            this.currentDaisy = new BlackDaisy(randomAge, Parameters.ALBEDO_BLACK);
        }
    }
    //public put daisy
    public void putDaisy(int type, int age){
    	this.patchType = type;
        if (type == 0){
            this.currentDaisy = null;
        }
        else if(type == 1){
            this.currentDaisy = new WhiteDaisy(age, Parameters.ALBEDO_WHITE);
        }
        else if(type == 2){
            this.currentDaisy = new BlackDaisy(age, Parameters.ALBEDO_BLACK);
        }
    }
    //Getter Methods
    public float getTemp(){
        return temp;
    }
    public int getType(){return patchType;}
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
    // ****Other Class Methods to interact with world****

    public boolean checkDaisyDead(){
        if(this.currentDaisy != null && this.currentDaisy.getAge() >= Parameters.DAISY_LIFE_EXPECTANCY){
            this.currentDaisy = null;
            this.patchType = 0;
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