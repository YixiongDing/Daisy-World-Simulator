public class Patch{
    private float temp;
    private int xAsis;
    private int yAsis;
    private float groudAlbedo;
    private int patchType;
    private Daisy currentDaisy;

    private double absorbLumin;

    public Patch(float temp, int x, int y, float gAlbedo, Daisy curDaisy){
        this.temp = temp;
        this.xAsis = x;
        this.yAsis = y;
        this.groudAlbedo = gAlbedo;
        this.currentDaisy = curDaisy;
    }
    public Patch(float temp; int x, int y, float gAlbedo, int type){
        this.temp = temp;
        this.xAsis = x;
        this.yAsis = y;
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
            int randomAge = (int )(Math.random() * (Parameter.DAISY_LIFE_EXPECTANCY + 1);
            this.currentDaisy = WhiteDaisy(randomAge, Parameter.ALBEDO_WHITE);
        }
        else if(this.patchType == 2){
            int randomAge = (int )(Math.random() * (Parameter.DAISY_LIFE_EXPECTANCY + 1);
            this.currentDaisy = BlackDaisy(randomAge, Parameter.ALBEDO_BLACK);
        }
    }
    //Getter Methods
    public float getTemp(){
        return temp;
    }
    public int getxAsis(){
        return xAsis;
    }
    public int getyAsis(){
        return yAsis;
    }
    public float getAlbedo(){
        if(this.currentDaisy == null){
            return groudAlbedo;
        }
        else{
            return this.currentDaisy.getAlbedo();
        }
    }
    public float getSeedThreshold(){
        if(this.currentDaisy != null){
            curTemp = getTemp();
            seedThreshold = (curTemp * 0.1457 - (0.0032 * curTemp^2) - 0.6443);
            return seedThreshold;
        }
        return 0;
    }
    // ****Other Class Methods to interact with world****

    public boolean checkDaisyDead(){
        if(this.currentDaisy.getAge() <= 0){
            this.currentDaisy = null;
            return True;
        }
        return False;
    }
    public float calAbosrbLumin(){
        absorbLumin = (1 - getAlbedo()) * Parameter.LUMINOSITY;
        return absorbLumin;
    }
    public void updateLocalTemp(){
        if(absorbLumin > 0){
            this.temp = Math.log(calAbosrbLumin()) * 72 + 80;
        }
        else{
            this.temp = 80;
        }
    }
    public void updateTemp(float diffuseTemp, int NeighborNum){
        curTemp = getTemp() * Parameter.DIFFUSION_RATE;
        if (NeighborNum < 8){
            newTemp = (1 + (8 - NeighborNum) / 8 ) * curTemp + diffuseTemp;
        }
        else{
            newTemp = curTemp + diffuseTemp;
        }
        this.temp = newTemp;
    }
}