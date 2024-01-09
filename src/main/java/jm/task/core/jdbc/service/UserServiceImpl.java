package jm.task.core.jdbc.service;

import jm.task.core.jdbc.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getConnection;

    public class UserServiceImpl implements UserService {
        User user = new User();
        Connection connection = getConnection();
@Override
        public void createUsersTable() throws SQLException {
            String sql = "CREATE TABLE IF NOT EXISTS `sys`.`users` (\n" +
                    "  `id` INT NOT NULL,\n" +
                    "  `name` VARCHAR(45) NOT NULL,\n" +
                    "  `lastname` VARCHAR(45) NOT NULL,\n" +
                    "  `age` INT NOT NULL,\n" +
                    "  PRIMARY KEY (`id`));";
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

@Override
        public void dropUsersTable() throws SQLException {
            String sql = "DROP TABLE IF EXISTS users";
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void saveUser(String name, String lastName, byte age) throws SQLException {

            String sql = "INSERT INTO users (id, name, lastname, age) VALUES(?,?,?,?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                preparedStatement.setLong(1, user.getId());
                preparedStatement.setString(2, user.getName());
                preparedStatement.setString(3, user.getLastName());
                preparedStatement.setByte(4, user.getAge());

                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }

@Override
        public void removeUserById(long id) throws SQLException {

            String sql = "DELETE FROM users WHERE ID=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                preparedStatement.setLong(1, user.getId());

                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
@Override
        public List<User> getAllUsers() throws SQLException {
            List<User> userList = new ArrayList<>();
            String sql = "SELECT id, name, lastname, age FROM users";
            try (Statement statement = connection.createStatement()) {

                ResultSet resultSet = statement.executeQuery(sql);

                while (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getLong("id"));
                    user.setName(resultSet.getString("name"));
                    user.setName(resultSet.getString("lastname"));
                    user.setAge(resultSet.getByte("age"));

                    userList.add(user);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return userList;
        }
@Override
        public void cleanUsersTable() throws SQLException {
            String sql = "DELETE FROM users";
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }
                }
            }


