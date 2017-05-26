import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lcasagrande on 26/05/17.
 */
public class Simulation {
    List<Particle> people;
    List<Particle> obstacle;
    double dt = 0.01;
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
            fl.write("2\n"+(acum*dt) +"\n");
            for(Particle p : people){
                Vector force =forces.get(p.id);
                force.set(p.goalForce());
                for(Particle o : obstacle){

                    double t = p.predict(o);
                    if(t > 0){
                        force.add(p.repulsionForce(o,t));
                    }
                }
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

    private boolean peopleHasReachTarget() {

        for(Particle p : people){
            if(!p.isOnTarget())
                return false;
        }
        return true;
    }
}
