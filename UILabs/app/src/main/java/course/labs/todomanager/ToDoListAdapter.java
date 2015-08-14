package course.labs.todomanager;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ToDoListAdapter extends BaseAdapter {

	private final List<ToDoItem> mItems = new ArrayList<ToDoItem>();
	private final Context mContext;

	private static final String TAG = "Lab-UserInterface";

	public ToDoListAdapter(Context context) {

		mContext = context;

	}

	// Add a ToDoItem to the adapter
	// Notify observers that the data set has changed

	public void add(ToDoItem item) {

		mItems.add(item);
		notifyDataSetChanged();

	}

	// Clears the list adapter of all items.

	public void clear() {

		mItems.clear();
		notifyDataSetChanged();

	}

	// Returns the number of ToDoItems

	@Override
	public int getCount() {

		return mItems.size();

	}

	// Retrieve the number of ToDoItems

	@Override
	public Object getItem(int pos) {

		return mItems.get(pos);

	}

	// Get the ID for the ToDoItem
	// In this case it's just the position

	@Override
	public long getItemId(int pos) {

		return pos;

	}

	// Create a View for the ToDoItem at specified position
	// Remember to check whether convertView holds an already allocated View
	// before created a new View.
	// Consider using the ViewHolder pattern to make scrolling more efficient
	// See: http://developer.android.com/training/improving-layouts/smooth-scrolling.html
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		final ToDoItem toDoItem = mItems.get(position);

		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.todo_item, parent, false);
		}

		RelativeLayout itemLayout = (RelativeLayout) convertView.findViewById(R.id.RelativeLayout1);

		// subRow = inflater.inflate(R.layout.sub_row, linearLayout, true);

		// Fill in specific ToDoItem data
		// Remember that the data that goes in this View
		// corresponds to the user interface elements defined
		// in the layout file

		final TextView titleView = (TextView) convertView.findViewById(R.id.titleView);
		titleView.setText(toDoItem.getTitle());


		final CheckBox statusView = (CheckBox) convertView.findViewById(R.id.statusCheckBox);
		Log.e(TAG, toDoItem.getStatus().toString());
		if(toDoItem.getStatus() == ToDoItem.Status.DONE) {
			statusView.setChecked(true);
		}
		if(toDoItem.getStatus() == ToDoItem.Status.NOTDONE){
			statusView.setChecked(false);
		}



		// which is called when the user toggles the status checkbox

		statusView.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
			if(isChecked) {
				toDoItem.setStatus(ToDoItem.Status.DONE);
			} else {
				toDoItem.setStatus(ToDoItem.Status.NOTDONE);
			}

                        
                        
                        
					}
				});

		final TextView priorityView = (TextView) convertView.findViewById(R.id.priorityView);
		Log.e(TAG, toDoItem.getPriority().toString());
		priorityView.setText(toDoItem.getPriority().toString());


		// time String
		final TextView dateView = (TextView) convertView.findViewById(R.id.dateView);
		dateView.setText(ToDoItem.FORMAT.format(toDoItem.getDate()));

		// Return the View you just created
		return itemLayout;

	}
}
