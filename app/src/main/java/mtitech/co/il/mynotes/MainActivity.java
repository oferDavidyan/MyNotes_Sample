package mtitech.co.il.mynotes;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.PhoneNumberUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{

    private RecyclerView recyclerView;
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private EditText myEditText;

    BroadcastReceiver myReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            // do my thing here
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myEditText = findViewById(R.id.myEditText);
        myEditText.setVisibility(View.GONE);

        registerReceiver(myReceiver, new IntentFilter(Intent.ACTION_BOOT_COMPLETED));

        recyclerView = (RecyclerView) findViewById(R.id.myListView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        final List<Note> dataSet = new ArrayList<Note>();
        dataSet.add(new Note("Lucnch @ 15:00"));
//        dataSet.add("note 2");
//        dataSet.add("note 3");

        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(dataSet);
        recyclerView.setAdapter(mAdapter);

//        registerForContextMenu(recyclerView);

        myEditText.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
//                PhoneNumberUtils.formatNumber(s, PhoneNumberUtils.FORMAT_NANP);
            }
        });

        myEditText.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
            {
                // Identifier of the action. This will be either the identifier you supplied,
                // or EditorInfo.IME_NULL if being called due to the enter key being pressed.
                if (actionId == EditorInfo.IME_ACTION_NEXT
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || actionId == EditorInfo.IME_ACTION_GO
                        ||
                        (event != null
                                &&
                                event.getAction() == KeyEvent.ACTION_DOWN
                                && event.getKeyCode() == KeyEvent.KEYCODE_ENTER))
                {
                    String newNote = v.getText().toString();
                    if(newNote.length() > 0)
                    {
                        mAdapter.add(new Note(newNote));
                        v.setText("");
                        v.clearFocus();

                        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                        myEditText.setVisibility(View.GONE);
                    }
                }
                return true;
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        int itemId = 1000;
        int groupId = 0;
        int order = 0;
        menu.add(groupId, itemId, order, "Add note");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        boolean parentAns = super.onOptionsItemSelected(item);
        if(item.getItemId() == 1000)
        {
            myEditText.setVisibility(View.VISIBLE);
            return true;
        }
        return parentAns;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Selected To Do Item");
        menu.add(0, 1001, Menu.NONE, "Remove");
        menu.add(0, 1002, Menu.NONE, "Share");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        super.onContextItemSelected(item);
        switch (item.getItemId())
        {
            case (1001):
            {
                AdapterView.AdapterContextMenuInfo menuInfo;
                menuInfo = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
                int index = menuInfo.position;
                mAdapter.remove(index);
                return true;
            }
        }
        return false;

    }
}
