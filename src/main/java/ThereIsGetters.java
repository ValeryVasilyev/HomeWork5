public class ThereIsGetters {
    String name;
    int age;
    boolean isMarried;

    public String getName() {
        return this.name;
    }
    private int getAge() {
        return this.age;
    }
    protected boolean isMarried() {
        return this.isMarried;
    }
    public void otherMethod() {
        System.out.println("I am not a getter");
    }
}
