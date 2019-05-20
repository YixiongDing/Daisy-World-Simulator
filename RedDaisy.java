public class RedDaisy extends Daisy{
    public RedDaisy(){
        super();
        this.updateAlbedo(Parameters.ALBEDO_RED);
    }
    public RedDaisy(int age){
        super(age);
        this.updateAlbedo(Parameters.ALBEDO_RED);
    }
    public RedDaisy(int age, double albedo){
        super(age, albedo);
    }
}
