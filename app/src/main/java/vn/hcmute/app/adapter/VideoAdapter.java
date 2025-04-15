package vn.hcmute.app.adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.hcmute.app.R;
import vn.hcmute.app.model.VideoModel;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private List<VideoModel> videos;
    private Context context;

    private boolean isFavorite = false;

    public VideoAdapter(List<VideoModel> videos, Context context) {
        this.videos = videos;
        this.context = context;
    }

    @NonNull
    @Override
    public VideoAdapter.VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoAdapter.VideoViewHolder holder, int position) {
        VideoModel video = videos.get(position);
        holder.videoTitle.setText(video.getTitle());
        holder.videoDescription.setText(video.getDescription());
        holder.videoView.setVideoURI(Uri.parse(video.getUrl()));
        holder.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                holder.videoProgressPar.setVisibility(View.GONE);
                mediaPlayer.start();

                float videoRatio = mediaPlayer.getVideoWidth() / (float) mediaPlayer.getVideoHeight();
                float screenRatio = holder.videoView.getWidth()/ (float) holder.videoView.getHeight();
                float scale = videoRatio / screenRatio;
                if(scale >= 1f){
                    holder.videoView.setScaleX(scale);
                }
                else {
                    holder.videoView.setScaleY(scale);
                }
            }
        });

        holder.videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                holder.videoView.setMediaController(new MediaController(context));
                holder.videoView.requestFocus();
                mediaPlayer.start();
            }
        });

        holder.favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isFavorite){
                    // doi mau icon
                    holder.favorites.setImageResource(R.drawable.fill_favorite);
                    isFavorite = true;
                }
                else {
                    holder.favorites.setImageResource(R.drawable.favorite);
                    isFavorite = false;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return videos == null ? 0 : videos.size();
    }


    public class VideoViewHolder extends RecyclerView.ViewHolder{

        private VideoView videoView;
        private ProgressBar videoProgressPar;
        private TextView videoTitle, videoDescription;
        private ImageView imgPerson, favorites, imgShare, imgMore;
        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);

            videoView = itemView.findViewById(R.id.videoView);
            videoProgressPar = itemView.findViewById(R.id.videoProgressBar);
            videoTitle = itemView.findViewById(R.id.textVideoTitle);
            videoDescription = itemView.findViewById(R.id.textVideoDescription);
            imgPerson = itemView.findViewById(R.id.imPerson);
            imgShare = itemView.findViewById(R.id.imShare);
            favorites = itemView.findViewById(R.id.favorites);
            imgPerson = itemView.findViewById(R.id.imMore);
        }
    }
}
