package com.example.demo.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

public class SummaryDTO {

    private Long id;

    @PositiveOrZero
    private Double entertainment;

    @PositiveOrZero
    private Double food;

    @PositiveOrZero
    private Double gambling;

    @PositiveOrZero
    private Double income;

    @PositiveOrZero
    private Double others;

    @PositiveOrZero
    private Double shopping;

    @PositiveOrZero
    private Double travel;

    @PositiveOrZero
    private Double utilities;

    @PositiveOrZero
    private Double savings;

    @NotBlank(message = "Loan type must not be blank")
    private String loantype;

    @Min(0)
    private Integer totalActiveEMIs;

    @Min(0)
    private Integer totalMonthlyEMIAmount;

    @Min(0)
    private Integer regularEMIPayments;

    @Min(0)
    private Integer lateEMIPayments;

    @NotBlank(message = "EMI behavior must not be blank")
    private String overallEMIBehavior;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
}
