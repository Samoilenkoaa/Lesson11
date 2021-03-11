package com.example.lesson11.notes;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lesson11.NoteDataClass;
import com.example.lesson11.R;
import com.example.lesson11.notedetails.DetaileFragment;
import com.example.lesson11.notedetails.EditFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class NotesFragment extends Fragment implements MyNoteDataClassRecyclerViewAdapter.OnItemClickListener, NotesFirestoreCallbacks {

    private final MyNoteDataClassRecyclerViewAdapter dataAdapter = new MyNoteDataClassRecyclerViewAdapter(new ArrayList<>());

    boolean isLand;
    FloatingActionButton floatingActionButton;
    private final List<NoteDataClass> noteList = new ArrayList<>();
    private final NotesRepository repository = new NotesRepositoryImpl(this);

    public static NotesFragment newInstance() {
        NotesFragment fragment = new NotesFragment();
        Bundle args = new Bundle();
//        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isLand = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;

        if (getArguments() != null) {
//            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        floatingActionButton = view.findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(v -> {
            if (!isLand) {
                FragmentTransaction fragmentTr = getActivity().getSupportFragmentManager().beginTransaction();
                EditFragment fragment = EditFragment.newInstance(null, () -> repository.requestNotes());
//                fragmentTr.addToBackStack("");
//                fragmentTr.replace(R.id.Fr_container, fragment);
//                fragmentTr.commit();
                fragment.show(fragmentTr, "null");
            } else {
                FragmentTransaction fragmentTr = getActivity().getSupportFragmentManager().beginTransaction();
                getActivity().findViewById(R.id.Fr_container2).setVisibility(View.VISIBLE);
                EditFragment fragment = EditFragment.newInstance(null, () -> repository.requestNotes());
                fragmentTr.replace(R.id.Fr_container2, fragment);
                fragmentTr.commit();
            }
        });

        // Set the adapter
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        dataAdapter.SetOnItemClickListener(this);
        recyclerView.setAdapter(dataAdapter);
        repository.requestNotes();

        return view;

    }

    public void onItemClick(View view, int position) {
        if (!isLand) {
            FragmentTransaction fragmentTr = getActivity().getSupportFragmentManager().beginTransaction();
            DetaileFragment dataFragment = DetaileFragment.newInstance(
                    dataAdapter.getNote(position)
            );
            fragmentTr.addToBackStack("");
            fragmentTr.replace(R.id.Fr_container, dataFragment);
            fragmentTr.commit();
        } else {
            FragmentTransaction fragmentTr = getActivity().getSupportFragmentManager().beginTransaction();
            getActivity().findViewById(R.id.Fr_container2).setVisibility(View.VISIBLE);
            DetaileFragment dataFragment = DetaileFragment.newInstance(
                    dataAdapter.getNote(position)
            );
            fragmentTr.replace(R.id.Fr_container2, dataFragment);
            fragmentTr.commit();
        }
    }

    @Override
    public void onSuccessNotes(@NonNull List<NoteDataClass> items) {
        noteList.clear();
        noteList.addAll(items);
        dataAdapter.submitList(items);
    }

    @Override
    public void onErrorNotes(@Nullable String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }
}