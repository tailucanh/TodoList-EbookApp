package vn.edu.poly.todolist.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import vn.edu.poly.todolist.Fragment.FragmentShowDrawing;
import vn.edu.poly.todolist.Fragment.FragmentShowNotes;

public class AdapterTabLayoutMain extends FragmentStateAdapter {


    public AdapterTabLayoutMain(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position == 0){
            return new FragmentShowNotes();
        }
        return new FragmentShowDrawing();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
