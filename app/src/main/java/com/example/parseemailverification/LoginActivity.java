package com.example.parseemailverification;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {

    EditText edtLoginPassword,edt_login_username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edt_login_username=findViewById(R.id.edt_login_username);
        edtLoginPassword=findViewById(R.id.edtLoginpassword);
    }
    public void loginIsPressed(View btnView){


            ParseUser.logInInBackground(edt_login_username.getText().toString(), edtLoginPassword.getText().toString(), new LogInCallback() {
                @Override
                public void done(ParseUser parseUser, ParseException e) {
                    if (parseUser != null) {
                        if (parseUser.getBoolean("emailVerified")) {
                            alertDisplayer("Successful Login", "Welcome back " + edt_login_username.getText().toString() + " !", false);
                        } else {
                            ParseUser.logOut();
                            alertDisplayer(" Login fail", "Please verify your email first", true);
                        }
                    } else {
                        ParseUser.logOut();
                        alertDisplayer(" Login fail", e.getMessage() + "Please re-try!!", true);
                    }
                }
            });

    }
    private void alertDisplayer(String title, String message, boolean b){
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        // don't forget to change the line below with the names of your Activities
                        Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
        AlertDialog ok = builder.create();
        ok.show();
    }
}
