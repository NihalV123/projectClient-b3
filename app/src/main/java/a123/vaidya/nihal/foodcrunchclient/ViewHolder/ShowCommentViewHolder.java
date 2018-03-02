package a123.vaidya.nihal.foodcrunchclient.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import a123.vaidya.nihal.foodcrunchclient.R;

public class ShowCommentViewHolder extends RecyclerView.ViewHolder {
        public TextView txtuserPhone,txtComment;
        public RatingBar ratingBar;

    public ShowCommentViewHolder(View itemView) {
        super(itemView);
        txtComment = (TextView)itemView.findViewById(R.id.textcomment);
        txtuserPhone = (TextView)itemView.findViewById(R.id.textuserPhone);
        ratingBar = (RatingBar)itemView.findViewById(R.id.ratingbar22);

    }
}
