package vn.hcmute.app.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import vn.hcmute.app.R;
import vn.hcmute.app.model.Video;

public class VideoFirebaseAdapter extends FirebaseRecyclerAdapter<Video, VideoFirebaseAdapter.MyHolder> {
    public VideoFirebaseAdapter(@NonNull FirebaseRecyclerOptions<Video> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyHolder holder, int position, @NonNull Video model) {
        holder.textVideoDesc.setText(model.getDesc());
        holder.textVideoTitle.setText(model.getTitle());

        holder.progressBar.setVisibility(View.VISIBLE);
        Uri videoUri = Uri.parse(model.getUrl());

        holder.videoView.setVideoURI(videoUri);

        holder.videoView.setOnPreparedListener(mp -> {
            holder.progressBar.setVisibility(View.GONE);

            mp.setLooping(true); // Lặp video thay vì dùng onCompletion
            mp.start();

            // Fix scale video đúng tỷ lệ màn hình
            float videoRatio = mp.getVideoWidth() / (float) mp.getVideoHeight();
            float screenRatio = holder.videoView.getWidth() / (float) holder.videoView.getHeight();
            float scale = videoRatio / screenRatio;

            if (scale >= 1f) {
                holder.videoView.setScaleX(scale);
                holder.videoView.setScaleY(1f);
            } else {
                holder.videoView.setScaleX(1f);
                holder.videoView.setScaleY(1f / scale);
            }
        });

        holder.videoView.setOnErrorListener((mp, what, extra) -> {
            holder.progressBar.setVisibility(View.GONE);
            return true; // Đã xử lý lỗi
        });

        holder.favorites.setOnClickListener(v -> {
            boolean isFav = (boolean) holder.favorites.getTag();
            if (!isFav) {
                holder.favorites.setImageResource(R.drawable.fill_favorite);
                holder.favorites.setTag(true);
            } else {
                holder.favorites.setImageResource(R.drawable.favorite);
                holder.favorites.setTag(false);
            }
        });

        // Gán mặc định icon favorite ban đầu và trạng thái false
        holder.favorites.setImageResource(R.drawable.favorite);
        holder.favorites.setTag(false);
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false);
        return new MyHolder(view);
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        VideoView videoView;
        ProgressBar progressBar;
        TextView textVideoTitle, textVideoDesc;
        ImageView imPerson, favorites, imShare, imMore;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            videoView = itemView.findViewById(R.id.videoView);
            progressBar = itemView.findViewById(R.id.videoProgressBar);
            textVideoTitle = itemView.findViewById(R.id.textVideoTitle);
            textVideoDesc = itemView.findViewById(R.id.textVideoDescription);
            imPerson = itemView.findViewById(R.id.imPerson);
            favorites = itemView.findViewById(R.id.favorites);
            imShare = itemView.findViewById(R.id.imShare);
            imMore = itemView.findViewById(R.id.imMore);
        }
    }
}
