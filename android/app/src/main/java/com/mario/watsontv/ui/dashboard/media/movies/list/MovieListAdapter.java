package com.mario.watsontv.ui.dashboard.media.movies.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mario.watsontv.R;
import com.mario.watsontv.responses.MediaResponse;
import com.mario.watsontv.responses.UserResponse;
import com.mario.watsontv.retrofit.services.MediaService;
import com.mario.watsontv.retrofit.services.UserService;
import com.mario.watsontv.ui.dashboard.media.MediaListListener;
import com.mario.watsontv.util.UtilToken;

import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ViewHolder>{
    private final MediaListListener mListener;
    UserResponse user;
    private List<MediaResponse> data;
    private Context context;
    private UserService userService;
    private MediaService mediaService;
    private String jwt;

    public MovieListAdapter(Context ctx, List<MediaResponse> data, MediaListListener mListener) {
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
        boolean isWatched = viewHolder.mItem.isWatched();
        boolean isWatchlisted = viewHolder.mItem.isWatchlisted();
        boolean isCollected = viewHolder.mItem.isCollected();

        Glide.with(context).load(viewHolder.mItem.getCoverImage()).into(viewHolder.coverImage);
        viewHolder.watchlist.setImageResource(R.drawable.ic_remove_red_eye_black_24dp);
        viewHolder.check.setImageResource(R.drawable.ic_check_white_24dp);
        if (isWatched) viewHolder.check.setImageResource(R.drawable.ic_check_box_black_24dp);
        if (isWatchlisted) viewHolder.watchlist.setImageResource(R.drawable.ic_eye_hide);
        if (viewHolder.mItem.getTitle().length() > 15) viewHolder.title.setText(viewHolder.mItem.getTitle().substring(0, 15) + "...");
        else viewHolder.title.setText(viewHolder.mItem.getTitle());

        try {
            if (viewHolder.mItem.getReleaseDate() != null) {
                viewHolder.releaseDate.setText(String.valueOf(viewHolder.mItem.getReleaseDate().get(Calendar.YEAR)));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        viewHolder.check.setOnClickListener(v -> mListener.updateWatched(viewHolder.mItem.getId()));
        viewHolder.collection.setOnClickListener(v -> mListener.updateCollected(viewHolder.mItem.getId()));
        viewHolder.watchlist.setOnClickListener(v -> mListener.updateWatchlisted(viewHolder.mItem.getId()));
        viewHolder.mView.setOnClickListener(v -> mListener.goToDetail(viewHolder.mItem.getId(), viewHolder.mItem.getMediaType()));
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
