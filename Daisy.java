public class Daisy {
    private int age;
    private float albedo;

    public Daisy(){

    }
    public Daisy(int age){
        this.age = age;
    }

    public Daisy(int age, float albedo){
        this.age = age;
        this.albedo = albedo;
    }

    public void updateAge(int age){
        this.age = age;
    }

    public void updateAlbedo(float albedo){
        this.albedo = albedo;
    }


    public int getAge(){
        return this.age;
    }

    public float getAlbedo(){
       return this.albedo;
    }
}
