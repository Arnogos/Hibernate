package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws SQLException {
        UserDao userDao = new UserDaoJDBCImpl();
        userDao.createUsersTable();
        userDao.saveUser("Poppy", "LoL", (byte) 24);
        userDao.saveUser("Warwick", "LoL", (byte) 33);
        userDao.saveUser("Kayn", "LoL", (byte) 20);
        userDao.saveUser("Rhaast", "LoL", (byte) 1000);
        userDao.getAllUsers();
        userDao.cleanUsersTable();
        userDao.dropUsersTable();

        }
    }

