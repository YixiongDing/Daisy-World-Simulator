public class BlackDaisy extends Daisy{
    public BlackDaisy(){
        super();
        this.updateAlbedo(Parameters.ALBEDO_BLACK);
    }
    public BlackDaisy(int age){
        super(age);
        this.updateAlbedo(Parameters.ALBEDO_BLACK);
    }
    public BlackDaisy(int age, double albedo){
        super(age, albedo);
    }
}
