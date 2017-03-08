package com.elis.ltm.todo.activities;

import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.elis.ltm.todo.R;
import com.elis.ltm.todo.adapters.NotaAdapter;
import com.elis.ltm.todo.db.Databasehandler;
import com.elis.ltm.todo.model.Nota;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Databasehandler databasehandler;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    NotaAdapter adapter;
    FloatingActionButton fab;
    Toolbar toolbar;
//    public ActionMode pippo;
//    Log log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.rv);
        adapter = new NotaAdapter();
        linearLayoutManager = new LinearLayoutManager(this);
        databasehandler = new Databasehandler(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter.setDataset(databasehandler.getAllNotes());

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
        registerForContextMenu(recyclerView);
    }

//    public ActionMode.Callback callBack = new ActionMode.Callback() {
//        @Override
//        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
//            MenuInflater menuInflater = mode.getMenuInflater();
//            menuInflater.inflate(R.menu.menu_toolbar, menu);
//            return true;
//        }
//
//        @Override
//        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
//            return false;
//        }
//
//        @Override
//        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
//            int posizione;
//            switch(item.getItemId()){
//                case R.id.delete:
//                    posizione = adapter.getPosition();
//                    Nota nota_rmv = adapter.getNota(posizione);
//                    long is = databasehandler.deleteNote(nota_rmv);
//                    System.out.println(is);
//                    adapter.removeNota(posizione);
//                    return true;
//                case R.id.edit:
//                    posizione = adapter.getPosition();
//                    Nota nota = adapter.getNota(posizione);
//                    showAlerDialogEdit(nota.getTitle(), nota.getBody(), nota.getExpiryDate(), nota.isStatus());
//                    return true;
//            }
//            return false;
//        }
//
//        @Override
//        public void onDestroyActionMode(ActionMode mode) {
//            pippo = null;
//
//        }
//    };

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.fab:
                showAlerDialogAdd();
                break;

        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int posizione;
        switch (item.getItemId()) {
            case R.id.item_delete:
                posizione = adapter.getPosition();
                Nota nota_rmv = adapter.getNota(posizione);
                long is = databasehandler.deleteNote(nota_rmv);
                System.out.println(is);
                adapter.removeNota(posizione);
                break;
            case R.id.item_edit:
                posizione = adapter.getPosition();
                Nota nota = adapter.getNota(posizione);
                showAlerDialogEdit(nota.getTitle(), nota.getBody(), nota.getExpiryDate(), nota.isStatus());
                break;
        }
        return super.onContextItemSelected(item);
    }


    public void showAlerDialogAdd() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        LayoutInflater inflate = this.getLayoutInflater();
        View da = inflate.inflate(R.layout.menu_add_activity, null);
        dialog.setView(da);
        final EditText noteTitle, noteBody, noteDate;
        final CheckBox speciale;
        noteTitle = (EditText) da.findViewById(R.id.title_et);
        noteBody = (EditText) da.findViewById(R.id.body_et);
        noteDate = (EditText) da.findViewById(R.id.date_et);
        speciale = (CheckBox) da.findViewById(R.id.radiobutton_special);
        dialog.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Nota nota = new Nota();
                nota.setTitle(noteTitle.getText().toString());
                nota.setBody(noteBody.getText().toString());
                nota.setExpiryDate(noteDate.getText().toString());
                nota.setStatus(speciale.isChecked());
                adapter.addNota(nota);
                long is = databasehandler.addNote(nota);
//                System.out.println(is);
            }
        });
        AlertDialog alert = dialog.create();
        alert.show();
    }

    public void showAlerDialogEdit(String t, String b, String d, boolean bool) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        LayoutInflater inflate = this.getLayoutInflater();
        View da = inflate.inflate(R.layout.menu_add_activity, null);
        dialog.setView(da);
        final CheckBox speciale;
        final TextView titolo;
        final EditText noteTitle, noteBody, noteDate;
        titolo = (TextView) da.findViewById(R.id.titolo_inflater);
        titolo.setText("Edit Todo");
        noteTitle = (EditText) da.findViewById(R.id.title_et);
        noteBody = (EditText) da.findViewById(R.id.body_et);
        noteDate = (EditText) da.findViewById(R.id.date_et);
        speciale = (CheckBox) da.findViewById(R.id.radiobutton_special);
        noteTitle.setText(t);
        noteBody.setText(b);
        noteDate.setText(d);
        speciale.setChecked(bool);
        dialog.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Nota nota = adapter.editNota(adapter.getPosition(),
                        noteTitle.getText().toString(),
                        noteBody.getText().toString(),
                        noteDate.getText().toString(),
                        speciale.isChecked());
                long is = databasehandler.updateNote(nota);
//                System.out.println(is);
            }
        });
        AlertDialog alert = dialog.create();
        alert.show();
    }


}
