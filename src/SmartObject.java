public abstract class SmartObject {
    private String alias;
    private String macId;
    private String IP;
    private boolean connectionStatus;

    public SmartObject() {
    }

    // This method connects the smart objects.
    public boolean connect(String IP) {
        this.IP = IP;
        System.out.println(this.alias + " connection established");
        return connectionStatus = true;
    }

    // This method disconnects the smart objects.
    public boolean disconnect() {
        this.IP = null;
        return connectionStatus = false;
    }

    // This method prints the objects main features.
    public void SmartObjectToString() {
        System.out.println("This is " + this.getClass().getSimpleName() + " device " + alias);
        System.out.println("\tMacId: " + macId + "\n\tIP: " + IP);
    }

    // This method controls the connection of the smart objects.
    public boolean controlConnection() {
        if (this.connectionStatus) {
            return true;
        } else {
            System.out.println("This device is not connected. " + this + " -> " + alias);
            return false;
        }
    }

    public abstract boolean testObject();

    public abstract boolean shutDownObject();

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getMacId() {
        return macId;
    }

    public void setMacId(String macId) {
        this.macId = macId;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public boolean isConnectionStatus() {
        return connectionStatus;
    }

    public void setConnectionStatus(boolean connectionStatus) {
        this.connectionStatus = connectionStatus;
    }
}
