package model;

class Cell {
    private int content = 0;
    private boolean isOpened = false;
    private boolean isMarked = false;

    boolean containsBomb(){
        if(content == -1)
            return true;
        return false;
    }

    void setBomb(){
        content = -1;
    }

    void increaseCounter(){
        if(content != -1)
            content++;
    }

    boolean isOpened(){
        return isOpened;
    }
    boolean isMarked() {
        return isMarked;
    }

    int open(){
        if(isOpened)
            throw new RuntimeException("This cell's already been opened");
        isOpened = true;
        return content;
    }

    void mark(){
        if(isOpened)
            throw new RuntimeException("You try to mark the opened cell");
        isMarked = !isMarked;
    }
}
