package com.espertech.esper.core;

public class StreamJoinAnalysisResult
{
    private final int numStreams;
    private int unidirectionalStreamNumber;
    private boolean[] isUnidirectionalInd;
    private boolean[] isUnidirectionalNonDriving;
    private boolean isPureSelfJoin;
    private boolean[] hasChildViews;
    private boolean[] isNamedWindow;

    public StreamJoinAnalysisResult(int numStreams)
    {
        unidirectionalStreamNumber = -1;
        this.numStreams = numStreams;
        isPureSelfJoin = false;
        isUnidirectionalInd = new boolean[numStreams];
        isUnidirectionalNonDriving = new boolean[numStreams];
        hasChildViews = new boolean[numStreams];
        isNamedWindow = new boolean[numStreams];
    }

    public boolean isUnidirectional()
    {
        return unidirectionalStreamNumber != -1;
    }

    public int getUnidirectionalStreamNumber()
    {
        return unidirectionalStreamNumber;
    }

    public void setUnidirectionalStreamNumber(int unidirectionalStreamNumber)
    {
        this.unidirectionalStreamNumber = unidirectionalStreamNumber;
    }

    public void setUnidirectionalInd(int index)
    {
        isUnidirectionalInd[index] = true;
    }

    public void setUnidirectionalNonDriving(int index)
    {
        isUnidirectionalNonDriving[index] = true;
    }

    public void setPureSelfJoin(boolean pureSelfJoin)
    {
        isPureSelfJoin = pureSelfJoin;
    }

    public void setHasChildViews(int index)
    {
        this.hasChildViews[index] = true;
    }

    public boolean[] getUnidirectionalInd()
    {
        return isUnidirectionalInd;
    }

    public boolean[] getUnidirectionalNonDriving()
    {
        return isUnidirectionalNonDriving;
    }

    public boolean isPureSelfJoin()
    {
        return isPureSelfJoin;
    }

    public boolean[] getHasChildViews()
    {
        return hasChildViews;
    }

    public boolean[] getNamedWindow()
    {
        return isNamedWindow;
    }

    public void setNamedWindow(int index)
    {
        isNamedWindow[index] = true;
    }

    public int getNumStreams()
    {
        return numStreams;
    }
}
