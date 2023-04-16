package com.example.fartosandroid;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.fartosandroid.LayoutManager.ScaleCenterItemLayoutManager;
import com.example.fartosandroid.activityfartos.MainActivity;
import com.example.fartosandroid.adapter.AdapterJugadorBtn;
import com.example.fartosandroid.dto.Jugador;
import com.example.fartosandroid.listenerinterfaces.SelectListenerJugador;
import java.util.ArrayList;
import java.util.List;

public class CartaOchoDialogFragment extends DialogFragment implements SelectListenerJugador {

  List<Jugador> jugadors = new ArrayList<>();
  Jugador selectedJugador;
  AdapterJugadorBtn adapterJugadorBtn;
  RecyclerView btnRv;
  Jugador user;
  boolean isUser = false;

  @SuppressLint("MissingInflatedId")
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

    LayoutInflater inflater = getActivity().getLayoutInflater();
    View view = inflater.inflate(R.layout.cas8_dialog_fragment, null);

    Bundle bundle = getArguments();
    jugadors = (List<Jugador>) bundle.getSerializable("jugadors");
    user = (Jugador) bundle.getSerializable("user");
    btnRv = view.findViewById(R.id.recylerPlayers8);

    initElements();

    builder.setView(view);
    return builder.create();
  }

  public void initElements() {
    adapterJugadorBtn = new AdapterJugadorBtn(jugadors, this);
    btnRv.setHasFixedSize(true);
    btnRv.setLayoutManager(
        new ScaleCenterItemLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
    btnRv.setAdapter(adapterJugadorBtn);
  }

  @Override
  public void onItemClicked(Jugador jugador) {
    selectedJugador = jugador;
    isUser = selectedJugador.equals(user);
    if (isUser) {
      ((MainActivity) getActivity()).move(selectedJugador, 5);
    } else {
      ((MainActivity) getActivity()).move(selectedJugador, -5);
    }
    dismiss();
  }
}
