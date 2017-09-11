package dao;
//import ent.PObject;
//import ent.Place;
import forms.LogForm;

import org.sqlite.JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.sql.SQLException;

/**
 * Created by Popov on 22.08.2017.
 */
public class LiteConnect {
    public static void main(String[] args) throws SQLException{
        Properties prop=new Properties();
        System.out.println("Go");
        Connection conn = JDBC.createConnection("jdbc:sqlite:log.db", prop);
        PreparedStatement pstmt = conn.prepareStatement("SELECT * from logs");
        ResultSet rs = pstmt.executeQuery();
        while(rs.next()){
            System.out.println(rs.getString("TEXT"));
        }


    }

    public static List<LogForm> getLogs(){
        Properties prop=new Properties();
      //  Connection conn = null;
        List<LogForm> ret=new ArrayList<>();

        final String query="select logs.id,text as 'Текст',brigade.name as 'Бригада',places.NAME as 'Место',objects.NAME as 'Объект' from logs inner join brigade on logs.BRIGADE_ID=brigade.id \n" +
                "inner join places on places.ID=logs.PLACE_ID\n" +
                "inner join objects on objects.ID=logs.POBJECT_ID";

        try (Connection conn = JDBC.createConnection("jdbc:sqlite:log.db", prop);
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery();
        ){
            while(rs.next()){
                int id=rs.getInt("id");
                String brig=rs.getString("Бригада");
                String text = rs.getString("Текст");
                String place=rs.getString("Место");
                String obj=rs.getString("Объект");
                ret.add(new LogForm(id,text,brig,place,obj));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ret;

    }
/*
    public static List<Place> getPlaces(){
        Properties prop=new Properties();
        //  Connection conn = null;
        List<Place> ret=new ArrayList<>();

        final String query="select * from places";
        try (Connection conn = JDBC.createConnection("jdbc:sqlite:log.db", prop);
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery();
        ){
            while(rs.next()){
                int id=rs.getInt("ID");
                String name=rs.getString("NAME");
                final Place place=new Place(id,name);
                List<PObject> objects = getObjForPlace(place.getId());
                place.setObjects(objects);
                ret.add(place);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ret;

    }
    public static List<PObject> getObjects(){
        Properties prop=new Properties();
        //  Connection conn = null;
        List<PObject> ret=new ArrayList<>();

        final String query="select * from objects";
        try (Connection conn = JDBC.createConnection("jdbc:sqlite:log.db", prop);
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery();
        ){
            while(rs.next()){
                int id=rs.getInt("ID");
                String name=rs.getString("NAME");

                ret.add(new PObject(id,name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ret;

    }

    public static List<PObject> getObjForPlace(int oid){
        Properties prop=new Properties();
        //  Connection conn = null;
        List<PObject> ret=new ArrayList<>();

        final String query="select places_objects.pobjectlist_ID as ID,objects.NAME \n" +
                "from places_objects join objects\n" +
                "on places_objects.pobjectlist_ID=objects.ID\n" +
                "where Place_ID=?";
        try (Connection conn = JDBC.createConnection("jdbc:sqlite:log.db", prop);

        ){
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1,oid);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()){
                int id=rs.getInt("ID");
                String name=rs.getString("NAME");

                ret.add(new PObject(id,name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ret;
    }
    */
}
