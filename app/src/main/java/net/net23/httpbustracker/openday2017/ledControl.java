package net.net23.httpbustracker.openday2017;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.IOException;
import java.util.UUID;

import static net.net23.httpbustracker.openday2017.R.id.green;
import static net.net23.httpbustracker.openday2017.R.id.red;
import static net.net23.httpbustracker.openday2017.R.id.text;
import static net.net23.httpbustracker.openday2017.R.id.yellow;

public class ledControl extends AppCompatActivity implements SensorEventListener {

    // Button btnOn, btnOff, btnDis;
    TextView text;
    SeekBar seekBar;
    Button Discnt, Abt,accStart,accStop;
    String address = null;
    private ProgressDialog progress;
    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;
    private boolean isBtConnected = false;
    //SPP UUID. Look for it
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    //Accelerometer class Objects
    Sensor accelerometer;
    SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_led_control);
            Intent newint = getIntent();
            address = newint.getStringExtra(DeviceList.EXTRA_ADDRESS); //receive the address of the bluetooth device


            //call the widgets
            Discnt = (Button)findViewById(R.id.discnt);
            Abt = (Button)findViewById(R.id.abt);
            accStart = (Button)findViewById(R.id.acc_start);
            accStop = (Button)findViewById(R.id.acc_stop);
            seekBar = (SeekBar)findViewById(R.id.servo_seekbar);
        seekBar.setMax(180);
        new ConnectBT().execute(); //Call the class to connect


        accStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Accelerometer setup
                sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
                accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                sensorManager.registerListener(ledControl.this,accelerometer,SensorManager.SENSOR_DELAY_GAME);
            }
        });
        accStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sensorManager.unregisterListener(ledControl.this);
                if (btSocket!=null)
                {
                    try
                    {
                        seekBar.setProgress(0);
                        btSocket.getOutputStream().write(5);
                    }
                    catch (IOException e)
                    {
                        msg("Error");
                        endActivity();
                    }
                }
            }
        });
         text = (TextView)findViewById(R.id.text);
            //commands to be sent to bluetooth


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (btSocket!=null) //If the btSocket is busy
                {
                    try
                    {
                        if(progress == 0)
                        {
                            progress = 5;
                        }
                        btSocket.getOutputStream().write(progress);
                    }
                    catch (IOException e)
                    { msg("Error");}
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });


            Discnt.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Disconnect(); //close connection
                }
            });


        }

        private void Disconnect()
        {
            if (btSocket==null) //If the btSocket is busy
            {
                try
                {
                    btSocket.close();//close connection
                }
                catch (IOException e)
                { msg("Error");}
            }
            finish(); //return to the first layout

        }

    public void onRedClicked(View view) {

        // Is the toggle on?
        boolean on = ((ToggleButton) view).isChecked();

        if (on) {
            // Enable vibrate
            if (btSocket!=null)
            {
                try
                {
                    btSocket.getOutputStream().write(181);
                }
                catch (IOException e)
                {
                    msg("Error");
                    endActivity();
                }
            }
        } else {
            // Disable vibrate
            if (btSocket!=null)
            {
                try
                {
                    btSocket.getOutputStream().write(191);
                }
                catch (IOException e)
                {
                    msg("Error");
                    endActivity();
                }
            }
        }
    }
    public void onYellowClicked(View view) {
        // Is the toggle on?
        boolean on = ((ToggleButton) view).isChecked();

        if (on) {
            // Enable vibrate
            if (btSocket!=null)
            {
                try
                {
                    btSocket.getOutputStream().write(182);
                }
                catch (IOException e)
                {
                    msg("Error");
                    endActivity();
                }
            }
        } else {
            // Disable vibrate
            if (btSocket!=null)
            {
                try
                {
                    btSocket.getOutputStream().write(192);
                }
                catch (IOException e)
                {
                    msg("Error");
                    endActivity();
                }
            }
        }
    }

    public void onGreenClicked(View view) {
        // Is the toggle on?
        boolean on = ((ToggleButton) view).isChecked();

        if (on) {
            // Enable vibrate
            if (btSocket!=null)
            {
                try
                {
                    btSocket.getOutputStream().write(183);
                }
                catch (IOException e)
                {
                    msg("Error");
                    endActivity();
                }
            }
        } else {
            // Disable vibrate
            if (btSocket!=null)
            {
                try
                {
                    btSocket.getOutputStream().write(193);
                }
                catch (IOException e)
                {
                    msg("Error");
                    endActivity();
                }
            }
        }
    }

    public void onFanClicked(View view) {
        // Is the toggle on?
        boolean on = ((Switch) view).isChecked();

        if (on) {
            // Enable vibrate
            if (btSocket!=null)
            {
                try
                {
                    btSocket.getOutputStream().write(184);
                }
                catch (IOException e)
                {
                    msg("Error");
                    endActivity();
                }
            }
        } else {
            // Disable vibrate
            if (btSocket!=null)
            {
                try
                {
                    btSocket.getOutputStream().write(194);
                }
                catch (IOException e)
                {
                    msg("Error");
                    endActivity();
                }
            }
        }
    }
    public void zero(View view) {
            if (btSocket!=null)
            {
                try
                {
                    seekBar.setProgress(0);
                    btSocket.getOutputStream().write(5);
                }
                catch (IOException e)
                {
                    msg("Error");
                    endActivity();
                }
            }
    }
    public void fourFive(View view) {
        if (btSocket!=null)
        {
            try
            {
                seekBar.setProgress(45);
                btSocket.getOutputStream().write(45);
            }
            catch (IOException e)
            {
                msg("Error");
                endActivity();
            }
        }
    }
    public void nineZero(View view) {
        if (btSocket!=null)
        {
            try
            {
                seekBar.setProgress(90);
                btSocket.getOutputStream().write(90);
            }
            catch (IOException e)
            {
                msg("Error");
                endActivity();
            }
        }
    }
    public void oneThreeFive(View view) {
        if (btSocket!=null)
        {
            try
            {
                seekBar.setProgress(135);
                btSocket.getOutputStream().write(135);
            }
            catch (IOException e)
            {
                msg("Error");
         endActivity();
            }
        }
    }
    public void oneEightZero(View view) {
        if (btSocket!=null)
        {
            try
            {
                seekBar.setProgress(180);
                btSocket.getOutputStream().write(180);
            }
            catch (IOException e)
            {
                msg("Error");
                endActivity();
            }
        }
    }

    private void endActivity()
    {
        try {
            btSocket.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        finish();
    }

    // fast way to call Toast
        private void msg(String s)
        {
            Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
        }

        public void about(View v)
        {
            if(v.getId() == R.id.abt)
            {
                Intent i = new Intent(this, About.class);
                startActivity(i);
            }
        }

    @Override
    public void onSensorChanged(SensorEvent event) {
        //new CountDownTimer(100, 1000) {
            //public void onTick(long millisUntilFinished) {}
            //public void onFinish() {
                int yAxis = Math.round(event.values[1]);
                int servopos = yAxis * 20;
                        if(servopos == 0)
                        {
                            servopos = 5;
                        }
                        if (btSocket!=null)
                {
                        seekBar.setProgress(servopos);
                }
                    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private class ConnectBT extends AsyncTask<Void, Void, Void>  // UI thread
    {
        private boolean ConnectSuccess = true; //if it's here, it's almost connected

        @Override
        protected void onPreExecute()
        {
            progress = ProgressDialog.show(ledControl.this, "Connecting...", "Please wait!!!");  //show a progress dialog
        }

        @Override
        protected Void doInBackground(Void... devices) //while the progress dialog is shown, the connection is done in background
        {
            try
            {
                if (btSocket == null || !isBtConnected)
                {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(address);//connects to the device's address and checks if it's available
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD_MR1) {
                        btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);//create a RFCOMM (SPP) connection
                    }
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect();//start connection
                }
            }
            catch (IOException e)
            {
                ConnectSuccess = false;//if the try failed, you can check the exception here
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) //after the doInBackground, it checks if everything went fine
        {
            super.onPostExecute(result);
            if (!ConnectSuccess)
            {
                msg("Connection Failed. Is it a SPP Bluetooth? Try again.");
                finish();
            }
            else
            {
                msg("Connected.");
                isBtConnected = true;
            }
            progress.dismiss();
        }
    }
}
