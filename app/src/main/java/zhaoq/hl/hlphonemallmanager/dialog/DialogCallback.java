package zhaoq.hl.hlphonemallmanager.dialog;

import zhaoq.hl.hlphonemallmanager.entity.DownBrandEntity;
import zhaoq.hl.hlphonemallmanager.entity.DownGUIGUGoodsEntiity;

/**
 * PACKAGE_NAME:zhaoq.hl.hlphonemallmanager.dialog
 * CREATE_BY:zhaoqiang
 * AUTHOR_EMAIL:zhaoq_hero@163.com
 * DATE: 2016/04/21  09:42
 */
public interface DialogCallback {
    void dialogCallbackSelectedItem(int position,String authority);

    void dialogCallbackInputDate(DownBrandEntity brand,String authority);
}
