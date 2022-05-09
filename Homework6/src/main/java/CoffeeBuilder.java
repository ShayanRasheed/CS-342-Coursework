public class CoffeeBuilder {
    private Coffee order;

    public CoffeeBuilder() {
        order = new BasicCoffee();
    }

    public void addExtraShot() {
        order = new ExtraShot(order);
    }

    public void addCream() {
        order = new Cream(order);
    }

    public void addSugar() {
        order = new Sugar(order);
    }

    public void addVanilla() {
        order = new Vanilla(order);
    }

    public void addWhippedCream() {
        order = new WhippedCream(order);
    }

    public double makeCoffee() {
        return order.makeCoffee();
    }

    public String getOrder() {
        double cost = (Math.round(makeCoffee()*100.0)/100.0);
        return "Total: " + cost;
    }

    // INNER CLASSES FOR COFFEE COMPONENTS:

    public class BasicCoffee implements Coffee {

        private double cost = 3.99;

        @Override
        public double makeCoffee() {
            return cost;
        }

    }

    public class ExtraShot extends CoffeeDecorator {

        private double cost = 1.20;

        ExtraShot(Coffee specialCoffee){
            super(specialCoffee);
        }

        public double makeCoffee() {
            return specialCoffee.makeCoffee() + addShot();
        }

        private double addShot() {
            return cost;
        }
    }

    public class Sugar extends CoffeeDecorator{

        private double cost = .50;

        Sugar(Coffee specialCoffee){
            super(specialCoffee);
        }

        public double makeCoffee() {
            return specialCoffee.makeCoffee()+ addSugar();
        }

        private double addSugar() {
            return cost;
        }
    }

    public class Cream extends CoffeeDecorator{
        private double cost = .50;

        Cream(Coffee specialCoffee){
            super(specialCoffee);
        }

        public double makeCoffee() {
            return specialCoffee.makeCoffee() + addCream();
        }

        private double addCream() {
            return cost;
        }
    }

    public class Vanilla extends CoffeeDecorator{
        private double cost = .35;

        Vanilla(Coffee specialCoffee) {
            super(specialCoffee);
        }

        public double makeCoffee() {
            return specialCoffee.makeCoffee()+ addVanilla();
        }

        public double addVanilla() {
            return cost;
        }
    }

    public class WhippedCream extends CoffeeDecorator{

        private double cost = .25;

        WhippedCream(Coffee specialCoffee){
            super(specialCoffee);
        }

        public double makeCoffee() {
            return specialCoffee.makeCoffee()+ addWhippedCream();
        }

        private double addWhippedCream() {
            return cost;
        }
    }
}
