package vn.edu.poly.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.util.Property;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.DexterBuilder;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.kyanogen.signatureview.SignatureView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Signature;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import pl.droidsonroids.gif.GifImageButton;
import yuku.ambilwarna.AmbilWarnaDialog;

public class DrawingBoardActivity extends AppCompatActivity {

    TextView textTitleToolBar;
    ImageView icBack;
    ImageButton icEraser,icChooserColor,icReturn,icPen,icSave;
    GifImageButton icPenGif;
    SeekBar seekBar;
    SignatureView signatureView;
    Bitmap bitmap;
    int defaultColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing_board);
        viewMapping();
        icBack.setOnClickListener(ic -> {
                DrawingBoardActivity.super.onBackPressed();
        });

        oscillateDemo(textTitleToolBar,getApplicationContext());
        askPermission();
        defaultColor =  ContextCompat.getColor(DrawingBoardActivity.this,R.color.black);
        icChooserColor.setOnClickListener(ic ->{
                openColorPicker();
        });

        seekBar.setVisibility(View.GONE);
        icPen.setVisibility(View.INVISIBLE);
        icPenGif.setVisibility(View.VISIBLE);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                signatureView.setPenSize(progress);
                seekBar.setMax(50);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        icPenGif.setOnClickListener(ic ->{
                icPenGif.setVisibility(View.INVISIBLE);
                icPen.setVisibility(View.VISIBLE);
                seekBar.setVisibility(View.VISIBLE);

        });
        icPen.setOnClickListener(ic ->{
                icPenGif.setVisibility(View.VISIBLE);
                seekBar.setVisibility(View.GONE);

        });
        icEraser.setOnClickListener(ic ->{
                signatureView.clearCanvas();

        });

        icSave.setOnClickListener(ic ->{
            bitmap = signatureView.getSignatureBitmap();


        });

    }




    private void viewMapping(){
        textTitleToolBar = findViewById(R.id.title_tool_bar_drawing);
        icBack = findViewById(R.id.ic_back);
        signatureView = findViewById(R.id.drawingBoard);
        icEraser = findViewById(R.id.ic_eraser);
        icPenGif = findViewById(R.id.ic_pen_gif);
        icChooserColor = findViewById(R.id.ic_color_palette);
        icReturn = findViewById(R.id.ic_return);
        icPen = findViewById(R.id.ic_pen);
        icSave = findViewById(R.id.ic_save);
        seekBar = findViewById(R.id.seekbar_size);
    }

    private void openColorPicker(){
        AmbilWarnaDialog dialog = new AmbilWarnaDialog(this,defaultColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                defaultColor = color;
                signatureView.setPenColor(color);
            }
        });
        dialog.show();
    }


    private void askPermission(){
        Dexter.withContext(this)
                .withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
                ).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {

            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                    permissionToken.continuePermissionRequest();
            }

        }).check();

    }

    public void oscillateDemo(final TextView textView, Context context) {
        final int whiteColor = ContextCompat.getColor(context, R.color.color_animate);
        final int greenColor = ContextCompat.getColor(context, R.color.color_animate_2);

        final int counter = 10000;

        Thread oscillateThread = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < counter; i++) {
                    final int fadeToColor = (i % 2 == 0) ? greenColor : whiteColor;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            animateTextViewColors(textView, fadeToColor);
                        }
                    });
                    try { Thread.sleep(2000);
                    } catch (InterruptedException iEx) {}
                }
            }
        };

        oscillateThread.start();
    }

    public  void animateTextViewColors(TextView textView, Integer colorTo) {
        final Property<TextView, Integer> property = new Property<TextView, Integer>(int.class, "textColor") {
            @Override
            public Integer get(TextView object) {
                return object.getCurrentTextColor();
            }
            @Override
            public void set(TextView object, Integer value) {
                object.setTextColor(value);
            }
        };

        final ObjectAnimator animator = ObjectAnimator.ofInt(textView, property, colorTo);
        animator.setDuration(8533L);
        animator.setEvaluator(new ArgbEvaluator());
        animator.setInterpolator(new DecelerateInterpolator(2));
        animator.start();
    }

}








