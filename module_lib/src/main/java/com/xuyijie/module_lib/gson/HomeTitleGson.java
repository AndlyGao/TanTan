package com.xuyijie.module_lib.gson;

public class HomeTitleGson {

    /**
     * id : 1
     * activeName : 圈子
     * activePicture : https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1515808625,2395100770&fm=11&gp=0.jpg
     * activeSorted : null
     */

    private int id;
    private String activeName;
    private String activePicture;
    private Object activeSorted;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getActiveName() {
        return activeName;
    }

    public void setActiveName(String activeName) {
        this.activeName = activeName;
    }

    public String getActivePicture() {
        return activePicture;
    }

    public void setActivePicture(String activePicture) {
        this.activePicture = activePicture;
    }

    public Object getActiveSorted() {
        return activeSorted;
    }

    public void setActiveSorted(Object activeSorted) {
        this.activeSorted = activeSorted;
    }
}
