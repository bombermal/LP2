
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Sardine extends Fish {

    private static final int BREEDING_AGE = 10;
    private static final int MAX_AGE = 40;
    private static final double BREEDING_PROBABILITY = 0.30;
    private static final int MAX_LITTER_SIZE = 5;
    private static final int SEAWEED_FOOD_VALUE = 4;
    private static final Random rand = Randomizer.getRandom();
    private int age;
    private int foodLevel;

    public Sardine(boolean randomAge, Ocean ocean, Location location) {
        super(ocean, location);
        if (randomAge) {
            age = rand.nextInt(MAX_AGE);
            foodLevel = rand.nextInt(SEAWEED_FOOD_VALUE);
        } else {
            age = 0;
            foodLevel = SEAWEED_FOOD_VALUE;
        }
    }
    
    public void act(List<Fish> newSardinees) {
        incrementAge();
        incrementHunger();
        if (isAlive()) {
            giveBirth(newSardinees);
            Location location = getLocation();
            Location newLocation = findFood(location);
            if (newLocation == null) {
                newLocation = getOcean().freeAdjacentLocation(location);
            }
            if (newLocation != null) {
                setLocation(newLocation);
            } else {
                setDead();
            }
        }
    }
    
    private void incrementAge() {
        age++;
        if (age > MAX_AGE) {
            setDead();
        }
    }

    private void incrementHunger() {
        foodLevel--;
        if (foodLevel <= 0) {
            setDead();
        }
    }
    //Ainda sem uso
    private boolean eatOrNotEat(){
        return foodLevel <= 50;
    }

    private Location findFood(Location location) {
        Ocean ocean = getOcean();
        List<Location> adjacent = ocean.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while (it.hasNext()) {
            Location where = it.next();
            Object animal = ocean.getObjectAt(where);
            if (animal instanceof SeaWeed) {
                SeaWeed sweed = (SeaWeed) animal;
                if (sweed.isAlive()) {
                    sweed.setDead();
                    foodLevel = SEAWEED_FOOD_VALUE;
                    // Remove the dead rabbit from the ocean.
                    return where;
                }
            }
        }
        return null;
    }

    private void giveBirth(List<Fish> newSardinees) {
        Ocean ocean = getOcean();
        List<Location> free = ocean.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for (int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Sardine young = new Sardine(false, ocean, loc);
            newSardinees.add(young);
        }
    }

    private int breed() {
        int births = 0;
        if (canBreed() && rand.nextDouble() <= BREEDING_PROBABILITY) {
            births = rand.nextInt(MAX_LITTER_SIZE) + 1;
        }
        return births;
    }

    private boolean canBreed() {
        return age >= BREEDING_AGE;
    }

}
