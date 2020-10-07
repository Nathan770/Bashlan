package com.example.bashlan;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class Fragment_recipes extends Fragment {
    private static final String TAG = "nathan";
    protected View view;
    private ArrayList<ProductData> listProduct;
    private ArrayList<RecipesData> listRecipes;
    private RecyclerView recipes_RCV_product;
    private RecycleAdapterRecipes recipes_RCV_adapter;
    private RecyclerView.LayoutManager recipes_RCV_manager;

    public static Fragment_recipes newInstance(ArrayList<ProductData> listProduct) {
        Fragment_recipes fragment = new Fragment_recipes();
        fragment.listProduct = listProduct;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_recipe, container, false);
        }

        listRecipes = new ArrayList<>();
        searchRecipes();

        return view;
    }

    private void searchRecipes() {
        String baseUrl = "https://api.spoonacular.com/recipes/findByIngredients?ingredients";
        String ingredientsUrl = "=";
        String numberUrl = "&number=10";
        String apiUrl = "&apiKey=" + getString(R.string.API_KEY);
        final ArrayList<JSONObject> recipesJson;
        recipesJson = new ArrayList<>();

        for (ProductData i : listProduct) {
            if (i.isHaving()) {
                ingredientsUrl += i.getName() + ",+";
            }
        }
        if (ingredientsUrl.length() > 2) {
            ingredientsUrl = ingredientsUrl.substring(0, ingredientsUrl.length() - 2);

            String url = baseUrl + ingredientsUrl + numberUrl + apiUrl;
            Log.d(TAG, "searchRecipes: " + url);

            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .header("Content-Type", "application/json")
                    .build();

            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onResponse(@NotNull Call call, @NotNull okhttp3.Response response) throws IOException {
                    if (response == null) {
                        Log.d(TAG, "onResponse: Response is null");
                    } else {
                        String all = response.body().string();
                        Log.d(TAG, "onResponse: " + all);
                        try {
                            JSONArray json = new JSONArray(all);
                            Log.d(TAG, "onResponse: " + json.toString());
                            JSONObject jsonObject1 = (JSONObject) json.get(0);
                            Log.d(TAG, "first" + jsonObject1);
                            for (int i = 0; i < json.length(); i++) {
                                recipesJson.add(i, (JSONObject) json.get(i));
                            }
                            for (JSONObject i : recipesJson) {
                                Log.d(TAG, "id = " + i.get("id") + " title = " + i.get("title") + " image = " + i.get("image"));
                                listRecipes.add(new RecipesData((int) i.get("id"), (String) i.get("title"), (String) i.get("image"), 1));
                            }
                            runThread();
                        } catch (JSONException e) {
                            Log.d(TAG, "httpRec Exception : " + e.getMessage());
                        }
                    }

                }

                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    Log.d(TAG, "onFailure: Request failed: nathan" + e.getMessage());
                }
            });
        } else {
            Toast.makeText(view.getContext(), "Add element to your fridge", Toast.LENGTH_LONG).show();
        }
    }

    private void addToActivity() {

        recipes_RCV_product = view.findViewById(R.id.recipes_RCV_product);
        recipes_RCV_product.setHasFixedSize(true);
        recipes_RCV_manager = new LinearLayoutManager(view.getContext());
        recipes_RCV_adapter = new RecycleAdapterRecipes(listRecipes);
        recipes_RCV_adapter.setClickListeners(recipesItemClickListener);
        recipes_RCV_product.setLayoutManager(recipes_RCV_manager);
        recipes_RCV_product.setAdapter(recipes_RCV_adapter);

    }

    RecycleAdapterRecipes.RecipesItemClickListener recipesItemClickListener = new RecycleAdapterRecipes.RecipesItemClickListener() {
        @Override
        public void itemClicked(RecipesData item) {

            Log.d(TAG, "itemClicked: " + item.getId() + " name " + item.getTitle());
            String url = "";
            url = getInstruction(item);

            Fragment_instruction fragment_instruction = Fragment_instruction.newInstance(url,listProduct);
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.main_LAY_app, fragment_instruction);
            transaction.commit();
        }
    };

    private String getInstruction(RecipesData item) {
        Log.d(TAG, "getRecipesInstruction: start");
        String idUrl = "";
        String typeUrl = "/analyzedInstructions";
        String baseUrl = "https://api.spoonacular.com/recipes/";
        String apiKeyUrl = "?apiKey=" + getString(R.string.API_KEY);
        String url = baseUrl + idUrl + typeUrl + apiKeyUrl;

        Log.d(TAG, "URL: " + url);
        idUrl = String.valueOf(item.getId());
        url = baseUrl + idUrl + typeUrl + apiKeyUrl;

        return url;
    }

    private void runThread() {
        new Thread() {
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(view.getContext(), "Loading recipes ...", Toast.LENGTH_SHORT).show();
                        addToActivity();
                    }
                });

            }

        }.start();
    }

}