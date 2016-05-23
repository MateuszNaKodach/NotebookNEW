package pl.nowakprojects.notebook;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class NoteEditFragment extends Fragment {

    private static final String MODIFIER_CATEOGRY = "pl.nowakprojects.notebook.ModifiedCategory";

    private long noteID = 0;
    private Boolean newNote=false;

    private ImageButton noteCatButton;
    private Note.Category savedButtonCateogry;
    private AlertDialog categoryDialogObject, confirmDialogObject;
    private EditText title;
    private EditText message;

    public NoteEditFragment() {
        // Required empty public constructor
    }


    //execute once we change orientation
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle bundle = this.getArguments();

        if(bundle!=null){
            newNote = bundle.getBoolean(NoteDetailActivity.NEW_NOTE_EXTRA, false);
        }

        if (savedInstanceState != null) {
            savedButtonCateogry = (Note.Category) savedInstanceState.get(MODIFIER_CATEOGRY);
        }

        View framgentLayout = inflater.inflate(R.layout.fragment_note_edit, container, false);

        title = (EditText) framgentLayout.findViewById(R.id.editNoteTitle);
        message = (EditText) framgentLayout.findViewById(R.id.editNoteMessage);
        noteCatButton = (ImageButton) framgentLayout.findViewById(R.id.editNoteButton);
        Button savedButton = (Button) framgentLayout.findViewById(R.id.saveNote);

        Intent intent = getActivity().getIntent();
        title.setText(intent.getExtras().getString(MainActivity.NOTE_TITLE_EXTRA, ""));
        message.setText(intent.getExtras().getString(MainActivity.NOTE_MESSAGE_EXTRA, ""));
        noteID = intent.getExtras().getLong(MainActivity.NOTE_ID_EXTRA, 0);

        //we came from our Bundle (saveInstance)
        if (savedButtonCateogry != null) {
            noteCatButton.setImageResource(Note.categoryToDrawable(savedButtonCateogry));
            //otherwise we came from our list fragment
        } else if (!newNote){
            Note.Category noteCat = (Note.Category) intent.getExtras().getSerializable(MainActivity.NOTE_CATEGORY_EXTRA);
            noteCatButton.setImageResource(Note.categoryToDrawable(noteCat));
            savedButtonCateogry = noteCat;
        }

        // Inflate the layout for this fragment
        buildCategoryDialog();
        buildConfirmDialog();

        noteCatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryDialogObject.show();
            }
        });

        savedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialogObject.show();
            }
        });

        return framgentLayout;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putSerializable(MODIFIER_CATEOGRY, savedButtonCateogry);
    }

    public void buildCategoryDialog() {
        final String[] categories = new String[]{"PERSONAL", "TECHNICAL", "QUOTE", "FINANCE"};
        AlertDialog.Builder categoryBuilder = new AlertDialog.Builder(getActivity());
        categoryBuilder.setTitle("Choose Note Category");
        categoryBuilder.setSingleChoiceItems(categories, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which) {
                    case 0:
                        savedButtonCateogry = Note.Category.PERSONAL;
                        noteCatButton.setImageResource(R.drawable.p);
                        break;
                    case 1:
                        savedButtonCateogry = Note.Category.TECHNICAL;
                        noteCatButton.setImageResource(R.drawable.t);
                        break;
                    case 2:
                        savedButtonCateogry = Note.Category.QUOTE;
                        noteCatButton.setImageResource(R.drawable.q);
                        break;
                    case 3:
                        savedButtonCateogry = Note.Category.FINANCE;
                        noteCatButton.setImageResource(R.drawable.f);
                        break;
                }

                categoryDialogObject.cancel();
            }
        });

        categoryDialogObject = categoryBuilder.create();
    }

    public void buildConfirmDialog() {
        AlertDialog.Builder confirmBuilder = new AlertDialog.Builder(getActivity());
        confirmBuilder.setTitle("Are you sure?");
        confirmBuilder.setMessage("Are you sure you want to save the note?");

        confirmBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("SAVED NOTE", "Note was saved");
                //if is a new note create in a database
                NotebookDataBaseAdapter notebookDataBaseAdapter = new NotebookDataBaseAdapter(getActivity().getBaseContext());
                notebookDataBaseAdapter.open();
                if(newNote){
                notebookDataBaseAdapter.createNote(title.getText().toString(),message.getText().toString(),
                        (savedButtonCateogry)==null?Note.Category.PERSONAL : savedButtonCateogry);
                    Toast.makeText(getActivity().getBaseContext(),"The new Note was created!",Toast.LENGTH_LONG).show();
                }else{
                    // if is a old note, update it in a database
                    notebookDataBaseAdapter.updateNote(noteID,title.getText().toString(),message.getText().toString(),savedButtonCateogry);
                }

                notebookDataBaseAdapter.close();

                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        confirmBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //do nothing here
            }
        });

        confirmDialogObject = confirmBuilder.create();
    }

}
