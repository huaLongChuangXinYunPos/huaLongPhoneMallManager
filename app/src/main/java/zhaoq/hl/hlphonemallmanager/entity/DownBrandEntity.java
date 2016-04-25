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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DownBrandEntity that = (DownBrandEntity) o;

        if (pinpai != null ? !pinpai.equals(that.pinpai) : that.pinpai != null) return false;
        if (pinpaino != null ? !pinpaino.equals(that.pinpaino) : that.pinpaino != null)
            return false;
        if (guizuNo != null ? !guizuNo.equals(that.guizuNo) : that.guizuNo != null) return false;
        return !(guizu != null ? !guizu.equals(that.guizu) : that.guizu != null);

    }

    @Override
    public int hashCode() {
        int result = pinpai != null ? pinpai.hashCode() : 0;
        result = 31 * result + (pinpaino != null ? pinpaino.hashCode() : 0);
        result = 31 * result + (guizuNo != null ? guizuNo.hashCode() : 0);
        result = 31 * result + (guizu != null ? guizu.hashCode() : 0);
        return result;
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
