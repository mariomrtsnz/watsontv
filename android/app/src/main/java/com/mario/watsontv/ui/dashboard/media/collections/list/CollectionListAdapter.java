package com.mario.watsontv.ui.dashboard.media.collections.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mario.watsontv.R;
import com.mario.watsontv.responses.CollectionResponse;
import com.mario.watsontv.responses.UserResponse;
import com.mario.watsontv.retrofit.generator.AuthType;
import com.mario.watsontv.retrofit.generator.ServiceGenerator;
import com.mario.watsontv.retrofit.services.CollectionService;
import com.mario.watsontv.retrofit.services.UserService;
import com.mario.watsontv.util.UtilToken;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CollectionListAdapter extends RecyclerView.Adapter<CollectionListAdapter.ViewHolder> {
    private final CollectionListListener mListener;
    UserResponse user;
    private List<CollectionResponse> data;
    private Context context;
    private UserService userService;
    private CollectionService collectionService;
    private String jwt;

    public CollectionListAdapter(Context ctx, List<CollectionResponse> data, CollectionListListener mListener) {
        this.data = data;
        this.context = ctx;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_collection_card, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        jwt = UtilToken.getToken(context);
        viewHolder.mItem = data.get(i);
        if (viewHolder.mItem.getName().length() > 15)
            viewHolder.name.setText(viewHolder.mItem.getName().substring(0, 15) + "...");
        else
            viewHolder.name.setText(viewHolder.mItem.getName());
        if (viewHolder.mItem.getDescription().length() > 100)
            viewHolder.description.setText(viewHolder.mItem.getDescription().substring(0, 100) + "...");
        else
            viewHolder.description.setText(viewHolder.mItem.getDescription());
        viewHolder.delete.setOnClickListener(v -> mListener.delete(viewHolder.mItem.getId()));
        viewHolder.mView.setOnClickListener(v -> mListener.goToDetails(viewHolder.mItem.getId()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView name, description;
        public final ImageButton delete;
        public CollectionResponse mItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            name = itemView.findViewById(R.id.item_collection_card_name);
            description = itemView.findViewById(R.id.item_collection_card_description);
            delete = itemView.findViewById(R.id.item_collection_card_ib_delete);
        }
    }
}
