package a123.vaidya.nihal.foodcrunchclient.Interface;

import android.support.v7.widget.RecyclerView;

public interface RecyclerItemTouchHelperListener {
    void onSwipe(RecyclerView.ViewHolder viewHolder,int direction,int position);
}
