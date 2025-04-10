package vn.hcmute.app.ui;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.hcmute.app.R;
import vn.hcmute.app.adapter.VideoAdapter;
import vn.hcmute.app.model.MessageVideoModel;
import vn.hcmute.app.model.VideoModel;
import vn.hcmute.app.network.APIService;

public class VideoShortActivity extends AppCompatActivity {

    private List<VideoModel> videos = new ArrayList<>();
    private ViewPager2 viewPager2;
    private VideoAdapter videoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_video_short);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        anhxa();
        getVideos();
    }

    private void getVideos() {
        APIService.apiService.getVideos().enqueue(new Callback<MessageVideoModel>() {
            @Override
            public void onResponse(Call<MessageVideoModel> call, Response<MessageVideoModel> response) {
                videos = response.body().getResult();
                videoAdapter = new VideoAdapter(videos, getApplicationContext());
                viewPager2.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
                viewPager2.setAdapter(videoAdapter);
            }

            @Override
            public void onFailure(Call<MessageVideoModel> call, Throwable t) {
                Log.d("TAG", t.getMessage());
            }
        });
    }

    private void anhxa() {
        viewPager2 = findViewById(R.id.viewPagerVideo);
    }
}