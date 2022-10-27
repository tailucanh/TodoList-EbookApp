package vn.edu.poly.todolist.ActivityExtra;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;

import android.os.Build;
import android.os.Bundle;

import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import vn.edu.poly.todolist.ChangeLanguage.LanguageManager;
import vn.edu.poly.todolist.R;


public class HelloFullScreenActivity extends AppCompatActivity {
    Button btn_start;
    TextView text1, text2,language;
    ImageView background;
    LanguageManager languageManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_full_screen);
        viewMapping();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        language.setText("Vietnamese");
        language.setCompoundDrawablesWithIntrinsicBounds(R.drawable.vietnam, 0, 0, 0);
        language.setCompoundDrawablePadding(10);


         languageManager= new LanguageManager(getBaseContext());
        language.setOnClickListener(view ->{
                languageManager.updateResource("en");
                recreate();
                overridePendingTransition(0, 0);

        });
        btn_start.setOnClickListener(btn ->{
                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(intent);
                finish();
        });

    }


    @Override
    protected void onRestart() {
        super.onRestart();

        language.setText("English");
        language.setCompoundDrawablesWithIntrinsicBounds(R.drawable.uk, 0, 0, 0);
        language.setCompoundDrawablePadding(10);

    }

   private void viewMapping(){
        background = findViewById(R.id. background_hello_screen);
        text1 = findViewById(R.id.text1);
        text2 = findViewById(R.id.text2);
        btn_start = findViewById(R.id.btn_start);
        language = findViewById(R.id.tv_language);
        animationButton(btn_start);
        animationImageView(background);
        animationTextView(text1);
        animationTextView(text2);
        animationTextView(language);
    }



    private void animationTextView(TextView tv){
        tv.setAlpha(0f);
        tv.setTranslationZ(-150);
        tv.animate().alpha(1f).translationZBy(150).setDuration(1500);

    }


    private void animationImageView(ImageView img){
        img.setAlpha(0f);
        img.setTranslationZ(-150);
        img.animate().alpha(1f).translationZBy(150).setDuration(1500);
    }
    private void animationButton(Button btn){
        btn.setAlpha(0f);
        btn.setTranslationZ(-150);
        btn.animate().alpha(1f).translationZBy(150).setDuration(1500);
    }




}