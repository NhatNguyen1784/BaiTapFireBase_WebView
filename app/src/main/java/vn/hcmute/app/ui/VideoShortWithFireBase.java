package vn.hcmute.app.ui;


import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import vn.hcmute.app.R;
import vn.hcmute.app.adapter.VideoFirebaseAdapter;
import vn.hcmute.app.model.Video;

public class VideoShortWithFireBase extends AppCompatActivity {
    ViewPager2 viewPager2;
    private VideoFirebaseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_video_short_with_fire_base);

        viewPager2 = findViewById(R.id.vpager2);
        DatabaseReference mDataBase = FirebaseDatabase.getInstance("https://myproject-b0d65-default-rtdb.asia-southeast1.firebasedatabase.app") .getReference( "videos"); // ten bang tren firebase
        FirebaseRecyclerOptions<Video> options = new FirebaseRecyclerOptions.Builder<Video>()
                .setQuery(mDataBase, Video.class).build();
        adapter = new VideoFirebaseAdapter(options);
        viewPager2.setOrientation(viewPager2.ORIENTATION_VERTICAL);
        viewPager2.setAdapter(adapter);
        //getVideos();
        Log.d("TAG", "Run with fire base thanh cong");
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    private void getVideos() {
        // ** set database*/
        DatabaseReference mDataBase = FirebaseDatabase.getInstance() .getReference( "videos"); // ten bang tren firebase
        FirebaseRecyclerOptions<Video> options = new FirebaseRecyclerOptions.Builder<Video>()
                .setQuery(mDataBase, Video.class).build();
        adapter = new VideoFirebaseAdapter(options);
        viewPager2.setOrientation(viewPager2.ORIENTATION_VERTICAL);
        viewPager2.setAdapter(adapter);
    }
}