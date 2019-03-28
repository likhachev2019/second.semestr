package utils.tasks;

import utils.tests.TaskTest;

public abstract class HardGUIAbstractTask<T extends TaskTest> implements Task<T> {

    public abstract boolean getOutputDataInTableFormat();

    public abstract Object[][] getInputData();

    public abstract Object[][] getOutputData();

    public abstract Object [] getColumnNames();

    public abstract Object getExtraParameter();

    public abstract String getExtraReport();

    protected int getMaxRowLength(int[][] inputData) {
        int max = 0;
                                                                    // пока костыль
        for (int r = 0; r < inputData.length; r++) {                //  ЭТИ методы потом заменю чем-нибудь более сносным.

            int [] row = inputData[r];

            if (row != null && row.length > max)
                max = row.length;
        }

        return max;
    }

    public Object[] getMaxRowLengthArr(Object[][] inputData) {
        Object[] max = new Object[0];

        for (int r = 0; r < inputData.length; r++) {

            Object [] row = inputData[r];

            if (row != null && row.length > max.length)
                max = row;
        }

        for (int i = 0; i < max.length; i++) {
            max[i] = i;
        }

        return max;
    }

}
