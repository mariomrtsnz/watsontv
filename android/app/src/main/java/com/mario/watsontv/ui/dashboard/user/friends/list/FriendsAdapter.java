package com.mario.watsontv.ui.dashboard.user.friends.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mario.watsontv.R;
import com.mario.watsontv.responses.UserResponse;
import com.mario.watsontv.retrofit.services.UserService;
import com.mario.watsontv.util.UtilToken;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.ViewHolder> {
    private final FriendsListener mListener;
    UserResponse user;
    private List<UserResponse> data;
    private Context context;
    private UserService userService;
    private String jwt;

    public FriendsAdapter(Context ctx, List<UserResponse> data, FriendsListener mListener) {
        this.data = data;
        this.context = ctx;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_user_card, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        jwt = UtilToken.getToken(context);
        viewHolder.mItem = data.get(i);
        boolean isFriend = viewHolder.mItem.isFriend();
        Glide.with(context).load(viewHolder.mItem.getPicture()).into(viewHolder.profilePic);
        viewHolder.username.setText(viewHolder.mItem.getName());
        if (viewHolder.mItem.get_Id().equals(UtilToken.getId(context)))
            viewHolder.befriend.setVisibility(View.GONE);
        if (isFriend)
            viewHolder.befriend.setImageResource(R.drawable.ic_remove_24dp);
        else
            viewHolder.befriend.setImageResource(R.drawable.fab_add);
        viewHolder.befriend.setOnClickListener(v -> mListener.updateFriend(viewHolder.mItem.get_Id()));
        viewHolder.mView.setOnClickListener(v -> mListener.goToUserDetails(viewHolder.mItem.get_Id()));
    }
    @Override
    public int getItemCount() { return data.size(); }

    class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView username;
        public final ImageView profilePic;
        public final FloatingActionButton befriend;
        public boolean isFriend;
        public UserResponse mItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            username = itemView.findViewById(R.id.item_user_card_username);
            profilePic = itemView.findViewById(R.id.item_user_card_profilePic);
            befriend = itemView.findViewById(R.id.item_user_card_fab_add);
        }
    }
}
