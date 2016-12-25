package by.bsu.nosti.entity;

public class UserResourceLink {
	private int userResourceLinkId;
	private int userId;
	private int resourceId;
	private String userName;
	private String resourceName;
	private Boolean editPermission;
	private Boolean deletePermission;
	private Boolean viewPermission;
	private int permission;

	public int getUserResourceLinkId() {
		return userResourceLinkId;
	}

	public void setUserResourceLinkId(int userResourceLinkId) {
		this.userResourceLinkId = userResourceLinkId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getResourceId() {
		return resourceId;
	}

	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public Boolean getEditPermission() {
		return editPermission;
	}

	public void setEditPermission(Boolean editPermission) {
		this.editPermission = editPermission;
	}

	public Boolean getDeletePermission() {
		return deletePermission;
	}

	public void setDeletePermission(Boolean deletePermission) {
		this.deletePermission = deletePermission;
	}

	public Boolean getViewPermission() {
		return viewPermission;
	}

	public void setViewPermission(Boolean viewPermission) {
		this.viewPermission = viewPermission;
	}

	public int getPermission() {
		int permission = 0;
		if (editPermission) {
			permission += Permission.Edit.getValue();
		}
		if (deletePermission) {
			permission += Permission.Delete.getValue();
		}
		if (viewPermission) {
			permission += Permission.View.getValue();
		}
		return permission;
	}

	public void setPermission(int permission) {
		this.permission = permission;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + permission;
		result = prime * result + resourceId;
		result = prime * result + ((resourceName == null) ? 0 : resourceName.hashCode());
		result = prime * result + userId;
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
		result = prime * result + userResourceLinkId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserResourceLink other = (UserResourceLink) obj;
		if (permission != other.permission)
			return false;
		if (resourceId != other.resourceId)
			return false;
		if (resourceName == null) {
			if (other.resourceName != null)
				return false;
		} else if (!resourceName.equals(other.resourceName))
			return false;
		if (userId != other.userId)
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		if (userResourceLinkId != other.userResourceLinkId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserResourceLink [userResourceLinkId=");
		builder.append(userResourceLinkId);
		builder.append(", userId=");
		builder.append(userId);
		builder.append(", resourceId=");
		builder.append(resourceId);
		builder.append(", userName=");
		builder.append(userName);
		builder.append(", resourceName=");
		builder.append(resourceName);
		builder.append(", editPermission=");
		builder.append(editPermission);
		builder.append(", deletePermission=");
		builder.append(deletePermission);
		builder.append(", viewPermission=");
		builder.append(viewPermission);
		builder.append(", permission=");
		builder.append(permission);
		builder.append("]");
		return builder.toString();
	}

}
