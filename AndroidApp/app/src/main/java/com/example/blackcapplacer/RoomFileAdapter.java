package com.example.blackcapplacer;

import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class RoomFileAdapter extends RecyclerView.Adapter<RoomFileAdapter.ViewHolder> {

    private final File directory;
    private List<File> imageFiles;
    private final Consumer<File> onItemClick;

    public RoomFileAdapter(File directory, Consumer<File> onItemClick) {
        this.directory = directory;
        this.onItemClick = onItemClick;
        refresh();
    }

    public void refresh() {
        File[] files = directory.listFiles((dir, name) ->
                name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".png")
        );
        if (files != null) {
            imageFiles = Arrays.asList(files);
        } else {
            imageFiles = List.of();
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_room_file, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        File file = imageFiles.get(position);
        holder.textView.setText(file.getName());

        // 画像のプレビューを読み込む
        holder.thumbnail.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));

        holder.itemView.setOnClickListener(v -> onItemClick.accept(file));
    }

    @Override
    public int getItemCount() {
        return imageFiles.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnail;
        TextView textView;

        ViewHolder(View itemView) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.image_thumbnail);
            textView = itemView.findViewById(R.id.image_name);
        }
    }
}

