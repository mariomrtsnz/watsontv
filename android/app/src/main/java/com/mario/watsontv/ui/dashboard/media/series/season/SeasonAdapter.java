package com.mario.watsontv.ui.dashboard.media.series.season;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mario.watsontv.R;
import com.mario.watsontv.responses.EpisodeResponse;
import com.mario.watsontv.responses.UserResponse;
import com.mario.watsontv.retrofit.services.MediaService;
import com.mario.watsontv.retrofit.services.UserService;
import com.mario.watsontv.util.UtilToken;

import java.text.ParseException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SeasonAdapter extends RecyclerView.Adapter<SeasonAdapter.ViewHolder>{
    private final SeasonListener mListener;
    UserResponse user;
    private List<EpisodeResponse> data;
    private Context context;
    private UserService userService;
    private MediaService mediaService;
    private String jwt;
    private int seasonNumber;
    Locale locale = Locale.getDefault();

    public SeasonAdapter(Context ctx, List<EpisodeResponse> data, SeasonListener mListener, int seasonNumber) {
        this.data = data;
        this.context = ctx;
        this.mListener = mListener;
        this.seasonNumber = seasonNumber;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_episode_card, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        jwt = UtilToken.getToken(context);
        viewHolder.mItem = data.get(i);
        boolean isWatched = viewHolder.mItem.isWatched();
        boolean isWatchlisted = viewHolder.mItem.isWatchlisted();
//        boolean isCollected = viewHolder.mItem.isCollected();
        viewHolder.check.setImageResource(R.drawable.ic_check_white_24dp);
        viewHolder.watchlist.setImageResource(R.drawable.ic_remove_red_eye_black_24dp);
        if (viewHolder.mItem.getName().length() > 25)
            viewHolder.title.setText(viewHolder.mItem.getName().substring(0, 25) + "...");
        else
            viewHolder.title.setText(viewHolder.mItem.getName());
        viewHolder.number.setText(seasonNumber + "x" + viewHolder.mItem.getNumber());
        viewHolder.synopsis.setText(viewHolder.mItem.getSynopsis());
        viewHolder.runtime.setText(String.valueOf(viewHolder.mItem.getDuration()) + "mins");
        Calendar airTime = null;
        try {
            airTime = viewHolder.mItem.getAirTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String day = String.valueOf(airTime.get(Calendar.DAY_OF_MONTH));
        String month = airTime.getDisplayName(Calendar.MONTH, Calendar.LONG, locale);
        String year = String.valueOf(airTime.get(Calendar.YEAR));
        viewHolder.releaseDate.setText(month + " " + day + ", " + year);
        if (isWatched) viewHolder.check.setImageResource(R.drawable.ic_check_box_black_24dp);
        if (isWatchlisted) viewHolder.watchlist.setImageResource(R.drawable.ic_eye_hide);

        viewHolder.check.setOnClickListener(v -> {
            mListener.updateWatched(viewHolder.mItem.getId());
        });
        viewHolder.collection.setOnClickListener(v -> {
            mListener.updateCollected(viewHolder.mItem.getId());
        });
        viewHolder.watchlist.setOnClickListener(v -> {
            mListener.updateWatchlisted(viewHolder.mItem.getId());
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView title, number, releaseDate, runtime, synopsis;
        public final ImageButton check;
        public final ImageButton collection;
        public final ImageButton watchlist;
        public boolean isChecked;
        public boolean isCollected;
        public boolean isWatchlisted;
        public EpisodeResponse mItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            title = itemView.findViewById(R.id.item_episode_tv_title);
            number = itemView.findViewById(R.id.item_episode_tv_episodeNumber);
            releaseDate = itemView.findViewById(R.id.item_episode_tv_episodeReleaseDate);
            runtime = itemView.findViewById(R.id.item_episode_tv_episodeRuntime);
            synopsis = itemView.findViewById(R.id.item_episode_tv_synopsis);
            check = itemView.findViewById(R.id.item_episode_ib_check);
            collection = itemView.findViewById(R.id.item_episode_ib_collection);
            watchlist = itemView.findViewById(R.id.item_episode_ib_watchlist);
        }
    }
}
