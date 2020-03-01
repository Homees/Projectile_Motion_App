package sk.mtmp.zadanie_1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class SuradniceAdapter extends ArrayAdapter<Suradnice> {

    public SuradniceAdapter(Context context, ArrayList<Suradnice> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Suradnice suradnice = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_list_item, parent, false);
        }

        // Lookup view for data population
        TextView surX = convertView.findViewById(R.id.surX);
        TextView surY = convertView.findViewById(R.id.surY);
        TextView time = convertView.findViewById(R.id.time);

        StringBuilder sb;
        // Populate the data into the template view using the data object
        sb = new StringBuilder().append("X: ").append(suradnice.getX()).append(" m");
        surX.setText(sb.toString());
        sb = new StringBuilder().append("Y: ").append(suradnice.getY()).append(" m");
        surY.setText(sb.toString());
        sb = new StringBuilder().append("Čas: ").append(suradnice.getTime()).append(" s");
        time.setText(sb.toString());

        // Return the completed view to render on screen
        return convertView;

    }

}