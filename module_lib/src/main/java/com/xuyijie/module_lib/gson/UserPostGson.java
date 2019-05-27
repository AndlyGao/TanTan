package com.xuyijie.module_lib.gson;

import java.util.List;

public class UserPostGson {

    /**
     * id : 1
     * userId : 100000
     * postTitle : 测试数据测试数据测试数据
     * postContent : 测试数据测试数据测试数据测试数据测试数据测试数据测试数据测试数据测试数据
     * createTime : 2019-05-25 21:37:11
     * topicType : 1
     * commentCount : 26
     * thumbCount : 100
     * shareCount : 100
     * user : {"avatar":"http://img0.imgtn.bdimg.com/it/u=2949723305,144355879&fm=26&gp=0.jpg","city":"嘉兴市","sex":"1","age":21,"school":"嘉兴学院","nickname":"新用户"}
     * pictures : ["https://wx1.sinaimg.cn/mw690/93c0135dgy1g3dvj17eqbj20go0gojrq.jpg","https://wx4.sinaimg.cn/mw690/006FCqYVgy1g3dshrejmmj31gx0u0ths.jpg"]
     */

    private int id;
    private String postTopic;

    public String getPostTopic() {
        return postTopic;
    }

    public void setPostTopic(String postTopic) {
        this.postTopic = postTopic;
    }

    private int userId;

    private String postContent;
    private String createTime;
    private String topicType;
    private String commentCount;
    private String thumbCount;
    private String shareCount;
    private UserGson user;
    private List<String> pictures;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getTopicType() {
        return topicType;
    }

    public void setTopicType(String topicType) {
        this.topicType = topicType;
    }

    public String getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }

    public String getThumbCount() {
        return thumbCount;
    }

    public void setThumbCount(String thumbCount) {
        this.thumbCount = thumbCount;
    }

    public String getShareCount() {
        return shareCount;
    }

    public void setShareCount(String shareCount) {
        this.shareCount = shareCount;
    }

    public UserGson getUser() {
        return user;
    }

    public void setUser(UserGson user) {
        this.user = user;
    }

    public List<String> getPictures() {
        return pictures;
    }

    public void setPictures(List<String> pictures) {
        this.pictures = pictures;
    }


}
