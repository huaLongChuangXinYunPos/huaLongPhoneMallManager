package zhaoq.hl.hlphonemallmanager.tickets.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import zhaoq.hl.hlphonemallmanager.R;

public class AddTicketInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView icBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_update_info);
        icBack  = (ImageView) findViewById(R.id.ic_back);
        icBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.ic_back:
                finish();
                break;

            default:
                break;
        }
    }
}
