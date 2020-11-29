package com.app.videoplayer;

import android.app.Service;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VideoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VideoFragment extends Fragment {

    private VideoView videoContainer;
    private MediaController mMediaController;
    private AudioManager mAudioManager;
    private Button btnStartVideo;
    private SeekBar seekVolume;
    private TextView txtVolume;

    public VideoFragment() {
        // Required empty public constructor
    }


    public static VideoFragment newInstance() {
        return new VideoFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Uri videoUri = Uri.parse("android.resource://" + getContext().getPackageName() + "/" + R.raw.shape_of_you_video);
        videoContainer.setVideoURI(videoUri);

        mMediaController = new MediaController(getActivity());
        mMediaController.setAnchorView(videoContainer);
        videoContainer.setMediaController(mMediaController);
        mAudioManager = (AudioManager) getActivity().getSystemService(Service.AUDIO_SERVICE);
        seekVolume.setMax(mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));


        btnStartVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoContainer.start();
                btnStartVideo.animate().alpha(0.0f).setDuration(3000);
            }
        });

        seekVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
                    txtVolume.setText(String.valueOf((progress)));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                txtVolume.animate().alpha(1.0f).setDuration(1000);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                txtVolume.animate().alpha(0.0f).setDuration(1000);
            }
        });


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video, container, false);
        videoContainer = view.findViewById(R.id.video_container);
        btnStartVideo = view.findViewById(R.id.btn_start_video);
        seekVolume = view.findViewById(R.id.seek_volume);
        txtVolume = view.findViewById(R.id.txt_volume);

        return view;
    }
}