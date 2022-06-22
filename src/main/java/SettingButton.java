import javax.swing.*;

public class SettingButton extends JButton {
    private final int X;
    private final int Y;
    private static final int Width=50;
    private final int height=50;
    private boolean visible=true;
    SettingButton(int x, int y, String insideText){
        this.X=x;
        this.Y=y;
    setText(insideText);
    setBounds(X,Y,Width,height);
    setVisible(visible);
    }
}
