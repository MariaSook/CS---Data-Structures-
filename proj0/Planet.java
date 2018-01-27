public class Planet {

	private double xxPos;
	private double yyPos;
	private double xxVel;
	private double yyVel;
	private double mass;
	private String imgFileName;

	public Planet(double xP, double yP, double xV,
	double yV, double m, String img) {
		this.xxPos = xP;
		this.yyPos = yP;
		this.xxVel = xV;
		this.yyVel = yV;
		this.mass = m;
		this.imgFileName = img;
	}

	public Planet(Planet p) {
	xxPos = p.xxPos;
	yyPos = p.yyPos;
	xxVel = p.xxVel;
	yyVel = p.yyVel;
	mass = p.mass;
	imgFileName = p.imgFileName;
	}

	public double calcDistance(Planet p) {
		return Math.sqrt(((p.xxPos - this.xxPos)*(p.xxPos - this.xxPos)) + ((p.yyPos - this.yyPos)*(p.yyPos - this.yyPos)));
	}

	public double calcForceExertedBy(Planet p) {
		double num = (6.67 * Math.pow(10, -11)) * this.mass * p.mass;
		double denom = calcDistance(p);
		return (num/(denom*denom));
	}

	public double calcForceExertedByX(Planet p) {
		double dx = (p.xxPos - this.xxPos);
		return (calcForceExertedBy(p)*dx)/calcDistance(p);
	}

	public double calcForceExertedByY(Planet p) {
		double dy = (p.yyPos - this.yyPos);
		return (calcForceExertedBy(p)*dy)/calcDistance(p);
	}

	public double calcNetForceExertedByX(Planet[] planets) {
		double netforce = 0;
		for (int i = 0; i < planets.length; i++) {
			if (this.equals(planets[i])) {
				continue;
			}
			else {
				netforce += calcForceExertedByX(planets[i]);
			}

		}
		return netforce;
	}

	public double calcNetForceExertedByY(Planet[] planets) {
		double netforce = 0;
		for (int i = 0; i < planets.length; i++) {
			if (this.equals(planets[i])) {
				continue;
			}
			else {
			netforce += calcForceExertedByY(planets[i]);
		}
		}
		return netforce;
	}

  public void update(double dt, double fX, double fY) {
    double accelx = fX/this.mass;
    double accely = fY/this.mass;

    this.xxVel = this.xxVel + (dt*accelx);
    this.yyVel = this.yyVel + (dt*accely);

    this.xxPos = this.xxPos + dt*this.xxVel;
    this.yyPos = this.yyPos + dt*this.yyVel;
  }

	public void draw(){
		StdDraw.picture(this.xxPos, this.yyPos, "images/" + this.imgFileName);
	}

}
