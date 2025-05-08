package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "summary_data")
public class SummaryData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

//    @Column(name = "emi_remarks", nullable = false)
//    private String emiRemarks;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    private Integer currCreditScore;

    public SummaryData() {
    }

    public SummaryData(Long id, Double entertainment, Double food, Double gambling, Double income, Double others,
                       Double shopping, Double travel, Double utilities, Double savings, String loantype,
                       Integer totalActiveEMIs, Integer totalMonthlyEMIAmount, Integer regularEMIPayments,
                       Integer lateEMIPayments, String overallEMIBehavior, String emiRemarks, Integer currCreditScore) {
        this.id = id;
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

//    public String getEmiRemarks() {
//        return emiRemarks;
//    }
//
//    public void setEmiRemarks(String emiRemarks) {
//        this.emiRemarks = emiRemarks;
//    }


    public Integer getCurrCreditScore() {
        return currCreditScore;
    }

    public void setCurrCreditScore(Integer currCreditScore) {
        this.currCreditScore = currCreditScore;
    }
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
