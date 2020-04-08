package damiao.hr.dbviewerdemo;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class BadCat {

    @Id(autoincrement = true)
    private Long id;

    private float bodyHeight;

    private double bodyWidth;

    private String nickName;

    private int weight;

    private byte[] tone;

    @Generated(hash = 2039594558)
    public BadCat(Long id, float bodyHeight, double bodyWidth, String nickName,
            int weight, byte[] tone) {
        this.id = id;
        this.bodyHeight = bodyHeight;
        this.bodyWidth = bodyWidth;
        this.nickName = nickName;
        this.weight = weight;
        this.tone = tone;
    }

    @Generated(hash = 513816651)
    public BadCat() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public float getBodyHeight() {
        return this.bodyHeight;
    }

    public void setBodyHeight(float bodyHeight) {
        this.bodyHeight = bodyHeight;
    }

    public double getBodyWidth() {
        return this.bodyWidth;
    }

    public void setBodyWidth(double bodyWidth) {
        this.bodyWidth = bodyWidth;
    }

    public String getNickName() {
        return this.nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getWeight() {
        return this.weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public byte[] getTone() {
        return this.tone;
    }

    public void setTone(byte[] tone) {
        this.tone = tone;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
