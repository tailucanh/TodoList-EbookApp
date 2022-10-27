package vn.edu.poly.todolist.ActivityExtra;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import vn.edu.poly.todolist.MainActivity;
import vn.edu.poly.todolist.R;

public class SignUpActivity extends AppCompatActivity {
    ConstraintLayout layout;
    TextInputLayout tilAccountName,tilPass;
    Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        viewMapping();

        SharedPreferences  sharedPreferences = getSharedPreferences("form_login", Context.MODE_PRIVATE);

        btnSignUp.setOnClickListener(btn ->{
                SharedPreferences.Editor editor = sharedPreferences.edit();
                String accountName = tilAccountName.getEditText().getText().toString();
                String pass = tilPass.getEditText().getText().toString();
                if (accountName.equals("")){
                    errSnackBar(layout,getString(R.string.error_account_name));
                }else if(pass.equals("")){
                    errSnackBar(layout,getString(R.string.error_pass));
                }else{
                    successSnackBar(layout,getString(R.string.successful_text_2));
                    editor.putString("name",accountName);
                    editor.putString("pass",pass);
                    editor.commit();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(getBaseContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }, 1000);
                    finish();
                }

        });


    }

    private void viewMapping(){
        layout = findViewById(R.id.activitySignUp);
        tilAccountName = findViewById(R.id.input_layout_user_sign_up);
        tilPass = findViewById(R.id.input_layout_pass_sign_up);
        btnSignUp = findViewById(R.id.button_sign_up);
        animationLayout(layout);

    }

    private void errSnackBar(ConstraintLayout constraintLayout,String errText){
        final Snackbar snackbar = Snackbar.make(constraintLayout,"",Snackbar.LENGTH_SHORT);
        View view = getLayoutInflater().inflate(R.layout.custom_snackbar_error, null);
        snackbar.getView().setBackgroundColor(Color.TRANSPARENT);
        Snackbar.SnackbarLayout  snackBarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
        snackBarLayout.setPadding(0,0,0,0);
        TextView tv_err = view.findViewById(R.id.tv_error);
        tv_err.setText(errText);

        snackBarLayout.addView(view, 0);
        snackbar.show();
    }

    private void successSnackBar(ConstraintLayout constraintLayout,String text){
        final Snackbar snackbar = Snackbar.make(constraintLayout,"",Snackbar.LENGTH_LONG);
        View view = getLayoutInflater().inflate(R.layout.custom_success_text, null);
        snackbar.getView().setBackgroundColor(Color.TRANSPARENT);
        Snackbar.SnackbarLayout  snackBarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
        snackBarLayout.setPadding(0,0,0,0);
        TextView tv_success = view.findViewById(R.id.tv_success);
        tv_success.setText(text);

        snackBarLayout.addView(view, 0);
        snackbar.show();
    }


    private void animationLayout(ConstraintLayout layout){
        layout.setAlpha(0f);
        layout.setTranslationZ(-100);
        layout.animate().alpha(1f).translationZBy(100).setDuration(1500);

    }

}