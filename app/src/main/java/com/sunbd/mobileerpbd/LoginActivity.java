package com.sunbd.mobileerpbd;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sunbd.mobileerpbd.Helper.ApiUtils;
import com.sunbd.mobileerpbd.Helper.Const;
import com.sunbd.mobileerpbd.Helper.HelperActivity;
import com.sunbd.mobileerpbd.Helper.SettingsHandler;
import com.sunbd.mobileerpbd.R;
import com.sunbd.mobileerpbd.Retrofit.APIService;


import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity{

    EditText mUsernameView,mPasswordView;
    Button login;
    private APIService mAPIService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SettingsHandler.getInstance().load(getSharedPreferences(Const.USER_PREFS,
                Activity.MODE_PRIVATE));


        mUsernameView = (EditText) findViewById(R.id.username);
        mPasswordView = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        mAPIService = ApiUtils.getAPIService();
        Map<String, String> map = new HashMap<>();
        getList(map);




        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("----click ");
                String username = mUsernameView.getText().toString().trim();
                String password = mPasswordView.getText().toString().trim();
                if(!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
                    System.out.println("-----ssss");
                    sendPost(username, password);
                }
            }
        });
    }

    public void sendPost(String title, String body) {

        // RxJava
        mAPIService.saveLogin(title, body).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Object>() {


                    @Override
                    public void onCompleted() {

                        System.out.println("----clickss ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("----error  "+e.toString());

                    }

                    @Override
                    public void onNext(Object post) {
                        System.out.println("-------post--"+post);
                        JSONObject response = HelperActivity.objectToJSONObject(post);

                        System.out.println("------------responsecc--" +response);

                        try {
                            String token = response.getString("token");
                            SettingsHandler.getInstance().setAccess_token(token);
                            SettingsHandler.getInstance().commit(getSharedPreferences(Const.USER_PREFS,
                                    Activity.MODE_PRIVATE).edit());

                        } catch (JSONException e) {

                            e.printStackTrace();
                        }

                        Map<String, String> map = new HashMap<>();
                        map.put("Accept", "application/json");
                        map.put("Authorization", SettingsHandler.getInstance().getAccess_token());
                        getList(map);


                        //showResponse(post.toString());
                    }
                });
    }

    public void getList(Map<String, String> map)
    {

        mAPIService.getList().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Object>() {


                    @Override
                    public void onCompleted() {

                        System.out.println("---- ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("----error  "+e.toString());

                    }

                    @Override
                    public void onNext(Object post) {
                        System.out.println("-------post--"+post);
                        JSONObject response = HelperActivity.objectToJSONObject(post);

                    }
                });

    }



}

