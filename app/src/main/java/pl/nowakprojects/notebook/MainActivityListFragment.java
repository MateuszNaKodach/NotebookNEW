package pl.nowakprojects.notebook;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainActivityListFragment extends ListFragment {

    private ArrayList<Note> notes;
    private NoteAdapter noteAdapter;

    @Override
    public void onActivityCreated(Bundle saveInstanceState){
        super.onActivityCreated(saveInstanceState);

/*
        String values[] = new String[]{"Android","iOs","Windows","iOs","Windows","iOs","Windows","iOs","Windows","iOs","Windows","iOs","Windows","iOs","Windows","iOs","Windows","iOs","Windows","iOs","Windows","iOs","Windows","iOs","Windows","iOs","Windows"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, values);

        this.setListAdapter(adapter);*/

        notes = new ArrayList<Note>();
        notes.add(new Note("New Note Titl2e","This is a body of our Note. SDasdasdasdhasjdhasjdhajshdNAUCZAMY i ZATRUDNIAMY!\n" +
                "Kursy Gamers Lab to nowa forma nauki programowania gier i aplikacji mobilnych w Javie i Unity. To rozwiązanie dla osób ceniących pracę w środowisku Grupy Developerskiej, nad realnymi produktami. 3 miesiące - 2x w tyg - 72h zajęć --> tyle potrzebujemy, aby naładować Cię umiejętnościami niezbędnymi przy tworzeniu własnych projektów. Gra, którą wspólnie stworzymy podczas kursu trafi do Google Play'a. Współpracujemy z firmami, które zatrudniają naszych kursantów po odbyciu szkolenia i pomagają rozwinąć im karierę. Zapraszam!\n" +
                "www.gamerslab.pl\n" +
                "Poznań ul.Krysiewicza 5/11NAUCZAMY i ZATRUDNIAMY!\n" +
                "Kursy Gamers Lab to nowa forma nauki programowania gier i aplikacji mobilnych w Javie i Unity. To rozwiązanie dla osób ceniących pracę w środowisku Grupy Developerskiej, nad realnymi produktami. 3 miesiące - 2x w tyg - 72h zajęć --> tyle potrzebujemy, aby naładować Cię umiejętnościami niezbędnymi przy tworzeniu własnych projektów. Gra, którą wspólnie stworzymy podczas kursu trafi do Google Play'a. Współpracujemy z firmami, które zatrudniają naszych kursantów po odbyciu szkolenia i pomagają rozwinąć im karierę. Zapraszam!\n" +
                "www.gamerslab.pl\n" +
                "Poznań ul.Krysiewicza 5/11NAUCZAMY i ZATRUDNIAMY!\n" +
                "Kursy Gamers Lab to nowa forma nauki programowania gier i aplikacji mobilnych w Javie i Unity. To rozwiązanie dla osób ceniących pracę w środowisku Grupy Developerskiej, nad realnymi produktami. 3 miesiące - 2x w tyg - 72h zajęć --> tyle potrzebujemy, aby naładować Cię umiejętnościami niezbędnymi przy tworzeniu własnych projektów. Gra, którą wspólnie stworzymy podczas kursu trafi do Google Play'a. Współpracujemy z firmami, które zatrudniają naszych kursantów po odbyciu szkolenia i pomagają rozwinąć im karierę. Zapraszam!\n" +
                "www.gamerslab.pl\n" +
                "Poznań ul.Krysiewicza 5/11", Note.Category.TECHNICAL));
        notes.add(new Note("New Note Title3","This is a body of our Note. SDasdasdasdhasjdhasjdhajshd", Note.Category.PERSONAL));
        notes.add(new Note("New Note Title4","This is a body of our Note. SDasdasdasdhasjdhasjdhajshd", Note.Category.PERSONAL));
        notes.add(new Note("New Note Title5","This is a body of our Note. SDasdasdasdhasjdhasjdhajshd", Note.Category.PERSONAL));
        notes.add(new Note("New Note Title6","This is a body of our Note. SDasdasdasdhasjdhasjdhajshd", Note.Category.PERSONAL));
        notes.add(new Note("New Note Title7","This is a body of our Note. SDasdasdasdhasjdhasjdhajshd", Note.Category.PERSONAL));
        notes.add(new Note("New Note Title8","This is a body of our Note. SDasdasdasdhasjdhasjdhajshd", Note.Category.PERSONAL));
        notes.add(new Note("New Note Title9","This is a body of our Note. SDasdasdasdhasjdhasjdhajshd", Note.Category.PERSONAL));
        notes.add(new Note("New Note Title0","This is a body of our Note. SDasdasdasdhasjdhasjdhajshd", Note.Category.PERSONAL));
        notes.add(new Note("New Note Title99","This is a body of our Note. SDasdasdasdhasjdhasjdhajshd", Note.Category.PERSONAL));
        notes.add(new Note("New Note Title777","This is a body of our Note. SDasdasdasdhasjdhasjdhajshd", Note.Category.PERSONAL));
        notes.add(new Note("New Note Title64","This is a body of our Note. SDasdasdasdhasjdhasjdhajshd", Note.Category.PERSONAL));
        notes.add(new Note("New Note Title55","This is a body of our Note. SDasdasdasdhasjdhasjdhajshd", Note.Category.PERSONAL));


        noteAdapter = new NoteAdapter(getActivity(),notes);

        setListAdapter(noteAdapter);

       // this.getListView().setDivider(ContextCompat.getDrawable(getContext(),android.R.color.black));
       // getListView().setDividerHeight(2);

        registerForContextMenu(getListView());

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        launchNoteDetailActivity(MainActivity.FragmentToLaunch.VIEW, position);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater menuInflater = getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.long_press_menu,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        //give the postion of pressed long item
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int rowPosition = info.position;

        switch(item.getItemId()){
            case R.id.edit:{
                Log.d("Menu Click","We pressed Edit");
                launchNoteDetailActivity(MainActivity.FragmentToLaunch.EDIT, rowPosition);
                return true;}
        }
        return super.onContextItemSelected(item);
    }

    private void launchNoteDetailActivity(MainActivity.FragmentToLaunch fragmentToLaunch, int position){
        Note note = (Note) getListAdapter().getItem(position);
        Intent intent = new Intent(getContext(),NoteDetailActivity.class);

        intent.putExtra(MainActivity.NOTE_TITLE_EXTRA,note.getTitle());
        intent.putExtra(MainActivity.NOTE_MESSAGE_EXTRA,note.getMessage());
        intent.putExtra(MainActivity.NOTE_CATEGORY_EXTRA,note.getCategory());
        intent.putExtra(MainActivity.NOTE_ID_EXTRA,note.getId());

        switch(fragmentToLaunch){
            case VIEW:
                intent.putExtra(MainActivity.NOTE_FRAGMENT_TO_LOAD_EXTRA,MainActivity.FragmentToLaunch.VIEW);break;
            case EDIT:
                intent.putExtra(MainActivity.NOTE_FRAGMENT_TO_LOAD_EXTRA,MainActivity.FragmentToLaunch.EDIT);break;
        }
        startActivity(intent);
    }
}
