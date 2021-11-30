package src;

public interface Programmable {

    // Here we create abstract method in an interface.
    public abstract void setTimer(int seconds);

    public abstract void cancelTimer();

    public abstract void runProgram();
}
