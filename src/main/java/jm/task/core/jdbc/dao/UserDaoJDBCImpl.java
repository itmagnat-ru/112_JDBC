package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {
    //todo по условиям задачи должен быть пустой конструктор
    }
    @Override
    public void createUsersTable() {
        final String CREATE = "CREATE TABLE `users` (\n" +
                "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                "  `name` VARCHAR(45) NULL,\n" +
                "  `lastName` VARCHAR(45) NULL,\n" +
                "  `age` INT NULL,\n" +
                "        PRIMARY KEY (`id`))";
        try (PreparedStatement ps = Util.getConnection().prepareStatement(CREATE)){
            ps.execute();
            System.out.println("Таблица создана");
        } catch (SQLException ex) {
            System.out.println("Ошибка при создании таблицы" + ex);
        }
    }
    @Override
    public void dropUsersTable() {
        try (PreparedStatement ps = Util.getConnection().prepareStatement("DROP TABLE IF EXISTS users")){
            ps.execute();
            System.out.println("Таблица удалена");
        } catch (SQLException ex) {
            System.out.println("Ошибка при удалении таблицы" + ex);
        }
    }
    @Override
    public void saveUser(String name, String lastName, byte age) {
        final String ADD = "INSERT INTO users (name, lastName, age) values (?, ?, ?)";
        try (PreparedStatement ps = Util.getConnection().prepareStatement(ADD)) {
            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setByte(3, age);
            ps.executeUpdate();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException ex) {
            System.out.println("Ошибка сохранения пользователя" + ex);
        }

    }
    @Override
    public void removeUserById(long id) {
        final String DELETE_BY_ID = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement ps = Util.getConnection().prepareStatement(DELETE_BY_ID)){
            ps.setDouble(1,id);
            ps.executeUpdate();
            System.out.println("Пользователь с id=" + id + " удалён из БД");
        } catch (SQLException e) {
            System.out.println("Ошибка удаления пользователя из БД" + e.getMessage());
        }
    }
    @Override
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
    @Override
    public void cleanUsersTable() {
        try (PreparedStatement ps = Util.getConnection().prepareStatement("DELETE FROM users")){
            ps.execute();
            System.out.println("Таблица очищена");
        } catch (SQLException ex) {
            System.out.println("Ошибка очистки таблицы" + ex);
        }
    }
}
