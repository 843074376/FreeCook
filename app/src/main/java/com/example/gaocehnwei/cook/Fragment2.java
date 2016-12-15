package com.example.gaocehnwei.cook;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Parcel;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
/**
 * Created by Alienware on 2016/12/13.
 */

public class Fragment2 extends Fragment {
    private View view;
    private Button playButton;
    private Button stopButton;
    private ListView musicList;

    private IBinder mBinder;
    private ServiceConnection mCon;
    private List<Map<String,String>> listOfMap;

    private static final String PLAYING = "pause";
    private static final String PAUSED = "play";
    private static final Parcel empty = Parcel.obtain();
    private static final String[] musicNames = new String[] {"1.mp3", "2.mp3", "3.mp3"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.d("state","fragment2 onCreateView");
        view=inflater.inflate(R.layout.fragment2, container, false);
        return view;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d("state","fragment1 onCreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        Log.d("state","fragment1 onStart()");
        super.onStart();
        initConnection();
        initButtons();
        initList();
    }
    private boolean controlMusic(int code, Parcel data) {
        boolean res = false;
        try {
            res = mBinder.transact(code, data, null, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    private void initConnection() {
        mCon = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {mBinder = service;}
            @Override
            public void onServiceDisconnected(ComponentName name) {mCon = null;}
        };

        Intent starIntent = new Intent(getActivity(), MusicService.class);
        getActivity().bindService(starIntent, mCon, Context.BIND_AUTO_CREATE);
    }

    private void initButtons() {
        playButton = (Button)view.findViewById(R.id.play);
        stopButton = (Button)view.findViewById(R.id.stop);
        playButton.setTag(PAUSED);
        playButton.setText(PAUSED);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controlMusic(MusicService.PLAY_MUSIC, empty);
                String tag = (playButton.getTag().equals(PLAYING) ? PAUSED : PLAYING);
                playButton.setTag(tag);
                playButton.setText(tag);
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controlMusic(MusicService.STOP_MUSIC, empty);
                playButton.setTag(PAUSED);
                playButton.setText(PAUSED);
            }
        });
    }

    private void initList() {
        musicList = (ListView)view.findViewById(R.id.list);
        listOfMap = new LinkedList<>();
        for(int i = 0; i < musicNames.length; ++i) {
            Map<String,String> map = new LinkedHashMap<>();
            map.put("name", musicNames[i]);
            listOfMap.add(map);
        }
        SimpleAdapter adapter = new SimpleAdapter(getActivity(), listOfMap, R.layout.list_item,
                new String[] {"name"}, new int[] {R.id.music_name});
        musicList.setAdapter(adapter);

        musicList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String,String> clicked = listOfMap.get(position);
                Parcel data = Parcel.obtain();
                data.writeString(clicked.get("name"));
                if(controlMusic(MusicService.PLAY_MUSIC, data)) {
                    playButton.setTag(PLAYING);
                    playButton.setText(PLAYING);
                }
            }
        });
    }

}