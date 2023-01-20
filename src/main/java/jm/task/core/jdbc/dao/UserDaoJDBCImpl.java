package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Statement statement = Util.getConnection().createStatement()){
            String sql = "CREATE TABLE `users` (\n" +
                    "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `name` VARCHAR(45) NULL,\n" +
                    "  `lastName` VARCHAR(45) NULL,\n" +
                    "  `age` INT NULL,\n" +
                    "        PRIMARY KEY (`id`))";
            statement.execute(sql);
            System.out.println("Таблица создана");
        } catch (SQLException ex) {
            System.out.println("Ошибка при создании таблицы" + ex);
        }
    }

    public void dropUsersTable() {
        try (Statement statement = Util.getConnection().createStatement()){
            String sql = "DROP TABLE users";
            statement.executeUpdate(sql);
            System.out.println("Таблица удалена");
        } catch (SQLException ex) {
            System.out.println("Ошибка при удалении таблицы" + ex);
        }
    }

    public void saveUser(String name, String lastName, byte age) {

        try (Statement statement = Util.getConnection().createStatement()) {
            String sql = "INSERT INTO users (name, lastName, age) " +
                    "values ('"
                    + name
                    + "','"
                    + lastName
                    + "',"
                    + age
                    + ")";
            statement.executeUpdate(sql);
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException ex) {
            System.out.println("Ошибка сохранения пользователя" + ex);
        }

    }

    public void removeUserById(long id) {
        try (Statement statement = Util.getConnection().createStatement()){
            String sql = "DELETE FROM users WHERE id=" + id;
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("Ошибка удаления пользователя из БД" + e.getMessage());
        }
    }

    public List<User> getAllUsers() {
        String sql = "SELECT * FROM users";
        try (Statement statement = Util.getConnection().createStatement()){
            List<User> users = new ArrayList<>();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                //users.add(new User(rs.getString("name"), rs.getString("lastName"), Byte.parseByte(rs.getString("age")));
                int userId = rs.getInt("id");
                String userName = rs.getString("name");
                String userLastName = rs.getString("lastName");
                byte userAge = Byte.parseByte(rs.getString("age"));
                System.out.println("--------------------");
                System.out.println("User ID:" + userId);
                System.out.println("User name:" + userName);
                System.out.println("User lastName:" + userLastName);
                System.out.println("User age:" + userAge);
                users.add(new User(userName, userLastName, userAge));
            }
            return users;
        } catch (SQLException ex) {
        return null;
        }
    }

    public void cleanUsersTable() {
        try (Statement statement = Util.getConnection().createStatement()){
            String sql = "DELETE FROM users";
            statement.executeUpdate(sql);
        } catch (SQLException ex) {
            System.out.println("Ошибка очистки таблицы" + ex);
        }
    }
}
