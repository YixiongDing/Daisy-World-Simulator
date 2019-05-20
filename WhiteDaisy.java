public class WhiteDaisy extends Daisy {
    public WhiteDaisy(){
        super();
        this.updateAlbedo(Parameters.ALBEDO_WHITE);
    }
    public WhiteDaisy(int age){
        super(age);
        this.updateAlbedo(Parameters.ALBEDO_WHITE);
    }
    public WhiteDaisy(int age, double albedo){
        super(age, albedo);
    }
}
