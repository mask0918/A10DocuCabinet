package com.bst.pidms.entity.reader;

import com.ibm.icu.text.CharsetDetector;
import com.ibm.icu.text.CharsetMatch;
import com.ibm.icu.text.NumberFormat;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.hslf.usermodel.HSLFSlideShow;
import org.apache.poi.hslf.usermodel.HSLFTable;
import org.apache.poi.hslf.usermodel.HSLFTextShape;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.sl.usermodel.Shape;
import org.apache.poi.sl.usermodel.Slide;
import org.apache.poi.sl.usermodel.SlideShow;
import org.apache.poi.sl.usermodel.TableCell;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFTable;
import org.apache.poi.xslf.usermodel.XSLFTableCell;
import org.apache.poi.xslf.usermodel.XSLFTextShape;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.*;
import java.util.List;

/**
 * @Author: BST
 * @Date: 2019/4/13 14:01
 */
public class OfficeReader {

    public static String getInfo(String filePath, String suffix) throws Exception {
        switch (suffix) {
            case "xls":
                return ExcelReader(filePath);
            case "xlsx":
                return ExcelReader(filePath);
            case "doc":
                return wordReader(filePath);
            case "docx":
                return wordReader(filePath);
            case "ppt":
                return pptReader(filePath);
            case "ppxt":
                return pptReader(filePath);
            case "pdf":
                return pdfReader(filePath);
            default:
                return txtReader(filePath);
        }
    }

    public static String getEncode(String path) throws Exception {
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(path));
        CharsetDetector detector = new CharsetDetector();
        detector.setText(bis);
        bis.close();
        CharsetMatch match = detector.detect();
        return match.getName();
    }

    public static String pptReader(String path) throws Exception {
        File file = new File(path);
        InputStream is = null;
        SlideShow slideShow = null;
        StringBuffer sb = new StringBuffer();
        try {
            is = new FileInputStream(file);
            if (path.endsWith(".ppt")) {
                slideShow = new HSLFSlideShow(is);
            } else if (path.endsWith(".pptx")) {
                slideShow = new XMLSlideShow(is);
            }
            if (slideShow != null) {
                // 文本内容
                StringBuilder content = new StringBuilder();
                // 一页一页读取
                for (Slide slide : (List<Slide>) slideShow.getSlides()) {
                    List shapes = slide.getShapes();
                    if (shapes != null) {
                        for (int i = 0; i < shapes.size(); i++) {
                            org.apache.poi.sl.usermodel.Shape shape = (Shape) shapes.get(i);
                            if (shape instanceof HSLFTextShape) {// 文本框
                                String text = ((HSLFTextShape) shape).getText();
                                content.append(text);
                            }
                            if (shape instanceof XSLFTextShape) {// 文本框
                                String text = ((XSLFTextShape) shape).getText();
                                content.append(text);
                            }
                            if (shape instanceof HSLFTable) {// 表格
                                int rowSize = ((HSLFTable) shape).getNumberOfRows();
                                int columnSize = ((HSLFTable) shape).getNumberOfColumns();
                                for (int rowNum = 0; rowNum < rowSize; rowNum++) {
                                    for (int columnNum = 0; columnNum < columnSize; columnNum++) {
                                        TableCell cell = ((HSLFTable) shape).getCell(rowNum, columnNum);
                                        if (cell != null) {
                                            String text = cell.getText();
                                            content.append(text);
                                        }
                                    }
                                }
                            }
                            if (shape instanceof XSLFTable) {// 表格
                                int rowSize = ((XSLFTable) shape).getNumberOfRows();
                                int columnSize = ((XSLFTable) shape).getNumberOfColumns();
                                for (int rowNum = 0; rowNum < rowSize; rowNum++) {
                                    for (int columnNum = 0; columnNum < columnSize; columnNum++) {
                                        XSLFTableCell cell = ((XSLFTable) shape).getCell(rowNum, columnNum);
                                        if (cell != null) {
                                            String text = cell.getText();
                                            content.append(text);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (content.length() > 0) {
                        sb.append(content);
                        content.delete(0, content.length());
                    }
                }
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        } finally {
            try {
                if (slideShow != null) {
                    slideShow.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
            }
        }
        return sb.toString();
    }

    public static String wordReader(String path) throws Exception {
        InputStream is = new FileInputStream(path);
        if (path.endsWith(".doc")) {
            HWPFDocument doc = new HWPFDocument(is);
            WordExtractor wordExtractor = new WordExtractor(doc);
            String text = wordExtractor.getText();
            is.close();
            return text;
        } else if (path.endsWith(".docx")) {
            XWPFDocument doc = new XWPFDocument(is);
            XWPFWordExtractor extractor = new XWPFWordExtractor(doc);
            String text = extractor.getText();
            is.close();
            return text;
        }
        return null;
    }

    public static String ExcelReader(String filePath) throws IOException {
        FileInputStream fis = new FileInputStream(filePath);   //文件流对象
        StringBuffer sb = new StringBuffer();
        if (filePath.endsWith("xls")) {
            HSSFWorkbook workbook = new HSSFWorkbook(fis);
            for (int sheetIndex = 0; sheetIndex < workbook.getNumberOfSheets(); sheetIndex++) {
                HSSFSheet sheet = workbook.getSheetAt(sheetIndex);
                for (int rowIndex = 0; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                    HSSFRow row = sheet.getRow(rowIndex);
                    if (row == null) {
                        continue;
                    }
                    for (int cellnum = 0; cellnum < row.getLastCellNum(); cellnum++) {
                        HSSFCell cell = row.getCell(cellnum);
                        if (cell != null) {
                            cell.setCellType(CellType.STRING);
                            sb.append(cell.getRichStringCellValue().getString() + "\t");
                        }
                    }
                }
            }
            workbook.close();
            return sb.toString().trim();
        } else if (filePath.endsWith("xlsx")) {
            XSSFWorkbook workbook = new XSSFWorkbook(filePath);
            for (int sheet = 0; sheet < workbook.getNumberOfSheets(); sheet++) {
                if (null != workbook.getSheetAt(sheet)) {
                    XSSFSheet aSheet = workbook.getSheetAt(sheet);
                    for (int row = 0; row <= aSheet.getLastRowNum(); row++) {
                        if (null != aSheet.getRow(row)) {
                            XSSFRow aRow = aSheet.getRow(row);
                            for (int cell = 0; cell < aRow.getLastCellNum(); cell++) {
                                if (null != aRow.getCell(cell)) {
                                    XSSFCell aCell = aRow.getCell(cell);
                                    if (convertCell(aCell).length() > 0) {
                                        sb.append(convertCell(aCell));
                                    }
                                }
                                sb.append("\t");
                            }
                        }
                    }
                }
            }
            workbook.close();
            return sb.toString().trim();
        }
        return null;
    }

    private static String convertCell(Cell cell) {
        NumberFormat formater = NumberFormat.getInstance();
        formater.setGroupingUsed(false);
        String cellValue = "";
        if (cell == null) {
            return cellValue;
        }
        switch (cell.getCellType()) {
            case NUMERIC:
                cellValue = formater.format(cell.getNumericCellValue());
                break;
            case STRING:
                cellValue = cell.getStringCellValue();
                break;
            case BLANK:
                cellValue = cell.getStringCellValue();
                break;
            case BOOLEAN:
                cellValue = Boolean.valueOf(cell.getBooleanCellValue()).toString();
                break;
            case ERROR:
                cellValue = String.valueOf(cell.getErrorCellValue());
                break;
            default:
                cellValue = "";
        }
        return cellValue.trim();
    }


    public static String pdfReader(String filePath) throws IOException {
        PDDocument pd = PDDocument.load(new FileInputStream(filePath));
        PDFTextStripper stripper = new PDFTextStripper();
        return stripper.getText(pd);
    }

    public static String txtReader(String filePath) throws Exception {
        InputStreamReader reader = new InputStreamReader(new FileInputStream(filePath), getEncode(filePath));
        StringBuffer sb = new StringBuffer();
        BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
        String line;
        //网友推荐更加简洁的写法
        while ((line = br.readLine()) != null) sb.append(line + "\r\n");
        reader.close();
        br.close();
        return sb.toString();
    }
}
