package com.bst.pidms.utils;

import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * @Author: BST
 * @Date: 2019/5/2 15:40
 */
public class PdfUtils {

    /**
     * 合并pdf
     *
     * @throws Exception
     */
    public static void mergePdf(String[] filesInFolder, String des) throws Exception {
        PDFMergerUtility mergePdf = new PDFMergerUtility();
        for (String s : filesInFolder) {
            mergePdf.addSource(s);
        }
        mergePdf.setDestinationFileName(des);
        mergePdf.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());
    }

    /**
     * 切割pdf
     *
     * @param pageIndexs
     * @param filePath
     * @param outPath
     * @return
     */
    public static String splitPdf(Integer[] pageIndexs, String filePath, String outPath) {
        File indexFile = new File(filePath);// 这是对应文件名
        PDDocument document = null;
        try {
            document = PDDocument.load(indexFile);
            PDDocument result = new PDDocument();
            PDPageTree pages = document.getPages();
            for (int index : pageIndexs) {
                result.addPage(pages.get(index));
            }
            result.save(outPath);
            result.close();
        } catch (IOException e) {
            e.getMessage();
        }
        return null;
    }

    public static void pdf2Png(String filepath, String des) throws Exception {
        PDDocument document = new PDDocument();
        File pdfFile = new File(filepath);
        document = PDDocument.load(pdfFile, (String) null);
        int size = document.getNumberOfPages();
        List<BufferedImage> picList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            BufferedImage image = new PDFRenderer(document).renderImageWithDPI(i, 296, ImageType.RGB);
            picList.add(image);
        }
        document.close();
        yPic(picList, des);
    }

    public static void yPic(List<BufferedImage> picList, String outPath) {// 纵向处理图片
        if (picList == null || picList.size() <= 0) {
            System.out.println("图片数组为空!");
            return;
        }
        try {
            int height = 0, // 总高度
                    width = 0, // 总宽度
                    _height = 0, // 临时的高度 , 或保存偏移高度
                    __height = 0, // 临时的高度，主要保存每个高度
                    picNum = picList.size();// 图片的数量
            File fileImg = null; // 保存读取出的图片
            int[] heightArray = new int[picNum]; // 保存每个文件的高度
            BufferedImage buffer = null; // 保存图片流
            List<int[]> imgRGB = new ArrayList<int[]>(); // 保存所有的图片的RGB
            int[] _imgRGB; // 保存一张图片中的RGB数据
            for (int i = 0; i < picNum; i++) {
                buffer = picList.get(i);
                heightArray[i] = _height = buffer.getHeight();// 图片高度
                if (i == 0) {
                    width = buffer.getWidth();// 图片宽度
                }
                height += _height; // 获取总高度
                _imgRGB = new int[width * _height];// 从图片中读取RGB
                _imgRGB = buffer
                        .getRGB(0, 0, width, _height, _imgRGB, 0, width);
                imgRGB.add(_imgRGB);
            }
            _height = 0; // 设置偏移高度为0
            // 生成新图片
            BufferedImage imageResult = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_BGR);
            for (int i = 0; i < picNum; i++) {
                __height = heightArray[i];
                if (i != 0)
                    _height += __height; // 计算偏移高度
                imageResult.setRGB(0, _height, width, __height, imgRGB.get(i),
                        0, width); // 写入流中
            }
            File outFile = new File(outPath);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ImageIO.write(imageResult, "jpg", out);// 写图片
            byte[] b = out.toByteArray();
            FileOutputStream output = new FileOutputStream(outFile);
            output.write(b);
            out.close();
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
