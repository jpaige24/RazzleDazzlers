package razzleDazzlers.mycafemac;

import java.util.ArrayList;

import razzleDazzlers.ratecafemac.R;
import razzleDazzlers.util.DishArrayAdapter;
import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MenuVegeActivity extends ListActivity {
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       
        ArrayList<String> vegeMenu = getIntent().getStringArrayListExtra("vegeMenu");
        ArrayList<String> names = new ArrayList<String>();
        ArrayList<String> des = new ArrayList<String>();
        for(int i=0;i<vegeMenu.size();i++){
			if(i%2 == 0){
				names.add(vegeMenu.get(i));
			}else{
				des.add(vegeMenu.get(i));
			}
		}
		
		setListAdapter(new DishArrayAdapter(this, names, des));
    }
    
	@Override
    protected void onListItemClick(ListView l, View v, int position, long id){

		System.out.println("*****");
		//get selected items
		TextView textView = (TextView) v.findViewById(R.id.name);
		String text = textView.getText().toString();
		System.out.println(text);
		//String selectedValue = (String) getListAdapter().getItem(position);
		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();

	}
    
}
