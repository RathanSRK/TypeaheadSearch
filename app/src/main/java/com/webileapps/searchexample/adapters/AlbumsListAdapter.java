package com.webileapps.searchexample.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import com.webileapps.searchexample.R;
import com.webileapps.data.search.Album;

import java.util.List;


public class AlbumsListAdapter extends RecyclerView.Adapter<AlbumsListAdapter.AlbumViewHolder>{

    Context mContext;
    LayoutInflater inflater;
    List<Album> list;

    public AlbumsListAdapter(Context mContext) {
        this.mContext=mContext;
        this.inflater=LayoutInflater.from(mContext);
    }

    @Override
    public AlbumViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view=inflater.inflate(R.layout.album_list_item,parent,false);
        return new AlbumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AlbumViewHolder holder, int position) {

        holder.albumName.setText(list.get(position).getName());

        String path = list.get(position).getImage().get(2).getImageUrl();

        path =  TextUtils.isEmpty(path) ? null : path;

        Picasso.with(mContext).load(path).fit().error(R.mipmap.ic_launcher).placeholder(R.drawable.placeholder).centerInside().into(holder.albumImage);

    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }

    public class AlbumViewHolder extends RecyclerView.ViewHolder {

        ImageView albumImage;
        TextView albumName;

        public AlbumViewHolder(View itemView) {
            super(itemView);
            albumImage= (ImageView) itemView.findViewById(R.id.albumImage);
            albumName= (TextView) itemView.findViewById(R.id.albumName);
        }
    }

    public void setData(List<Album> list)
    {
        this.list=list;
        notifyDataSetChanged();
    }

    public void emptyData() {
        this.list = null;
        notifyDataSetChanged();
    }
}
