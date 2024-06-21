package com.example.catfacts;

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
        getCatFact(resourceId);
        binding.prog.setVisibility(View.GONE);
        binding.BTN.setOnClickListener(click -> {
            binding.prog.setVisibility(View.VISIBLE);
            getCatFact(resourceId);
        });
    }

    void getCatFact(int resource){
        String randomFact = JsonUtils.getRandomFact(this, resource);
        binding.text.setText(randomFact);
        binding.prog.setVisibility(View.GONE);
    }
}