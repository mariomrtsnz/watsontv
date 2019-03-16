package com.mario.watsontv.ui.dashboard.media.series.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mario.watsontv.R;
import com.mario.watsontv.responses.MediaResponse;
import com.mario.watsontv.responses.UserResponse;
import com.mario.watsontv.retrofit.generator.AuthType;
import com.mario.watsontv.retrofit.generator.ServiceGenerator;
import com.mario.watsontv.retrofit.services.MediaService;
import com.mario.watsontv.retrofit.services.UserService;
import com.mario.watsontv.util.UtilToken;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeriesListAdapter extends RecyclerView.Adapter<SeriesListAdapter.ViewHolder>{
    private final SeriesListListener mListener;
    UserResponse user;
    private List<MediaResponse> data;
    private Context context;
    private UserService userService;
    private MediaService mediaService;
    private String jwt;

    public SeriesListAdapter(Context ctx, List<MediaResponse> data, SeriesListListener mListener) {
        this.data = data;
        this.context = ctx;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_media_card, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        jwt = UtilToken.getToken(context);
        viewHolder.mItem = data.get(i);
//        if (data.get(i).)
//            viewHolder.fav.setImageResource(R.drawable.ic_favorite_black_24dp);
        Glide.with(context).load(data.get(i).getCoverImage()).into(viewHolder.coverImage);
        if (data.get(i).getTitle().length() > 15)
            viewHolder.title.setText(data.get(i).getTitle().substring(0, 15) + "...");
        else
            viewHolder.title.setText(data.get(i).getTitle());
//        viewHolder.fav.setOnClickListener(v -> updateFav(viewHolder, data.get(i)));
//        viewHolder.mView.setOnClickListener(v -> mListener.onPropertyClick(v, viewHolder.mItem));
    }

    void updateFav(ViewHolder v, MediaResponse p) {
        mediaService = ServiceGenerator.createService(MediaService.class, jwt, AuthType.JWT);
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
        public final TextView title, releaseDate;
        public final ImageView coverImage;
        public final ImageButton check;
        public final ImageButton collection;
        public final ImageButton watchlist;
        public boolean isChecked;
        public boolean isCollected;
        public boolean isWatchlisted;
        public MediaResponse mItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            title = itemView.findViewById(R.id.item_media_card_tv_title);
            releaseDate = itemView.findViewById(R.id.item_media_card_tv_release_date);
            coverImage = itemView.findViewById(R.id.item_media_card_iv_cover_image);
            check = itemView.findViewById(R.id.item_media_card_ib_check);
            collection = itemView.findViewById(R.id.item_media_card_ib_collect);
            watchlist = itemView.findViewById(R.id.item_media_card_ib_watchlist);
        }

    }
}