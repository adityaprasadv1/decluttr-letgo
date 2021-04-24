package com.example.myshoppingstore;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class list_fragment extends Fragment {

    RecyclerView recyclerView;
    private Query query;
    FirebaseRecyclerAdapter adapter;
    AlertDialog.Builder builder;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_list_fragment, container,false);
        recyclerView = v.findViewById(R.id.recyclerView);

        query = FirebaseDatabase.getInstance().getReference().child("Products");
        FirebaseRecyclerOptions<CreateForm> options = new FirebaseRecyclerOptions.Builder<CreateForm>().setQuery(query, CreateForm.class).build();

        adapter = new ProductAdapter(options);
        recyclerView = v.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));

        Intent intent = getActivity().getIntent();
        if(intent.getStringExtra("AD")!=null) {
            String toastString   = intent.getStringExtra("AD").toString();
            builder = new AlertDialog.Builder(getContext());
            Toast.makeText(getContext(), toastString, Toast.LENGTH_SHORT).show();
        }
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }
}