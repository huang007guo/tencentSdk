package com.wjj.application.facade.kangmei.model.save;

import java.util.List;

/**
 * @author haoyanbing
 * @since 2020/4/1
 */
public class Pdetail {
    private String user_name;
    private String age;
    private String gender;
    private String tel;
    private String is_suffering;
    private String amount;
    private String type;
    private String is_within;
    private String other_pres_num;
    private String special_instru;
    private String doctor;
    private String money;
    private String is_pregnant;
    private String prescript_remark;
    private String medication_methods;
    private String medication_instruction;
    private String is_invoice;
    private List<Xq> medici_xq;

    public String getIs_invoice() {
        return is_invoice;
    }

    public void setIs_invoice(String is_invoice) {
        this.is_invoice = is_invoice;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getIs_suffering() {
        return is_suffering;
    }

    public void setIs_suffering(String is_suffering) {
        this.is_suffering = is_suffering;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIs_within() {
        return is_within;
    }

    public void setIs_within(String is_within) {
        this.is_within = is_within;
    }

    public String getOther_pres_num() {
        return other_pres_num;
    }

    public void setOther_pres_num(String other_pres_num) {
        this.other_pres_num = other_pres_num;
    }

    public String getSpecial_instru() {
        return special_instru;
    }

    public void setSpecial_instru(String special_instru) {
        this.special_instru = special_instru;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getIs_pregnant() {
        return is_pregnant;
    }

    public void setIs_pregnant(String is_pregnant) {
        this.is_pregnant = is_pregnant;
    }

    public String getPrescript_remark() {
        return prescript_remark;
    }

    public void setPrescript_remark(String prescript_remark) {
        this.prescript_remark = prescript_remark;
    }

    public String getMedication_methods() {
        return medication_methods;
    }

    public void setMedication_methods(String medication_methods) {
        this.medication_methods = medication_methods;
    }

    public String getMedication_instruction() {
        return medication_instruction;
    }

    public void setMedication_instruction(String medication_instruction) {
        this.medication_instruction = medication_instruction;
    }

    public List<Xq> getMedici_xq() {
        return medici_xq;
    }

    public void setMedici_xq(List<Xq> medici_xq) {
        this.medici_xq = medici_xq;
    }

    @Override
    public String toString() {
        return "Pdetail{" +
                "user_name='" + user_name + '\'' +
                ", age='" + age + '\'' +
                ", gender='" + gender + '\'' +
                ", tel='" + tel + '\'' +
                ", is_suffering='" + is_suffering + '\'' +
                ", amount='" + amount + '\'' +
                ", type='" + type + '\'' +
                ", is_within='" + is_within + '\'' +
                ", other_pres_num='" + other_pres_num + '\'' +
                ", special_instru='" + special_instru + '\'' +
                ", doctor='" + doctor + '\'' +
                ", money='" + money + '\'' +
                ", is_pregnant='" + is_pregnant + '\'' +
                ", prescript_remark='" + prescript_remark + '\'' +
                ", medication_methods='" + medication_methods + '\'' +
                ", medication_instruction='" + medication_instruction + '\'' +
                ", is_invoice='" + is_invoice + '\'' +
                ", medici_xq=" + medici_xq +
                '}';
    }
}
