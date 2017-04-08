package com.example.lilactests.addeditnote;

import com.example.lilactests.model.domain.Note;

/**
 * Created by wing on 2016/5/6.
 */
public interface AddEditNoteContract {
    interface Presenter {
        void saveNote(Note note);

        void updateNote(Note note);
    }

    interface View {
        void showProcess();

        void dismissProcess();

        void showSuccessRemind();

        void showFailureRemind();
    }

}
