package zhaoq.hl.hlphonemallmanager.entity;

/**
 * PACKAGE_NAME:zhaoq.hl.hlphonemallmanager.entity
 * CREATE_BY:zhaoqiang
 * AUTHOR_EMAIL:zhaoq_hero@163.com
 * DATE: 2016/04/26  11:43
 */
public final class TicketsInfoToserver {


    /**
     * lsdno : 2016040210001
     * spno : 1002.2
     * shuliang : 1
     * danjia : 150
     * jine : 150
     * lsriqi : 2016-04-01
     * gonghao : 0500
     * mingcheng : 导购员
     */

    private String lsdno;
    private String spno;
    private String shuliang;
    private String danjia;
    private String jine;
    private String lsriqi;
    private String gonghao;
    private String mingcheng;

    public String getLsdno() {
        return lsdno;
    }

    public void setLsdno(String lsdno) {
        this.lsdno = lsdno;
    }

    public String getSpno() {
        return spno;
    }

    public void setSpno(String spno) {
        this.spno = spno;
    }

    public String getShuliang() {
        return shuliang;
    }

    public void setShuliang(String shuliang) {
        this.shuliang = shuliang;
    }

    public String getDanjia() {
        return danjia;
    }

    public void setDanjia(String danjia) {
        this.danjia = danjia;
    }

    public String getJine() {
        return jine;
    }

    public void setJine(String jine) {
        this.jine = jine;
    }

    public String getLsriqi() {
        return lsriqi;
    }

    public void setLsriqi(String lsriqi) {
        this.lsriqi = lsriqi;
    }

    public String getGonghao() {
        return gonghao;
    }

    public void setGonghao(String gonghao) {
        this.gonghao = gonghao;
    }

    public String getMingcheng() {
        return mingcheng;
    }

    public void setMingcheng(String mingcheng) {
        this.mingcheng = mingcheng;
    }

    @Override
    public String toString() {
        return "{" +
                "lsdno='" + lsdno + '\'' +
                ", spno='" + spno + '\'' +
                ", shuliang='" + shuliang + '\'' +
                ", danjia='" + danjia + '\'' +
                ", jine='" + jine + '\'' +
                ", lsriqi='" + lsriqi + '\'' +
                ", gonghao='" + gonghao + '\'' +
                ", mingcheng='" + mingcheng + '\'' +
                '}';
    }
}
