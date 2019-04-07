package com.gameObjects;

public enum CmdType {

    // command type identifiers with a name and the parameter count
    STEP("step", 1),
    TURN("turn", 1),
    REPEAT("repeat", 1);

    // data fields
    public final String name;
    public final int paramCount;

    // private constructor used internally to create the enums and fill them with data
    CmdType(String name, int paramCount) {
        this.name = name;
        this.paramCount = paramCount;
    }

    /**
     * Get-function to get a type by name.
     * Returns null if there is no type associated with a string. This is handy because
     * you can easily check for null and safely assume there was either a typo or an unknown
     * command has been entered.
     */
    public static final CmdType get(String name) {
        for (CmdType cmdType : CmdType.values())
            if (cmdType.name.equals(name)) return cmdType;
        return null;
    }

    @Override
    public String toString() {
        return name + "(" + paramCount + ")";
    }
}