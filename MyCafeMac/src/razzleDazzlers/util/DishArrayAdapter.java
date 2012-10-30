package razzleDazzlers.util;

import java.util.ArrayList;

import razzleDazzlers.ratecafemac.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

public class DishArrayAdapter extends ArrayAdapter<String> {
	private final Context context;
	private final ArrayList<String> names;
	private final ArrayList<String> des;
 
	public DishArrayAdapter(Context context, ArrayList<String> names, ArrayList<String> des) {
		super(context, R.layout.activity_menuall, names);
		this.context = context;
		this.names = names;
		this.des = des;
	}
 
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
		View rowView = inflater.inflate(R.layout.activity_menuall, parent, false);
		TextView name = (TextView) rowView.findViewById(R.id.name);
		name.setText(names.get(position));
		RatingBar bar = (RatingBar) rowView.findViewById(R.id.rating);
		bar.setRating((float) 3.0);
		bar.setFocusable(false);
		bar.setFocusableInTouchMode(false);
		TextView description = (TextView) rowView.findViewById(R.id.description);
		description.setText(des.get(position));
 
		return rowView;
	}
}
