package zhaoq.hl.hlphonemallmanager.entity;

import java.io.Serializable;

/**
 * PACKAGE_NAME:zhaoq.hl.hlphonemallmanager.entity
 * CREATE_BY:zhaoqiang
 * AUTHOR_EMAIL:zhaoq_hero@163.com
 * DATE: 2016/04/20  11:49
 */
public final class DownGUIGUGoodsEntiity implements Serializable {
    /**
     * guizu : 甯濊豹鍥借嵂
     * guizuno : 1001
     * pinpai : 甯濊豹
     * pinpaino : 100101
     * cSpno_POS : 1
     * Dw1 :
     * SpNo : 1001
     * Mingcheng : 甯濊豹鍥借嵂
     * Bzjj : 0.0
     * Bzlsj : 0.0
     * Danwei :
     * guige :
     */

    private String amount;//购买 数量
    private String money;//购买 金额


    private String guizu;
    private String guizuno;
    private String pinpai;
    private String pinpaino;
    private String cSpno_POS;
    private String Dw1;
    private String SpNo;
    private String Mingcheng;
    private double Bzjj;
    private double Bzlsj;
    private String Danwei;
    private String guige;

    public String getcSpno_POS() {
        return cSpno_POS;
    }

    public void setcSpno_POS(String cSpno_POS) {
        this.cSpno_POS = cSpno_POS;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

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

    public String getCSpno_POS() {
        return cSpno_POS;
    }

    public void setCSpno_POS(String cSpno_POS) {
        this.cSpno_POS = cSpno_POS;
    }

    public String getDw1() {
        return Dw1;
    }

    public void setDw1(String Dw1) {
        this.Dw1 = Dw1;
    }

    public String getSpNo() {
        return SpNo;
    }

    public void setSpNo(String SpNo) {
        this.SpNo = SpNo;
    }

    public String getMingcheng() {
        return Mingcheng;
    }

    public void setMingcheng(String Mingcheng) {
        this.Mingcheng = Mingcheng;
    }

    public double getBzjj() {
        return Bzjj;
    }

    public void setBzjj(double Bzjj) {
        this.Bzjj = Bzjj;
    }

    public double getBzlsj() {
        return Bzlsj;
    }

    public void setBzlsj(double Bzlsj) {
        this.Bzlsj = Bzlsj;
    }

    public String getDanwei() {
        return Danwei;
    }

    public void setDanwei(String Danwei) {
        this.Danwei = Danwei;
    }

    public String getGuige() {
        return guige;
    }

    public void setGuige(String guige) {
        this.guige = guige;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DownGUIGUGoodsEntiity entiity = (DownGUIGUGoodsEntiity) o;

        if (Double.compare(entiity.Bzjj, Bzjj) != 0) return false;
        if (Double.compare(entiity.Bzlsj, Bzlsj) != 0) return false;
        if (guizu != null ? !guizu.equals(entiity.guizu) : entiity.guizu != null) return false;
        if (guizuno != null ? !guizuno.equals(entiity.guizuno) : entiity.guizuno != null)
            return false;
        if (pinpai != null ? !pinpai.equals(entiity.pinpai) : entiity.pinpai != null) return false;
        if (pinpaino != null ? !pinpaino.equals(entiity.pinpaino) : entiity.pinpaino != null)
            return false;
        if (cSpno_POS != null ? !cSpno_POS.equals(entiity.cSpno_POS) : entiity.cSpno_POS != null)
            return false;
        if (Dw1 != null ? !Dw1.equals(entiity.Dw1) : entiity.Dw1 != null) return false;
        if (SpNo != null ? !SpNo.equals(entiity.SpNo) : entiity.SpNo != null) return false;
        if (Mingcheng != null ? !Mingcheng.equals(entiity.Mingcheng) : entiity.Mingcheng != null)
            return false;
        if (Danwei != null ? !Danwei.equals(entiity.Danwei) : entiity.Danwei != null) return false;
        return !(guige != null ? !guige.equals(entiity.guige) : entiity.guige != null);

    }

    @Override
    public int hashCode() {
        int result = 1;
        long temp;
        result = 31 * result + (guizu != null ? guizu.hashCode() : 0);
        result = 31 * result + (guizuno != null ? guizuno.hashCode() : 0);
        result = 31 * result + (pinpai != null ? pinpai.hashCode() : 0);
        result = 31 * result + (pinpaino != null ? pinpaino.hashCode() : 0);
        result = 31 * result + (cSpno_POS != null ? cSpno_POS.hashCode() : 0);
        result = 31 * result + (Dw1 != null ? Dw1.hashCode() : 0);
        result = 31 * result + (SpNo != null ? SpNo.hashCode() : 0);
        result = 31 * result + (Mingcheng != null ? Mingcheng.hashCode() : 0);
        temp = Double.doubleToLongBits(Bzjj);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(Bzlsj);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (Danwei != null ? Danwei.hashCode() : 0);
        result = 31 * result + (guige != null ? guige.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "{" +
                "amount='" + amount + '\'' +
                ", money='" + money + '\'' +
                ", guizu='" + guizu + '\'' +
                ", guizuno='" + guizuno + '\'' +
                ", pinpai='" + pinpai + '\'' +
                ", pinpaino='" + pinpaino + '\'' +
                ", cSpno_POS='" + cSpno_POS + '\'' +
                ", Dw1='" + Dw1 + '\'' +
                ", SpNo='" + SpNo + '\'' +
                ", Mingcheng='" + Mingcheng + '\'' +
                ", Bzjj=" + Bzjj +
                ", Bzlsj=" + Bzlsj +
                ", Danwei='" + Danwei + '\'' +
                ", guige='" + guige + '\'' +
                '}';
    }
}



