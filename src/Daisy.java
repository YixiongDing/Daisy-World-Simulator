public class Daisy {
    private int age;
    private double alberdo;
    private double reproducrRate;
    private double heatValue;

    public Daisy(int age, double alberdo, double reproducrRate, double heatValue){
        this.age = age;
        this.alberdo = alberdo;
        this.reproducrRate = reproducrRate;
        this.heatValue = heatValue;
    }

    public void updateAge(int age){
        this.age = age;
    }

    public void updateAlberdo(double alberdo){
        this.alberdo = alberdo;
    }

    public void updateReproducrRate(double reproducrRate){
        this.reproducrRate = reproducrRate;
    }

    public void updateHeatValue(double heatValue){
        this.heatValue = heatValue;
    }

    public int getAge(){
        return this.age;
    }

    public double getAlberdo(){
       return this.alberdo;
    }

    public double getReproducrRate(){
       return this.reproducrRate;
    }

    public double getHeatValue(){
        return this.heatValue;
    }
}
