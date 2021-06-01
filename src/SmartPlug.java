import java.util.Calendar;

// This class is a subclass of SmartObject class and instance of Programmable interface.
public class SmartPlug extends SmartObject implements Programmable {
    private boolean status;
    private Calendar programTime = Calendar.getInstance();
    private boolean programAction;

    public SmartPlug(String alias, String macId) {
        super.setAlias(alias);
        super.setMacId(macId);
    }

    // This method turns on the plug if it isn't turned on already.
    public void turnOn() {
        // Firstly we control the connection status of the any smart object in all objects' methods.
        if (super.isConnectionStatus()) {
            if (status) {
                System.out.println("Smart Plug - " + getAlias() + " has been already turned on");
            } else {
                Calendar currentTime = Calendar.getInstance();
                System.out.printf("Smart Plug - " + getAlias() + " is turned on now (Current time: %tT)%n", currentTime);
                setStatus(true);
            }
        }
    }

    // This method turns off the plug if it isn't turned off already.
    public void turnOff() {
        if (super.isConnectionStatus()) {
            if (!status) {
                System.out.println("Smart Plug - " + getAlias() + " has been already turned off");
            } else {
                Calendar currentTime = Calendar.getInstance();
                System.out.printf("Smart Plug - " + getAlias() + " is turned off now (Current time: %tT)%n", currentTime);
                setStatus(false);
            }
        }
    }


    // This override method test the object with turning on and turning off the plug.
    @Override
    public boolean testObject() {
        if (super.isConnectionStatus()) {
            System.out.println("Test is starting for SmartPlug");
            super.SmartObjectToString();
            turnOn();
            turnOff();
            System.out.println("Test completed for SmartPlug\n");
            return true;
        }
        return false;
    }

    // This method can shut down the object if we want.
    @Override
    public boolean shutDownObject() {
        if (super.isConnectionStatus()) {
            if (status) {
                super.SmartObjectToString();
                turnOff();
            }
        } else
            return false;
        return true;
    }

    // This method can sets a timer for turn uff or turn on the plug.
    @Override
    public void setTimer(int seconds) {
        if (super.isConnectionStatus()) {
            // Here we creates two Calendar object and adds the timer's second to programTime.
            Calendar currentTime = Calendar.getInstance();
            programTime = Calendar.getInstance();
            programTime.add(Calendar.SECOND, seconds);

            // This if statement checks the smart plug's status and prints what do it do when the program time come.
            if (status) {
                System.out.printf("Smart Plug - " + getAlias() + " will be turned off " + seconds + " seconds later! (Current time: %tT)%n", currentTime);
                setProgramAction(false);
            } else {
                System.out.printf("Smart Plug - " + getAlias() + " will be turned on " + seconds + " seconds later! (Current time: %tT)%n", currentTime);
                setProgramAction(true);
            }
        }
    }

    // With this method we can cancel the timer if we want.
    @Override
    public void cancelTimer() {
        if (super.isConnectionStatus())
            programTime = null;
    }

    // With this method we runs the program when the program time comes up.
    @Override
    public void runProgram() {
        if (super.isConnectionStatus()) {
            // Here we creates a currentTime object for compare with programTime.
            Calendar currentTime = Calendar.getInstance();
            try {
                // Whit this if statement we check what we have to do.
                if (isProgramAction()) {
                    // If currentTime and programTime is equal to each other than we can do the action.
                    if (currentTime.get(Calendar.SECOND) == programTime.get(Calendar.SECOND)
                            && currentTime.get(Calendar.MINUTE) == programTime.get(Calendar.MINUTE)
                            && currentTime.get(Calendar.HOUR_OF_DAY) == programTime.get(Calendar.HOUR_OF_DAY)) {
                        System.out.println("runProgram -> Smart Plug - " + getAlias());
                        turnOn();
                        programTime = null;
                    }
                } else {
                    // If currentTime and programTime is equal to each other than we can do the action.
                    if (currentTime.get(Calendar.SECOND) == programTime.get(Calendar.SECOND)
                            && currentTime.get(Calendar.MINUTE) == programTime.get(Calendar.MINUTE)
                            && currentTime.get(Calendar.HOUR_OF_DAY) == programTime.get(Calendar.HOUR_OF_DAY)) {
                        System.out.println("runProgram -> Smart Plug - " + getAlias());
                        turnOff();
                        programTime = null;
                    }
                }
            } catch (Exception ignored) {
            }
        }
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Calendar getProgramTime() {
        return programTime;
    }

    public void setProgramTime(Calendar programTime) {
        this.programTime = programTime;
    }

    public boolean isProgramAction() {
        return programAction;
    }

    public void setProgramAction(boolean programAction) {
        this.programAction = programAction;
    }
}
