import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;
import java.util.LinkedList;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class GameCourt extends JPanel {

    public static final int ONE_PIXEL = 5;

    private String[] stageFilenames;
    private Enemy[] enemyTypes;
    private String playerFilename;
    private String swordFilename;
    private String highScoreTextFile;
    private List<String> highScores;

    private LinkedList<Stage> stages;
    private Stage currentStage;
    private Player player;

    private boolean playing;
    private boolean gameOver;
    private boolean inTitleScreen;
    private boolean inInfoScreen;
    private boolean inHighScoreScreen;
    private boolean inVictoryScreen;
    private boolean inAir;

    private int timeElapsed;

    public GameCourt(String[] stageFilenames, Enemy[] enemyTypes, String playerFilename,
            String swordFilename, String highScoreTextFile) {

        this.stageFilenames = stageFilenames;
        this.enemyTypes = enemyTypes;
        this.playerFilename = playerFilename;
        this.swordFilename = swordFilename;
        this.highScoreTextFile = highScoreTextFile;
        stages = new LinkedList<>();

       toTitleScreen();

        if (stageFilenames.length != enemyTypes.length)  {

            throw new IllegalArgumentException("ERROR: Not enough enemies/stages provided.");

        }

        setFocusable(true);

        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {

            }
        });

        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (playing) {
                    if (e.getKeyCode() == KeyEvent.VK_A)
                            player.walk(-ONE_PIXEL);
                    else if (e.getKeyCode() == KeyEvent.VK_D)
                            player.walk(ONE_PIXEL);
                    else if (e.getKeyCode() == KeyEvent.VK_W) {
                        if (player.getPosY() == 0) {
                            player.fall(5 * ONE_PIXEL);
                            inAir = true;
                        }
                    }
                    else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                        Character enemyAttacked = currentStage.swordIntersectsAnEnemy();
                        if (enemyAttacked != null) {
                            currentStage.killEnemy(enemyAttacked);
                        }
                    }
                }
                else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (inTitleScreen) {
                        reset();
                    }
                    else if (gameOver || inVictoryScreen) {
                        toTitleScreen();
                    }
                    else if (inInfoScreen) {
                        toHighScoreScreen();
                    }
                }
                else if (e.getKeyCode() == KeyEvent.VK_I) {
                    if (inTitleScreen) {
                        toInfoScreen();
                    }
                }
                else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    if (inInfoScreen || inHighScoreScreen) {
                        toTitleScreen();
                    }
                }
            }
        });

        Timer timer = new Timer(35, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                    tick();
            }
        });
        timer.start();
    }

    public void updateHighScores(String user) {
        try {
            BufferedReader highScoresIn = new BufferedReader(new FileReader(highScoreTextFile));
            String currentLine = highScoresIn.readLine();
            String[] newHighScores = new String[10];

            String newHighScore = user + ": " + timeElapsed;

            int count = 0;
            boolean addHighScore = true;

            if (currentLine != null) {
                while (currentLine != null && count < 10) {

                    if (currentLine.trim().length() > 0) {

                      if (currentLine.indexOf(':') == -1) {
                          System.out.println("ERROR: Invalid format in high score file.");
                          return;
                      }

                      String[] splitLine = currentLine.split(":", 2);

                      int userTime = Integer.parseInt(splitLine[1].trim());

                      if (timeElapsed < userTime && addHighScore) {
                              if (user.equals(splitLine[0])) {
                                  newHighScores[count] = newHighScore;
                                  count++;
                              }
                              else {
                                  newHighScores[count] = newHighScore;
                                  if (count + 1 < 10) {
                                      newHighScores[count + 1] = currentLine;
                                      count++;
                                  }
                                  count++;
                              }
                              addHighScore = false;
                      }
                      else {
                          newHighScores[count] = currentLine;
                          count++;
                          if (user.equals(splitLine[0])) {
                              addHighScore = false;
                          }
                      }
                  }
                  currentLine = highScoresIn.readLine();
              }
            }
            if (addHighScore && count < 10) {
                newHighScores[count] = newHighScore;
            }
            else {
                newHighScores[0] = newHighScore;
                addHighScore = false;
            }

            highScoresIn.close();

            BufferedWriter highScoresOut = new BufferedWriter(new FileWriter(highScoreTextFile));

            for (int i = 0; i < 10; i++) {
                if (newHighScores[i] != null) {
                    highScoresOut.write(newHighScores[i]);
                    highScoresOut.newLine();
                }
            }

            highScoresOut.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void tick() {
        if (playing) {
            int tickCount = timeElapsed % 20;
            currentStage.updateEnemies(tickCount);

            if (inAir) {
              if (player.getPosY() == 0) {
                inAir = false;
              }
              else {
                player.fall(-1 * ONE_PIXEL);
              }
            }

            if (currentStage.playerIntersectsAnEnemy()) {
                toGameOverScreen();
                stages.clear();
            }

            if (currentStage.playerIntersectsWall()) {
                if (currentStage.getNumEnemies() == 0) {
                    if (stages.size() > 0) {
                        currentStage = stages.remove();
                        player.resetPos();
                    }
                    else {
                        toVictoryScreen();
                    }
                }
            }

            timeElapsed++;
        }
        repaint();
    }

    public void toVictoryScreen() {
        inTitleScreen = false;
        playing = false;
        inInfoScreen = false;
        inHighScoreScreen = false;
        gameOver = false;
        inVictoryScreen = true;
    }

    public void toGameOverScreen() {
        inVictoryScreen = false;
        inTitleScreen = false;
        playing = false;
        inInfoScreen = false;
        inHighScoreScreen = false;
        gameOver = true;
    }

    public void toHighScoreScreen() {
      try {
        inVictoryScreen = false;
        inTitleScreen = false;
        playing = false;
        gameOver = false;
        inInfoScreen = false;
        inHighScoreScreen = true;

        highScores = new LinkedList<String>();

        BufferedReader highScoreReader = new BufferedReader(new FileReader(highScoreTextFile));

        String line = null;

        while ((line = highScoreReader.readLine()) != null) {

          String[] splitLine = line.split(":");

          int timeInSeconds = 35 * Integer.parseInt(splitLine[1].trim()) / 1000;

          String scoreLine = splitLine[0] + " finished in " + timeInSeconds + " seconds.";

          highScores.add(scoreLine);
        }

        highScoreReader.close();

      }
      catch (Exception e) {
        System.out.println("Exception while reading high scores");
      }
    }

    public void toInfoScreen() {
        inTitleScreen = false;
        inVictoryScreen = false;
        playing = false;
        gameOver = false;
        inHighScoreScreen = false;
        inInfoScreen = true;
    }

    public void toTitleScreen() {
        inVictoryScreen = false;
        playing = false;
        gameOver = false;
        inInfoScreen = false;
        inHighScoreScreen = false;
        inTitleScreen = true;
    }

    public void reset() {

        inTitleScreen = false;
        inVictoryScreen = false;
        playing = true;
        gameOver = false;
        inInfoScreen = false;
        inHighScoreScreen = false;
        player = new Player(0, 0, getPreferredSize().width, getPreferredSize().height,
                playerFilename, swordFilename);

        for (int i = 0; i < stageFilenames.length; i++) {
            stages.add(new Stage(stageFilenames[i], player, enemyTypes[i], 3));
        }

        currentStage = stages.remove();

    }

    public boolean playerWon() {
        return inVictoryScreen;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (inTitleScreen) {
            try {
                BufferedImage titleScreenImg = ImageIO.read(new File("titlescreen.png"));
                g.drawImage(titleScreenImg, 0, 0, titleScreenImg.getWidth() / 2,
                        titleScreenImg.getHeight() / 2, null);
            }
            catch (Exception e) {
                System.out.println("ERROR: \'Title screen\' image file not found.");
            }
        }
        else if (inInfoScreen) {
            try {
                BufferedImage infoScreenImg = ImageIO.read(new File("infoscreen.png"));
                g.drawImage(infoScreenImg, 0, 0, infoScreenImg.getWidth() / 2,
                        infoScreenImg.getHeight() / 2, null);
            }
            catch (Exception e) {
                System.out.println("ERROR: \'Info screen\' image file not found.");
            }
        }
        else if (inVictoryScreen) {
            try {
                BufferedImage victoryScreenImg = ImageIO.read(new File("victoryscreen.png"));
                g.drawImage(victoryScreenImg, 0, 0, victoryScreenImg.getWidth() / 2,
                        victoryScreenImg.getHeight() / 2, null);
            }
            catch (Exception e) {
                System.out.println("ERROR: \'Victory screen\' image file not found.");
            }
        }
        else if (inHighScoreScreen) {
            try {
                BufferedImage HighScoreScreenImg = ImageIO.read(new File("highscores.png"));
                g.drawImage(HighScoreScreenImg, 0, 0, HighScoreScreenImg.getWidth() / 2,
                        HighScoreScreenImg.getHeight() / 2, null);

                int i = 1;

                for (String scoreLine : highScores) {
                  if (i > 5) {
                    break;
                  }
                  g.setColor(Color.BLACK);
                  g.drawString(scoreLine, 0, 50 * i);
                  i++;
                }

            }
            catch (Exception e) {
                System.out.println("ERROR: \'High score screen\' image file not found.");
            }
        }
        else if (gameOver) {
            try {
                BufferedImage gameOverImg = ImageIO.read(new File("gameover.png"));
                g.drawImage(gameOverImg, 0, 0, gameOverImg.getWidth() / 2,
                        gameOverImg.getHeight() / 2, null);
            }
            catch (Exception e) {
                System.out.println("ERROR: \'Game over\' image file not found.");
            }
        }
        else if (playing) {
            currentStage.paintComponent(g);
        }
    }
    @Override
    public Dimension getPreferredSize() {

        Dimension defaultDimension = new Dimension(750, 500);

        if (currentStage != null) {
            return currentStage.getPreferredSize();
        }

        return defaultDimension;
    }
}
