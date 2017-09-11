package ent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.h2.jdbc.*;
import org.sqlite.JDBC;

import dao.LiteConnect;
import forms.LogForm;
import repositories.ObjectRepository;

public class MoveH2 {

	
	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		  Properties prop=new Properties();
	      //  Connection conn = null;
	        List<LogForm> ret=new ArrayList<>();
        Class.forName("org.h2.Driver");
        Connection conn = DriverManager.
            getConnection("jdbc:h2:file:C:/data/sample", "sa", "");
		
		System.out.println("Go!");
		List<forms.LogForm> logs = LiteConnect.getLogs();
		PreparedStatement insert = conn.prepareStatement("insert into logs values (?,?,?,?,?,?)");
	   final String query="select * from logs ";
	   try (Connection lconn = JDBC.createConnection("jdbc:sqlite:log.db", prop);
	             PreparedStatement pstmt = lconn.prepareStatement(query);
	             ResultSet rs = pstmt.executeQuery();
	        ){
	            while(rs.next()){
	                int id=rs.getInt("id");
	                long date=rs.getLong("date");
	                String text = rs.getString("text");
	                int brig_id=rs.getInt("brigade_id");
	                int place_id=rs.getInt("place_id");
	                int pobject_id=rs.getInt("pobject_id");
	                insert.setInt(1, id);
	                insert.setLong(2, date);
	                insert.setString(3, text);
	                insert.setInt(4, brig_id);
	                insert.setInt(5, place_id);
	                insert.setInt(6, pobject_id);
	                insert.executeUpdate();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
//		for( ent.Log log :logs) {
//			//System.out.println(po.getName());
//			short id=(short) log.id;
//			String text=log.getText();
//		
//			//String name=pl.name;
////			List<ent.PObject> pobjcts = LiteConnect.getObjForPlace(id);
////			for(ent.PObject po: pobjcts) {
////				insert.setInt(1, id);
////				insert.setInt(2, po.id);
////				insert.executeUpdate();
////			}
//			//orep.save(new PObject(id,po.getName()));
//		}
		insert.close();
//		PreparedStatement st = conn.prepareStatement("select * from change");
//		ResultSet rs = st.executeQuery();
//		while(rs.next()) {
//			System.out.println(rs.getString("name"));
//		}
//		rs.close();
//		st.close();
		conn.close();
		

	}

}
