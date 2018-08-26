package com.app.retrofitdemo.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.app.retrofitdemo.Model.User;
import com.app.retrofitdemo.R;
import com.app.retrofitdemo.activity.StirUpMain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.realm.Realm;

/**
 * Created by admin on 11/21/2016.
 */

public class RegisterFragment extends BaseFragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private View view;

    private TextInputLayout regEmailWrapper;
    private EditText regEtEmail;
    private TextInputLayout regUsernameWrapper;
    private EditText regEtUsername;
    private TextInputLayout regPasswordWrapper;
    private EditText regEtPassword;
    private RadioButton regRbMale;
    private RadioButton regRbFemale;
    private Button regBtnReg;
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
    private Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    private Matcher matcher;
    private String Gender;
    private Realm realm;
    private String TAG="Regsiter Activity";
    private LoginFragment login;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      view=inflater.inflate(R.layout.activity_register,container,false);
        realm = Realm.getDefaultInstance();
        login=new LoginFragment();
        initComponents();
        setListner();
        prepairView();

        return view;
    }
    @Override
    void initComponents() {
        regEmailWrapper = (TextInputLayout) view.findViewById(R.id.reg_emailWrapper);
        regEtEmail = (EditText)view. findViewById(R.id.reg_et_email);
        regUsernameWrapper = (TextInputLayout) view.findViewById(R.id.reg_usernameWrapper);
        regEtUsername = (EditText) view.findViewById(R.id.reg_et_username);
        regPasswordWrapper = (TextInputLayout)view. findViewById(R.id.reg_passwordWrapper);
        regEtPassword = (EditText) view.findViewById(R.id.reg_et_password);
        regRbMale = (RadioButton)view. findViewById(R.id.reg_rb_male);
        regRbFemale = (RadioButton) view.findViewById(R.id.reg_rb_female);
        regBtnReg = (Button) view.findViewById(R.id.reg_btn_reg);


    }

    @Override
    void setListner() {
        regBtnReg.setOnClickListener(this);
        regRbMale.setOnCheckedChangeListener(this);
        regRbFemale.setOnCheckedChangeListener(this);
    }

    @Override
    void prepairView() {

    }

    @Override
    public void onClick(View view) {
        if (view == regBtnReg) {
            hideKeyboard();

            String email = regEtEmail.getText().toString();
            String password = regEtPassword.getText().toString();
            String username=regEtUsername.getText().toString();
            if (email == null || email.isEmpty() || email.length() == 0) {
                regEmailWrapper.setError("Email cannot be Empty!");

            } else if (password == null || password.isEmpty() || password.length() == 0) {
                regPasswordWrapper.setError("Password cannot be empty!!");
            }
            else if(username == null || username.isEmpty() || username.length() == 0)
            {
                regUsernameWrapper.setError("Username cannot be empty!!");
            }
            else if(Gender == null || Gender.isEmpty() || Gender.length() == 0)
            {
                Toast.makeText(getContext(),"select Gender",Toast.LENGTH_SHORT).show();
            }
            else {
                if (!validateEmail(email)) {
                    regEmailWrapper.setError("Not a valid email address!");
                } else if (!validatePassword(password)) {
                    regPasswordWrapper.setError("Not a valid password!");
                } else {
                    regEmailWrapper.setErrorEnabled(false);
                    regPasswordWrapper.setErrorEnabled(false);
                    regUsernameWrapper.setErrorEnabled(false);
                    doRegister(username,password,email,Gender);


                }
            }
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
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if(compoundButton==regRbMale)
        {
            regRbFemale.setChecked(false);
            Gender="Male";
        }
        else if(compoundButton==regRbFemale)
        {
            regRbMale.setChecked(false);
            Gender="Female";
        }
    }
    public void doRegister(final String un, final String pd, String email, String gender) {

        final User u1 = new User();
        u1.setEmail(email);
        u1.setPassword(pd);
        u1.setUsername(un);
        u1.setGender(gender);
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Toast.makeText(getContext(), "Successfull", Toast.LENGTH_SHORT).show();
                realm.copyToRealm(u1);
                Log.e(TAG,"Username"+un);
                Log.e(TAG,"Password"+pd);
                ((StirUpMain)getActivity()).setLogFragment(new LoginFragment());



            }
        });


    }
}
