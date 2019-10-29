package mtitech.co.il.mynotes;

public class Note
{
    public String noteBody;
    public long timestamp = System.currentTimeMillis();

    public Note(String note)
    {
        noteBody = note;
    }
}
