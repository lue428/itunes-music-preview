package com.mcs.luel.itunesmusicpriview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.tabs.TabLayout;
import com.mcs.luel.itunesmusicpriview.pojoClasses.ResultsPojo;
import com.mcs.luel.itunesmusicpriview.pojoClasses.TrackPojo;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    private String TAG = "tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout = findViewById(R.id.tabLayout);

        new mThread(tabLayout.getSelectedTabPosition()).execute();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                new mThread(tab.getPosition()).execute();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                new mThread(tab.getPosition()).execute();
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

//        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                switch (tab.getPosition()){
//                }
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
    }

    class mThread extends AsyncTask<Void, Void, ResultsPojo> {
        int tabPosition = 0;
        public mThread(int tabPosition) {
            this.tabPosition = tabPosition;
        }

        @Override
        protected ResultsPojo doInBackground(Void... voids) {
            ResultsPojo data = new ResultsPojo();
            try {
                String fromNetworkCall = setUpNetworkCall();
                ResultsPojo createdData = createPojo(fromNetworkCall);
                data = createdData;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return data;


        }

        @Override
        protected void onPostExecute(ResultsPojo s) {
            super.onPostExecute(s);
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

            FragmentResults fragmentResults = FragmentResults.newInstance(s);
            transaction.add(R.id.fragment_container, fragmentResults);
            transaction.commit();


        }

        public String setUpNetworkCall() throws Exception{


            final String classicURLString = "https://itunes.apple.com/search?term=classick&amp;amp;media=music&amp;amp;entity=song&amp;amp;limit=50";
            final String popURLString = "https://itunes.apple.com/search?term=pop&amp;amp;media=music&amp;amp;entity=song&amp;amp;limit=50";
            final String rockURLString = "https://itunes.apple.com/search?term=rock&amp;amp;media=music&amp;amp;entity=song&amp;amp;limit=50";
            InputStream is;

            URL classicURL = parseUri(classicURLString);
            HttpURLConnection  classicConn = setUpHttps(classicURL);
            is = classicConn.getInputStream();
            String classicdata = parseRawData(is);

            URL popURL = parseUri(popURLString);
            HttpURLConnection  popConn = setUpHttps(popURL);
            is = popConn.getInputStream();
            String popData = parseRawData(is);

            URL rockURL = parseUri(rockURLString);
            HttpURLConnection  rockConn = setUpHttps(rockURL);
            is = rockConn.getInputStream();
            String rockData = parseRawData(is);
            switch (tabPosition) {
                case 0:
                    return rockData;

                case 1:
                    return classicdata;

                default:
                    return popData;

            }
        }

        private HttpURLConnection setUpHttps(URL url) throws Exception{
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setReadTimeout(1000);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();

            return conn;
        }

        private URL parseUri(String urlString) throws Exception{
            Uri builtUri = Uri.parse(urlString);
            URL toReturn = new URL(builtUri.toString());
            return toReturn;
        }

        public String parseRawData(InputStream toParse) throws IOException {
            String toReturn;
            StringBuilder builder = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(toParse));
            String line;

            while ((line = reader.readLine()) != null) {
                builder.append(line + "\n");
            }

            if (builder.length() == 0) {
                return  null;
            }

            toReturn = builder.toString();
            return toReturn;
        }
    }

    private ResultsPojo createPojo(String data) throws Exception{
        ResultsPojo dataPojo = new ResultsPojo();
        List<TrackPojo> trackDetails = new ArrayList();
        JSONObject resultsJson = new JSONObject(data);
        for(int i = 0; i < resultsJson.getJSONArray("results").length(); i++){
        //for (int i = 0; i < 10; i++) {
            JSONObject trackJson = resultsJson.getJSONArray("results").getJSONObject(i);

            TrackPojo trackInfo = new TrackPojo();

            try {

                if(trackJson.isNull("artistName")){
                    trackInfo.setArtistName("artist name unavailable");
                }
                else trackInfo.setArtistName(trackJson.getString("artistName"));

                if(trackJson.isNull("collectionName")){
                    trackInfo.setCollectionName("collection name unavailable");
                }
                else trackInfo.setCollectionName(trackJson.getString("collectionName"));


                if(trackJson.isNull("trackPrice")){
                    trackInfo.setTrackPrice("price unavailable");
                }
                else trackInfo.setTrackPrice(trackJson.getString("trackPrice"));

                if(trackJson.isNull("previewUrl")){
                }
                else trackInfo.setPreviewURL(trackJson.getString("previewUrl"));


                Drawable defaultDrawable = this.getDrawable(R.drawable.ic_launcher_background);
                trackInfo.setArt(defaultDrawable);

                if(trackJson.isNull("artworkUrl100") || trackJson.getString("artworkUrl100").isEmpty()){
                    trackInfo.setArt(defaultDrawable);
                }
                else{
                    trackInfo.setArtworkUrl100(trackJson.getString("artworkUrl100"));
                    URL url = new URL(trackInfo.getArtworkUrl100());
                    Bitmap bmp;
                    try {
                        bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                        if (bmp.equals(null) || bmp == null) {
                            trackInfo.setArt(defaultDrawable);
                        }
                        else trackInfo.setArt(new BitmapDrawable(getResources(), bmp));
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }


                }

            }
            catch (NullPointerException err) {
                err.printStackTrace();
            }
            trackDetails.add(trackInfo);
        }
        dataPojo.setResults(trackDetails);
        return dataPojo;
    }

}



