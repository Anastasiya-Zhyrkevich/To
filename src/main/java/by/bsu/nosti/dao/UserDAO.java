package by.bsu.nosti.dao;

import by.bsu.nosti.entity.User;
import by.bsu.nosti.exception.DAOException;

public interface UserDAO extends BaseDAO<Integer, User> {
    User getUser(String login, String password) throws DAOException;
    User getUser(String login) throws DAOException;
}
