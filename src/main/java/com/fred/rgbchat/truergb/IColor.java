
package com.fred.rgbchat.truergb;

public interface IColor {
    int alpha();

    int red();

    int green();

    int blue();

    default int toInt() {
        return this.red() << 16 | this.green() << 8 | this.blue();
    }
}

