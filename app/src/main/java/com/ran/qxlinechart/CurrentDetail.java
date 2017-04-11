package com.ran.qxlinechart;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CurrentDetail {
    public CurrentInfo baseInfo;
    public BigDecimal earningsPer10000; // 万份收益
    public Double maxDisplayInterestRate; // 最大可显示收益率
    public Double minDisplayInterestRate; // 最小可显示收益率
    public Integer intervalCount; // 收益率显示间隔
    public List<DayInterestRate> last7DaysInterestRates = new ArrayList<>(); // 近7日收益率列表
    public String investRulesURL; // 活期申购规则链接
    public String bannerUrl;//Banner URL
    public ProductInfo productInfo = new ProductInfo();

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (this.baseInfo == null ? 0 : this.baseInfo.hashCode());
        result = 31 * result + (this.bannerUrl == null ? 0: this.bannerUrl.hashCode());
        result = 31 * result + (this.earningsPer10000 == null ? 0: this.earningsPer10000.hashCode());
        result = 31 * result + (this.maxDisplayInterestRate == null ? 0: this.maxDisplayInterestRate.hashCode());
        result = 31 * result + (this.minDisplayInterestRate == null ? 0: this.minDisplayInterestRate.hashCode());
        result = 31 * result + (this.intervalCount == null ? 0: this.intervalCount.hashCode());
        result = 31 * result + (this.last7DaysInterestRates == null ? 0: this.last7DaysInterestRates.hashCode());
        result = 31 * result + (this.investRulesURL == null ? 0: this.investRulesURL.hashCode());
        result = 31 * result + (this.productInfo == null ? 0: this.productInfo.hashCode());
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

        CurrentDetail currentDetail = (CurrentDetail) obj;
        return this.baseInfo == null ? currentDetail.baseInfo == null : this.baseInfo.currentID == currentDetail.baseInfo.currentID;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class CurrentDetail {\n");

        sb.append("  baseInfo: ").append(baseInfo).append("\n");
        sb.append("  bannerUrl: ").append(bannerUrl).append("\n");
        sb.append("  earningsPer10000: ").append(earningsPer10000).append("\n");
        sb.append("  maxDisplayInterestRate: ").append(maxDisplayInterestRate).append("\n");
        sb.append("  minDisplayInterestRate: ").append(minDisplayInterestRate).append("\n");
        sb.append("  intervalCount: ").append(intervalCount).append("\n");
        sb.append("  last7DaysInterestRates: ").append(last7DaysInterestRates).append("\n");
        sb.append("  investRulesURL: ").append(investRulesURL).append("\n");
        sb.append("  productInfo: ").append(productInfo).append("\n");
        sb.append("}\n");

        return sb.toString();
    }

    public static class DayInterestRate {
        public Double interestRate; // 当日年化收益率
        public Long date; // 收益率日期

        @Override
        public int hashCode() {
            int result = 17;
            result = 31 * result + (this.interestRate == null ? 0: this.interestRate.hashCode());
            result = 31 * result + (this.date == null ? 0: this.date.hashCode());
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

            DayInterestRate dayInterestRate = (DayInterestRate) obj;
            return (this.interestRate == null ? dayInterestRate.interestRate == null : this.interestRate.equals(dayInterestRate.interestRate)) &&
                    (this.date == null ? dayInterestRate.date == null : this.date.equals(dayInterestRate.date));
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("class DayInterestRate {\n");

            sb.append("  interestRate: ").append(interestRate).append("\n");
            sb.append("  date: ").append(date).append("\n");
            sb.append("}\n");
            return sb.toString();
        }
    }

    public static class ProductInfo {
        public List<ProductItem> listItems = new ArrayList<>(); // 产品说明相关文档
        public String productManager; // 产品管理人

        @Override
        public int hashCode() {
            int result = 17;
            result = 31 * result + (this.productManager == null ? 0: this.productManager.hashCode());
            result = 31 * result + (this.listItems == null ? 0: this.listItems.hashCode());
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

            ProductInfo productInfo = (ProductInfo) obj;
            return (this.productManager == null ? productInfo.productManager == null : this.productManager.equals(productInfo.productManager)) &&
                    (this.listItems == null ? productInfo.listItems == null : this.listItems.equals(productInfo.listItems));
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("class ProductInfo {\n");

            sb.append("  productManager: ").append(productManager).append("\n");
            sb.append("  productItems: ").append(listItems).append("\n");
            sb.append("}\n");

            return sb.toString();
        }
    }

    public static class ProductItem {
        public String name;
        public String url;
        public Integer order;

        @Override
        public int hashCode() {
            int result = 17;
            result = 31 * result + (this.name == null ? 0: this.name.hashCode());
            result = 31 * result + (this.order == null ? 0: this.order.hashCode());
            result = 31 * result + (this.url == null ? 0: this.url.hashCode());
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

            ProductItem productItem = (ProductItem) obj;
            return (this.name == null ? productItem.name == null : this.name.equals(productItem.name)) &&
                    (this.order == null ? productItem.order == null : this.order.equals(productItem.order)) &&
                    (this.url == null ? productItem.url == null : this.url.equals(productItem.url));
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("class ProductItem {\n");

            sb.append("  name: ").append(name).append("\n");
            sb.append("  order: ").append(order).append("\n");
            sb.append("  url: ").append(url).append("\n");
            sb.append("}\n");

            return sb.toString();
        }
    }
}
