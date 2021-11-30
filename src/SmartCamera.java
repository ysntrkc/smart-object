// This is a subclass of SmartObjects class and instance of MotionControl and Comparable interfaces.
package src;

public class SmartCamera extends SmartObject implements MotionControl, Comparable<SmartCamera> {
    private boolean status;
    private int batteryLife;
    private boolean nightVision;

    public SmartCamera(String alias, String macId, boolean nightVision, int batteryLife) {
        super.setAlias(alias);
        super.setMacId(macId);
        this.nightVision = nightVision;
        this.batteryLife = batteryLife;
    }

    // This method starts the recording according to isDay and nightVision.
    public void recordOn(boolean isDay) {
        // Firstly we control the connection status of the any smart object in all
        // objects' methods.
        if (super.isConnectionStatus()) {
            // This if condition check isDay. If isDay is false than another if statement
            // checks the night vision feature.
            if (isDay) {
                if (status) {
                    System.out.println("Smart Camera - " + getAlias() + " has been already turned on");
                } else {
                    System.out.println("Smart Camera - " + getAlias() + " is turned on now");
                    status = true;
                }
            } else {
                if (nightVision) {
                    if (status) {
                        System.out.println("Smart Camera - " + getAlias() + " has been already turned on");
                    } else {
                        System.out.println("Smart Camera - " + getAlias() + " is turned on now");
                        status = true;
                    }
                } else {
                    System.out.println("Sorry! Smart Camera - " + getAlias() + " does not have night vision feature.");
                }
            }
        }
    }

    // This method stops the recording.
    public void recordOff() {
        if (super.isConnectionStatus()) {
            if (!status) {
                System.out.println("Smart Camera - " + getAlias() + " has been already turned off");
            } else {
                System.out.println("Smart Camera - " + getAlias() + " is turned off now");
                status = false;
            }
        }
    }

    // This override method test the camera and print the messages.
    @Override
    public boolean testObject() {
        if (super.isConnectionStatus()) {
            System.out.println("Test is starting for SmartCamera");

            // Firstly, we call the superclass's SmartObjectToString method because of
            // printing the object's name and ip.
            super.SmartObjectToString();

            // Second, we run the camera at day time.
            System.out.println("Test is starting for SmartCamera day time");
            recordOn(true);
            recordOff();

            // Finally, we run the camera at night time.
            System.out.println("Test is starting for SmartCamera night time");
            recordOn(false);
            recordOff();
            System.out.println("Test completed for SmartCamera\n");
            return true;
        } else {
            return false;
        }
    }

    // This method can shut down the object if we want.
    @Override
    public boolean shutDownObject() {
        if (super.isConnectionStatus()) {
            if (status) {
                super.SmartObjectToString();
                recordOff();
            }
            return true;
        } else {
            return false;
        }
    }

    // This method calls from MotionControl interface and check the motion according
    // to isDay and nightVision.
    @Override
    public boolean controlMotion(boolean hasMotion, boolean isDay) {
        if (hasMotion) {
            if (isDay) {
                System.out.println("Motion detected!");
                recordOn(true);
            } else {
                if (nightVision) {
                    System.out.println("Motion detected!");
                    recordOn(false);
                } else {
                    System.out.println("Motion not detected!");
                }
            }
        } else {
            System.out.println("Motion not detected!");
        }
        return true;
    }

    // This method compares the battery life of cameras.
    @Override
    public int compareTo(SmartCamera smartCamera) {
        return Integer.compare(batteryLife, smartCamera.getBatteryLife());
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getBatteryLife() {
        return batteryLife;
    }

    public void setBatteryLife(int batteryLife) {
        this.batteryLife = batteryLife;
    }

    public boolean isNightVision() {
        return nightVision;
    }

    public void setNightVision(boolean nightVision) {
        this.nightVision = nightVision;
    }

    @Override
    public String toString() {
        return "Smart Camera -> " + getAlias() + "'s battery life is " + getBatteryLife() + " status is recording";
    }
}
