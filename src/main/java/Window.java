import javax.swing.*;

public class Window extends JFrame{
    public final static int WINDOW_WIDTH = 1920;
    public final static int WINDOW_HEIGHT = 1080;

    public static void main(String[] args) {
        Window main=new Window();
    }
    Window(){
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(true);
        EditorBody editorBody=new EditorBody();
        this.add(editorBody);
        this.setVisible(true);

    }
}
