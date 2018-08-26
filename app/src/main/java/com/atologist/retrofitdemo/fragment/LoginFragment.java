package com.app.retrofitdemo.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.retrofitdemo.Model.User;
import com.app.retrofitdemo.R;
import com.app.retrofitdemo.activity.MainActivity;
import com.app.retrofitdemo.activity.StirUpMain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by admin on 11/21/2016.
 */

public class LoginFragment extends android.support.v4.app.Fragment {
    private View view;
    private TextInputLayout un_textlayout;
    private TextInputLayout pwd_textlayout;
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
    private Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    private Matcher matcher;
    private Button btn_signin;
    private EditText et_pwd;
    private EditText et_un;
    private Realm realm;
    private String TAG = "Login Fragment";
    private TextView tv_register;
    private RegisterFragment register;
    private LoginFragment loginFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_login, container, false);
        realm = Realm.getDefaultInstance();
        un_textlayout = (TextInputLayout) view.findViewById(R.id.usernameWrapper);
        pwd_textlayout = (TextInputLayout) view.findViewById(R.id.passwordWrapper);
        et_un = (EditText)view. findViewById(R.id.et_username);
        et_pwd = (EditText) view.findViewById(R.id.et_password);
        btn_signin = (Button) view.findViewById(R.id.btn_signin);
        tv_register=(TextView)view.findViewById(R.id.tv_register);
        register=new RegisterFragment();

        loginFragment = new LoginFragment();
        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard();

                String username = un_textlayout.getEditText().getText().toString();
                String password = pwd_textlayout.getEditText().getText().toString();
                if (username == null || username.isEmpty() || username.length() == 0) {
                    un_textlayout.setError("Username cannot be Empty!");

                } else if (password == null || password.isEmpty() || password.length() == 0) {
                    pwd_textlayout.setError("Password cannot be empty!!");
                } else {
                    if (!validatePassword(password)) {
                        pwd_textlayout.setError("Not a valid password!");
                    } else {
                        un_textlayout.setErrorEnabled(false);
                        pwd_textlayout.setErrorEnabled(false);
                        doLogin(username, password);
                    }
                }

            }
        });
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* getFragmentManager().beginTransaction()
                        .replace(R.id.activity_login, register).commit();*/

                ((StirUpMain)getActivity()).setRegFragment(register);

            }
        });


        return view;
    }


    public void doLogin(String un, String pd) {


        RealmQuery<User> query = realm.where(User.class);
        query.equalTo("username", un);
        query.equalTo("password", pd);

        RealmResults<User> results = query.findAll();
        Log.e(TAG, "ResultSize" + results.size());
        if (results.size() == 0) {
            Toast.makeText(getContext(), "Invalid Username/Password", Toast.LENGTH_SHORT).show();

        } else {
            SharedPreferences sp = getContext().getSharedPreferences("LoginPref", MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("islogin", "true");
            editor.commit();


            getActivity().finish();
            Intent intent = new Intent(getContext(), MainActivity.class);
            startActivity(intent);
        }



    }

    public boolean validateEmail(String email) {
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean validatePassword(String password) {
        return password.length() > 5;
    }

    private void hideKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
