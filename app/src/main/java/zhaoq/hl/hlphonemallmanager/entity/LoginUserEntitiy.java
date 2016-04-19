package zhaoq.hl.hlphonemallmanager.entity;

import java.io.Serializable;

/**
 * PACKAGE_NAME:zhaoq.hl.hlphonemallmanager.entity
 * CREATE_BY:zhaoqiang
 * AUTHOR_EMAIL:zhaoq_hero@163.com
 * DATE: 2016/04/19  11:41
 */
public final class LoginUserEntitiy implements Serializable{

    /**
     * gonghao : 0500
     * guizu : 甯濊豹鍥借嵂
     * guizuno : 1001
     * mingcheng : 瀵艰喘鍛�
     * Pass : null
     */
    private String gonghao;
    private String guizu;
    private String guizuno;
    private String mingcheng;
    private String Pass;

    public String getGonghao() {
        return gonghao;
    }

    public void setGonghao(String gonghao) {
        this.gonghao = gonghao;
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

    public String getMingcheng() {
        return mingcheng;
    }

    public void setMingcheng(String mingcheng) {
        this.mingcheng = mingcheng;
    }

    public String getPass() {
        return Pass;
    }

    public void setPass(String Pass) {
        this.Pass = Pass;
    }

    @Override
    public String toString() {
        return "{" +
                "gonghao='" + gonghao + '\'' +
                ", guizu='" + guizu + '\'' +
                ", guizuno='" + guizuno + '\'' +
                ", mingcheng='" + mingcheng + '\'' +
                ", Pass=" + Pass +
                '}';
    }
}
