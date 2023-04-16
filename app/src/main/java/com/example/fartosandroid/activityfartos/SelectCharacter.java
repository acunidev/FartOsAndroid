package com.example.fartosandroid.activityfartos;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.fartosandroid.R;

public class SelectCharacter extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_select_character);
    Button button = findViewById(R.id.btnPlay);
    button.setOnClickListener(v -> startActivity(new Intent(this, MainActivity.class)));
  }
}