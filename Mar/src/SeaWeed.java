
import java.util.List;
import java.util.Random;


public class SeaWeed extends Fish
{

    private static final int BREEDING_AGE = 5;
    private static final int MAX_AGE = 10;
    private static final double BREEDING_PROBABILITY = 0.9;
    private static final int MAX_LITTER_SIZE = 4;
    private static final Random rand = Randomizer.getRandom();
    private int age;

    public SeaWeed(boolean randomAge, Ocean ocean, Location location)
    {
        super(ocean, location);
        age = 0;
        if(randomAge) {
            age = rand.nextInt(MAX_AGE);
        }
    }
    
    public void act(List<Fish> newSeaWeeds)
    {
        incrementAge();
        if(isAlive()) {
            giveBirth(newSeaWeeds);            
        }
    }

    private void incrementAge()
    {
        age++;
        if(age > MAX_AGE) {
            setDead();
        }
    }
    
    private void giveBirth(List<Fish> newSeaWeeds)
    {
        Ocean ocean = getOcean();
        List<Location> free = ocean.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            SeaWeed young = new SeaWeed(false, ocean, loc);
            newSeaWeeds.add(young);
        }
    }
        
    private int breed()
    {
        int births = 0;
        if(canBreed() && rand.nextDouble() <= BREEDING_PROBABILITY) {
            births = rand.nextInt(MAX_LITTER_SIZE) + 1;
        }
        return births;
    }

    private boolean canBreed()
    {
        return age >= BREEDING_AGE;
    }

    
}
