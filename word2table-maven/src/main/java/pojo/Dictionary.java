package pojo;

import common.Constant;

import java.util.Date;

public class Dictionary {
    private String dirKey;
    private String dicLabel;
    private String dicValue;
    private int dicType;
    private int sortIndex;
    private String parentKey;
    private int isActive;
    private Date updateTime;
    private String description;

    public String getDirKey() {
        return dirKey;
    }

    public void setDirKey(String dirKey) {
        this.dirKey = dirKey;
    }

    public String getDicLabel() {
        return dicLabel;
    }

    public void setDicLabel(String dicLabel) {
        this.dicLabel = dicLabel;
    }

    public String getDicValue() {
        return dicValue;
    }

    public void setDicValue(String dicValue) {
        this.dicValue = dicValue;
    }

    public int getDicType() {
        return dicType;
    }

    public void setDicType(int dicType) {
        this.dicType = dicType;
    }

    public int getSortIndex() {
        return sortIndex;
    }

    public void setSortIndex(int sortIndex) {
        this.sortIndex = sortIndex;
    }

    public String getParentKey() {
        return parentKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Dictionary(String dirKey, String dicLabel, String dicValue, int dicType, int sortIndex, String parentKey, String description) {
        this.dirKey = dirKey;
        this.dicLabel = dicLabel;
        this.dicValue = dicValue;
        this.dicType = dicType;
        this.sortIndex = sortIndex;
        this.parentKey = parentKey;
        this.description = description;

        this.updateTime = new Date();
        this.isActive = Constant.DICTIONARY_IS_ACTIVE;
    }
}
