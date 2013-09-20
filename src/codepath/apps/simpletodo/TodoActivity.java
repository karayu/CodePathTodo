package codepath.apps.simpletodo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
//import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class TodoActivity extends Activity {
    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_todo);
        setContentView(R.layout.activity_todo);
        readItems();
        
        lvItems = (ListView) findViewById(R.id.lvItems);
        items = new ArrayList<String>();
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
        items.add("First item");
        items.add("Second item");
        setupListViewListener();
    }
 
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.todo, menu);
        return true;
    }
    
    //deleting items through long click
    private void setupListViewListener() {
    	lvItems.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override

    		public boolean onItemLongClick(AdapterView<?> aView, 
    				View item, int pos, long id) {
    		    items.remove(pos);
    		    //itemsAdapter.notifyDataSetInvalidated();
    		    itemsAdapter.notifyDataSetChanged();
    		    saveItems();
    		    return true;
    		}
    	});	
    }

    //onClick action for "add" button (it adds the item)
    public void addTodoItem(View v) {
    	EditText etNewItem = (EditText)
    			findViewById(R.id.etNewItem);
    	itemsAdapter.add(etNewItem.getText().toString());
    	etNewItem.setText("");
    }

   //reading todo list items from a file
   public void readItems() {
	   File filesDir = getFilesDir();
	   File todoFile = new File(filesDir, "todo.txt");
	   try {
		   items = new ArrayList<String>(FileUtils.readLines(todoFile));
	   } catch (IOException e) {
		   items = new ArrayList<String>();
		   e.printStackTrace();
	   }
   }

   private void saveItems() {
	   File filesDir = getFilesDir();
	   File todoFile = new File(filesDir, "todo.txt");
	   try {
		   FileUtils.writeLines(todoFile, items);
	   } catch (IOException e) {
		   e.printStackTrace();
	   }
   }
   
   
}
