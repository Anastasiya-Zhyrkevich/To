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

import by.bsu.nosti.dao.ResourceDAO;
import by.bsu.nosti.entity.Resource;
import by.bsu.nosti.exception.DAOException;

public class ResourceDAOImpl implements ResourceDAO {

	private static final String SQL_CREATE_RESOURCE = "INSERT INTO RESOURCE(NAME, INFO) VALUES(?,?)";
	private static final String SQL_SELECT_RESOURCE_BY_ID = "SELECT NAME, INFO FROM RESOURCE WHERE ID=?";
	private static final String SQL_SELECT_ALL_RESOURCES = "SELECT ID, NAME, INFO FROM RESOURCE";
	private static final String SQL_UPDATE_RESOURCE = "UPDATE RESOURCE SET NAME=?, INFO=? WHERE ID=?";
	private static final String SQL_DELETE_RESOURCE = "DELETE FROM RESOURCE WHERE ID=?";
	private static final String SQL_SELECT_RESOURCES_BY_GROUP = "SELECT res.RESOURCES_ID, res.PROJECT_NAME, res.PROJECT_INFO FROM GROUP_RESOURCES group_resources INNER JOIN RESOURCES res ON res.RESOURCES_ID = group_resources.RESOURCES_ID WHERE group_resources.GROUP_RESOURCES_NAME_ID =? ";

	private static final String RESOURCE_ID = "ID";
	private static final String NAME = "NAME";
	private static final String INFO = "INFO";
	@Autowired
	private DataSource dataSource;

	@Override
	public void create(Resource resource) throws DAOException {
		try (Connection con = dataSource.getConnection();
				PreparedStatement ps = con.prepareStatement(SQL_CREATE_RESOURCE);) {
			ps.setString(1, resource.getName());
			ps.setString(2, resource.getInfo());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("Error while insert new resource. " + e.getMessage(), e);
		}
	}

	@Override
	public Resource retrieve(Integer resourceId) throws DAOException {
		Resource resource = null;

		try (Connection con = dataSource.getConnection();
				PreparedStatement ps = con.prepareStatement(SQL_SELECT_RESOURCE_BY_ID);) {
			ps.setInt(1, resourceId);
			try (ResultSet rs = ps.executeQuery();) {
				while (rs.next()) {
					String projectName = rs.getString(NAME);
					String projectInfo = rs.getString(INFO);
					resource = new Resource(resourceId, projectName, projectInfo);
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Error while retrieve resource from db. " + e.getMessage(), e);
		}
		return resource;
	}

	@Override
	public List<Resource> retrieveAll() throws DAOException {
		List<Resource> resources = new ArrayList<>();
		Resource resource = null;

		try (Connection con = dataSource.getConnection();
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(SQL_SELECT_ALL_RESOURCES);) {
			while (rs.next()) {
				int resourceId = rs.getInt(RESOURCE_ID);
				String projectName = rs.getString(NAME);
				String projectInfo = rs.getString(INFO);
				resource = new Resource(resourceId, projectName, projectInfo);
				resources.add(resource);
			}
		} catch (SQLException e) {
			throw new DAOException("Error while retrive list of resources from db. " + e.getMessage(), e);
		}

		return resources;
	}

	@Override
	public void update(Resource resource) throws DAOException {
		try (Connection con = dataSource.getConnection();
				PreparedStatement ps = con.prepareStatement(SQL_UPDATE_RESOURCE);) {
			ps.setString(1, resource.getName());
			ps.setString(2, resource.getInfo());
			ps.setInt(3, resource.getResourceId());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("Error while update resource. " + e.getMessage(), e);
		}
	}

	@Override
	public void delete(Integer resourceId) throws DAOException {
		try (Connection con = dataSource.getConnection();
				PreparedStatement ps = con.prepareStatement(SQL_DELETE_RESOURCE)) {
			ps.setInt(1, resourceId);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("Error while delete resources. " + e.getMessage(), e);
		}
	}

	@Override
	public List<Resource> getResources(Integer groupId) throws DAOException {
		List<Resource> resources = new ArrayList<>();
		Resource resource = null;

		try (Connection con = dataSource.getConnection();
				PreparedStatement ps = con.prepareStatement(SQL_SELECT_RESOURCES_BY_GROUP);) {
			ps.setInt(1, groupId);
			try (ResultSet rs = ps.executeQuery();) {
				while (rs.next()) {
					int resourceId = rs.getInt(RESOURCE_ID);
					String projectName = rs.getString(NAME);
					String projectInfo = rs.getString(INFO);
					resource = new Resource(resourceId, projectName, projectInfo);
					resources.add(resource);
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Error while retrieve resource from db. " + e.getMessage(), e);
		}
		return resources;
	}
}
