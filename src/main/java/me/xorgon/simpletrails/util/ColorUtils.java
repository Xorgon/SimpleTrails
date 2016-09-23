package me.xorgon.simpletrails.util;


/**
 * Class containing methods for RGB and HSL conversions.
 */
public class ColorUtils {

    /**
     * Method that takes an rgb value and returns an array containing RGB integer values.
     * The first RGB integer is the original rgb.
     * The second RGB integer is the 'contrasting' color.
     *
     * @param r red value
     * @param g green value
     * @param b blue value
     * @return Array of RGB integer values.
     */
    public static int[] contrastRGB(int r, int g, int b) {
        double[] oHSL = RGBtoHSL(r, g, b);
        double[] nHSL = new double[3];
        int[] contrastRGB = new int[2];

        nHSL[0] = oHSL[0] + 0.5;
        if (nHSL[0] > 1) {
            nHSL[0] -= 1;
        }

        nHSL[1] = oHSL[1];
        nHSL[2] = 1.0 - oHSL[2];

        int[] nRGB;

        nRGB = HSLtoRGB(nHSL[0], nHSL[1], nHSL[2]);

        contrastRGB[0] = RGBtoInt(r, g, b);
        contrastRGB[1] = RGBtoInt(nRGB[0], nRGB[1], nRGB[2]);

        return contrastRGB;
    }

    /**
     * Method that takes an rgb value and returns an array containing RGB arrays.
     * The first RGB array is the original rgb.
     * The second RGB array is the 'contrasting' color.
     *
     * @param r red value
     * @param g greed value
     * @param b blue value
     * @param t boolean, return RGB arrays rather than RGB integer values.
     * @return Array of RGB arrays in the format {r,g,b}
     */
    public static int[][] contrastRGB(int r, int g, int b, boolean t) {
        double[] oHSL = RGBtoHSL(r, g, b);
        double[] nHSL = new double[3];
        int[][] contrastRGB = new int[2][3];

        nHSL[0] = oHSL[0] + 0.5;
        if (nHSL[0] > 1) {
            nHSL[0] -= 1;
        }

        nHSL[1] = oHSL[1];
        nHSL[2] = 1.0 - oHSL[2];

        int[] nRGB;

        nRGB = HSLtoRGB(nHSL[0], nHSL[1], nHSL[2]);

        contrastRGB[0] = new int[]{r, g, b};
        contrastRGB[1] = nRGB;

        return contrastRGB;
    }

    /**
     * Converts RGB to HSL.
     * HSL values are in decimal form: <br>
     * H: 360 = 1, <br>
     * S: 100 = 1, <br>
     * L: 100 = 1. <br>
     * Thanks to http://serennu.com/colour/rgbtohsl.php for help on this calculation.
     *
     * @param r red value
     * @param g green value
     * @param b blue value
     * @return Array containing HSL values in the format {h,s,l}
     */
    public static double[] RGBtoHSL(int r, int g, int b) {
        double h = 0;
        double s = 0;
        double l = 0;


        double varR = r / 255.0;
        double varG = g / 255.0;
        double varB = b / 255.0;

        double varMin = Math.min(varR, Math.min(varG, varB));
        double varMax = Math.max(varR, Math.max(varG, varB));
        double delMax = varMax - varMin;

        l = (varMin + varMax) / 2;
        if (delMax == 0) {
            h = 0;
            s = 0;
        } else {
            if (l < 0.5) {
                s = delMax / (varMax + varMin);
            } else {
                s = delMax / (2 - varMax - varMin);
            }

            double delR = (((varMax - varR) / 6.0) + (delMax / 2.0)) / delMax;
            double delG = (((varMax - varG) / 6.0) + (delMax / 2.0)) / delMax;
            double delB = (((varMax - varB) / 6.0) + (delMax / 2.0)) / delMax;

            if (varR == varMax) {
                h = delB - delG;
            } else if (varG == varMax) {
                h = (1.0 / 3.0) + delR + delB;
            } else if (varB == varMax) {
                h = (2.0 / 3.0) + delG - delR;
            }

            if (h < 0) {
                h += 1;
            }

            if (h > 1) {
                h -= 1;
            }
        }

        double[] hsl = new double[3];
        hsl[0] = h;
        hsl[1] = s;
        hsl[2] = l;
        return hsl;
    }

    /**
     * Converts HSL to RGB.
     * HSL values are in decimal form: <br>
     * H: 360 = 1, <br>
     * S: 100 = 1, <br>
     * L: 100 = 1. <br>
     * Thanks to http://serennu.com/colour/rgbtohsl.php for help on this calculation.
     *
     * @param h red value
     * @param s green value
     * @param l blue value
     * @return Array containing RGB values in the format {r,g,b}
     */
    public static int[] HSLtoRGB(double h, double s, double l) {
        int r;
        int g;
        int b;

        double var1;
        double var2;

        if (s == 0) {
            r = (int) (l * 255);
            g = (int) (l * 255);
            b = (int) (l * 255);
        } else {
            if (l < 0.5) {
                var2 = l * (1 + s);
            } else {
                var2 = (l + s) - (s * l);
            }

            var1 = 2 * l - var2;
            r = (int) (255 * HueToRGB(var1, var2, h + (1.0 / 3.0)));
            g = (int) (255 * HueToRGB(var1, var2, h));
            b = (int) (255 * HueToRGB(var1, var2, h - (1.0 / 3.0)));
        }

        int[] rgb = new int[3];
        rgb[0] = r;
        rgb[1] = g;
        rgb[2] = b;
        return rgb;
    }

    //Only used in HSLtoRGB().
    public static double HueToRGB(double v1, double v2, double vh) {
        if (vh < 0) {
            vh += 1;
        }
        if (vh > 1) {
            vh -= 1;
        }
        if ((6 * vh) < 1) {
            return (v1 + (v2 - v1) * 6 * vh);
        }
        if ((2 * vh) < 1) {
            return (v2);
        }
        if ((3 * vh) < 2) {
            return (v1 + (v2 - v1) * ((2.0 / 3.0 - vh) * 6));
        }
        return v1;
    }

    /**
     * Converts RGB values to an RGB integer value.
     *
     * @param r red value
     * @param g green value
     * @param b blue value
     * @return RGB integer
     */
    public static int RGBtoInt(int r, int g, int b) {
        return (256 * 256 * r + 256 * g + b);
    }

    /**
     * Converts an RGB integer value into an array of RGB values.
     *
     * @param rgbInt RGB integer value
     * @return Array containing RGB values in the format {r,g,b}
     */
    public static int[] IntToRGB(int rgbInt) {
        int r = (int) (rgbInt / 256.0 / 256.0 % 256);
        int g = (int) (rgbInt / 256.0 % 256);
        int b = (int) (rgbInt % 256);
        return new int[]{r, g, b};
    }
}