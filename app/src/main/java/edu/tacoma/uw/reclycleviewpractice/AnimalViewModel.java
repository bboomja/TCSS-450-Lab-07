package edu.tacoma.uw.reclycleviewpractice;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AnimalViewModel extends AndroidViewModel {
    private MutableLiveData<JSONObject> mResponse;
    private MutableLiveData<List<Animal>> mAnimalList;

    public AnimalViewModel(@NonNull Application application) {
        super(application);
        mResponse = new MutableLiveData<>();
        mResponse.setValue(new JSONObject());
        mAnimalList = new MutableLiveData<>();
        mAnimalList.setValue(new ArrayList<>());
    }

    public void addResponseObserver(@NonNull LifecycleOwner owner,
                                    @NonNull Observer<? super JSONObject> observer) {
        mResponse.observe(owner, observer);
    }

    private void handleError(final VolleyError error) {
        if (Objects.isNull(error.networkResponse)) {
            try {
                mResponse.setValue(new JSONObject("{" +
                        "error:\"" + error.getMessage() +
                        "\"}"));
            } catch (JSONException e) {
                Log.e("JSON PARSE", "JSON Parse Error in handleError");
            }
        } else {
            String data = new String(error.networkResponse.data, Charset.defaultCharset())
                    .replace('\"', '\'');
            try {
                mResponse.setValue(new JSONObject("{" +
                        "code:" + error.networkResponse.statusCode +
                        ", data:\"" + data +
                        "\"}"));
            } catch (JSONException e) {
                Log.e("JSON PARSE", "JSON Parse Error in handleError");
            }
        }
    }

    public void addAnimalListObserver(@NonNull LifecycleOwner owner,
                                      @NonNull Observer<? super List<Animal>> observer) {
        mAnimalList.observe(owner, observer);
    }

    private void handleResult(final JSONObject result) {
        try {
            String data = result.getString("animals");
            JSONArray arr = new JSONArray(data);
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                Animal animal = new Animal(Integer.parseInt(obj.getString(Animal.ID)),
                        obj.getString(Animal.KIND),
                        obj.getString(Animal.NAME));
                mAnimalList.getValue().add(animal);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("ERROR!", e.getMessage());
        }
        mAnimalList.setValue(mAnimalList.getValue());
    }

    public void getAnimals() {

        String url =

                "https://students.washington.edu/gilrieom/get_animals.php";

        Request request = new JsonObjectRequest(

                Request.Method.GET,

                url,

                null, //no body for this get request

                this::handleResult,

                this::handleError);



        request.setRetryPolicy(new DefaultRetryPolicy(

                10_000,

                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,

                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        //Instantiate the RequestQueue and add the request to the queue

        Volley.newRequestQueue(getApplication().getApplicationContext())

                .add(request);

    }

    public void addAnimal(String id, String kind, String name) {
        String url = "https://students.washington.edu/uwnetid/add_animal.php";
        JSONObject body = new JSONObject();
        try {
            body.put("id", id);
            body.put("kind", kind);
            body.put("name", name);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Request request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                body, //no body for this get request
                mResponse::setValue,
                this::handleError);

        Log.i("AnimalViewModel", request.getUrl().toString());
        request.setRetryPolicy(new DefaultRetryPolicy(
                10_000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Instantiate the RequestQueue and add the request to the queue
        Volley.newRequestQueue(getApplication().getApplicationContext())
                .add(request);
    }
}
