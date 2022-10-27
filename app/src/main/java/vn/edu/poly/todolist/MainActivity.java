package vn.edu.poly.todolist;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import vn.edu.poly.todolist.Adapter.AdapterTabLayoutMain;
import vn.edu.poly.todolist.AnimationViewPage2.DepthPageTransformer;


public class MainActivity extends AppCompatActivity {

    DrawerLayout layoutContainer;
    CircleImageView imgUser;
    ImageButton icAddImg;
    FloatingActionButton button;
    BottomAppBar bottomAppBar;
    TabLayout tabLayout;
    ViewPager2 viewPagerTab;

    private boolean isBackPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewMapping();
        animationLayout(layoutContainer);


        icAddImg.setOnClickListener(icBtn ->{
            ImagePicker.with(MainActivity.this).crop()
                    .compress(1024)
                    .maxResultSize(1080, 1080)
                    .start();
        });

        viewPagerTab.setPageTransformer(new DepthPageTransformer());
        AdapterTabLayoutMain adapter = new AdapterTabLayoutMain(this);
        viewPagerTab.setAdapter(adapter);


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPagerTab.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPagerTab.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });

        button.setOnClickListener(button ->{
                Intent intent = new Intent(getBaseContext(),NotesActivity.class);
                startActivity(intent);
        });




        bottomAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case  R.id.ic_home:
                        break;
                    case  R.id.ic_draw:
                        Intent intent = new Intent(getBaseContext(),DrawingBoardActivity.class);
                        startActivity(intent);
                        break;
                    case  R.id.ic_mic:

                        break;
                    case  R.id.ic_photo:
                        ImagePicker.with(MainActivity.this).crop()
                                .compress(1024)
                                .maxResultSize(1080, 1080)
                                .start();
                        break;

                }
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (isBackPressedOnce) {
            super.onBackPressed();
            return;
        }
        Toast.makeText(MainActivity.this, getString(R.string.text_pressed_app), Toast.LENGTH_SHORT).show();
        isBackPressedOnce = true;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isBackPressedOnce = false;
            }
        },2000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            Uri uri = data.getData();
            imgUser.setImageURI(uri);

    }




    private void viewMapping(){
        layoutContainer = findViewById(R.id.container_main);
        imgUser = findViewById(R.id.img_user);
        icAddImg = findViewById(R.id.ic_add_img);
        tabLayout = findViewById(R.id.tab_layout);
        button = findViewById(R.id.floating_button);
        bottomAppBar = findViewById(R.id.bottom_appbar);
        viewPagerTab = findViewById(R.id.viewpager_tab);


    }



    private void loadImageUser(Context context){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.custom_layout_upload_image);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView tvAddImg = dialog.findViewById(R.id.add_img);
        TextView tvChooserImg = dialog.findViewById(R.id.chooser_img);
        tvAddImg.setOnClickListener(img ->{
                dialog.dismiss();
        });
        tvChooserImg.setOnClickListener(tv ->{
            dialog.dismiss();
        });

        dialog.show();
    }


    private void animationLayout(DrawerLayout layout){
        layout.setAlpha(0f);
        layout.setTranslationZ(-100);
        layout.animate().alpha(1f).translationZBy(100).setDuration(1500);

    }






}