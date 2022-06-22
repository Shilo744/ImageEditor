import javax.swing.*;
import java.awt.*;

public class BottomsList extends JButton{
    private final int X;
    private final int Y;
    private final JButton [] BOTTOMS_LIST;
    private boolean visible=false;
    private int yLocation;
    private final int FONT_SIZE=20;
    public BottomsList(int x,int y,String insideText,int width,int height,JButton [] list,String []textList){
        this.X=x;
        this.Y=y;
        this.BOTTOMS_LIST =list;
        this.setFont(new Font("arial", Font.BOLD, FONT_SIZE));
        setBounds(X,Y,width,height);
        setText(insideText);
        setVisible(true);
        yLocation=y;
        for (int i = 0; i < list.length; i++) {
            yLocation+=height;
            BOTTOMS_LIST[i]=new JButton();
            BOTTOMS_LIST[i].setBounds(X,yLocation,width,height);
            BOTTOMS_LIST[i].setVisible(false);
            BOTTOMS_LIST[i].setText(textList[i]);
            BOTTOMS_LIST[i].addActionListener((e)-> changeVisible());
        }
        addActionListener((e)-> changeVisible());
    }

    public void changeVisible(){
        visible=!visible;
        for (int i = 0; i < BOTTOMS_LIST.length; i++) {
            BOTTOMS_LIST[i].setVisible(visible);
        }
    }
}
