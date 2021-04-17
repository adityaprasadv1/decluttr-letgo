package com.example.myshoppingstore;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class ProductAdapter extends FirebaseRecyclerAdapter<CreateForm, ProductAdapter.ProductHolder> {
    public ProductAdapter(FirebaseRecyclerOptions<CreateForm> options){
        super(options);
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new ProductHolder(layoutInflater,parent);
    }

    @Override
    protected void onBindViewHolder(@NonNull ProductAdapter.ProductHolder holder, int position, @NonNull CreateForm model) {

    }

    class ProductHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ProductHolder(LayoutInflater inflater,ViewGroup parent){
            super(inflater.inflate(R.layout.row_layout,parent,false));
        }
        @Override
        public void onClick(View v) {

        }
    }
}
