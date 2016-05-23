package pl.nowakprojects.notebook;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NoteViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoteViewFragment extends Fragment {


    public NoteViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentLayout = inflater.inflate(R.layout.fragment_note_view, container, false);

        TextView title = (TextView) fragmentLayout.findViewById(R.id.viewNoteTitle);
        TextView message = (TextView) fragmentLayout.findViewById(R.id.viewNoteMessage);
        ImageView image = (ImageView) fragmentLayout.findViewById(R.id.viewItemNoteImg);

        Bundle extras = getActivity().getIntent().getExtras();

        title.setText(extras.getString(MainActivity.NOTE_TITLE_EXTRA));
        message.setText(extras.getString(MainActivity.NOTE_MESSAGE_EXTRA));
        Note.Category noteCat = (Note.Category) extras.getSerializable(MainActivity.NOTE_CATEGORY_EXTRA);
        image.setImageResource(Note.categoryToDrawable(noteCat));

        return fragmentLayout;
    }

}
