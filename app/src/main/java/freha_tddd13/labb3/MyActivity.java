package freha_tddd13.labb3;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import freha_tddd13.labb3.R;


public class MyActivity extends Activity {
    EditText result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        result = (EditText) findViewById(R.id.editText);
        Button n = (Button) findViewById(R.id.button);
        n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadData();
            }
        });


    }

    private void loadData() {
        loadWithThread();
        //loadWithAsync();
    }

    private void loadWithAsync() {
        new NetWorker().execute();
    }

    private void loadWithThread(){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                final String data = doNetworkCall();
                result.post(new Runnable() {
                    @Override
                    public void run() {
                        result.setText(data);
                    }
                });
            }
        });
        t.start();
    }

    private String doNetworkCall(){
        try {
            DefaultHttpClient httpclient = new DefaultHttpClient();
            HttpGet httpget = new HttpGet("http://flask-afteach.rhcloud.com/getnames/3/Emm");
            HttpResponse response = null;
            response = httpclient.execute(httpget);

            BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuffer sb = new StringBuffer("");
            String line = "";
            String NL = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                sb.append(line + NL);
            }
            in.close();
            String result = sb.toString();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my, menu);

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

    private class NetWorker extends AsyncTask<Void,Void,String> {


        @Override
        protected String doInBackground(Void... params) {
            return doNetworkCall();
        }

        @Override
        protected void onPostExecute(String s) {
            result.setText(s);
        }
    }

}