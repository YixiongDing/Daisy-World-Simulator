public class Daisy {
    private int age;
    private double albedo;

    public Daisy(int age, double albedo){
        this.age = age;
        this.albedo = albedo;
    }

    public void updateAge(int age){
        this.age = age;
    }

    public void updateAlbedo(double albedo){
        this.albedo = albedo;
    }

    public int getAge(){
        return this.age;
    }

    public double getAlbedo(){
       return this.albedo;
    }
}
