package pl.nowakprojects.notebook;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    public static final String NOTE_ID_EXTRA = "pl.nowakprojects.notebook.id";
    public static final String NOTE_TITLE_EXTRA = "pl.nowakprojects.notebook.title";
    public static final String NOTE_MESSAGE_EXTRA = "pl.nowakprojects.notebook.message";
    public static final String NOTE_CATEGORY_EXTRA = "pl.nowakprojects.notebook.category";
    public static final String NOTE_FRAGMENT_TO_LOAD_EXTRA = "pl.nowakprojects.notebook.Fragment_To_Launch";

    public enum FragmentToLaunch {VIEW, EDIT, CREATE}

    ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        loadPreferences();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, AppPreferences.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_add_note) {
            Intent intent = new Intent(getApplicationContext(), NoteDetailActivity.class);
            intent.putExtra(MainActivity.NOTE_FRAGMENT_TO_LOAD_EXTRA, FragmentToLaunch.CREATE);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_github) {
            Uri githubUri = Uri.parse("https://github.com/nowakprojects/Notebook");
        Intent intent = new Intent(Intent.ACTION_VIEW,githubUri);
        startActivity(intent);
        return true;
    }

        return super.onOptionsItemSelected(item);
    }

    public void loadPreferences(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        boolean isBackGroundDark = sharedPreferences.getBoolean("background_color_dark", false);
        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.mainActivityLayout);
        if(isBackGroundDark){
            mainLayout.setBackgroundColor(Color.parseColor("#3c3f41"));
        }else{
            mainLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }

        String notebookTitle = sharedPreferences.getString("title","NOTEBook");
        setTitle(notebookTitle);

    }
}
