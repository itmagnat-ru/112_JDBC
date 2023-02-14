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
    }

    @Override
    public void createUsersTable() {
        try (Statement st = Util.getConnection().createStatement()) {
            st.executeUpdate(
                    "CREATE TABLE `users` (\n" +
                            "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                            "  `name` VARCHAR(45) NULL,\n" +
                            "  `lastName` VARCHAR(45) NULL,\n" +
                            "  `age` INT NULL,\n" +
                            "        PRIMARY KEY (`id`))"
            );
            System.out.println("Таблица создана");
        } catch (SQLException ex) {
            System.out.println("Ошибка при создании таблицы" + ex);
        }
    }

    @Override
    public void dropUsersTable() {
        try (Statement st = Util.getConnection().createStatement()) {
            st.executeUpdate("DROP TABLE IF EXISTS users");
            System.out.println("Таблица удалена");
        } catch (SQLException ex) {
            System.out.println("Ошибка при удалении таблицы" + ex);
        }
    }

    // Вопрос: правильно ли я понимаю, что при таком подходе (без статических переменных с запросами)
    // созданные нами String объекты запросов будут висеть в пуле строк,
    // и сколько бы раз мы не вызвали метод, новый объект типа String создан не будет,
    // а будет извлечён из пула строк?
    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement ps = Util.getConnection().prepareStatement("INSERT INTO users (name, lastName, age) values (?, ?, ?)")) {
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
        try (PreparedStatement ps = Util.getConnection().prepareStatement("DELETE FROM users WHERE id = ?")) {
            ps.setDouble(1, id);
            ps.executeUpdate();
            System.out.println("Пользователь с id=" + id + " удалён из БД");
        } catch (SQLException e) {
            System.out.println("Ошибка удаления пользователя из БД" + e.getMessage());
        }
    }

    @Override
    public List<User> getAllUsers() throws SQLException {
        try (Statement statement = Util.getConnection().createStatement()) {
            List<User> users = new ArrayList<>();
            ResultSet rs = statement.executeQuery("SELECT * FROM users");
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
            System.out.println("Ошибка получения получения полного списка пользователей");
            ex.printStackTrace();
            throw ex;
        }
    }


    @Override
    public void cleanUsersTable() {
        try (PreparedStatement ps = Util.getConnection().prepareStatement("DELETE FROM users")) {
            ps.execute();
            System.out.println("Таблица очищена");
        } catch (SQLException ex) {
            System.out.println("Ошибка очистки таблицы" + ex);
        }
    }
}
