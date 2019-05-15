public class World {
    public static void main(String args[]) {
        BlackDaisy bd1 = new BlackDaisy(5, 1, 1, 1);
        BlackDaisy bd2 = new BlackDaisy(6, 2, 2, 2);
        WhiteDaisy wd1 = new WhiteDaisy(5, 1, 1, 1);
        WhiteDaisy wd2 = new WhiteDaisy(6, 3, 3, 3);

        System.out.println("bd1 "+ bd1.getAge()+" "+bd1.getAlberdo()+" "+bd1.getReproducrRate()+" "+bd1.getHeatValue());

    }
}
