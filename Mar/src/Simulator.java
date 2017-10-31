
import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Simulator {

    private Ocean ocean;
    private SimulatorView simView;

    private List<Fish> seres;
    private int step;

    private static final double ALGAS_PROB = 0.01;
    private static final double SARDINE_PROB = 0.03;
    private static final double TUNA_PROB = 0.02;
    private static final double SHARK_PROB = 0.01;

    public static void main(String[] args) throws InterruptedException {
        Simulator sim = new Simulator(100, 100);
        sim.run(true);
    }

    public Simulator(int height, int width) {

        seres = new ArrayList<Fish>();
        ocean = new Ocean(height, width);
        simView = new SimulatorView(height, width);

        simView.setColor(SeaWeed.class, Color.green);
        simView.setColor(Sardine.class, Color.cyan);
        simView.setColor(Tuna.class, Color.red);
        simView.setColor(Shark.class, Color.white);
        reset();

    }

    public void run(boolean tOrF) throws InterruptedException {  
        step = 0;
        while (tOrF) {
            simulateOneStep();
            Thread.sleep(60);
            //simView.showStatus(step, ocean);
        }

    }

    public void simulateOneStep() {
        step++;

        List<Fish> newAnimals = new ArrayList<Fish>();
        for (Iterator<Fish> it = seres.iterator(); it.hasNext();) {
            Fish animal = it.next();
            animal.act(newAnimals);
            if (!animal.isAlive()) {
                it.remove();
            }
            if (step % 30 == 0) {
               newSWeedEveryTime(newAnimals);
            }
        }
        seres.addAll(newAnimals);
        simView.showStatus(step, ocean);
    }

    public void reset() {
        step = 0;
        seres.clear();
        populate();

        simView.showStatus(step, ocean);
    }

    private void populate() {
        Random rand = Randomizer.getRandom();
        ocean.clear();
        for (int row = 0; row < ocean.getHeight(); row++) {
            for (int col = 0; col < ocean.getWidth(); col++) {
                if (rand.nextDouble() <= ALGAS_PROB) {
                    Location location = new Location(row, col);
                    SeaWeed alga = new SeaWeed(true, ocean, location);
                    seres.add(alga);
                    //System.out.println(alga);
                } else if (rand.nextDouble() <= SARDINE_PROB) {
                    Location location = new Location(row, col);
                    Sardine sardinha = new Sardine(true, ocean, location);
                    seres.add(sardinha);
                } else if (rand.nextDouble() <= TUNA_PROB) {
                    Location location = new Location(row, col);
                    Tuna atum = new Tuna(true, ocean, location);
                    seres.add(atum);
                } else if (rand.nextDouble() <= SHARK_PROB) {
                    Location location = new Location(row, col);
                    Shark tuba = new Shark(true, ocean, location);
                    seres.add(tuba);
                }
            }
        }
    }

    private void newSWeedEveryTime(List<Fish> newSeaWeeds) {
        Random rand = Randomizer.getRandom();
        Random rdn = new Random();
        int vlrRow = rdn.nextInt(ocean.getHeight());
        int vlrCol = rdn.nextInt(ocean.getWidth());
        if (ocean.getFishAt(vlrRow, vlrCol) == null && rand.nextDouble() <= ALGAS_PROB ) {
            Location location = new Location(vlrRow, vlrCol);
            SeaWeed alga = new SeaWeed(false, ocean, location);
            newSeaWeeds.add(alga);
        }
    }
}
