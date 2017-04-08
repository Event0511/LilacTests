package com.example.lilactests.notes;

import com.example.lilactests.model.domain.Note;

import java.util.List;

/**
 * Created by wing on 2016/5/6.
 */
public interface NotesContract {
    interface Presenter {
        void showNotesList();

        void refreshNotes();

        void deleteNote(Long id);

    }

    interface View {

        void showNotes(List<Note> noteList);

        void refreshNotes(List<Note> noteList);

        void showLoading();

        void cancelLoading();
    }
}
