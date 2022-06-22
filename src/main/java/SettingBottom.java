import javax.swing.*;

public class SettingBottom extends JButton {
    private final int X;
    private final int Y;
    private static final int Width=50;
    private final int height=50;
    private boolean visible=true;
    SettingBottom(int x,int y,String insideText){
        this.X=x;
        this.Y=y;
    setText(insideText);
    setBounds(X,Y,Width,height);
    setVisible(visible);
    }
}
