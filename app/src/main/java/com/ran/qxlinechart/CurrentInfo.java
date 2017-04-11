package com.ran.qxlinechart;



import java.math.BigDecimal;

public class CurrentInfo extends LoanType {
    public Integer currentID; // 活期ID
    public String title; // 活期标题
    public Double interestRate; // 活期利率
    public String interestRateDescription; // 活期利率描述
    public BigDecimal startAmount = new BigDecimal("0"); // 起投金额
    public BigDecimal increaseAmount = new BigDecimal("0"); // 递增金额
    public String termUnit; // 期单位
    public Integer status; // 活期标的状态
    public Integer supportedRedEnvelopeTypes; // 支持的红包类型

    public CurrentInfo() {
        super(Type.TYPE_CURRENT);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (this.currentID == null ? 0: this.currentID.hashCode());
        result = 31 * result + (this.title == null ? 0: this.title.hashCode());
        result = 31 * result + (this.interestRate == null ? 0: this.interestRate.hashCode());
        result = 31 * result + (this.interestRateDescription == null ? 0: this.interestRateDescription.hashCode());
        result = 31 * result + (this.startAmount == null ? 0: this.startAmount.hashCode());
        result = 31 * result + (this.increaseAmount == null ? 0: this.increaseAmount.hashCode());
        result = 31 * result + (this.termUnit == null ? 0: this.termUnit.hashCode());
        result = 31 * result + (this.status == null ? 0: this.status.hashCode());
        result = 31 * result + (this.supportedRedEnvelopeTypes == null ? 0: this.supportedRedEnvelopeTypes.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        CurrentInfo currentInfo = (CurrentInfo) obj;
        return (this.currentID == null ? currentInfo.currentID == null : this.currentID.equals(currentInfo.currentID));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class CurrentInfo {\n");

        sb.append("  currentId: ").append(currentID).append("\n");
        sb.append("  title: ").append(title).append("\n");
        sb.append("  interestRate: ").append(interestRate).append("\n");
        sb.append("  interestRateDescription: ").append(interestRateDescription).append("\n");
        sb.append("  startAmount: ").append(startAmount).append("\n");
        sb.append("  increaseAmount: ").append(increaseAmount).append("\n");
        sb.append("  termUnit: ").append(termUnit).append("\n");
        sb.append("  status: ").append(status).append("\n");
        sb.append("  supportedRedbagTypes: ").append(supportedRedEnvelopeTypes).append("\n");
        sb.append("}\n");

        return sb.toString();
    }

    public static class Status {
        public static final Integer STATUS_SALING = 1;  // 立即买入
        public static final Integer STATUS_SOLD_OUT= 2; // 已售罄
    }
}
