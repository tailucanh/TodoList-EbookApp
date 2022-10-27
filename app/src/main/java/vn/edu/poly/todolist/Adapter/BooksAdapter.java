package vn.edu.poly.todolist.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RenderEffect;
import android.graphics.Shader;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.renderscript.Allocation;
import androidx.renderscript.Element;
import androidx.renderscript.RenderScript;
import androidx.renderscript.ScriptIntrinsicBlur;

import vn.edu.poly.todolist.MyBlurView.MyBlurBuilder;
import vn.edu.poly.todolist.R;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BooksViewHolder> {
    Context context;
    private AnimatedVectorDrawable emptyHeart;
    private AnimatedVectorDrawable fillHeart;
    private boolean full = false;
    public  static  String TEXT_STAR;
    int imgs[] = {
            R.drawable.book_3, R.drawable.book_2,R.drawable.book_1
    };

    int titles[] ={
            R.string.title_book3, R.string.title_book2, R.string.title_book1
    };
    int authors[] ={
            R.string.author_book3, R.string.author_book2, R.string.author_book1
    };

    public BooksAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public BooksAdapter.BooksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_one_item_books,parent,false);

        return new BooksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BooksAdapter.BooksViewHolder holder, int position) {
        final int index = position;
        holder.imgBook.setImageResource(imgs[index]);

        holder.icHeart.setOnClickListener(ic ->{
                animateHeart(holder.icHeart);
        });


        holder.layoutStar.setOnClickListener(layout-> {
                dialogFiveStar(context,holder.textStar);

        });

        holder.title.setText(titles[index]);
        holder.author.setText(authors[index]);

        holder.tvDetails.setOnClickListener(tv ->{
                TEXT_STAR = holder.textStar.getText().toString();
                dialogDetailsBooks(context,imgs,index);
        });

    }

    @Override
    public int getItemCount() {
        return titles.length;
    }

    public class BooksViewHolder extends RecyclerView.ViewHolder{
        LinearLayout layoutStar;
        ImageView imgBook,icHeart;
        TextView title, author,textStar,tvDetails, tvRead;
        public BooksViewHolder(@NonNull View view) {
            super(view);
            layoutStar = view.findViewById(R.id.layout_star);
            imgBook = view.findViewById(R.id.item_book);
            icHeart = view.findViewById(R.id.ic_heart);
            tvDetails = view.findViewById(R.id.tv_details);
            tvRead = view.findViewById(R.id.tv_read);
            title = view.findViewById(R.id.title_book);
            author = view.findViewById(R.id.author_name);
            textStar = view.findViewById(R.id.text_star);

        }
    }
    public void animateHeart(ImageView imageView)
    {
        emptyHeart = (AnimatedVectorDrawable)context.getDrawable(R.drawable.avd_heart_empty);
        fillHeart = (AnimatedVectorDrawable) context.getDrawable( R.drawable.avd_heart_fill);
        AnimatedVectorDrawable drawable = full ? emptyHeart : fillHeart;
        imageView.setImageDrawable(drawable);
        drawable.start();
        full = !full;
    }

    public  void dialogFiveStar(Context context, TextView tv){
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_five_star_dialog);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView tvCancel = dialog.findViewById(R.id.tv_cancel);
        TextView tvAgree = dialog.findViewById(R.id.tv_agree);
        RatingBar ratingBar = dialog.findViewById(R.id.rating_bar);

        ratingBar.setRating(Float.parseFloat(tv.getText().toString()));

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                 tv.setText(String.valueOf(rating));
            }
        });

        tvCancel.setOnClickListener(textView ->{
                tv.setText("5.0");
                dialog.dismiss();
        });
        tvAgree.setOnClickListener(textView ->{
                dialog.dismiss();

        });

        dialog.show();
    }

    ImageButton imClose ;
    ImageView icBackground, imCloseToolBar ,imgBookDetails,imgLine,icLike,icLike2;
    LinearLayout layoutText;
    NestedScrollView scrollView;
    View viewShow;
    TextView tvShow,tvHide,textStarDetails,titleBookDetails,authorBookDetails,titleDetailsDescription,tvContent1,
            tvContent2,tvContent3,tvContent4,tvContent5,tvContent6,tvContent7,tvContent8,tvContent9,tvContent10,tvContent11,tvContent12,tvContent13,
            tvContent14,tvContent15,tvContent16,tvContent17,tvContent18;
    RatingBar ratingBarDetails;

    private void dialogDetailsBooks(Context context, int[] arr, int position){
        final Dialog dialog = new Dialog(context, android.R.style.Theme_Material_Light_NoActionBar);
        dialog.setContentView(R.layout.dialog_details_book);
        context.setTheme(R.style.Theme_ToDoList);
        changeStatusBarColor(dialog);
        viewMappingDialogDetails(dialog);
        showOrHideTextContent(tvShow,tvHide,viewShow,layoutText);


        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(@NonNull NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(scrollY > 0 || scrollX > 0 ){
                    imgBookDetails.setVisibility(View.INVISIBLE);


                }else{
                    imgBookDetails.setVisibility(View.VISIBLE);


                }

            }
        });

        ratingBarDetails.setRating(Float.parseFloat(TEXT_STAR));
        textStarDetails.setText(TEXT_STAR);

        if (arr[position] == arr[0]){
           setTextDialogDetailsBook1(dialog);

        }else if(arr[position] == arr[1]){
            setTextDialogDetailsBook2(dialog);

        }else if(arr[position] == arr[2]){

        }

        onClickLike();
        imClose.setOnClickListener(img ->{
                dialog.cancel();
        });
        imCloseToolBar.setOnClickListener(img ->{
                dialog.cancel();
        });

        dialog.show();
    }


    private  void viewMappingDialogDetails(Dialog dialog){
        icBackground = dialog.findViewById(R.id.background_details);
        layoutText = dialog.findViewById(R.id.layout_hide_or_show);
        viewShow = dialog.findViewById(R.id.view_show);
        tvShow = dialog.findViewById(R.id.tv_show);
        tvHide = dialog.findViewById(R.id.tv_hide);
        textStarDetails = dialog.findViewById(R.id.text_star_details);
        titleBookDetails = dialog.findViewById(R.id.text_title_details);
        authorBookDetails = dialog.findViewById(R.id.text_author_details);
        ratingBarDetails = dialog.findViewById(R.id.rating_bar_details);
        scrollView = dialog.findViewById(R.id.scroll_view_details);
        imgBookDetails = dialog.findViewById(R.id.img_book);
        imClose = dialog.findViewById(R.id.ic_back_appbar);
        imgLine = dialog.findViewById(R.id.img_line);
        imCloseToolBar = dialog.findViewById(R.id.ic_back_toolbar);
        icLike = dialog.findViewById(R.id.ic_like);
        icLike2 = dialog.findViewById(R.id.ic_like_2);
        titleDetailsDescription = dialog.findViewById(R.id.title_1);
        tvContent1 = dialog.findViewById(R.id.text_content_1);
        tvContent2 = dialog.findViewById(R.id.text_content_2);
        tvContent3 = dialog.findViewById(R.id.text_content_3);
        tvContent4 = dialog.findViewById(R.id.text_content_4);
        tvContent5 = dialog.findViewById(R.id.text_content_5);
        tvContent6 = dialog.findViewById(R.id.text_content_6);
        tvContent7 = dialog.findViewById(R.id.text_content_7);
        tvContent8 = dialog.findViewById(R.id.text_content_8);
        tvContent9 = dialog.findViewById(R.id.text_content_9);   //Ba dấu ***
        tvContent10 = dialog.findViewById(R.id.text_content_10);
        tvContent11 = dialog.findViewById(R.id.text_content_11);
        tvContent12 = dialog.findViewById(R.id.text_content_12);
        tvContent13 = dialog.findViewById(R.id.text_content_13);
        tvContent14 = dialog.findViewById(R.id.text_content_14);
        tvContent15 = dialog.findViewById(R.id.text_content_15);
        tvContent16 = dialog.findViewById(R.id.text_content_16);
        tvContent17 = dialog.findViewById(R.id.text_content_17);
        tvContent18 = dialog.findViewById(R.id.text_content_18);

    }

    private void onClickLike(){
        icLike.setVisibility(View.VISIBLE);
        icLike2.setVisibility(View.INVISIBLE);
        icLike.setOnClickListener(ic ->{
                icLike.setVisibility(View.INVISIBLE);
                icLike2.animate().rotation(360f).setDuration(2000).start();
                icLike2.setVisibility(View.VISIBLE);
        });
        icLike2.setOnClickListener(ic ->{
                icLike.setVisibility(View.VISIBLE);
                icLike2.setVisibility(View.INVISIBLE);
        });
    }

    private void setTextDialogDetailsBook1(Dialog dialog){

        icBackground.setImageResource(R.drawable.background_details);
        createBlurImage(20,context,icBackground);
        imgBookDetails.setImageResource(R.drawable.item_details_book_1);
        titleBookDetails.setText(R.string.text_title_book_details);
        authorBookDetails.setText(R.string.text_author_book_details);
        titleBookDetails.setTextColor(Color.parseColor("#000000"));
        textStarDetails.setTextColor(Color.parseColor("#FFFFFFFF"));
        authorBookDetails.setTextColor(Color.parseColor("#FFFFFFFF"));

        titleDetailsDescription.setText(R.string.text_title_details_1);
        tvContent1.setText(R.string.content_details_book1);
        tvContent2.setText(R.string.content_details_book1_1);
        tvContent3.setText(R.string.content_details_book1_2);
        tvContent4.setText(R.string.content_details_book1_3);
        tvContent5.setText(R.string.content_details_book1_4);
        tvContent6.setText(R.string.content_details_book1_5);
        tvContent7.setText(R.string.content_details_book1_6);
        tvContent8.setText(R.string.content_details_book1_7);
        tvContent10.setText(R.string.content_details_book1_8);
        tvContent11.setText(R.string.content_details_book1_9);
        tvContent12.setText(R.string.content_details_book1_10);
        tvContent13.setText(R.string.content_details_book1_11);
        tvContent14.setText(R.string.content_details_book1_12);
        tvContent15.setText(R.string.content_details_book1_13);
        tvContent16.setText(R.string.content_details_book1_14);
        tvContent17.setText(R.string.content_details_book1_15);
        tvContent18.setText(R.string.content_details_book1_16);
    }

    private void setTextDialogDetailsBook2(Dialog dialog){

        icBackground.setImageResource(R.drawable.background_details_2);
        createBlurImage2(25,context,icBackground);
        imgBookDetails.setImageResource(R.drawable.item_details_book_2);
        imgLine.setImageResource(R.drawable.line_details_2);
        titleBookDetails.setText(R.string.text_title_book_details_2);
        authorBookDetails.setText(R.string.text_author_book_details_2);
        titleBookDetails.setTextColor(Color.parseColor("#CE1E1E1E"));
        textStarDetails.setTextColor(Color.parseColor("#4C4A4A"));
        authorBookDetails.setTextColor(Color.parseColor("#4C4A4A"));


        titleDetailsDescription.setText(R.string.text_title_details_2);
        tvContent1.setText(R.string.content_details_book2);
        tvContent1.setGravity(Gravity.CENTER);
        tvContent1.setTextColor(Color.parseColor("#E4D142"));
        tvContent2.setText(R.string.content_details_book2_1);
        tvContent3.setVisibility(View.GONE);
        tvContent4.setText(R.string.content_details_book2_2);
        tvContent5.setVisibility(View.GONE);
        tvContent6.setText(R.string.content_details_book2_3);
        tvContent7.setVisibility(View.GONE);
        tvContent8.setText(R.string.content_details_book2_4);

        tvContent9.setVisibility(View.GONE);
        tvContent10.setVisibility(View.GONE);
        tvContent11.setText(R.string.content_details_book2_5);
        tvContent12.setText(R.string.content_details_book2_6);

        tvContent12.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
        tvContent13.setVisibility(View.GONE);
        tvContent14.setVisibility(View.GONE);
        tvContent15.setVisibility(View.GONE);
        tvContent16.setVisibility(View.GONE);
        tvContent17.setVisibility(View.GONE);
        tvContent18.setText(R.string.content_details_book2_7);
        tvContent18.setBackgroundColor(Color.parseColor("#FFFFFFFF"));

    }



    private void createBlurImage(int blurRadius,Context context,ImageView imageView)
    {
        Bitmap srcBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.background_details);
        Bitmap blurredBitmap = MyBlurBuilder.applyBlur(context, srcBitmap, blurRadius);
        if (blurredBitmap != null)
        {
            imageView.setImageBitmap(blurredBitmap);
        }
    }

    private void createBlurImage2(int blurRadius,Context context,ImageView imageView)
    {
        Bitmap srcBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.background_details_2);
        Bitmap blurredBitmap = MyBlurBuilder.applyBlur(context, srcBitmap, blurRadius);
        if (blurredBitmap != null)
        {
            imageView.setImageBitmap(blurredBitmap);
        }
    }

    private void changeStatusBarColor(Dialog dialog){
        Window window = dialog.getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    private void showOrHideTextContent(TextView tv1,TextView tv2,View view,LinearLayout layout){
        layout.setVisibility(View.GONE);
        view.setVisibility(View.VISIBLE);
        tv1.setVisibility(View.VISIBLE);
        tv1.setText(R.string.text_show);
        tv2.setText(R.string.text_hide);
        tv1.setOnClickListener(tv ->{
                layout.setVisibility(View.VISIBLE);
                view.setVisibility(View.GONE);
                tv1.setVisibility(View.GONE);
        });
        tv2.setOnClickListener(tv ->{
                view.setVisibility(View.VISIBLE);
                layout.setVisibility(View.GONE);
                tv1.setVisibility(View.VISIBLE);

        });
    }



}


