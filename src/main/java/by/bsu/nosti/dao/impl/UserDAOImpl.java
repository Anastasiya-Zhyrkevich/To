package by.bsu.nosti.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import by.bsu.nosti.dao.UserDAO;
import by.bsu.nosti.entity.User;
import by.bsu.nosti.entity.UserRole;
import by.bsu.nosti.exception.DAOException;

public class UserDAOImpl implements UserDAO {

	private static final String SQL_CREATE_USER = "INSERT INTO USER(LOGIN, EMAIL, PASSWORD, ROLE, K) VALUES(?,?,?,?,?)";
	private static final String SQL_SELECT_USER_BY_ID = "SELECT LOGIN, EMAIL, PASSWORD, ROLE, K FROM USER WHERE ID=?";
	private static final String SQL_SELECT_USER_BY_LOGIN_AND_PASSWORD = "SELECT ID, EMAIL, ROLE, K FROM USER WHERE LOGIN=? AND PASSWORD=?";
	private static final String SQL_SELECT_USER_BY_LOGIN = "SELECT ID, EMAIL, PASSWORD, ROLE, K FROM USER WHERE LOGIN=?";
	private static final String SQL_SELECT_ALL_USERS = "SELECT ID, LOGIN, EMAIL, PASSWORD, ROLE, K FROM USER";
	private static final String SQL_UPDATE_USER = "UPDATE USER SET LOGIN=?, EMAIL=?, PASSWORD=?, ROLE=? WHERE ID=?";
	private static final String SQL_DELETE_USER = "DELETE FROM USER WHERE ID=?";
	private static final String SQL_SET_PUBLICKEY_BY_ID = "UPDATE USER SET PUBLICKEY=? where ID=?";

	private static final String USER_ID = "ID";
	private static final String LOGIN = "LOGIN";
	private static final String PASSWORD = "PASSWORD";
	private static final String EMAIL = "EMAIL";
	private static final String USER_ROLE = "ROLE";
	private static final String K = "K";
	

	@Autowired
	private DataSource dataSource;

	@Override
	public void create(User user) throws DAOException {
		try (Connection con = dataSource.getConnection();
				PreparedStatement ps = con.prepareStatement(SQL_CREATE_USER);) {
			ps.setString(1, user.getLogin());
			ps.setString(2, user.getEmail());
			ps.setString(3, user.getPassword());
			ps.setInt(4, user.getRole());
			ps.setString(5, user.getK());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("Error while insert new user. " + e.getMessage(), e);
		}
	}

	@Override
	public User retrieve(Integer userId) throws DAOException {
		User user = null;

		try (Connection con = dataSource.getConnection();
				PreparedStatement ps = con.prepareStatement(SQL_SELECT_USER_BY_ID);) {
			ps.setInt(1, userId);
			try (ResultSet rs = ps.executeQuery();) {
				while (rs.next()) {
					String login = rs.getString(LOGIN);
					String email = rs.getString(EMAIL);
					String password = rs.getString(PASSWORD);
					int role = rs.getInt(USER_ROLE);
					String k = rs.getString(K);
					user = new User(userId, login, email, password, role, k);
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Error while retrieve user from db. " + e.getMessage(), e);
		}
		return user;
	}

	@Override
	public List<User> retrieveAll() throws DAOException {
		List<User> users = new ArrayList<>();
		User user = null;

		try (Connection con = dataSource.getConnection();
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(SQL_SELECT_ALL_USERS);) {
			while (rs.next()) {
				int userId = rs.getInt(USER_ID);
				String login = rs.getString(LOGIN);
				String email = rs.getString(EMAIL);
				String password = rs.getString(PASSWORD);
				int role = rs.getInt(USER_ROLE);
				String k = rs.getString(K);
				if (UserRole.User.ordinal() == role) {
					user = new User(userId, login, email, password, role, k);
					users.add(user);
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Error while retrive list of users from db. " + e.getMessage(), e);
		}

		return users;
	}

	@Override
	public void update(User user) throws DAOException {
		try (Connection con = dataSource.getConnection();
				PreparedStatement ps = con.prepareStatement(SQL_UPDATE_USER);) {
			ps.setString(1, user.getLogin());
			ps.setString(2, user.getEmail());
			ps.setString(3, user.getPassword());
			ps.setInt(4, user.getRole());
			ps.setInt(5, user.getUserId());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("Error while update user. " + e.getMessage(), e);
		}
	}

	@Override
	public void delete(Integer userId) throws DAOException {
		try (Connection con = dataSource.getConnection();
				PreparedStatement ps = con.prepareStatement(SQL_DELETE_USER);) {
			ps.setInt(1, userId);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("Error while delete user. " + e.getMessage(), e);
		}
	}

	@Override
	public User getUser(String login, String password) throws DAOException {
		User user = null;

		try (Connection con = dataSource.getConnection();
				PreparedStatement ps = con.prepareStatement(SQL_SELECT_USER_BY_LOGIN_AND_PASSWORD);) {
			ps.setString(1, login);
			ps.setString(2, password);
			try (ResultSet rs = ps.executeQuery();) {
				while (rs.next()) {
					int userId = rs.getInt(USER_ID);
					String email = rs.getString(EMAIL);
					int role = rs.getInt(USER_ROLE);
					String k = rs.getString(K);
					user = new User(userId, login, email, password, role, k);
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Error while retrieve user from db. " + e.getMessage(), e);
		}
		return user;
	}

	@Override
	public User getUser(String login) throws DAOException {
		User user = null;

		try (Connection con = dataSource.getConnection();
				PreparedStatement ps = con.prepareStatement(SQL_SELECT_USER_BY_LOGIN);) {
			ps.setString(1, login);
			try (ResultSet rs = ps.executeQuery();) {
				while (rs.next()) {
					int userId = rs.getInt(USER_ID);
					String email = rs.getString(EMAIL);
					String password = rs.getString(PASSWORD);
					int role = rs.getInt(USER_ROLE);
					String k = rs.getString(K);
					user = new User(userId, login, email, password, role, k);
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Error while retrieve user from db. " + e.getMessage(), e);
		}
		return user;
	}
	
	
	public User addPublicKey(User user, String publicKey) throws DAOException {

		try (Connection con = dataSource.getConnection();
				PreparedStatement ps = con.prepareStatement(SQL_SET_PUBLICKEY_BY_ID);) {
			ps.setString(1, publicKey);
			ps.setInt(2, user.getUserId());
			ps.executeUpdate();
			
		} catch (SQLException e) {
			throw new DAOException("Error updating User Public Key on the serevr side. " + e.getMessage(), e);
		}
		return user;
	}

	
	
}
