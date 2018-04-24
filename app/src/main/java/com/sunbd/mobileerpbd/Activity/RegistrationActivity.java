package com.sunbd.mobileerpbd.Activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;
import com.sunbd.mobileerpbd.Helper.ApiUtils;
import com.sunbd.mobileerpbd.Helper.Const;
import com.sunbd.mobileerpbd.Helper.HelperActivity;
import com.sunbd.mobileerpbd.Helper.JsonHelper;
import com.sunbd.mobileerpbd.Helper.SettingsHandler;
import com.sunbd.mobileerpbd.LoginActivity;
import com.sunbd.mobileerpbd.R;
import com.sunbd.mobileerpbd.Retrofit.APIService;

import org.json.JSONException;

import org.json.JSONTokener;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RegistrationActivity extends AppCompatActivity {

    EditText name, email, username, password, confirm_password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        name = (EditText) findViewById(R.id.txtName);
        email = (EditText) findViewById(R.id.txtEmail);
        username = (EditText) findViewById(R.id.txtUsername);
        password = (EditText) findViewById(R.id.txtPwd);
        confirm_password = (EditText) findViewById(R.id.txtConfirmPwd);
        Button Registration =  (Button) findViewById(R.id.btnRegistration);

        TextView login = (TextView) findViewById(R.id.lnkLogin);

        Registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name1 = name.getText().toString().trim();
                String email1 = email.getText().toString().trim();
                String username1 = username.getText().toString().trim();
                String password1 = password.getText().toString().trim();
                String confirm_password1 = confirm_password.getText().toString().trim();

             //   if(!TextUtils.isEmpty(name1) && !TextUtils.isEmpty(password1)&&!TextUtils.isEmpty(email1) && !TextUtils.isEmpty(username1)&&!TextUtils.isEmpty(password1)) {

                    sendPost(name1,email1,username1,password1, confirm_password1);
               // }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        }


    public void sendPost(String name1, String email1, String username1, final String password1, String confirm_password1) {

        // RxJava
        ApiUtils.getAPIService().saveRegistration(name1,email1,username1,password1, confirm_password1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JSONObject>() {

                    @Override
                    public void onCompleted() {

                        System.out.println("----clickss ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("----error  "+e.toString());

                    }

                    @Override
                    public void onNext(JSONObject post) {

                        System.out.println("-------postaaa--"+post);

        try {

            if(post.containsKey("status")) {
                String status = (String) post.get("status");
                if (status.equals("error")) {
                    LinkedTreeMap<String, ArrayList<String>> validationMap = (LinkedTreeMap<String, ArrayList<String>>) post.get("message");

                    System.out.println("---------sixe " + validationMap.size());

                    for (Map.Entry<String, ArrayList<String>> entry : validationMap.entrySet()) {
                        String key = entry.getKey();
                        ArrayList<String> value = entry.getValue();

                        System.out.println(key + " => " + value.get(0));
                    }

                    if (validationMap.containsKey("name")) {
                        name.setError(validationMap.get("name").get(0));
                    }
                    if (validationMap.containsKey("username")) {
                        username.setError(validationMap.get("username").get(0));
                    }
                    if (validationMap.containsKey("email")) {
                        email.setError(validationMap.get("email").get(0));
                    }
                    if (validationMap.containsKey("password")) {
                        password.setError(validationMap.get("password").get(0));
                    }

                    if (validationMap.containsKey("c_password")) {
                        confirm_password.setError(validationMap.get("c_password").get(0));
                    }


                } else if (status.equals("success")) {

                    Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                    startActivity(intent);

                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }


    }
});
    }


}
