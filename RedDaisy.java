/**
 * Black Daisy class.
 *
 * @author Shenglan Yu<shenglany1@student.unimelb.edu.au> - 808600
 * @author Haohua Wu<haohuaw@student.unimelb.edu.au> - 927081
 *
 */
public class RedDaisy extends Daisy{

    // Below are all constructors
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
