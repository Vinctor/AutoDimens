package com.vinctor.autodimens

import org.gradle.api.DefaultTask
import com.android.utils.FileUtils
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

public class AutoDimensTask extends DefaultTask {

    @Input
    File projectFile;
    @Input
    PropertyFinder finder

    int maxPx = 100;

    final String dimen_w_fileName = "dimen_w.xml"
    final String dimen_h_fileName = "dimen_h.xml"
    final String add_file_warning = "do-not-add-any-file.xml"

    @TaskAction
    void action() {
        ArrayList<Integer> standard = finder.getStandard()
        maxPx = finder.getMaxPx()
        ArrayList extraDimens = finder.getExtraDimens()
        boolean useDeviceSize = finder.getUseDeviceSize()//true包括虚拟键
        println((useDeviceSize ? "" : "不") + "包括虚拟键高度")
        printf "基准:w=%dpx,h=%dpx\r\n", standard

        def standard_w = standard[0]
        def standard_h = standard[1]


        def dimens = []
        [320, 480]
        [480, 800]
        [480, 854]
        [540, 888]
        [540, 960]
        [768, 1024]
        [768, 1280]
        [720, 1280]
        [720, 1184]
        [720, 1280]
        [800, 1280]
        [1080, 1776]
        [1080, 1812]
        [1080, 1920]
        [1440, 2560]


        extraDimens.each {
            dimen ->
                if (dimen[0] && dimen[1]) {
                    printf "新values:w=%d,h=%d\r\n", dimen
                    dimens.add([dimen[0], dimen[1]])
                }
        }

        float scale = 0.9f

        File routerFolder = FileUtils.join(projectFile, "src", "main", "res")
        if (routerFolder.exists()) {
            dimens.each { dimen ->
                int otherH = 0
                int dimenFactH = 0//实际高度
                if (!useDeviceSize) {//不包含虚拟键
                    dimenFactH = dimen[1] * scale
                    otherH = dimen[1]
                } else {//包含虚拟键
                    dimenFactH = dimen[1]
                    otherH = dimen[1] * scale
                }
                //delete other values
                deleteOther(routerFolder, dimen[0], otherH)

                File valuesFile = FileUtils.join(routerFolder, String.format("values-%dx%d", dimenFactH, dimen[0]));
                if (!valuesFile.exists()) valuesFile.mkdir();
                File dimen_w_File = FileUtils.join(valuesFile, dimen_w_fileName)
                File dimen_h_File = FileUtils.join(valuesFile, dimen_h_fileName)
                creatDimens(dimen_w_File)
                creatDimens(dimen_h_File)
                setDimens(dimen_w_File, dimen[0], standard_w, "w")//宽
                setDimens(dimen_h_File, dimenFactH, standard_h, "h")//高

                File warning = FileUtils.join(valuesFile, add_file_warning)
                setWaringContent(warning, dimen);
                println "DimenFile : " + valuesFile.getName() + " generate succeed!"
            }
        }
        File valuesDefaultFile = FileUtils.join(routerFolder, "values");
        if (valuesDefaultFile.exists()) {
            File dimen_w_File = FileUtils.join(valuesDefaultFile, dimen_w_fileName)
            File dimen_h_File = FileUtils.join(valuesDefaultFile, dimen_h_fileName)
            creatDimens(dimen_w_File)
            creatDimens(dimen_h_File)
            setDimens(dimen_w_File, standard_w, standard_w, "w")//宽
            setDimens(dimen_h_File, standard_h, standard_h, "h")//高
            println "DimenFile : " + valuesDefaultFile.getName() + " generate succeed!"
        }
    }

    def creatDimens(File file) {
        if (!file.exists())
            file.createNewFile()
    }
/**
 *
 * @param file
 * @param dimen 当前
 * @param standard 基准`
 * @param header 头
 * @return
 */
    def setDimens(File file, dimen, standard, header) {
        def warningMsg = "generate dimens,do not edit!(自动生成的dimen,请不要编辑)";
        //set
        StringBuilder builder = new StringBuilder();
        builder.append("<!--" + warningMsg + "-->\r\n");
        builder.append("<resources>\r\n");
        for (int i = 0; i <= maxPx; i++) {
            builder.append(getDimenXml(i, dimen, standard, header) + "\r\n")
        }
        builder.append("</resources>\r\n");
        builder.append("<!--" + warningMsg + "-->\r\n");

        String encoding = "UTF-8";
        OutputStreamWriter outstream = new OutputStreamWriter(new FileOutputStream(file.getAbsolutePath()), encoding);
        PrintWriter pw = new PrintWriter(outstream)
        pw.write(builder.toString())
        pw.flush()
        pw.close()
    }

    def getDimenXml(current, dimen, standard, header) {
        int temp = current * dimen;
        int result;
        if (temp % standard == 0) {
            result = temp / standard;
        } else {
            result = temp / standard + 1;
        }
        return String.format("<dimen name=\"%s%d\">%dpx</dimen>", header, current, result);
    }

    def setWaringContent(warning, dimen) {
        def warningMsg = dimen[1] + "x" + dimen[0] + "-不要在自动生成的文件夹(values - xxx) 内添加或修改任何文件 !否则, 误删后果自负!";
        //set
        StringBuilder builder = new StringBuilder();
        builder.append("<resources>\r\n");
        builder.append("<!--" + warningMsg + "-->\r\n");
        builder.append("</resources>\r\n");

        String encoding = "UTF-8";
        OutputStreamWriter outstream = new OutputStreamWriter(new FileOutputStream(warning.getAbsolutePath()), encoding);
        PrintWriter pw = new PrintWriter(outstream)
        pw.write(builder.toString())
        pw.flush()
        pw.close()
    }

    def deleteOther(routerFolder, width, height) {
        File valuesFile = FileUtils.join(routerFolder, String.format("values-%dx%d", height, width));

        File dimen_w_File = FileUtils.join(valuesFile, dimen_w_fileName)
        File dimen_h_File = FileUtils.join(valuesFile, dimen_h_fileName)
        File warinFile = FileUtils.join(valuesFile, add_file_warning)

        dimen_w_File.delete()
        dimen_h_File.delete()
        warinFile.delete()

        valuesFile.delete()
    }
}