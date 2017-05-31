/**
 * Created by lcasagrande on 21/03/17.
 */
public class Vector {
    double x = 0;
    double y = 0;

    public Vector(double x, double y){
        this.x = x;
        this.y = y;
    }
    public Vector(){

    }

    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }
    public Vector add(Vector v){
        x+= v.x;
        y+= v.y;
        return this;
    }
    public Vector add(double x, double y){
        this.x+=x;
        this.y+=y;
        return this;
    }
    @Override
    public String toString() {
        return "[Vector X: "+ x + " ,Y: " + y;
    }

    public void perpendicular(){
        double aux = x;
        x = -y;
        y = aux;
    }

    public void set(int x, double y) {
        this.x = x;
        this.y = y;
    }
    public void set(Vector v){
        this.x = v.x;
        this.y = v.y;
    }

    public Vector multiply(double i) {
        this.x*= i;
        this.y*= i;
        return this;
    }
}