public class Daisy {
    private int age;
    private float alberdo;

    public Daisy(int age, float alberdo){
        this.age = age;
        this.alberdo = alberdo;
    }

    public void updateAge(int age){
        this.age = age;
    }

    public void updateAlberdo(float alberdo){
        this.alberdo = alberdo;
    }


    public int getAge(){
        return this.age;
    }

    public float getAlberdo(){
       return this.alberdo;
    }
}
