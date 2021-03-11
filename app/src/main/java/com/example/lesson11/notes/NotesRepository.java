package com.example.lesson11.notes;

import androidx.annotation.NonNull;

public interface NotesRepository {

    void requestNotes();

    void onDeleteClicked(@NonNull String id);
}