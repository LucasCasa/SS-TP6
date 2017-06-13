import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lcasagrande on 26/05/17.
 */
public class Main {
    static final int w = 10;
    static final int h = 20;
    public static void main(String[] args) throws IOException {
        List<Particle> obstacles = new ArrayList<>();
        List<Particle> people = new ArrayList<>();
        Simulation s = new Simulation(people, obstacles);
        FileWriter time = new FileWriter("data.txt");
        int repeat = 1000;
        for(int i = 0;i<repeat;i++) {
            people.clear();
            obstacles.clear();
            //createObstacles(obstacles);
            createDinamicObstacles(people);
            Particle hooman = new Particle(0, 0.5, Math.random() * w, h+3, 0, 0, 80);
            boolean collision = false;
            for (Particle o : obstacles) {
                if (Particle.dist2(hooman, o) < (hooman.radius + o.radius) * (hooman.radius + o.radius)) {
                    collision = true;
                }
            }
            hooman.setPrivateSpace(hooman.radius+0.1);
            while (collision) {
                collision = false;
                hooman.setX(Math.random() * (w-2) + 1);
                for (Particle o : obstacles) {
                    if (Particle.dist2(hooman, o) < (hooman.radius + o.radius) * (hooman.radius + o.radius)) {
                        collision = true;
                    }
                }
            }
            hooman.setGoal(new Vector(w/2, -2));
            people.add(hooman);
            s.simulate(repeat == 1);
        }
        for(int i = 0; i<s.time.size();i++){
            time.write(s.time.get(i) + "\t" + s.avgSpeed.get(i) + "\t" +  s.longitud.get(i) + "\t" + s.opt.get(i) + "\n");
        }
        time.close();
    }


    private static void createObstacles(List<Particle> obstacles) {
        int id = 1;
        for(int i = 0; i<25;i++) {
            Particle p = new Particle(id, 0.2, Math.random() * w, Math.random() * h, 0, 0, 1);
            id++;
            boolean collision = false;
            for (Particle p2 : obstacles) {
                if (Particle.dist2(p, p2) < (p.radius + p2.radius + 2) * (p.radius + p2.radius + 2)) {
                    collision = true;
                }
            }
            while (collision) {
                p.setX(Math.random() * w);
                p.setY(Math.random() * h);
                collision = false;
                for (Particle p2 : obstacles) {
                    if (Particle.dist2(p, p2) < (p.radius + p2.radius + 2) * (p.radius + p2.radius + 2)) {
                        collision = true;
                    }
                }
            }
            obstacles.add(p);
        }
        /*Particle topLeft = new Particle(100,0,0);
        topLeft.setMod();
        obstacles.add(topLeft);
        Particle bottomLeft = new Particle(100,0,0);
        topLeft.setMod();
        obstacles.add(bottomLeftLeft);
        Particle topRigth = new Particle(100,0,0);
        topLeft.setMod();
        obstacles.add(topLeft);
        Particle bottomRigth= new Particle(100,0,0);
        topLeft.setMod();
        obstacles.add(topLeft);
        */

    }
    public static void createDinamicObstacles(List<Particle> obstacles){
        for(int i = 0; i<h;i++){
            Particle p = new Particle(i+1, 0.4, Math.random() * w, i, Math.random()*4 - 2, 0, 1);
            if(p.vx > 0)
                p.setGoal(new Vector(10,i));
            else
                p.setGoal(new Vector(0,i));
            p.setPrivateSpace(0.5);
            obstacles.add(p);
        }

    }

    public static void createDeterministicObstacles(List<Particle> obs){
        int c = 0;
        for(int i = 0; i<h;i+=2){
            for(int j = 0; j<w;j+=2) {
                Particle p = new Particle(i + 1, 0.2, j + c % 2, i, 0, 0, 1);
                obs.add(p);
            }
            c++;
        }
    }
}
