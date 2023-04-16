package com.example.fartosandroid.activityfartos;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.fartosandroid.CartaOchoDialogFragment;
import com.example.fartosandroid.DialogFragmentCarta;
import com.example.fartosandroid.LayoutManager.ScaleCenterItemLayoutManager;
import com.example.fartosandroid.R;
import com.example.fartosandroid.adapter.AdapterCartaView;
import com.example.fartosandroid.adapter.AdapterCasilla;
import com.example.fartosandroid.dto.CartaView;
import com.example.fartosandroid.dto.Casilla;
import com.example.fartosandroid.dto.Jugador;
import com.example.fartosandroid.listenerinterfaces.SelectListenerCarta;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SelectListenerCarta {
  private static final List<Casilla> CASILLAS = new ArrayList<>();
  private final List<Jugador> players = new ArrayList<>();
  private Casilla casillaFinal;
  private List<CartaView> baralla = new ArrayList<>();
  private AdapterCasilla adapterCasilla;
  private RecyclerView cartaRV;
  private RecyclerView casillaRV;
  private Jugador user;
  private int backgroundTransparent;

  @SuppressLint("MissingInflatedId")
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    Button buttonShowHideCartas;
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    cartaRV = findViewById(R.id.recyclerCartas);
    buttonShowHideCartas = findViewById(R.id.btnMostrarOcultarCartas);
    buttonShowHideCartas.setOnClickListener(view -> {
      if (cartaRV.getVisibility() == View.VISIBLE) {
        cartaRV.setVisibility(View.GONE);
        buttonShowHideCartas.setText(R.string.mostrarCartas);
      } else {
        cartaRV.setVisibility(View.VISIBLE);
        buttonShowHideCartas.setText(R.string.ocultarCartas);
      }
    });
    backgroundTransparent = R.drawable.background_transparente;
    initJugadors();
    user = players.get(0);
    initMap();
    initBaraja();
    shuffleCartas();

  }

  public void initJugadors() {
    players.add(new Jugador("Sasuke", R.drawable.sprite_sasuke, R.drawable.icono_sasuke));
    players.add(new Jugador("Naruto", R.drawable.sprite_naruto_kyubi, R.drawable.icon_naruto));
    players.add(new Jugador("Kakashi", R.drawable.sprite_kakashi, R.drawable.icon_kakashi));
  }

  public void shuffleCartas() {
    int numCardsPerPlayer = (players.size() >= 5) ? 5 : 6;
    for (Jugador jugador : players) {
      for (int i = 0; i < numCardsPerPlayer; i++) {
        jugador.getCartasMano().add(baralla.remove(0));
      }
    }
    recyclerBaraja();
  }

  public void recyclerBaraja() {
    List<CartaView> cartaEnMano = user.getCartasMano();

    if (cartaEnMano.isEmpty()) {
      cartaRV.setVisibility(View.GONE);
      String toastMessage = "T'has quedat sense cartes a la baralla";
      if (casillaFinal.getRonda() == 3) {
        toastMessage += "\nRonda finalitzada " + casillaFinal.getRonda();
      } else {
        toastMessage += "\nNova ronda! " + (casillaFinal.getRonda() + 1);
        casillaFinal.setRonda(casillaFinal.getRonda() + 1);
        initBaraja();
        shuffleCartas();
      }
      Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();
    } else {
      cartaRV.setVisibility(View.VISIBLE);
      if (cartaRV.getAdapter() == null) {
        AdapterCartaView adapterCartaView = new AdapterCartaView(cartaEnMano, this);
        cartaRV.setAdapter(adapterCartaView);
        ScaleCenterItemLayoutManager linearLayoutManager =
            new ScaleCenterItemLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        cartaRV.setLayoutManager(linearLayoutManager);
        cartaRV.setHasFixedSize(true);
      }
    }
  }

  public void recyclerCasillas() {
    adapterCasilla = new AdapterCasilla(CASILLAS);
    casillaRV.setHasFixedSize(true);
    casillaRV.setLayoutManager(new LinearLayoutManager(this));
    casillaRV.setAdapter(adapterCasilla);
  }

  public void initBaraja() {
    baralla = new ArrayList<>();
    int[] quantitats = {28, 18, 10, 3, 4, 3, 2, 2}; // cantidad de cada tipo de carta
    int[] ids = {R.drawable.moure_1_naruto, R.drawable.moure_2_naruto, R.drawable.moure_3_naruto,
        R.drawable.teleport_naruto, R.drawable.zancadilla_naruto, R.drawable.patada_naruto,
        R.drawable.hundimiento_naruto,
        R.drawable.broma_naruto}; // ids de imagen de cada tipo de carta
    String[] noms =
        {"Moure 1", "Moure2", "Moure3", "Teleport", "Zancadilla", "Patada", "Hundimiento",
            "Broma"}; // nombres de cada tipo de carta

    for (int i = 0; i < quantitats.length; i++) {
      for (int j = 0; j < quantitats[i]; j++) {
        baralla.add(new CartaView(noms[i], ids[i], i + 1));
      }
    }
    Collections.shuffle(baralla);
  }

  public void initMap() {
    casillaRV = findViewById(R.id.recyclerCasillas);
    for (int i = 0; i < 16; i++) {
      CASILLAS.add(new Casilla(String.valueOf(i), i, backgroundTransparent, backgroundTransparent));
    }
    casillaFinal = CASILLAS.get(CASILLAS.size() - 1);
    recyclerCasillas();

    move(players.get(2), 0);
    move(players.get(0), 1);
    move(players.get(1), 1);

  }

  public void move(Jugador jugador, int move) {
    int nCasilla1 = Math.max(jugador.getNumCasilla(), 0);
    int nCasilla2 = nCasilla1 + move;
    if (nCasilla2 <= 0) {
      nCasilla2 = 0;
    }
    if (nCasilla2 >= CASILLAS.size() - 1) {
      nCasilla2 = CASILLAS.size() - 1;
    }
    jugador.setNumCasilla(nCasilla2);

    if (CASILLAS.get(nCasilla2).getP1() == backgroundTransparent) {
      if (jugador.isPlayer()) {
        CASILLAS.get(nCasilla1).setP1(backgroundTransparent);
      } else {
        CASILLAS.get(nCasilla1).setP2(backgroundTransparent);
      }
      CASILLAS.get(nCasilla2).setP1(jugador.getSprite());
      jugador.setPlayer(true);
    } else if (CASILLAS.get(nCasilla2).getP2() == backgroundTransparent) {
      if (jugador.isPlayer()) {
        CASILLAS.get(nCasilla1).setP1(backgroundTransparent);
      } else {
        CASILLAS.get(nCasilla1).setP2(backgroundTransparent);
      }
      CASILLAS.get(nCasilla2).setP2(jugador.getSprite());
      jugador.setPlayer(false);
    }

    casillaRV.setAdapter(adapterCasilla);
  }

  //  Teleport: Intercanvia les posicions de 2 jugadors objectiu. 3 cartes
  public void teleport(Jugador user, Jugador selectedJugador) {
    int casillaUser = user.getNumCasilla();
    boolean pUser = user.isPlayer();
    int casillaSelectedJugador = selectedJugador.getNumCasilla();
    boolean pSelectedJugador = selectedJugador.isPlayer();
    user.setNumCasilla(casillaSelectedJugador);
    user.setPlayer(pSelectedJugador);
    selectedJugador.setNumCasilla(casillaUser);
    selectedJugador.setPlayer(pUser);

    if (user.isPlayer()) {
      CASILLAS.get(user.getNumCasilla()).setP1(user.getSprite());
    } else {
      CASILLAS.get(user.getNumCasilla()).setP2(user.getSprite());
    }
    if (selectedJugador.isPlayer()) {
      CASILLAS.get(selectedJugador.getNumCasilla()).setP1(backgroundTransparent);
      CASILLAS.get(selectedJugador.getNumCasilla()).setP1(selectedJugador.getSprite());
    } else {
      CASILLAS.get(selectedJugador.getNumCasilla()).setP2(selectedJugador.getSprite());
    }
    recyclerCasillas();
  }

  //  Zancadilla: El Jugador objectiu perd una carta de la seva mà. 4
  public void zancadilla(Jugador jugador) {
    jugador.getCartasMano().remove((int) (Math.random() * jugador.getCartasMano().size()));
  }

  /*
   * Patada: Durant una ronda el jugador objectiu
   * veu minoritzada en 1 l’efecte de les
   * seves cartes 3 cartes
   */
  public void patada(Jugador jugador) {
    jugador.setPatada(true);
  }

  /*
   * Hundimiento: El jugador objectiu retorna a
   * la casella de sortida 2 cartes
   */
  public void hundimiento(Jugador jugador) {
    int move = jugador.getNumCasilla();
    move(jugador, -move);
  }

  //  Broma: Intercanvia les cartes amb el jugador objectiu 2 cartes
  public void broma(Jugador player, Jugador selectedPlayer) {
    int posTrob = -1;
    boolean trobada = false;
    int pos = 0;
    for (CartaView c : player.getCartasMano()) {
      if (c.getTipus() == 8 && !trobada) {
        posTrob = pos;
        trobada = true;
      }
      pos = pos + 1;
    }
    if (!(player.getCartasMano().isEmpty())) {
      player.getCartasMano().remove(posTrob);
    }
    List<CartaView> handUser = player.getCartasMano();
    List<CartaView> handSelectedJugador = selectedPlayer.getCartasMano();
    player.setMa(handSelectedJugador);
    selectedPlayer.setMa(handUser);

    recyclerBaraja();
  }

  //  Teleport: Intercanvia les posicions de 2 jugadors objectiu. 3 cartes
  public void casilla8(Jugador user) {
    CartaOchoDialogFragment cartaOchoDialogFragment = new CartaOchoDialogFragment();
    Bundle bundle = new Bundle();
    bundle.putSerializable("user", user);
    bundle.putSerializable("jugadors", (Serializable) players);
    cartaOchoDialogFragment.setArguments(bundle);
    cartaOchoDialogFragment.show(getSupportFragmentManager(), "Casilla 8 Fragment");
  }

  public void deleteCasilla() {
    players.removeIf(jugador -> jugador.getNumCasilla() == 0);
    for (Jugador jugador : players) {
      jugador.setNumCasilla(jugador.getNumCasilla() - 1);
    }
    CASILLAS.remove(0);

  }

  public void checkWin(Jugador jugador) {
    if (jugador.getNumCasilla() == CASILLAS.size() - 1) {
      Toast.makeText(this, "Guanyador " + jugador.getName() + "!", Toast.LENGTH_SHORT).show();
    }
    if (players.size() == 1) {
      Toast.makeText(this, "Guanyador " + players.get(0).getName() + "!", Toast.LENGTH_SHORT)
           .show();
    }
  }

  @Override
  public void onItemClicked(CartaView cartaView) {
    CartaView cartaSeleccionada;
    cartaSeleccionada = cartaView;
    Jugador jugador = players.get(0);
    DialogFragmentCarta dialogFragmentCarta = new DialogFragmentCarta();
    Bundle bundle = new Bundle();
    bundle.putSerializable("carta", cartaSeleccionada);
    bundle.putSerializable("jugadors", (Serializable) players);
    bundle.putSerializable("user", jugador);
    bundle.putSerializable("casillas", (Serializable) CASILLAS);
    dialogFragmentCarta.setArguments(bundle);
    dialogFragmentCarta.show(getSupportFragmentManager(), "Carta Fragment");
  }


}