package it.ohalee.minecraftgpt;

public enum Type {

    SINGLE,
    BROADCAST,
    FULL;

    public static Type getType(String type) {
        switch (type.toLowerCase()) {
            case "single":
                return Type.SINGLE;
            case "broadcast":
                return Type.BROADCAST;
            case "full":
                return Type.FULL;
            default:
                return null;
        }
    }

}
