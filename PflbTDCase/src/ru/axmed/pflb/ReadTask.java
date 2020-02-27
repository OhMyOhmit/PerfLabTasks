package ru.axmed.pflb;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.TimerTask;

public class ReadTask extends TimerTask {
    int count = 1000;

    // run is a abstract method that defines task performed at scheduled time.
    public void run() {
        System.out.println("Read Cycle #" + count);
        count++;
		try {
	        ResultSet resultSet = PflbTDMain.statement.executeQuery("select * from inTable");

	        while (resultSet.next()) {
	        	int id = resultSet.getInt(1);
	        	Timestamp ts = resultSet.getTimestamp(2);
	        	String body = resultSet.getString(3);
	        	
	        	while(body.contains("${RAND a}")) {
	        		body = body.replace("${RAND a}", PflbTDMain.getRandomInteger());
	        	}
	        	
	        	while(body.contains("${RAND b}")) {
	        		body = body.replace("${RAND b}", PflbTDMain.getRandomCharArray());
	        	}
	        	
	        	while(body.contains("${RAND c}")) {
	        		body = body.replace("${RAND c}", PflbTDMain.getRandomFile());
	        	}
	        	
	        	while(body.contains("${RAND d}")) {
	        		body = body.replace("${RAND d}", ts + "");
	        	}
	        	
	        	System.out.println("Sending " + body);
	        	PreparedStatement ps = PflbTDMain.connection.prepareStatement("DELETE FROM inTable WHERE dtCreate = ?");
	        	ps.setTimestamp(1, ts);
	        	ps.execute();

	        	ps = PflbTDMain.connection.prepareStatement("insert into outTable (id, dtCreate, body) values (?, ?, ?)");
	        	ps.setInt(1, id);
	        	ps.setTimestamp(2, ts);
	        	ps.setString(3, body);
	        	ps.execute();
	        }
	        
	        //DEBUG INSERT!!!
	        if(count % 4 == 0) {
	        	PreparedStatement ps = PflbTDMain.connection.prepareStatement("insert into inTable (id, dtCreate, body) values (?, ?, ?)");
	        	ps.setInt(1, count);
	        	ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
	        	ps.setString(3, "BLABLABLA ${RAND a} + ${RAND b} + ${RAND c} + ${RAND d} KEK");
	        	ps.execute();
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}
        
    }
}
