package com.xuyijie.module_lib.gson;

public class UserTopicGson {

    /**
     * id : 2
     * topicName : 今日份的摄影
     * postCount : 31
     * userId : 100001
     */

    private int id;
    private String topicName;
    private String postCount;
    private int userId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getPostCount() {
        return postCount;
    }

    public void setPostCount(String postCount) {
        this.postCount = postCount;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
