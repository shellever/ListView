package com.shellever.listview;

/**
 * Created by linuxfor on 6/5/2016.
 */
public class UserBean {
    private int iconResId;
    private String name;
    private String description;

    public UserBean(){}

    public UserBean(int iconResId, String name, String description) {
        this.iconResId = iconResId;
        this.name = name;
        this.description = description;
    }

    public int getIconResId() {
        return iconResId;
    }

    public void setIconResId(int iconResId) {
        this.iconResId = iconResId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "iconResId=" + iconResId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
