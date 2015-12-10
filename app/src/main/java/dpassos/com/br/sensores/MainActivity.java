package dpassos.com.br.sensores;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;


public class MainActivity extends ActionBarActivity implements SensorEventListener {
    private SensorManager sm;
    List<Sensor> sensores;
    TextView txtValores ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensores = sm.getSensorList(Sensor.TYPE_ALL);

        ListView liv = (ListView)findViewById(R.id.listView);
        liv.setAdapter(new MeuAdapter());

        txtValores = (TextView)findViewById(R.id.textView);
    }

    @Override
    public void onResume() {
        super.onResume();
        Sensor senAccelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sm.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onPause() {
        sm.unregisterListener(this);
        super.onPause();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        String valores = "X "+event.values[0]+ "\nY "+event.values[1]+ "\nZ "+event.values[2];
        txtValores.setText(valores);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    class MeuAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return sensores.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if(v == null){
                LayoutInflater inf = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inf.inflate(R.layout.linha_layout, null);
            }
            TextView txt = (TextView)v.findViewById(R.id.textView);
            txt.setText(sensores.get(position).getName());
            return v;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
