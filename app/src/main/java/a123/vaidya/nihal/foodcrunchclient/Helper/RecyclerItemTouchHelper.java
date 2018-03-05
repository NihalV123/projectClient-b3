package a123.vaidya.nihal.foodcrunchclient.Helper;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import a123.vaidya.nihal.foodcrunchclient.Interface.RecyclerItemTouchHelperListener;
import a123.vaidya.nihal.foodcrunchclient.ViewHolder.CartViewHolder;
import a123.vaidya.nihal.foodcrunchclient.ViewHolder.FavoritesViewHolder;

public class RecyclerItemTouchHelper extends ItemTouchHelper.SimpleCallback{

//    /**
//     * Creates a Callback for the given drag and swipe allowance. These values serve as
//     * defaults
//     * and if you want to customize behavior per ViewHolder, you can override
//     * {@link #getSwipeDirs(RecyclerView, ViewHolder)}
//     * and / or {@link #getDragDirs(RecyclerView, ViewHolder)}.
//     *
//     * @param dragDirs  Binary OR of direction flags in which the Views can be dragged. Must be
//     *                  composed of {@link #LEFT}, {@link #RIGHT}, {@link #START}, {@link
//     *                  #END},
//     *                  {@link #UP} and {@link #DOWN}.
//     * @param swipeDirs Binary OR of direction flags in which the Views can be swiped. Must be
//     *                  composed of {@link #LEFT}, {@link #RIGHT}, {@link #START}, {@link
//     *                  #END},
//     *                  {@link #UP} and {@link #DOWN}.
//     */

        private RecyclerItemTouchHelperListener listener;


    public RecyclerItemTouchHelper(int dragDirs, int swipeDirs, RecyclerItemTouchHelperListener listener) {
        super(dragDirs, swipeDirs);
        this.listener = listener;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
           if(listener != null)
               listener.onSwipe(viewHolder,direction,viewHolder.getAdapterPosition());
    }

    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }


    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        if(viewHolder instanceof  CartViewHolder){
        View foregroundView = ((CartViewHolder)viewHolder).view_foreground;
        getDefaultUIUtil().clearView(foregroundView);}
       else if(viewHolder instanceof FavoritesViewHolder){
            View foregroundView = ((FavoritesViewHolder)viewHolder).view_foreground2;
            getDefaultUIUtil().clearView(foregroundView);}

    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if(viewHolder instanceof CartViewHolder){
        View foregroundView = ((CartViewHolder)viewHolder).view_foreground;
        getDefaultUIUtil().onDraw(c,recyclerView,foregroundView,dX,dY,actionState,isCurrentlyActive);}
        else if(viewHolder instanceof FavoritesViewHolder){
            View foregroundView = ((FavoritesViewHolder)viewHolder).view_foreground2;
            getDefaultUIUtil().onDraw(c,recyclerView,foregroundView,dX,dY,actionState,isCurrentlyActive);}
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if(viewHolder != null)
        {
            if(viewHolder instanceof CartViewHolder){
            View foregroundView = ((CartViewHolder)viewHolder).view_foreground;
            getDefaultUIUtil().onSelected(foregroundView);}
            else if(viewHolder instanceof FavoritesViewHolder){
                View foregroundView = ((FavoritesViewHolder)viewHolder).view_foreground2;
                getDefaultUIUtil().onSelected(foregroundView);}
        }
    }

    @Override
    public void onChildDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
       if(viewHolder instanceof CartViewHolder){
        View foregroundView = ((CartViewHolder)viewHolder).view_foreground;
        getDefaultUIUtil().onDrawOver(c,recyclerView,foregroundView,dX,dY,actionState,isCurrentlyActive);}
        else if(viewHolder instanceof FavoritesViewHolder){
           View foregroundView = ((FavoritesViewHolder)viewHolder).view_foreground2;
           getDefaultUIUtil().onDrawOver(c,recyclerView,foregroundView,dX,dY,actionState,isCurrentlyActive);}
    }
}
