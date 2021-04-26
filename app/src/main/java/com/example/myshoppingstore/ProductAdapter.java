package com.example.myshoppingstore;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ProductAdapter extends FirebaseRecyclerAdapter<CreateForm, ProductAdapter.ProductHolder> {
    public ProductAdapter(FirebaseRecyclerOptions<CreateForm> options){
        super(options);
    }

    @NonNull
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new ProductHolder(layoutInflater,parent);
    }

    @Override
    protected void onBindViewHolder(@NonNull ProductAdapter.ProductHolder holder, int position, CreateForm model) {
        StorageReference storeRef = FirebaseStorage.getInstance().getReferenceFromUrl(model.getImage());
        Glide.with(holder.productImage.getContext()).load(storeRef).into(holder.productImage);
        holder.productName.setText(model.getName());
        holder.productPrice.setText(Double.toString(model.getPrice()));
        double d = model.getCondition();
        float rating = (float)d;
        holder.ratingBar.setRating(rating);
        String productId = model.getId();

        Log.i("productId", productId);


    }

    class ProductHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView productImage;
        TextView productName, productPrice;
        CardView cardView;
        RatingBar ratingBar;

        public ProductHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.row_layout, parent, false));
            productImage = itemView.findViewById(R.id.imgProductImage);
            productName = itemView.findViewById(R.id.txtProductName);
            productPrice = itemView.findViewById(R.id.txtProductPrice);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            cardView = itemView.findViewById(R.id.cardViewLayout);
            cardView.setOnClickListener(this); // Making each item in the Products page clickable
        }

        @Override
        public void onClick(View v) {
            CreateForm myCreateAd = getItem(getLayoutPosition());

            String productImage = myCreateAd.getImage();
            String productName = myCreateAd.getName();
            Double productPrice = myCreateAd.getPrice();
            Double productCondition = myCreateAd.getCondition();
            String productDescription = myCreateAd.getDescription();
            String productId = myCreateAd.getId();

            Intent detailsIntent = new Intent(v.getContext(), Details.class);

            // Passing data to next activity through putExtra()
            detailsIntent.putExtra("productImage", productImage);
            detailsIntent.putExtra("productName", productName);
            detailsIntent.putExtra("productPrice", productPrice);
            detailsIntent.putExtra("productDescription", productDescription);
            detailsIntent.putExtra("productCondition", productCondition);
            detailsIntent.putExtra("productId", productId);
            v.getContext().startActivity(detailsIntent);
        }

    }
}
