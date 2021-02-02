import java.util.Objects;

class Perfomance {
  private int hotWater;
  private int coldWater;
  private int t1;
  private int t2;
  private int t3;
  
  Perfomance(int hotWater, int coldWater, int t1, int t2, int t3) {
    this.hotWater = hotWater;
    this.coldWater = coldWater;
    this.t1 = t1;
    this.t2 = t2;
    this.t3 = t3;
    System.out.println("Perfomance constructed");
  }

  public int getColdWater() {
    return coldWater;
  }

  public int getHotWater() {
    return hotWater;
  }

  public int getT1() {
    return t1;
  }

  public int getT2() {
    return t2;
  }

  public int getT3() {
    return t3;
  }

  @Override
  public String toString() {
    return "Perfomance{" +
      "hotWater=" + hotWater +
      ", coldWater=" + coldWater +
      ", t1=" + t1 +
      ", t2=" + t2 +
      ", t3=" + t3 +
      '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Perfomance that = (Perfomance) o;
    return hotWater == that.hotWater &&
      coldWater == that.coldWater &&
      t1 == that.t1 &&
      t2 == that.t2 &&
      t3 == that.t3;
  }

  @Override
  public int hashCode() {
    return Objects.hash(hotWater, coldWater, t1, t2, t3);
  }
}
