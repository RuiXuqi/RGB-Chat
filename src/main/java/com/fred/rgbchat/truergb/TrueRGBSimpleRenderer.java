package com.fred.rgbchat.truergb;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Tuple;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class TrueRGBSimpleRenderer extends FontRenderer {
    int preColor = -1;

    public TrueRGBSimpleRenderer(GameSettings gameSettingsIn, ResourceLocation location, TextureManager textureManagerIn, boolean unicode) {
        super(gameSettingsIn, location, textureManagerIn, unicode);
    }

    public int drawString(String text, float x, float y, int color, boolean dropShadow) {
        List<Tuple<String, RGBSettings>> settings = RGBSettings.split(text);

        int cColor = color;
        preColor = cColor;
        this.posX = x;
        for (Tuple<String, RGBSettings> tuple : settings) {
            String s = tuple.getFirst();
            RGBSettings set = tuple.getSecond();
            Set<Integer> se = new HashSet<>();
            List<Integer> sset = new ArrayList<>();

            for (int j = 0; j < set.getLength(); j++) {
                {
                    int cc = Objects.requireNonNull(set.getColorAt(j)).toInt();
                    if (se.isEmpty() || !se.contains(cc)) {
                        sset.add(cc);
                        se.add(cc);
                        //System.out.println(set.getColorAt(j).red() + ", " + set.getColorAt(j).green() + ", " + set.getColorAt(j).blue());
                    }
                }
            }

            if (set.isFixedColor()) {
                cColor = Optional.ofNullable(set.getColorAt(0)).map(IColor::toInt).orElse(cColor);
                super.drawString(set.getFormatString() + s, this.posX, y, cColor, dropShadow);
                continue;
            }

            List<Integer> colorSet = new ArrayList<>();
            int partlen = (int) Math.ceil(1.0 * s.length() / (sset.size() - 1));
            //int partlen=(int)Math.ceil(1.0*s.length()/(set.getLength()-1));
            for (int i = 0; i < s.length(); i++) {
//                double increment=(1.0*set.getColorAt(Math.min(i/partlen,set.getLength()-1)).toInt()
//                       - 1.0*set.getColorAt(Math.min(i/partlen + 1,set.getLength()-1)).toInt() )/(set.getLength() - 1);
//                colorSet.add(set.getColorAt(Math.min(i/partlen,set.getLength()-1)).toInt()+(int)increment*i%partlen);
                int pre = sset.get(Math.min(i / partlen, sset.size() - 1));
                int cur = sset.get(Math.min(i / partlen + 1, sset.size() - 1));
                int increment = ((cur - pre) / partlen);
                colorSet.add(pre + increment * (i % partlen));
//                colourGradient.setGradient(spectrum[i], spectrum[i + 1]);
//                colourGradient.setNumberRange(minNum + increment * i, minNum + increment * (i + 1));
//                colourGradients.add(colourGradient);
            }

            for (int i = 0; i < s.length(); ++i) {
                //cColor = Optional.ofNullable(set.getColorAt(i)).map(IColor::toInt).orElse(colorSet.get(i));
                cColor = colorSet.get(i);
                super.drawString(set.getFormatString() + s.charAt(i), this.posX, y, cColor, dropShadow);
            }
        }
        return (int) this.posX;
    }

    public int getStringWidth(String text) {
        return super.getStringWidth(RGBSettings.split(text).stream().map(Tuple::getFirst).collect(Collectors.joining()));
        //return super.getStringWidth(text);
    }
}