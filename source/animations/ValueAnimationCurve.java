package animations;

public class ValueAnimationCurve {
	public final ValueAnimationPoint point1;
	public final ValueAnimationPoint point2;
	private float a, b, c, d;
	
	public ValueAnimationCurve(ValueAnimationPoint point1, ValueAnimationPoint point2){
		if(point1.position.x<point2.position.x){
			this.point1 = point1;
			this.point2 = point2;
		}else
		{
			this.point2 = point1;
			this.point1 = point2;
		}
		update();
	}
	public void update(){
		float x = point2.position.x-point1.position.x;
		float x2 = (float) Math.pow(x, 2);
		float x3 = (float) Math.pow(x, 3);
		float y1 = point1.position.y;
		float y2 = point2.position.y;
		float m1 = point1.pitch;
		float m2 = point2.pitch;
		d = y1;
		c = m1;
		b = -(3*y1+(m2+2*m1)*x-3*y2)/x2;
		a = (2*y1+(m2+m1)*x-2*y2)/x3;

	}
	public float getHeight(float x){
		x-=point1.position.x;
		return (float)(a*Math.pow(x, 3)+b*Math.pow(x, 2)+c*x+d);
	}
	public boolean isInRange(float x){
		return point1.position.x<=x&&point2.position.x>=x;
	}

}
