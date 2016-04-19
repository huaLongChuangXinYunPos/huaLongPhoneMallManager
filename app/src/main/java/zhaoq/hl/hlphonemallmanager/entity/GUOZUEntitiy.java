package zhaoq.hl.hlphonemallmanager.entity;

/**
 * PACKAGE_NAME:zhaoq.hl.hlphonemallmanager.entity
 * CREATE_BY:zhaoqiang
 * AUTHOR_EMAIL:zhaoq_hero@163.com
 * DATE: 2016/04/19  18:05
 */
public final class GUOZUEntitiy {
    /**
     * guizu : 甯濊豹鍥借嵂
     * guizuno : 1001
     * quno : a11
     * qu : 鑽簵鍖�
     * leixing : 鍖栧鍝�
     * TelCode : 9879D918F2B00F075C56F2347E27EF24
     */
    private String guizu;
    private String guizuno;
    private String quno;
    private String qu;
    private String leixing;
    private String TelCode;

    public String getGuizu() {
        return guizu;
    }

    public void setGuizu(String guizu) {
        this.guizu = guizu;
    }

    public String getGuizuno() {
        return guizuno;
    }

    public void setGuizuno(String guizuno) {
        this.guizuno = guizuno;
    }

    public String getQuno() {
        return quno;
    }

    public void setQuno(String quno) {
        this.quno = quno;
    }

    public String getQu() {
        return qu;
    }

    public void setQu(String qu) {
        this.qu = qu;
    }

    public String getLeixing() {
        return leixing;
    }

    public void setLeixing(String leixing) {
        this.leixing = leixing;
    }

    public String getTelCode() {
        return TelCode;
    }

    public void setTelCode(String TelCode) {
        this.TelCode = TelCode;
    }

    @Override
    public String toString() {
        return "GUOZUEntitiy{" +
                "guizu='" + guizu + '\'' +
                ", guizuno='" + guizuno + '\'' +
                ", quno='" + quno + '\'' +
                ", qu='" + qu + '\'' +
                ", leixing='" + leixing + '\'' +
                ", TelCode='" + TelCode + '\'' +
                '}';
    }
}
