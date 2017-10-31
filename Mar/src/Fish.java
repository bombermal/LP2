
import java.util.List;

public abstract class Fish {

    private boolean alive;
    private Ocean ocean;
    private Location location;
    private int HungerLvl;

    public Fish(Ocean ocean, Location location) {
        alive = true;
        this.ocean = ocean;
        setLocation(location);
    }

    abstract public void act(List<Fish> newAnimals);

    public boolean isAlive() {
        return alive;
    }

    public void setDead() {
        alive = false;
        if (location != null) {
            ocean.clear(location);
            location = null;
            ocean = null;
        }
    }

    public Location getLocation() {
        return location;
    }

    public Ocean getOcean() {
        return ocean;
    }

    public void setLocation(Location newLocation) {
        if (location != null) {
            ocean.clear(location);
        }
        location = newLocation;
        ocean.place(this, newLocation);
    }
}
