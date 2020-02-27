package ru.axmed.pflb;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.Timer;

public class PflbTDMain {
	private static final int randIntFrom = 10;
	private static final int randIntTo = 50;
	private static final int randStringLength = 10;
	
	static Connection connection;
	static Statement statement;
	
	public static void main(String[] args) {
		 try {
		        Class.forName("oracle.jdbc.driver.OracleDriver");
		        System.out.println("Driver loaded");

		        String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:XE";
		        
		        connection = DriverManager.getConnection(jdbcUrl, "system", "admin");
		        System.out.println("Database connected");

		        statement = connection.createStatement();

//		        ResultSet resultSet = statement.executeQuery("CREATE TABLE outTable (id number(10) PRIMARY KEY, dtCreate timestamp, body varchar2(255))");

		        Timer timer = new Timer();
		        ReadTask task = new ReadTask();
		        timer.scheduleAtFixedRate(task, 500l, 5000l);

//		        connection.close();
		    } catch (ClassNotFoundException|SQLException e) {
		        System.out.println("Database Access Error.");
		        e.printStackTrace();
		    }
	}
	
	public static String getRandomInteger() {
    	Random r = new Random();
    	return (randIntFrom + r.nextInt(randIntTo)) + "";
	}
	
	public static String getRandomCharArray() {
		byte[] array = new byte[randStringLength];
	    new Random().nextBytes(array);
	    String generatedString = new String(array, Charset.forName("UTF-8"));
	 
    	return generatedString;
	}
	
	public static String getRandomFile() {
		String output = "";
		try {
			File dir = new File("C:\\Users\\ΐυμεδ\\Documents\\pflbTDInput");
			File[] files = dir.listFiles();

			Random rand = new Random();
			File file = files[rand.nextInt(files.length)];
			BufferedReader br = new BufferedReader(new FileReader(file));
			int size = (int) Files.lines(file.toPath()).count();
			
			for(int i = 0; i < size; i++)
				output += br.readLine();
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    	return output;
	}
	
}
