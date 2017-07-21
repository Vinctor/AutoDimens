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

    @TaskAction
    void action() {
        int[] standard = finder.getStandard()
        maxPx = finder.getMaxPx()
        int[] extraDimens = finder.getExtraDimens()

        println extraDimens

        def standard_w = standard[0]
        def standard_h = standard[1]

        final String dimen_w_fileName = "dimen_w.xml"
        final String dimen_h_fileName = "dimen_h.xml"


        def dimens = []
        dimens.add([480, 800])
        dimens.add([480, 854])
        dimens.add([540, 960])
        dimens.add([768, 1024])
        dimens.add([720, 1184])
        dimens.add([720, 1280])
        dimens.add([800, 1280])
        dimens.add([1080, 1776])
        dimens.add([1080, 1812])
        dimens.add([1080, 1920])
        dimens.add([1440, 2560])
        extraDimens.each {
            dimen ->
                dimens.add(dimen[0],dimen[1])
        }


        File routerFolder = FileUtils.join(projectFile, "src", "main", "res")
        if (routerFolder.exists()) {
            dimens.each { dimen ->
                int dimenFactH = dimen[1] * 0.9
                File valuesFile = FileUtils.join(routerFolder, String.format("values-%dx%d", dimenFactH, dimen[0]));
                if (!valuesFile.exists()) valuesFile.mkdir();
                File dimen_w_File = FileUtils.join(valuesFile, dimen_w_fileName)
                File dimen_h_File = FileUtils.join(valuesFile, dimen_h_fileName)
                creatDimens(dimen_w_File)
                creatDimens(dimen_h_File)
                setDimens(dimen_w_File, dimen[0], standard_w, "w")//宽
                setDimens(dimen_h_File, dimenFactH, standard_h, "h")//高
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
        for (int i = 0; i < maxPx; i++) {
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
}