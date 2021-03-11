package com.example.lesson11.notedetails;

import androidx.annotation.NonNull;

import com.example.lesson11.Constants;
import com.example.lesson11.NoteDataClass;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class NoteRepositoryImpl implements NoteRepository {

    private final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private final NoteFirestoreCallbacks callbacks;

    public NoteRepositoryImpl(NoteFirestoreCallbacks callbacks) {
        this.callbacks = callbacks;
    }

    @Override
    public void onDeleteClicked(@NonNull String id) {
        firebaseFirestore
                .collection(Constants.TABLE_NAME_NOTES)
                .document(id)
                .delete()
                .addOnSuccessListener(aVoid -> callbacks.onSuccess("Заметка успешно Удалена"));
    }

    @Override
    public void getNote(String id) {
        firebaseFirestore.collection(Constants.TABLE_NAME_NOTES).document(id).get().addOnCompleteListener(task -> {
            callbacks.onGetNote(task.getResult().toObject(NoteDataClass.class));
        });
    }

    @Override
    public void setNote(NoteDataClass note) {

        final Map<String, Object> noteToDb = new HashMap<>();
        noteToDb.put("id", note.getId());
        noteToDb.put("name", note.getName());
        noteToDb.put("description", note.getDescription());
        noteToDb.put("date", note.getDate());

        firebaseFirestore
                .collection(Constants.TABLE_NAME_NOTES)
                .document(note.getId())
                .set(noteToDb)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        callbacks.onSuccess("Заметка успешно обновлена");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callbacks.onError(e.getMessage());
                    }
                });
    }
}