package freha_tddd13.labb3;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;


import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class InteractiveSearcher extends EditText {

    private int ID;
    private Context context;
    private Map<Integer, List<String>> searchResult;

    public InteractiveSearcher(Context context) {
        super(context);
        this.context = context;
        ID = 0;
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                loadData(s.toString());
                ID++;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

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
            }
        });
        thread.start();
    }

}
