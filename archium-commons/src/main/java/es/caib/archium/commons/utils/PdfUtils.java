package es.caib.archium.commons.utils;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.layout.element.Text;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.io.IOException;
import java.util.Collections;

@Named("pdfUtils")
@ApplicationScoped
public class PdfUtils {

    //private final Color COLOR_OFICIAL = new DeviceRgb(203, 2, 62);
    private final Color COLOR_OFICIAL = new DeviceRgb(0, 78, 150);

    private final Color COLOR_GREY = new DeviceRgb(150, 150, 150);

    private final Color COLOR_BLACK = new DeviceRgb(0, 0, 0);

    private final Color COLOR_WHITE = new DeviceRgb(255, 255, 255);

    //private final Color COLOR_PURPLE = new DeviceRgb(91, 39, 125);
    private final Color COLOR_BLUE = new DeviceRgb(0, 78, 150);

    private final Color COLOR_GREEN = new DeviceRgb(0, 128, 0);

    private final Color COLOR_RED = new DeviceRgb(128, 0, 0);


    public PdfFont getFont(String path)  {
        PdfFont pdfFont;
        try {
            pdfFont = PdfFontFactory.createFont(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return pdfFont;
    }

    public String tractarCampStr(Object dades) {
        String str;
        if (dades == null || dades.toString().length() == 0) {
            str = "";
        } else {
            str = dades.toString();
        }
        return str;
    }

    public String tractarCampStr(Object[] dadesTaula, int index, int numSaltsLinia) {
        String str;
        if (dadesTaula[index] == null || dadesTaula[index].toString().length() == 0) {
            str = String.join("", Collections.nCopies(numSaltsLinia, "\n"));
        } else {
            str = dadesTaula[index].toString();
        }
        return str;
    }

    public String tractarCampStr(Object[] dadesTaula, int index) {
        return tractarCampStr(dadesTaula, index, 0);
    }

    public Text tractarCamp(Object[] dadesTaula, int index) {
        return new Text(tractarCampStr(dadesTaula, index));
    }

    public Color getColorOficial() {
        return COLOR_OFICIAL;
    }

    public Color getColorBlack() {
        return COLOR_BLACK;
    }

    public Color getColorWhite() {
        return COLOR_WHITE;
    }

    public Color getColorBlue() {
        return COLOR_BLUE;
    }

    public Color getColorGreen() {
        return COLOR_GREEN;
    }

    public Color getColorRed() {
        return COLOR_RED;
    }

    public Color getColorGrey() {
        return COLOR_GREY;
    }
}
