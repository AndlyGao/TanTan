package com.xuyijie.module_lib.base;

public interface Presenter<T extends BaseView> {
    void attachView(T view);

    void detachView();
}
