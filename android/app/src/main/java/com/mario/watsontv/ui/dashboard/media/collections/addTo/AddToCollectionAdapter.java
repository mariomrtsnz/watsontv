package com.mario.watsontv.ui.dashboard.media.collections.addTo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.mario.watsontv.R;
import com.mario.watsontv.responses.CollectionResponse;
import com.mario.watsontv.responses.UserResponse;
import com.mario.watsontv.retrofit.services.CollectionService;
import com.mario.watsontv.retrofit.services.UserService;
import com.mario.watsontv.ui.dashboard.media.collections.list.CollectionListListener;
import com.mario.watsontv.util.UtilToken;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AddToCollectionAdapter extends RecyclerView.Adapter<AddToCollectionAdapter.ViewHolder> {
    private final AddToCollectionListener mListener;
    UserResponse user;
    private List<CollectionResponse> data;
    private Context context;
    private UserService userService;
    private CollectionService collectionService;
    private String jwt;

    public AddToCollectionAdapter(Context ctx, List<CollectionResponse> data, AddToCollectionListener mListener) {
        this.data = data;
        this.context = ctx;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_add_collection, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        jwt = UtilToken.getToken(context);
        viewHolder.mItem = data.get(i);
        viewHolder.checkbox.setText(viewHolder.mItem.getName());
        viewHolder.checkbox.setOnClickListener(v -> {
            System.out.println(viewHolder.checkbox.isChecked());
            if (viewHolder.checkbox.isChecked())
                mListener.addToCollections(viewHolder.mItem.getId());
            else
                mListener.removeFromCollections(viewHolder.mItem.getId());
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final CheckBox checkbox;
        public CollectionResponse mItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            checkbox = itemView.findViewById(R.id.item_add_collection_checkbox);
        }
    }
}
