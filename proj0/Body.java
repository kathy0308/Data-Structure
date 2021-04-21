public class Body {
	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;
	public String imgFileName;
	private static final double g = 6.67E-11;

	public Body(double xP, double yP, double xV, double yV, double m, String img) {
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img;
	}

	public Body(Body b) {
		xxPos = b.xxPos;
		yyPos = b.yyPos;
		xxVel = b.xxVel;
		yyVel = b.yyVel;
		mass = b.mass;
		imgFileName = b.imgFileName;
	}

	public double calcDistance(Body b) {
		return Math.sqrt(Math.pow(b.xxPos - this.xxPos, 2) + Math.pow(b.yyPos - this.yyPos, 2));
	}

	public double calcForceExertedBy(Body b) {
		if (this.equals(b)){
						return 0;
		    }
				return g * this.mass * b.mass / Math.pow(calcDistance(b), 2);
	}

	public double calcForceExertedByX(Body b) {
		if (this.equals(b)){
						return 0;
		    }
        return calcForceExertedBy(b) * (b.xxPos - this.xxPos) / calcDistance(b);
  }

	public double calcForceExertedByY(Body b) {
		if (this.equals(b)){
						return 0;
		    }
        return calcForceExertedBy(b) * (b.yyPos - this.yyPos) / calcDistance(b);
  }

	public double calcNetForceExertedByX(Body[] bodyArray) {
		double netForceX = 0;
		for (Body b : bodyArray) {
				if (!this.equals(b)) {
						netForceX += this.calcForceExertedByX(b);
				}
		}
		return netForceX;
	}

	public double calcNetForceExertedByY(Body[] bodyArray) {
		double netForceY = 0;
        for (Body b : bodyArray) {
            if (!this.equals(b)) {
                netForceY += this.calcForceExertedByY(b);
            }
        }
        return netForceY;
	}

	public void update(double dt, double fX, double fY) {
        double ax = fX / this.mass;
        double ay = fY / this.mass;
        this.xxVel += ax * dt;
        this.yyVel += ay * dt;
        this.xxPos += this.xxVel * dt;
        this.yyPos += this.yyVel * dt;
    }
		public void draw() {
		StdDraw.picture(xxPos, yyPos, "images/" + imgFileName);
	}


}
