/**
  * Copyright 2020 bejson.com 
  */
package com.wjj.application.facade.ca.casdk.vo.in.recipesyn;
import java.util.Date;
import java.util.List;

/**
 * 处方信息
 *
 * urId	String	是	处方ID，字母、数字，或者字母数字组合，长度32位以内	2343d6
 * openId	String	是	医网信医师唯一标识 长度35位
 * patientName	String	是	患者姓名	张三
 * patientAge	String	是	患者年龄	19
 * patientSex	String	是	患者性别	男/女/未知
 * patientCard	String	是	患者医保号身份证护照（如果证件类型为QT，可为空字符串）
 * patientCardType	String	是	证件类型YB：患者医保号SF：身份证HZ：护照QT：其它
 * recipeTime	String	是	开具时间，yyyy-MM-dd HH:mm:ss
 * subject	String	否	第三方签名信息主题，第三方提供后期在列表页展示（0~50位）
 *
 * pathogeny	String	是	患者病因	流鼻涕、发烧、咳嗽
 * diagnose	String	是	医师诊断	感冒
 * recipeTerm	int	是	处方时效单位：天	14
 * recipeInfo	jsonArray	否	医师处方，定义处方药品条目列表
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class RecipeIn {

    private String patientAge;
    private String urId;
    private String subject;
    private String recipeTerm;
    private String recipeTime;
    private String diagnose;
    private String patientCard;
    private String patientCardType;
    private String patientName;
    private String pathogeny;
    private String openId;
    private String patientSex;
    private List<RecipeInfoIn> recipeInfo;
    public void setPatientAge(String patientAge) {
         this.patientAge = patientAge;
     }
     public String getPatientAge() {
         return patientAge;
     }

    public void setUrId(String urId) {
         this.urId = urId;
     }
     public String getUrId() {
         return urId;
     }

    public void setSubject(String subject) {
         this.subject = subject;
     }
     public String getSubject() {
         return subject;
     }

    public void setRecipeTerm(String recipeTerm) {
         this.recipeTerm = recipeTerm;
     }
     public String getRecipeTerm() {
         return recipeTerm;
     }

    public void setRecipeTime(String recipeTime) {
         this.recipeTime = recipeTime;
     }
     public String getRecipeTime() {
         return recipeTime;
     }

    public void setDiagnose(String diagnose) {
         this.diagnose = diagnose;
     }
     public String getDiagnose() {
         return diagnose;
     }

    public void setPatientCard(String patientCard) {
         this.patientCard = patientCard;
     }
     public String getPatientCard() {
         return patientCard;
     }

    public void setPatientCardType(String patientCardType) {
         this.patientCardType = patientCardType;
     }
     public String getPatientCardType() {
         return patientCardType;
     }

    public void setPatientName(String patientName) {
         this.patientName = patientName;
     }
     public String getPatientName() {
         return patientName;
     }

    public void setPathogeny(String pathogeny) {
         this.pathogeny = pathogeny;
     }
     public String getPathogeny() {
         return pathogeny;
     }

    public void setOpenId(String openId) {
         this.openId = openId;
     }
     public String getOpenId() {
         return openId;
     }

    public void setPatientSex(String patientSex) {
         this.patientSex = patientSex;
     }
     public String getPatientSex() {
         return patientSex;
     }

    public void setRecipeInfo(List<RecipeInfoIn> recipeInfo) {
         this.recipeInfo = recipeInfo;
     }
     public List<RecipeInfoIn> getRecipeInfo() {
         return recipeInfo;
     }

}