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

        createObstacles(obstacles);
        Particle hooman = new Particle(0,0.5,Math.random()*5,20,0,0,80);
        boolean collision = false;
        for(Particle o : obstacles){
            if(Particle.dist2(hooman,o)< (hooman.radius + o.radius)*(hooman.radius + o.radius)){
                collision = true;
            }
        }
        hooman.setPrivateSpace(0.7);
        while(collision){
            collision = false;
            hooman.setX(Math.random()*8 + 1);
            for(Particle o : obstacles){
                if(Particle.dist2(hooman,o)< (hooman.radius + o.radius)*(hooman.radius + o.radius)){
                    collision = true;
                }
            }
        }
        Particle obs = new Particle(1,0.5,5,4.8,0,0,80);
        Particle obs2 = new Particle(2,0.5,7,4.8,0,0,80);
        Particle obs4 = new Particle(3,0.5,11,4.8,0,0,80);
        Particle obs3 = new Particle(4,0.5,13,4.8,0,0,80);
        Particle obs5 = new Particle(5,0.5,13,8,0,0,80);
        Particle obs6 = new Particle(6,1,11,5.5,0,0,80);
        Particle obs7 = new Particle(7,0.5,11,9,0,0,80);
        Particle obs8 = new Particle(8,0.5,7,10,0,0,80);
        /*obstacles.add(obs);
        obstacles.add(obs2);
        obstacles.add(obs3);
        //obstacles.add(obs4);
        obstacles.add(obs5);
        obstacles.add(obs6);
        obstacles.add(obs7);
        //obstacles.add(obs8);*/
        hooman.setGoal(new Vector(5,-1));
        people.add(hooman);
        System.out.println("SIMULO");
        Simulation s = new Simulation(people,obstacles);

        s.simulate();
    }

    private static void createObstacles(List<Particle> obstacles) {
        int id = 1;
        for(int i = 0; i<40;i++) {
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
}
