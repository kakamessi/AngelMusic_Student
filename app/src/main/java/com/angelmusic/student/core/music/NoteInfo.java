package com.angelmusic.student.core.music;

/**
 * Created by DELL on 2017/2/16.
 */

public class NoteInfo {

    private int NoteNum;
    private int keyIndex;
    private int noteIndex;
    private boolean Red;

    public NoteInfo(int noteNum, int noteIndex,int keyIndex, boolean red) {
        NoteNum = noteNum;
        this.keyIndex = keyIndex;
        this.noteIndex = noteIndex;
        Red = red;
    }

    public int getNoteNum() {
        return NoteNum;
    }

    public void setNoteNum(int noteNum) {
        NoteNum = noteNum;
    }

    public int getKeyIndex() {
        return keyIndex;
    }

    public void setKeyIndex(int keyIndex) {
        this.keyIndex = keyIndex;
    }

    public int getNoteIndex() {
        return noteIndex;
    }

    public void setNoteIndex(int noteIndex) {
        this.noteIndex = noteIndex;
    }

    public boolean isRed() {
        return Red;
    }

    public void setRed(boolean red) {
        Red = red;
    }
}
