package vn.edu.poly.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import vn.edu.poly.todolist.Adapter.NotesAdapter;
import vn.edu.poly.todolist.DAO.NoteObjDAO;
import vn.edu.poly.todolist.DTO.NoteSubject;

public class NotesActivity extends AppCompatActivity {
    ImageView icChooserColor,icBack, icPin, icSetting, icNotification ;
    TextView tvTime,tvTitle;
    EditText edTitle,edContent;
    Button btnNotes;
    LinearLayout layoutBottomNotes,layoutContainerNotes;
    Toolbar toolbarNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        viewMapping();

        icChooserColor.setOnClickListener(image -> {
            dialogChangeColor(NotesActivity.this);
        });
        setPresentTime(tvTime);
        icBack.setOnClickListener(image -> {
            NotesActivity.super.onBackPressed();
        });
        btnNotes.setOnClickListener(button ->{
            addNotes(NotesActivity.this);
        });



    }

    private void viewMapping(){
        icChooserColor = findViewById(R.id.ic_chooser_color);
        tvTime = findViewById(R.id.tv_time_note);
        tvTitle = findViewById(R.id.title_note);
        edTitle = findViewById(R.id.ed_title);
        edContent = findViewById(R.id.ed_content);
        btnNotes = findViewById(R.id.btn_note);
        layoutBottomNotes = findViewById(R.id.layout_bottom_note);
        toolbarNotes = findViewById(R.id.tool_bar_note);
        layoutContainerNotes = findViewById(R.id.layout_container_note);
        icBack = findViewById(R.id.ic_back);
        icPin = findViewById(R.id.ic_pin);
        icNotification = findViewById(R.id.ic_notification);
        icSetting = findViewById(R.id.ic_setting_note);
    }

    public void addNotes(Context context){
        NoteObjDAO objDAO = new NoteObjDAO(context);
        ArrayList<NoteSubject> listNotes = new ArrayList<>();


        if (edTitle.getText().toString().equals("")) {
            Toast.makeText(context, getString(R.string.notification_title_note), Toast.LENGTH_SHORT).show();
        } else if (edContent.getText().toString().equals("")) {
            Toast.makeText(context, getString(R.string.notification_content_note), Toast.LENGTH_SHORT).show();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setIcon(R.drawable.ic_save);
            builder.setTitle(getString(R.string.notification_confirm));
            builder.setMessage(getString(R.string.notification_confirm_2));
            builder.setCancelable(false);

            NoteSubject noteSubject = new NoteSubject();
            String title = edTitle.getText().toString();
            String content = edContent.getText().toString();

            noteSubject.setTitle(title);
            noteSubject.setContent(content);

            long res = objDAO.insertNotes(noteSubject);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (res > 0) {
                        listNotes.clear();
                        listNotes.addAll(objDAO.selectAll());
                        dialog.cancel();
                        Intent intent = new Intent(NotesActivity.this,MainActivity.class);
                        startActivity(intent);

                    }
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(context, "Cancelled! ", Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                }
            });
            AlertDialog sh = builder.create();
            sh.show();

        }
    }



    private void setPresentTime(TextView textView){
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        String time = dateFormat.format(Calendar.getInstance().getTime());
        textView.setText(getString(R.string.text_time_present)+time);
    }

    ImageView check1,check2,check3,check4,check5,color1,color2,color3,
            color4,color5,cavBackgroundHide;
    CardView cavBackground1,cavBackground2,cavBackground3,cavBackground4,cavBackground5;

    private void dialogChangeColor(Context context){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_chooser_color);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.gravity = Gravity.BOTTOM;
        window.setAttributes(layoutParams);
        viewMapping();
        findByIdDialog(dialog);
        LinearLayout dialogLayout = dialog.findViewById(R.id.dialog_chooser_color);
        animationDialog(dialogLayout);

        showOneImageCheck(check1,check2,check3,check4,check5);
        color1.setOnClickListener(ic ->{
            changeBackgroundColorWhite();

        });
        color2.setOnClickListener(ic ->{
            changeBackgroundColorYellow();

        });
        color3.setOnClickListener(ic ->{
            changeBackgroundColorBlue();
        });
        color4.setOnClickListener(ic ->{
            changeBackgroundColorGreen();
        });
        color5.setOnClickListener(ic ->{
            changeBackgroundColorBrown();
        });

        cavBackgroundHide.setOnClickListener(cav ->{
            changeBackgroundLayoutDefault();
        });

        cavBackground1.setOnClickListener(cav ->{
            changeBackgroundLayout1();
        });
        cavBackground2.setOnClickListener(cav ->{
            changeBackgroundLayout2();
        });
        cavBackground3.setOnClickListener(cav ->{
            changeBackgroundLayout3();
        });
        cavBackground4.setOnClickListener(cav ->{
            changeBackgroundLayout4();
        });
        cavBackground5.setOnClickListener(cav ->{
            changeBackgroundLayout5();

        });

        dialog.show();
    }

    private void findByIdDialog(Dialog dialog){
         check1 = dialog.findViewById(R.id.check_1);
         check2 = dialog.findViewById(R.id.check_2);
         check3 = dialog.findViewById(R.id.check_3);
         check4 = dialog.findViewById(R.id.check_4);
         check5 = dialog.findViewById(R.id.check_5);
         color1 = dialog.findViewById(R.id.color1);
         color2 = dialog.findViewById(R.id.color2);
         color3 = dialog.findViewById(R.id.color3);
         color4 = dialog.findViewById(R.id.color4);
         color5 = dialog.findViewById(R.id.color5);
         cavBackgroundHide = dialog.findViewById(R.id.cav_background_hide);
         cavBackground1 = dialog.findViewById(R.id.cav_background_1);
         cavBackground2 = dialog.findViewById(R.id.cav_background_2);
         cavBackground3 = dialog.findViewById(R.id.cav_background_3);
         cavBackground4 = dialog.findViewById(R.id.cav_background_4);
         cavBackground5 = dialog.findViewById(R.id.cav_background_5);
    }



    private void animationDialog(LinearLayout dialog){
        dialog.setAlpha(0f);
        dialog.setTranslationY(150);
        dialog.animate().alpha(1f).translationYBy(-150).setDuration(1000);
    }


    private void animationImg(ImageView imageView){
        imageView.setAlpha(0f);
        imageView.setTranslationY(50);
        imageView.animate().alpha(1f).translationYBy(-50).setDuration(700);
    }

    private void showOneImageCheck(ImageView imageView, ImageView imageView2,ImageView imageView3,ImageView imageView4,ImageView imageView5){
        imageView.setVisibility(View.VISIBLE);
        imageView2.setVisibility(View.INVISIBLE);
        imageView3.setVisibility(View.INVISIBLE);
        imageView4.setVisibility(View.INVISIBLE);
        imageView5.setVisibility(View.INVISIBLE);
    }

    private void changeBackgroundColorWhite(){
        edTitle.setBackground(getDrawable(R.drawable.background_color_edit_text_note_1));
        edContent.setBackground(getDrawable(R.drawable.background_color_edit_text_note_1));
        btnNotes.setBackground(getDrawable(R.drawable.custom_button_notes_1));
        layoutBottomNotes.setBackground(getDrawable(R.color.white));
        toolbarNotes.setBackground(getDrawable(R.color.white));
        changeTextColorNotesBlack();
        showOneImageCheck(check1,check2,check3,check4,check5);
        animationImg(check1);
    }
    private void changeBackgroundColorYellow(){
        edTitle.setBackground(getDrawable(R.drawable.background_color_edit_text_note_2));
        edContent.setBackground(getDrawable(R.drawable.background_color_edit_text_note_2));
        btnNotes.setBackground(getDrawable(R.drawable.custom_button_notes_2));
        layoutBottomNotes.setBackground(getDrawable(R.color.yellow1));
        toolbarNotes.setBackground(getDrawable(R.color.yellow1));
        changeTextColorNotesBlack();
        showOneImageCheck(check2,check1,check3,check4,check5);
        animationImg(check2);
    }
    private void changeBackgroundColorBlue(){
        edTitle.setBackground(getDrawable(R.drawable.background_color_edit_text_note_3));
        edContent.setBackground(getDrawable(R.drawable.background_color_edit_text_note_3));
        btnNotes.setBackground(getDrawable(R.drawable.custom_button_notes_3));
        layoutBottomNotes.setBackground(getDrawable(R.color.blue1));
        toolbarNotes.setBackground(getDrawable(R.color.blue1));
        changeTextColorNotesBlack();
        showOneImageCheck(check3,check1,check2,check4,check5);
        animationImg(check3);
    }

    private void changeBackgroundColorGreen(){
        edTitle.setBackground(getDrawable(R.drawable.background_color_edit_text_note_4));
        edContent.setBackground(getDrawable(R.drawable.background_color_edit_text_note_4));
        btnNotes.setBackground(getDrawable(R.drawable.custom_button_notes_4));
        layoutBottomNotes.setBackground(getDrawable(R.color.green1));
        toolbarNotes.setBackground(getDrawable(R.color.green1));
        changeTextColorNotesBlack();
        showOneImageCheck(check4,check1,check3,check2,check5);
        animationImg(check4);
    }

    private void changeBackgroundColorBrown(){
        edTitle.setBackground(getDrawable(R.drawable.background_color_edit_text_note_5));
        edContent.setBackground(getDrawable(R.drawable.background_color_edit_text_note_5));
        btnNotes.setBackground(getDrawable(R.drawable.custom_button_notes_5));
        layoutBottomNotes.setBackground(getDrawable(R.color.pink_3));
        toolbarNotes.setBackground(getDrawable(R.color.pink_3));
        changeTextColorNotesBlack();
        showOneImageCheck(check5,check1,check3,check4,check2);
        animationImg(check5);
    }

    private void changeBackgroundLayoutDefault(){
        viewMapping();
        layoutContainerNotes.setBackground(getDrawable(R.color.gray_3));
        toolbarNotes.setBackground(getDrawable(R.color.white));
        layoutBottomNotes.setBackground(getDrawable(R.color.white));
        changeBackgroundColorWhite();
    }


    private void changeBackgroundLayout1(){
        layoutContainerNotes.setBackground(getDrawable(R.drawable.background_1));
        setBackgroundTransparent();
    }
    private void changeBackgroundLayout2(){
        layoutContainerNotes.setBackground(getDrawable(R.drawable.background_2));
        setBackgroundTransparent();
    }
    private void changeBackgroundLayout3(){
        layoutContainerNotes.setBackground(getDrawable(R.drawable.background_3));
        setBackgroundTransparent();
    }
    private void changeBackgroundLayout4(){
        layoutContainerNotes.setBackground(getDrawable(R.drawable.background_4));
        setBackgroundTransparent();
    }
    private void changeBackgroundLayout5(){
        layoutContainerNotes.setBackground(getDrawable(R.drawable.background_5));
        setBackgroundTransparent();
    }

    private void setBackgroundTransparent(){
        edContent.setBackground(getDrawable(android.R.color.transparent));
        edTitle.setBackground(getDrawable(android.R.color.transparent));
        btnNotes.setBackground(getDrawable(android.R.color.transparent));
        toolbarNotes.setBackground(getDrawable(android.R.color.transparent));
        layoutBottomNotes.setBackground(getDrawable(android.R.color.transparent));
        changeTextColorNotesWhite();
    }


    private void changeTextColorNotesWhite(){
        btnNotes.setTextColor(Color.parseColor("#FFFFFF"));
        tvTime.setTextColor(Color.parseColor("#FFFFFF"));
        tvTitle.setTextColor(Color.parseColor("#FFFFFF"));
        edContent.setTextColor(Color.parseColor("#FFFFFF"));
        edTitle.setTextColor(Color.parseColor("#FFFFFF"));
        edContent.setHintTextColor(Color.parseColor("#FFFFFF"));
        edTitle.setHintTextColor(Color.parseColor("#FFFFFF"));
        icBack.setColorFilter(Color.parseColor("#FFFFFF"));
        icPin.setColorFilter(Color.parseColor("#FFFFFF"));
        icNotification.setColorFilter(Color.parseColor("#FFFFFF"));
        icSetting.setColorFilter(Color.parseColor("#FFFFFF"));
        icChooserColor.setColorFilter(Color.parseColor("#FFFFFF"));
    }
    private void changeTextColorNotesBlack(){
        btnNotes.setTextColor(Color.parseColor("#000000"));
        tvTitle.setTextColor(Color.parseColor("#000000"));
        tvTime.setTextColor(Color.parseColor("#000000"));
        edContent.setTextColor(Color.parseColor("#000000"));
        edTitle.setTextColor(Color.parseColor("#000000"));
        edContent.setHintTextColor(Color.parseColor("#000000"));
        edTitle.setHintTextColor(Color.parseColor("#000000"));
        icBack.setColorFilter(Color.parseColor("#191919"));
        icPin.setColorFilter(Color.parseColor("#191919"));
        icNotification.setColorFilter(Color.parseColor("#191919"));
        icSetting.setColorFilter(Color.parseColor("#191919"));
        icChooserColor.setColorFilter(Color.parseColor("#191919"));
    }


}