package com.example.lesson11.notedetails;

import android.app.DatePickerDialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.lesson11.NoteDataClass;
import com.example.lesson11.R;

import java.util.Calendar;
import java.util.UUID;


public class EditFragment extends DialogFragment implements NoteFirestoreCallbacks {
    private EditText name;
    private EditText description;
    private EditText date;
    private Button buttonSave;
    private boolean isLand;
    private DatePickerDialog picker;
    private final NoteRepository repository = new NoteRepositoryImpl(this);
    private final static String ARG_MODEL_KEY = "arg_model_key";
    private NoteDataClass note;
    private NoteCreateCallback ndc;

    public static EditFragment newInstance(NoteDataClass note, NoteCreateCallback ndc) {
        EditFragment fragment = new EditFragment();
        fragment.ndc = ndc;
        if (note != null) {
            Bundle args = new Bundle();
            args.putSerializable("key", note);
            fragment.setArguments(args);
        }

        return fragment;
    }


    private void initView(View view) {
        name = view.findViewById(R.id.name);
        description = view.findViewById(R.id.description);
        date = view.findViewById(R.id.date);
        date.setOnClickListener(v -> {
            final Calendar cldr = Calendar.getInstance();
            int day = cldr.get(Calendar.DAY_OF_MONTH);
            int month = cldr.get(Calendar.MONTH);
            int year = cldr.get(Calendar.YEAR);
            // date picker dialog
            picker = new DatePickerDialog(getContext(),
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                        }
                    }, year, month, day);

            picker.show();
        });
        buttonSave = view.findViewById(R.id.button_save);

        buttonSave.setOnClickListener(v -> {
            if (getArguments() == null) {
                note = new NoteDataClass();
                note.setName(name.getText().toString());
                note.setDescription(description.getText().toString());
                note.setDate(date.getText().toString());
                note.setId(UUID.randomUUID().toString());
                update(note);

            } else {
                note = new NoteDataClass();
                note.setName(name.getText().toString());
                note.setDescription(description.getText().toString());
                note.setDate(date.getText().toString());
                note.setId(((NoteDataClass) getArguments().getSerializable("key")).getId());
                update(note);
            }
            dismiss();
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_fragmet, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null) {
            if (getArguments().getSerializable("key") != null) {
                name.setText(((NoteDataClass) getArguments().getSerializable("key")).getName());
                description.setText(((NoteDataClass) getArguments().getSerializable("key")).getDescription());
                date.setText(((NoteDataClass) getArguments().getSerializable("key")).getDate());
            }
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isLand = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    @Override
    public void onSuccess(@Nullable String message) {
        if(ndc != null) {
            ndc.onCreateNote();
        }

//        showToastMessage(message);
//
//        if (note != null) {
//            if (!isLand) {
//                FragmentTransaction fragmentTr = getActivity().getSupportFragmentManager().beginTransaction();
//                DetaileFragment dataFragment = DetaileFragment.newInstance(
//                        note);
//                fragmentTr.replace(R.id.Fr_container, dataFragment);
//                fragmentTr.commit();
//            } else {
//                FragmentTransaction fragmentTr = getActivity().getSupportFragmentManager().beginTransaction();
//                getActivity().findViewById(R.id.Fr_container2).setVisibility(View.VISIBLE);
//                DetaileFragment dataFragment = DetaileFragment.newInstance(
//                        note);
//                fragmentTr.replace(R.id.Fr_container2, dataFragment);
//                fragmentTr.commit();
//
//            }
//        }
    }

    @Override
    public void onError(@Nullable String message) {
//        showToastMesage(message);
    }

    @Override
    public void onGetNote(NoteDataClass note) {

    }

    private void update(NoteDataClass note) {
        repository.setNote(note);

//        if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(description)) {
//            if (getArguments() != null) {
//                NoteDataClass noteModel = (NoteDataClass) getArguments().getSerializable(ARG_MODEL_KEY);
//                if (noteModel != null) {
//                    repository.setNote(noteModel.getId(), title, description);
//                } else {
//                    String id = UUID.randomUUID().toString();
//                    repository.setNote(id, title, description);
//                }
//            }
//        } else {
//            showToastMessage("Поля не могут быть пустые");
//        }
    }

//    private void showToastMessage(@Nullable String message) {
//        if (message != null) {
//            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
//        }
//    }
}