package org.zbigniewcebula.blyatmobil;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.OutputStream;
import java.util.Set;

import android.widget.Button;
import android.widget.Toast;
import android.content.Intent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import java.io.IOException;
import android.os.ParcelUuid;

public class BlyatMobil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*
        Button buttonOne = (Button)findViewById(R.id.button);
        buttonOne.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                lewo();
            }
        });
        Button buttonOne2 = (Button)findViewById(R.id.button2);
        buttonOne2.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                gaz();
            }
        });
        Button buttonOne1 = (Button)findViewById(R.id.button3);
        buttonOne1.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                prawo();
            }
        });
        */


        try {
            init();
        } catch(IOException e) {
            msg("Nolel: " + e.getMessage());
        }

        Intent turnBTon = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(turnBTon,1);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blyat_mobil);
/*
        pairedDevices = myBluetooth.getBondedDevices();
        //ArrayList list = new ArrayList();

        if (pairedDevices.size() > 0) {
            for(BluetoothDevice bt : pairedDevices) {
                if (bt.hashCode() == -1999520770) {
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(bt.getAddress());
                    try {
                        btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);
                        BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                        btSocket.connect();
                        connected = true;
                        msg("Connected!");
                    } catch(IOException e) {
                        connected = false;
                        msg("Error: " + e.getMessage());
                    }
                    break;
                } else {
                    msg(bt.getName() + "\n" + Integer.toString(bt.hashCode()));
                }
                //list.add(); //Get the device's name and the address
            }
        }*/
    }

    private OutputStream outputStream = null;
    //private InputStream inStream;

    BluetoothSocket socket = null;

    BluetoothAdapter blueAdapter = null;
    BluetoothDevice device = null;

    private void init() throws IOException {
        blueAdapter = BluetoothAdapter.getDefaultAdapter();
        if (blueAdapter != null) {
            if (socket == null) {
                if (blueAdapter.isEnabled()) {
                    Set<BluetoothDevice> bondedDevices = blueAdapter.getBondedDevices();

                    if (bondedDevices.size() > 0) {
                        int position = -1;
                        for (BluetoothDevice dev : bondedDevices) {
                            if (dev.hashCode() == -1999520770) {
                                device = dev;
                                break;
                            }
                            ++position;
                        }
                        ParcelUuid[] uuids = device.getUuids();
                        socket = device.createRfcommSocketToServiceRecord(uuids[0].getUuid());
                        socket.connect();
                        outputStream = socket.getOutputStream();
                        // inStream = socket.getInputStream();
                    } else {
                        msg("error" + "No appropriate paired devices.");
                    }
                }
            } else {
                msg("error" + "Bluetooth is disabled.");
            }
        }
    }


    private void msg(String s)
    {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }

    public void gaz(View v) {
        try {
            if (outputStream != null) {
                outputStream.write("c".getBytes());
            } else {
                msg("gaz: err");
            }
        } catch(IOException e) {
            msg("Ex gaz: " + e.getMessage());
        }
    }
    public void tyl(View v) {
        try {
            if (outputStream != null) {
                outputStream.write("d".getBytes());
            } else {
                msg("tyl: err");
            }
        } catch(IOException e) {
            msg("Ex tyl: " + e.getMessage());
        }
    }
    public void lewo(View v) {
        try {
            if (outputStream != null) {
                outputStream.write("b".getBytes());
            } else {
                msg("lewo: err");
            }
        } catch(IOException e) {
            msg("Ex gaz: " + e.getMessage());
        }
    }
    public void prawo(View v) {
        try {
            if (outputStream != null) {
                outputStream.write("a".getBytes());
            } else {
                msg("prawo: err");
            }
        } catch(IOException e) {
            msg("Ex gaz: " + e.getMessage());
        }
    }
}
