package de.thexxturboxx.fontfixer;

import cn.xylose.btw.bettergamesetting.config.BGSConfig;
import net.minecraft.src.*;
import org.lwjgl.opengl.GL11;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.Bidi;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import static org.lwjgl.opengl.GL11.glColor4f;

public class FontFixer {
    private static final ResourceLocation[] unicodePageLocations = new ResourceLocation[256];
    public static String FIXER_VERSION = "1";
    private int[] charWidth = new int[Short.MAX_VALUE];
    public int fontTextureName = 0;
    public int FONT_HEIGHT = 9;
    public Random fontRandom = new Random();
    private byte[] glyphWidth = new byte[65536];
    private final int[] glyphTextureName = new int[256];
    public int[] colorCode = new int[32];
    private int boundTextureName;
    private final TextureManager renderEngine;
    private float posX;
    private float posY;
    private boolean unicodeFlag;
    private boolean bidiFlag;
    private float red;
    private float blue;
    private float green;
    private float alpha;
    private int textColor;
    private boolean randomStyle = false;
    private boolean boldStyle = false;
    private boolean italicStyle = false;
    private boolean underlineStyle = false;
    private boolean strikethroughStyle = false;
    private final ResourceLocation locationFontTexture;

    public final String ASCII = "ÀÁÂÈÊËÍÓÔÕÚßãõğİıŒœŞşŴŵžȇ\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000ÇüéâäàåçêëèïîìÄÅÉæÆôöòûùÿÖÜø£Ø×ƒáíóúñÑªº¿®¬½¼¡«»░▒▓│┤╡╢╖╕╣║╗╝╜╛┐└┴┬├─┼╞╟╚╔╩╦╠═╬╧╨╤╥╙╘╒╓╫╪┘┌█▄▌▐▀αβΓπΣσμτΦΘΩδ∞∅∈∩≡±≥≤⌠⌡÷≈°∙·√ⁿ²■\u0000";


//    public FontFixer() {
//        this.renderEngine = null;
//    }

    public FontFixer(GameSettings gameSettings, ResourceLocation resourceLocation, TextureManager renderEngine, boolean unicodeFlag) {
        this.locationFontTexture = resourceLocation;
        this.renderEngine = renderEngine;
        this.unicodeFlag = unicodeFlag;
        renderEngine.bindTexture(this.locationFontTexture);
        this.readGlyphSizes();

        BufferedImage bufferedImage;
        try {
            bufferedImage = ImageIO.read(Minecraft.getMinecraft().getResourceManager().getResource(this.locationFontTexture).getInputStream());
        } catch (IOException var17) {
            throw new RuntimeException(var17);
        }

        int var19 = bufferedImage.getWidth();
        int var7 = bufferedImage.getHeight();
        int[] var8 = new int[var19 * var7];
        bufferedImage.getRGB(0, 0, var19, var7, var8, 0, var19);

        int var9;
        int var10;
        int var11;
        int var12;
        int var13;
        int var15;
        int var16;
        for (var9 = 0; var9 < 256; ++var9) {
            var10 = var9 % 16;
            var11 = var9 / 16;

            for (var12 = 7; var12 >= 0; --var12) {
                var13 = var10 * 8 + var12;
                boolean var14 = true;

                for (var15 = 0; var15 < 8 && var14; ++var15) {
                    var16 = (var11 * 8 + var15) * var19;
                    int var17 = var8[var13 + var16] & 255;
                    if (var17 > 0) {
                        var14 = false;
                    }
                }

                if (!var14) {
                    break;
                }
            }

            if (var9 == 32) {
                var12 = 2;
            }

            this.charWidth[var9] = var12 + 2;
        }

        this.fontTextureName = 1;
//        renderEngine.method_1417(bufferedImage);

        for (var9 = 0; var9 < 32; ++var9) {
            var10 = (var9 >> 3 & 1) * 85;
            var11 = (var9 >> 2 & 1) * 170 + var10;
            var12 = (var9 >> 1 & 1) * 170 + var10;
            var13 = (var9 & 1) * 170 + var10;
            if (var9 == 6) {
                var11 += 85;
            }

            if (gameSettings.anaglyph) {
                int var20 = (var11 * 30 + var12 * 59 + var13 * 11) / 100;
                var15 = (var11 * 30 + var12 * 70) / 100;
                var16 = (var11 * 30 + var13 * 70) / 100;
                var11 = var20;
                var12 = var15;
                var13 = var16;
            }

            if (var9 >= 16) {
                var11 /= 4;
                var12 /= 4;
                var13 /= 4;
            }

            this.colorCode[var9] = (var11 & 255) << 16 | (var12 & 255) << 8 | var13 & 255;
        }

        for (int var14 = 0; var14 < 32; ++var14) {
            int var6 = (var14 >> 3 & 1) * 85;
            int var20 = (var14 >> 2 & 1) * 170 + var6;
            int var21 = (var14 >> 1 & 1) * 170 + var6;
            int var22 = (var14 >> 0 & 1) * 170 + var6;
            if (var14 == 6) {
                var20 += 85;
            }

            if (gameSettings.anaglyph) {
                int var23 = (var20 * 30 + var21 * 59 + var22 * 11) / 100;
                int var24 = (var20 * 30 + var21 * 70) / 100;
                int var25 = (var20 * 30 + var22 * 70) / 100;
                var20 = var23;
                var21 = var24;
                var22 = var25;
            }

            if (var14 >= 16) {
                var20 /= 4;
                var21 /= 4;
                var22 /= 4;
            }

            this.colorCode[var14] = (var20 & 255) << 16 | (var21 & 255) << 8 | var22 & 255;
        }
    }

    private void readGlyphSizes() {
        try {
            InputStream stream = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("font/glyph_sizes.bin")).getInputStream();
            stream.read(this.glyphWidth);
            this.glyphWidth['（'] = 127;
        } catch (IOException var2) {
            throw new RuntimeException(var2);
        }
    }

    public float renderCharAtPos(int charWidth, char glyphWidth, boolean par3) {
        if (glyphWidth == ' ') {
            return 4.0F;
        } else {
            return (this.ASCII.indexOf(glyphWidth) != -1) && !BGSConfig.FORCE_UNICODE_FONT.getValue() ? this.renderDefaultChar(charWidth, par3) : this.renderUnicodeChar(glyphWidth, par3);
        }
    }

    private float renderDefaultChar(int par1, boolean par2) {
        float var3 = (float)(par1 % 16 * 8);
        float var4 = (float)(par1 / 16 * 8);
        float var5 = par2 ? 1.0F : 0.0F;
        this.renderEngine.bindTexture(this.locationFontTexture);

        float var6 = (float)this.charWidth[par1] - 0.01F;
        GL11.glBegin(5);
        GL11.glTexCoord2f(var3 / 128.0F, var4 / 128.0F);
        GL11.glVertex3f(this.posX + var5, this.posY, 0.0F);
        GL11.glTexCoord2f(var3 / 128.0F, (var4 + 7.99F) / 128.0F);
        GL11.glVertex3f(this.posX - var5, this.posY + 7.99F, 0.0F);
        GL11.glTexCoord2f((var3 + var6) / 128.0F, var4 / 128.0F);
        GL11.glVertex3f(this.posX + var6 + var5, this.posY, 0.0F);
        GL11.glTexCoord2f((var3 + var6) / 128.0F, (var4 + 7.99F) / 128.0F);
        GL11.glVertex3f(this.posX + var6 - var5, this.posY + 7.99F, 0.0F);
        GL11.glEnd();
        return (float)this.charWidth[par1];
    }

    private ResourceLocation getUnicodePageLocation(int page) {
        if (unicodePageLocations[page] == null) {
            unicodePageLocations[page] = new ResourceLocation(String.format("textures/font/unicode_page_%02x.png", page));
        }

        return unicodePageLocations[page];
    }

    public final void loadGlyphTexture(int page) {
        this.renderEngine.bindTexture(this.getUnicodePageLocation(page));
    }

    private float renderUnicodeChar(char glyphWidth, boolean par2) {
        if (this.glyphWidth[glyphWidth] == 0) {
            return 0.0F;
        } else {
            int var3 = glyphWidth / 256;
            this.loadGlyphTexture(var3);
            int var4 = this.glyphWidth[glyphWidth] >>> 4;
            int var5 = this.glyphWidth[glyphWidth] & 15;
            float var6 = (float)var4;
            float var7 = (float)(var5 + 1);
            float var8 = (float)(glyphWidth % 16 * 16) + var6;
            float var9 = (float)((glyphWidth & 255) / 16 * 16);
            float var10 = var7 - var6 - 0.02F;
            float var11 = par2 ? 1.0F : 0.0F;
            GL11.glBegin(5);
            GL11.glTexCoord2f(var8 / 256.0F, var9 / 256.0F);
            GL11.glVertex3f(this.posX + var11, this.posY, 0.0F);
            GL11.glTexCoord2f(var8 / 256.0F, (var9 + 15.98F) / 256.0F);
            GL11.glVertex3f(this.posX - var11, this.posY + 7.99F, 0.0F);
            GL11.glTexCoord2f((var8 + var10) / 256.0F, var9 / 256.0F);
            GL11.glVertex3f(this.posX + var10 / 2.0F + var11, this.posY, 0.0F);
            GL11.glTexCoord2f((var8 + var10) / 256.0F, (var9 + 15.98F) / 256.0F);
            GL11.glVertex3f(this.posX + var10 / 2.0F - var11, this.posY + 7.99F, 0.0F);
            GL11.glEnd();
            return (var7 - var6) / 2.0F + 1.0F;
        }
    }

    public int drawStringWithShadow(String text, int x, int y, int textColor) {
        return this.drawString(text, x, y, textColor, true);
    }

    public int drawString(String text, int x, int y, int textColor) {
        return this.drawString(text, x, y, textColor, false);
    }

    public int drawString(String text, int x, int y, int textColor, boolean shadow) {
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        this.resetStyles();
        if (this.bidiFlag) {
            text = this.bidiReorder(text);
        }

        int var6;
        if (shadow) {
            var6 = this.renderString(text, x + 1, y + 1, textColor, true);
            var6 = Math.max(var6, this.renderString(text, x, y, textColor, false));
        } else {
            var6 = this.renderString(text, x, y, textColor, false);
        }

        return var6;
    }

    private String bidiReorder(String text) {
        if (text != null && Bidi.requiresBidi(text.toCharArray(), 0, text.length())) {
            Bidi var2 = new Bidi(text, -2);
            byte[] var3 = new byte[var2.getRunCount()];
            String[] var4 = new String[var3.length];

            int var7;
            for(int var5 = 0; var5 < var3.length; ++var5) {
                int var6 = var2.getRunStart(var5);
                var7 = var2.getRunLimit(var5);
                int var8 = var2.getRunLevel(var5);
                String var9 = text.substring(var6, var7);
                var3[var5] = (byte)var8;
                var4[var5] = var9;
            }

            String[] var11 = (String[])var4.clone();
            Bidi.reorderVisually(var3, 0, var4, 0, var3.length);
            StringBuilder var12 = new StringBuilder();

            for(var7 = 0; var7 < var4.length; ++var7) {
                byte var13 = var3[var7];

                int var14;
                for(var14 = 0; var14 < var11.length; ++var14) {
                    if (var11[var14].equals(var4[var7])) {
                        var13 = var3[var14];
                        break;
                    }
                }

                if ((var13 & 1) == 0) {
                    var12.append(var4[var7]);
                } else {
                    for(var14 = var4[var7].length() - 1; var14 >= 0; --var14) {
                        char var10 = var4[var7].charAt(var14);
                        if (var10 == '(') {
                            var10 = ')';
                        } else if (var10 == ')') {
                            var10 = '(';
                        }

                        var12.append(var10);
                    }
                }
            }

            return var12.toString();
        } else {
            return text;
        }
    }

    private void resetStyles() {
        this.randomStyle = false;
        this.boldStyle = false;
        this.italicStyle = false;
        this.underlineStyle = false;
        this.strikethroughStyle = false;
    }

    public void renderStringAtPos(String text, boolean shadow) {
        for(int var3 = 0; var3 < text.length(); ++var3) {
            char var4 = text.charAt(var3);
            int var5;
            int var6;
            if (var4 == 167 && var3 + 1 < text.length()) {
                var5 = "0123456789abcdefklmnor".indexOf(text.toLowerCase().charAt(var3 + 1));
                if (var5 < 16) {
                    this.randomStyle = false;
                    this.boldStyle = false;
                    this.strikethroughStyle = false;
                    this.underlineStyle = false;
                    this.italicStyle = false;
                    if (var5 < 0 || var5 > 15) {
                        var5 = 15;
                    }

                    if (shadow) {
                        var5 += 16;
                    }

                    var6 = this.colorCode[var5];
                    this.textColor = var6;
                    GL11.glColor4f((float)(var6 >> 16) / 255.0F, (float)(var6 >> 8 & 255) / 255.0F, (float)(var6 & 255) / 255.0F, this.alpha);
                    var3 = applyCustomFormatCodes(this, text, shadow, var3);
                } else if (var5 == 16) {
                    this.randomStyle = true;
                } else if (var5 == 17) {
                    this.boldStyle = true;
                } else if (var5 == 18) {
                    this.strikethroughStyle = true;
                } else if (var5 == 19) {
                    this.underlineStyle = true;
                } else if (var5 == 20) {
                    this.italicStyle = true;
                } else if (var5 == 21) {
                    this.randomStyle = false;
                    this.boldStyle = false;
                    this.strikethroughStyle = false;
                    this.underlineStyle = false;
                    this.italicStyle = false;
                    GL11.glColor4f(this.red, this.blue, this.green, this.alpha);
                }

                ++var3;
            } else {
                var5 = this.ASCII.indexOf(var4);
                if (this.randomStyle && var5 != -1) {
                    do {
                        var6 = this.fontRandom.nextInt(this.charWidth.length);
                    } while (this.charWidth[var5] != this.charWidth[var6]);

                    var5 = var6;
                }

                float var11 = var5 == -1 ? 0.5F : 1.0F;
                boolean isUnicodeFlag = (var4 == 0 || var5 == -1) && shadow;
                if (isUnicodeFlag) {
                    this.posX -= var11;
                    this.posY -= var11;
                }

                float var9 = this.renderCharAtPos(var5, var4, this.italicStyle);
                if (isUnicodeFlag) {
                    this.posX += var11;
                    this.posY += var11;
                }

                if (this.boldStyle) {
                    this.posX += var11;
                    if (isUnicodeFlag) {
                        this.posX -= var11;
                        this.posY -= var11;
                    }

                    this.renderCharAtPos(var5, var4, this.italicStyle);
                    this.posX -= var11;
                    if (isUnicodeFlag) {
                        this.posX += var11;
                        this.posY += var11;
                    }

                    ++var9;
                }

                Tessellator var7;
                if (this.strikethroughStyle) {
                    var7 = Tessellator.instance;
                    GL11.glDisable(3553);
                    var7.startDrawingQuads();
                    var7.addVertex((double)this.posX, (double)(this.posY + (float)(this.FONT_HEIGHT / 2)), 0.0);
                    var7.addVertex((double)(this.posX + var9), (double)(this.posY + (float)(this.FONT_HEIGHT / 2)), 0.0);
                    var7.addVertex((double)(this.posX + var9), (double)(this.posY + (float)(this.FONT_HEIGHT / 2) - 1.0F), 0.0);
                    var7.addVertex((double)this.posX, (double)(this.posY + (float)(this.FONT_HEIGHT / 2) - 1.0F), 0.0);
                    var7.draw();
                    GL11.glEnable(3553);
                }

                if (this.underlineStyle) {
                    var7 = Tessellator.instance;
                    GL11.glDisable(3553);
                    var7.startDrawingQuads();
                    int var8 = this.underlineStyle ? -1 : 0;
                    var7.addVertex((double)(this.posX + (float)var8), (double)(this.posY + (float)this.FONT_HEIGHT), 0.0);
                    var7.addVertex((double)(this.posX + var9), (double)(this.posY + (float)this.FONT_HEIGHT), 0.0);
                    var7.addVertex((double)(this.posX + var9), (double)(this.posY + (float)this.FONT_HEIGHT - 1.0F), 0.0);
                    var7.addVertex((double)(this.posX + (float)var8), (double)(this.posY + (float)this.FONT_HEIGHT - 1.0F), 0.0);
                    var7.draw();
                    GL11.glEnable(3553);
                }

                this.posX += (float) ((int) var9);
            }
        }

    }

    /**
     * api for emi
     */
    public static int applyCustomFormatCodes(FontFixer subject, String text, boolean shadow, int i) {
        if (text.charAt(i + 1) == 'x') {
            int next = text.indexOf(String.valueOf('\u00a7') + "x", i + 1);
            if (next != -1) {
                String s = text.substring(i + 1, next);
                int color = Integer.parseInt(s.replace(String.valueOf('\u00a7'), "").substring(1), 16);
                if (shadow) {
                    color = (color & 16579836) >> 2 | color & -16777216;
                }
                subject.textColor = color;
                glColor4f((color >> 16) / 255.0F, (color >> 8 & 255) / 255.0F, (color & 255) / 255.0F, subject.alpha);
                i += s.length() + 1;
            }
        }
        return i;
    }

    private int renderStringAligned(String text, int x, int y, int width, int textColor, boolean shadow) {
        if (this.bidiFlag) {
            text = this.bidiReorder(text);
            int var7 = this.getStringWidth(text);
            x = x + width - var7;
        }

        return this.renderString(text, x, y, textColor, shadow);
    }

    private int renderString(String text, int x, int y, int textColor, boolean shadow) {
        if (text != null && this.bidiFlag) {
            text = this.bidiReorder(text);
        }
        if (text == null) {
            return 0;
        } else {
            this.boundTextureName = 0;
            if ((textColor & 0xFC000000) == 0) {
                textColor |= 0xFF000000;
            }

            if (shadow) {
                textColor = (textColor & 0xFCFCFC) >> 2 | textColor & 0xFF000000;
            }

            this.red = (float) (textColor >> 16 & 0xFF) / 255.0F;
            this.blue = (float) (textColor >> 8 & 0xFF) / 255.0F;
            this.green = (float) (textColor & 0xFF) / 255.0F;
            this.alpha = (float) (textColor >> 24 & 0xFF) / 255.0F;
            GL11.glColor4f(this.red, this.blue, this.green, this.alpha);
            this.posX = (float) x;
            this.posY = (float) y;
            this.renderStringAtPos(text, shadow);
            return (int) this.posX;
        }
    }

    public int getStringWidth(String text) {
        if (text == null) {
            return 0;
        } else {
            int var2 = 0;
            boolean var3 = false;

            for (int var4 = 0; var4 < text.length(); ++var4) {
                char var5 = text.charAt(var4);
                int var6 = this.getCharWidth(var5);
                if (var6 < 0 && var4 < text.length() - 1) {
                    ++var4;
                    var5 = text.charAt(var4);
                    if (var5 != 'l' && var5 != 'L') {
                        if (var5 == 'r' || var5 == 'R') {
                            var3 = false;
                        }
                    } else {
                        var3 = true;
                    }

                    var6 = 0;
                }

                var2 += var6;
                if (var3) {
                    ++var2;
                }
            }

            return var2;
        }
    }

    public int getCharWidth(char glyphWidth) {
        if (glyphWidth == 167) {
            return -1;
        } else if (glyphWidth == ' ') {
            return 4;
        } else {
            int var2 = this.ASCII.indexOf(glyphWidth);
            if (glyphWidth > 0 && var2 != -1 && !BGSConfig.FORCE_UNICODE_FONT.getValue()) {
                return this.charWidth[var2];
            } else if (this.glyphWidth[glyphWidth] != 0) {
                int var3 = this.glyphWidth[glyphWidth] >>> 4;
                int var4 = this.glyphWidth[glyphWidth] & 15;
                if (var4 > 7) {
                    var4 = 15;
                    var3 = 0;
                }

                ++var4;
                return (var4 - var3) / 2 + 1;
            } else {
                return 0;
            }
        }
    }

    public String trimStringToWidth(String text, int width) {
        return this.trimStringToWidth(text, width, false);
    }

    public String trimStringToWidth(String text, int width, boolean par3) {
        StringBuilder var4 = new StringBuilder();
        int var5 = 0;
        int var6 = par3 ? text.length() - 1 : 0;
        int var7 = par3 ? -1 : 1;
        boolean var8 = false;
        boolean var9 = false;

        for (int var10 = var6; var10 >= 0 && var10 < text.length() && var5 < width; var10 += var7) {
            char var11 = text.charAt(var10);
            int var12 = this.getCharWidth(var11);
            if (var8) {
                var8 = false;
                if (var11 != 'l' && var11 != 'L') {
                    if (var11 == 'r' || var11 == 'R') {
                        var9 = false;
                    }
                } else {
                    var9 = true;
                }
            } else if (var12 < 0) {
                var8 = true;
            } else {
                var5 += var12;
                if (var9) {
                    ++var5;
                }
            }

            if (var5 > width) {
                break;
            }

            if (par3) {
                var4.insert(0, var11);
            } else {
                var4.append(var11);
            }
        }

        return var4.toString();
    }

    private String trimStringNewline(String text) {
        while(text != null && text.endsWith("\n")) {
            text = text.substring(0, text.length() - 1);
        }

        return text;
    }

    public void drawSplitString(String text, int x, int y, int width, int textColor) {
        this.resetStyles();
        this.textColor = textColor;
        text = this.trimStringNewline(text);
        this.renderSplitString(text, x, y, width, false);
    }

    private void renderSplitString(String text, int x, int y, int width, boolean shadow) {
        List textList = this.listFormattedStringToWidth(text, width);

        for (Iterator var7 = textList.iterator(); var7.hasNext(); y += this.FONT_HEIGHT) {
            String var8 = (String) var7.next();
            this.renderStringAligned(var8, x, y, width, this.textColor, shadow);
        }

    }

    public int splitStringWidth(String text, int width) {
        return this.FONT_HEIGHT * this.listFormattedStringToWidth(text, width).size();
    }

    public void setUnicodeFlag(boolean unicodeFlag) {
        this.unicodeFlag = unicodeFlag;
    }

    public boolean getUnicodeFlag() {
        return this.unicodeFlag;
    }

    public void setBidiFlag(boolean bidiFlag) {
        this.bidiFlag = bidiFlag;
    }

    public boolean getBidiFlag() {
        return this.bidiFlag;
    }

    public List listFormattedStringToWidth(String text, int width) {
        return Arrays.asList(this.wrapFormattedStringToWidth(text, width).split("\n"));
    }

    public String wrapFormattedStringToWidth(String text, int width) {
        int var3 = this.sizeStringToWidth(text, width);
        if (text.length() <= var3) {
            return text;
        } else {
            String var4 = text.substring(0, var3);
            char var5 = text.charAt(var3);
            boolean var6 = var5 == ' ' || var5 == '\n';
            String var7 = getFormatFromString(var4) + text.substring(var3 + (var6 ? 1 : 0));
            return var4 + "\n" + this.wrapFormattedStringToWidth(var7, width);
        }
    }

    private int sizeStringToWidth(String text, int width) {
        int var3 = text.length();
        int var4 = 0;
        int var5 = 0;
        int var6 = -1;

        for(boolean var7 = false; var5 < var3; ++var5) {
            char var8 = text.charAt(var5);
            switch (var8) {
                case '\n':
                    --var5;
                    break;
                case ' ':
                    var6 = var5;
                default:
                    var4 += this.getCharWidth(var8);
                    if (var7) {
                        ++var4;
                    }
                    break;
                case '§':
                    if (var5 < var3 - 1) {
                        ++var5;
                        char var9 = text.charAt(var5);
                        if (var9 != 'l' && var9 != 'L') {
                            if (var9 == 'r' || var9 == 'R' || isFormatColor(var9)) {
                                var7 = false;
                            }
                        } else {
                            var7 = true;
                        }
                    }
            }

            if (var8 == '\n') {
                ++var5;
                var6 = var5;
                break;
            }

            if (var4 > width) {
                break;
            }
        }

        return var5 != var3 && var6 != -1 && var6 < var5 ? var6 : var5;
    }

    private static boolean isFormatColor(char textChar) {
        return textChar >= '0' && textChar <= '9' || textChar >= 'a' && textChar <= 'f' || textChar >= 'A' && textChar <= 'F';
    }

    private static boolean isFormatSpecial(char textChar) {
        return textChar >= 'k' && textChar <= 'o' || textChar >= 'K' && textChar <= 'O' || textChar == 'r' || textChar == 'R';
    }

    private static String getFormatFromString(String text) {
        StringBuilder var1 = new StringBuilder();
        int var2 = -1;
        int var3 = text.length();

        while((var2 = text.indexOf(167, var2 + 1)) != -1) {
            if (var2 < var3 - 1) {
                char var4 = text.charAt(var2 + 1);
                if (isFormatColor(var4)) {
                    var1 = new StringBuilder("§" + var4);
                } else if (isFormatSpecial(var4)) {
                    var1.append("§").append(var4);
                }
            }
        }

        return var1.toString();
    }
}
