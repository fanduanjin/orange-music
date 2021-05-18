package cn.fan.model.file;

import java.io.Serializable;

public class Resource implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column resource.id
     *
     * @mbg.generated Sun May 16 22:00:30 CST 2021
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column resource.resource_id
     *
     * @mbg.generated Sun May 16 22:00:30 CST 2021
     */
    private Long resourceId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column resource.source_path
     *
     * @mbg.generated Sun May 16 22:00:30 CST 2021
     */
    private String sourcePath;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column resource.dfs_path
     *
     * @mbg.generated Sun May 16 22:00:30 CST 2021
     */
    private String dfsPath;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table resource
     *
     * @mbg.generated Sun May 16 22:00:30 CST 2021
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column resource.id
     *
     * @return the value of resource.id
     *
     * @mbg.generated Sun May 16 22:00:30 CST 2021
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column resource.id
     *
     * @param id the value for resource.id
     *
     * @mbg.generated Sun May 16 22:00:30 CST 2021
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column resource.resource_id
     *
     * @return the value of resource.resource_id
     *
     * @mbg.generated Sun May 16 22:00:30 CST 2021
     */
    public Long getResourceId() {
        return resourceId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column resource.resource_id
     *
     * @param resourceId the value for resource.resource_id
     *
     * @mbg.generated Sun May 16 22:00:30 CST 2021
     */
    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column resource.source_path
     *
     * @return the value of resource.source_path
     *
     * @mbg.generated Sun May 16 22:00:30 CST 2021
     */
    public String getSourcePath() {
        return sourcePath;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column resource.source_path
     *
     * @param sourcePath the value for resource.source_path
     *
     * @mbg.generated Sun May 16 22:00:30 CST 2021
     */
    public void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column resource.dfs_path
     *
     * @return the value of resource.dfs_path
     *
     * @mbg.generated Sun May 16 22:00:30 CST 2021
     */
    public String getDfsPath() {
        return dfsPath;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column resource.dfs_path
     *
     * @param dfsPath the value for resource.dfs_path
     *
     * @mbg.generated Sun May 16 22:00:30 CST 2021
     */
    public void setDfsPath(String dfsPath) {
        this.dfsPath = dfsPath;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table resource
     *
     * @mbg.generated Sun May 16 22:00:30 CST 2021
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", resourceId=").append(resourceId);
        sb.append(", sourcePath=").append(sourcePath);
        sb.append(", dfsPath=").append(dfsPath);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}