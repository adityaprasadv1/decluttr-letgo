package com.example.myshoppingstore;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.FirebaseDatabase;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.Query;

public class list_fragment extends Fragment {

    RecyclerView recyclerView;
    private Query query;
    FirebaseRecyclerAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_list_fragment,container,false);
        recyclerView = v.findViewById(R.id.recyclerView);

        query = FirebaseDatabase.getInstance().getReference().child("Book");
        FirebaseRecyclerOptions<CreateForm> options = new FirebaseRecyclerOptions.Builder<CreateForm>().setQuery(query, CreateForm.class).build();

        adapter = new ProductAdapter(options);
        recyclerView = v.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }
}