package io.gresse.hugo.tp2;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

import java.util.regex.Pattern;

/**
 * Created by raziel-11 on 04/12/2017.
 */

public class NamePickerActivity extends AppCompatActivity {

    public static final String TAG = NamePickerActivity.class.getSimpleName();

    EditText    mNameEditText;
    EditText    mEmailEditText;
    TextView    mErrorText;
    Button      mSubmitButton;
    String      login;
    String      email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_namepicker);
        mNameEditText = findViewById(R.id.nameEditText);
        mEmailEditText = findViewById(R.id.emailEditText);
        mErrorText = findViewById(R.id.errorText);
        mSubmitButton = findViewById(R.id.submitButton);

        //UserStorage.saveUserInfo(this, "Florian-11", "florian-11@hotmail.fr");

        login = UserStorage.getName(this);
        if (login != null){
            mNameEditText.setText(login);
        }

        getEmailAccount();

        email = UserStorage.getEmail(this);
        if (email != null){
            mEmailEditText.setText(email);
        }

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mNameEditText.getText().toString().isEmpty() || mEmailEditText.getText().toString().isEmpty()){
                    mErrorText.setVisibility(View.VISIBLE);
                    return;
                }
                if(! mNameEditText.getText().toString().isEmpty() || ! mEmailEditText.getText().toString().isEmpty()){
                    mErrorText.setVisibility(View.INVISIBLE);
                    UserStorage.saveUserInfo(getApplicationContext(), mNameEditText.getText().toString(), mEmailEditText.getText().toString());
                    goToMailActivity();
                    return;
                }

            }
        });
    }

    public void goToMailActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
    }

    public void getEmailAccount(){
        Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
        Account[] accounts = AccountManager.get(this).getAccounts();
        for (Account account : accounts) {
            if (emailPattern.matcher(account.name).matches()) {
                String possibleEmail = account.name;
                if (possibleEmail != null) {
                    mEmailEditText.setText(possibleEmail);
                }
            }
        }
    }

}
