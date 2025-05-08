package com.example.demo.dto;

public class FilteredSummaryDataDTO {
    private Double entertainment;
    private Double food;
    private Double gambling;
    private Double income;
    private Double others;
    private Double shopping;
    private Double travel;
    private Double utilities;
    private Double savings;
    private Integer currCreditScore;

    private String loantype;
    private Integer totalActiveEMIs;
    private Integer totalMonthlyEMIAmount;
    private Integer regularEMIPayments;
    private Integer lateEMIPayments;
    private String overallEMIBehavior;

    // Default constructor
    public FilteredSummaryDataDTO() {}

    // Parameterized constructor
    public FilteredSummaryDataDTO(Double entertainment, Double food, Double gambling, Double income, Double others,
                                  Double shopping, Double travel, Double utilities, Double savings,
                                  String loantype, Integer totalActiveEMIs, Integer totalMonthlyEMIAmount,
                                  Integer regularEMIPayments, Integer lateEMIPayments, String overallEMIBehavior, Integer currCreditScore) {
        this.entertainment = entertainment;
        this.food = food;
        this.gambling = gambling;
        this.income = income;
        this.others = others;
        this.shopping = shopping;
        this.travel = travel;
        this.utilities = utilities;
        this.savings = savings;
        this.loantype = loantype;
        this.totalActiveEMIs = totalActiveEMIs;
        this.totalMonthlyEMIAmount = totalMonthlyEMIAmount;
        this.regularEMIPayments = regularEMIPayments;
        this.lateEMIPayments = lateEMIPayments;
        this.overallEMIBehavior = overallEMIBehavior;
        this.currCreditScore = currCreditScore;
    }

    // Getters and Setters
    public Double getEntertainment() {
        return entertainment;
    }

    public void setEntertainment(Double entertainment) {
        this.entertainment = entertainment;
    }

    public Double getFood() {
        return food;
    }

    public void setFood(Double food) {
        this.food = food;
    }

    public Double getGambling() {
        return gambling;
    }

    public void setGambling(Double gambling) {
        this.gambling = gambling;
    }

    public Double getIncome() {
        return income;
    }

    public void setIncome(Double income) {
        this.income = income;
    }

    public Double getOthers() {
        return others;
    }

    public void setOthers(Double others) {
        this.others = others;
    }

    public Double getShopping() {
        return shopping;
    }

    public void setShopping(Double shopping) {
        this.shopping = shopping;
    }

    public Double getTravel() {
        return travel;
    }

    public void setTravel(Double travel) {
        this.travel = travel;
    }

    public Double getUtilities() {
        return utilities;
    }

    public void setUtilities(Double utilities) {
        this.utilities = utilities;
    }

    public Double getSavings() {
        return savings;
    }

    public void setSavings(Double savings) {
        this.savings = savings;
    }

    public String getLoantype() {
        return loantype;
    }

    public void setLoantype(String loantype) {
        this.loantype = loantype;
    }

    public Integer getTotalActiveEMIs() {
        return totalActiveEMIs;
    }

    public void setTotalActiveEMIs(Integer totalActiveEMIs) {
        this.totalActiveEMIs = totalActiveEMIs;
    }

    public Integer getTotalMonthlyEMIAmount() {
        return totalMonthlyEMIAmount;
    }

    public void setTotalMonthlyEMIAmount(Integer totalMonthlyEMIAmount) {
        this.totalMonthlyEMIAmount = totalMonthlyEMIAmount;
    }

    public Integer getRegularEMIPayments() {
        return regularEMIPayments;
    }

    public void setRegularEMIPayments(Integer regularEMIPayments) {
        this.regularEMIPayments = regularEMIPayments;
    }

    public Integer getLateEMIPayments() {
        return lateEMIPayments;
    }

    public void setLateEMIPayments(Integer lateEMIPayments) {
        this.lateEMIPayments = lateEMIPayments;
    }

    public String getOverallEMIBehavior() {
        return overallEMIBehavior;
    }

    public void setOverallEMIBehavior(String overallEMIBehavior) {
        this.overallEMIBehavior = overallEMIBehavior;
    }

    public Integer getCurrCreditScore() {
        return currCreditScore;
    }

    public void setCurrCreditScore(Integer currCreditScore) {
        this.currCreditScore = currCreditScore;
    }
}
