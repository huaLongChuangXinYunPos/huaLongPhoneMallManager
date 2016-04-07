package zhaoq.hl.hlphonemallmanager.tickets;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import zhaoq.hl.hlphonemallmanager.R;
import zhaoq.hl.hlphonemallmanager.tickets.activity.AddTicketInfoActivity;

public class TicketActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView icBack;

    private Button updateInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);

        icBack  = (ImageView) findViewById(R.id.ic_back);
        updateInfo = (Button) findViewById(R.id.tickets_up_info_btn);

        icBack.setOnClickListener(this);
        updateInfo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.ic_back:
                finish();
                break;

            case R.id.tickets_up_info_btn:

                Intent intent = new Intent();
                intent.setClass(this,AddTicketInfoActivity.class);
                startActivity(intent);

                break;

            default:
                break;
        }
    }
}
