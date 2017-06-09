import java.io.FileWriter;
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
        Simulation s = new Simulation(people, obstacles);
        FileWriter time = new FileWriter("data.txt");
        for(int i = 0;i<1000;i++) {
            people.clear();
            obstacles.clear();
            createObstacles(obstacles);
            Particle hooman = new Particle(0, 0.5, Math.random() * 10, 23, 0, 0, 80);
            boolean collision = false;
            for (Particle o : obstacles) {
                if (Particle.dist2(hooman, o) < (hooman.radius + o.radius) * (hooman.radius + o.radius)) {
                    collision = true;
                }
            }
            hooman.setPrivateSpace(0.7);
            while (collision) {
                collision = false;
                hooman.setX(Math.random() * 8 + 1);
                for (Particle o : obstacles) {
                    if (Particle.dist2(hooman, o) < (hooman.radius + o.radius) * (hooman.radius + o.radius)) {
                        collision = true;
                    }
                }
            }
            hooman.setGoal(new Vector(5, -1));
            people.add(hooman);
            s.simulate();
        }
        for(int i = 0; i<1000;i++){
            time.write(s.time.get(i) + "\t" + s.avgSpeed.get(i) + "\t" +  s.longitud.get(i) + "\t" + s.opt.get(i) + "\n");
        }
        time.close();
    }


    private static void createObstacles(List<Particle> obstacles) {
        int id = 1;
        for(int i = 0; i<30;i++) {
            Particle p = new Particle(id, 0.2, Math.random() * 10, Math.random() * 20, 0, 0, 1);
            id++;
            boolean collision = false;
            for (Particle p2 : obstacles) {
                if (Particle.dist2(p, p2) < (p.radius + p2.radius + 1.5) * (p.radius + p2.radius + 1.5)) {
                    collision = true;
                }
            }
            while (collision) {
                p.setX(Math.random() * 10);
                p.setY(Math.random() * 20);
                collision = false;
                for (Particle p2 : obstacles) {
                    if (Particle.dist2(p, p2) < (p.radius + p2.radius + 1.5) * (p.radius + p2.radius + 1.5)) {
                        collision = true;
                    }
                }
            }
            obstacles.add(p);
        }
        for(double i = 0; i<20;i+=0.5){
            obstacles.add(new Particle(id,0.5,-0.5,i,0,0,1));
            id++;
            obstacles.add(new Particle(id,0.5,10.5,i,0,0,1));
            id++;
        }

    }
    public static void createDinamicObstacles(List<Particle> obstacles){
        for(int i = 0; i<20;i++){
            Particle p = new Particle(i+1, 0.5, Math.random() * 10, i, Math.random()*4 - 2, 0, 1);
            obstacles.add(p);
        }

    }
}
