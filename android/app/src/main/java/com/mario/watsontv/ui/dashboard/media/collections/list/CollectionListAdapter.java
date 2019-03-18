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
//        if (data.get(i).)
//            viewHolder.fav.setImageResource(R.drawable.ic_favorite_black_24dp);
        if (data.get(i).getName().length() > 15)
            viewHolder.name.setText(data.get(i).getName().substring(0, 15) + "...");
        else
            viewHolder.name.setText(data.get(i).getName());
        if (data.get(i).getDescription().length() > 100)
            viewHolder.description.setText(data.get(i).getDescription().substring(0, 100) + "...");
        else
            viewHolder.description.setText(data.get(i).getDescription());
//        viewHolder.fav.setOnClickListener(v -> updateFav(viewHolder, data.get(i)));
//        viewHolder.mView.setOnClickListener(v -> mListener.onPropertyClick(v, viewHolder.mItem));
        viewHolder.delete.setOnClickListener(v -> mListener.delete(data.get(i).getId()));
        viewHolder.mView.setOnClickListener(v -> mListener.goToDetails(data.get(i).getId()));
    }

    void updateFav(ViewHolder v, CollectionResponse p) {
        collectionService = ServiceGenerator.createService(CollectionService.class, jwt, AuthType.JWT);
//        if (v.isChecked) {
//            Call<UserResponse> call = mediaService.checkAsWatched(p.getId());
//            call.enqueue(new Callback<UserResponse>() {
//                @Override
//                public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
//                    if (response.code() != 200) {
//                        Toast.makeText(context, "Request Error", Toast.LENGTH_SHORT).show();
//                    } else {
//                        v.fav.setImageResource(R.drawable.ic_favorite_border_white_24dp);
//                        v.isFav = false;
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<UserResponse> call, Throwable t) {
//                    Toast.makeText(context, "Network Failure", Toast.LENGTH_SHORT).show();
//                }
//            });
//        } else {
//            Call<UserResponse> call = mediaService.checkAsWatched(p.getId());
//            call.enqueue(new Callback<UserResponse>() {
//                @Override
//                public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
//                    if (response.code() != 200) {
//                        Toast.makeText(context, "Request Error", Toast.LENGTH_SHORT).show();
//                    } else {
//                        v.fav.setImageResource(R.drawable.ic_favorite_black_24dp);
//                        v.isFav = true;
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<UserResponse> call, Throwable t) {
//                    Toast.makeText(context, "Network Failure", Toast.LENGTH_SHORT).show();
//                }
//            });
//        }
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
