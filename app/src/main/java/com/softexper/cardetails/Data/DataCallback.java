package com.softexper.cardetails.Data;


public interface DataCallback<T> {

    /**
     * On data received.
     *
     * @param t the t
     */
    void onDataReceived(T t);

    default void onFailure(Throwable t) {
    }


    default void onFailure(String s) {
    }

}
