package vn.edu.poly.todolist.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import vn.edu.poly.todolist.Adapter.NotesAdapter;
import vn.edu.poly.todolist.DAO.NoteObjDAO;
import vn.edu.poly.todolist.DTO.NoteSubject;
import vn.edu.poly.todolist.R;

public class FragmentShowNotes extends Fragment {

    RecyclerView recyclerView;
    SearchView searchView;
    private GridLayoutManager gridLayoutManager;
    private LinearLayoutManager linearLayoutManager;
    NotesAdapter notesAdapterLinear;
    NoteObjDAO noteObjDAO;
    ImageView icShowSearch,icHideSearch, icChangeLayoutLinear,icChangeLayoutGirl;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_notes, container, false);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findByIdView(view);

        icChangeLayoutLinear.setVisibility(View.INVISIBLE);
        icChangeLayoutGirl.setVisibility(View.VISIBLE);

        noteObjDAO = new NoteObjDAO(getContext());
        notesAdapterLinear = new NotesAdapter(noteObjDAO.selectAll(),noteObjDAO,getContext());
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setAdapter(notesAdapterLinear);
        recyclerView.setLayoutManager(linearLayoutManager);


        icChangeLayoutLinear.setOnClickListener(ic ->{
            linearLayoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(linearLayoutManager);

            searchView.setVisibility(View.INVISIBLE);
            searchView.setQuery("", false);
            searchView.clearFocus();
            icHideSearch.setVisibility(View.INVISIBLE);
            icShowSearch.setVisibility(View.VISIBLE);
            icChangeLayoutGirl.setVisibility(View.VISIBLE);
            icChangeLayoutLinear.setVisibility(View.INVISIBLE);

        });
        icChangeLayoutGirl.setOnClickListener(ic ->{
            gridLayoutManager = new GridLayoutManager(getContext(),2);
            recyclerView.setLayoutManager(gridLayoutManager);


            searchView.setVisibility(View.INVISIBLE);
            searchView.setQuery("", false);
            searchView.clearFocus();
            icShowSearch.setVisibility(View.VISIBLE);
            icHideSearch.setVisibility(View.INVISIBLE);
            icChangeLayoutGirl.setVisibility(View.INVISIBLE);
            icChangeLayoutLinear.setVisibility(View.VISIBLE);
        });

        ArrayList<NoteSubject> listNote = noteObjDAO.selectAll();
        final RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        searchView.setVisibility(View.INVISIBLE);
        icShowSearch.setVisibility(View.VISIBLE);
        icHideSearch.setVisibility(View.INVISIBLE);
        icShowSearch.setOnClickListener(image ->{
            showSearchView(searchView);
            icHideSearch.setVisibility(View.VISIBLE);
            icShowSearch.setVisibility(View.INVISIBLE);

            searchView.clearFocus();
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    ArrayList<NoteSubject> listLinear = new ArrayList<>();
                    if(layoutManager instanceof LinearLayoutManager) {
                        for(NoteSubject noteSubject : listNote){
                            if(noteSubject.getTitle().toLowerCase().contains(newText.toLowerCase())){
                                listLinear.add(noteSubject);
                            }
                        }
                        if(listLinear.isEmpty()){
                            Toast.makeText(getContext(), "Not found!", Toast.LENGTH_SHORT).show();
                        }else {
                            notesAdapterLinear.setFilter(listLinear);
                        }

                    }

                    return true;
                }
            });
        });
        icHideSearch.setOnClickListener(image ->{
            hideSearchView(searchView);
            icHideSearch.setVisibility(View.INVISIBLE);
            icShowSearch.setVisibility(View.VISIBLE);
        });

    }

    private void findByIdView(View view){
        recyclerView = view.findViewById(R.id.list_notes);
        searchView = view.findViewById(R.id.search_notes);
        icShowSearch = view.findViewById(R.id.ic_show_search);
        icHideSearch = view.findViewById(R.id.ic_hide_search);
        icChangeLayoutLinear = view.findViewById(R.id.ic_change_layout_linear);
        icChangeLayoutGirl = view.findViewById(R.id.ic_change_layout_girl);
    }


    private void showSearchView(SearchView searchView) {
        searchView.setVisibility(View.VISIBLE);
        searchView.setAlpha(0f);
        searchView.setTranslationX(100);
        searchView.animate().alpha(1f).translationXBy(-100).setDuration(1500);
    }
    private void hideSearchView(SearchView searchView) {
        searchView.setAlpha(1f);
        searchView.setTranslationX(0);
        searchView.animate().alpha(0f).translationXBy(100).setDuration(1500);
    }

}
