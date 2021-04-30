/**
  * Copyright 2020 bejson.com 
  */
package com.wjj.application.facade.ca.casdk.vo.in.recipesyn;

/**
 * 处方药品条目
 *
 * name	String	否	药品名称	阿莫西林
 * standard	String	否	药品规格	0.25
 * dosage	String	否	用量	0.5
 * unit	String	否	药品单位	盒
 * usage	String	否	用法	口服
 * frequency	String	否	频次	每日三次
 * days	String	否	天数	7天
 * quantity	String	否	数量	2
 * unitOf	String	否	剂量单位	g
 * price	String	否	药品单价	10
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class RecipeInfoIn {

    private String Quantity;
    private String standard;
    private String Price;
    private String name;
    private String Days;
    private String Dosage;
    private String UnitOf;
    private String Frequency;
    private String Unit;
    private String Usage;
    public void setQuantity(String Quantity) {
         this.Quantity = Quantity;
     }
     public String getQuantity() {
         return Quantity;
     }

    public void setStandard(String standard) {
         this.standard = standard;
     }
     public String getStandard() {
         return standard;
     }

    public void setPrice(String Price) {
         this.Price = Price;
     }
     public String getPrice() {
         return Price;
     }

    public void setName(String name) {
         this.name = name;
     }
     public String getName() {
         return name;
     }

    public void setDays(String Days) {
         this.Days = Days;
     }
     public String getDays() {
         return Days;
     }

    public void setDosage(String Dosage) {
         this.Dosage = Dosage;
     }
     public String getDosage() {
         return Dosage;
     }

    public void setUnitOf(String UnitOf) {
         this.UnitOf = UnitOf;
     }
     public String getUnitOf() {
         return UnitOf;
     }

    public void setFrequency(String Frequency) {
         this.Frequency = Frequency;
     }
     public String getFrequency() {
         return Frequency;
     }

    public void setUnit(String Unit) {
         this.Unit = Unit;
     }
     public String getUnit() {
         return Unit;
     }

    public void setUsage(String Usage) {
         this.Usage = Usage;
     }
     public String getUsage() {
         return Usage;
     }

}