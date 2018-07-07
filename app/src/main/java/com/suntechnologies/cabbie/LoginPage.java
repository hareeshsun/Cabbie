package com.suntechnologies.cabbie;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import dmax.dialog.SpotsDialog;

public class LoginPage extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private String TAG ="LoginPaGE";
    SharedPreferences.Editor editor;
    private String USER_TOKEN_KEY = "USERTOKEN";
    private String USER_UID = "USERUID";
    SharedPreferences preferences;
    private Dialog loadingDialog;
    private DatabaseReference mDatabase;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login_page);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        final EditText email =  (EditText) findViewById(R.id.email);
        final EditText password =  (EditText) findViewById(R.id.password);
        final Button login = (Button) findViewById(R.id.login);
        TextView signUp = (TextView) findViewById(R.id.signUpText);

        loadingDialog = new SpotsDialog(this,"Logging...");
        preferences = getSharedPreferences(USER_TOKEN_KEY, Context.MODE_PRIVATE);
        editor =preferences.edit();

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignUpPage.class);
                startActivity(intent);
                finish();
            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {
                email.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.white_background_with_black_border,null));
            }
        });

        password.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {
                password.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.white_background_with_black_border,null));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {
                if (email.getText().toString().trim().length()>0 && password.getText().toString().trim().length()>0)
                {
                    if(!HelperMethods.isValidEmaillId(email.getText().toString().trim())){
                        email.setError("Email is not valid");
                    }else{
                        loadingDialog.show();
                        mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(LoginPage.this, new OnCompleteListener<AuthResult>()
                        {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task)
                            {
                                if(loadingDialog != null && loadingDialog.isShowing()){
                                    loadingDialog.dismiss();
                                }
                                if (task.isSuccessful())
                                {
                                    editor.putString(USER_UID,mAuth.getCurrentUser().getUid() );
                                    editor.apply();

                                    Intent intent = new Intent(LoginPage.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();

                                } else
                                {
                                    try {
                                        throw task.getException();
                                    } catch(FirebaseAuthWeakPasswordException e) {

                                        HelperMethods.showDialog(LoginPage.this, "Error", "Password is week");

                                    } catch(FirebaseAuthInvalidCredentialsException e) {
                                        HelperMethods.showDialog(LoginPage.this, "Error", "this credential is not available!");

                                    } catch(FirebaseAuthUserCollisionException e) {
                                        HelperMethods.showDialog(LoginPage.this, "Error", "This email is already is registered !");

                                    } catch(Exception e) {
                                        Log.e(TAG, e.getMessage());
                                    }
                                    // If sign in fails, display a message to the user.
                                    HelperMethods.showDialog(LoginPage.this, "signInWithEmail:failure", task.getException().toString());
                                }
                            }
                        });
                    }
                }
                else{
                    HelperMethods.showDialog(LoginPage.this,"Alert", "Email or Password filed cannot be empty! ");
                }
            }
        });

    }
}
