package perflab.tasks;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class TaskThree {

	public static void main(String[] args) throws IOException {
		String path = args[0];
		float maxAvg = 0;
		int maxTime = 0;
		float[] avgs = new float[16];
		for(int i = 0; i < 5; i++) {
			File file = new File(path, "Cash" + (i+1) + ".txt");
			BufferedReader br = new BufferedReader(new FileReader(file));
			String st;
			int j = 0;
			while((st = br.readLine()) != null) {
				avgs[j] += Float.valueOf(st.replace("\\n", ""));
				if(avgs[j] > maxAvg) {
					maxAvg = avgs[j];
					maxTime = j;
				}
				j++;
			}
			br.close();
		}
		System.out.println((maxTime+1));
	}
}
