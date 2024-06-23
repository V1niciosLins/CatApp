package com.example.catfacts;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.catfacts.Others.JsonUtils;
import com.example.catfacts.databinding.ActivityMainBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    static final String translateUrl = "https://libretranslate.de/translate";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        int resourceId = getResources().getIdentifier("catfacts","raw", getPackageName());
        int resourceId2= getResources().getIdentifier("cuidados","raw", getPackageName());
        getCatFact(resourceId);
        getCatImage("https://cataas.com/cat?position=center&blur=0&html=true&height=250&width=435");
        getCatCareTip(resourceId2);
        binding.prog.setVisibility(View.GONE);
        binding.BTN.setOnClickListener(click -> {
            binding.prog.setVisibility(View.VISIBLE);
            getCatFact(resourceId);
            getCatImage("https://cataas.com/cat?position=center&blur=0&html=true&height=250&width=435");
            getCatCareTip(resourceId2);
        });
        openCatVideo();
    }

    void getCatFact(int resource){
        String randomFact = "Fato interessante:\n"+JsonUtils.getRandomFact(this, resource, "facts_about_cats");

        binding.text.setText(randomFact);
    }

    void getCatCareTip(int resource){
        String randomCare = "Dica de cuidado gatístico:\n"+
                JsonUtils.getRandomFact(this, resource, "cat_care_tips");

        binding.Dica.setText(randomCare);
    }

    void openCatVideo(){
        binding.BTN2.setOnClickListener(click->{
            String url = "https://youtu.be/XEAyM_3ucDc?si=XFBBWjBngdyRB6yj";
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(i);
        });
    }

    void getCatImage(String url){
        Request request = new Request.Builder().url(url).build();

        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "Erro ao tentar exibir a foto gatistica", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()){
                    final String htmlResponse = response.body().string();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            binding.WebView.loadData(htmlResponse,"text/html","UTF-8");
                            binding.WebView.zoomOut();
                            binding.prog.setVisibility(View.GONE);
                        }
                    });
                }
            }
        });
    }
}