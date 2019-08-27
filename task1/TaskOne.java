package perflab.tasks;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.util.Arrays;

public class TaskOne {

	public static void main(String[] args) throws IOException {
		String path = args[0];
		File f = new File(path);
		BufferedReader br = new BufferedReader(new FileReader(f));
		int size = (int) Files.lines(f.toPath()).count(); //Количество строк в файле
		
		short[] array = new short[size];
		float percentile, median, avg, max;
		float min = max = array[0] = Short.valueOf(br.readLine()); //Задаю значение 0-ой строки, чтобы было с чем сравнивать в дальнейшем.
		int sum = array[0]; //Присваиваю значение 0-го элемента массива, чтобы не пропустить дальше, так как в цикле мы начинаем уже с 1-го
		
		String st;
		int i = 1; //Счётчик начинаем с 1, так как 0-ую строку мы уже получили;
		while((st = br.readLine()) != null) {
			array[i] = Short.valueOf(st);
			min = (short) Math.min(min, array[i]);
			max = (short) Math.max(max, array[i]);
			sum += array[i];
			i++;
		}
		br.close(); //Закрываем BufferedReader
		
		avg = (float)sum/array.length;
		Arrays.sort(array);
		median = median(array);
		percentile = percentile(array);
		BigDecimal bd = BigDecimal.valueOf(percentile);
		bd = bd.setScale(2, RoundingMode.HALF_UP); //Округление до сотых
	    
		System.out.println(bd.doubleValue());
		System.out.println(median);
		System.out.println(max);
		System.out.println(min);
		System.out.println(avg);
	}
	
	private static float percentile(short[] array) {
		int l = array.length;
		float p = (l - 1) * 0.9f + 1;
	    
		if (p == 1f) 
			return array[0];  //Такое может случиться, если в массиве всего один элемент
	    else {
			int i = (int) p;
			float d = p - i;
			return array[i - 1] + d * (array[i] - array[i - 1]);
	    }
	}
	
	private static float median(short[] array) {
		if(array.length % 2 == 0) {
			return (array[array.length/2] + array[array.length/2-1])/2;
		} else {
			return array[(array.length-1)/2];
		}
	}
}
