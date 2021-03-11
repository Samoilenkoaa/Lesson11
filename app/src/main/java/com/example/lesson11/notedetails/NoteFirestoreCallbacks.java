package com.example.lesson11.notedetails;

import androidx.annotation.Nullable;

import com.example.lesson11.NoteDataClass;

public interface NoteFirestoreCallbacks {

    void onSuccess(@Nullable String message);
    void onError(@Nullable String message);
    void onGetNote(NoteDataClass note);
}