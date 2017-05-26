/**
 * Created by lcasagrande on 26/05/17.
 */
public class Collision implements Comparable<Collision> {
    Particle p;
    double time;

    public Collision(Particle p , double t){
        this.p = p;
        time = t;
    }

    @Override
    public int compareTo(Collision collision) {
        if(time < collision.time) return -1;
        if(time > collision.time) return 1;
        return 0;
    }
}
