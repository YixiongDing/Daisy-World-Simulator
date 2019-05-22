/**
 * Daisy class for  handling daisy status.
 *
 * @author Shenglan Yu<shenglany1@student.unimelb.edu.au> - 808600
 * TODO: FILL your name and id
 *
 */
public class Daisy {
    private int age;
    private double albedo;
    
    // constructors
    public Daisy(){ }
    
    // clone constructor
    public Daisy(Daisy another) {
    	this.age = another.age;
    	this.albedo = another.albedo;
    }
    
    public Daisy(int age){
        this.age = age;
    }

    public Daisy(int age, double albedo){
        this.age = age;
        this.albedo = albedo;
    }
    
    // setter
    public void updateAge(int age){
        this.age = age;
    }
    
    // setter
    public void updateAlbedo(double albedo){
        this.albedo = albedo;
    }

    // getter
    public int getAge(){
        return this.age;
    }
    
    // getter
    public double getAlbedo(){
       return this.albedo;
    }
}
