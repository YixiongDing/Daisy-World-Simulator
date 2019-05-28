/**
 * White Daisy class.
 *
 * @author Shenglan Yu<shenglany1@student.unimelb.edu.au> - 808600
 * @author Haohua Wu<haohuaw@student.unimelb.edu.au> - 927081
 *
 */
public class WhiteDaisy extends Daisy {
	
	// Below are all constructors
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
