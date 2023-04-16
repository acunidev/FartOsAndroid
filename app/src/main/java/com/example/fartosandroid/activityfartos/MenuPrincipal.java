package com.example.fartosandroid.activityfartos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.fartosandroid.R;

public class MenuPrincipal extends AppCompatActivity implements View.OnClickListener {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_menu_principal);
    Button jugadors3 = findViewById(R.id.button_3_jugadors);
    Button jugadors4 = findViewById(R.id.button_4_jugadors);
    Button jugadors5 = findViewById(R.id.button_5_jugadors);
    Button jugadors6 = findViewById(R.id.button_6_jugadors);

    jugadors3.setOnClickListener(this);
    jugadors4.setOnClickListener(this);
    jugadors5.setOnClickListener(this);
    jugadors6.setOnClickListener(this);

  }

  @Override
  public void onClick(View v) {
    startActivity(new Intent(this, SelectCharacter.class));
  }
}