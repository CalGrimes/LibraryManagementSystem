package com.calgrimes.librarymanagementsystem;
public enum ExitStatus
{
    SUCCESS  (0),
    WARNING  (4),
    ERROR    (8),
    CRITICAL (16);

    private final int value;

    ExitStatus(int i)
    {
        value = i;
    }

    public int get()
    {
        return value;
    }
}