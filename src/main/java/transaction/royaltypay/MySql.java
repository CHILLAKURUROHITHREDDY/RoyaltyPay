package transaction.royaltypay;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class MySql {

    Connection connection;
    void setConnection(){

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/royaltypay", "root", "MySqlTable@1");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    void setDisconnection(){

        try{
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    void write(String rowValues){

        try{
            setConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(rowValues);
            statement.close();
            setDisconnection();
        }catch (SQLException ignored){
        }
    }

    String valueQuery(String query, String column){

        String result = "0";
        try{

            setConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                result = resultSet.getString(column);
            }
            resultSet.close();
            statement.close();
            setDisconnection();
        }catch (SQLException ignored){
        }

        return result;
    }
}
