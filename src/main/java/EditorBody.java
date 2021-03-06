import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class EditorBody extends JPanel{
    // error frame
    private JFrame errorFrame = new JFrame();

    //choose photo
    private final TextField PHOTO_LOCATION =new TextField();
    private final int PHOTO_PATH_IN_COMPUTER_X =640;
    private final int PHOTO_PATH_IN_COMPUTER_Y =25;
    private final int PHOTO_PATH_IN_COMPUTER_WIDTH =190;
    private final int PHOTO_PATH_IN_COMPUTER_HEIGHT =30;
    private final JButton SEND_PATH_IN_COMPUTER_BUTTON =new JButton();

    //set Photo
    private String pathInComputer;
    private String add;
    private int newPhotoName;
    private final int MAKE_RELATIVE_SIZE=1000;
    private File file;
    private BufferedImage image;
    private int qualityWidth;
    private int qualityHeight;
    private Pixel[][] fullPhoto;
    private final int CHANGE_RATIO =1;
    private int shrinkPhoto;
    private int shrinkPhotoHeight;
    private int shrinkPhotoWidth;
    private Pixel[][] shrinkImage;
    private Pixel[][] shrinkImageOriginal;
    private boolean displayOriginal=false;

    //buttons
    private final String [] ENGLISH_BUTTON_NAMES ={"shadows","lights","contrast","colors","warm","red","green","blue","white","sunLight"};
    private final String [] HEBREW_BUTTON_NAMES ={"צללים","אור","ניגודיות","צבעים","חמים","אדום","ירוק","כחול","לבן","שמש"};
    private boolean isLanguageEnglish=true;
    private final SettingButton[] PLUS_BUTTONS = new SettingButton[ENGLISH_BUTTON_NAMES.length];
    private final SettingButton[] MINUS_BUTTONS = new SettingButton[ENGLISH_BUTTON_NAMES.length];
    private final TextField[] FUNCTION =new TextField[ENGLISH_BUTTON_NAMES.length];
    private final JButton RESET =new JButton();
    private final JButton SHOW_ORIGINAL_PHOTO =new JButton();
    private String edited="showEdited";
    private String original="showOriginal";
    private final JButton SAVE=new JButton();
    private final int SAVE_Y=25;
    private final int GENERAL_BOTTOM_WIDTH =160;
    private final int GENERAL_BOTTOM_HEIGHT =70;
    private final int START_ADD =11;
    private final int PLUS_BUTTONS_START =150;
    private final int MINUS_BUTTONS_START =10;
    private final int Y_START=120;
    private final int BUTTONS_DISTANCE =90;
    private final int FONT_SIZE=20;
    private final int LITTLE_FONT_SIZE=12;
    private final int NOTHING=0;

    //label
    private final JLabel EXPLAIN =new JLabel("copy and paste photo path (ctrl+shift+c)");
    private final int NEW_LABEL_WIDTH =300;
    private final int NEW_LABEL_HEIGHT =30;

    //effect buttons
    private final String[] ENGLISH_EFFECT_NAMES = {"negative", "makeGray", "sparkles","creativeChange","addNoise","mirror"};
    private final String[] HEBREW_EFFECT_NAMES = {"היפוך צבעים", "גווני אפור", "ניצוצות צבעוניים","שינוי יצירתי","הוסף רעש","מראה"};
    private final int NUM_OF_EFFECTS= ENGLISH_EFFECT_NAMES.length;
    private final JButton[] EFFECT_BUTTONS = new JButton[NUM_OF_EFFECTS];
    private final ButtonsList MENU_BUTTON =new ButtonsList(1300,Y_START-90,"effects",160,60, EFFECT_BUTTONS, ENGLISH_EFFECT_NAMES);
    private final JButton CHANGE_LANGUAGE =new JButton();

    //facebook button and fields
    private final JButton FACEBOOK_BUTTON =new JButton();
    private final int FACEBOOK_X=550;
    private final int FACEBOOK_Y=25;
    private final int FACEBOOK_WIDTH=90;
    private final int FACEBOOK_HEIGHT=30;
    private final String FACEBOOK_ENGLISH="facebook";
    private final String FACEBOOK_HEBREW="פייסבוק";
    private final String FACEBOOK_ENGLISH_LOCAL="Local";
    private final String FACEBOOK_HEBREW_LOCAL="מקומי";

    private boolean facebook=false;
    private final TextField USERNAME_TEXT_FIELD =new TextField();
    private final TextField PATH_TEXT_FIELD =new TextField();
    private final JButton SUBMIT_FACEBOOK_DETAILS_BUTTON =new JButton();
    private final JLabel EXPLAIN_PATH=new JLabel();
    private final String EXPLAIN_PATH_ENGLISH="  Save location";
    private final String EXPLAIN_PATH_HEBREW="שמור במיקום   ";
    private final JLabel EXPLAIN_USERNAME=new JLabel();
    private final String EXPLAIN_USERNAME_ENGLISH="Enter facebook username:";
    private final String EXPLAIN_USERNAME_HEBREW="הכנס את שם משתמש של פייסבוק:";

    //fields
    private final int MAX_COLOR=255;
    private final int change=3;
    private int shadows=0;
    private int contrast=0;
    private int light =0;
    private int color=0;
    private int warm=0;
    private int sparkles =0;
    private int addNoise=0;
    private int red=0;
    private int green=0;
    private int blue=0;
    private int white=0;
    private int sunLight =0;

    //effects
    private boolean isNegativeEffect =false;
    private boolean isGrayEffect =false;
    private boolean isMirrorEffect =false;

    EditorBody(){
        try {
            this.setLayout(null);
            this.setBackground(null);
            this.setDoubleBuffered(true);
            this.setBackground(Color.black);

            //effects button list
            this.add(MENU_BUTTON);
            int rightSide=0;
            for (int i = 0; i < EFFECT_BUTTONS.length; i++) {
                this.add(EFFECT_BUTTONS[i]);}
            int numberOfLeftButtons=5;
            for (int i = 0; i < ENGLISH_BUTTON_NAMES.length; i++) {
                int yLocation;
                if(i>=numberOfLeftButtons){
                    yLocation= Y_START + (i-numberOfLeftButtons) * BUTTONS_DISTANCE;
                    rightSide= MENU_BUTTON.getX()- MINUS_BUTTONS_START -12;}
                else {
                    yLocation = Y_START + i * BUTTONS_DISTANCE;
                }

                PLUS_BUTTONS[i]=new SettingButton(PLUS_BUTTONS_START +rightSide,yLocation,">+");
                MINUS_BUTTONS[i]=new SettingButton(MINUS_BUTTONS_START +rightSide,yLocation,"-<");
                FUNCTION[i]=new TextField();
                FUNCTION[i].setBounds(MINUS_BUTTONS_START + MINUS_BUTTONS[i].getWidth()+rightSide,yLocation, BUTTONS_DISTANCE, MINUS_BUTTONS[i].getHeight());
                FUNCTION[i].setEnabled(false);
                FUNCTION[i].setFont(new Font("arial", Font.BOLD, FONT_SIZE));
                FUNCTION[i].setText(ENGLISH_BUTTON_NAMES[i]);
                this.add(PLUS_BUTTONS[i]);
                this.add(MINUS_BUTTONS[i]);
                this.add(FUNCTION[i]);
            }
            //editing buttons
            PLUS_BUTTONS[0].addActionListener((e)-> shadowChange(shrinkImage,shrinkPhotoWidth,shrinkPhotoHeight,change));
            MINUS_BUTTONS[0].addActionListener((e)-> shadowChange(shrinkImage,shrinkPhotoWidth,shrinkPhotoHeight,-change));
            PLUS_BUTTONS[1].addActionListener((e)-> light(shrinkImage,shrinkPhotoWidth,shrinkPhotoHeight,change));
            MINUS_BUTTONS[1].addActionListener((e)-> light(shrinkImage,shrinkPhotoWidth,shrinkPhotoHeight,-change));
            PLUS_BUTTONS[2].addActionListener((e)-> enhanceContrast(shrinkImage,shrinkPhotoWidth,shrinkPhotoHeight,change));
            MINUS_BUTTONS[2].addActionListener((e)-> enhanceContrast(shrinkImage,shrinkPhotoWidth,shrinkPhotoHeight,-change));
            PLUS_BUTTONS[3].addActionListener((e)-> addColor(shrinkImage,shrinkPhotoWidth,shrinkPhotoHeight,change));
            MINUS_BUTTONS[3].addActionListener((e)-> addColor(shrinkImage,shrinkPhotoWidth,shrinkPhotoHeight,-change));
            PLUS_BUTTONS[4].addActionListener((e)-> warm(shrinkImage,shrinkPhotoWidth,shrinkPhotoHeight,change));
            MINUS_BUTTONS[4].addActionListener((e)-> warm(shrinkImage,shrinkPhotoWidth,shrinkPhotoHeight,-change));
            PLUS_BUTTONS[5].addActionListener((e)-> red(shrinkImage,shrinkPhotoWidth,shrinkPhotoHeight,change));
            MINUS_BUTTONS[5].addActionListener((e)-> red(shrinkImage,shrinkPhotoWidth,shrinkPhotoHeight,-change));
            PLUS_BUTTONS[6].addActionListener((e)-> green(shrinkImage,shrinkPhotoWidth,shrinkPhotoHeight,change));
            MINUS_BUTTONS[6].addActionListener((e)-> green(shrinkImage,shrinkPhotoWidth,shrinkPhotoHeight,-change));
            PLUS_BUTTONS[7].addActionListener((e)-> blue(shrinkImage,shrinkPhotoWidth,shrinkPhotoHeight,change));
            MINUS_BUTTONS[7].addActionListener((e)-> blue(shrinkImage,shrinkPhotoWidth,shrinkPhotoHeight,-change));
            PLUS_BUTTONS[8].addActionListener((e)-> white(shrinkImage,shrinkPhotoWidth,shrinkPhotoHeight,change));
            MINUS_BUTTONS[8].addActionListener((e)-> white(shrinkImage,shrinkPhotoWidth,shrinkPhotoHeight,-change));
            PLUS_BUTTONS[9].addActionListener((e)-> yellow(shrinkImage,shrinkPhotoWidth,shrinkPhotoHeight,change));
            MINUS_BUTTONS[9].addActionListener((e)-> yellow(shrinkImage,shrinkPhotoWidth,shrinkPhotoHeight,-change));
            //effect buttons
            EFFECT_BUTTONS[0].addActionListener((e)-> negative(shrinkImage,shrinkPhotoWidth,shrinkPhotoHeight));
            EFFECT_BUTTONS[1].addActionListener((e)-> makeGray(shrinkImage,shrinkPhotoWidth,shrinkPhotoHeight));
            EFFECT_BUTTONS[2].addActionListener((e)-> sparkles(shrinkImage,shrinkPhotoWidth,shrinkPhotoHeight));
            EFFECT_BUTTONS[3].addActionListener((e)-> creativeChange(shrinkImage,shrinkPhotoWidth,shrinkPhotoHeight));
            EFFECT_BUTTONS[4].addActionListener((e)-> addNoise(shrinkImage,shrinkPhotoWidth,shrinkPhotoHeight));
            EFFECT_BUTTONS[5].addActionListener((e)-> mirror(shrinkImage,shrinkPhotoWidth,shrinkPhotoHeight));


            setNormalButton(SAVE,"save");
            SAVE.setBounds(MINUS_BUTTONS_START + START_ADD,SAVE_Y, GENERAL_BOTTOM_WIDTH, GENERAL_BOTTOM_HEIGHT);
            SAVE.addActionListener((e)-> setLargePhoto());
            this.add(SAVE);

            setNormalButton(RESET,"reset");
            RESET.setBounds(MINUS_BUTTONS_START + START_ADD, PLUS_BUTTONS[4].getY()+ PLUS_BUTTONS[4].getHeight()+Y_START, GENERAL_BOTTOM_WIDTH, GENERAL_BOTTOM_HEIGHT);
            RESET.addActionListener((e)-> resetPhoto());
            this.add(RESET);

            setNormalButton(SHOW_ORIGINAL_PHOTO,"showOriginal");
            SHOW_ORIGINAL_PHOTO.setBounds(MINUS_BUTTONS_START + START_ADD, PLUS_BUTTONS[4].getY()+ PLUS_BUTTONS[4].getHeight()+Y_START-RESET.getHeight()-20, GENERAL_BOTTOM_WIDTH, GENERAL_BOTTOM_HEIGHT);
            SHOW_ORIGINAL_PHOTO.addActionListener((e)->{
                displayOriginal=!displayOriginal;
                if(displayOriginal){
                    SHOW_ORIGINAL_PHOTO.setText(edited);
                }
                else{
                    SHOW_ORIGINAL_PHOTO.setText(original);
                }
                repaint();
            });
            this.add(SHOW_ORIGINAL_PHOTO);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Error at editorBody");
        }
        addTextAndButton();
        this.add(PHOTO_LOCATION);
        this.add(SEND_PATH_IN_COMPUTER_BUTTON);
        setNormalButton(CHANGE_LANGUAGE,"לעברית");
        CHANGE_LANGUAGE.setBounds(MENU_BUTTON.getX(), SHOW_ORIGINAL_PHOTO.getY(), GENERAL_BOTTOM_WIDTH,GENERAL_BOTTOM_HEIGHT);

        CHANGE_LANGUAGE.addActionListener((e)->changeLanguage());
        this.add(CHANGE_LANGUAGE);
        newLabel(EXPLAIN,PHOTO_LOCATION.getX(),NOTHING);
        FACEBOOK_BUTTON.setText(FACEBOOK_ENGLISH_LOCAL);
        FACEBOOK_BUTTON.setBounds(FACEBOOK_X,FACEBOOK_Y,FACEBOOK_WIDTH,FACEBOOK_HEIGHT);

        FACEBOOK_BUTTON.addActionListener((e)->{
            facebook=!facebook;
            PHOTO_LOCATION.setVisible(!facebook);
            SEND_PATH_IN_COMPUTER_BUTTON.setVisible(!facebook);
            EXPLAIN.setVisible(!facebook);
            PATH_TEXT_FIELD.setVisible(facebook);
            USERNAME_TEXT_FIELD.setVisible(facebook);
            SUBMIT_FACEBOOK_DETAILS_BUTTON.setVisible(facebook);
            EXPLAIN_USERNAME.setVisible(facebook);
            EXPLAIN_PATH.setVisible(facebook);
            if(facebook){
                if(isLanguageEnglish){
                    FACEBOOK_BUTTON.setText(FACEBOOK_ENGLISH);
                }else {
                    FACEBOOK_BUTTON.setText(FACEBOOK_HEBREW);
                }
            }else {
                if(isLanguageEnglish){
                    FACEBOOK_BUTTON.setText(FACEBOOK_ENGLISH_LOCAL);
                }else {
                    FACEBOOK_BUTTON.setText(FACEBOOK_HEBREW_LOCAL);
                }
            }
        });
        add(FACEBOOK_BUTTON);
        USERNAME_TEXT_FIELD.setBounds(FACEBOOK_X+FACEBOOK_WIDTH,FACEBOOK_Y, PHOTO_PATH_IN_COMPUTER_WIDTH,FACEBOOK_HEIGHT);
        USERNAME_TEXT_FIELD.setVisible(false);
        add(USERNAME_TEXT_FIELD);
        PATH_TEXT_FIELD.setBounds(FACEBOOK_X+FACEBOOK_WIDTH,FACEBOOK_Y+FACEBOOK_HEIGHT, PHOTO_PATH_IN_COMPUTER_WIDTH,FACEBOOK_HEIGHT);
        PATH_TEXT_FIELD.setVisible(false);
        add(PATH_TEXT_FIELD);
        SUBMIT_FACEBOOK_DETAILS_BUTTON.setBounds(PATH_TEXT_FIELD.getX()+ PHOTO_PATH_IN_COMPUTER_WIDTH,FACEBOOK_Y,2*FACEBOOK_HEIGHT,2*FACEBOOK_HEIGHT);
        SUBMIT_FACEBOOK_DETAILS_BUTTON.setVisible(false);
        SUBMIT_FACEBOOK_DETAILS_BUTTON.setText("...");

        SUBMIT_FACEBOOK_DETAILS_BUTTON.addActionListener((e)-> takeFacebookPhoto());
        add(SUBMIT_FACEBOOK_DETAILS_BUTTON);
        EXPLAIN_PATH.setVisible(false);
        EXPLAIN_PATH.setBounds(FACEBOOK_X,FACEBOOK_Y+FACEBOOK_HEIGHT,FACEBOOK_WIDTH,FACEBOOK_HEIGHT);
        EXPLAIN_PATH.setForeground(Color.WHITE);
        EXPLAIN_PATH.setText(EXPLAIN_PATH_ENGLISH);
        add(EXPLAIN_PATH);

        EXPLAIN_USERNAME.setVisible(false);
        EXPLAIN_USERNAME.setBounds(EXPLAIN.getBounds());
        EXPLAIN_USERNAME.setForeground(Color.WHITE);
        EXPLAIN_USERNAME.setText("Enter facebook username:");
        add(EXPLAIN_USERNAME);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Pixel[][] displayedArray;
        displayedArray=arrayToDraw();
        try{
            for (int i = 0; i < qualityHeight /shrinkPhoto; i++) {
                for (int j = 0; j < qualityWidth /shrinkPhoto; j++) {
                    displayedArray[i][j].paint(g);
                }
            }
        }catch (Exception ignored){
        }
    }

    private void addTextAndButton(){
        PHOTO_LOCATION.setBounds(PHOTO_PATH_IN_COMPUTER_X, PHOTO_PATH_IN_COMPUTER_Y, PHOTO_PATH_IN_COMPUTER_WIDTH, PHOTO_PATH_IN_COMPUTER_HEIGHT);
        PHOTO_LOCATION.setFont(new Font("arial", Font.BOLD, LITTLE_FONT_SIZE));
        SEND_PATH_IN_COMPUTER_BUTTON.setBounds(PHOTO_LOCATION.getX()+ PHOTO_LOCATION.getWidth(), PHOTO_LOCATION.getY(), PHOTO_LOCATION.getHeight(), PHOTO_LOCATION.getHeight());
        SEND_PATH_IN_COMPUTER_BUTTON.setText("...");

        SEND_PATH_IN_COMPUTER_BUTTON.addActionListener((e)->{
            if(PHOTO_LOCATION.getText().equals("")){
               JOptionPane.showConfirmDialog(errorFrame, "Invalid Input", "Error", JOptionPane.CLOSED_OPTION);
            }
            else {
                String path= PHOTO_LOCATION.getText();
                setPhoto(path);
                PHOTO_LOCATION.setText("");
                repaint();
            }
        });
    }

    private void setNormalButton(JButton button, String text){
        button.setFont(new Font("arial", Font.BOLD, FONT_SIZE));
        button.setText(text);
    }

    private void setPhoto(String path){
        try {
            add ="";
            newPhotoName=1;
            int correctionX=215;
            int correctionY=150;
            resetPhoto();

            pathInComputer =setNewPhoto(path);
            file = new File(pathInComputer + add);
            if(file.exists()){
                image = ImageIO.read(file);

                qualityWidth =image.getWidth();
                qualityHeight =image.getHeight();
                fullPhoto =new Pixel[qualityHeight][qualityWidth];
                if(qualityWidth>qualityHeight){
                    shrinkPhoto=(qualityWidth)/MAKE_RELATIVE_SIZE;}
                else{
                    shrinkPhoto=(qualityHeight)/MAKE_RELATIVE_SIZE;}
                while (!checkDivideByTwo(shrinkPhoto)){
                    shrinkPhoto++;}

                shrinkPhotoHeight= qualityHeight /shrinkPhoto;
                shrinkPhotoWidth= qualityWidth /shrinkPhoto;
                shrinkImage=new Pixel[shrinkPhotoHeight][shrinkPhotoWidth];
                shrinkImageOriginal=new Pixel[shrinkPhotoHeight][shrinkPhotoWidth];
                int middleXLocation=(Window.WINDOW_WIDTH/2)-(qualityWidth/shrinkPhoto/2)-correctionX;
                int middleYLocation=(Window.WINDOW_HEIGHT/2)-(qualityHeight/shrinkPhoto/2)-correctionY;
                for (int i = 0; i < qualityHeight; i+=shrinkPhoto) {
                    for (int j = 0; j < qualityWidth; j+=shrinkPhoto) {
                        try {
                            int pixel = image.getRGB(j,i);
                            Color pixelColor = new Color(pixel);
                            shrinkImage[i/shrinkPhoto][j/shrinkPhoto]=new Pixel(middleXLocation +j* CHANGE_RATIO /shrinkPhoto,middleYLocation+i* CHANGE_RATIO /shrinkPhoto,pixelColor);
                            shrinkImageOriginal[i/shrinkPhoto][j/shrinkPhoto]=new Pixel(middleXLocation +j* CHANGE_RATIO /shrinkPhoto,middleYLocation+i* CHANGE_RATIO /shrinkPhoto,pixelColor);
                        } catch (Exception ignored) {
                        }
                    }}
                for (int i = 0; i < qualityHeight; i++) {
                    for (int j = 0; j < qualityWidth; j++) {
                        int pixel = image.getRGB(j,i);
                        Color pixelColor = new Color(pixel);
                        fullPhoto[i][j]=new Pixel( j,i,pixelColor);
                    }}
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        repaint();
    }

    private String setNewPhoto(String location){
        String resultPath="";
        String changeAdd="";

        for (int i = location.length()-2; i > 0; i--) {
            changeAdd+=location.charAt(i);
            if(location.charAt(i)=='.'){
                break;}
        }
        for (int i = changeAdd.length()-1; i >= 0; i--) {
            add +=changeAdd.charAt(i);
        }
        for (int i = 1; i < location.length()- add.length()-1; i++) {
            resultPath+=location.charAt(i);
        }
        return resultPath;}

    private boolean checkDivideByTwo(int check){
        while (check>2){
            check /= 2;
        }
        if(check==2){
            return true;}
        else return false;
    }

    private Pixel[][] arrayToDraw(){
        if(displayOriginal){
            return shrinkImageOriginal;}
        else return shrinkImage;
    }

    private void blue(Pixel[][]photo, int width, int height, int change){
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Color color=photo[i][j].getColor();
                int red= color.getRed();
                int greed= color.getGreen();
                int blue= color.getBlue()+change;
                blue=checkIfColorToBig(blue);
                try{
                    Color newColor=new Color(red,greed,blue);
                    photo[i][j].setColor(newColor);}catch (Exception e) {
                    e.printStackTrace();
                }
            }}
        repaint();
        if(change>NOTHING){
            blue++;}
        else {
            blue--;
        }    }

    private void green(Pixel[][]photo, int width, int height, int change){
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Color color=photo[i][j].getColor();
                int red= color.getRed();
                int green= color.getGreen()+change;
                int blue= color.getBlue();
                green=checkIfColorToBig(green);
                try{
                    Color newColor=new Color(red,green,blue);
                    photo[i][j].setColor(newColor);}catch (Exception ignored){}
            }}
        repaint();
        if(change>NOTHING){
            green++;}
        else {
            green--;
        }    }

    private void red(Pixel[][]photo, int width, int height, int change){
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Color color=photo[i][j].getColor();
                int red= color.getRed()+change;
                int greed= color.getGreen();
                int blue= color.getBlue();
                red=checkIfColorToBig(red);
                try{
                    Color newColor=new Color(red,greed,blue);
                    photo[i][j].setColor(newColor);}catch (Exception ignored){}
            }}
        repaint();
        if(change>NOTHING){
            red++;}
        else {
            red--;
        }    }

    private void light(Pixel[][]photo, int width, int height, int change){
        int lightAmount=550;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Color color=photo[i][j].getColor();
                int red= color.getRed();
                int green= color.getGreen();
                int blue= color.getBlue();
                try{
                    if(red+green+blue>lightAmount) {
                        red += change;
                        green+=change;
                        blue+=change;
                        red=checkIfColorToBig(red);
                        green=checkIfColorToBig(green);
                        blue=checkIfColorToBig(blue);}
                    Color newColor=new Color(red,green,blue);
                    photo[i][j].setColor(newColor);}catch (Exception ignored){}
            }
        }
        repaint();
        if(change>NOTHING){
            light++;}
        else {
            light--;
        }    }

    private void shadowChange(Pixel[][]photo, int width, int height, int change){
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Color color=photo[i][j].getColor();
                int red= color.getRed();
                int green= color.getGreen();
                int blue= color.getBlue();
                try{
                    if(red+green+blue<MAX_COLOR) {
                        red -= change;
                        green-=change;
                        blue-=change;
                        red=checkIfColorToLittle(red);
                        green=checkIfColorToLittle(green);
                        blue=checkIfColorToLittle(blue);}
                    Color newColor=new Color(red,green,blue);
                    photo[i][j].setColor(newColor);}catch (Exception ignored){}
            }
        }
        repaint();
        if(change>NOTHING){
            shadows++;}
        else {
            shadows--;
        }    }

    private int checkIfColorToLittle(int color){
        if(color<NOTHING)
            return NOTHING;

        return color;
    }

    private void enhanceContrast(Pixel[][]photo, int width, int height, int change){
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Color color=photo[i][j].getColor();
                int red= brightDarkCheck(color.getRed(),change);
                int greed= brightDarkCheck(color.getGreen(),change);
                int blue= brightDarkCheck(color.getBlue(),change);
                Color newColor=new Color(red,greed,blue);
                try {
                    photo[i][j].setColor(newColor);
                } catch (Exception ignored) {}
            }
        }
        repaint();
        if(change>NOTHING){
            contrast++;}
        else {
            contrast--;
        }
    }

    private int brightDarkCheck(int color,int change){
        if(color+change>=MAX_COLOR){
            return MAX_COLOR;
        }
        else if(color>130){
            color+=change;
        }else{
            if(color>change){
                color-=change;
            }else {
                color=NOTHING;
            }
        }
        return color;}

    private void resetPhoto(){
        for (int i = 0; i < qualityHeight; i+=shrinkPhoto) {
            for (int j = 0; j < qualityWidth; j+=shrinkPhoto) {
                int pixel = image.getRGB(j,i);
                Color pixelColor = new Color(pixel);
                try {
                    shrinkImage[i/shrinkPhoto][j/shrinkPhoto].setColor(pixelColor);
                } catch (Exception ignored) {}
                repaint();
                resetAds();
            }}
        for (int i = 0; i < qualityHeight; i++) {
            for (int j = 0; j < qualityWidth; j++) {
                int pixel = image.getRGB(j,i);
                Color pixelColor = new Color(pixel);
                fullPhoto[i][j].setColor(pixelColor);
            }}
    }

    private void resetAds(){
        shadows=NOTHING;
        contrast=NOTHING;
        light =NOTHING;
        color=NOTHING;
        warm=NOTHING;
        sparkles =NOTHING;
        addNoise=NOTHING;
        red=NOTHING;
        green=NOTHING;
        blue=NOTHING;
        white=NOTHING;
        sunLight =NOTHING;
        isNegativeEffect =false;
        isGrayEffect =false;
        isMirrorEffect =false;
    }

    private void addColor(Pixel[][]photo, int width, int height, int change){
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Color color=photo[i][j].getColor();
                try{
                    int red= color.getRed();
                    int green= color.getGreen();
                    int blue=color.getBlue();
                    if(red>=green && green>=blue){
                        red+=change;
                        red= checkIfColorToBig(red);
                    }
                    else if(blue>=green && green>=red){
                        blue+=change;
                        blue= checkIfColorToBig(blue);

                    }
                    else if(green>=red && red>=blue){
                        green+=change;
                        green= checkIfColorToBig(green);
                    }
                    Color newColor=new Color(red,green,blue);
                    photo[i][j].setColor(newColor);
                }catch (Exception ignored){}
            }
        }
        repaint();
        if(change>NOTHING){
            color++;}
        else {
            color--;
        }
    }

    private int checkIfColorToBig(int color){
        if(color>=MAX_COLOR)
            return MAX_COLOR;

        return color;
    }

    private void white(Pixel[][]photo, int width, int height, int change){
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Color color=photo[i][j].getColor();
                try{
                    int red= color.getRed()+change;
                    int green= color.getGreen()+change;
                    int blue=color.getBlue()+change;
                    red=checkIfColorToBig(red);
                    green=checkIfColorToBig(green);
                    blue=checkIfColorToBig(blue);

                    Color newColor=new Color(red,green,blue);
                    if(red<MAX_COLOR && green<MAX_COLOR && blue<MAX_COLOR )
                        photo[i][j].setColor(newColor);
                }catch (Exception ignored){}
            }
        }
        repaint();
        if(change>NOTHING){
            white++;}
        else {
            white--;
        }
    }

    private void warm(Pixel[][]photo, int width, int height, int change){
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Color color=photo[i][j].getColor();
                try{
                    int red= color.getRed()+change;
                    int greed= color.getGreen()+change;
                    int blue=color.getBlue();
                    red=checkIfColorToBig(red);
                    greed=checkIfColorToBig(greed);
                    Color newColor=new Color(red,greed,blue);
                    photo[i][j].setColor(newColor);
                }catch (Exception ignored){}
            }
        }
        repaint();
        if(change>NOTHING){
            warm++;}
        else {
            warm--;
        }
    }

    private void yellow(Pixel[][]photo, int width, int height, int change){
        int difference=60;
        int strongerThanBlue=60;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                try{
                    int red = photo[i][j].getColor().getRed();
                    int green=photo[i][j].getColor().getGreen();
                    int blue=photo[i][j].getColor().getBlue();

                    if(red+difference>green &&
                       green+difference>red)
                    if(blue+strongerThanBlue<red && blue+strongerThanBlue<green)
                    {
                        red+=change;
                        green+=change;
                        blue+=change;

                        red=checkIfColorToBig(red);
                        green=checkIfColorToBig(green);
                        blue=checkIfColorToBig(blue);
                        photo[i][j].setColor(new Color(red,green,blue));
                    }
                }catch (Exception ignored){}
            }
        }
        repaint();
        if(change>NOTHING){
        sunLight++;}
        else sunLight--;
    }

    private void newLabel(JLabel label,int x,int y){
        label.setFont(new Font("arial", Font.BOLD, LITTLE_FONT_SIZE));
        label.setBounds(x,y, NEW_LABEL_WIDTH, NEW_LABEL_HEIGHT);
        label.setForeground(Color.WHITE);
        add(label);
    }
    //effects
    private void negative(Pixel[][]photo, int width, int height){
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Color color=photo[i][j].getColor();
                try{
                    int red= MAX_COLOR-color.getRed();
                    int greed= MAX_COLOR-color.getGreen();
                    int blue=MAX_COLOR-color.getBlue();
                    Color newColor=new Color(red,greed,blue);
                    photo[i][j].setColor(newColor);
                }catch (Exception ignored){}
            }
        }
        repaint();
        isNegativeEffect =!isNegativeEffect;
    }

    private void mirror(Pixel[][]photo, int width, int height){
        Pixel[][]photoToCopy=new Pixel[height][width];
        for (int i = 0; i <height; i++) {
            for (int j = 0; j < width; j++) {
                photoToCopy[i][j]=new Pixel(photo[i][j].getX(),photo[i][j].getY(),photo[i][j].getColor());
            }
        }
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                try{
                    Color pixelColor = photoToCopy[i][width-j].getColor();
                    photo[i][j].setColor(pixelColor);
                }catch (Exception ignored){}
            }
        }
        repaint();
        isMirrorEffect =true;
    }

    private void makeGray(Pixel[][]photo, int width, int height){
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Color color=photo[i][j].getColor();
                try{
                    int red= color.getRed();
                    int greed= color.getGreen();
                    int blue=color.getBlue();
                    int gray=(red+greed+blue)/3;
                    Color newColor=new Color(gray,gray,gray);
                    photo[i][j].setColor(newColor);
                }catch (Exception ignored){}
            }
        }
        repaint();
        isGrayEffect =true;
    }

    private void sparkles(Pixel[][]photo, int width, int height){
        int maxDistance=50;
        int distance;
        int add=20;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                try{
                    distance=(int)((Math.random() * (maxDistance)));

                    photo[i][j+=distance].setColor(new Color(photo[i][j+=distance].getColor().getRed()+add,
                            photo[i][j+=distance].getColor().getGreen()+add,
                            photo[i][j+=distance].getColor().getBlue()+add));
                }catch (Exception ignored){}
            }
        }
        repaint();
        sparkles++;
    }

    private void creativeChange(Pixel[][]photo, int width, int height){
        Color newColor;
        int max=14;
        int min=-14;

        int green=(int)((Math.random() * (max - min)) + min);
        int red=(int)((Math.random() * (max - min)) + min);
        int blue=(int)((Math.random() * (max - min)) + min);

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                try{
                    Color previousColor=new Color(photo[i][j].getColor().getRGB());
                    newColor=new Color(previousColor.getRed()+red,previousColor.getGreen()+green,previousColor.getBlue()+blue);
                    photo[i][j].setColor(newColor);
                }catch (Exception ignored){}
            }
        }
        for (int i = 0; i < qualityHeight; i++) {
            for (int j = 0; j < qualityWidth; j++) {
                try{
                    Color pixelColor=new Color(fullPhoto[i][j].getColor().getRGB());
                    newColor=new Color(pixelColor.getRed()+red,pixelColor.getGreen()+green,pixelColor.getBlue()+blue);
                    fullPhoto[i][j].setColor(newColor);
                }catch (Exception ignored){}
            }
        }
        repaint();
    }

    private void addNoise(Pixel[][]photo, int width, int height){
        int max=15;
        int min=-15;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                try{
                    int green=(int)((Math.random() * (max - min)) + min);
                    int red=(int)((Math.random() * (max - min)) + min);
                    int blue=(int)((Math.random() * (max - min)) + min);
                    Color previousColor=new Color(photo[i][j].getColor().getRGB());
                    Color newColor=new Color(previousColor.getRed()+red,previousColor.getGreen()+green,previousColor.getBlue()+blue);
                    photo[i][j].setColor(newColor);
                }catch (Exception ignored){}
            }
        }
        repaint();
        addNoise++;
    }

    private void changeLanguage(){
        String []effectsNames;
        String []buttonsNames;
        isLanguageEnglish=!isLanguageEnglish;
        if(isLanguageEnglish){
        effectsNames=ENGLISH_EFFECT_NAMES;
        buttonsNames= ENGLISH_BUTTON_NAMES;
        RESET.setText("reset");
        SAVE.setText("save");
        MENU_BUTTON.setText("effects");
        edited="showEdited";
        original="showOriginal";
        CHANGE_LANGUAGE.setText("לעברית");
        EXPLAIN.setText("copy and paste photo path (ctrl+shift+c)");
        if(facebook){
        FACEBOOK_BUTTON.setText(FACEBOOK_ENGLISH);}
        else {
        FACEBOOK_BUTTON.setText(FACEBOOK_ENGLISH_LOCAL);
        }
        EXPLAIN_PATH.setText(EXPLAIN_PATH_ENGLISH);
        EXPLAIN_USERNAME.setText(EXPLAIN_USERNAME_ENGLISH);
        }
        else{
            effectsNames=HEBREW_EFFECT_NAMES;
            buttonsNames= HEBREW_BUTTON_NAMES;
            RESET.setText("אתחל");
            SAVE.setText("שמור");
            MENU_BUTTON.setText("אפקטים");
            edited="הצג ערוך";
            original="הצג מקורי";
            CHANGE_LANGUAGE.setText("ToEnglish");
            EXPLAIN.setText("העתק והדבק את נתיב התמונה (ctrl+shift+c)");
            if(facebook){
            FACEBOOK_BUTTON.setText(FACEBOOK_HEBREW);}
            else {
            FACEBOOK_BUTTON.setText(FACEBOOK_HEBREW_LOCAL);
            }
            EXPLAIN_PATH.setText(EXPLAIN_PATH_HEBREW);
            EXPLAIN_USERNAME.setText(EXPLAIN_USERNAME_HEBREW);
        }
        if(displayOriginal){
            SHOW_ORIGINAL_PHOTO.setText(edited);
        }else
            SHOW_ORIGINAL_PHOTO.setText(original);
            for (int i = 0; i < EFFECT_BUTTONS.length; i++) {
                    EFFECT_BUTTONS[i].setText(effectsNames[i]);
            }
        for (int i = 0; i < FUNCTION.length; i++) {
            FUNCTION[i].setText(buttonsNames[i]);
        }
    }

    private void takeFacebookPhoto(){
        try {
            String path=PATH_TEXT_FIELD.getText();
            String location=USERNAME_TEXT_FIELD.getText();
            if(!(path.equals("")||location.equals(""))){
                ScrapPhoto scrapPhoto=new ScrapPhoto();
                scrapPhoto.setPartOfLocation(path);
                scrapPhoto.usePhoto(location);
                PATH_TEXT_FIELD.setText("");
                USERNAME_TEXT_FIELD.setText("");
                setPhoto(scrapPhoto.getFullLocation());
            }else {
                JOptionPane.showConfirmDialog(errorFrame, "Invalid Input", "Error", JOptionPane.CLOSED_OPTION);
            }
        } catch (Exception e) {
            e.printStackTrace();
            PATH_TEXT_FIELD.setText("");
            USERNAME_TEXT_FIELD.setText("");
        }
    }

    private void setLargePhoto(){
        //timesEffects
        while (sparkles >NOTHING) {
            sparkles(fullPhoto,qualityWidth,qualityHeight);
            sparkles -=2;
        }
        while (addNoise>NOTHING) {
            addNoise(fullPhoto,qualityWidth,qualityHeight);
            addNoise-=2;
        }
        //effects
        if(isNegativeEffect){negative(fullPhoto,qualityWidth,qualityHeight);}
        if(isGrayEffect){makeGray(fullPhoto,qualityWidth,qualityHeight);}
        if(isMirrorEffect){mirror(fullPhoto,qualityWidth,qualityHeight);}

        //editing changes
        if(white!=NOTHING){white(fullPhoto,qualityWidth,qualityHeight,change*white);}
        if(color!=NOTHING){addColor(fullPhoto,qualityWidth,qualityHeight,change*color);}
        if(warm!=NOTHING){warm(fullPhoto,qualityWidth,qualityHeight,change*warm);}
        if(light!=NOTHING){light(fullPhoto,qualityWidth,qualityHeight,change*light);}
        if(shadows!=NOTHING){shadowChange(fullPhoto,qualityWidth,qualityHeight,change*shadows);}
        if(contrast!=NOTHING){enhanceContrast(fullPhoto,qualityWidth,qualityHeight,change*contrast);}
        if(sunLight !=NOTHING){yellow(fullPhoto,qualityWidth,qualityHeight,change* sunLight);}
        if(red!=NOTHING){red(fullPhoto,qualityWidth,qualityHeight,change*red);}
        if(green!=NOTHING){green(fullPhoto,qualityWidth,qualityHeight,change*green);}
        if(blue!=NOTHING){blue(fullPhoto,qualityWidth,qualityHeight,change*blue);}
        try{
            File photo=new File(pathInComputer +newPhotoName+ add);
            BufferedImage savedImage=ImageIO.read(file);

            for (int i = 0; i < savedImage.getWidth(); i++) {
                for (int j = 0; j <savedImage.getHeight(); j++) {
                    Color pixel = fullPhoto[j][i].getColor();
                    savedImage.setRGB(i, j, pixel.getRGB());
                }
            }
            ImageIO.write(savedImage,"png",photo);
            newPhotoName++;
        }catch (Exception ignored){}
    }
}