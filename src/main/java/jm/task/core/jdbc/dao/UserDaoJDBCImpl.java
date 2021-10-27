package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static String SAVE_USER =
            "INSERT INTO my_db.my_first_table " +
                    "(name, last_name, age) VALUES (?, ?, ?)";

    private static String REMOVE_USER =
            "DELETE FROM my_db.my_first_table WHERE id = ?";

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        String CREATE_TABLE =
                "CREATE TABLE IF NOT EXISTS my_first_table(" +
                        "id INT PRIMARY KEY AUTO_INCREMENT," +
                        "name VARCHAR(45)," +
                        "last_name VARCHAR(45)," +
                        "age TINYINT(3))";
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(CREATE_TABLE);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void dropUsersTable() {
        String DROP_TABLE =
                "DROP TABLE IF EXISTS my_first_table";
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(DROP_TABLE);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(SAVE_USER)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.execute();
            System.out.println("User с именем – " + name
                    + " добавлен в базу данных");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(REMOVE_USER)) {
            preparedStatement.setInt(1, (int) id);
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        String GET_ALL =
                "SELECT * FROM my_db.my_first_table";
        List<User> listUsers = new ArrayList<>();

        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(GET_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                User user = new User();
                user.setId((long) resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setAge(resultSet.getByte("age"));
                listUsers.add(user);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return listUsers;
    }

    public void cleanUsersTable() {
        String CLEAR =
                "DELETE FROM my_db.my_first_table";
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(CLEAR);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
