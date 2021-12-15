package com.example.moviz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.hardware.lights.LightsManager;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class MainActivity extends AppCompatActivity {

    //http://run.mocky.io/v3/79f722b0-a730-42a0-99aa-36029861f115
    private static String json_url="https://api.themoviedb.org/3/movie/popular?api_key=16189ebd9e3212ad7e4a873d57713406";
    static List<MovieModel> movieList ;
    static RecyclerView recyclerView;
    static LinearLayoutManager layoutManager ;
    static Context mcontext;
    static  LinearLayoutManager linearLayoutManager_grid;
    private ImageView header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        movieList=new ArrayList<>();
        recyclerView=findViewById(R.id.recyclerview);
         linearLayoutManager_grid=new GridLayoutManager(this,3);

        layoutManager =new LinearLayoutManager(this);
        mcontext=this;

        header=findViewById(R.id.headers);
        Glide.with(this).load(R.drawable.header).into(header);

        GetData getData= new GetData();
        getData.execute();


    }

    private class GetData extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground(String... strings) {
            String current = "";

            try{

                URL url;
                HttpURLConnection urlConnection=null;

                try{
                    url=new URL(json_url);
                    urlConnection=(HttpURLConnection) url.openConnection();

                    InputStream is=urlConnection.getInputStream();
                    InputStreamReader isr=new InputStreamReader(is);

                    int data=isr.read();
                    while (data!=-1){
                        current += (char)data;
                        data=isr.read();

                    }

                    return current;

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (urlConnection!= null)
                    {
                        urlConnection.disconnect();

                    }
                }


            } catch (Exception e) {
                e.printStackTrace();
            }


            return current;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject jsonObject=new JSONObject(s);
                JSONArray jsonArray=jsonObject.getJSONArray("results");

                for(int i =0 ;i<jsonArray.length();i++){

                    JSONObject jsonObject1 =jsonArray.getJSONObject(i);

                    MovieModel model =new MovieModel();
                    model.setId(jsonObject1.getString("vote_average"));
                    model.setName(jsonObject1.getString("title"));
                    model.setImg(jsonObject1.getString("poster_path"));
                    movieList.add(model);

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            PutDataIntoRecyclerView(movieList);

        }

        private void PutDataIntoRecyclerView(List<MovieModel> movieList) {
            Adaptery adaptery=new Adaptery(mcontext ,  movieList);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setLayoutManager(linearLayoutManager_grid);
            recyclerView.setAdapter(adaptery);
        }

    }

}