package by.bsu.nosti.dao;

import java.util.List;

import by.bsu.nosti.entity.Resource;
import by.bsu.nosti.exception.DAOException;

public interface ResourceDAO extends BaseDAO<Integer, Resource> {
	List<Resource> getResources(Integer groupId) throws DAOException;
}
