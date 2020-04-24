package com.yang.util;



import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 计算利息工具类
 */
public class InterestUtils {

    /**
     *
     * @param bidRequestAmount 借款金额
     * @param returnType 还款类型
     * @param currentRate 利息
     * @param monthes2Return  期数
     * @return
     */
    public static BigDecimal totalInterest(BigDecimal bidRequestAmount,int returnType,BigDecimal currentRate, int monthes2Return ){

        //定义总利息为0；
       BigDecimal totalInterest=BidConst.ZERO;

        BigDecimal month=currentRate.divide(BigDecimal.valueOf(100),BidConst.scale_CACULATE,BigDecimal.ROUND_HALF_UP).divide(BigDecimal.valueOf(12),BidConst.scale_CACULATE,BigDecimal.ROUND_HALF_UP);
        if(returnType==0){
            //月利息
            BigDecimal temp1=bidRequestAmount.multiply(month);
            //总金额率
            BigDecimal temp2=BigDecimal.ONE.add(month).pow(monthes2Return);
            BigDecimal temp3=temp2.subtract(BigDecimal.ONE);
            //每月还款
            BigDecimal monthAmount=temp1.multiply(temp2).divide(temp3,BidConst.scale_CACULATE,BigDecimal.ROUND_HALF_UP);
            //总利息=每月本息*期数-本金
            totalInterest=monthAmount.multiply(BigDecimal.valueOf(monthes2Return)).subtract(bidRequestAmount);
        }else if(returnType==1){

            //每月本金=总金额/期数
            //第一个月利息=本金*月利率=每月本金*期数*月利率
            //最后一期=每月本金*月利率
            //总利息=（每月本金*期数*月利率）+（每月本金*月利率）*期数/2
            //===（本金*月利率）*（期数+1）/2
           totalInterest=bidRequestAmount.
                   multiply(month).
                   multiply(BigDecimal.valueOf(monthes2Return+1)).
                   divide(BigDecimal.valueOf(2));
        }else if(returnType==2){
            //本金*月利率
            BigDecimal multiply = bidRequestAmount.multiply(month);
            totalInterest= multiply.multiply(BigDecimal.valueOf(monthes2Return));

        }

       return totalInterest;

    }



    /**
     *
     * @param totalAmount 本金
     * @param yearRate 年化利率
     * @param month2Return 期数
     * @param returnType 还款方式
     * @param monthIndex 期号（第几期）
     * @return 该期的还款额度
     */
    public static BigDecimal caculateMonthAmount(
            BigDecimal totalAmount,
            BigDecimal yearRate,
            int month2Return,
            int returnType ,
            int monthIndex){

        //默认返回结果
        BigDecimal monthAmount = BigDecimal.ZERO;

        //月利率
        BigDecimal monthRate =  yearRate.divide(BigDecimal.valueOf(100)).divide(BigDecimal.valueOf(12), BidConst.scale_CACULATE,BigDecimal.ROUND_HALF_UP);

        if(returnType == BidConst.RETURN_TYPE_MONTH_INTERST){//按月到期
            if(monthIndex == month2Return){//最后一期
                monthAmount = totalAmount.multiply(monthRate).add(totalAmount).setScale(BidConst.scale_Store);
            }else{
                //不是最后一期，则等于月利息
                monthAmount = totalAmount.multiply(monthRate).setScale(BidConst.scale_Store);
            }

        }else if(returnType == BidConst.RETURN_TYPE_MONTH_PRINCIPAL){//等额本金
            //等额本金  该期的本金部分
            BigDecimal monthPrincipal = totalAmount.divide(BigDecimal.valueOf(month2Return),BidConst.scale_CACULATE, RoundingMode.HALF_UP);
            //等额本金  该期的利息部分
            BigDecimal monthInterest =  monthPrincipal.multiply(monthRate).multiply(  BigDecimal.valueOf(month2Return-monthIndex+1) );
            monthAmount = monthPrincipal.add(monthInterest).setScale(BidConst.scale_Store);
        }else if(returnType == BidConst.RETURN_TYPE_MONTH_INTERST_PRINCIPAL){//等额本息
            //每月还款额 =  月利息* 总金额率 / 总利率
            BigDecimal temp1 = totalAmount.multiply(monthRate);//月利息
            BigDecimal temp2 =  BigDecimal.ONE.add(monthRate).pow(month2Return);//总金额率
            BigDecimal temp3 = temp2.subtract(BigDecimal.ONE);

            monthAmount = temp1.multiply(temp2).divide(temp3, BidConst.scale_Store,BigDecimal.ROUND_HALF_UP);

        }
        return monthAmount;
    }


    /**
     *计算某一期的利息
     * @param totalAmount 本金
     * @param yearRate 年化利率
     * @param month2Return 期数
     * @param returnType 还款方式
     * @param monthIndex 期号（第几期）
     * @return 该期的利息
     */
    public static BigDecimal caculateMonthInterest(
            BigDecimal totalAmount,
            BigDecimal yearRate,
            int month2Return,
            int returnType ,
            int monthIndex){

        //默认返回结果
        BigDecimal monthInterest = BigDecimal.ZERO;

        //月利率
        BigDecimal monthRate =  yearRate.divide(BigDecimal.valueOf(100)).divide(BigDecimal.valueOf(12), BidConst.scale_CACULATE,BigDecimal.ROUND_HALF_UP);

        if(returnType == BidConst.RETURN_TYPE_MONTH_INTERST){//按月到期

            //不是最后一期，则等于月利息
            monthInterest = totalAmount.multiply(monthRate).setScale(BidConst.scale_Store);


        }else if(returnType == BidConst.RETURN_TYPE_MONTH_PRINCIPAL){//等额本金
            //等额本金  该期的本金部分
            BigDecimal monthPrincipal = totalAmount.divide(BigDecimal.valueOf(month2Return),BidConst.scale_CACULATE, RoundingMode.HALF_UP);
            //等额本金  该期的利息部分
            monthInterest =  monthPrincipal.multiply(monthRate).multiply(  BigDecimal.valueOf(month2Return-monthIndex+1) ).setScale(BidConst.scale_Store);
        }else if(returnType == BidConst.RETURN_TYPE_MONTH_INTERST_PRINCIPAL){//等额本息
            BigDecimal multiply = totalAmount.multiply(monthRate);
            BigDecimal pow = BigDecimal.ONE.add(monthRate).pow(month2Return);
            BigDecimal curent = BigDecimal.ONE.add(monthRate).pow(monthIndex -1);
            BigDecimal subtract1 = multiply.multiply(pow).subtract(curent);
            BigDecimal subtract = BigDecimal.ONE.add(monthRate).pow(month2Return - 1);
            monthInterest = subtract1.divide(subtract,BidConst.scale_Store,BigDecimal.ROUND_HALF_UP);

        }
        return monthInterest;
    }

















    public static void main(String[] args) {

        BigDecimal bigDecimal = totalInterest(BigDecimal.valueOf(20000), 0, BigDecimal.valueOf(5), 6);
        System.out.println(bigDecimal);
    }







}
