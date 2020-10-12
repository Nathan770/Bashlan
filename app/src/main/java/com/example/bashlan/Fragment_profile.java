package com.example.bashlan;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

public class Fragment_profile extends Fragment {
    private static final String TAG = "nathan";
    protected View view;
    private String mEmail = "";
    private String mName = "";
    private String mUrlPicture = "";
    private TextView profile_LBL_name;
    private TextView profile_LBL_email;
    private TextInputLayout profile_EDT_weight;
    private TextInputLayout profile_EDT_height;
    private MaterialButton profile_BTN_imc;
    private TextView profile_LBL_imc;
    private ImageView profile_IMG_personne;

    public static Fragment_profile newInstance(String email, String name, String urlPicture) {
        Fragment_profile fragment = new Fragment_profile();
        fragment.mEmail = email;
        fragment.mName = name;
        fragment.mUrlPicture = urlPicture;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_profile, container, false);
        }
        findViews();
        changeProfile();
        getImc();

        return view;
    }

    private void getImc() {
        profile_BTN_imc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double imc = 0.0;
                String weight = profile_EDT_weight.getEditText().getText().toString();
                String height = profile_EDT_height.getEditText().getText().toString();
                if ((!weight.equals("")) && (!height.equals(""))) {
                    double wei = Integer.parseInt(weight.toString());
                    double hei = Integer.parseInt(height.toString());

                    imc = (wei / ((hei * hei) / 10000));
                    profile_LBL_imc.setText(String.format("Your IMC is : %.2f", imc));
                }
                if (imc > 25) {
                    Toast.makeText(view.getContext(), "You are in over weight", Toast.LENGTH_SHORT).show();
                } else if (imc < 18.5) {
                    Toast.makeText(view.getContext(), "You are thin CONTINUE !!!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(view.getContext(), "You are in a good shape", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void changeProfile() {
        if (!mEmail.equals("")) {
            profile_LBL_email.setText(mEmail);
        }
        if (mName == null) {
            profile_LBL_name.setText("No name");
        } else if (!mName.equals("")) {
            profile_LBL_name.setText(mName);
        }
        if (mUrlPicture.equals("")) {
            profile_IMG_personne.setImageResource(R.drawable.ic_profile);
        } else {
            Glide
                    .with(view.getContext())
                    .load(mUrlPicture)
                    .centerCrop()
                    .into(profile_IMG_personne);
        }
    }

    private void findViews() {
        profile_LBL_name = view.findViewById(R.id.profile_LBL_name);
        profile_LBL_email = view.findViewById(R.id.profile_LBL_email);

        profile_EDT_weight = view.findViewById(R.id.profile_EDT_weight);
        profile_EDT_height = view.findViewById(R.id.profile_EDT_height);
        profile_BTN_imc = view.findViewById(R.id.profile_BTN_imc);
        profile_LBL_imc = view.findViewById(R.id.profile_LBL_imc);

        profile_IMG_personne = view.findViewById(R.id.profile_IMG_personne);

    }
}