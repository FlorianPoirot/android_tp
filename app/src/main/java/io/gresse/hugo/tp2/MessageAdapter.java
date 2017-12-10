package io.gresse.hugo.tp2;

import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.Date;
import java.util.List;

/**
 * Display chat messages
 * <p>
 * Created by Hugo Gresse on 26/11/2017.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {


    private List<Message> mData;

    public MessageAdapter(List<Message> data) {
        mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_messages, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setData(List<Message> data) {
        mData = data;
        this.notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView    mDateTextView;
        TextView    mUserTextView;
        TextView    mContentTextView;
        ImageView   mUserImageView;

        ViewHolder(View itemView) {
            super(itemView);

            mDateTextView = itemView.findViewById(R.id.dateTextView);
            mUserTextView = itemView.findViewById(R.id.userTextView);
            mContentTextView = itemView.findViewById(R.id.contentTextView);
            mUserImageView = itemView.findViewById(R.id.userImageView);
        }

        void setData(Message message) {
            //String dateString = DateFormat.format("MM/dd/yyyy", new Date(message.timestamp)).toString();

            mDateTextView.setText(
                    DateFormat.format("MM/dd/yyyy", new Date(message.timestamp)).toString()
                            + " à : "
                            + DateFormat.format("hh:mma", new Date(message.timestamp)).toString()
            );

            Glide
                    .with(mUserImageView.getContext())
                    .load(Constant.getGravatarPrefix() + Utils.md5(message.userEmail))
                    .apply(RequestOptions.circleCropTransform())
                    .into(mUserImageView);

            mUserTextView.setText(message.userName + ": ");
            mContentTextView.setText(message.content);
        }
    }
}
