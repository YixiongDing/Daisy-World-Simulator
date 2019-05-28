/**
 * White Daisy class.
 *
 * @author Shenglan Yu<shenglany1@student.unimelb.edu.au> - 808600
 * 		   Yixiong Ding - 671499
 *  	   Haohua Wu - 927081
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
