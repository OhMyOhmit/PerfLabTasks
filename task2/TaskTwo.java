package perflab.tasks;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class TaskTwo {

	public static void main(String[] args) throws IOException {
		String path1 = args[0];
		String path2 = args[1];
		File input1 = new File(path1);
		File input2 = new File(path2);
		BufferedReader br = new BufferedReader(new FileReader(input1));
		
		Point[] ps = new Point[4]; 
		
		String st;
		int i = 0;
		while((st = br.readLine()) != null) { //Не совсем понятный input. Строки и так разделены, но в конце все равно стоят \n. Читаю построчно.
			String[] coords = st.split(" "); //Делю по пробелам
			ps[i] = new Point(Float.valueOf(coords[0]), Float.valueOf(coords[1].replace("\\n", ""))); //replace("\\n", "") стоит на случай, если \n всё-таки считается
			i++;
		}
		br.close();
		
		br = new BufferedReader(new FileReader(input2));
		while((st = br.readLine()) != null) {
			String[] coords = st.split(" "); //Делю по пробелам
			float x = Float.valueOf(coords[0]);
			float y = Float.valueOf(coords[1].replace("\\n", ""));
			getPointPlacement(ps, x, y);
		}
		br.close();
	}
	
	private static final void getPointPlacement(Point[] ps, float x, float y) {
		for (int i = 0; i < ps.length; i++) {
			if (x == ps[i].getX() && y == ps[i].getY()) {
	        	System.out.print("0 - точка на одной из вершин\n");
	            return;
	        }
		}

		float r1 = (ps[0].getX() - x) * (ps[1].getY() - ps[0].getY()) - (ps[1].getX() - ps[0].getX()) * (ps[0].getY() - y);
		float r2 = (ps[1].getX() - x) * (ps[2].getY() - ps[1].getY()) - (ps[2].getX() - ps[1].getX()) * (ps[1].getY() - y);
	    float r3 = (ps[2].getX() - x) * (ps[3].getY() - ps[2].getY()) - (ps[3].getX() - ps[2].getX()) * (ps[2].getY() - y);
	    float r4 = (ps[3].getX() - x) * (ps[0].getY() - ps[3].getY()) - (ps[0].getX() - ps[3].getX()) * (ps[3].getY() - y);
	    if ((r1 == 0 && y > ps[0].getY() && y < ps[1].getY()) 
		|| ( r2 == 0 && x > ps[1].getX() && x < ps[2].getX()) 
		|| ( r3 == 0 && y > ps[3].getY() && y < ps[2].getY())
		|| ( r4 == 0 && x > ps[0].getX() && x < ps[3].getX())) {
            System.out.print("1 - точка на одной из сторон\n");
	    }
	    else if ((r1 > 0 && r2 > 0 && r3 > 0 && r4 > 0) || (r1 < 0 && r2 < 0 && r3 < 0 && r4 < 0)) {
	    	System.out.print("2 - точка внутри\n");
	    }
	    else {
	    	System.out.print("3 - точка снаружи\n");
	    }
	}
	
	private static final class Point {
		//в java.awt есть классы Point и Polygon, которые мы могли бы использовать, но там координаты типа int, а нам нужны float
		private float x;
		private float y;
		
		public Point(float x, float y) {
			this.x = x;
			this.y = y;
		}
		
		public float getX() {
			return x;
		}
		
		public float getY() {
			return y;
		}
	}
}
