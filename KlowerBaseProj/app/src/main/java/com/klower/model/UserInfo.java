/**
 * All rights reserved
 */

package com.klower.model;

import com.j256.ormlite.field.DatabaseField;

/**
 * Please describe
 * 
 * @version 1.0
 * @createDate 2013-3-19
 */
public class UserInfo {

    @DatabaseField(generatedId = true)
    int user_id;

    @DatabaseField(canBeNull = false)
    String user_name;

    @DatabaseField(canBeNull = false)
    String user_password;

    @DatabaseField(canBeNull = false)
    String user_sex;
    
    @DatabaseField(canBeNull = false)
    Long time;

    @DatabaseField(canBeNull = true)
    int user_age;

    @DatabaseField(canBeNull = true)
    float user_height;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getUser_sex() {
        return user_sex;
    }

    public void setUser_sex(String user_sex) {
        this.user_sex = user_sex;
    }

    public int getUser_age() {
        return user_age;
    }

    public void setUser_age(int user_age) {
        this.user_age = user_age;
    }

    public float getUser_height() {
        return user_height;
    }

    public void setUser_height(float user_height) {
        this.user_height = user_height;
    }

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

}
