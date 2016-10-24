package com.example.infs3634project2.Data;

import android.app.DownloadManager;
import android.content.Context;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.infs3634project2.model.Projects;
import com.example.infs3634project2.views.TutorialsActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by student on 17/10/2016.
 */

public class GitHubDataProvider{

    private Context context;
    ArrayList<Projects> listOfProjects = new ArrayList<>();

    public GitHubDataProvider(Context context) {
        this.context = context;
    }

    public void getGitProject(final String username, final GitHubCallback<ArrayList<Projects>> callback) {

        Log.d("Debug", username);
        String apiURL = "https://api.github.com/users/" + username + "/repos";
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        final JsonArrayRequest listObject = new JsonArrayRequest(Request.Method.GET, apiURL,

                new Response.Listener<JSONArray>() {

                    // Takes the response from the JSON request
                    @Override
                    public void onResponse(JSONArray response) {
                        try {

                            for(int i = 0 ; i < response.length() ; i++) {
                                JSONObject currentProject = response.getJSONObject(i);
                                String name = currentProject.getString("name");
                                String url = currentProject.getString("svn_url");
                                String createdDate = currentProject.getString("created_at");
                                String updatedDate = currentProject.getString("updated_at");

                                Projects project = new Projects(name, url, createdDate, updatedDate);
                                listOfProjects.add(project);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Log.d("Debug", listOfProjects.toString());
                        callback.onTaskCompleted(listOfProjects);
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    // Handles errors that occur due to Volley
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error");
                        error.printStackTrace();
                        callback.onFailure("Fail");
                    }
                }
        );
        requestQueue.add(listObject);
    }
}
