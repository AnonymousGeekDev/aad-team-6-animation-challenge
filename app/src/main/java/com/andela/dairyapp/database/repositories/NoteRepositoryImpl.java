package com.andela.dairyapp.database.repositories;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.andela.dairyapp.database.DairyDatabaseContract;
import com.andela.dairyapp.database.DairyDatabaseOpenHelper;
import com.andela.dairyapp.models.Note;

import java.util.ArrayList;
import java.util.List;


public class NoteRepositoryImpl {

    private final String TAG = getClass().getSimpleName();

    private DairyDatabaseOpenHelper mDatabaseHelper;

    public NoteRepositoryImpl(DairyDatabaseOpenHelper databaseHelper) {
        mDatabaseHelper = databaseHelper;
    }

    public long insert(@NonNull Note note) {
        ContentValues values = new ContentValues();
        values.put(DairyDatabaseContract.NoteEntry.COLUMN_COLOR_CODE, note.getColor_code());
        values.put(DairyDatabaseContract.NoteEntry.COLUMN_NOTE_NAME, note.getNote_name());
        values.put(DairyDatabaseContract.NoteEntry.COLUMN_NOTE_DESCRIPTION, note.getNote_description());
        values.put(DairyDatabaseContract.NoteEntry.COLUMN_CREATED_AT, note.getCreated_at());


        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        long rowId = db.insert(DairyDatabaseContract.NoteEntry.TABLE_NAME, null, values);
        db.close();
        Log.d(TAG, "Note SAVED");
        return rowId;
    }

    public void insert(@NonNull List<Note> notes) {
        ContentValues values = new ContentValues();
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        for (Note note : notes) {
            values.clear();
            values.put(DairyDatabaseContract.NoteEntry.COLUMN_COLOR_CODE, note.getColor_code());
            values.put(DairyDatabaseContract.NoteEntry.COLUMN_NOTE_NAME, note.getNote_name());
            values.put(DairyDatabaseContract.NoteEntry.COLUMN_NOTE_DESCRIPTION, note.getNote_description());
            values.put(DairyDatabaseContract.NoteEntry.COLUMN_CREATED_AT, note.getCreated_at());

            db.insert(DairyDatabaseContract.NoteEntry.TABLE_NAME, null, values);
            Log.d(TAG, "Note SAVED");
        }
        db.close();
    }

    public void removeAll() {
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        db.delete(DairyDatabaseContract.NoteEntry.TABLE_NAME, null, null);
        Log.d(TAG, "Notes removed");
        db.close();
    }

    public List<Note> findAll() {
        SQLiteDatabase database = mDatabaseHelper.getReadableDatabase();
        List<Note> result = new ArrayList<>();

        String[] columns = {
                DairyDatabaseContract.NoteEntry._ID,
                DairyDatabaseContract.NoteEntry.COLUMN_COLOR_CODE,
                DairyDatabaseContract.NoteEntry.COLUMN_NOTE_NAME,
                DairyDatabaseContract.NoteEntry.COLUMN_NOTE_DESCRIPTION,
                DairyDatabaseContract.NoteEntry.COLUMN_CREATED_AT,

        };

        Cursor cursor = database.query(DairyDatabaseContract.NoteEntry.TABLE_NAME, columns, null, null, null, null, null);

        int idPos, colorPos, namePos, desPos, createPos;
        if (cursor != null) {
            idPos = cursor.getColumnIndex(DairyDatabaseContract.NoteEntry._ID);
            namePos = cursor.getColumnIndex(DairyDatabaseContract.NoteEntry.COLUMN_NOTE_NAME);
            colorPos = cursor.getColumnIndex(DairyDatabaseContract.NoteEntry.COLUMN_COLOR_CODE);
            desPos = cursor.getColumnIndex(DairyDatabaseContract.NoteEntry.COLUMN_NOTE_DESCRIPTION);
            createPos = cursor.getColumnIndex(DairyDatabaseContract.NoteEntry.COLUMN_CREATED_AT);

            Note note;
            int id, color;
            String name, description, createAt;
            while (cursor.moveToNext()) {
                id = cursor.getInt(idPos);
                name = cursor.getString(namePos);
                description = cursor.getString(desPos);
                color = cursor.getInt(colorPos);
                createAt = cursor.getString(createPos);

                note = new Note(id, color, name, description, createAt);
                result.add(note);
            }
            cursor.close();
            database.close();
        }

        return result;
    }

    public Cursor loadAll() {
        SQLiteDatabase database = mDatabaseHelper.getReadableDatabase();
        List<Note> result = new ArrayList<>();

        String[] columns = {
                DairyDatabaseContract.NoteEntry._ID,
                DairyDatabaseContract.NoteEntry.COLUMN_COLOR_CODE,
                DairyDatabaseContract.NoteEntry.COLUMN_NOTE_NAME,
                DairyDatabaseContract.NoteEntry.COLUMN_NOTE_DESCRIPTION,
                DairyDatabaseContract.NoteEntry.COLUMN_CREATED_AT,

        };

        Cursor cursor = database.query(DairyDatabaseContract.NoteEntry.TABLE_NAME, columns, null, null, null, null, null);
        return cursor;
    }


}
