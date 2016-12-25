package by.bsu.nosti.dao;

import java.util.List;

import by.bsu.nosti.entity.UserResourceLink;
import by.bsu.nosti.exception.DAOException;

public interface UserResourceLinkDAO extends BaseDAO<Integer, UserResourceLink> {

	List<UserResourceLink> getUserResourceLinkByUserId(Integer userId) throws DAOException;
}
