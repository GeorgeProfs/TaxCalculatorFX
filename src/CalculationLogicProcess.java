class CalculationLogicProcess implements CalculationLogic {

  private int calcWaterDifference(int oldPerfHotWater, int newPerfHotWater, int oldPerfColdWater, int newPerfColdWater, int divider) {
    double hotLitersDiff = (newPerfHotWater - oldPerfHotWater) / divider / 1000;
    double coldLitersDiff = (newPerfColdWater - oldPerfColdWater) / divider / 1000;
    return (int)(hotLitersDiff * TaxRates.HOTWATER + coldLitersDiff * TaxRates.COLDWATER + (hotLitersDiff+coldLitersDiff) * TaxRates.WATERDISPOSAL);
  }

  private int calcElectricityDifference(int oldPerfT1, int newPerfT1, int oldPerfT2, int newPerfT2, int oldPerfT3, int newPerfT3, int divider) {
    double t1Result = (newPerfT1 - oldPerfT1) / divider * TaxRates.T1;
    double t2Result= (newPerfT2 - oldPerfT2) / divider * TaxRates.T2;
    double t3Result= (newPerfT3 - oldPerfT3) / divider * TaxRates.T3;
    return (int)(t1Result + t2Result + t3Result);
  }

  public int calcResultOfMonth(Perfomance oldPerfomance, Perfomance newPerfomance, int divider) {
    int waterResult = calcWaterDifference(oldPerfomance.getHotWater(), newPerfomance.getHotWater(),oldPerfomance.getColdWater(), newPerfomance.getColdWater(), divider);
    int electricityResult = calcElectricityDifference(oldPerfomance.getT1(), newPerfomance.getT1(), oldPerfomance.getT2(), newPerfomance.getT2(), oldPerfomance.getT3(), newPerfomance.getT3(), divider);
    return waterResult + electricityResult;
  }

  private static class TaxRates {
    final static double HOTWATER = 198.19;
    final static double COLDWATER = 40.48;
    final static double T1 = 7.00;
    final static double T2 = 2.29;
    final static double T3 = 5.38;
    final static double WATERDISPOSAL = 59.14;
  }
}
