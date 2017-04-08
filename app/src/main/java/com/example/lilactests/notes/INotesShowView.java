package com.example.lilactests.notes;

import com.example.lilactests.model.domain.Note;

import java.util.List;

/**
 * Created by wing on 2016/4/20.
 * the view interface for IListNotesPresenter
 */
public interface INotesShowView {

    void showNotes(List<Note> noteList);

    void refreshNotes(List<Note> noteList);

    void showLoading();

    void cancelLoading();
}
