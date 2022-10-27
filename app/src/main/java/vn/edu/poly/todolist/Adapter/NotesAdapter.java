package vn.edu.poly.todolist.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import vn.edu.poly.todolist.DAO.NoteObjDAO;
import vn.edu.poly.todolist.DTO.NoteSubject;
import vn.edu.poly.todolist.R;

public class NotesAdapter extends  RecyclerView.Adapter<NotesAdapter.MyViewHolderNotes>{

    ArrayList<NoteSubject> listNotes;
    NoteObjDAO noteObjDAO;
    Context context;


    public NotesAdapter(ArrayList<NoteSubject> listNotes, NoteObjDAO noteObjDAO, Context context) {
        this.listNotes = listNotes;
        this.noteObjDAO = noteObjDAO;
        this.context = context;
    }
    public void setFilter(ArrayList<NoteSubject> filterList){
        this.listNotes = filterList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MyViewHolderNotes onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_one_item_note,parent,false);

        return new NotesAdapter.MyViewHolderNotes(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderNotes holder, int position) {
        NoteSubject noteSubject = listNotes.get(position);
        holder.title.setText(noteSubject.getTitle());
        holder.content.setText(noteSubject.getContent());
        holder.layout.setOnClickListener(layout ->{
            holder.layout.setBackground(context.getDrawable(R.drawable.custom_solid_one_items_notes_1));
        });
        holder.layout.setOnLongClickListener(layout ->{
            animationLongClick(holder.layout);
            holder.layout.setBackground(context.getDrawable(R.drawable.background_color_long_click_items));
            dialogFunctionItemNotes(context,position,holder.layout);
            return true;
        });

    }

    @Override
    public int getItemCount() {
        return listNotes.size();
    }

    public class MyViewHolderNotes extends  RecyclerView.ViewHolder{
        TextView title,content;
        LinearLayout layout;

        public MyViewHolderNotes(@NonNull View view) {
            super(view);
            layout = view.findViewById(R.id.layout_one_item);
            title = view.findViewById(R.id.tv_title_note);
            content = view.findViewById(R.id.tv_content_note);
        }
    }


    private void animationLongClick(LinearLayout layout){
        layout.setAlpha(0f);
        layout.setTranslationZ(-100);
        layout.animate().alpha(1f).translationZBy(100).setDuration(1000);
    }

    public void dialogFunctionItemNotes(Context context,int index,LinearLayout changeColorLayout){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.layout_function_item_notes);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.gravity = Gravity.BOTTOM;
        window.setAttributes(layoutParams);
        LinearLayout dialogLayout = dialog.findViewById(R.id.dialog_function_notes_item);
        animationDialog(dialogLayout);


        NoteSubject noteSubject = listNotes.get(index);
        dialog.findViewById(R.id.tv_delete).setOnClickListener(tv ->{
                deleteNotes(context,noteSubject,index);
                dialog.cancel();

        });
        dialog.findViewById(R.id.tv_change_color).setOnClickListener(tv ->{
           dialogChangeColorNotes(context,dialog,changeColorLayout);
        });

        dialog.show();

    }

    public void deleteNotes(Context context,NoteSubject noteSubject,int index){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getString(R.string.text_function_delete_1));
        builder.setIcon(android.R.drawable.ic_delete);
        builder.setMessage(context.getString(R.string.text_function_delete_2));
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int kq = noteObjDAO.deleteNote(noteSubject);
                if(kq > 0)
                {
                    listNotes.remove(index);
                    Toast.makeText(context, context.getString(R.string.text_function_delete_3), Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged();
                    dialogInterface.dismiss();
                }else
                    Toast.makeText(context, context.getString(R.string.text_function_delete_4) + kq, Toast.LENGTH_SHORT).show();

                dialogInterface.dismiss();

            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(context, context.getString(R.string.text_function_delete_5), Toast.LENGTH_SHORT).show();
                dialogInterface.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void dialogChangeColorNotes(Context context,Dialog mDialog, LinearLayout layout){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.custom_layout_chooser_color_notes);
        dialog.setCancelable(false);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        dialog.findViewById(R.id.change_color1).setOnClickListener(img ->{
            layout.setBackground(context.getDrawable(R.drawable.custom_solid_one_items_notes_1));
            mDialog.cancel();
            dialog.cancel();
        });
        dialog.findViewById(R.id.change_color2).setOnClickListener(img ->{
            layout.setBackground(context.getDrawable(R.drawable.custom_solid_one_items_notes_2));
            mDialog.cancel();
            dialog.cancel();
        });
        dialog.findViewById(R.id.change_color3).setOnClickListener(img ->{
            layout.setBackground(context.getDrawable(R.drawable.custom_solid_one_items_notes_3));
            mDialog.cancel();
            dialog.cancel();
        });
        dialog.findViewById(R.id.change_color4).setOnClickListener(img ->{
            layout.setBackground(context.getDrawable(R.drawable.custom_solid_one_items_notes_4));
            mDialog.cancel();
            dialog.cancel();
        });
        dialog.findViewById(R.id.change_color5).setOnClickListener(img ->{
            layout.setBackground(context.getDrawable(R.drawable.custom_solid_one_items_notes_5));
            mDialog.cancel();
            dialog.cancel();
        });
        dialog.findViewById(R.id.change_color6).setOnClickListener(img ->{
            layout.setBackground(context.getDrawable(R.drawable.custom_solid_one_items_notes_6));
            mDialog.cancel();
            dialog.cancel();
        });

        dialog.show();
    }

    public  void animationDialog(LinearLayout dialog){
        dialog.setAlpha(0f);
        dialog.setTranslationY(150);
        dialog.animate().alpha(1f).translationYBy(-150).setDuration(1000);
    }


}
