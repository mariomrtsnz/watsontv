package com.mario.watsontv.ui.dashboard.media;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mario.watsontv.R;
import com.mario.watsontv.responses.ActorResponse;
import com.mario.watsontv.responses.MediaResponse;
import com.mario.watsontv.responses.UserResponse;
import com.mario.watsontv.retrofit.services.MediaService;
import com.mario.watsontv.retrofit.services.UserService;
import com.mario.watsontv.util.UtilToken;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MediaDetailsAdapter extends RecyclerView.Adapter<MediaDetailsAdapter.ViewHolder> {
    private final MediaDetailsListener mListener;
    UserResponse user;
    private List<ActorResponse> data;
    private Context context;
    private UserService userService;
    private MediaService mediaService;
    private String jwt;

    public MediaDetailsAdapter(Context ctx, List<ActorResponse> data, MediaDetailsListener mListener) {
        this.data = data;
        this.context = ctx;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_cast, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        jwt = UtilToken.getToken(context);
        viewHolder.mItem = data.get(i);
        Glide.with(context).load(viewHolder.mItem.getPicture()).into(viewHolder.picture);
        viewHolder.name.setText(viewHolder.mItem.getName());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView name;
        public final ImageView picture;
        public ActorResponse mItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            name = itemView.findViewById(R.id.item_cast_name);
            picture = itemView.findViewById(R.id.item_cast_picture);
        }
    }
}
