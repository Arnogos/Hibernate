package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getConnection;

public class UserDaoJDBCImpl implements UserDao {

    //  U̶s̶e̶r̶ ̶u̶s̶e̶r̶ ̶=̶ ̶n̶e̶w̶ ̶U̶s̶e̶r̶(̶)̶;̶ - Не нужен здесь, т.к. юза ешь в одном методе.

    // Не было private static final, это должно быть единственное и служебное соединение.
    // Служебное чтобы не быть свойством объекта UserDaoJDBCImpl, т.к. по факту это утилите-класс
    // Приватное чтобы не иметь возможность его плодить.
    private static final Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {

    }

    /*

    UserDaoJDBCImpl implements UserDao - АУ методы должны быть с аннотацией @Override, ты же логику интерфейса реализуешь!!!!

    */

    @Override
    public void createUsersTable() {
        // AUTO_INCREMENT у id в запросе забыл!
        String sql = """
                CREATE TABLE IF NOT EXISTS `sys`.`users` (
                  `id` INT NOT NULL AUTO_INCREMENT,
                  `name` VARCHAR(45) NOT NULL,
                  `lastname` VARCHAR(45) NOT NULL,
                  `age` INT NOT NULL,
                  PRIMARY KEY (`id`));""";
        try (Statement statement = connection.createStatement()) {
            statement.execute(sql); // Почитай зачем нужен executeUpdate - тут нужен простой update
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE users"; // IF EXISTS - излишен
        try (Statement statement = connection.createStatement()) {
            statement.execute(sql); // Почитай зачем нужен executeUpdate - тут нужен простой execute
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) throws SQLException {
        //  ̶c̶o̶n̶n̶e̶c̶t̶i̶o̶n̶.̶s̶e̶t̶A̶u̶t̶o̶C̶o̶m̶m̶i̶t̶(̶f̶a̶l̶s̶e̶)̶;̶ - нахуя?)

        String sql = "INSERT INTO users (name, lastname, age) VALUES(?,?,?)"; // нам нахуй id вашш не нужён!
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // Передавать параметр, а не свойство конкретного объекта - нарушаешь принцип единственности ответственности SOLID
            // Индекс 1-го параметра начинается с 1 и начиная с той колонки, которую ты указал первую в запросе:
            // INSERT INTO users (name, lastname, age) VALUES(?,?,?) - тут name имеет индекс 1, а id в контексте запроса нет)
            //  ̶p̶r̶e̶p̶a̶r̶e̶d̶S̶t̶a̶t̶e̶m̶e̶n̶t̶.̶s̶e̶t̶L̶o̶n̶g̶(̶1̶,̶ ̶u̶s̶e̶r̶.̶g̶e̶t̶I̶d̶(̶)̶)̶;̶
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            // ЗАБЫВАЕШЬ БЛЯТЬ КОММИТЕТЬ СУКА!!!!!!!!!!!!
            // БЫСТРО В ЮТУБ: https://www.youtube.com/watch?v=NUfMe40y0BY&list=PL786bPIlqEjTblvnalTTxuH9kzMC6oOxR&index=16
            // Тайминг: 5:20
            connection.commit();

        } catch (SQLException e) {
            // Забыл ролбэкнуть при catch
            // БЫСТРО В ЮТУБ: https://www.youtube.com/watch?v=NUfMe40y0BY&list=PL786bPIlqEjTblvnalTTxuH9kzMC6oOxR&index=16
            try{
                connection.rollback();
            } catch (SQLException ex) {

            }
        }
        //  ̶c̶o̶n̶n̶e̶c̶t̶i̶o̶n̶.̶c̶o̶m̶m̶i̶t̶(̶)̶;̶ - коммиты делаем только в рамках try
    }

    @Override
    public void removeUserById(long id) {
        String sql = "DELETE FROM users WHERE ID=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, id); // Передавать параметр, а не свойство конкретного объекта - нарушаешь принцип единственности ответственности SOLID
            preparedStatement.executeUpdate();

            // ЗАБЫВАЕШЬ БЛЯТЬ КОММИТЕТЬ СУКА!!!!!!!!!!!!
            // БЫСТРО В ЮТУБ: https://www.youtube.com/watch?v=NUfMe40y0BY&list=PL786bPIlqEjTblvnalTTxuH9kzMC6oOxR&index=16
            // Тайминг: 5:20
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String sql = "SELECT * FROM users"; // Можно упростить - было: "SELECT id, name, lastname, age FROM users"
        try (Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                User user = new User();
                // Кинул 6 летний видос, вот лучше: https://www.youtube.com/watch?v=3AqWyO86f_U&list=PLIU76b8Cjem5qdMQLXiIwGLTLyUHkTqi2&index=6
                user.setId(resultSet.getLong(1));
                user.setName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                //  ̶u̶s̶e̶r̶.̶s̶e̶t̶N̶a̶m̶e̶(̶r̶e̶s̶u̶l̶t̶S̶e̶t̶.̶g̶e̶t̶S̶t̶r̶i̶n̶g̶(̶3̶)̶)̶;̶  - 2 setName - WTF????
                user.setAge(resultSet.getByte(4));

                userList.add(user);
            }

            // ЗАБЫВАЕШЬ БЛЯТЬ КОММИТЕТЬ СУКА!!!!!!!!!!!!
            // БЫСТРО В ЮТУБ: https://www.youtube.com/watch?v=NUfMe40y0BY&list=PL786bPIlqEjTblvnalTTxuH9kzMC6oOxR&index=16
            // Тайминг: 5:20
            connection.commit();

        } catch (SQLException e) {
            // Забыл ролбэкнуть при catch
            // БЫСТРО В ЮТУБ: https://www.youtube.com/watch?v=NUfMe40y0BY&list=PL786bPIlqEjTblvnalTTxuH9kzMC6oOxR&index=16
            try {
                connection.rollback();
            } catch (SQLException b){

            }
            e.printStackTrace();
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        String sql = "TRUNCATE TABLE users ";
        try (Statement statement = connection.createStatement()) {
            statement.execute(sql); // Почитай зачем нужен executeUpdate - тут нужен простой update
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
