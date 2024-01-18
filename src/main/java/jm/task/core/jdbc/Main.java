package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws SQLException {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Poppy", "LoL", (byte) 24);
        userService.saveUser("Warwick", "LoL", (byte) 33);
        userService.saveUser("Kayn", "LoL", (byte) 20);
        userService.saveUser("Rhaast", "LoL", (byte) 1000);
        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();

        }
    }

