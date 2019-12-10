package com.capgemini.tdd.dao.enums;

public enum MoveValueEnum
{
    X("X"),
    O("O");

    public static final MoveValueEnum[] ALL = new MoveValueEnum[] { X, O };
    private final String value;

    MoveValueEnum(final String value)
    {
        this.value = value;
    }

    public static MoveValueEnum fromCode(final String value)
    {
        for (final MoveValueEnum item : ALL)
        {
            if (value.equals(item.getCode()))
            {
                return item;
            }
        }
        throw new IllegalArgumentException("Unknown MoveValueEnum code '" + value + '\'');
    }

    public String getCode()
    {
        return value;
    }
}
