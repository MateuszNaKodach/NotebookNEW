package pl.nowakprojects.notebook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Mateusz on 2016-05-23.
 */
public class NotebookDataBaseAdapter {

    public static final String DATABASE_NAME = "notebook.db";
    public static final int DATABASE_VERSION = 1;

    public static final String NOTE_TABLE = "note";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_MESSAGE = "message";
    public static final String COLUMN_CATEGORY = "category";
    public static final String COLUMN_DATE = "date";

    private String[] allColumns = {COLUMN_ID, COLUMN_TITLE, COLUMN_MESSAGE, COLUMN_CATEGORY, COLUMN_DATE};

    //SQL CREATE TABLE Statement
    public static final String CREATE_TABLE_NOTE = "CREATE TABLE " + NOTE_TABLE + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_TITLE + " text not null, "
            + COLUMN_MESSAGE + " text not null, "
            + COLUMN_CATEGORY + " text not null, "
            + COLUMN_DATE + ");";

    private SQLiteDatabase sqlDB;
    private Context context;

    private NotebookDBHelper notebookDBHelper;

    public NotebookDataBaseAdapter(Context ctx){
        this.context = ctx;
    }

    public NotebookDataBaseAdapter open() throws android.database.SQLException{
        notebookDBHelper = new NotebookDBHelper(this.context);
        sqlDB = notebookDBHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        notebookDBHelper.close();
    }

    public Note createNote(String title, String message, Note.Category category){
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_MESSAGE, message);
        values.put(COLUMN_CATEGORY, category.name());
        values.put(COLUMN_DATE, Calendar.getInstance().getTimeInMillis()+""); // "" to convert to string

        long insertID = sqlDB.insert(NOTE_TABLE, null, values);

        //set cursor on our new note
        Cursor cursor = sqlDB.query(NOTE_TABLE,allColumns, COLUMN_ID +" = "+insertID,null,null,null,null);

        cursor.moveToFirst(); //first is the only note (the new) form this query
        Note newNote = cursorToNote(cursor);
        cursor.close();

        return newNote;
    }

    //long - how many rows we deleted
    public long deleteNote(long idToDelete){
        return sqlDB.delete(NOTE_TABLE,COLUMN_ID + " = "+ idToDelete,null);
    }

    public long updateNote(long idToUpdate, String newTitle, String newMessage, Note.Category newCategory){
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, newTitle);
        values.put(COLUMN_MESSAGE, newMessage);
        values.put(COLUMN_CATEGORY, newCategory.name());
        values.put(COLUMN_DATE, Calendar.getInstance().getTimeInMillis()+""); // "" to convert to string

        return sqlDB.update(NOTE_TABLE, values, COLUMN_ID + " = "+ idToUpdate, null);
    }




    public ArrayList<Note> getAllNotes(){
        ArrayList<Note> notes = new ArrayList<Note>();

        //grab all inforamtion in our database for the notes in it
        Cursor cursor = sqlDB.query(NOTE_TABLE, allColumns, null, null, null, null, null);

        for(cursor.moveToLast(); !cursor.isBeforeFirst(); cursor.moveToPrevious()){
            Note note = cursorToNote(cursor);
            notes.add(note);
        }

        cursor.close();

        return notes;
    }

    private Note cursorToNote(Cursor cursor){
        Note newNote = new Note(cursor.getString(1),cursor.getString(2),Note.Category.valueOf(cursor.getString(3)), cursor.getLong(0), cursor.getLong(4));
        return newNote;
    }

    private static class NotebookDBHelper extends SQLiteOpenHelper{

        NotebookDBHelper(Context context){
            super(context,DATABASE_NAME,null,DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            //create note table
            db.execSQL(CREATE_TABLE_NOTE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            //execute whenerver database is updating
            Log.w(NotebookDBHelper.class.getName(),
                    "Upgrading database from version:"+ oldVersion +" to version: "+newVersion +", which will destroy all old data.");
            db.execSQL("DROP TABLE IS EXISTS "+ NOTE_TABLE);
            onCreate(db);
        }
    }
}
