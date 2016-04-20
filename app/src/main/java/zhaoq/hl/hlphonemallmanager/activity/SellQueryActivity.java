package zhaoq.hl.hlphonemallmanager.activity;

import android.view.View;
import android.widget.ImageView;

import zhaoq.hl.hlphonemallmanager.BaseActivity;
import zhaoq.hl.hlphonemallmanager.R;

public class SellQueryActivity extends BaseActivity{

    private ImageView icBack;

    @Override
    protected void findView() {
        setContentView(R.layout.activity_sell_query);
        icBack  = (ImageView) findViewById(R.id.ic_back);
        icBack.setOnClickListener(this);
    }

    @Override
    protected void myOnclick(View v) {
        switch(v.getId()){
            case R.id.ic_back:
                finish();
                break;
        }
    }

}
