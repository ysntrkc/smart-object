package src;

import java.util.Calendar;

public class SmartLight extends SmartObject implements LocationControl, Programmable {
    private boolean hasLightTurned;
    private Calendar programTime;
    private boolean programAction;

    public SmartLight(String alias, String macId) {
        super.setAlias(alias);
        super.setMacId(macId);
    }

    // This method turns on the light if it isn't turned on already.
    public void turnOnLight() {
        // Firstly we control the connection status of the any smart object in all
        // objects' methods.
        if (super.isConnectionStatus()) {
            if (hasLightTurned) {
                System.out.println("Smart Light - " + getAlias() + " has been already turned on");
            } else {
                Calendar currentTime = Calendar.getInstance();
                System.out.printf("Smart Light - " + getAlias() + " is turned on now (Current time: %tT)%n",
                        currentTime);
                setHasLightTurned(true);
            }
        }
    }

    // This method turns off the light if it isn't turned off already.
    public void turnOffLight() {
        if (super.isConnectionStatus()) {
            if (!hasLightTurned) {
                System.out.println("Smart Light - " + getAlias() + " has been already turned off");
            } else {
                Calendar currentTime = Calendar.getInstance();
                System.out.printf("Smart Light - " + getAlias() + " is turned off now (Current time: %tT)%n",
                        currentTime);
                setHasLightTurned(false);
            }
        }
    }

    // This override method test the object with turning on and turning off the
    // light.
    @Override
    public boolean testObject() {
        if (super.isConnectionStatus()) {
            System.out.println("Test is starting for SmartLight");
            super.SmartObjectToString();
            turnOnLight();
            turnOffLight();
            System.out.println("Test completed for SmartLight\n");
            return true;
        } else {
            return false;
        }
    }

    // This method can shut down the object if we want.
    @Override
    public boolean shutDownObject() {
        if (super.isConnectionStatus()) {
            if (hasLightTurned) {
                super.SmartObjectToString();
                turnOffLight();
            }
        } else {
            return false;
        }
        return true;
    }

    // This method turns off the light when we leave from the room.
    @Override
    public void onLeave() {
        if (super.isConnectionStatus()) {
            System.out.println("On Leave -> Smart Light - " + getAlias());
            turnOffLight();
        }
    }

    // This method turns on the light when we come to the room.
    @Override
    public void onCome() {
        if (super.isConnectionStatus()) {
            System.out.println("On Come -> Smart Light - " + getAlias());
            turnOnLight();
        }
    }

    // This method can sets a timer for turn uff or turn on the light.
    @Override
    public void setTimer(int seconds) {
        if (super.isConnectionStatus()) {
            // Here we creates two Calendar object and adds the timer's second to
            // programTime.
            Calendar currentTime = Calendar.getInstance();
            programTime = Calendar.getInstance();
            programTime.add(Calendar.SECOND, seconds);

            // This if statement checks the smart light's status and prints what do it do
            // when the program time come.
            if (hasLightTurned) {
                System.out.printf("Smart Light - " + getAlias() + " will be turned off " + seconds
                        + " seconds later! (Current time: %tT)%n", currentTime);
                setProgramAction(false);
            } else {
                System.out.printf("Smart Light - " + getAlias() + " will be turned on " + seconds
                        + " seconds later! (Current time: %tT)%n", currentTime);
                setProgramAction(true);
            }
        }
    }

    // With this method we can cancel the timer if we want.
    @Override
    public void cancelTimer() {
        if (super.isConnectionStatus()) {
            programTime = null;
        }
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
                    // If currentTime and programTime is equal to each other than we can do the
                    // action.
                    if (currentTime.get(Calendar.SECOND) == programTime.get(Calendar.SECOND)
                            && currentTime.get(Calendar.MINUTE) == programTime.get(Calendar.MINUTE)
                            && currentTime.get(Calendar.HOUR_OF_DAY) == programTime.get(Calendar.HOUR_OF_DAY)) {
                        System.out.println("runProgram -> Smart Light - " + getAlias());
                        turnOnLight();
                        programTime = null;
                    }
                } else {
                    // If currentTime and programTime is equal to each other than we can do the
                    // action.
                    if (currentTime.get(Calendar.SECOND) == programTime.get(Calendar.SECOND)
                            && currentTime.get(Calendar.MINUTE) == programTime.get(Calendar.MINUTE)
                            && currentTime.get(Calendar.HOUR_OF_DAY) == programTime.get(Calendar.HOUR_OF_DAY)) {
                        System.out.println("runProgram -> Smart Light - " + getAlias());
                        turnOffLight();
                        programTime = null;
                    }
                }
            } catch (Exception ignored) {
            }
        }
    }

    public boolean isHasLightTurned() {
        return hasLightTurned;
    }

    public void setHasLightTurned(boolean hasLightTurned) {
        this.hasLightTurned = hasLightTurned;
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
