import java.util.ArrayList;
import java.util.Arrays;

public class SmartHome {
    private ArrayList<SmartObject> smartObjectList = new ArrayList<SmartObject>();
    private int ip = 100;

    public SmartHome() {
    }

    // With this method we add new objects and then print their testObject method which is abstract method in SmartObject class.
    public void addSmartObject(SmartObject smartObject) {
        System.out.println("--------------------------------------------------------------------------");
        System.out.println("--------------------------------------------------------------------------");
        System.out.println("Adding new SmartObject");
        System.out.println("--------------------------------------------------------------------------");
        smartObject.connect("10.0.0." + ip);
        smartObject.testObject();
        ip++;
        smartObjectList.add(smartObject);
    }

    // With this method we can remove objects.
    public boolean removeSmartObject(SmartObject smartObject) {
        return smartObjectList.remove(smartObject);
    }

    // With this method we test the location with LocationControl interface's onCome and onLeave methods.
    public void controlLocation(boolean onCome) {
        if (onCome) {
            System.out.println("--------------------------------------------------------------------------");
            System.out.println("--------------------------------------------------------------------------");
            System.out.println("LocationControl: OnCome");
            System.out.println("--------------------------------------------------------------------------");
        } else {
            System.out.println("--------------------------------------------------------------------------");
            System.out.println("--------------------------------------------------------------------------");
            System.out.println("LocationControl: OnLeave");
            System.out.println("--------------------------------------------------------------------------");
        }

        for (SmartObject smartObject : smartObjectList) {
            if (smartObject instanceof LocationControl) {
                if (onCome) {
                    ((LocationControl) smartObject).onCome();
                } else {
                    ((LocationControl) smartObject).onLeave();
                }
            }
        }
    }

    // With this method we test the motion with MotionControl interface's controlMotion method.
    public void controlMotion(boolean hasMotion, boolean isDay) {
        System.out.println("--------------------------------------------------------------------------");
        System.out.println("--------------------------------------------------------------------------");
        System.out.println("MotionControl: HasMotion, isDay");
        System.out.println("--------------------------------------------------------------------------");

        for (SmartObject smartObject : smartObjectList) {
            if (smartObject instanceof MotionControl) {
                ((MotionControl) smartObject).controlMotion(hasMotion, isDay);
            }
        }
    }

    // If an object is programmable, we test that with Programmable interface's runProgram method.
    public void controlProgrammable() {
        System.out.println("--------------------------------------------------------------------------");
        System.out.println("--------------------------------------------------------------------------");
        System.out.println("Programmable: runProgram");
        System.out.println("--------------------------------------------------------------------------");
        for (SmartObject smartObject : smartObjectList) {
            if (smartObject instanceof Programmable) {
                ((Programmable) smartObject).runProgram();
            }
        }
    }

    // With this method we can test the setTimer method with given second.
    public void controlTimer(int seconds) {
        System.out.println("--------------------------------------------------------------------------");
        System.out.println("--------------------------------------------------------------------------");
        System.out.println("Programmable: Timer = " + seconds);
        System.out.println("--------------------------------------------------------------------------");

        for (SmartObject smartObject : smartObjectList) {
            if (smartObject instanceof Programmable) {
                if (seconds > 0) {
                    ((Programmable) smartObject).setTimer(seconds);
                } else if (seconds == 0) {
                    ((Programmable) smartObject).cancelTimer();
                }
            }
        }
    }

    // With this method we can test the setTimer method with random second.
    public void controlTimerRandomly() {
        System.out.println("--------------------------------------------------------------------------");
        System.out.println("--------------------------------------------------------------------------");
        System.out.println("Programmable: Timer = 5 or 10 seconds randomly");
        System.out.println("--------------------------------------------------------------------------");

        for (SmartObject smartObject : smartObjectList) {
            int randomTime = (int) (Math.random() * 2 + 1);
            if (smartObject instanceof Programmable) {
                if (randomTime == 1 || randomTime == 2) {
                    ((Programmable) smartObject).setTimer(randomTime * 5);
                } else {
                    ((Programmable) smartObject).cancelTimer();
                }
            }
        }
    }

    // With this method we can sort the cameras by charge status.
    public void sortCameras() {
        int count = 0;
        System.out.println("--------------------------------------------------------------------------");
        System.out.println("--------------------------------------------------------------------------");
        System.out.println("Sort Smart Cameras");
        System.out.println("--------------------------------------------------------------------------");

        // This loop counts the comparable objects in smartObjectList.
        for (SmartObject object : smartObjectList) {
            if (object instanceof Comparable) {
                count++;
            }
        }

        // Here we create new array for comparable smart cameras.
        SmartCamera[] arr = new SmartCamera[count];
        count = 0;

        // With this loop we add comparable objects smartObjectList to arr.
        for (SmartObject smartObject : smartObjectList) {
            if (smartObject instanceof Comparable) {
                arr[count] = ((SmartCamera) smartObject);
                count++;
            }
        }

        // Here we sort the array by cameras' charge status.
        Arrays.sort(arr);
        for (SmartCamera smartCamera : arr) {
            System.out.println(smartCamera.toString());
        }
    }

    public ArrayList<SmartObject> getSmartObjectList() {
        return smartObjectList;
    }

    public void setSmartObjectList(ArrayList<SmartObject> smartObjectList) {
        this.smartObjectList = smartObjectList;
    }
}
