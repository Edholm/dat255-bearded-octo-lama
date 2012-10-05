package it.chalmers.dat255_bearded_octo_lama.activities;

import it.chalmers.dat255_bearded_octo_lama.AlarmAdapter;
import it.chalmers.dat255_bearded_octo_lama.AlarmController;
import it.chalmers.dat255_bearded_octo_lama.R;
import android.os.Bundle;
import android.widget.ListView;

public class ListAlarmsActivity extends AbstractActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_alarms);
        
        
        AlarmAdapter adapter = new AlarmAdapter(this, 
                R.layout.row_alarm, AlarmController.INSTANCE.getAllAlarms(this));
        
        
        ListView lv = (ListView)findViewById(R.id.listView);
        lv.setAdapter(adapter);
    }
}
