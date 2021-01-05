/**
  * Copyright 2020 bejson.com 
  */
package com.wjj.application.facade.ca.casdk.vo.in;

/**
 *  医师信息同步接口 入参
 *
 * doctorIdType	String	是	职业资格证类别
 * YS:医师资格证
 * YJ：药剂师资格证
 * YY：营养师资格证
 * ZC：助产师证
 * XL：心理咨询师证
 * YL：医疗技师证
 * HS：护士资格证
 * QT:其它
 * doctorName	String	是	医师姓名	用户证件上的有效姓名，只能包含汉字、字母、·
 * uid	String	是	医师证件号码	220121198807060523
 * uidCardType	String	是	医师证件类型
 * SF：身份证
 * HZ：护照
 * phone	String	是	手机号码	18600000000
 * department	String	是	科室
 * orgName	String	否	执业地点
 * doctorId	String	否	资格证号	2015120417541766（无法提供医师资格证号时职业资格证类别传QT，doctorId传空字符串）
 * email	String	否	邮箱
 * title	String	否	职称
 * secDepartment	String	否	二级科室
 * speciality	String	否	医师专长
 * telqualification	String	否	专业技术资格(评)
 * technicaltitle	String	否	专业技术职务(聘)
 * position	String	否	专业技术职务聘用岗位
 * organizationcode	String	否	编制情况
 * uPic	String	否	医师照片（base64）
 * idCardPic	String	否	证件照正面照片（base64）
 * idCardBackPic	String	否	证件照背面 照片（base64）
 * titlePic	String	否	医师职称照片（base64）
 * doctorIdPic	String	否	医师资格证照片（base64）
 */
public class DoctorSynIn{
    private String doctorIdType;
    private String orgName;
    private String position;
    private String uid;
    private String phone;
    private String speciality;
    private String department;
    private String organizationcode;
    private String secDepartment;
    private String title;
    private String doctorId;
    private String email;
    private String uidCardType;
    private String telqualification;
    private String technicaltitle;
    private String doctorName;
    public void setDoctorIdType(String doctorIdType) {
        this.doctorIdType = doctorIdType;
    }
    public String getDoctorIdType() {
        return doctorIdType;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
    public String getOrgName() {
        return orgName;
    }

    public void setPosition(String position) {
        this.position = position;
    }
    public String getPosition() {
        return position;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
    public String getUid() {
        return uid;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getPhone() {
        return phone;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }
    public String getSpeciality() {
        return speciality;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
    public String getDepartment() {
        return department;
    }

    public void setOrganizationcode(String organizationcode) {
        this.organizationcode = organizationcode;
    }
    public String getOrganizationcode() {
        return organizationcode;
    }

    public void setSecDepartment(String secDepartment) {
        this.secDepartment = secDepartment;
    }
    public String getSecDepartment() {
        return secDepartment;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }
    public String getDoctorId() {
        return doctorId;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return email;
    }

    public void setUidCardType(String uidCardType) {
        this.uidCardType = uidCardType;
    }
    public String getUidCardType() {
        return uidCardType;
    }

    public void setTelqualification(String telqualification) {
        this.telqualification = telqualification;
    }
    public String getTelqualification() {
        return telqualification;
    }

    public void setTechnicaltitle(String technicaltitle) {
        this.technicaltitle = technicaltitle;
    }
    public String getTechnicaltitle() {
        return technicaltitle;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }
    public String getDoctorName() {
        return doctorName;
    }


    @Override
    public String toString() {
        return "DoctorSynIn{" +
                "doctorIdType='" + doctorIdType + '\'' +
                ", orgName='" + orgName + '\'' +
                ", position='" + position + '\'' +
                ", uid='" + uid + '\'' +
                ", phone='" + phone + '\'' +
                ", speciality='" + speciality + '\'' +
                ", department='" + department + '\'' +
                ", organizationcode='" + organizationcode + '\'' +
                ", secDepartment='" + secDepartment + '\'' +
                ", title='" + title + '\'' +
                ", doctorId='" + doctorId + '\'' +
                ", email='" + email + '\'' +
                ", uidCardType='" + uidCardType + '\'' +
                ", telqualification='" + telqualification + '\'' +
                ", technicaltitle='" + technicaltitle + '\'' +
                ", doctorName='" + doctorName + '\'' +
                '}';
    }
}