
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Ocean {

    private static final Random rand = Randomizer.getRandom();

    private int height, width;
    private Fish[][] matriz = new Fish[100][100];

    public Ocean(int height, int width) {
        this.height = height;
        this.width = width;
    }

    public Fish getObjectAt(Location location) {
        return getFishAt(location.getRow(), location.getCol());
    }

    public Fish getFishAt(int row, int col) {
        return matriz[row][col];
    }

    public List<Location> getFreeAdjacentLocations(Location location) {
        List<Location> free = new LinkedList<Location>();
        List<Location> adjacent = adjacentLocations(location);
        for (Location next : adjacent) {
            if (getObjectAt(next) == null) {
                free.add(next);
            }
        }
        return free;
    }

    public Location freeAdjacentLocation(Location location) {
        List<Location> free = getFreeAdjacentLocations(location);
        if (free.size() > 0) {
            return free.get(0);
        } else {
            return null;
        }
    }
    
    public List<Location> adjacentLocations(Location location) {
        assert location != null : "Null location passed to adjacentLocations";
        List<Location> locations = new LinkedList<Location>();
        if (location != null) {
            int row = location.getRow();
            int col = location.getCol();
            for (int roffset = -1; roffset <= 1; roffset++) {
                int nextRow = row + roffset;
                if (nextRow >= 0 && nextRow < height) {
                    for (int coffset = -1; coffset <= 1; coffset++) {
                        int nextCol = col + coffset;
                        if (nextCol >= 0 && nextCol < width && (roffset != 0 || coffset != 0)) {
                            locations.add(new Location(nextRow, nextCol));
                        }
                    }
                }
            }

            Collections.shuffle(locations, rand);
        }
        return locations;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void clear() {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                matriz[row][col] = null;
            }
        }
    }

    public void clear(Location location) {
        matriz[location.getRow()][location.getCol()] = null;
    }

    public void place(Fish animal, int row, int col) {
        place(animal, new Location(row, col));
    }

    public void place(Fish animal, Location location) {
        matriz[location.getRow()][location.getCol()] = animal;
    }
}
