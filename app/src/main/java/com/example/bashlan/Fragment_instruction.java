package com.example.bashlan;


import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

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

public class Fragment_instruction extends Fragment {
    private static final String TAG = "nathan";
    private View view;
    private TextView instruction_LBL_all;
    private MaterialButton instruction_BTN_back;
    private String mUrl = "";
    private ArrayList<String> listInstruction;
    private ArrayList<ProductData> mlistProduct;


    public static Fragment_instruction newInstance(String url, ArrayList<ProductData> listProduct) {
        Fragment_instruction fragment = new Fragment_instruction();
        fragment.mUrl = url;
        fragment.mlistProduct = listProduct;
        Log.d(TAG, "newInstance: " + url);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_instruction, container, false);
        }

        findViews();
        httpInstruction(mUrl);
        instruction_BTN_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction tx = getActivity().getSupportFragmentManager().beginTransaction();
                tx.replace(R.id.main_LAY_app, Fragment_recipes.newInstance(mlistProduct)).addToBackStack("tag").commit();
            }
        });

        return view;
    }

    private void addInstruction() {
        Log.d(TAG, "addInstruction: " + listInstruction.size() + " have in " + listInstruction.toString());
        for (int i = 0; i < listInstruction.size(); i++) {
            instruction_LBL_all.setText(instruction_LBL_all.getText() + " \n \n" + listInstruction.get(i).toString());
        }
    }

    private void findViews() {
        instruction_LBL_all = view.findViewById(R.id.instruction_LBL_all);
        instruction_BTN_back = view.findViewById(R.id.instruction_BTN_back);
        listInstruction = new ArrayList<>();
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: back press");
    }


    private void httpInstruction(String url) {
        Log.d(TAG, "httpInstruction: start");
        runThread();
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
                    String ans = response.body().string();
                    Log.d(TAG, "onResponse: " + ans);

                    try {
                        JSONArray responseJson = new JSONArray(ans);
                        Log.d(TAG, "onResponse: " + responseJson.toString());
                        JSONObject jsonObject1 = (JSONObject) responseJson.get(0);
                        Log.d(TAG, "first" + jsonObject1);
                        JSONArray steps = (JSONArray) jsonObject1.get("steps");

                        for (int i = 0; i < steps.length(); i++) {
                            JSONObject instruction = (JSONObject) steps.get(i);
                            Log.d(TAG, "instruction " + instruction.get("step").toString());
                            listInstruction.add("Step " + (i + 1) + " : " + instruction.get("step").toString());
                        }
                    } catch (JSONException e) {
                        Log.d(TAG, "onResponse: " + e.getMessage().toString());
                        failToast("Fail to have recipes please reload");
                        e.printStackTrace();
                    }


                }
            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d(TAG, "onFailure: Request failed:" + e.getMessage());
            }
        });
    }

    private void failToast(final String message) {

        new Thread() {
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(view.getContext(), "" + message, Toast.LENGTH_SHORT).show();
                    }
                });

            }

        }.start();
    }

    private void runThread() {

        new Thread() {
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(view.getContext(), "Loading Instruction ...", Toast.LENGTH_SHORT).show();
                        addInstruction();
                    }
                });

            }

        }.start();
    }
}