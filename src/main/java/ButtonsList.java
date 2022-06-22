import javax.swing.*;
import java.awt.*;

public class ButtonsList extends JButton{
    private final int X;
    private final int Y;
    private final JButton [] BUTTONS_LIST;
    private boolean visible=false;
    private int yLocation;
    private final int FONT_SIZE=20;

    public ButtonsList(int x, int y, String insideText, int width, int height, JButton [] list, String []textList){
        this.X=x;
        this.Y=y;
        this.BUTTONS_LIST =list;
        this.setFont(new Font("arial", Font.BOLD, FONT_SIZE));
        setBounds(X,Y,width,height);
        setText(insideText);
        setVisible(true);
        yLocation=y;
        for (int i = 0; i < list.length; i++) {
            yLocation+=height;
            BUTTONS_LIST[i]=new JButton();
            BUTTONS_LIST[i].setBounds(X,yLocation,width,height);
            BUTTONS_LIST[i].setVisible(false);
            BUTTONS_LIST[i].setText(textList[i]);
            BUTTONS_LIST[i].addActionListener((e)-> changeVisible());
        }
        addActionListener((e)-> changeVisible());
    }

    public void changeVisible(){
        visible=!visible;
        for (int i = 0; i < BUTTONS_LIST.length; i++) {
            BUTTONS_LIST[i].setVisible(visible);
        }
    }
}
