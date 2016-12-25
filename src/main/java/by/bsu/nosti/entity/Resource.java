package by.bsu.nosti.entity;

public class Resource {
	private int resourceId;
	private String name;
	private String info;

	public Resource() {
		super();
	}

	public Resource(String projectName, String projectInfo) {
		this.setInfo(projectInfo);
		this.setName(projectName);
	}

	public Resource(int resourceId, String projectName, String projectInfo) {
		this.resourceId = resourceId;
		this.setName(projectName);
		this.setInfo(projectInfo);
	}

	public int getResourceId() {
		return resourceId;
	}

	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Resource resource = (Resource) o;

		if (resourceId != resource.resourceId)
			return false;
		if (name.equals(resource.name))
			return false;
		return info != null ? info.equals(resource.info) : resource.info == null;

	}

	@Override
	public int hashCode() {
		int result = resourceId;
		result = 31 * result + (name != null ? name.hashCode() : 0) + (info != null ? info.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "Resource{" + "resourceId=" + resourceId + ", project name='" + name + '\'' + '}';
	}

	public String getName() {
		return name;
	}

	public void setName(String projectName) {
		this.name = projectName;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String projectInfo) {
		this.info = projectInfo;
	}
}
