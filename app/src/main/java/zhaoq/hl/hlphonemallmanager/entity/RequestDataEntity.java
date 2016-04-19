package zhaoq.hl.hlphonemallmanager.entity;

/**
 * PACKAGE_NAME:zhaoq.hl.hlphonemallmanager.entity
 * CREATE_BY:zhaoqiang
 * AUTHOR_EMAIL:zhaoq_hero@163.com
 * DATE: 2016/04/18  15:16
 * dialog 实体类 获取柜组信息数据
 */
public final class RequestDataEntity {
    /**
     * action : telCode
     * telCode : 9879D918F2B00F075C56F2347E27EF24
     * guizuNo : 1001
     * guizuPwd :
     */
    private String action;
    private String telCode;
    private String guizuNo;
    private String guizuPwd;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getTelCode() {
        return telCode;
    }

    public void setTelCode(String telCode) {
        this.telCode = telCode;
    }

    public String getGuizuNo() {
        return guizuNo;
    }

    public void setGuizuNo(String guizuNo) {
        this.guizuNo = guizuNo;
    }

    public String getGuizuPwd() {
        return guizuPwd;
    }

    public void setGuizuPwd(String guizuPwd) {
        this.guizuPwd = guizuPwd;
    }
}
