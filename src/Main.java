import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lcasagrande on 26/05/17.
 */
public class Main {

    public static void main(String[] args) throws IOException {
        List<Particle> obstacles = new ArrayList<>();
        List<Particle> people = new ArrayList<>();
        Particle hooman = new Particle(0,0.5,1,5,0,0,80);
        hooman.setPrivateSpace(1);
        Particle obs = new Particle(1,0.5,5,4.9,0,0,80);
        obstacles.add(obs);
        hooman.setGoal(new Vector(15,5));
        people.add(hooman);
        Simulation s = new Simulation(people,obstacles);

        s.simulate();
    }
}
