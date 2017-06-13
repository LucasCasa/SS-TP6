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
    List<Double> time = new ArrayList<Double>();
    List<Double> longitud = new ArrayList<Double>();
    List<Double> avgSpeed = new ArrayList<Double>();
    List<Double> opt = new ArrayList<Double>();
    double dt = 1.0/60;
    double ta = 3;
    double t;
    public Simulation(List<Particle> p, List<Particle> o){
        people = p;
        obstacle = o;
    }

    public void simulate(boolean file) throws IOException{
        t++;
        Particle h = getHuman();
        double optim = Math.sqrt((h.x - h.goal.x)*(h.x - h.goal.x) + (h.y - h.goal.y)*(h.y - h.goal.y))- h.radius;
        List<Vector> forces = new ArrayList<>();
        for(int i = 0; i<people.size();i++){
            forces.add(new Vector(0,0));
        }
        FileWriter fl = null;
        if(file)
             fl = new FileWriter("out.txt");
        int acum = 0;
        while(!peopleHasReachTarget() && acum*dt < 35){
            acum++;
            if(file)
                fl.write((people.size() + obstacle.size()) + "\n"+(acum*dt) +"\n");
            for(Particle p : people){
                Vector force =forces.get(p.id);
                force.set(p.goalForce());
                /*for(Particle o : obstacle){
                    force.add(p.repulsionWall(o));
                }*/
                getForce(p,people,force);
                getForce(p,obstacle,force);
                p.updatePosition(force,dt);

            }

            for(Particle p : people){
                if(file)
                    fl.write(p.toString());
                if(p.id > 0){
                    if(p.isOnTarget()){
                        if(p.goal.x == 0)
                            p.setGoal(new Vector(10,p.getY()));
                        else
                            p.setGoal(new Vector(0,p.getY()));
                        p.onTarget = false;
                    }
                }else{
                    if(p.isOnTarget()) {
                        //System.out.println("Llegue");
                    }
                }
            }
            for (Particle o : obstacle){
                if(file)
                    fl.write(o.toString());
                o.updatePosition(dt);
                if(o.x > Main.w || o.x < 0){
                    o.vx*=-1;
                }
            }
        }
        if(h.onTarget){
            //System.out.println("Tiempo: " + acum*dt);
            time.add(acum*dt);
            //System.out.println("Longitud del Recorrido: " + people.get(0).acum);
            longitud.add(people.get(0).acum);
            //System.out.println("Recorrido Optimo: " + optim);
            opt.add(optim);
            //System.out.println("Velocidad Media: " + people.get(0).acum/(acum*dt));
            avgSpeed.add(people.get(0).acum/(acum*dt));
            //System.out.println(t);
        }else{
            System.out.println("ERROR");
        }

        if(file)
            fl.close();

    }

    private Particle getHuman() {
        for(Particle p : people){
            if(p.id == 0){
                return p;
            }
        }
        return null;
    }

    private void getPeopleForce(Particle p, Vector force) {

    }

    private Vector getForce(Particle p,List<Particle> oth,Vector force) {
        Vector f = new Vector();
        Particle aux = new Particle(p);
        List<Collision> crash = new ArrayList<>();
        for(Particle o : oth){
            double t = p.predict(o);
            if(t >= 0 && t < 5) {
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
                p.testPosition(force.add(p.repulsionForce(o.p, o.time).multiply(1.0/(i+1))), dt);
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
            if(p.id == 0 && p.onTarget)
                return true;
        }
        return false;
    }
}
