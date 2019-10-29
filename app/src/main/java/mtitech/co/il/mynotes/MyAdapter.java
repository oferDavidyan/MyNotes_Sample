package mtitech.co.il.mynotes;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>
{
    public List<Note> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener
    {
        // each data item is just a string in this case
        public TextView body;
        public TextView timestamp;

        public MyViewHolder(View v)
        {
            super(v);
            body = v.findViewById(R.id.body);
            timestamp = v.findViewById(R.id.timestamp);
            v.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
        {
//            menu.setHeaderTitle("Selected To Do Item");
            menu.add(0, 1001, Menu.NONE, "Remove").setOnMenuItemClickListener(this);
            menu.add(0, 1002, Menu.NONE, "Share").setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item)
        {
            switch (item.getItemId())
            {
                case (1001):
                {
                    AdapterView.AdapterContextMenuInfo menuInfo;
                    remove(getAdapterPosition());
                    return true;
                }
            }
            return false;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(List<Note> myDataset)
    {
        mDataset = myDataset;
    }


    public void add(Note n)
    {
        mDataset.add(n);
        notifyDataSetChanged();
    }

    public void remove(int itemIndex)
    {
        mDataset.remove(itemIndex);
        notifyDataSetChanged();
    }

    /**
     * Return date in specified format.
     * @param milliSeconds Date in milliseconds
     * @param dateFormat Date format
     * @return String representing date in specified format
     */
    public static String getDate(long milliSeconds, String dateFormat)
    {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        // create a new view
        RelativeLayout rl = (RelativeLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        MyViewHolder vh = new MyViewHolder(rl);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.body.setText(mDataset.get(position).noteBody);
        holder.timestamp.setText(getDate(mDataset.get(position).timestamp, "dd/MM/yyyy hh:mm"));

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount()
    {
        return mDataset.size();
    }
}