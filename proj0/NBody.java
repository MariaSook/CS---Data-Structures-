public class NBody{

  public static double readRadius(String args){
    In dataplanets = new In("./data/planets.txt");

    int count = 0;
    double radius = 0;
    while (count < 2){
      radius = dataplanets.readDouble();
      count += 1;
    }
    return radius;
  }

  public static Planet[] readPlanets(String args){

    In dataplanets = new In("./data/planets.txt");

    int planetnum = dataplanets.readInt();
    double radius = dataplanets.readDouble();

    Planet[] planets = new Planet[planetnum];

    for (int i = 0; i < planetnum; i++) {
      double xcoor = dataplanets.readDouble();
      double ycoor = dataplanets.readDouble();
      double xvel = dataplanets.readDouble();
      double yvel = dataplanets.readDouble();
      double mass = dataplanets.readDouble();
      String img = dataplanets.readString();

       planets[i] = new Planet(xcoor, ycoor, xvel, yvel, mass, img);
     }
     return planets;

  }

  public static void main(String[] args){
    double T = Double.parseDouble(args[0]);
    double dt = Double.parseDouble(args[1]);
    String filename = args[2];
    double radius = readRadius(filename);
    Planet[] planetarray = readPlanets(filename);
    StdDraw.setScale(-radius, radius);
    StdDraw.picture(0,0, "images/starfield.jpg");

    for (Planet planet: planetarray) {
      planet.draw();
      }

    StdDraw.enableDoubleBuffering();
  	double time = 0;
    double[] xForces = new double[planetarray.length];
    double[] yForces = new double[planetarray.length];

    while (time < T) {
  		for (int i = 0; i < planetarray.length; i++) {
  			xForces[i] = planetarray[i].calcNetForceExertedByX(planetarray);
  			yForces[i] = planetarray[i].calcNetForceExertedByX(planetarray);
  			}

      for (int i = 0; i < planetarray.length; i++) {
  			planetarray[i].update(dt, xForces[i], yForces[i]);
  			}
      StdDraw.picture(0, 0, "images/starfield.jpg");
      for (int i = 0; i < planetarray.length; i++) {
  			planetarray[i].draw();
  			}
      StdDraw.show();
      StdDraw.pause(10);
      time += dt;
  		}

      StdOut.printf("%d\n", planets.length);
      StdOut.printf("%.2e\n", radius);
      for (int i = 0; i < planets.length; i++) {
        StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                   planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                   planets[i].yyVel, planets[i].mass, planets[i].imgFileName);
 }

     }




}
