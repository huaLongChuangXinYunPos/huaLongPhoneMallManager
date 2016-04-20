package zhaoq.hl.hlphonemallmanager.activity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import zhaoq.hl.hlphonemallmanager.BaseActivity;
import zhaoq.hl.hlphonemallmanager.R;

public class BrandManageActivity extends BaseActivity {

    private ImageView icBack;
    private Button addInfo;

    @Override
    protected void findView() {
        setContentView(R.layout.activity_brand_info);
        addInfo = (Button) findViewById(R.id.add_brand_info_btn);
        icBack  = (ImageView) findViewById(R.id.ic_back);

        icBack.setOnClickListener(this);
        addInfo.setOnClickListener(this);
    }

    @Override
    protected void myOnclick(View v) {
        switch(v.getId()){
            case R.id.ic_back:
                finish();
                break;

            case R.id.add_brand_info_btn:
                //弹出  对话框  录入品拍新戏

                break;
        }
    }
}
