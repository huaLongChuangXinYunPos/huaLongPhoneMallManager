package zhaoq.hl.hlphonemallmanager.entity;

/**
 * PACKAGE_NAME:zhaoq.hl.hlphonemallmanager.entity
 * CREATE_BY:zhaoqiang
 * AUTHOR_EMAIL:zhaoq_hero@163.com
 * DATE: 2016/04/20  11:37
 */
public final class DownBrandEntity {
    /**
     * pinpai : 甯濊豹
     * pinpaino : 100101
     * guizuNo : 1001
     * guizu : 甯濊豹鍥借嵂
     */
    private String pinpai;
    private String pinpaino;
    private String guizuNo;
    private String guizu;

    public String getPinpai() {
        return pinpai;
    }

    public void setPinpai(String pinpai) {
        this.pinpai = pinpai;
    }

    public String getPinpaino() {
        return pinpaino;
    }

    public void setPinpaino(String pinpaino) {
        this.pinpaino = pinpaino;
    }

    public String getGuizuNo() {
        return guizuNo;
    }

    public void setGuizuNo(String guizuNo) {
        this.guizuNo = guizuNo;
    }

    public String getGuizu() {
        return guizu;
    }

    public void setGuizu(String guizu) {
        this.guizu = guizu;
    }

    @Override
    public String toString() {
        return "{" +
                "pinpai='" + pinpai + '\'' +
                ", pinpaino='" + pinpaino + '\'' +
                ", guizuNo='" + guizuNo + '\'' +
                ", guizu='" + guizu + '\'' +
                '}';
    }
}
