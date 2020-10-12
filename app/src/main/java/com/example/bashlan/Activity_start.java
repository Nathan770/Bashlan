package com.example.bashlan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class Activity_start extends AppCompatActivity implements Activity_dialog.ActivityDialogListener{
    private static final String TAG = "nathan";
    private static final int RC_SIGN_IN = 100;
    private FirebaseAuth mAuth;
    private String email = "";
    private String name = "";
    private String password = "";
    private String mUrl = "";
    private boolean loginByMail = false;



    private SignInButton start_BTN_signGoogle;
    private GoogleSignInClient mGoogleSignInClient;

    private LoginButton start_BTN_signFacebook;
    private CallbackManager callbackManager;

    private MaterialButton start_BTN_create;
    private MaterialButton start_BTN_login;
    private TextInputLayout start_EDT_email;
    private TextInputLayout start_EDT_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        findViews();
        checkLogin();
        checkLogout();
        btnAction();

    }

    private void checkLogin() {
        try{
            if(AccessToken.isCurrentAccessTokenActive()){
                Log.d(TAG, "onCreate: login by facebook");
                goToActivity(mUrl);
            }else if (GoogleSignIn.getLastSignedInAccount(this.getApplicationContext()) != null){
                Log.d(TAG, "onCreate: login by google");
                goToActivity(mUrl);
            }else if(loginByMail){
                Log.d(TAG, "onCreate: login by mail");
                goToActivity(mUrl);
            }
        }catch (Exception e){
            Log.d(TAG, "checkLogin: "+e.getMessage());
            Toast.makeText(this,""+e.getMessage(),Toast.LENGTH_SHORT);
        }

    }

    private void checkLogout() {
        try{
            boolean check = false;
            check = getIntent().getBooleanExtra("logout",check);
            if(check == true){
                if(AccessToken.isCurrentAccessTokenActive()){
                    Log.d(TAG, "onCreate: logout by facebook");
                    LoginManager.getInstance().logOut();
                }else if (GoogleSignIn.getLastSignedInAccount(this.getApplicationContext()) != null){
                    Log.d(TAG, "onCreate: logout by google");
                    mGoogleSignInClient.signOut();
                }else if(loginByMail){
                    Log.d(TAG, "onCreate: logout by mail");
                    loginByMail = false;
                }
                check = false;
            }
        }catch (Exception e){
            Log.d(TAG, "checkLogout: "+e.getMessage());
            Toast.makeText(this,""+e.getMessage(),Toast.LENGTH_SHORT);
        }

    }

    private void btnAction() {

        start_BTN_signGoogle.setOnClickListener(googleListener);

        start_BTN_signFacebook.registerCallback(callbackManager, facebookCallback);

        start_BTN_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });

        start_BTN_login.setOnClickListener(mailAndPasswordListener);

    }

    View.OnClickListener googleListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            signInGoogle();
        }
    };

    FacebookCallback<LoginResult> facebookCallback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(final LoginResult loginResult) {
            Log.d(TAG, "onSuccess: " + loginResult.toString());
            //Request the users info
            GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken()
                    , new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject object, GraphResponse response) {
                            Log.d(TAG, "onCompleted: response: " + response.toString());
                            Log.d(TAG, "onCompleted: json: " + object.toString());
                            try {
                                name = (String) object.get("name");
                                email = (String) object.get("email");
                            } catch (JSONException e) {
                                Log.d(TAG, "JSONException : "+e.getMessage());
                            }
                            mUrl = "https://graph.facebook.com/"+loginResult.getAccessToken().getUserId()+"/picture?return_ssl_resources=1";
                            goToActivity(mUrl);
                        }
                    });
            Bundle parameters = new Bundle();
            request.setParameters(parameters);
            request.executeAsync();
        }

        @Override
        public void onCancel() {
            Log.d(TAG, "onCancel: ");
        }

        @Override
        public void onError(FacebookException error) {
            Log.d(TAG, "onError: " + error.getMessage());
        }
    };

    View.OnClickListener mailAndPasswordListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(start_EDT_email.getEditText().getText().toString() == null || start_EDT_email.getEditText().getText().toString().equals("")){
                Toast.makeText(getApplicationContext(),"Fail to login email empty",Toast.LENGTH_SHORT).show();
            }else if(start_EDT_password.getEditText().getText().toString() == null || start_EDT_password.getEditText().getText().toString().equals("")){
                Toast.makeText(getApplicationContext(),"Fail to login password empty",Toast.LENGTH_SHORT).show();
            }else {
                mAuth.signInWithEmailAndPassword(start_EDT_email.getEditText().getText().toString(),start_EDT_password.getEditText().getText().toString())
                        .addOnCompleteListener(Activity_start.this,new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                try{
                                    if(task.isSuccessful()){
                                        Log.d(TAG, "onComplete: login " + task.isSuccessful());
                                        Toast.makeText(getApplicationContext(),"Login Successful",Toast.LENGTH_SHORT).show();
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        updateUI(user);
                                        loginByMail = true;
                                        goToActivity("");
                                    }else {
                                        Toast.makeText(getApplicationContext(),"Fail to login "+task.getException().getMessage().toString(),Toast.LENGTH_LONG).show();
                                    }
                                }catch (Exception e){
                                    Log.d(TAG, "onException: "+e.getMessage().toString());
                                    Toast.makeText(getApplicationContext(),"Fail to login "+e.getMessage().toString(),Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
            }

        }
    };

    private void goToActivity(String url) {
        Intent intent = new Intent(Activity_start.this, MainActivity.class);
        intent.putExtra("email", email);
        intent.putExtra("name", name);
        intent.putExtra("urlPicture",url);
        startActivity(intent);
    }

    private void openDialog() {
        Activity_dialog activity_dialog = new Activity_dialog();
        activity_dialog.show(getSupportFragmentManager(),"activity dialog");
    }

    private void findViews() {
        mAuth = FirebaseAuth.getInstance();
        start_BTN_create = findViewById(R.id.start_BTN_create);
        start_BTN_login = findViewById(R.id.start_BTN_login);
        start_EDT_email = findViewById(R.id.start_EDT_email);
        start_EDT_password = findViewById(R.id.start_EDT_password);

        start_BTN_signGoogle = findViewById(R.id.start_BTN_signGoogle);

        start_BTN_signFacebook = findViewById(R.id.start_BTN_signFacebook);
        callbackManager = CallbackManager.Factory.create();
        start_BTN_signFacebook.setPermissions(Arrays.asList("public_profile", "email"));
    }

    private void signInGoogle() {

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.toString());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.d(TAG, "Google sign in failed " + e);
                Toast.makeText(this, "Google sign in failed", Toast.LENGTH_LONG).show();
            }
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
        if (currentUser != null) {
            email = currentUser.getEmail();
            name = currentUser.getDisplayName();
        }
    }


    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            updateUI(null);
                        }

                        goToActivity("");
                    }
                });
    }

    @Override
    public void getInfoUser(String mName, final String mEmail, final String mPassword) {
            email = mEmail;
            name = mName;
            password = mPassword;

            if (mPassword.length() <= 6){
                Toast.makeText(this, "Password should be at least 6 characters", Toast.LENGTH_LONG).show();
            }else if(mEmail.equals("") ||  mName.equals("")) {
                Toast.makeText(this, "Name or Email is empty \n Try again !", Toast.LENGTH_LONG).show();
            }else {

            mAuth.createUserWithEmailAndPassword(mEmail,mPassword)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Log.d(TAG, "onComplete: reussi " + task.isSuccessful());
                                Toast.makeText(getApplicationContext(),"Subsribe Successful",Toast.LENGTH_SHORT).show();
                                FirebaseUser user = mAuth.getCurrentUser();
                                FirebaseUser user1 = FirebaseAuth.getInstance().getCurrentUser();

                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(name)
                                        .build();

                                user1.updateProfile(profileUpdates);

                                updateUI(user);
                            }else {
                                Log.d(TAG, "onfail: "+ task.getResult());
                                Toast.makeText(getApplicationContext(),"Fail to  Subsribe "+task.getException().getMessage().toString(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

            }

    }
}