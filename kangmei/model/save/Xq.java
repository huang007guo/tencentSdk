package com.wjj.application.facade.kangmei.model.save;

/**
 * @author haoyanbing
 * @since 2020/4/1
 */
public class Xq {
    private String medicines;
    private String dose;
    private String unit;
    private String goods_num;
    private String remark;
    private String m_usage;
    private String herb_unit_price;

    public String getMedicines() {
        return medicines;
    }

    public void setMedicines(String medicines) {
        this.medicines = medicines;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getGoods_num() {
        return goods_num;
    }

    public void setGoods_num(String goods_num) {
        this.goods_num = goods_num;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getM_usage() {
        return m_usage;
    }

    public void setM_usage(String m_usage) {
        this.m_usage = m_usage;
    }

    public String getHerb_unit_price() {
        return herb_unit_price;
    }

    public void setHerb_unit_price(String herb_unit_price) {
        this.herb_unit_price = herb_unit_price;
    }

    @Override
    public String toString() {
        return "Xq{" +
                "medicines='" + medicines + '\'' +
                ", dose='" + dose + '\'' +
                ", unit='" + unit + '\'' +
                ", goods_num='" + goods_num + '\'' +
                ", remark='" + remark + '\'' +
                ", m_usage='" + m_usage + '\'' +
                ", herb_unit_price='" + herb_unit_price + '\'' +
                '}';
    }
}
