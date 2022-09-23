package com.mutanplex.wolplex;

import android.annotation.SuppressLint;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.mutanplex.wolplex.databinding.ActivityMainBinding;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class MainActivity extends AppCompatActivity {
    public TextView status;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        WifiManager wifi = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        if (wifi.isWifiEnabled()) {
            status = root.findViewById(R.id.status);
            Thread thread = new Thread(() -> {
                try {
                    DatagramPacket packet = PacketBuilder.buildMagicPacket("192.168.1.255", "2C:F0:5D:0C:74:8C", 7);
                    DatagramSocket socket = new DatagramSocket();
                    socket.send(packet);
                    socket.close();
                    status.setText("Computer turned on");
                } catch (Exception e) {
                    status.setText(e.getMessage());
                }
            });
            thread.start();
        } else {
            binding.status.setText("Turn on wifi");
        }

        setContentView(root);
    }
}