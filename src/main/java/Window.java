import javax.swing.*;

public class Window extends JFrame{
    public final static int WINDOW_WIDTH = 1920;
    public final static int WINDOW_HEIGHT = 1080;

    public static void main(String[] args) {
        Window main=new Window();
    }
    Window(){
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        EditorBody editorBody=new EditorBody();
        add(editorBody);
        setVisible(true);

    }
}
