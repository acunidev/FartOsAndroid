package com.example.fartosandroid.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.fartosandroid.R;
import com.example.fartosandroid.dto.CartaView;
import com.example.fartosandroid.listenerinterfaces.SelectListenerCarta;
import java.util.List;

public class AdapterCartaView extends RecyclerView.Adapter<AdapterCartaView.MyViewHolder> {

  private List<CartaView> cartaViewList;
  private SelectListenerCarta listenerCarta;

  public AdapterCartaView(List<CartaView> cartaViewList, SelectListenerCarta listenerCarta) {
    this.cartaViewList = cartaViewList;
    this.listenerCarta = listenerCarta;
  }

  @NonNull
  @Override
  public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new MyViewHolder(
        LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_card, parent, false));
  }

  @Override
  public void onBindViewHolder(@NonNull MyViewHolder holder,
                               @SuppressLint("RecyclerView") int position) {
    holder.bindData(cartaViewList.get(position));

    holder.cardView.setOnClickListener(
        v -> listenerCarta.onItemClicked(cartaViewList.get(position)));
  }

  @Override
  public int getItemCount() {
    return cartaViewList.size();
  }


  public class MyViewHolder extends RecyclerView.ViewHolder {
    ImageView cartaImg;
    CardView cardView;

    public MyViewHolder(@NonNull View itemView) {
      super(itemView);
      cartaImg = itemView.findViewById(R.id.carta);
      cardView = itemView.findViewById(R.id.cv_card);

    }

    void bindData(final CartaView cartaView) {
      cartaImg.setImageResource(cartaView.getSkin());
    }
  }
}
