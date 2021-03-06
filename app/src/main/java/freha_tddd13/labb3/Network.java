package freha_tddd13.labb3;


import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Network {

    public Network() {
    }



    private String doNetworkCall(int ID, String input){
        try {
            DefaultHttpClient httpclient = new DefaultHttpClient();
            HttpGet httpget = new HttpGet("http://flask-afteach.rhcloud.com/getnames/" + ID + "/" + input);
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
        return "Det blev inget :(";

    }

    public String getResult(int ID, String input) {
        return doNetworkCall(ID, input);
    }
}
