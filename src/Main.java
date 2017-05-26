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
        hooman.setPrivateSpace(2);
        Particle obs = new Particle(1,0.5,5,4.8,0,0,80);
        Particle obs2 = new Particle(2,0.5,7,4.8,0,0,80);
        Particle obs4 = new Particle(3,0.5,11,4.8,0,0,80);
        Particle obs3 = new Particle(4,0.5,13,4.8,0,0,80);
        Particle obs5 = new Particle(5,0.5,13,8,0,0,80);
        Particle obs6 = new Particle(6,0.5,11,6,0,0,80);
        Particle obs7 = new Particle(7,0.5,11,9,0,0,80);
        Particle obs8 = new Particle(8,0.5,7,10,0,0,80);
        obstacles.add(obs);
        obstacles.add(obs2);
        obstacles.add(obs3);
        obstacles.add(obs4);
        obstacles.add(obs5);
        obstacles.add(obs6);
        obstacles.add(obs7);
        //obstacles.add(obs8);
        hooman.setGoal(new Vector(15,5));
        people.add(hooman);
        Simulation s = new Simulation(people,obstacles);

        s.simulate();
    }
}
