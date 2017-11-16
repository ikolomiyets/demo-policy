package io.iktech.demo;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Policy {
    private String policyNumber;
    private Date startDate;
    private List<Long> insured;

    public Policy(String policyNumber, Date startDate, Long[] insured) {
        this.policyNumber = policyNumber;
        this.startDate = startDate;
        this.insured = Arrays.asList(insured);
    }
    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public List<Long> getInsured() {
        return insured;
    }

    public void setInsured(List<Long> insured) {
        this.insured = insured;
    }
}
