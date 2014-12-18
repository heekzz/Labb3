package freha_tddd13.labb3;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainActivity extends ActionBarActivity {

    private EditText textField;
    private TextView textView;
    private String result;
    private int ID = 1;
    private Map<Integer, List<String>> searchResult = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InteractiveSearcher interactiveSearcher = new InteractiveSearcher(this);
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 50);
        addContentView(interactiveSearcher, param);

        textField = (EditText) findViewById(R.id.textField);
        textView = (TextView) findViewById(R.id.textView);
        result = "initial text";

//        textField.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                loadData(s.toString());
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//            }
//        });

    }


    private void loadData(final String input) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                final String result = new Network().getResult(ID, input);
                List<String> words = new LinkedList<>();
                Pattern regex = Pattern.compile("([A-ZÅÄÖ]+)");
                Matcher match = regex.matcher(result);
                while(match.find()) {
                    words.add(match.group(1));
                }
                searchResult.put(ID, words);
                ID++;
                textView.post(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText("");
                    }
                });
            }
        });
        thread.start();
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

}