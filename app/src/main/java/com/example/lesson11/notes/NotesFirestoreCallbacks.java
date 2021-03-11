package com.example.lesson11.notes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.lesson11.NoteDataClass;

import java.util.List;

public interface NotesFirestoreCallbacks {

    void onSuccessNotes(@NonNull List<NoteDataClass> items);
    void onErrorNotes(@Nullable String message);
}