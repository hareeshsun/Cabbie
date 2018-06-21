package com.suntechnologies.cabbie;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login_page);

        final EditText email =  (EditText) findViewById(R.id.email);
        final EditText password =  (EditText) findViewById(R.id.password);
        Button login = (Button) findViewById(R.id.login);
        TextView signUp = (TextView) findViewById(R.id.signUpText);

/*        email.setText("admin@suntechnologies.com");
        password.setText("Reset123");*/

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignUpPage.class);
                startActivity(intent);
                finish();
            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.white_background_with_black_border,null));
            }
        });

        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                password.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.white_background_with_black_border,null));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email.getText().toString().equalsIgnoreCase("admin@suntechnologies.com") && password.getText().toString().equalsIgnoreCase("Reset123")){
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    if (email.getText().toString().length() > 7 && password.getText().length() > 5) {
                        HelperMethods.showDialog("Error","Invalid email or password");
                    }
                    else {
                        HelperMethods.showDialog("Error","please fill required fields");
                        if(email.getText().toString().length() == 0)
                        email.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.white_background_with_red_border,null));

                        if(password.getText().toString().length() == 0)
                            password.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.white_background_with_red_border,null));
                    }
                }
            }
        });

    }

}
