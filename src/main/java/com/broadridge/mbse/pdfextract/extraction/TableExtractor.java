package com.broadridge.mbse.pdfextract.extraction;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import technology.tabula.Page;
import technology.tabula.Rectangle;
import technology.tabula.Table;
import technology.tabula.detectors.DetectionAlgorithm;
import technology.tabula.detectors.NurminenDetectionAlgorithm;
import technology.tabula.extractors.BasicExtractionAlgorithm;
import technology.tabula.extractors.SpreadsheetExtractionAlgorithm;


@Service
public class TableExtractor {
    private boolean guess = true;
    private boolean useLineReturns = false;
    private BasicExtractionAlgorithm basicExtractor = new BasicExtractionAlgorithm();
    private SpreadsheetExtractionAlgorithm spreadsheetExtractor = new SpreadsheetExtractionAlgorithm();

    private boolean verticalRulingPositionsRelative = false;
    private List<Float> verticalRulingPositions = null;

    private ExtractionMethod method = ExtractionMethod.BASIC;

    public TableExtractor() {
    }

    public void setVerticalRulingPositions(List<Float> positions) {
        this.verticalRulingPositions = positions;
    }

    public void setVerticalRulingPositionsRelative(boolean relative) {
        this.verticalRulingPositionsRelative = relative;
    }

    public void setGuess(boolean guess) {
        this.guess = guess;
    }

    public void setUseLineReturns(boolean useLineReturns) {
        this.useLineReturns = useLineReturns;
    }

    public void setMethod(ExtractionMethod method) {
        this.method = method;
    }

    public List<Table> extractTables(Page page) {
        ExtractionMethod effectiveMethod = this.method;
        if (effectiveMethod == ExtractionMethod.DECIDE) {
            effectiveMethod = spreadsheetExtractor.isTabular(page) ?
                    ExtractionMethod.SPREADSHEET :
                    ExtractionMethod.BASIC;
        }
        switch (effectiveMethod) {
            case BASIC:
                return extractTablesBasic(page);
            case SPREADSHEET:
                return extractTablesSpreadsheet(page);
            default:
                return new ArrayList<>();
        }
    }

    public List<Table> extractTablesBasic(Page page) {
        if (guess) {
            // guess the page areas to extract using a detection algorithm
            // currently we only have a detector that uses spreadsheets to find table areas
            DetectionAlgorithm detector = new NurminenDetectionAlgorithm();
            List<Rectangle> guesses = detector.detect(page);
            List<Table> tables = new ArrayList<>();

            for (Rectangle guessRect : guesses) {
                Page guess = page.getArea(guessRect);
                tables.addAll(basicExtractor.extract(guess));
            }
            return tables;
        }

        if (verticalRulingPositions != null) {
            List<Float> absoluteRulingPositions;

            if (this.verticalRulingPositionsRelative) {
                // convert relative to absolute
                absoluteRulingPositions = new ArrayList<>(verticalRulingPositions.size());
                for (float relative : this.verticalRulingPositions) {
                    float absolute = (float) (relative / 100.0 * page.getWidth());
                    absoluteRulingPositions.add(absolute);
                }
            } else {
                absoluteRulingPositions = this.verticalRulingPositions;
            }
            return basicExtractor.extract(page, absoluteRulingPositions);
        }

        return basicExtractor.extract(page);
    }

    public List<Table> extractTablesSpreadsheet(Page page) {
        return spreadsheetExtractor.extract(page);
    }
}
