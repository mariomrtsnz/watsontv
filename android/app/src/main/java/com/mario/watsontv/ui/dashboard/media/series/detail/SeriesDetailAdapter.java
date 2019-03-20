package com.mario.watsontv.ui.dashboard.media.series.detail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mario.watsontv.R;
import com.mario.watsontv.responses.SeasonResponse;
import com.mario.watsontv.responses.UserResponse;
import com.mario.watsontv.retrofit.services.MediaService;
import com.mario.watsontv.retrofit.services.UserService;
import com.mario.watsontv.util.UtilToken;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SeriesDetailAdapter extends RecyclerView.Adapter<SeriesDetailAdapter.ViewHolder> {

    private List<SeasonResponse> data;
    private final SeriesDetailListener mListener;
    UserResponse user;
    private Context context;
    private UserService userService;
    private MediaService mediaService;
    private String jwt;

    public SeriesDetailAdapter(Context ctx, List<SeasonResponse> data, SeriesDetailListener mListener) {
        this.data = data;
        this.context = ctx;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_season, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        jwt = UtilToken.getToken(context);
        viewHolder.mItem = data.get(i);
        viewHolder.number.setText(String.valueOf(viewHolder.mItem.getNumber()));
        viewHolder.episodes.setText(String.valueOf(viewHolder.mItem.getEpisodes().size()));
        viewHolder.mView.setOnClickListener(v -> mListener.goToSeasonDetail(viewHolder.mItem.getId()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView number, episodes;
        public SeasonResponse mItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            number = itemView.findViewById(R.id.item_season_number);
            episodes = itemView.findViewById(R.id.item_season_tv_episodes);
        }
    }
}
