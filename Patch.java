import java.security.KeyStore;

public class Patch{
    private float temp;
    private int xAsis;
    private int yAsis;
    private float groudAlbedo;
    private int patchType;
    private Daisy currentDaisy;

    private double absorbLumin;

    public Patch(){

    }
    public Patch(float temp, float gAlbedo, Daisy curDaisy){
        this.temp = temp;
        this.groudAlbedo = gAlbedo;
        this.currentDaisy = curDaisy;
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
            return groudAlbedo;
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
        if(this.currentDaisy.getAge() <= 0){
            this.currentDaisy = null;
            return true;
        }
        return false;
    }
    public float calAbosrbLumin(){
        float absorbLumin = (1 - getAlbedo()) * Parameters.LUMINOSITY;
        return absorbLumin;
    }
    public void updateLocalTemp(){
        if(absorbLumin > 0){
            this.temp = (float)(Math.log(calAbosrbLumin()) * 72 + 80);
        }
        else{
            this.temp = 80;
        }
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
}