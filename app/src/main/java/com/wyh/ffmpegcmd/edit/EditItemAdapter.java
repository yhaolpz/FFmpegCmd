package com.wyh.ffmpegcmd.edit;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wyh.ffmpegcmd.R;
import com.wyh.ffmpegcmd.common.Click;
import com.wyh.ffmpegcmd.util.DeviceUtil;
import com.wyh.ffmpegcmd.util.ViewUtil;

import java.util.List;

/**
 * Created by wyh on 2019/3/17.
 */
public class EditItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private static final int ITEM_SIZE = DeviceUtil.getScreenWidth() / 3;
    private static final int ITEM_IV_SIZE = ITEM_SIZE - DeviceUtil.dip2px(32);
    private List<EditItem> editItems;

    private Click.OnObjectClickListener<EditItem> mEditItemOnClickListener;

    public EditItemAdapter(List<EditItem> editItems) {
        this.editItems = editItems;
    }

    public void setEditItemOnClickListener(Click.OnObjectClickListener<EditItem> mEditItemOnClickListener) {
        this.mEditItemOnClickListener = mEditItemOnClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_edit, viewGroup, false);
        return new ItemVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof ItemVH) {
            ItemVH itemVH = (ItemVH) viewHolder;
            itemVH.bind(editItems.get(i), i);
        }
    }

    @Override
    public int getItemCount() {
        return editItems == null ? 0 : editItems.size();
    }

    @Override
    public void onClick(View v) {
        if (v.getTag() instanceof EditItem) {
            if (mEditItemOnClickListener != null) {
                mEditItemOnClickListener.onObjectClick((EditItem) v.getTag());
            }
        }
    }

    class ItemVH extends RecyclerView.ViewHolder {
        final TextView mTvItem;
        final ImageView mIvItem;
        final View mDvRight;
        private final View mRvRoot;


        ItemVH(@NonNull View itemView) {
            super(itemView);
            mTvItem = itemView.findViewById(R.id.tv_item);
            mRvRoot = itemView.findViewById(R.id.rl_item);
            mIvItem = itemView.findViewById(R.id.iv_item);
            mDvRight = itemView.findViewById(R.id.dv_right);
            mRvRoot.setOnClickListener(EditItemAdapter.this);
            ViewUtil.setViewSize(mRvRoot, ITEM_SIZE, ITEM_SIZE);
            ViewUtil.setViewSize(mIvItem, ITEM_IV_SIZE, ITEM_IV_SIZE);
        }

        void bind(EditItem editItem, int pos) {
            Glide.with(mIvItem).load(editItem.imgRes).into(mIvItem);
            mTvItem.setText(editItem.text);
            mRvRoot.setTag(editItem);
            if (pos % 3 == 2) {
                mDvRight.setVisibility(View.GONE);
            } else {
                mDvRight.setVisibility(View.VISIBLE);
            }
        }
    }
}
