package zhaoq.hl.hlphonemallmanager.entity;

/**
 * PACKAGE_NAME:zhaoq.hl.hlphonemallmanager.entity
 * CREATE_BY:zhaoqiang
 * AUTHOR_EMAIL:zhaoq_hero@163.com
 * DATE: 2016/04/23  15:03
 */
public final class RequestSellEntitiy {

    /**
     * guizuNo : 1002
     * pinpaiNo : 100201
     * dDate1 : 2015-05-01
     * dDate2 : 2015-05-31
     */

    private String guizuNo;
    private String pinpaiNo;
    private String dDate1;
    private String dDate2;

    public String getGuizuNo() {
        return guizuNo;
    }

    public void setGuizuNo(String guizuNo) {
        this.guizuNo = guizuNo;
    }

    public String getPinpaiNo() {
        return pinpaiNo;
    }

    public void setPinpaiNo(String pinpaiNo) {
        this.pinpaiNo = pinpaiNo;
    }

    public String getDDate1() {
        return dDate1;
    }

    public void setDDate1(String dDate1) {
        this.dDate1 = dDate1;
    }

    public String getDDate2() {
        return dDate2;
    }

    public void setDDate2(String dDate2) {
        this.dDate2 = dDate2;
    }
    @Override
    public String toString() {
        return "{" +
                "guizuNo='" + guizuNo + '\'' +
                ", pinpaiNo='" + pinpaiNo + '\'' +
                ", dDate1='" + dDate1 + '\'' +
                ", dDate2='" + dDate2 + '\'' +
                '}';
    }
}
