package zhaoq.hl.hlphonemallmanager.brand;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import zhaoq.hl.hlphonemallmanager.R;

public class BrandInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView icBack;
    private Button addInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_info);
        addInfo = (Button) findViewById(R.id.add_brand_info_btn);
        icBack  = (ImageView) findViewById(R.id.ic_back);
        icBack.setOnClickListener(this);
        addInfo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
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
