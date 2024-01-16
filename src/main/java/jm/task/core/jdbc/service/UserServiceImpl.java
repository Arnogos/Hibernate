package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getConnection;

public class UserServiceImpl implements UserService {
    UserDao userDaoJDBC = new UserDaoJDBCImpl();

    @Override
    public void createUsersTable() {
        userDaoJDBC.createUsersTable();
    }

    @Override
    public void dropUsersTable() {
        userDaoJDBC.dropUsersTable();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) throws SQLException {
        userDaoJDBC.saveUser(name, lastName, age);

    }

    @Override
    public void removeUserById(long id) {

        userDaoJDBC.removeUserById(id);
    }

    @Override
    public List<User> getAllUsers() {
       return userDaoJDBC.getAllUsers();
    }

    @Override
    public void cleanUsersTable() {
        userDaoJDBC.cleanUsersTable();
    }
}


