/**
 * Black Daisy class.
 *
 * @author Shenglan Yu<shenglany1@student.unimelb.edu.au> - 808600
 * TODO: FILL your name and id
 *
 */
public class BlackDaisy extends Daisy{
	
	// Below are all constructors
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
