package com.friday.peanutbutter.model;

public class User {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.ID
     *
     * @mbg.generated Sun Apr 02 16:18:52 CST 2023
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.ACCOUNT_ID
     *
     * @mbg.generated Sun Apr 02 16:18:52 CST 2023
     */
    private String accountId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.NAME
     *
     * @mbg.generated Sun Apr 02 16:18:52 CST 2023
     */
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.TOKEN
     *
     * @mbg.generated Sun Apr 02 16:18:52 CST 2023
     */
    private String token;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.GMT_CREATE
     *
     * @mbg.generated Sun Apr 02 16:18:52 CST 2023
     */
    private Long gmtCreate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.GMT_MODIFIED
     *
     * @mbg.generated Sun Apr 02 16:18:52 CST 2023
     */
    private Long gmtModified;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.AVATAR_URL
     *
     * @mbg.generated Sun Apr 02 16:18:52 CST 2023
     */
    private String avatarUrl;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.ACCOUNT_TYPE
     *
     * @mbg.generated Sun Apr 02 16:18:52 CST 2023
     */
    private String accountType;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.ID
     *
     * @return the value of user.ID
     *
     * @mbg.generated Sun Apr 02 16:18:52 CST 2023
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.ID
     *
     * @param id the value for user.ID
     *
     * @mbg.generated Sun Apr 02 16:18:52 CST 2023
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.ACCOUNT_ID
     *
     * @return the value of user.ACCOUNT_ID
     *
     * @mbg.generated Sun Apr 02 16:18:52 CST 2023
     */
    public String getAccountId() {
        return accountId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.ACCOUNT_ID
     *
     * @param accountId the value for user.ACCOUNT_ID
     *
     * @mbg.generated Sun Apr 02 16:18:52 CST 2023
     */
    public void setAccountId(String accountId) {
        this.accountId = accountId == null ? null : accountId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.NAME
     *
     * @return the value of user.NAME
     *
     * @mbg.generated Sun Apr 02 16:18:52 CST 2023
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.NAME
     *
     * @param name the value for user.NAME
     *
     * @mbg.generated Sun Apr 02 16:18:52 CST 2023
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.TOKEN
     *
     * @return the value of user.TOKEN
     *
     * @mbg.generated Sun Apr 02 16:18:52 CST 2023
     */
    public String getToken() {
        return token;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.TOKEN
     *
     * @param token the value for user.TOKEN
     *
     * @mbg.generated Sun Apr 02 16:18:52 CST 2023
     */
    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.GMT_CREATE
     *
     * @return the value of user.GMT_CREATE
     *
     * @mbg.generated Sun Apr 02 16:18:52 CST 2023
     */
    public Long getGmtCreate() {
        return gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.GMT_CREATE
     *
     * @param gmtCreate the value for user.GMT_CREATE
     *
     * @mbg.generated Sun Apr 02 16:18:52 CST 2023
     */
    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.GMT_MODIFIED
     *
     * @return the value of user.GMT_MODIFIED
     *
     * @mbg.generated Sun Apr 02 16:18:52 CST 2023
     */
    public Long getGmtModified() {
        return gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.GMT_MODIFIED
     *
     * @param gmtModified the value for user.GMT_MODIFIED
     *
     * @mbg.generated Sun Apr 02 16:18:52 CST 2023
     */
    public void setGmtModified(Long gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.AVATAR_URL
     *
     * @return the value of user.AVATAR_URL
     *
     * @mbg.generated Sun Apr 02 16:18:52 CST 2023
     */
    public String getAvatarUrl() {
        return avatarUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.AVATAR_URL
     *
     * @param avatarUrl the value for user.AVATAR_URL
     *
     * @mbg.generated Sun Apr 02 16:18:52 CST 2023
     */
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl == null ? null : avatarUrl.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.ACCOUNT_TYPE
     *
     * @return the value of user.ACCOUNT_TYPE
     *
     * @mbg.generated Sun Apr 02 16:18:52 CST 2023
     */
    public String getAccountType() {
        return accountType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.ACCOUNT_TYPE
     *
     * @param accountType the value for user.ACCOUNT_TYPE
     *
     * @mbg.generated Sun Apr 02 16:18:52 CST 2023
     */
    public void setAccountType(String accountType) {
        this.accountType = accountType == null ? null : accountType.trim();
    }
}