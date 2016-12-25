package by.bsu.nosti.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import by.bsu.nosti.dao.UserResourceLinkDAO;
import by.bsu.nosti.entity.Permission;
import by.bsu.nosti.entity.UserResourceLink;
import by.bsu.nosti.exception.DAOException;

public class UserResourceLinkDAOImpl implements UserResourceLinkDAO {

	private static final String SQL_CREATE_USER_RESOURCE_LINK = "INSERT INTO USER_RESOURCE_PERMISSIONS(USER_ID, RESOURCE_ID, PERMISSION) VALUES(?,?,?)";
	private static final String SQL_SELECT_USER_RESOURCE_LINK_BY_ID = "SELECT PERMISSION.BIT, USER.LOGIN, RESOURCE.NAME, USER_RESOURCE_PERMISSIONS.ID, USER_ID, RESOURCE_ID, PERMISSION FROM USER_RESOURCE_PERMISSIONS LEFT JOIN PERMISSION ON +"
			+ " USER_RESOURCE_PERMISSIONS.PERMISSION & PERMISSION.BIT LEFT JOIN USER USER ON +"
			+ " USER_RESOURCE_PERMISSIONS.USER_ID = USER.ID LEFT JOIN RESOURCE ON + "
			+ " USER_RESOURCE_PERMISSIONS.RESOURCE_ID = RESOURCE.ID WHERE PERMISSION.BIT IS NOT NULL AND USER_RESOURCE_PERMISSIONS.ID=?;";
	private static final String SQL_SELECT_ALL_USER_RESOURCE_LINKS = "SELECT PERMISSION.BIT, USER.LOGIN, RESOURCE.NAME, USER_RESOURCE_PERMISSIONS.ID, USER_ID, RESOURCE_ID, PERMISSION FROM USER_RESOURCE_PERMISSIONS LEFT JOIN PERMISSION ON +"
			+ " USER_RESOURCE_PERMISSIONS.PERMISSION & PERMISSION.BIT LEFT JOIN USER USER ON +"
			+ " USER_RESOURCE_PERMISSIONS.USER_ID = USER.ID LEFT JOIN RESOURCE ON + "
			+ " USER_RESOURCE_PERMISSIONS.RESOURCE_ID = RESOURCE.ID WHERE PERMISSION.BIT IS NOT NULL;";
	private static final String SQL_SELECT_ALL_USER_RESOURCE_LINKS_BY_USER_ID = "SELECT PERMISSION.BIT, USER.LOGIN, RESOURCE.NAME, USER_RESOURCE_PERMISSIONS.ID, USER_ID, RESOURCE_ID, PERMISSION FROM USER_RESOURCE_PERMISSIONS LEFT JOIN PERMISSION ON +"
			+ " USER_RESOURCE_PERMISSIONS.PERMISSION & PERMISSION.BIT LEFT JOIN USER USER ON +"
			+ " USER_RESOURCE_PERMISSIONS.USER_ID = USER.ID LEFT JOIN RESOURCE ON + "
			+ " USER_RESOURCE_PERMISSIONS.RESOURCE_ID = RESOURCE.ID WHERE PERMISSION.BIT IS NOT NULL AND USER_ID=?;";
	private static final String SQL_UPDATE_USER_RESOURCE_LINK = "UPDATE USER_RESOURCE_PERMISSIONS SET USER_ID=?, RESOURCE_ID=?, PERMISSION=? WHERE ID=?";
	private static final String SQL_DELETE_USER_RESOURCE_PERMISSIONS = "DELETE FROM USER_RESOURCE_PERMISSIONS WHERE ID=?";

	private static final String USER_RESOURCE_ID = "ID";
	private static final String USER_ID = "USER_ID";
	private static final String RESOURCE_ID = "RESOURCE_ID";
	private static final String PERMISSION = "PERMISSION";
	private static final String PERMISSION_BIT = "BIT";
	private static final String USER_LOGIN = "LOGIN";
	private static final String RESOURCE_NAME = "NAME";

	@Autowired
	private DataSource dataSource;

	@Override
	public void create(UserResourceLink link) throws DAOException {
		try (Connection con = dataSource.getConnection();
				PreparedStatement ps = con.prepareStatement(SQL_CREATE_USER_RESOURCE_LINK);) {
			ps.setInt(1, link.getUserId());
			ps.setInt(2, link.getResourceId());
			ps.setInt(3, link.getPermission());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("Error while insert new user resource link. " + e.getMessage(), e);
		}
	}

	@Override
	public UserResourceLink retrieve(Integer linkIdtoGet) throws DAOException {
		Map<Integer, UserResourceLink> linksMap = new HashMap<Integer, UserResourceLink>();
		UserResourceLink link = null;

		try (Connection con = dataSource.getConnection();
				PreparedStatement ps = con.prepareStatement(SQL_SELECT_USER_RESOURCE_LINK_BY_ID);) {
			ps.setInt(1, linkIdtoGet);
			try (ResultSet rs = ps.executeQuery();) {
				while (rs.next()) {
					int permissionBit = rs.getInt(PERMISSION_BIT);
					String login = rs.getString(USER_LOGIN);
					String resourceName = rs.getString(RESOURCE_NAME);
					Integer linkId = rs.getInt(USER_RESOURCE_ID);
					Integer userId = rs.getInt(USER_ID);
					Integer resourceId = rs.getInt(RESOURCE_ID);
					Integer permission = rs.getInt(PERMISSION);
					link = new UserResourceLink();
					link.setUserName(login);
					link.setResourceName(resourceName);
					link.setUserResourceLinkId(linkId);
					link.setUserId(userId);
					link.setResourceId(resourceId);
					link.setPermission(permission);
					if (linksMap.containsKey(linkId)) {
						link = linksMap.get(linkId);
					}
					if (permissionBit == Permission.Edit.getValue()) {
						link.setEditPermission(true);
					} else if (permissionBit == Permission.Delete.getValue()) {
						link.setDeletePermission(true);
					} else if (permissionBit == Permission.View.getValue()) {
						link.setViewPermission(true);
					}
					if (!linksMap.containsKey(linkId)) {
						linksMap.put(linkId, link);
					}
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Error while retrieve link from db. " + e.getMessage(), e);
		}
		return linksMap.get(linkIdtoGet);
	}

	@Override
	public List<UserResourceLink> retrieveAll() throws DAOException {
		Map<Integer, UserResourceLink> linksMap = new HashMap<Integer, UserResourceLink>();
		UserResourceLink link = null;

		try (Connection con = dataSource.getConnection();
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(SQL_SELECT_ALL_USER_RESOURCE_LINKS);) {
			while (rs.next()) {
				int permissionBit = rs.getInt(PERMISSION_BIT);
				String login = rs.getString(USER_LOGIN);
				String resourceName = rs.getString(RESOURCE_NAME);
				Integer linkId = rs.getInt(USER_RESOURCE_ID);
				Integer userId = rs.getInt(USER_ID);
				Integer resourceId = rs.getInt(RESOURCE_ID);
				Integer permission = rs.getInt(PERMISSION);
				link = new UserResourceLink();
				link.setUserName(login);
				link.setResourceName(resourceName);
				link.setUserResourceLinkId(linkId);
				link.setUserId(userId);
				link.setResourceId(resourceId);
				link.setPermission(permission);
				if (linksMap.containsKey(linkId)) {
					link = linksMap.get(linkId);
				}
				if (permissionBit == Permission.Edit.getValue()) {
					link.setEditPermission(true);
				} else if (permissionBit == Permission.Delete.getValue()) {
					link.setDeletePermission(true);
				} else if (permissionBit == Permission.View.getValue()) {
					link.setViewPermission(true);
				}
				if (!linksMap.containsKey(linkId)) {
					linksMap.put(linkId, link);
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Error while retrive list of links from db. " + e.getMessage(), e);
		}

		return new ArrayList<UserResourceLink>(linksMap.values());
	}

	@Override
	public List<UserResourceLink> getUserResourceLinkByUserId(Integer userIdToGet) throws DAOException {
		Map<Integer, UserResourceLink> linksMap = new HashMap<Integer, UserResourceLink>();
		UserResourceLink link = null;

		try (Connection con = dataSource.getConnection();
				PreparedStatement ps = con.prepareStatement(SQL_SELECT_ALL_USER_RESOURCE_LINKS_BY_USER_ID);) {
			ps.setInt(1, userIdToGet);
			try (ResultSet rs = ps.executeQuery();) {
				while (rs.next()) {
					int permissionBit = rs.getInt(PERMISSION_BIT);
					String login = rs.getString(USER_LOGIN);
					String resourceName = rs.getString(RESOURCE_NAME);
					Integer linkId = rs.getInt(USER_RESOURCE_ID);
					Integer userId = rs.getInt(USER_ID);
					Integer resourceId = rs.getInt(RESOURCE_ID);
					Integer permission = rs.getInt(PERMISSION);
					link = new UserResourceLink();
					link.setUserName(login);
					link.setResourceName(resourceName);
					link.setUserResourceLinkId(linkId);
					link.setUserId(userId);
					link.setResourceId(resourceId);
					link.setPermission(permission);
					if (linksMap.containsKey(linkId)) {
						link = linksMap.get(linkId);
					}
					if (permissionBit == Permission.Edit.getValue()) {
						link.setEditPermission(true);
					} else if (permissionBit == Permission.Delete.getValue()) {
						link.setDeletePermission(true);
					} else if (permissionBit == Permission.View.getValue()) {
						link.setViewPermission(true);
					}
					if (!linksMap.containsKey(linkId)) {
						linksMap.put(linkId, link);
					}
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Error while retrieve link from db. " + e.getMessage(), e);
		}
		return new ArrayList<UserResourceLink>(linksMap.values());
	}

	@Override
	public void update(UserResourceLink link) throws DAOException {
		try (Connection con = dataSource.getConnection();
				PreparedStatement ps = con.prepareStatement(SQL_UPDATE_USER_RESOURCE_LINK);) {
			ps.setInt(1, link.getUserId());
			ps.setInt(2, link.getResourceId());
			ps.setInt(3, link.getPermission());
			ps.setInt(4, link.getUserResourceLinkId());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("Error while update link." + e.getMessage(), e);
		}
	}

	@Override
	public void delete(Integer linkId) throws DAOException {
		try (Connection con = dataSource.getConnection();
				PreparedStatement ps = con.prepareStatement(SQL_DELETE_USER_RESOURCE_PERMISSIONS);) {
			ps.setInt(1, linkId);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("Error while delete link." + e.getMessage(), e);
		}
	}

}
