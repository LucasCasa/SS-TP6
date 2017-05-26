import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Created by lcasagrande on 26/05/17.
 */
public class Simulation {
    List<Particle> people;
    List<Particle> obstacle;
    double dt = 1.0/60;
    double ta = 3;
    public Simulation(List<Particle> p, List<Particle> o){
        people = p;
        obstacle = o;
    }

    public void simulate() throws IOException{
        List<Vector> forces = new ArrayList<>();
        for(int i = 0; i<people.size();i++){
            forces.add(new Vector(0,0));
        }
        FileWriter fl = new FileWriter("out.txt");
        int acum = 0;
        while(!peopleHasReachTarget()){
            acum++;
            fl.write((people.size() + obstacle.size()) + "\n"+(acum*dt) +"\n");
            for(Particle p : people){
                Vector force =forces.get(p.id);

                force.set(p.goalForce());
                for(Particle o : obstacle){
                    force.add(p.repulsionWall(o));
                }
                //getObstacleForce(p,force);

                p.updatePosition(force,dt);

            }

            for(Particle p : people){
                fl.write(p.x + "\t" + p.y + "\t" + p.radius + "\t" + p.vx + "\t" + p.vy + "\n");
                if(p.isOnTarget()){
                    System.out.println("Llegue");
                }
            }
            for (Particle o : obstacle){
                fl.write(o.x + "\t" + o.y + "\t" + o.radius + "\t" + o.vx + "\t" + o.vy + "\n");
            }
        }
        System.out.println("TODOS");
        fl.close();

    }

    private Vector getObstacleForce(Particle p,Vector force) {
        Vector f = new Vector();
        Particle aux = new Particle(p);
        List<Collision> crash = new ArrayList<>();
        for(Particle o : obstacle){
            double t = p.predict(o);
            if(t >= 0 && t < 10) {
                crash.add(new Collision(o,t));
            }
        }
        Collections.sort(crash);
        int i = 0;
        boolean avoidCrash = false;
        if(!crash.isEmpty()) {
            while (i < 5 && !avoidCrash) {
                avoidCrash = true;
                Collision o = crash.get(i);
                p.testPosition(force.add(p.repulsionForce(o.p, o.time)), dt);
                for (int j = i + 1; j < crash.size() && avoidCrash; j++) {
                    avoidCrash = (p.testPredict(crash.get(j).p) == -1);
                }
                i++;
            }
        }
        return force;
    }

    private boolean peopleHasReachTarget() {

        for(Particle p : people){
            if(!p.isOnTarget())
                return false;
        }
        return true;
    }
}
