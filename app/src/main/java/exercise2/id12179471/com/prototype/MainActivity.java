package exercise2.id12179471.com.prototype;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ListView dictionary;
    private EditText searchBox;
    private ArrayAdapter<CharSequence> adaptor;
    private ScrollView scroll;
    private TextView letters;
    private Drawable img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        overridePendingTransition(0, R.anim.anim_slide_out_left);

        dictionary = (ListView) findViewById(R.id.list_view);
        searchBox = (EditText) findViewById(R.id.inputSearch);
        scroll = (ScrollView) findViewById(R.id.scrollView);

        adaptor = ArrayAdapter.createFromResource(this, R.array.words, R.layout.listview_text);
        dictionary.setAdapter(adaptor);

        searchBox.addTextChangedListener(searchFilter);
        populateScrollView();

        /**
         * Adds an empty view for ListView
         */
        View empty = getLayoutInflater().inflate(R.layout.empty_list_item, null, false);
        addContentView(empty, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        dictionary.setEmptyView(empty);

        /**
         * hides the search icon once it gains focus.
         */
        searchBox.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    img = MainActivity.this.getResources().getDrawable(R.drawable.ic_search);
                    searchBox.setCompoundDrawables(img, null, null, null);
                } else {
                    searchBox.setCompoundDrawables(null, null, null, null);
                }
            }
        });

        dictionary.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String word = dictionary.getItemAtPosition(position).toString();
                Intent i = new Intent(MainActivity.this, Details.class);
                i.putExtra("word", word);
                startActivity(i);
                overridePendingTransition(0, R.anim.anim_slide_out_left);
            }
        });


    }

    /**
     * Search Filter for List View
     */
    private TextWatcher searchFilter = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            MainActivity.this.adaptor.getFilter().filter(s);

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    /**
     *  Populates the scroll view with alphabets.
     */
    public void populateScrollView() {
        LinearLayout alpha = new LinearLayout(this);
        alpha.setOrientation(LinearLayout.VERTICAL);

        for (char alphabet = 'A'; alphabet <= 'Z'; alphabet++) {

            letters = new TextView(MainActivity.this);
            letters.setText(String.valueOf(alphabet));
            letters.setTextColor(getResources().getColor(R.color.colorAccent));
            letters.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            alpha.addView(letters);
        }

        scroll.addView(alpha);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Clear the Search Box values once the activity changes
     */
    @Override
    protected void onStop() {
        searchBox.clearFocus();
        searchBox.setText("");
        super.onStop();
    }


}
