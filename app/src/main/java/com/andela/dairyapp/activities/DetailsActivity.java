package com.andela.dairyapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.andela.dairyapp.R;
import com.andela.dairyapp.models.Note;
import com.google.gson.Gson;

import java.util.Objects;

public class DetailsActivity extends AppCompatActivity {
    Note note;
    TextView color_code, note_description, created_at;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        note = new Gson().fromJson(getIntent().getStringExtra("diary"), Note.class);
        Objects.requireNonNull(getSupportActionBar()).setTitle(note.getNote_name());
        color_code = findViewById(R.id.color_code);
        note_description = findViewById(R.id.note_description);
        created_at = findViewById(R.id.created_at);

        note_description.setText(note.getNote_description());
        created_at.setText(note.getCreated_at());

        color_code.setText(note.getNote_name().subSequence(0, 2));
        color_code.setBackgroundColor(note.getColor_code());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
        }else if(item.getItemId() == R.id.action_edit){
            //TODO
        }else if(item.getItemId() == R.id.action_delete){
            //TODO
        }else if(item.getItemId() == R.id.action_share){
            //TODO
        }/*else if(item.getItemId() == R.id.action_about){
            //TODO
        }else if (item.getItemId() == R.id.action_quit){
            //TODO
        }*/
        return super.onOptionsItemSelected(item);
    }
}
