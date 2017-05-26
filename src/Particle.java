/**
 * Created by Lucas on 02/04/2017.
 */
import java.util.ArrayList;

public class Particle {


    int id;
    double radius;
    double x;
    double y;
    double vx;
    double vy;
    double mass;
    double lastRx = 0;
    double nextX = 0;
    double lastAx = 0;
    double lastAy = 0;
    double lastRy = 0;
    double lastVx = 0;
    double lastVy = 0;
    double nextY = 0;
    Vector f;
    double ax;
    double ay;
    double prefSpeed = 1.4;
    double tau = 0.5;
    boolean onTarget;
    Vector goal;
    double privateSpace = 0;

    public Particle(int id,double radius, double x, double y,Vector speed,double mass){
        this.id = id;
        this.radius = radius;
        this.x = x;
        this.y = y;
        this.vx = speed.getX();
        this.vy = speed.getY();
        //this.nextSpeed = new Vector(0.03,0);
        this.mass = mass;
    }

    public Particle(int id,double radius, double x, double y,double velx,double vely,double mass){
        this.id = id;
        this.radius = radius;
        this.x = x;
        this.y = y;
        this.vx = velx;
        this.vy = vely;
        this.lastRx = x;
        this.lastRy = y;
        lastVx = velx;
        lastVy = vely;
        this.mass = mass;
        this.f = new Vector();
        privateSpace = radius;
    }


    public Particle(int id,double radius, double x, double y,double velx,double vely,double ax,double ay,double mass){
        this.id = 1;
        this.radius = radius;
        this.x=x;
        this.y = y;
        this.vx = velx;
        this.vy = vely;
        this.ax = ax;
        this.ay = ay;
        this.mass = mass;
        this.f = new Vector();
    }

    public Particle(int id, double radius){
        this.id = id;
        this.radius = radius;
    }

    public Particle(Particle p){
        x = p.x;
        y = p.y;
        vx = p.vx;
        vy = p.vy;
        mass = p.mass;
        radius = p.radius;
        lastRx = p.lastRx;
        lastRy = p.lastRy;
        id = p.id;
        f = p.f;
    }

    public void setPrevious(double x, double y){
        lastRx = x;
        lastRy = y;
    }

    public Particle(double mass, double radius){
        this.mass = mass;
        this.radius = radius;
    }

    public Particle(double x, double radius, double mass){
        this.x = x;
        this.y = 0;
        this.vx=0;
        this.vy=0;
        this.ax=0;
        this.ay=0;
        this.radius=radius;
        this.mass=mass;
    }

    public Particle(double x, double vx, double ax, double radius, double mass){
        this.x= x;
        this.y=0;
        this.vx = vx;
        this.vy = 0;
        this.ax = ax;
        this.ay = 0;
        this.radius = radius;
        this.mass = mass;

    }
    public double getRadius() {
        return radius;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public static double dist2(Particle p1, Particle p2){
        return (p2.x-p1.x)*(p2.x-p1.x) + (p2.y-p1.y)*(p2.y-p1.y);
    }

    public void setX(double x) {
        this.x = x;
        this.lastRx = x;
    }
    public void setY(double y){

        this.y = y;
        this.lastRy = y;
    }

    //System.out.println("CUANTO VALE EL MODULO DE SPEED? " + p.getSpeed().getModule())
    public int getId() {
        return id;
    }
    /*
        public Vector getSpeed() {
            return speed;
        }

        public void setSpeed(Vector speed) {
            this.speed = speed;
        }
    */
    public double getSpeedX(){
        return vx;
    }
    public double getSpeedY(){
        return vy;
    }

    public double getMass(){
        return mass;
    }

    @Override
    public String toString() {
        return x + "\t" + y + "\t" + radius + "\t" + f.x + "\t" + f.y + "\n";
    }

    public void setSpeedX(double speedX) {
        this.vx = speedX;
        this.lastVx = speedX;
        this.ax = 0;
    }
    public void setSpeedY(double speedY){
        this.vy = speedY;
        this.lastVy = speedY;
        this.ay = 0;
    }

    /*public Vector getGravityForces(Particle o){
        double dist2 = dist2(this,o);
        double module = GRAVITY*mass*o.mass / (dist2);
        double dist = Math.sqrt(dist2);
        double ex = (o.x-x)/dist;
        double ey = (o.y - y)/dist;
        return new Vector(module*ex,module*ey);
    }*/

    public static double angle(Particle p, Particle o) {
        return Math.atan2(o.getY()-p.y,o.getX()-p.x);
    }

    /*public void updatePosition(Vector force,double dt) {

        double rx = 2*x - lastRx + (force.getX()/mass)*dt*dt;
        double ry = 2*y - lastRy + (force.getY()/mass)*dt*dt;
        double fm = Math.sqrt((force.x*force.x + force.y*force.y));
        f = new Vector(force.x / fm,force.y / fm);
        vx = (rx - lastRx) / (2*dt);
        vy = (ry - lastRy) / (2*dt);
        lastRx = x;
        nextX = rx;
        lastRy = y;
        nextY = ry;


    }*/

    public void setNewPositions(){
        lastRx = x;
        lastRy = y;
        x = nextX;
        y = nextY;

    }
    @Override
    public boolean equals(Object o) {
        if(o instanceof Particle){
            return id == ((Particle) o).id;
        }
        return false;
    }

    public void updatePosition(Vector f,double dt){
        vx+= f.x*dt;
        vy+= f.y*dt;

        x+= vx*dt;
        y+= vy*dt;
        if(Math.abs(x - goal.x) < radius && Math.abs(y - goal.y) < radius){
            onTarget = true;
        }
    }

    public void beeman(Vector v, double time){
        lastAx = ax;
        lastAy = ay;
        f = v;
        ax = v.x /mass;
        ay = v.y /mass;
        nextX = x + vx*time + (0.666667)*ax*time*time - (0.16666667)*lastAx*time*time;
        nextY = y + vy*time + (0.666667)*ay*time*time - (0.16666667)*lastAy*time*time;

        lastVx = vx;
        lastVy = vy;
        vx = vx + 1.5*ax*time -0.5*lastAx*time;
        vy = vy + 1.5*ay*time -0.5*lastAy*time;
    }
    public void beemanCorrection(Vector f,double dt){
        double newAx = f.x / mass;
        double newAy = f.y / mass;
        vx = lastVx + 0.333333*newAx*dt + 0.8333333*ax*dt - 0.16666667*lastAx*dt;
        vy = lastVy + 0.333333*newAy*dt + 0.8333333*ay*dt - 0.16666667*lastAy*dt;
    }

    public boolean isOnTarget() {
        return onTarget;
    }

    public Vector goalForce() {
       double dx = goal.x - x;
       double dy = goal.y - y;
       double dist = Math.sqrt(dx*dx + dy*dy);
       double nx = dx/dist;
       double ny = dy/dist;

       return new Vector((prefSpeed*nx - vx) / tau, (prefSpeed*ny - vy)/tau);

    }
    public void setGoal(Vector g){
        goal = g;
    }

    public double predict(Particle o){
        double dx = o.x - this.x;
        double dy = o.y - this.y;
        double dvx = o.getSpeedX() - this.getSpeedX();
        double dvy = o.getSpeedY() - this.getSpeedY();
        double dvdr = dvx*dx + dvy*dy;
        if(dvdr >= 0 ){
            return -1;
        }
        double dvdv = dvx*dvx + dvy*dvy;
        double drdr = dx*dx + dy*dy;
        double d = dvdr*dvdr - dvdv*(drdr - (privateSpace + o.radius)*(privateSpace + o.radius));
        if(d < 0 ){
            return -1;
        }

        return -(dvdr + Math.sqrt(d))/dvdv;
    }

    public Vector repulsionForce(Particle o,double time) {
        double cix = x + vx*time;
        double ciy = y + vy*time;
        double cjx = o.x + o.vx*time;
        double cjy = o.y + o.vy*time;

        double dx = cix - cjx;
        double dy = ciy - cjy ;
        double dist = Math.sqrt(dx*dx + dy*dy);
        double nx = dx / dist;
        double ny = dy / dist;

        double rx = cix - x;
        double ry = ciy - y;
        double d = Math.sqrt(rx*rx + ry*ry) + (dist - privateSpace - o.radius);
        return new Vector(nx*d,ny*d);
    }

    public void setPrivateSpace(int privateSpace) {
        this.privateSpace = privateSpace;
    }
}
