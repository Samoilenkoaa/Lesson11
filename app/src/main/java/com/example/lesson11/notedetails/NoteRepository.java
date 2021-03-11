package com.example.lesson11.notedetails;

import androidx.annotation.NonNull;

import com.example.lesson11.NoteDataClass;

public interface NoteRepository {

    void setNote(NoteDataClass note);

    void onDeleteClicked(@NonNull String id);

    void getNote(String id);
}
