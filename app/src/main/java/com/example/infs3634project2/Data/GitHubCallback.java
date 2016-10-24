package com.example.infs3634project2.Data;

/**
 * Created by student on 17/10/2016.
 */

public interface GitHubCallback<T> {

    void onTaskCompleted(T result);

    void onFailure(String fail);

}