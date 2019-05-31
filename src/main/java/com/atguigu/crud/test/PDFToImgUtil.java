package com.atguigu.crud.test;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @Auther: Albert
 * @Date: 2018/8/17 20:01
 * @Description:
 */
public class PDFToImgUtil {
    /***
     * @param sourceFile	源文件
     * @param pageSignedNo	指定返回页的图片名称，具体使用可自行扩展
     * @param scale 		精度
     * @return
     */
    public static String  PDF2Image(String sourceFile,int pageSignedNo,float scale){
        PDDocument pdf = null;
        String rlt="";
        try {
            int pos=sourceFile.indexOf(".pdf");
            String pngFileName=sourceFile.substring(0,pos);

            File f=new File(sourceFile);
            if(!f.exists()){
                System.out.print("文件不存在！");
                return "";
            }
            pdf = PDDocument.load(f);
            PDFRenderer pdfRenderer = new PDFRenderer(pdf);
            PDPageTree pageTree = pdf.getPages();
            int pageCounter = 0;
            for(PDPage page : pageTree){
                float width = page.getCropBox().getWidth();
//           	float scale = 1.0f;
                if(width >720){
                    scale = 720/width;
                }
                BufferedImage bim = pdfRenderer.renderImage(pageCounter,scale, ImageType.RGB);

                String outputFileName=pngFileName+"_p"+ (pageCounter+1) +".jpg";
                ImageIOUtil.writeImage(bim, outputFileName , 300);
                if((pageCounter+1)==pageSignedNo){
                    rlt=outputFileName;
                }
                pageCounter++;
            }
            return rlt;
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                if(null!=pdf) pdf.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return rlt;
    }

    public static void main(String[] args) {
        Long time = System.currentTimeMillis();
        String file="d://pdfsrc/bbb.pdf";
        PDF2Image(file,1,2);
        Long time1 = System.currentTimeMillis();
        System.out.println("耗时："+(time1-time)/1000+"s");
    }
}
