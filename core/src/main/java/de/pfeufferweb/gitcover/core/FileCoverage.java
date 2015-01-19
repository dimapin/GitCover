package de.pfeufferweb.gitcover.core;

import java.util.List;
import java.util.Map;

public class FileCoverage
{
    public int getChangesLines() {
        return changesLines;
    }

    public int getRelevantLines() {
        return relevantLines;
    }

    public int getCoveredLines() {
        return coveredLines;
    }

    final int changesLines;
    final int relevantLines;
    final int coveredLines;

    private FileCoverage(int changesLines, int relevantLines, int coveredLines)
    {
        this.changesLines = changesLines;
        this.relevantLines = relevantLines;
        this.coveredLines = coveredLines;
    }

    public static FileCoverage buildFrom(Map<Integer, Integer> lineCoverage, List<Integer> lines)
    {
        int changedLines = 0;
        int coveredLines = 0;
        int relevantLines = 0;
        for (int line : lines)
        {
            ++changedLines;
            if (lineCoverage.containsKey(line))
            {
                ++relevantLines;
                if (lineCoverage.get(line) > 0)
                {
                    ++coveredLines;
                }
            }
        }
        return new FileCoverage(changedLines, relevantLines, coveredLines);
    }

    boolean completelyCovered()
    {
        return changesLines == coveredLines;
    }

    @Override
    public String toString()
    {
        return changesLines + " Zeile" + (changesLines == 1 ? "" : "n") + " ge�ndert, " + relevantLines + " Zeile"
                + (relevantLines == 1 ? "" : "n") + " testrelevant" + ", Testabdeckung: " + getCoverage() + "%";
    }

    public int getCoverage()
    {
        return relevantLines == 0 ? 100 : (100 * coveredLines / relevantLines);
    }
}
