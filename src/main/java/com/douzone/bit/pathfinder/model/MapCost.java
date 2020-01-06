package com.douzone.bit.pathfinder.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MapCost {
  private double payroll;
  private double distance;
  private double mileage;
  private double mileageCost;
  private double ton;
  private double tonRatio;
  private List<Integer> costList;

  private double resultCost;

  public void setPayroll(double payroll) {
    this.payroll = payroll;
  }

  public void setMileage(double mileage) {
    this.mileage = mileage;
    this.mileageCost = 1700 / mileage;
  }

  public void setTon(double ton) {
    this.ton = ton;
    this.tonRatio = ton / 6;
  }

  public double getResultCost(double distance, int index, int homeIndex) {
    resultCost = (distance * (payroll + mileageCost));
    resultCost += ((costList.get(index) + costList.get(homeIndex) * 0.4) * tonRatio); 
 
    return resultCost;
  }

  public double getResultDist(double distance, int index) {
    resultCost = (distance * (payroll + mileageCost));

    return resultCost;
  }
}
